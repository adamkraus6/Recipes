package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper
import scala.xml.*
import scala.collection.mutable

class Mix(name: String) extends Ingredient(name: String) with XMLReadWrite {
    def loadXML(node: Node): Unit = {
        val children = node.child
        for (child <- children) {
            val tag = child.label
            tag match {
                case Mix.TAG =>
                    val name = child.attribute("name").get.toString
                    val mix = Mix(name)
                    mix.loadXML(child)
                    subIngredients += mix
                case Baked.TAG =>
                    val name = child.attribute("name").getOrElse("").toString
                    val expFac = child.attribute("expansion").getOrElse("1").toString.toDouble
                    val baked = Baked(name, expFac)
                    baked.loadXML(child)
                    subIngredients += baked
                case Remeasure.TAG =>
                    val quantity = child.attribute("quantity").getOrElse("1").toString.toDouble
                    val remeasure = Remeasure(quantity)
                    remeasure.loadXML(child)
                    subIngredients += remeasure
                case Single.TAG =>
                    val name = child.text
                    val cups = child.attribute("cups").getOrElse("1").toString.toDouble
                    val calories = child.attribute("calories").getOrElse("100").toString.toDouble
                    val single = Single(name, calories, cups)
                    subIngredients += single
                case _ =>
            }
        }
    }

    def writeXML(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", name))
        val children = subIngredients.map(i => i.writeXML())
        XMLHelper.makeNode(Mix.TAG, attr, children)
    }

    def findIngredient(name: String): Boolean = {
        if (this.name == name)
            return true

        for (ing <- subIngredients)
            if (ing.findIngredient(name))
                return true

        false
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