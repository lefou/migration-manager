import de.tototec.sbuild._
import de.tototec.sbuild.TargetRefs._
import de.tototec.sbuild.ant._
import de.tototec.sbuild.ant.tasks._
import de.tototec.sbuild.natures.experimental._

@version("0.3.2")
@classpath("mvn:org.apache.ant:ant:1.8.4")
@include("../Natures-SNAPSHOT.scala")
class SBuild(implicit _project: Project) {

  val scalaVersion = "2.10.0"

  val compileCp = 
      s"mvn:org.scala-lang:scala-library:${scalaVersion}" ~
      s"mvn:org.scala-lang:scala-compiler:${scalaVersion}" ~
      s"mvn:org.scala-lang:scala-reflect:${scalaVersion}" ~
      s"../core/target/com.typesafe.tools.mima.core-MASTER.jar"

  lazy val runCp = compileCp ~ main.jar_output

  lazy val main = new CleanNature with CompileScalaNature with JarNature {

    override def artifact_name = "com.typesafe.tools.mima.reporter"
    override def artifact_version = "MASTER"

    override def jar_dependsOn = compileScala_targetName

    override def compileScala_scalaVersion = scalaVersion
    override def compileScala_classpath = compileCp

  }
  main.createTargets

  Target("phony:all") dependsOn main.jar_output

  Target("phony:run") dependsOn runCp  exec {
    val cp = runCp.files.mkString(":")
    val command = Array("java", "-cp", cp, "com.typesafe.tools.mima.cli.Main")
    AntEcho(message = "Execute:\n" + command.mkString(" "))
  }

}
