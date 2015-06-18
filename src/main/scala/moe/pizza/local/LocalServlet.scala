package moe.pizza.local

import com.redis.RedisClient
import org.scalatra._
import org.scalatra.scalate.ScalateSupport

class LocalServlet extends ScalatraServlet with ScalateSupport {

  import scala.concurrent.ExecutionContext.global
  val localscan = new LocalScan(new RedisClient())(global)


  get("/") {
    <html>
      <h1>PIZZA local scan tool</h1>
      <p>Paste your local here:</p>
      <form action="/" method="post">
        <textarea name="input"></textarea>
        <input name="Parse" type="submit" value="Parse" />
      </form>
    </html>
  }

  get("/:id") {
    contentType="text/html"
    ssp("frontpage.ssp")
  }

  post("/") {
    val input = params("input")
    val scan = localscan.scan(input)
    val analysis = Analysis.fromPilots(scan.pilots)
    <html>
      <h1>local scan breakdown, {scan.cacheCount} from cache, {scan.apiCount} from API</h1>
      <table>
        <tr>
          <th>Corporation</th>
          <th>Count</th>
        </tr>
        {for ((corp, count) <- analysis.corporations.toSeq.sortBy{0-_._2}) yield
          <tr>
            <td>{corp.name}</td>
            <td>{count}</td>
          </tr>
        }
      </table>

      <table>
        <tr>
          <th>Alliance</th>
          <th>Count</th>
        </tr>
        {for ((alli, count) <- analysis.alliances.toSeq.sortBy{0-_._2}) yield
          <tr>
            <td>{alli.name}</td>
            <td>{count}</td>
          </tr>
        }
      </table>
    </html>
  }
}