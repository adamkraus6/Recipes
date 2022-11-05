package kraus_adam

import kraus_adam.XMLHelper._
import scala.xml.*

trait XMLReadWrite {
    def loadXML(node: Node): Unit
    def writeXML(): Elem
}