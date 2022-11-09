package kraus_adam.Ingredients

import java.text.DecimalFormat
import kraus_adam.XMLReadWrite
import scala.collection.mutable.ListBuffer
import scala.io.StdIn

/*
Parent ingredient class
*/
abstract class Ingredient(val name: String) extends XMLReadWrite {
    protected val subIngredients: ListBuffer[Ingredient] = ListBuffer[Ingredient]()
    protected val spaces = "  "
    protected val format = new DecimalFormat("0.#")
    
    /*
    Adds an ingredient to recipe/another ingredient
    */
    def addIngredient(): Unit = {
        print("What ingredient (mix, baked, remeasure, single):> ")
        var ingType = StdIn.readLine()
        ingType = ingType.toLowerCase

        // TODO change to make each ingredient do their own prompting
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
            val cups = StdIn.readLine().toDouble
            println("Added single")
            subIngredients += Single(name, calories, cups)
        } else {
            println("Ingredient format not found")
        }
    }

    /*
    Searches subIngredient(s) for ingredient
    return: true if found
    */
    def findIngredient(name: String): Boolean

    /*
    Gets ingredient calories
    return: calories
    */
    def getCal: Double
    
    /*
    Gets ingredient volume
    return: volume
    */
    def getVol: Double

    /*
    Gets ingredient information
    param depth: depth to align text
    return: formatted string
    */
    def getInfo(depth: Int): String
}