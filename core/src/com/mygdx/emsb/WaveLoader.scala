package com.mygdx.emsb

import collection.mutable.Buffer
import java.io._
import collection.mutable.Map
import util.Try

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Files
import com.badlogic.gdx.files.FileHandle

/**
 * WaveLoader loads from a file if possible
 */
class WaveLoader(val input: String){

	//var input = "waves.txt"
	val waves = Buffer[Wave]()
	waves += new Wave(0, Array("-"), 10)
	try {
		
		val handle: FileHandle = Gdx.files.internal("waves/waves.txt")
		val f = handle.readString().split("\n")
		var fileLines = f.toList
										 .filter(x => x != "" && !x.startsWith("#"))
										 .map(x => x.toLowerCase())
										 .dropWhile(x => !x.contains("wave"))
										 
		//println(fileLines.mkString("\n"))
		
		while (fileLines.size >= 3) {
			waves += createWave(fileLines.take(3))
			fileLines = fileLines.drop(3)
			while(fileLines.size >= 1 && !fileLines(0).contains("wave")) {
				fileLines = fileLines.drop(1)
			}
		}
		
	} catch {
		case ex: FileNotFoundException => println("Error: File " + input + " not found")
		case e: Exception							 => println("Error: " + e)
		case _ : Throwable 						 => println("An error occurred")
	}
	
	/**
	 * Load waves
	 */
	def getWaves() = this.waves.sortBy(_.number)
	
	
	/**
	 * Create a wave from a three line string sequence
	 */
	
	def createWave(threeLines: List[String]): Wave = {
		val wave = Map[String, Any]()
		
		val nroLine = new Array[(String, String)](3)
		
		for (i <- 0 until 3)
			nroLine(i) = (threeLines(i).before, threeLines(i).after)
		
		var nro = 0
		
		/**
		 * If the format is not correct, return a normal wave with index 0
		 */
		try {
			if (nroLine(0)._1.trim().toLowerCase() != "wave" || nroLine(1)._1.trim().toLowerCase() != "enemies" || nroLine(2)._1.trim().toLowerCase() != "speed")  {
				throw new Exception("The format for loading waves is: \nwave: wave number\nenemies: all enemies that appear separated by comma\nspeed: wave spawn speed")
				return new Wave(0, Array("vihuy"), 60)
			}
		} catch {
			case e: Exception => {
				println(e)
				return new Wave(0, Array("vihuy"), 60)
			}
		}
		
		
		if (Try(nroLine(0)._2.toInt).isSuccess) {
			nro = nroLine(0)._2.toInt
		} else {
			nro = waves.size
		}
		
		val enemies = Buffer[String]()
		var line = nroLine(1)._2.trim()
		
		while (line.size > 0) {
			var enemy = line.takeWhile(_ != ',').trim()
			line = line.dropWhile(_ != ',').drop(1)
			enemies += enemy
		}
		var speed = 60
		if (Try(nroLine(2)._2.toInt).isSuccess) {
			speed = nroLine(2)._2.toInt
		}	
		
		new Wave(nro, enemies.toArray, speed)
		
	}
	
	/**
	 * This is used for splitting strings
	 */
	implicit class StringImp(s: String) {
		/*
		 * Compare string when they are lowercased and trimmed
		 */
		def ===(ss: String) = s.trim().toLowerCase() == ss.trim().toLowerCase()

		def cmpLine(ss: String): Boolean = s.takeWhile(_ != ':') === ss
		
		def after: String = s.dropWhile(_ != ':').drop(1).takeWhile(_ != '#').trim()
		
		def before: String = s.takeWhile(_ != ':').trim().toLowerCase()
	}	
}