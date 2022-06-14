ThisBuild / scalaVersion := "2.13.8"

val chiselVersion = "3.5.3"

import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("nullobject", "axon", "hello@joshbassett.info"))
publishTo := sonatypePublishToBundle.value

// For all Sonatype accounts created on or after February 2021
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"

lazy val root = (project in file("."))
  .settings(
    name := "axon",
    organization := "info.joshbassett",
    licenses := Seq("GPL3" -> url("https://www.gnu.org/licenses/gpl-3.0.en.html")),
    description := "MiSTer FPGA development framework for Chisel",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3" % chiselVersion,
      "edu.berkeley.cs" %% "chiseltest" % "0.5.1" % "test"
    ),
    scalacOptions ++= Seq(
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
      "-P:chiselplugin:genBundleElements",
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % chiselVersion cross CrossVersion.full),
  )
