package com.mygdx.emsb.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mygdx.emsb.Controller
/**
 * @author alkiok1
 */ 
object DesktopLauncher1 {
	def main(args: Array[String]) {
		var config: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
		config.foregroundFPS = 120
		config.width  = 1280
		config.height = 720
		config.title  = "Hermo's Revenge: Evil Miguli Strikes Back"
		config.vSyncEnabled = false
		config.forceExit = false
		config.resizable = false
		
		//        cfg.addIcon("data/ic_launcher.png", Files.FileType.Internal);

		new LwjglApplication(new Controller, config)
	}
}
