package moe.pizza.local

import org.scalatra._
import org.scalatra.scalate.ScalateSupport

class AuthServlet extends ScalatraServlet with ScalateSupport {

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
    <html>
      <p>
        { input }
      </p>
    </html>
  }
}