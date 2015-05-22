name := "ScalaZDemo"

scalaVersion in ThisBuild := "2.11.5"

scalacOptions in ThisBuild ++= Seq(
  "-feature",
  "-deprecation",
  "-Yno-adapted-args",
  "-Ywarn-value-discard",
  "-Ywarn-numeric-widen",
  "-Ywarn-dead-code",
  "-Xlint",
  "-Xfatal-warnings",
  "-unchecked"
)

libraryDependencies in ThisBuild ++= Seq(
  "io.argonaut"   %% "argonaut"     % "6.1-M5",
  "org.scalaz"    %% "scalaz-core"  % "7.1.1",
  "org.joda"      %  "joda-convert" % "1.3.1",
  "joda-time"     %  "joda-time"    % "2.2",
  "org.scala-stm" %% "scala-stm"    % "0.7"
)

initialCommands in console := """
  import argonaut._, Argonaut._
  import scalaZExamples._
"""

lazy val demo = project.in(file("."))
