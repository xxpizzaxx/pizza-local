package moe.pizza.local

import com.redis.RedisClient

import moe.pizza.eveapi._

import argonaut._, Argonaut._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


/**
 * Class capable of parsing local pilot lists
 * @param redis instance of a RedisClient
 */
class LocalScan(redis: RedisClient)(implicit val ex: ExecutionContext) {
  case class LocalScanResult(pilots: Seq[Pilot], apiCount: Int, cacheCount: Int)
  implicit def PilotCodecJson = casecodec6(Pilot.apply, Pilot.unapply)("id", "name", "corporation", "corporationName", "alliance", "allianceName")
  val api = new EVEAPI()

  /**
   * Scan a list of pilots and return the full Pilot data
   * @param data list of pilot names
   * @return seq of Pilot objects containing full data
   */
  def scan(data: String): LocalScanResult = {
    val ids = data.split("\\r?\\n").grouped(100).flatMap { idSet =>
      api.eve.CharacterID(idSet).sync() match {
        case Success(r) => r.seq.map {_.characterID.toLong}
        case Failure(f) => Seq.empty
      }
    }.toSeq
    println(ids)
    val cachedPilots = redis.mget(ids.head.toString, ids.tail.map{_.toString}:_*).getOrElse(Seq.empty).flatten.map{_.decodeOption[Pilot]}.flatten

    // end of the test code
    val cachedPilotIds = cachedPilots.map {
      _.id
    }
    // get the new ones
    val lookupIds = ids.filter(!cachedPilotIds.contains(_))
    val results = lookupIds.grouped(250).flatMap { ids =>
      api.eve.CharacterAffiliation(ids.map {
        _.toString
      }).sync() match {
        case Success(r) => r.map {Pilot.fromApiResponse}
        case Failure(f) => Seq.empty
      }
    }.toSeq

    // persist results to redis
    if(results.nonEmpty) {
      redis.mset(results.map{pilot => (pilot.id, pilot.asJson)} :_*)
    }

    // merge results and return
    LocalScanResult(results ++ cachedPilots, results.size, cachedPilots.size)
  }
}
