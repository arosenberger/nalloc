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
    ).settings(Defaults.itSettings: _*)
    .settings(compile <<= compile in Compile dependsOn(compile in Test, compile in IntegrationTest))
    .settings(parallelExecution in IntegrationTest := false)
    .settings(mappings in(Compile, packageBin) ++= mappings.in(macros, Compile, packageBin).value)
    .settings(mappings in(Compile, packageSrc) ++= mappings.in(macros, Compile, packageSrc).value)
    .dependsOn(macros)

  lazy val sandbox =
    project(
      id = "sandbox",
      base = file("sandbox")
    ).dependsOn(optional)
    .settings(publish := {})
    .settings(publishLocal := {})
    .settings(fork in run := true)
    .settings(outputStrategy := Some(StdoutOutput))
    .settings(javaOptions in run ++= Seq(
      "-ms4g",
      "-mx4g",
      "-XX:+AlwaysPreTouch",
      "-XX:+TieredCompilation"
    ))

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
        Seq(libraryDependencies ++= Shared.testDeps)
    ).configs(IntegrationTest)
    .settings(
      publishTo <<= version { version: String =>
        val nexus = "https://oss.sonatype.org/"
        if (version.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
      })
    .settings(publishArtifact in Test := false)
    .settings(credentials += Credentials(Path.userHome / ".ivy2" / ".nalloc_credentials"))
    .settings(pomIncludeRepository := { _ => false})
    .settings(pomExtra :=
              <url>http://nalloc.org</url>
                <scm>
                  <url>git@github.com:arosenberger/nalloc.git</url>
                  <connection>scm:git:git@github.com:arosenberger/nalloc.git</connection>
                </scm>
                <licenses>
                  <license>
                    <name>The Apache Software License, Version 2.0</name>
                    <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
                    <distribution>repo</distribution>
                    <comments>A business-friendly OSS license</comments>
                  </license>
                </licenses>
                <developers>
                  <developer>
                    <id>arosenberger</id>
                    <name>Adam Rosenberger</name>
                    <url>http://github.com/arosenberger/</url>
                  </developer>
                </developers>
    )
}

object Shared {

  val testDeps = Seq(
    "org.scalatest" %% "scalatest" % "2.1.0" % "it,test",
    "org.scalacheck" %% "scalacheck" % "1.11.3" % "it,test"
  )

  val settings = Seq(
    organization := "org.nalloc",
    scalaVersion := "2.11.0-RC1",
    scalacOptions := Seq(
      "-deprecation",
      "-feature",
      "-optimise",
      "-language:experimental.macros",
      "-Yinline-warnings",
      "-unchecked",
      "-feature"
      //      ,"-Ymacro-debug-lite"
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
