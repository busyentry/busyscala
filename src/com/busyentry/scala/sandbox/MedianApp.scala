package com.busyentry.scala.sandbox

/**
 * @note
 * 
 * Given a file with an arbitrary number of lines: each lines consists of a list of integers
  
   For example:
 
   4234  22 123 234  3322 32 2 444
   42 2212 1224 22
   3234 44 1  2 1  233 1234  
   ..
  
    Find the median of the medians. I.e. find the median of each line then the median
    of the resulting line medians
 * 
 * @usecase
 * @example
 * @author hduser
 * @version
 *  
 */

import java.io.File
import scala.io.Source
import scala.collection.mutable.ListBuffer
import com.busyentry.scala.shared.Base
import com.busyentry.scala.shared.Utility

/*
 * app main entry
 */
object MedianApp extends Base {
  
  // set default file path if it's not passed in
  val path = 
    if(args.length > 0) 
      args(0) 
    else "./resources/files/numbers.txt"
      
  // getLogger().info("Current dir is: " + new File(".").getAbsolutePath)
      
  // validate the path
  getLogger().info("Validating path: " + path)
  val file = new File(path)
  if(!file.exists || !file.isFile) {
    getLogger().info("Invalid file path: " + path)
    System.exit(1)
  }
  
  // process the file
  getLogger().info("*****************************************************")
  val median = Median.calculate(file)
  val display = (median match {
    case Some(x:Int) => x.toString
    case _ => "N/A"
  })
  
  getLogger().info("RESULT: The median is %s".format(display))
  getLogger().info("*****************************************************")
}

/*
 * Calculate median of median(s) in a file
 * 
 */
object Median {
  
  private val _medians = new ListBuffer[Int]() // stores medians for each line
  
  // calculate median of medians for all the lines in a file
  def calculate(file: File): Option[Int] = {
    // store all the the lines of the given file
    val lines = Source.fromFile(file).getLines().toList
    
    // calculate median for each non-empty line and add to the list
    lines.filter(_.nonEmpty).foreach(s => { 
      val m = calculateLine(s)
      
      _medians += m
    })
    
    // if the file is empty, we return None
    if(_medians.length <= 0) {
      None
    } else {
      Some(calculateMedian(_medians.toList))
    }
  }
  
  // calculate median for given string
  private def calculateLine(s: String): Int  = {
    // extract all the numbers out
    val numbers = s.split(" +").map(_.toInt)
   
    // calculate median
    calculateMedian(numbers.toList)
  }
  
  // calculate median for given List of Int
  private def calculateMedian(numbers: List[Int]) : Int = {
    // calculate median of medians
      val sorted = numbers.sorted
      val len = sorted.length
       
      if(Utility.isOdd(len)) {
         sorted(len / 2)
      } else {
         (sorted(len / 2) + sorted(len / 2 + 1)) / 2
      }
  }
}
