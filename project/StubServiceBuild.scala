import sbt._

object StubServiceBuild extends Build with MicroService {

  val appName = "investment-tax-relief-submission-dynamic-stub"

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {
  import play.core.PlayVersion
  import play.sbt.PlayImport._


  private val microserviceBootstrapVersion = "6.13.0"
  private val hmrcTestVersion = "2.3.0"
  private val scalaTestVersion = "2.2.6"
  private val pegDownVersion = "1.6.0"
  private val domainVersion = "5.0.0"
  private val playUrlBindersVersion = "2.1.0"
  private val mockitoAll = "1.9.5"
  private val scalaTestPlus = "1.5.1"
  private val playReactivemongoVersion = "5.2.0"
  private val jsonSchemaValidatorVersion = "2.2.6"

  val compile = Seq(
    ws,
    "uk.gov.hmrc" %% "microservice-bootstrap" % microserviceBootstrapVersion,
    "uk.gov.hmrc" %% "play-reactivemongo" % playReactivemongoVersion,
    "uk.gov.hmrc" %% "play-url-binders" % playUrlBindersVersion,
    "uk.gov.hmrc" %% "domain" % domainVersion,
    "com.github.fge" % "json-schema-validator" % jsonSchemaValidatorVersion
  )

  trait TestDependencies {
    lazy val scope: String = "test"
    lazy val test : Seq[ModuleID] = ???
  }

  object Test {
    def apply() :Seq[ModuleID] = new TestDependencies {
      override lazy val test = Seq(
        "uk.gov.hmrc" %% "hmrctest" % hmrcTestVersion % scope,
        "org.scalatest" %% "scalatest" % scalaTestVersion % scope,
        "org.pegdown" % "pegdown" % pegDownVersion % scope,
        "org.mockito" % "mockito-all" % mockitoAll % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlus % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope
      )
    }.test
  }

  object IntegrationTest {
    def apply() = new TestDependencies {

      override lazy val scope: String = "it"

      override lazy val test = Seq(
        "uk.gov.hmrc" %% "hmrctest" % hmrcTestVersion % scope,
        "org.scalatest" %% "scalatest" % scalaTestVersion % scope,
        "org.pegdown" % "pegdown" % pegDownVersion % scope,
        "org.mockito" % "mockito-all" % mockitoAll % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlus % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope

      )
    }.test
  }

  def apply() = compile ++ Test() ++ IntegrationTest()
}


