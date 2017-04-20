package com.mygdx.emsb

import scala.io.Source
import collection.mutable.Buffer
import java.io._
import collection.mutable.Map
//import com.mygdx.emsb.Wave

object WaveLoader extends App {

	val waves = Buffer[Wave]()
	try {

		val f = Source.fromFile("waves.txt")
		var fileLines = f.getLines.toList
		println(fileLines.mkString("\n"))

		val input: Reader = new StringReader(f.mkString)
		val lineReader = new BufferedReader(input)
		var lines = Buffer[String]()

		fileLines = fileLines.dropWhile(x => !x.toLowerCase.contains("wave"))
		println(fileLines.mkString("\n"))
		
		waves += makeWave(fileLines.take(3))
		
	} catch {
		case ex: FileNotFoundException => println("file not found")
		//case _ : Throwable 						 => println("An error occurred")
	}
	
	def makeWave(threeLines: List[String]) = {
		val wave = Map[String, Any]()
		for (i <- 0 until threeLines.size) {
			var (eka, toka) = threeLines(0).trim.splitAt(':')
			wave += eka -> toka
		}
		try {
			val j = new Wave(wave("wave").asInstanceOf[Int], wave("enemies").asInstanceOf[Array[String]], wave("speed").asInstanceOf[Int])
		} catch {
			case ex: Throwable => println(ex)
		}
	//	j
	}
}