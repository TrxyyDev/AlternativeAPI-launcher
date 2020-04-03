package fr.trxyy.launcher.template;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.GameVersion;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api_ui.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherBase;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase {
	private GameFolder gameFolder = new GameFolder("customlauncher");
	private LauncherPreferences launcherPreferences = new LauncherPreferences("Launcher Custom", 950, 600, true);
	private GameEngine gameEngine = new GameEngine(gameFolder, launcherPreferences, GameVersion.V_1_12_ALL, GameStyle.VANILLA);
	private GameLinks gameLinks = new GameLinks("http://alternative-api.fr/datas/", "1.12.2.json");

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		this.gameEngine.reg(gameLinks);
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, gameEngine);
		launcher.setIconImage(primaryStage, getResourceLocation().loadImage(gameEngine, "favicon.png"));
	}

	private Parent createContent() {
		LauncherPane contentPane = new LauncherPane(gameEngine);
		Rectangle rect = new Rectangle(gameEngine.getLauncherPreferences().getWidth(), gameEngine.getLauncherPreferences().getHeight());
		rect.setArcHeight(15.0);
		rect.setArcWidth(15.0);
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new LauncherBackground(gameEngine, getResourceLocation().getMedia(gameEngine, "background.mp4"), contentPane);
		new LauncherPanel(contentPane, gameEngine);
		return contentPane;
	}

}
