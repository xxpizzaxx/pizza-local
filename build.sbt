name := "local"

organization := "moe.pizza"

scalaVersion := "2.11.7"

isSnapshot := true

resolvers += Resolver.jcenterRepo

libraryDependencies += "org.scalatra" %% "scalatra" % "2.3.0"

libraryDependencies += "org.scalatra" %% "scalatra-scalate" % "2.3.0"

libraryDependencies += "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016"

libraryDependencies += "org.eclipse.jetty" % "jetty-webapp" % "9.0.4.v20130625"

libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.1.0"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.13"

libraryDependencies += "net.debasishg" %% "redisclient" % "3.0"

libraryDependencies += "io.argonaut" %% "argonaut" % "6.0.4"

libraryDependencies += "moe.pizza" %% "eveapi" % "0.13"

