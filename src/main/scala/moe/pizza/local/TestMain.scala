package moe.pizza.local

import com.redis.RedisClient


import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.global

/**
 * Created by Andi on 17/06/2015.
 */
object TestMain extends App {
  implicit val ec = global
  val redis = new RedisClient()
  val scanner = new LocalScan(redis)
  val r = scanner.scan("wheniaminspace")
  r.pilots.foreach {println}
}
