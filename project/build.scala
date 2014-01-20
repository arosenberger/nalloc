import sbt.Keys._
import sbt._
import sbtrelease.ReleasePlugin._

object Build extends sbt.Build {

  lazy val root =
    project(
      id = "nalloc",
      base = file(".")
    ).aggregate(optional, macros, sandbox)

  lazy val optional =
    project(
      id = "optional",
      base = file("optional")
    ).dependsOn(macros)
    .settings(Defaults.itSettings: _*)
    .settings(compile <<= compile in Compile dependsOn(compile in Test, compile in IntegrationTest))
    .settings(parallelExecution in IntegrationTest := false)

  lazy val sandbox =
    project(
      id = "sandbox",
      base = file("sandbox")
    ).dependsOn(optional)

  lazy val macros =
    project(
      id = "macros",
      base = file("macros")
    ).settings(libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _))

  def project(id: String, base: File) =
    Project(
      id = id,
      base = base,
      settings =
        Project.defaultSettings ++
        Shared.settings ++
        releaseSettings ++
        Seq(
          libraryDependencies ++= Shared.testDeps
        )
    ).configs(IntegrationTest)
}

object Shared {

  val testDeps = Seq(
    "org.scalatest" %% "scalatest" % "2.0.1-SNAP4" % "it,test",
    "org.scalacheck" %% "scalacheck" % "1.11.1" % "it,test"
  )

  val settings = Seq(
    organization := "com.bitb.kcits",
    scalaVersion := "2.11.0-M7",
    scalacOptions := Seq(
      "-deprecation",
      "-feature",
      "-optimise",
      "-language:experimental.macros",
      "-Yinline-warnings"
      ,"-Ymacro-debug-lite"
    ),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    shellPrompt := ShellPrompt.buildShellPrompt
  )
}

object ShellPrompt {
  object devnull extends ProcessLogger {
    def info(s: => String) {}
    def error(s: => String) {}
    def buffer[T](f: => T): T = f
  }

  def currBranch = (
                   ("git status -sb" lines_! devnull headOption)
                   getOrElse "-" stripPrefix "## "
                   )

  val buildShellPrompt = {
    (state: State) => {
      val currProject = Project.extract(state).currentProject.id
      "[%s](%s)$ ".format(
        currProject, currBranch /*, BuildSettings.buildVersion*/
      )
    }
  }
}
