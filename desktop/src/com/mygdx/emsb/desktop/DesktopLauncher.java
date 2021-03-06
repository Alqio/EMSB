package com.mygdx.emsb.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.emsb.Controller;
import com.badlogic.gdx.Files;


public class DesktopLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = 120;
		config.backgroundFPS = 120;
		config.width  = 1280;
		config.height = 720;
		config.title  = "Hermo's Revenge - Evil Miguli Strikes Back";
		config.vSyncEnabled = false;
		config.forceExit = false;
		config.resizable = false;
		config.addIcon("images/snowTowerIcon.png", Files.FileType.Internal);
		new LwjglApplication(new Controller(), config);		
	}
}