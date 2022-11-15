package kraus_adam.Ingredients

import kraus_adam.XMLReadWrite
import scala.collection.mutable.ListBuffer

/*
Parent ingredient class
*/
abstract class Ingredient() extends XMLReadWrite {
    protected var name: String = _
    protected val subIngredients: ListBuffer[Ingredient] = ListBuffer[Ingredient]()
    protected val spaces = "  "
    
    /*
    Prompts for class information and then subingredient(s)
    */
    def addIngredient(): Unit

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
    Gets the ingredient name
    */
    def getName: String = name

    /*
    Gets ingredient information
    param depth: depth to align text
    return: formatted string
    */
    def getInfo(depth: Int): String
}