package kraus_adam.Ingredients

import java.text.DecimalFormat
import scala.collection.mutable.ListBuffer

abstract class Ingredient(name: String) {
    protected val subIngredients: ListBuffer[Ingredient] = ListBuffer[Ingredient]()
    protected val spaces = "  "
    protected val format = new DecimalFormat("0.#")
    
    def addIngredient(ing: Ingredient): Unit = {
        subIngredients += ing
    }

    def getInfo(depth: Int): String
}