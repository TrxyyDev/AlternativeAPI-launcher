package fr.trxyy.launcher;

import fr.trxyy.alternative.alternative_api.GameConnect;
import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.GameVersion;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api.maintenance.GameMaintenance;
import fr.trxyy.alternative.alternative_api.maintenance.Maintenance;
import fr.trxyy.alternative.alternative_api_ui.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherBase;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase {
	/** ===================== DOSSIER D'INSTALLATION ===================== */
	private static GameFolder INSTALL_DIRECTORY = new GameFolder("customlauncher");
	/** ===================== TAILLE DU LAUNCHER ===================== */
	private static LauncherPreferences LAUNCHER_SIZE = new LauncherPreferences("Launcher Custom", 950, 550, true);
	/** ===================== INFOS DU JEU ===================== */
	private static GameEngine GAME_ENGINE = new GameEngine(INSTALL_DIRECTORY, LAUNCHER_SIZE, GameVersion.V_1_12_ALL, GameStyle.VANILLA);
	/** ===================== CONNEXION AUTO SERVEUR ===================== */
	private static GameConnect DIRECT_CONNECT = new GameConnect("funcraft.net", "25565");
	/** ===================== ARGUMENTS SUPPLEMENTAIRES ===================== */
//	private static GameArguments CUSTOM_ARGUMENTS = new GameArguments(new String[] {"--customArg", "something"});
	/** ===================== MAINTENANCE ===================== */
	private static GameMaintenance MAINTENANCE = new GameMaintenance(Maintenance.DONT_USE, GAME_ENGINE);
	/** ===================== URL DU FICHIER JSON ===================== */
	private static GameLinks LINKS = new GameLinks("http://alternative-api.fr/datas/", "1.12.2.json");
	
	public static void main(String[] args) {
		GAME_ENGINE.reg(LINKS);
		GAME_ENGINE.reg(MAINTENANCE);
		GAME_ENGINE.reg(DIRECT_CONNECT);
//		GAME_ENGINE.reg(CUSTOM_ARGUMENTS);
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.UNDECORATED, GAME_ENGINE);
		/** ===================== IMAGE ICONE DU LAUNCHER ===================== */
		launcher.setIconImage(primaryStage, getResourceLocation().loadImage(GAME_ENGINE, "favicon.png"));
	}

	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize(GAME_ENGINE.getLauncherPreferences().getWidth(), GAME_ENGINE.getLauncherPreferences().getHeight());
		/** ===================== LA VIDEO D'ARRIERE PLAN ===================== */
		new LauncherBackground(GAME_ENGINE, getResourceLocation().getMedia(GAME_ENGINE, "background.mp4"), root);
		/** ===================== LA CLASSE PANEL OU ON AFFICHE TOUT LES COMPOSANTS ===================== */
		new LauncherPanel(root, GAME_ENGINE);
		return root;
	}

}
