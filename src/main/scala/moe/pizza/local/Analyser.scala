package moe.pizza.local

/**
 * Created by Andi on 18/06/2015.
 */

case class Corporation(id: Long, name: String)

case class Alliance(id: Long, name: String)

case class Analysis(
                   corporations: Map[Corporation, Int],
                   alliances: Map[Alliance, Int]
                     )

object Analysis {
  def fromPilots(pilots: Seq[Pilot]): Analysis = {
    new Analysis(
      pilots.groupBy{p => Corporation(p.corporation, p.corporationName)}.transform{(k, v) => v.size},
      pilots.groupBy{p => Alliance(p.alliance.get, p.allianceName.get)}.transform{(k, v) => v.size}
    )
  }
}
