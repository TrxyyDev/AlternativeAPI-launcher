package fr.trxyy.launcher;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.account.AccountType;
import fr.trxyy.alternative.alternative_api.auth.GameAuth;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.UsernameSaver;
import fr.trxyy.alternative.alternative_api_ui.IScreen;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.LauncherConsole;
import fr.trxyy.alternative.alternative_api_ui.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.LauncherPasswordField;
import fr.trxyy.alternative.alternative_api_ui.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.LauncherTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LauncherPanel extends IScreen {
	
	private LauncherTextField usernameField;
	private LauncherPasswordField passwordField;
	
	private LauncherButton settingsButton;
	private LauncherButton loginButton;
	private LauncherButton closeButton;
	private LauncherButton minimizeButton;
	
	private LauncherRectangle topRectangle;
	
	private LauncherLabel consoleLabel;
	private LauncherLabel titleLabel;
	private LauncherLabel authLabel;
	
	private Thread updateThread;
	
	private GameUpdater updater = new GameUpdater();
	
	private LauncherConsole consoleArea;
	private String before = "";
	
	private UsernameSaver saver;

	public LauncherPanel(Pane root, GameEngine engine) {
		this.saver = new UsernameSaver(engine);
		this.saver.readUsername();
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, engine.getWidth(), 24);
		this.topRectangle.setOpacity(0.7);
		/** ===================== LABEL TITRE ===================== */
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("Launcher Test");
		this.titleLabel.setStyle("-fx-text-fill: white;");
		this.titleLabel.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 16F));
		this.titleLabel.setPosition(3, -4);
		this.titleLabel.setSize(230, 35);
		/** ===================== AFFICHER UN LOGO ===================== */
		this.drawLogo(engine, getResourceLocation().loadImage(engine, "logo.png"), engine.getWidth() - 170, 250, 120, 80, root);
		/** ===================== LABEL DE LA CONSOLE ===================== */
		this.consoleLabel = new LauncherLabel(root);
		this.consoleLabel.setText(" CONSOLE DE MISE A JOUR");
		this.consoleLabel.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.consoleLabel.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 18F));
		this.consoleLabel.setPosition(20, 40);
		this.consoleLabel.setSize(230, 35);
		/** ===================== ZONE DE LA CONSOLE (TEXTAREA) ===================== */
		this.consoleArea = new LauncherConsole(root);
		this.consoleArea.setText("Bienvenue sur la console d'update. \nLes infos lors du telechargement s'afficheront ici.");
		this.consoleArea.setStyle("fx-background-color: rgba(53,89,119,0.0); -fx-text-fill: black;");
		this.consoleArea.textProperty().addListener(new ChangeListener<Object>() {
		    @Override
		    public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
		    	consoleArea.setScrollTop(Double.MAX_VALUE);
		    }
		});
		this.consoleArea.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 14F));
		this.consoleArea.setEditable(false);
		this.consoleArea.setPosition(20, 70);
		this.consoleArea.setSize(700, 450);
		this.consoleArea.setWrapText(true);
		/** ===================== LABEL D'AUTHENTIFICATION ===================== */
		this.authLabel = new LauncherLabel(root);
		this.authLabel.setText("AUTHENTIFICATION");
		this.authLabel.setStyle("-fx-text-fill: white;");
		this.authLabel.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 16F));
		this.authLabel.setPosition(engine.getWidth() - 180, engine.getHeight() - 194);
		this.authLabel.setSize(230, 35);
		/** ===================== CASE DU PSEUDONYME ===================== */
		this.usernameField = new LauncherTextField(root);
		this.usernameField.setText(saver.getUsername());
		this.usernameField.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.usernameField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 18F));
		this.usernameField.setPosition(engine.getWidth() - 210, engine.getHeight() - 165);
		this.usernameField.setSize(200, 35);
		/** ===================== CASE DU MOT DE PASSE ===================== */
		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.passwordField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 18F));
		this.passwordField.setPosition(engine.getWidth() - 210, engine.getHeight() - 120);
		this.passwordField.setSize(200, 35);
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton = new LauncherButton(root);
		this.loginButton.setText("Se connecter");
		this.loginButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.loginButton.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 18F));
		this.loginButton.setPosition(engine.getWidth() - 140, engine.getHeight() - 70);
		this.loginButton.setSize(130, 35);
		this.loginButton.setAction(ce -> {
			saver.writeUsername(this.usernameField.getText());
			/** ===================== AUTHENTIFICATION OFFLINE (CRACK) ===================== */
			if (this.usernameField.getText().length() < 3) {
				new LauncherAlert("Authentification echouee", "Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
			}
			else if (this.usernameField.getText().length() > 3 && this.passwordField.getText().isEmpty()) {
				GameAuth auth = new GameAuth(this.usernameField.getText(), this.passwordField.getText(), AccountType.OFFLINE);
				if (auth.isLogged()) {
					this.usernameField.setDisable(true);
					this.passwordField.setDisable(true);
					this.loginButton.setDisable(true);
					this.settingsButton.setDisable(true);
					
					updater.reg(engine);
					updater.reg(auth.getSession());
					engine.reg(updater);
				    this.updateThread = new Thread() {
				        public void run() {
				        	engine.getGameUpdater().run();
				        }
				      };
				    this.updateThread.start();
					
				    this.timeline = new Timeline(new KeyFrame[] { new KeyFrame(javafx.util.Duration.seconds(0.0D), e -> showFileInConsole(engine), new javafx.animation.KeyValue[0]), new KeyFrame(javafx.util.Duration.seconds(0.1D), new javafx.animation.KeyValue[0]) });
				    this.timeline.setCycleCount(Animation.INDEFINITE);
				    this.timeline.play();
				}
			}
			/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
			else if (this.usernameField.getText().length() > 3 && !this.passwordField.getText().isEmpty()) {
				GameAuth auth = new GameAuth(this.usernameField.getText(), this.passwordField.getText(), AccountType.MOJANG);
				if (auth.isLogged()) {
					this.usernameField.setDisable(true);
					this.passwordField.setDisable(true);
					this.loginButton.setDisable(true);
					this.settingsButton.setDisable(true);
					
					updater.reg(engine);
					updater.reg(auth.getSession());
					engine.reg(updater);
				    this.updateThread = new Thread() {
				        public void run() {
				        	engine.getGameUpdater().run();
				        }
				      };
				    this.updateThread.start();
					
				    this.timeline = new Timeline(new KeyFrame[] { new KeyFrame(javafx.util.Duration.seconds(0.0D), e -> showFileInConsole(engine), new javafx.animation.KeyValue[0]), new KeyFrame(javafx.util.Duration.seconds(0.1D), new javafx.animation.KeyValue[0]) });
				    this.timeline.setCycleCount(Animation.INDEFINITE);
				    this.timeline.play();
				}
				else {
					new LauncherAlert("Authentification echouee!", "Impossible de se connecter, l'authentification semble etre une authentification 'en-ligne'"
							+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres. (compte non migrer)"
							+ "\n-Faites bien attention aux majuscules et minuscules. \nAssurez-vous d'utiliser un compte Mojang.");
				}
			}
			else {
				new LauncherAlert("Authentification echouee!", "Impossible de se connecter, l'authentification semble etre une authentification 'hors-ligne'"
						+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres.");
			}
		});
		/** ===================== BOUTON PARAMETRES ===================== */
		this.settingsButton = new LauncherButton(root);
		ImageView imageButton = new ImageView(getResourceLocation().loadImage(engine, "settings.png"));
        imageButton.setFitWidth(27);
        imageButton.setFitHeight(27);
        this.settingsButton.setGraphic(imageButton);
		this.settingsButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.settingsButton.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 18F));
		this.settingsButton.setPosition(engine.getWidth() - 210, engine.getHeight() - 70);
		this.settingsButton.setSize(60, 35);
		this.settingsButton.setAction(ce -> {
			new LauncherSettings(engine);
		});
		/** ===================== BOUTON FERMER ===================== */
		this.closeButton = new LauncherButton(root);
		this.closeButton.setSize(38, 22);
		this.closeButton.setPosition(engine.getWidth() - 38, 0);
		this.closeButton.setInvisible();
		this.closeButton.setStyle("-fx-padding: 0;");
		ImageView closeIcon = new ImageView(getResourceLocation().loadImage(engine, "close.png"));
		closeIcon.setFitWidth(this.closeButton.getPrefWidth());
		closeIcon.setFitHeight(this.closeButton.getPrefHeight());
		this.closeButton.setGraphic(closeIcon);
		this.closeButton.setAction(e -> {
			System.exit(0);
		});
		/** ===================== BOUTON REDUIRE ===================== */
		this.minimizeButton = new LauncherButton(root);
		this.minimizeButton.setSize(38, 22);
		this.minimizeButton.setPosition(engine.getWidth() - 76, 0);
		this.minimizeButton.setInvisible();
		this.minimizeButton.setStyle("-fx-padding: 0;");
		ImageView minimizeIcon = new ImageView(getResourceLocation().loadImage(engine, "minimize.png"));
		minimizeIcon.setFitWidth(this.minimizeButton.getPrefWidth());
		minimizeIcon.setFitHeight(this.minimizeButton.getPrefHeight());
		this.minimizeButton.setGraphic(minimizeIcon);
		this.minimizeButton.setAction(e -> {
			Stage stage = (Stage)((LauncherButton)e.getSource()).getScene().getWindow();
			stage.setIconified(true);
		});
	}
	/** ===================== AFFICHER DU TEXTE DANS LE TEXTAREA ===================== */
	private void showFileInConsole(GameEngine engine) {
		if (engine.getGameUpdater().getDownloadingFileName() != "") {
			if (!this.before.equals(engine.getGameUpdater().getDownloadingFileName())) {
				this.before = engine.getGameUpdater().getDownloadingFileName();
				String s = "\n" + engine.getGameUpdater().getDownloadingFileName();
				this.consoleArea.appendText(s);
			}
		}
	}

}
