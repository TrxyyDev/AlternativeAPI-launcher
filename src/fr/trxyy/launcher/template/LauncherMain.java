package fr.trxyy.launcher.template;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api.utils.Forge;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.WindowStyle;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.base.LauncherBase;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase {
	private GameFolder GAME_FOLDER = new GameFolder("customlauncher");
	private GameLinks GAME_LINKS = new GameLinks("http://localhost/alternative/1_18_1/", "1.18.1.json");
//	private GameForge GAME_FORGE = new GameForge(Forge.FORGE_CLIENT, "1.18",  "38.0.17", "20211130.085255");
	private LauncherPreferences LAUNCHER_PREFERENCES = new LauncherPreferences("Launcher Template AlternativeAPI v2", 880, 520, Mover.MOVE);
	private GameEngine GAME_ENGINE = new GameEngine(this.GAME_FOLDER, this.GAME_LINKS, this.LAUNCHER_PREFERENCES, GameStyle.VANILLA/*, this.GAME_FORGE*/);

	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(this.createContent());
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, this.GAME_ENGINE);
		launcher.setIconImage(primaryStage, "favicon.png");
	}

	private Parent createContent() {
		LauncherPane contentPane = new LauncherPane(this.GAME_ENGINE, 5, WindowStyle.TRANSPARENT);
		new LauncherBackground(this.GAME_ENGINE, "background.mp4", contentPane);
		new LauncherPanel(contentPane, this.GAME_ENGINE);
		return contentPane;
	}
	
	public static void main(String[] args) {
		Logger.log("Hello World ! :)");
		Application.launch(args);
	}
}
