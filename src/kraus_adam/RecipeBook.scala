package kraus_adam

import scala.xml.*
import scala.collection.mutable.ListBuffer

class RecipeBook() {
    private var recipes: ListBuffer[Recipe] = ListBuffer[Recipe]()

    def loadXML(node: Node): Unit = {
        val children = node.child
        for(child <- children) {
            val tag = child.label
            tag match {
                case Recipe.TAG =>
                    val recipe = Recipe(child.attribute("name").get.toString)
                    recipe.loadXML(child)
                    recipes += recipe
                case _ =>
            }
        }
    }

    def addRecipe(recipe: Recipe) = {
        recipes += recipe
    }

    def find(name: String): Boolean = {
        for(recipe <- recipes) {
            if(recipe.getName == name) {
                return true
            }
        }

        false
    }

    override def toString: String = {
        var str = ""
        for(recipe <- recipes) {
            str += recipe.toString
        }
        str
    }
}

object RecipeBook {
    val TAG = "recipebook"

    def apply(): RecipeBook = {
        new RecipeBook()
    }
}