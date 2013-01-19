import de.tototec.sbuild._
import de.tototec.sbuild.TargetRefs._
import de.tototec.sbuild.ant._
import de.tototec.sbuild.ant.tasks._
import de.tototec.sbuild.natures.experimental._

@version("0.3.1.9000")
@classpath(
  "mvn:org.apache.ant:ant:1.8.4"
)
@include(
  "../Natures-SNAPSHOT.scala"
)
class SBuild(implicit _project: Project) {

  val scalaVersion = "2.9.2"

  val main = new CleanNature with CompileScalaNature with JarNature {

    override def artifact_name = "com.typesafe.tools.mima.core"
    override def artifact_version = "MASTER"

    override def jar_dependsOn = compileScala_targetName

    override def compileScala_scalaVersion = scalaVersion
    override def compileScala_classpath = 
      s"mvn:org.scala-lang:scala-library:${scalaVersion}" ~
      s"mvn:org.scala-lang:scala-compiler:${scalaVersion}" 
//      s"mvn:org.scala-lang:scala-reflect:${scalaVersion}" 

  }
  main.createTargets

  Target("phony:all") dependsOn main.jar_output

}
