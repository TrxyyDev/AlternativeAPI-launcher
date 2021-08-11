package fr.trxyy.launcher.template;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameMemory;
import fr.trxyy.alternative.alternative_api.GameSize;
import fr.trxyy.alternative.alternative_api.JVMArguments;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherPasswordField;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherProgressBar;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import fr.trxyy.alternative.alternative_auth.account.AccountType;
import fr.trxyy.alternative.alternative_auth.base.GameAuth;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherPanel extends IScreen {
	/** TOP */
	private LauncherRectangle topRectangle;
	private LauncherRectangle backgroundWhiteRectangle;
	private LauncherImage titleImage;
	private LauncherLabel titleLabel;
	private LauncherButton closeButton;
	private LauncherButton reduceButton;
	/** BUTTONS IMAGE URL */
	private LauncherButton facebookButton;
	private LauncherButton twitterButton;
	private LauncherButton instagramButton;
	private LauncherButton youtubeButton;
	/** LOGIN */
	private LauncherTextField usernameField;
	private LauncherPasswordField passwordField;
	private LauncherButton loginButton;
	private LauncherButton settingsButton;
	/** UPDATE */
	public Timeline timeline;
	private DecimalFormat decimalFormat = new DecimalFormat(".#");
	private Thread updateThread;
	private GameUpdater updater = new GameUpdater();
	private LauncherRectangle updateRectangle;
	private LauncherLabel updateLabel;
	private LauncherLabel currentFileLabel;
	private LauncherLabel percentageLabel;
	private LauncherLabel currentStep;
	/** USERNAME SAVER, CONFIG SAVER */
	/** PROGRESS BAR */
	public LauncherProgressBar bar;
	/** LINKS FOR BUTTONS IMAGES */
	private String FACEBOOK_URL = "http://facebook.com/";
	private String INSTAGRAM_URL = "http://instagram.com/";
	private String TWITTER_URL = "http://twitter.com/";
	private String YOUTUBE_URL = "http://youtube.com/c/Trxyy";
	/** GAMEENGINE REQUIRED */
	private GameEngine theGameEngine;
	/** AUTO LOGIN */
	private Timer autoLoginTimer;
	private LauncherLabel autoLoginLabel;
	private LauncherRectangle autoLoginRectangle;
	private LauncherButton autoLoginButton;
	/** CONFIGURATION **/
	public LauncherConfig config;
	/** MICROSOFT **/
	private GameAuth gameAuthentication;
	private LauncherButton microsoftButton;

	public LauncherPanel(Pane root, GameEngine engine) {
		this.theGameEngine = engine;
		this.config = new LauncherConfig(engine);
		this.config.loadConfiguration();
		
//		this.drawBackgroundImage(theGameEngine, root, "background.png");
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.backgroundWhiteRectangle = new LauncherRectangle(root, 0, 0, theGameEngine.getWidth(), theGameEngine.getHeight());
		this.backgroundWhiteRectangle.setFill(Color.rgb(255, 255, 255, 0.17));
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, theGameEngine.getWidth(), 31);
		this.topRectangle.setFill(Color.rgb(0, 0, 0, 0.70));
		this.topRectangle.setOpacity(1.0);
		/** ===================== AFFICHER UN LOGO ===================== */
		this.drawImage(theGameEngine, getResourceLocation().loadImage(theGameEngine, "alternative_logo.png"), theGameEngine.getWidth() / 2 - 200, 120, 400, 100, root, Mover.DONT_MOVE);
		/** ===================== ICONE BLOCK EN HAUT MILIEU ===================== */
		this.titleImage = new LauncherImage(root);
		this.titleImage.setImage(getResourceLocation().loadImage(theGameEngine, "favicon.png"));
		this.titleImage.setBounds(theGameEngine.getWidth() / 3 + 40, 3, 25, 25);
		/** ===================== TEXTE EN HAUT MILIEU ===================== */
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("Mon Super Launcher");
		this.titleLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.titleLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.titleLabel.setPosition(theGameEngine.getWidth() / 2 - 80, -4);
		this.titleLabel.setOpacity(0.7);
		this.titleLabel.setSize(500, 40);
		/** ===================== BOUTON FERMER ===================== */
		this.closeButton = new LauncherButton(root);
		this.closeButton.setInvisible();
		this.closeButton.setPosition(theGameEngine.getWidth() - 35, 2);
		this.closeButton.setSize(15, 15);
		this.closeButton.setBackground(null);
		LauncherImage closeImage = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "close.png"));
		closeImage.setSize(15, 15);
		this.closeButton.setGraphic(closeImage);
		this.closeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		/** ===================== BOUTON REDUIRE ===================== */
		this.reduceButton = new LauncherButton(root);
		this.reduceButton.setInvisible();
		this.reduceButton.setPosition(theGameEngine.getWidth() - 55, 2);
		this.reduceButton.setSize(15, 15);
		this.reduceButton.setBackground(null);
		LauncherImage reduceImage = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "reduce.png"));
		reduceImage.setSize(15, 15);
		this.reduceButton.setGraphic(reduceImage);
		this.reduceButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((LauncherButton) event.getSource()).getScene().getWindow();
				stage.setIconified(true);
			}
			
		});
		/** ===================== CASE PSEUDONYME ===================== */
		this.usernameField = new LauncherTextField(root);
		this.usernameField.setText((String)this.config.getValue("username"));
		this.usernameField.setBounds(theGameEngine.getWidth() / 2 - 135, theGameEngine.getHeight() / 2 - 57, 270, 50);
		this.usernameField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.usernameField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.usernameField.setVoidText("Nom de compte");
		/** ===================== CASE MOT DE PASSE ===================== */
		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setBounds(theGameEngine.getWidth() / 2 - 135, theGameEngine.getHeight() / 2, 270, 50);
		this.passwordField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.passwordField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.passwordField.setVoidText("Mot de passe (vide = crack)");
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton = new LauncherButton(root);
		this.loginButton.setText("Se connecter");
		this.loginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.loginButton.setBounds(theGameEngine.getWidth() / 2 - 67, theGameEngine.getHeight() / 2 + 60, 200, 45);
		this.loginButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.loginButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				config.updateValue("username", usernameField.getText());
				/**
				 * ===================== AUTHENTIFICATION OFFLINE (CRACK) =====================
				 */
				if (usernameField.getText().length() < 3) {
					new LauncherAlert("Authentification echouee",
							"Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
				} else if (usernameField.getText().length() > 3 && passwordField.getText().isEmpty()) {
					gameAuthentication = new GameAuth(usernameField.getText(), passwordField.getText(),
							AccountType.OFFLINE);
					if (gameAuthentication.isLogged()) {
						update(gameAuthentication);
					}
				}
				/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
				else if (usernameField.getText().length() > 3 && !passwordField.getText().isEmpty()) {
					gameAuthentication = new GameAuth(usernameField.getText(), passwordField.getText(),
							AccountType.MOJANG);
					if (gameAuthentication.isLogged()) {
						update(gameAuthentication);
					} else {
						new LauncherAlert("Authentification echouee!",
								"Impossible de se connecter, l'authentification semble etre une authentification 'en-ligne'"
										+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres. (compte non migrer)"
										+ "\n-Faites bien attention aux majuscules et minuscules. \nAssurez-vous d'utiliser un compte Mojang.");
					}
				} else {
					new LauncherAlert("Authentification echouee!",
							"Impossible de se connecter, l'authentification semble etre une authentification 'hors-ligne'"
									+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres.");
				}
			}
		});
		
		/** ===================== BOUTON PARAMETRES ===================== */
		this.microsoftButton = new LauncherButton(root);
		LauncherImage mcaImage = new LauncherImage(root, loadImage(theGameEngine, "microsoft.png"));
		mcaImage.setSize(20, 20);
		this.microsoftButton.setGraphic(mcaImage);
		this.microsoftButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.microsoftButton.setBounds(theGameEngine.getWidth() / 2 + 140, theGameEngine.getHeight() / 2 + 60, 45, 43);
		this.microsoftButton.setInvisible();
		this.microsoftButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gameAuthentication = new GameAuth(AccountType.MICROSOFT);
				showMicrosoftAuth(gameAuthentication);
				if (gameAuthentication.isLogged()) {
					update(gameAuthentication);
				}
			}
		});
		/** ===================== BOUTON PARAMETRES ===================== */
		this.settingsButton = new LauncherButton(root);
		this.settingsButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		LauncherImage imageButton = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "settings.png"));
		imageButton.setSize(27, 27);
		this.settingsButton.setGraphic(imageButton);
		this.settingsButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.settingsButton.setBounds(theGameEngine.getWidth() / 2 - 135, theGameEngine.getHeight() / 2 + 60, 60, 45);
		this.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Scene scene = new Scene(createSettingsPanel());
				Stage stage = new Stage();
				scene.setFill(Color.TRANSPARENT);
				stage.setResizable(false);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle("Parametres Launcher");
				stage.setWidth(500);
				stage.setHeight(300);
				stage.setScene(scene);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
			}
		});
		/** ===================== BOUTON URL FACEBOOK ===================== */
		this.facebookButton = new LauncherButton(root);
		this.facebookButton.setInvisible();
		LauncherImage facebookImg = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "fb_icon.png"));
		facebookImg.setSize(80, 80);
		this.facebookButton.setGraphic(facebookImg);
		this.facebookButton.setBounds(theGameEngine.getWidth() / 2 - 125, theGameEngine.getHeight() - 130, (int) facebookImg.getFitWidth(), (int) facebookImg.getFitHeight());
		this.facebookButton.setBackground(null);
		this.facebookButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				openLink(FACEBOOK_URL);
			}
		});
		/** ===================== BOUTON URL TWITTER ===================== */
		this.twitterButton = new LauncherButton(root);
		this.twitterButton.setInvisible();
		LauncherImage twitterImg = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "twitter_icon.png"));
		twitterImg.setSize(80, 80);
		this.twitterButton.setGraphic(twitterImg);
		this.twitterButton.setBounds(theGameEngine.getWidth() / 2 + 25, theGameEngine.getHeight() - 130, (int) twitterImg.getFitWidth(), (int) twitterImg.getFitHeight());
		this.twitterButton.setBackground(null);
		this.twitterButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				openLink(TWITTER_URL);
			}
		});
		/** ===================== BOUTON URL INSTAGRAM ===================== */
		this.instagramButton = new LauncherButton(root);
		this.instagramButton.setInvisible();
		LauncherImage instagramImg = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "insta_icon.png"));
		instagramImg.setSize(80, 80);
		this.instagramButton.setGraphic(instagramImg);
		this.instagramButton.setBounds(theGameEngine.getWidth() / 2 - 125 - 150, theGameEngine.getHeight() - 130, (int) instagramImg.getFitWidth(), (int) instagramImg.getFitHeight());
		this.instagramButton.setBackground(null);
		this.instagramButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				openLink(INSTAGRAM_URL);
			}
		});
		/** ===================== BOUTON URL YOUTUBE ===================== */
		this.youtubeButton = new LauncherButton(root);
		this.youtubeButton.setInvisible();
		LauncherImage youtubeImg = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "yt_icon.png"));
		youtubeImg.setSize(80, 80);
		this.youtubeButton.setGraphic(youtubeImg);
		this.youtubeButton.setBounds(theGameEngine.getWidth() / 2 - 125 + 300, theGameEngine.getHeight() - 130, (int) youtubeImg.getFitWidth(), (int) youtubeImg.getFitHeight());
		this.youtubeButton.setBackground(null);
		this.youtubeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				openLink(YOUTUBE_URL);
			}
		});

		/**
		 * ============================== MISE A JOUR ==============================
		 **/
		this.updateRectangle = new LauncherRectangle(root, theGameEngine.getWidth() / 2 - 175, theGameEngine.getHeight() / 2 - 60, 350, 180);
		this.updateRectangle.setArcWidth(10.0);
		this.updateRectangle.setArcHeight(10.0);
		this.updateRectangle.setFill(Color.rgb(0, 0, 0, 0.60));
		this.updateRectangle.setVisible(false);
		/** =============== LABEL TITRE MISE A JOUR =============== **/
		this.updateLabel = new LauncherLabel(root);
		this.updateLabel.setText("- MISE A JOUR -");
		this.updateLabel.setAlignment(Pos.CENTER);
		this.updateLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.updateLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.updateLabel.setBounds(theGameEngine.getWidth() / 2 - 95, theGameEngine.getHeight() / 2 - 55, 190, 40);
		this.updateLabel.setOpacity(1);
		this.updateLabel.setVisible(false);
		/** =============== ETAPE DE MISE A JOUR =============== **/
		this.currentStep = new LauncherLabel(root);
		this.currentStep.setText("Preparation de la mise a jour.");
		this.currentStep.setFont(Font.font("Verdana", FontPosture.ITALIC, 18F)); // FontPosture.ITALIC
		this.currentStep.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.currentStep.setAlignment(Pos.CENTER);
		this.currentStep.setBounds(theGameEngine.getWidth() / 2 - 160, theGameEngine.getHeight() / 2 + 83, 320, 40);
		this.currentStep.setOpacity(0.4);
		this.currentStep.setVisible(false);
		/** =============== FICHIER ACTUEL EN TELECHARGEMENT =============== **/
		this.currentFileLabel = new LauncherLabel(root);
		this.currentFileLabel.setText("launchwrapper-12.0.jar");
		this.currentFileLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.currentFileLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.currentFileLabel.setAlignment(Pos.CENTER);
		this.currentFileLabel.setBounds(theGameEngine.getWidth() / 2 - 160, theGameEngine.getHeight() / 2 + 25, 320, 40);
		this.currentFileLabel.setOpacity(0.8);
		this.currentFileLabel.setVisible(false);
		/** =============== POURCENTAGE =============== **/
		this.percentageLabel = new LauncherLabel(root);
		this.percentageLabel.setText("0%");
		this.percentageLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 30F));
		this.percentageLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.percentageLabel.setAlignment(Pos.CENTER);
		this.percentageLabel.setBounds(theGameEngine.getWidth() / 2 - 50, theGameEngine.getHeight() / 2 - 5, 100, 40);
		this.percentageLabel.setOpacity(0.8);
		this.percentageLabel.setVisible(false);
		
		this.bar = new LauncherProgressBar(root);
		this.bar.setPosition(theGameEngine.getWidth() / 2 - 125, theGameEngine.getHeight() / 2 + 60);
		this.bar.setSize(250, 20);
		this.bar.setVisible(false);
		
		/** =============== LOGIN AUTOMATIQUE (CRACK SEULEMENT) =============== **/
		this.autoLoginRectangle = new LauncherRectangle(root, 0, theGameEngine.getHeight() - 32, 1000, theGameEngine.getHeight());
		this.autoLoginRectangle.setFill(Color.rgb(0, 0, 0, 0.70));
		this.autoLoginRectangle.setOpacity(1.0);
		this.autoLoginRectangle.setVisible(false);
		
		this.autoLoginLabel = new LauncherLabel(root);
		this.autoLoginLabel.setText("Connexion auto dans 3 secondes. Appuyez sur ECHAP pour annuler.");
		this.autoLoginLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.autoLoginLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
		this.autoLoginLabel.setBounds(theGameEngine.getWidth() / 2 - 280, theGameEngine.getHeight() - 34, 700, 40);
		this.autoLoginLabel.setOpacity(0.7);
		this.autoLoginLabel.setVisible(false);
		
		this.autoLoginButton = new LauncherButton(root);
		this.autoLoginButton.setText("Annuler");
		this.autoLoginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.autoLoginButton.setBounds(theGameEngine.getWidth() / 2 + 60, theGameEngine.getHeight() - 30, 200, 20);
		this.autoLoginButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-text-fill: black;");
		this.autoLoginButton.setVisible(false);
		this.autoLoginButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				autoLoginTimer.cancel();
				autoLoginLabel.setVisible(false);
				autoLoginButton.setVisible(false);
				autoLoginRectangle.setVisible(false);
			}
		});
		String userName = (String)this.config.getValue("username");
		if (userName.length() > 2 && !userName.contains("@") && (boolean)this.config.getValue("autologin").equals(true)) {
			Platform.runLater(new Runnable() {
				public void run() {
					autoLoginTimer = new Timer();
					TimerTask timerTask = new TimerTask() {
						int waitTime = 5;
						int elapsed = 0;
						@Override
						public void run() {
							elapsed++;

							if (elapsed % waitTime == 0) {
								if (!theGameEngine.getGameMaintenance().isAccessBlocked()) {
									loginButton.fire();
									autoLoginTimer.cancel();
									autoLoginLabel.setVisible(false);
									autoLoginButton.setVisible(false);
									autoLoginRectangle.setVisible(false);
								}
							} else {
								final int time = (waitTime - (elapsed % waitTime));
								Platform.runLater(new Runnable() {
									public void run() {
										autoLoginLabel.setText("Connexion auto dans " + time + " secondes.");
									}
								});
							}
						}
					};
					autoLoginTimer.schedule(timerTask, 0, 1000);
					autoLoginLabel.setVisible(true);
					autoLoginRectangle.setVisible(true);
					autoLoginButton.setVisible(true);
				}
			});
		}
	}

	private void update(GameAuth auth) {
		this.usernameField.setDisable(true);
		this.passwordField.setDisable(true);
		this.loginButton.setDisable(true);
		this.settingsButton.setDisable(true);
		this.usernameField.setVisible(false);
		this.passwordField.setVisible(false);
		this.loginButton.setVisible(false);
		this.settingsButton.setVisible(false);
		this.microsoftButton.setVisible(false);
		this.updateRectangle.setVisible(true);
		this.updateLabel.setVisible(true);
		this.currentStep.setVisible(true);
		this.currentFileLabel.setVisible(true);
		this.percentageLabel.setVisible(true);
		this.bar.setVisible(true);
		this.updater.reg(theGameEngine);
		this.updater.reg(auth.getSession());
		/**
		 * Change settings in GameEngine from launcher_config.json
		 */
		this.theGameEngine.reg(GameMemory.getMemory(Double.parseDouble((String) this.config.getValue("allocatedram"))));
		this.theGameEngine.reg(GameSize.getWindowSize(Integer.parseInt((String) this.config.getValue("gamesize"))));
		boolean useVmArgs = (Boolean)config.getValue("usevmarguments");
		String vmArgs = (String) config.getValue("vmarguments");
		String[] s = null;
		if (useVmArgs) {
			if (vmArgs.length() > 3) {
				s = vmArgs.split(" ");
			}
			JVMArguments arguments = new JVMArguments(s);
			this.theGameEngine.reg(arguments);
		}
		/** END */
		this.theGameEngine.reg(this.updater);
		this.updateThread = new Thread() {
			public void run() {
				theGameEngine.getGameUpdater().run();
			}
		};
		this.updateThread.start();
		/** ===================== REFAICHIR LE NOM DU FICHIER, PROGRESSBAR, POURCENTAGE  ===================== **/
		this.timeline = new Timeline(
				new KeyFrame[] { new KeyFrame(javafx.util.Duration.seconds(0.0D), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						timelineUpdate(theGameEngine);
					}
				}, new javafx.animation.KeyValue[0]),
						new KeyFrame(javafx.util.Duration.seconds(0.1D), new javafx.animation.KeyValue[0]) });
		this.timeline.setCycleCount(Animation.INDEFINITE);
		this.timeline.play();
	}

	private Parent createSettingsPanel() {
		LauncherPane contentPane = new LauncherPane(theGameEngine);
		Rectangle rect = new Rectangle(500, 300);
		rect.setArcHeight(15.0);
		rect.setArcWidth(15.0);
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new LauncherSettings(contentPane, theGameEngine, this);
		return contentPane;
	}
	
	private void showMicrosoftAuth(GameAuth auth) {
		Scene scene = new Scene(createMicrosoftPanel(auth));
		Stage stage = new Stage();
		scene.setFill(Color.TRANSPARENT);
		stage.setResizable(false);
		stage.setTitle("Microsoft Authentication");
		stage.setWidth(500);
		stage.setHeight(600);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}

	private Parent createMicrosoftPanel(GameAuth auth) {
		LauncherPane contentPane = new LauncherPane(theGameEngine);
		auth.connectMicrosoft(contentPane);
		return contentPane;
	}

	public void timelineUpdate(GameEngine engine) {
		if (engine.getGameUpdater().downloadedFiles > 0) {
			this.percentageLabel.setText(decimalFormat.format(
				engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload) + "%");
		}
		this.currentFileLabel.setText(engine.getGameUpdater().getCurrentFile());
		this.currentStep.setText(engine.getGameUpdater().getCurrentInfo());
		double percent = (engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload / 100.0D);
		this.bar.setProgress(percent);
	}

}