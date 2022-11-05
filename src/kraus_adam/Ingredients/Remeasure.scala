package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper
import scala.xml.*

class Remeasure(name: String) extends Ingredient(name: String) with XMLReadWrite {
    def loadXML(node: Node): Unit = {
        
    }

    def writeXML(): Elem = {
        XMLHelper.makeNode(Remeasure.TAG)
    }
}

object Remeasure {
    val TAG = "remeasure"
}