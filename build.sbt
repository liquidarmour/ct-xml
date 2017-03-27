import play.core.PlayVersion

name := """ct-xml"""
organization := "uk.gov.hmrc"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "uk.gov.hmrc" %% "ct-calculations" % "2.120.0",
  "uk.gov.hmrc" %% "crypto" % "3.0.0",
  "uk.gov.hmrc" %% "domain" % "4.0.0",
  "uk.gov.hmrc" %% "play-time" % "0.3.0",

  specs2 % Test,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "uk.gov.hmrc" %% "hmrctest" % "2.2.0" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)
resolvers += Resolver.bintrayRepo("hmrc", "releases")
resolvers += Resolver.jcenterRepo

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


fork in run := true