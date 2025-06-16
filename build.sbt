val ProjectName      = "assignment"
val OrganisationName = "some"
val ProjectVersion   = "0.0.1"
val ScalaVersion     = "2.11.12"

def common: Seq[Setting[_]] = Seq(
  organization := OrganisationName,
  version      := ProjectVersion,
  scalaVersion := ScalaVersion
)

lazy val root = (project in file("."))
  .settings( common: _*)
  .settings(
    name := ProjectName,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest"  % "3.0.9"  % "test",
      "com.lihaoyi"   %  "ammonite"   % "0.7.9"  % "test" cross CrossVersion.full
    )
  )

/** adds the ammonite repl on top of sbt, usage: ```test:console``` */
// initialCommands in (Test, console) := """ammonite.Main().run()"""