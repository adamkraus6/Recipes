package kraus_adam

import kraus_adam.Ingredients.*
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.StdIn
import scala.xml.*

/*
Recipe class that holds ingredients
*/
class Recipe(name: String) extends XMLReadWrite {
    private val ingredients: ListBuffer[Ingredient] = ListBuffer[Ingredient]()

    /*
    Loads information from an XML node into class
    param node: XML node
    */
    def loadXML(node: Node): Unit = {
        val children = node.child
        for(child <- children) {
            val tag = child.label
            tag match {
                case Mix.TAG =>
                    val mix = Mix()
                    mix.loadXML(child)
                    ingredients += mix
                case Baked.TAG =>
                    val baked = Baked()
                    baked.loadXML(child)
                    ingredients += baked
                case Remeasure.TAG =>
                    val remeasure = Remeasure()
                    remeasure.loadXML(child)
                    ingredients += remeasure
                case Single.TAG =>
                    val single = Single()
                    single.loadXML(child)
                    ingredients += single
                case _ =>
            }
        }
    }

    /*
    Writes class info into XML
    return: XML Element
    */
    def writeXML(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", name))
        val child = ingredients.map(i => i.writeXML())
        XMLHelper.makeNode(Recipe.TAG, attr, child)
    }

    /*
    Prompts for ingredient type and adds sub ingredient(s)
    */
    def addIngredient(): Unit = {
        print("What ingredient (mix, baked, remeasure, single):> ")
        val ingType = StdIn.readLine().toLowerCase

        if (ingType == "mix" || ingType == "m") {
            val mix = Mix()
            mix.addIngredient()
            println("Added mix")
            ingredients += mix
        } else if (ingType == "baked" || ingType == "b") {
            val baked = Baked()
            baked.addIngredient()
            println("Added baked")
            ingredients += baked
        } else if (ingType == "remeasure" || ingType == "r") {
            val remeasure = Remeasure()
            remeasure.addIngredient()
            println("Added remeasure")
            ingredients += remeasure
        } else if (ingType == "single" || ingType == "s") {
            val single = Single()
            single.addIngredient()
            println("Added single")
            ingredients += single
        } else {
            println("Ingredient format not found")
        }
    }

    def findIngredient(name: String): Boolean = {
        if(this.name == name)
            return true

        for(ing <- ingredients)
            if(ing.findIngredient(name))
                return true

        false
    }
    
    def getName: String = {
        this.name
    }

    def calcCal(): Double = {
        ingredients(0).getCal
    }
    
    def calcVol(): Double = {
        ingredients(0).getVol
    }

    def calcDensity(): Double = {
        calcCal() / calcVol()
    }

    override def toString: String = {
        s"""
          |Recipe: ${name.capitalize}
          |==================================
          |${ingredients.map(x => x.getInfo(1)).mkString("\n")}
          """.stripMargin
    }
}

object Recipe {
    val TAG = "recipe"

    def apply(name: String): Recipe = {
        new Recipe(name)
    }
}