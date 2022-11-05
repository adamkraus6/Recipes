package kraus_adam

import kraus_adam.Ingredients.*
import scala.xml.*
import scala.collection.mutable.ListBuffer

class Recipe(name: String) extends XMLReadWrite {
    private val ingredients: ListBuffer[Ingredient] = ListBuffer[Ingredient]()

    def loadXML(node: Node): Unit = {
        
    }

    def writeXML(): Elem = {
        XMLHelper.makeNode(Recipe.TAG)
    }

    def addIngredient(ing: Ingredient): Unit = {
        ingredients += ing
    }
    
    def getName: String = {
        this.name
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