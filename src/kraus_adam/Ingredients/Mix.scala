package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper
import scala.xml.*
import scala.collection.mutable

class Mix(name: String) extends Ingredient(name: String) with XMLReadWrite {
    def loadXML(node: Node): Unit = {
        
    }

    def writeXML(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", name))
        val children = subIngredients.map(i => i.writeXML())
        XMLHelper.makeNode(Mix.TAG, attr, children)
    }

    def getInfo(depth: Int): String = {
        s"${spaces * depth}${name.capitalize}\n" +
          s"${spaces * depth}*****************************\n" +
          subIngredients.map(x => x.getInfo(depth+1)).mkString("\n") +
          s"${spaces * depth}*****************************\n"
    }
}

object Mix {
    val TAG = "mix"

    def apply(name: String): Mix = {
        new Mix(name)
    }
}