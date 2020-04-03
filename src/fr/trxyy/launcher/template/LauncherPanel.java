package fr.trxyy.launcher.template;

import java.text.DecimalFormat;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.account.AccountType;
import fr.trxyy.alternative.alternative_api.auth.GameAuth;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.UserConfig;
import fr.trxyy.alternative.alternative_api.utils.UsernameSaver;
import fr.trxyy.alternative.alternative_api_ui.IScreen;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.LauncherPasswordField;
import fr.trxyy.alternative.alternative_api_ui.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.LauncherTextField;
import fr.trxyy.alternative.alternative_api_ui.Mover;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherPanel extends IScreen {
	
	private LauncherRectangle topRectangle;
	private LauncherRectangle backgroundWhiteRectangle;
	private LauncherImage titleImage;
	private LauncherLabel titleLabel;
	private LauncherButton closeButton;
	private LauncherButton reduceButton;
	
	private LauncherButton facebookButton;
	private LauncherButton twitterButton;
	private LauncherButton instagramButton;
	private LauncherButton youtubeButton;
	
	private LauncherRectangle updateRectangle;
	private LauncherLabel updateLabel;
	private LauncherLabel currentFileLabel;
	private LauncherLabel percentageLabel;
	private LauncherLabel currentStep;
	
	private LauncherTextField usernameField;
	private LauncherPasswordField passwordField;
	private LauncherButton loginButton;
	private LauncherButton settingsButton;
	
	public Timeline timeline;
	private DecimalFormat decimalFormat = new DecimalFormat(".#");
	private Thread updateThread;
	private GameUpdater updater = new GameUpdater();
	private UsernameSaver saver;
	public UserConfig userConfig;
	
	private String FACEBOOK_URL = "http://facebook.com/";
	private String INSTAGRAM_URL = "http://instagram.com/";
	private String TWITTER_URL = "http://twitter.com/";
	private String YOUTUBE_URL = "http://youtube.com/c/Trxyy";

	public LauncherPanel(Pane root, GameEngine engine) {
		/** ===================== CONFIGURATION UTILISATEUR ===================== */
		this.userConfig = new UserConfig(engine);
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.saver = new UsernameSaver(engine);
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.backgroundWhiteRectangle = new LauncherRectangle(root, 0, 0, engine.getWidth(), engine.getHeight());
		this.backgroundWhiteRectangle.setFill(Color.rgb(255, 255, 255, 0.17));
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, engine.getWidth(), 31);
		this.topRectangle.setFill(Color.rgb(0, 0, 0, 0.70));
		this.topRectangle.setOpacity(1.0);
		/** ===================== AFFICHER UN LOGO ===================== */
		this.drawLogo(engine, getResourceLocation().loadImage(engine, "alternative_logo.png"), engine.getWidth() / 2 - 165, 30, 330, 230, root, Mover.DONT_MOVE);
		/** ===================== ICONE BLOCK EN HAUT MILIEU ===================== */
		this.titleImage = new LauncherImage(root);
		this.titleImage.setImage(getResourceLocation().loadImage(engine, "favicon.png"));
		this.titleImage.setSize(25, 25);
		this.titleImage.setPosition(engine.getWidth() / 3 + 40, 3);
		/** ===================== TEXTE EN HAUT MILIEU ===================== */
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("Mon Super Launcher");
		this.titleLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.titleLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.titleLabel.setPosition(engine.getWidth() / 2 - 80, -4);
		this.titleLabel.setOpacity(0.7);
		this.titleLabel.setSize(500, 40);
		/** ===================== BOUTON FERMER ===================== */
		this.closeButton = new LauncherButton(root);
		this.closeButton.setInvisible();
		this.closeButton.setPosition(engine.getWidth() - 35, 2);
		this.closeButton.setSize(15, 15);
		this.closeButton.setBackground(null);
		LauncherImage closeImage = new LauncherImage(root, getResourceLocation().loadImage(engine, "close.png"));
		closeImage.setSize(15, 15);
		this.closeButton.setGraphic(closeImage);
		this.closeButton.setOnAction(event -> {
			System.exit(0);
		});
		/** ===================== BOUTON REDUIRE ===================== */
		this.reduceButton = new LauncherButton(root);
		this.reduceButton.setInvisible();
		this.reduceButton.setPosition(engine.getWidth() - 55, 2);
		this.reduceButton.setSize(15, 15);
		this.reduceButton.setBackground(null);
		LauncherImage reduceImage = new LauncherImage(root, getResourceLocation().loadImage(engine, "reduce.png"));
		reduceImage.setSize(15, 15);
		this.reduceButton.setGraphic(reduceImage);
		this.reduceButton.setOnAction(event -> {
			Stage stage = (Stage) ((LauncherButton) event.getSource()).getScene().getWindow();
			stage.setIconified(true);
		});
		/** ===================== CASE PSEUDONYME ===================== */
		this.usernameField = new LauncherTextField(root);
		this.usernameField.setText(this.saver.getUsername());
		this.usernameField.setPosition(engine.getWidth() / 2 - 135, engine.getHeight() / 2 - 57);
		this.usernameField.setSize(270, 50);
		this.usernameField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.usernameField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.usernameField.setVoidText("Nom de compte");
		/** ===================== CASE MOT DE PASSE ===================== */
		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setPosition(engine.getWidth() / 2 - 135, engine.getHeight() / 2);
		this.passwordField.setSize(270, 50);
		this.passwordField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.passwordField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.passwordField.setVoidText("Mot de passe (vide = crack)");
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton = new LauncherButton(root);
		this.loginButton.setText("Se connecter");
		this.loginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.loginButton.setPosition(engine.getWidth() / 2 - 67, engine.getHeight() / 2 + 60);
		this.loginButton.setSize(200, 45);
		this.loginButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.loginButton.setAction(ce -> {
			saver.writeUsername(this.usernameField.getText());
			/** ===================== AUTHENTIFICATION OFFLINE (CRACK) ===================== */
			if (this.usernameField.getText().length() < 3) {
				new LauncherAlert("Authentification echouee", "Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
			}
			else if (this.usernameField.getText().length() > 3 && this.passwordField.getText().isEmpty()) {
				GameAuth auth = new GameAuth(this.usernameField.getText(), this.passwordField.getText(), AccountType.OFFLINE);
				if (auth.isLogged()) {
					this.update(engine, auth);
				}
			}
			/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
			else if (this.usernameField.getText().length() > 3 && !this.passwordField.getText().isEmpty()) {
				GameAuth auth = new GameAuth(this.usernameField.getText(), this.passwordField.getText(), AccountType.MOJANG);
				if (auth.isLogged()) {
					this.update(engine, auth);
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
		this.settingsButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		LauncherImage imageButton = new LauncherImage(root, getResourceLocation().loadImage(engine, "settings.png"));
        imageButton.setSize(27, 27);
        this.settingsButton.setGraphic(imageButton);
		this.settingsButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.settingsButton.setPosition(engine.getWidth() / 2 - 135, engine.getHeight() / 2 + 60);
		this.settingsButton.setSize(60, 45);
		this.settingsButton.setAction(ev -> {
			Scene scene = new Scene(createSettingsContent(engine));
			Stage stage = new Stage();
			scene.setFill(Color.TRANSPARENT);
			stage.setResizable(false);
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setTitle("Parametres Launcher");
			stage.setWidth(500);
			stage.setHeight(230);
			stage.setScene(scene);
			stage.show();
		});
		/** ===================== BOUTON URL FACEBOOK ===================== */
		this.facebookButton = new LauncherButton(root);
		this.facebookButton.setInvisible();
		this.facebookButton.setPosition(engine.getWidth() / 2 - 125, engine.getHeight() - 130);
		LauncherImage facebookImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "fb_icon.png"));
		facebookImg.setSize(80, 80);
		this.facebookButton.setGraphic(facebookImg);
		this.facebookButton.setSize((int)facebookImg.getFitWidth(), (int)facebookImg.getFitHeight());
		this.facebookButton.setBackground(null);
		this.facebookButton.setOnAction(event -> {
			openLink(FACEBOOK_URL);
		});
		/** ===================== BOUTON URL TWITTER ===================== */
		this.twitterButton = new LauncherButton(root);
		this.twitterButton.setInvisible();
		this.twitterButton.setPosition(engine.getWidth() / 2 + 25, engine.getHeight() - 130);
		LauncherImage twitterImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "twitter_icon.png"));
		twitterImg.setSize(80, 80);
		this.twitterButton.setGraphic(twitterImg);
		this.twitterButton.setSize((int)twitterImg.getFitWidth(), (int)twitterImg.getFitHeight());
		this.twitterButton.setBackground(null);
		this.twitterButton.setOnAction(event -> {
			openLink(TWITTER_URL);
		});
		/** ===================== BOUTON URL INSTAGRAM ===================== */
		this.instagramButton = new LauncherButton(root);
		this.instagramButton.setInvisible();
		this.instagramButton.setPosition(engine.getWidth() / 2 - 125 - 150, engine.getHeight() - 130);
		LauncherImage instagramImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "insta_icon.png"));
		instagramImg.setSize(80, 80);
		this.instagramButton.setGraphic(instagramImg);
		this.instagramButton.setSize((int)instagramImg.getFitWidth(), (int)instagramImg.getFitHeight());
		this.instagramButton.setBackground(null);
		this.instagramButton.setOnAction(event -> {
			openLink(INSTAGRAM_URL);
		});
		/** ===================== BOUTON URL YOUTUBE ===================== */
		this.youtubeButton = new LauncherButton(root);
		this.youtubeButton.setInvisible();
		this.youtubeButton.setPosition(engine.getWidth() / 2 - 125 + 300, engine.getHeight() - 130);
		LauncherImage youtubeImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "yt_icon.png"));
		youtubeImg.setSize(80, 80);
		this.youtubeButton.setGraphic(youtubeImg);
		this.youtubeButton.setSize((int)youtubeImg.getFitWidth(), (int)youtubeImg.getFitHeight());
		this.youtubeButton.setBackground(null);
		this.youtubeButton.setOnAction(event -> {
			openLink(YOUTUBE_URL);
		});
		
		/** ============================== MISE A JOUR ==============================  **/
		this.updateRectangle = new LauncherRectangle(root, engine.getWidth() / 2 - 175, engine.getHeight() / 2 - 60, 350, 180);
		this.updateRectangle.setArcWidth(10.0);
		this.updateRectangle.setArcHeight(10.0);
		this.updateRectangle.setFill(Color.rgb(0, 0, 0, 0.60));
		this.updateRectangle.setVisible(false);
		/** =============== LABEL TITRE MISE A JOUR ===============  **/
		this.updateLabel = new LauncherLabel(root);
		this.updateLabel.setText("- MISE A JOUR -");
		this.updateLabel.setAlignment(Pos.CENTER);
		this.updateLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.updateLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.updateLabel.setPosition(engine.getWidth() / 2 - 95, engine.getHeight() / 2 - 55);
		this.updateLabel.setOpacity(1);
		this.updateLabel.setSize(190, 40);
		this.updateLabel.setVisible(false);
		/** =============== ETAPE DE MISE A JOUR ===============  **/
		this.currentStep = new LauncherLabel(root);
		this.currentStep.setText("Preparation de la mise a jour.");
		this.currentStep.setFont(Font.font("Verdana", FontPosture.ITALIC, 18F)); // FontPosture.ITALIC
		this.currentStep.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.currentStep.setAlignment(Pos.CENTER);
		this.currentStep.setPosition(engine.getWidth() / 2 - 160, engine.getHeight() / 2 + 83);
		this.currentStep.setOpacity(0.4);
		this.currentStep.setSize(320, 40);
		this.currentStep.setVisible(false);
		/** =============== FICHIER ACTUEL EN TELECHARGEMENT ===============  **/
		this.currentFileLabel = new LauncherLabel(root);
		this.currentFileLabel.setText("launchwrapper-12.0.jar");
		this.currentFileLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.currentFileLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.currentFileLabel.setAlignment(Pos.CENTER);
		this.currentFileLabel.setPosition(engine.getWidth() / 2 - 160, engine.getHeight() / 2 + 25);
		this.currentFileLabel.setOpacity(0.8);
		this.currentFileLabel.setSize(320, 40);
		this.currentFileLabel.setVisible(false);
		/** =============== POURCENTAGE ===============  **/
		this.percentageLabel = new LauncherLabel(root);
		this.percentageLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 30F));
		this.percentageLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.percentageLabel.setAlignment(Pos.CENTER);
		this.percentageLabel.setPosition(engine.getWidth() / 2 - 50, engine.getHeight() / 2 - 5);
		this.percentageLabel.setOpacity(0.8);
		this.percentageLabel.setSize(100, 40);
		this.percentageLabel.setVisible(false);
	}
	
	private void update(GameEngine engine, GameAuth auth) {
		this.usernameField.setDisable(true);
		this.passwordField.setDisable(true);
		this.loginButton.setDisable(true);
		this.settingsButton.setDisable(true);
		this.usernameField.setVisible(false);
		this.passwordField.setVisible(false);
		this.loginButton.setVisible(false);
		this.settingsButton.setVisible(false);
		this.updateRectangle.setVisible(true);
		this.updateLabel.setVisible(true);
		this.currentStep.setVisible(true);
		this.currentFileLabel.setVisible(true);
		this.percentageLabel.setVisible(true);
		updater.reg(engine);
		updater.reg(auth.getSession());
		engine.reg(this.updater);
	    this.updateThread = new Thread() {
	        public void run() {
	        	engine.getGameUpdater().run();
	        }
	      };
	    this.updateThread.start();
		/** ===================== REFAICHIR LE NOM DI FICHIER EN TELECHARGEMENT ===================== */
	    this.timeline = new Timeline(new KeyFrame[] { new KeyFrame(javafx.util.Duration.seconds(0.0D), e -> timelineUpdate(engine), new javafx.animation.KeyValue[0]), new KeyFrame(javafx.util.Duration.seconds(0.1D), new javafx.animation.KeyValue[0]) });
	    this.timeline.setCycleCount(Animation.INDEFINITE);
	    this.timeline.play();
	}
	
	private Parent createSettingsContent(GameEngine engine) {
		LauncherPane contentPane = new LauncherPane(engine);
		Rectangle rect = new Rectangle(500, 230);
		rect.setArcHeight(15.0);
		rect.setArcWidth(15.0);
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new LauncherSettings(contentPane, engine, this);
		return contentPane;
	}
	
	public void timelineUpdate(GameEngine engine) {
		if (engine.getGameUpdater().downloadedFiles > 0) {
			this.percentageLabel.setText(decimalFormat.format(engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload) + "%");
		}
		this.currentFileLabel.setText(engine.getGameUpdater().getCurrentFile());
		this.currentStep.setText(engine.getGameUpdater().getCurrentInfo());
	}
	
}
