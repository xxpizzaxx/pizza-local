package moe.pizza.local

/**
 * Created by Andi on 18/06/2015.
 */
case class Pilot(id: Long, name: String, corporation: Long, corporationName: String, alliance: Option[Long], allianceName: Option[String])

object Pilot {
  def fromApiResponse(row: moe.pizza.eveapi.generated.eve.CharacterAffiliation.Row): Pilot = {
    new Pilot(row.characterID.toLong, row.characterName, row.corporationID.toLong, row.corporationName, Option(row.allianceID.toLong), Option(row.allianceName))
  }
}
