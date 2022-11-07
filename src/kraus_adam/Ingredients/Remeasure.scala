package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import kraus_adam.XMLHelper
import scala.collection.mutable

import scala.collection.mutable.ListBuffer
import scala.xml.*

class Remeasure(name: String, quantity: Double) extends Ingredient(name: String) with XMLReadWrite {
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
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("quantity", quantity.toString))
        val child = subIngredients.map(i => i.writeXML())
        XMLHelper.makeNode(Remeasure.TAG, attr, child)
    }

    def getInfo(depth: Int): String = {
        s"${spaces * depth}Remeasure to ${quantity} cups\n" +
          subIngredients.map(x => x.getInfo(depth+1)).mkString("\n")
    }
}

object Remeasure {
    val TAG = "remeasure"

    def apply(quantity: Double): Remeasure = {
        new Remeasure("", quantity)
    }
}