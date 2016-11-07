package com.busyentry.scala.shared

/**
 * @note
 * 	
 * 	Base class for program main entry
 * 
 * @usecase
 * @example
 * @author hduser
 * @version
 *  
 */

import org.apache.log4j.Logger

abstract class Base() extends App {
  
  private var log: Logger = Logger.getLogger(this.getClass)
  
  def getLogger(): Logger = log
}