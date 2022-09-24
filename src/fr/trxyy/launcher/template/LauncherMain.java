package fr.trxyy.launcher.template;

import fr.trxyy.alternative.alternative_apiv2.base.AlternativeBase;
import fr.trxyy.alternative.alternative_apiv2.base.GameConnect;
import fr.trxyy.alternative.alternative_apiv2.base.GameEngine;
import fr.trxyy.alternative.alternative_apiv2.base.GameFolder;
import fr.trxyy.alternative.alternative_apiv2.base.GameLinks;
import fr.trxyy.alternative.alternative_apiv2.base.LauncherBackground;
import fr.trxyy.alternative.alternative_apiv2.base.LauncherBase;
import fr.trxyy.alternative.alternative_apiv2.base.LauncherPane;
import fr.trxyy.alternative.alternative_apiv2.base.LauncherPreferences;
import fr.trxyy.alternative.alternative_apiv2.base.WindowStyle;
import fr.trxyy.alternative.alternative_apiv2.utils.Mover;
import fr.trxyy.alternative.alternative_authv2.base.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase {
	public static GameFolder GAME_FOLDER = new GameFolder("minecraft2022-ar");
	private GameLinks GAME_LINKS = new GameLinks("http://localhost/alternative_versions/forge/", "1.19.2-forge-43.1.1.json");
	private LauncherPreferences LAUNCHER_PREFS = new LauncherPreferences("Launcher Template AlternativeAPI v3.0", 880, 520, Mover.MOVE);
	private GameEngine GAME_ENGINE = new GameEngine(GAME_FOLDER, GAME_LINKS, LAUNCHER_PREFS);
	private GameConnect GAME_CONNECT = new GameConnect("mc.hypixel.net", "25565");

	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(this.createContent());
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, this.GAME_ENGINE);
		launcher.setIconImage(primaryStage, "favicon.png");
	}

	private Parent createContent() {
		LauncherPane contentPane = new LauncherPane(this.GAME_ENGINE, 5, WindowStyle.TRANSPARENT);
		/** Direct Connect **/
		this.GAME_ENGINE.reg(GAME_CONNECT);
		new LauncherBackground(this.GAME_ENGINE, "background.mp4", contentPane);
		new LauncherPanel(contentPane, this.GAME_ENGINE);
		return contentPane;
	}
	
	public static void main(String[] args) {
		Logger.log("Fabric, Forge & Optifine works perfectly ! (Launcher)");
		Application.launch(args);
	}
}
