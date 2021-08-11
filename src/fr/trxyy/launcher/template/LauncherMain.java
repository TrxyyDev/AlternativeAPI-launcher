package fr.trxyy.launcher.template;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api.utils.Forge;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.base.LauncherBase;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase {
	private GameFolder gameFolder = new GameFolder("customlauncher");
	private LauncherPreferences launcherPreferences = new LauncherPreferences("Launcher Template AlternativeAPI", 950, 600, Mover.MOVE);
	private GameLinks gameLinks = new GameLinks("http://127.0.0.1/alternative/1_16_3/", "1.16.3.json");
	private GameEngine gameEngine = new GameEngine(this.gameFolder, this.gameLinks, this.launcherPreferences, GameStyle.FORGE_1_13_HIGHER);
	private GameForge newForge = new GameForge(Forge.DEFAULT, "1.16.3", "34.1.0", "20200911.084530");

	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		this.gameEngine.reg(primaryStage);
		this.gameEngine.reg(this.newForge);
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, this.gameEngine);
		launcher.setIconImage(primaryStage, "favicon.png");
	}

	private Parent createContent() {
		LauncherPane contentPane = new LauncherPane(this.gameEngine);
		Rectangle rect = new Rectangle(this.gameEngine.getLauncherPreferences().getWidth(), this.gameEngine.getLauncherPreferences().getHeight());
		rect.setArcHeight(15.0);
		rect.setArcWidth(15.0);
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new LauncherBackground(this.gameEngine, "background.mp4", contentPane);
		new LauncherPanel(contentPane, this.gameEngine);
		return contentPane;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}

}