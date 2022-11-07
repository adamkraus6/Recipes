package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite

import java.text.DecimalFormat
import scala.collection.mutable.ListBuffer
import scala.io.StdIn

abstract class Ingredient(val name: String) extends XMLReadWrite {
    protected val subIngredients: ListBuffer[Ingredient] = ListBuffer[Ingredient]()
    protected val spaces = "  "
    protected val format = new DecimalFormat("0.#")
    
    def addIngredient(): Unit = {
        print("What ingredient (mix, baked, remeasure, single):> ")
        var ingType = StdIn.readLine()
        ingType = ingType.toLowerCase

        if(ingType == "mix" || ingType == "m") {
            print("Name:> ")
            val name = StdIn.readLine().toLowerCase
            val mix = Mix(name)
            var more = ""
            while(more != "n") {
                mix.addIngredient()
                print("Add another ingredient (y/n):> ")
                more = StdIn.readLine()
                more = more.toLowerCase
            } 
            println("Added mix")
            subIngredients += mix
        } else if(ingType == "baked" || ingType == "b") {
            print("Name:> ")
            val name = StdIn.readLine().toLowerCase
            print("Expansion Factor:> ")
            val expFac = StdIn.readLine().toDouble
            val baked = Baked(name, expFac)
            baked.addIngredient()
            println("Added baked")
            subIngredients += baked
        } else if(ingType == "remeasure" || ingType == "r") {
            print("New Quantity:> ")
            val quantity = StdIn.readLine().toDouble
            val remeasure = Remeasure(quantity)
            remeasure.addIngredient()
            println("Added remeasure")
            subIngredients += remeasure
        } else if(ingType == "single" || ingType == "s") {
            print("Name:> ")
            val name = StdIn.readLine().toLowerCase
            print("Calories:> ")
            val calories = StdIn.readLine().toDouble
            print("Cups:> ")
            var cups = StdIn.readLine().toDouble
            println("Added single")
            subIngredients += Single(name, calories, cups)
        } else {
            println("Ingredient format not found")
            null
        }
    }

    def getInfo(depth: Int): String
}