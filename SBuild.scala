import de.tototec.sbuild._
import de.tototec.sbuild.TargetRefs._
import de.tototec.sbuild.ant._
import de.tototec.sbuild.ant.tasks._

@version("0.3.2")
@classpath("mvn:org.apache.ant:ant:1.8.4")
class SBuild(implicit _project: Project) {

  val modules = Seq("core", "reporter")

  modules.foreach { m => Module(m) }

  Target("phony:clean") dependsOn modules.map { m => TargetRef(m + "::clean") }
  Target("phony:all") dependsOn modules.map { m => TargetRef(m + "::all") }

}
