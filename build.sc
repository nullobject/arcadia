import mill._, scalalib._, publish._
import mill.bsp._
import mill.define.Sources
import mill.modules.Util
import mill.scalalib.TestModule.ScalaTest
import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.1.4`
import de.tobiasroeser.mill.vcs.version.VcsVersion

object axon extends ScalaModule with PublishModule { m =>
  def scalaVersion = "2.13.8"

  def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
    "-P:chiselplugin:genBundleElements"
  )

  def ivyDeps = Agg(
    ivy"edu.berkeley.cs::chisel3:3.5.3",
  )

  def scalacPluginIvyDeps = Agg(
    ivy"edu.berkeley.cs:::chisel3-plugin:3.5.3",
  )

  object test extends Tests with ScalaTest {
    override def ivyDeps = m.ivyDeps() ++ Agg(
      ivy"edu.berkeley.cs::chiseltest:0.5.1"
    )
  }

  def publishVersion: T[String] = VcsVersion
    .vcsState()
    .format(tagModifier = {
      case t if t.startsWith("v") => t.substring(1)
      case t => t
    })

  def pomSettings = PomSettings(
    description = "axon",
    organization = "info.joshbassett",
    url = "https://github.com/nullobject/axon",
    licenses = Seq(License.`GPL-3.0-only`),
    versionControl = VersionControl.github("nullobject", "axon"),
    developers = Seq(
      Developer("nullobject", "Joshua Bassett", "https://github.com/nullobject")
    )
  )

  def sonatypeUri: String = "https://s01.oss.sonatype.org/service/local"
}
