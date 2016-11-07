package com.busyentry.scala.shared

/**
 * @note
 * 
 * Utilities
 * 
 * @usecase
 * @example
 * @author hduser
 * @version
 *  
 */

import org.apache.log4j.Logger
import java.io.File

object Utility {
  
  /*
   * return list of Files for given path
   */
  def getFiles(dir: File): List[File] = {

    if(dir.exists && dir.isDirectory) {
      dir.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }
  
  /*
   * test for even or odd number
   * 
   */
  def isEven(n: Int) = n % 2 == 0
  def isOdd(n: Int) = !isEven(n)
  
}