package kraus_adam

import scala.xml.*

trait XMLReadWrite {
    def loadXML(node: Node): Unit
    def writeXML(): Elem
}