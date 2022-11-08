package kraus_adam

import kraus_adam.Ingredients.*
import scala.xml.*
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.StdIn

class Recipe(name: String) extends XMLReadWrite {
    private val ingredients: ListBuffer[Ingredient] = ListBuffer[Ingredient]()

    def loadXML(node: Node): Unit = {
        val children = node.child
        for(child <- children) {
            val tag = child.label
            tag match {
                case Mix.TAG =>
                    val name = child.attribute("name").get.toString
                    val mix = Mix(name)
                    mix.loadXML(child)
                    ingredients += mix
                case Baked.TAG =>
                    val name = child.attribute("name").getOrElse("").toString
                    val expFac = child.attribute("expansion").getOrElse("1").toString.toDouble
                    val baked = Baked(name, expFac)
                    baked.loadXML(child)
                    ingredients += baked
                case Remeasure.TAG =>
                    val quantity = child.attribute("quantity").get.toString.toDouble
                    val remeasure = Remeasure(quantity)
                    remeasure.loadXML(child)
                    ingredients += remeasure
                case Single.TAG =>
                    val name = child.text
                    val cups = child.attribute("cups").getOrElse("1").toString.toDouble
                    val calories = child.attribute("calories").getOrElse("100").toString.toDouble
                    val single = Single(name, calories, cups)
                    ingredients += single
                case _ =>
            }
        }
    }

    def writeXML(): Elem = {
        val attr: mutable.HashMap[String, String] = mutable.HashMap(("name", name))
        val child = ingredients.map(i => i.writeXML())
        XMLHelper.makeNode(Recipe.TAG, attr, child)
    }

    def addIngredient(): Unit = {
        print("What ingredient (mix, baked, remeasure, single):> ")
        var ingType = StdIn.readLine()
        ingType = ingType.toLowerCase

        if (ingType == "mix" || ingType == "m") {
            print("Name:> ")
            val name = StdIn.readLine()
            val mix = Mix(name)
            var more = ""
            while (more != "n") {
                mix.addIngredient()
                print("Add another ingredient (y/n):> ")
                more = StdIn.readLine()
                more = more.toLowerCase
            }
            println("Added mix")
            ingredients += mix
        } else if (ingType == "baked" || ingType == "b") {
            print("Name:> ")
            val name = StdIn.readLine()
            print("Expansion Factor:> ")
            val expFac = StdIn.readLine().toDouble
            val baked = Baked(name, expFac)
            baked.addIngredient()
            println("Added baked")
            ingredients += baked
        } else if (ingType == "remeasure" || ingType == "r") {
            print("New Quantity:> ")
            val quantity = StdIn.readLine().toDouble
            val remeasure = Remeasure(quantity)
            remeasure.addIngredient()
            println("Added remeasure")
            ingredients += remeasure
        } else if (ingType == "single" || ingType == "s") {
            print("Name:> ")
            val name = StdIn.readLine()
            print("Calories:> ")
            val calories = StdIn.readLine().toDouble
            print("Cups:> ")
            val volume = StdIn.readLine().toDouble
            println("Added single")
            ingredients += Single(name, calories, volume)
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
        ingredients(0).getCal()
    }
    
    def calcVol(): Double = {
        ingredients(0).getVol()
    }

    def calcDensity(): Double = {
        calcCal() / calcVol()
    }

    override def toString: String = {
        s"""
          |Recipe: ${this.name}
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