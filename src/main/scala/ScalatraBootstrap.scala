import javax.servlet.ServletContext

import moe.pizza.local.LocalServlet
import org.scalatra.LifeCycle

class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {

    context mount(new LocalServlet, "/*")
  }
}
