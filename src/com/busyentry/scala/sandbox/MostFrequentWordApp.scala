package com.busyentry.scala.sandbox

/**
 * @note
 * 
 * Given a list of files, find the most frequent word. Assume everything fits into memory (no problem with scale). Please use functional style.
 * 
 * @usecase
 * @example
 * @author hduser
 * @version
 *  
 */

import java.io.File
import scala.io.Source
import com.busyentry.scala.shared.Base
import com.busyentry.scala.shared.Utility

/*
 * app main entry
 */
object MostFrequentWordApp extends Base {
  
  // set default path if it's not passed in
  val path = 
    if(args.length > 0) 
      args(0) 
    else "./resources/files"
      
  // getLogger().info("Current dir is: " + new File(".").getAbsolutePath)
      
  // validate the path
  getLogger().info("Validating path: " + path)
  val dir = new File(path)
  if(!dir.exists || !dir.isDirectory) {
    getLogger().info("Invalid path: " + path)
    System.exit(1)
  }
  
  // retrieve a list of files from the directory
  getLogger().info("Retrieving file names from path: " + path)
  val files = Utility.getFiles(dir)
  
  // process the Files
  getLogger().info("*****************************************************")
  val (word: String, count: Int) = MostFrequentWord.count(files)
  getLogger().info("RESULT: The most frequent word is \"%s\" (%d)".format(word, count))
  getLogger().info("*****************************************************")
}

/*
 * Count most frequent word
 * 
 */
object MostFrequentWord {
  
  private var _mostFrequentWord: (String, Int) = ("N/A", 0) // stores most frequent word and the count
  private val _wordMap = collection.mutable.Map[String, Int]().withDefaultValue(0) // stores all the words and their counts
  
  // count words for list of given files and return most frequent word
  def count(files: List[File]): (String, Int) = {
    
    files.foreach(f => countWords(f))
    
    _mostFrequentWord
  }
  
  // count words for a single file
  private def countWords(file: File) {
    
    // read entire text from the file
    val text = Source.fromFile(file).mkString
    
    // extract all words from the text
    // @todo: NEED FUTURE DEVELOPMENT for an enhanced word extracting algorithm
    val words = text.split("\\W+")
    
    // update the word Map
    words.foreach(w => {
      val word = w.toLowerCase()
      
      if(_wordMap.contains(word)) { // if the word in the map, increase the counter 
        _wordMap(word) += 1
      } else { // if the word not in the map, add the word to the map
        _wordMap.put(word, 1)
      }
      
      // if this word is more frequent than what we have so far, choose it
      if(_wordMap(word) > _mostFrequentWord._2) {
          _mostFrequentWord = (word, _wordMap(word))
      }
    })
  }
}
