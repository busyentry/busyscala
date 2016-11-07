package com.busyentry.scala.sandbox

/**
 * @note
 * 
 * Spark Cluster - Hadoop HDFS programming... IN PROGRESS...
 * 
 * @usecase
 * @example
 * @author hduser
 * @version
 *  
 */

import java.util.UUID
import java.net.URI
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IOUtils

import com.busyentry.scala.shared.Base

object SparkApp extends Base {
  
   // or set VM argument: -Dhadoop.home.dir=/usr/local/hadoop
   System.setProperty("hadoop.home.dir", "/usr/local/hadoop")
  
   var name = "My Spark App " 
    
    // Test.run()
    
//      if(args.length != 2) { 
//         getLogger().error("paramemter  error!")
//         return
//      }
    
    var inputPath = args(0)
    var outputPath = "" //args(1)
    
    if(inputPath == null || inputPath == "") {
      inputPath = "/home/hduser/war_and_peace.txt" //"hdfs://127.0.0.1:9000/test/war_and_peace.txt"
    }
    
    inputPath = "/home/hduser/war_and_peace.txt"
    
    getLogger().info(inputPath)
    getLogger().info(outputPath)
    
    // read and process file
    var conf = new SparkConf()
    // conf.setMaster("spark://RaysUnix:7077") // why not working?
    conf.setMaster("local[*]") //works!
    conf.setAppName("MySpark")
    
    var sc = new SparkContext(conf)
    var textFile = sc.textFile("hdfs://127.0.0.1:9000/test/war_and_peace.txt")
    var c = textFile.count()
    
    //write to hdfs
    val cfg = new Configuration()
    val hdfsOutputPath = "hdfs://127.0.0.1:9000/test/" + UUID.randomUUID().toString + ".txt"
    val fs = FileSystem.get(URI.create(hdfsOutputPath), cfg)
    val os = fs.create(new Path(hdfsOutputPath))
    val is = new ByteArrayInputStream(("Result: " + c.toString()).getBytes(StandardCharsets.UTF_8))
    IOUtils.copyBytes(is, os, cfg)
    
    getLogger().info("*****************************************************")
    getLogger().info("Result: " + c.toString())
    getLogger().info("*****************************************************")
      
}



