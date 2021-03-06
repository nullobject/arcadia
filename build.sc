import mill._, scalalib._, publish._
import mill.scalalib.TestModule.ScalaTest
import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.1.4`
import de.tobiasroeser.mill.vcs.version.VcsVersion

object arcadia extends ScalaModule with PublishModule { m =>
  def scalaVersion = "2.13.8"

  override def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
    "-P:chiselplugin:genBundleElements"
  )

  override def ivyDeps = Agg(
    ivy"edu.berkeley.cs::chisel3:3.5.3",
  )

  override def scalacPluginIvyDeps = Agg(
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
    description = "arcadia",
    organization = "info.joshbassett",
    url = "https://github.com/nullobject/arcadia",
    licenses = Seq(License.`GPL-3.0-only`),
    versionControl = VersionControl.github("nullobject", "arcadia"),
    developers = Seq(
      Developer("nullobject", "Joshua Bassett", "https://github.com/nullobject")
    )
  )

  override def sonatypeUri: String = "https://s01.oss.sonatype.org/service/local"
}
