package com.nicolas.tetris;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static com.nicolas.tetris.config.TetrisConfig.WINDOW_WIDTH;
import static com.nicolas.tetris.config.TetrisConfig.WINDOW_HEIGHT;
// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("JTetris");
		config.setResizable(false);
		config.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
		new Lwjgl3Application(new Tetris(), config);
	}
}
