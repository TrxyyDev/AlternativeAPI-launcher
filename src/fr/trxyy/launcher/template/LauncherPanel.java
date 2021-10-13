package fr.trxyy.launcher.template;

import java.util.HashMap;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameMemory;
import fr.trxyy.alternative.alternative_api.GameSize;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.EnumConfig;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherPasswordField;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherProgressBar;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import fr.trxyy.alternative.alternative_auth.account.AccountType;
import fr.trxyy.alternative.alternative_auth.base.GameAuth;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LauncherPanel extends IScreen {
	/** TOP */
	private LauncherButton closeButton;
	private LauncherButton reduceButton;
	/** LOGIN */
	private LauncherTextField usernameField;
	private LauncherPasswordField passwordField;
	private LauncherButton loginButton;
	private LauncherButton settingsButton;
	private LauncherButton microsoftButton;
	/** USERNAME SAVER, CONFIG SAVER */
	public LauncherConfig config;
	/** GAMEENGINE REQUIRED */
	private GameEngine gameEngine;
	private LauncherProgressBar progressBar;
	private LauncherLabel updateLabel;
	private Rectangle updateRectangle;
	private GameUpdater updater = new GameUpdater();
	private Thread updateThread;
	/** LOGGED ING **/
	private Rectangle loggedRectangle;
	private LauncherImage headImage;
	private LauncherLabel accountLabel;
	/** SETTINGS **/
	private LauncherButton saveButton;
	private LauncherLabel memorySliderLabel;
	private Slider memorySlider;
	private LauncherLabel windowSizeLabel;
	private ComboBox<String> windowsSizeList;
	private CheckBox autoLogin;
	private GameAuth gameAuthentication;

	private Font customFont = FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F);
	
	public LauncherPanel(Pane root, GameEngine engine) {
		this.gameEngine = engine;
		this.config = new LauncherConfig(engine);
		this.config.loadConfiguration();
		
		this.drawRect(root, 0, 0, gameEngine.getWidth(), gameEngine.getHeight(), Color.rgb(255, 255, 255, 0.10));
		/** ===================== RECTANGLE NOIR EN BAS ===================== */
		this.drawRect(root, 0, engine.getHeight() - 110, engine.getWidth(), 300, Color.rgb(0, 0, 0, 0.4));
		/** ===================== AFFICHER UN LOGO ===================== */
		this.drawImage(gameEngine, loadImage(gameEngine, "alternativeapi_logo.png"), 0, this.gameEngine.getHeight() - 100, 400, 100, root, Mover.DONT_MOVE);
		/** ===================== BOUTON FERMER ===================== */
		this.closeButton = new LauncherButton(root);
		this.closeButton.setInvisible();
		this.closeButton.setBounds(gameEngine.getWidth() - 50, -3, 40, 20);
		LauncherImage closeImage = new LauncherImage(root, loadImage(gameEngine, "close.png"));
		closeImage.setSize(40, 20);
		this.closeButton.setGraphic(closeImage);
		this.closeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		/** ===================== BOUTON REDUIRE ===================== */
		this.reduceButton = new LauncherButton(root);
		this.reduceButton.setInvisible();
		this.reduceButton.setBounds(gameEngine.getWidth() - 91, -3, 40, 20);
		LauncherImage reduceImage = new LauncherImage(root, loadImage(gameEngine, "reduce.png"));
		reduceImage.setSize(40, 20);
		this.reduceButton.setGraphic(reduceImage);
		this.reduceButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((LauncherButton) event.getSource()).getScene().getWindow();
				stage.setIconified(true);
			}
		});
		/** ===================== CASE PSEUDONYME ===================== */
		this.usernameField = new LauncherTextField((String)this.config.getValue(EnumConfig.USERNAME), root);
		this.usernameField.setBounds(this.gameEngine.getWidth() - 360, this.gameEngine.getHeight() - 85, 220, 20);
		this.setFontSize(14.0F);
		this.usernameField.setFont(this.customFont);
		this.usernameField.addStyle("-fx-background-color: rgb(230, 230, 230);");
		this.usernameField.addStyle("-fx-text-fill: black;");
		this.usernameField.addStyle("-fx-border-radius: 0 0 0 0;");
		this.usernameField.addStyle("-fx-background-radius: 0 0 0 0;");
		this.usernameField.setVoidText("Nom de compte / Mail");
		/** ===================== CASE MOT DE PASSE ===================== */
		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setBounds(this.gameEngine.getWidth() - 360, this.gameEngine.getHeight() - 50, 220, 20);
		this.setFontSize(14.0F);
		this.passwordField.setFont(this.customFont);
		this.passwordField.addStyle("-fx-background-color: rgb(230, 230, 230);");
		this.passwordField.addStyle("-fx-text-fill: black;");
		this.passwordField.addStyle("-fx-border-radius: 0 0 0 0;");
		this.passwordField.addStyle("-fx-background-radius: 0 0 0 0;");
		this.passwordField.setVoidText("Mot de passe (vide = crack)");
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton = new LauncherButton("Connexion", root);
		this.setFontSize(12.5F);
		this.loginButton.setFont(this.customFont);
		this.loginButton.setBounds(this.gameEngine.getWidth() - 130, this.gameEngine.getHeight() - 50, 90, 20);
		this.loginButton.addStyle("-fx-background-color: rgb(230, 230, 230);");
		this.loginButton.addStyle("-fx-text-fill: black;");
		this.loginButton.addStyle("-fx-border-radius: 0 0 0 0;");
		this.loginButton.addStyle("-fx-background-radius: 0 0 0 0;");
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
					gameAuthentication = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.OFFLINE);
					if (gameAuthentication.isLogged()) {
						updateGame(gameAuthentication);
					}
				}
				/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
				else if (usernameField.getText().length() > 3 && !passwordField.getText().isEmpty()) {
					gameAuthentication = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.MOJANG);
					if (gameAuthentication.isLogged()) {
						updateGame(gameAuthentication);
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
		
		this.microsoftButton = new LauncherButton(root);
		LauncherImage mcaImage = new LauncherImage(root, loadImage(gameEngine, "microsoft.png"));
		mcaImage.setSize(20, 20);
		this.microsoftButton.setGraphic(mcaImage);
		this.microsoftButton.setBounds(this.gameEngine.getWidth() - 40, this.gameEngine.getHeight() - 50, 20, 20);
		this.microsoftButton.setInvisible();
		this.microsoftButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gameAuthentication = new GameAuth(AccountType.MICROSOFT);
				showMicrosoftAuth(gameAuthentication);
				if (gameAuthentication.isLogged()) {
					updateGame(gameAuthentication);
				}
			}
		});
		
		this.settingsButton = new LauncherButton("Parametres", root);
		this.setFontSize(14.0F);
		this.settingsButton.setFont(this.customFont);
		this.settingsButton.setBounds(this.gameEngine.getWidth() - 130, this.gameEngine.getHeight() - 85, 120, 20);
		this.settingsButton.addStyle("-fx-background-color: rgb(230, 230, 230);");
		this.settingsButton.addStyle("-fx-text-fill: black;");
		this.settingsButton.addStyle("-fx-border-radius: 0 0 0 0;");
		this.settingsButton.addStyle("-fx-background-radius: 0 0 0 0;");
		this.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				changeToSettings();
			}
		});
		
		/** ======================================================== **/
		this.updateLabel = new LauncherLabel(root);
		this.updateLabel.setText("Mise a jour...");
		this.setFontSize(30.0F);
		this.updateLabel.setFont(this.customFont);
		this.updateLabel.setBounds(this.gameEngine.getWidth() - 250, this.gameEngine.getHeight() - 70, 200, 20);
		this.updateLabel.addStyle("-fx-text-fill: white;");
		this.updateLabel.setOpacity(0.0D);
		this.updateLabel.setVisible(false);
		
		this.loggedRectangle = this.drawRect(root, this.gameEngine.getWidth() / 2 - 115, 50, 230, 200, Color.rgb(0, 0, 0, 0.4));
		this.loggedRectangle.setOpacity(0.0D);
		this.loggedRectangle.setVisible(false);
		
		this.headImage = new LauncherImage(root);
		this.headImage.setFitWidth(120);
		this.headImage.setFitHeight(120);
		this.headImage.setLayoutX(this.gameEngine.getWidth() / 2 - 60);
		this.headImage.setLayoutY(70);
		this.headImage.setOpacity(0.0D);
		this.headImage.setVisible(false);
		
		this.accountLabel = new LauncherLabel(root);
		this.accountLabel.setText((String)this.config.getValue(EnumConfig.USERNAME));
		this.accountLabel.setAlignment(Pos.CENTER);
		this.setFontSize(20.0F);
		this.accountLabel.setFont(this.customFont);
		this.accountLabel.setBounds(this.gameEngine.getWidth() / 2 - 110, this.gameEngine.getHeight() / 2 - 50, 220, 20);
		this.accountLabel.addStyle("-fx-text-fill: white;");
		this.accountLabel.setOpacity(0.0D);
		this.accountLabel.setVisible(false);
		
		this.updateRectangle = this.drawRect(root, this.gameEngine.getWidth() / 2 - 250, this.gameEngine.getHeight() / 2 + 70, 500, 30, Color.rgb(0, 0, 0, 0.4));
		this.updateRectangle.setOpacity(0.0D);
		this.updateRectangle.setVisible(false);
		
		this.progressBar = new LauncherProgressBar(root);
		this.progressBar.setBounds(this.gameEngine.getWidth() / 2 - 245, this.gameEngine.getHeight() / 2 + 75, 490, 21);
		this.progressBar.setOpacity(0.0D);
		this.progressBar.setVisible(false);
		
		/** ===================== SETTINGS ===================== */
		
		/** ===================== BOUTON DE SAUVEGARDE ===================== */
		this.saveButton = new LauncherButton("Valider", root);
		this.setFontSize(14.0F);
		this.saveButton.setFont(this.customFont);
		this.saveButton.setBounds(this.gameEngine.getWidth() - 130, this.gameEngine.getHeight() - 50, 120, 20);
		this.saveButton.addStyle("-fx-background-color: rgb(230, 230, 230);");
		this.saveButton.addStyle("-fx-text-fill: black;");
		this.saveButton.addStyle("-fx-border-radius: 0 0 0 0;");
		this.saveButton.addStyle("-fx-background-radius: 0 0 0 0;");
		this.saveButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				HashMap<String, String> configMap = new HashMap<String, String>();
				configMap.put("allocatedram", String.valueOf(memorySlider.getValue()));
				configMap.put("gamesize", "" + GameSize.getWindowSize(windowsSizeList.getValue()));
				configMap.put("autologin", "" + autoLogin.isSelected());
				config.updateValues(configMap);
				engine.reg(GameMemory.getMemory(Double.parseDouble((String) config.getValue(EnumConfig.RAM))));
				engine.reg(GameSize.getWindowSize(Integer.parseInt((String) config.getValue(EnumConfig.GAME_SIZE))));
				changeToMain();
			}
		});
		this.saveButton.setOpacity(0.0D);
		this.saveButton.setVisible(false);

		this.autoLogin = new CheckBox();
		this.autoLogin.setText("Connexion auto (crack)");
		this.autoLogin.setSelected((Boolean)config.getValue(EnumConfig.AUTOLOGIN));
		this.setFontSize(14.0F);
		this.autoLogin.setFont(this.customFont);
		this.autoLogin.setStyle("-fx-text-fill: white;");
		this.autoLogin.setLayoutX(this.gameEngine.getWidth() - 350);
		this.autoLogin.setLayoutY(this.gameEngine.getHeight() - 45);
		this.autoLogin.setOpacity(0.0D);
		this.autoLogin.setVisible(false);
		root.getChildren().add(autoLogin);

		/**
		 * ===================== WINDOWS SIZE LABEL SELECTIONNED =====================
		 */
		this.windowSizeLabel = new LauncherLabel(root);
		this.windowSizeLabel.setText("Taille du jeu:");
		this.windowSizeLabel.setOpacity(1.0);
		this.setFontSize(12.0F);
		this.windowSizeLabel.setFont(this.customFont);
		this.windowSizeLabel.setStyle("-fx-text-fill: white;");
		this.windowSizeLabel.setSize(370, 30);
		this.windowSizeLabel.setPosition(this.gameEngine.getWidth() - 130, this.gameEngine.getHeight() - 110);
		this.windowSizeLabel.setOpacity(0.0D);
		this.windowSizeLabel.setVisible(false);

		/** ===================== MC SIZE LIST ===================== */
		this.windowsSizeList = new ComboBox<String>();
		this.populateSizeList();
		if (config.getValue(EnumConfig.GAME_SIZE) != null) {
			this.windowsSizeList.setValue(GameSize.getWindowSize(Integer.parseInt((String) config.getValue(EnumConfig.GAME_SIZE))).getDesc());
		}
		this.windowsSizeList.setPrefSize(120, 20);
		this.windowsSizeList.setLayoutX(this.gameEngine.getWidth() - 130);
		this.windowsSizeList.setLayoutY(this.gameEngine.getHeight() - 85);
		this.windowsSizeList.setVisibleRowCount(5);
		this.windowsSizeList.setOpacity(0.0D);
		this.windowsSizeList.setVisible(false);
		root.getChildren().add(this.windowsSizeList);
		 
		/** ===================== SLIDER RAM LABEL SELECTIONNED ===================== */
		this.memorySliderLabel = new LauncherLabel(root);
		this.memorySliderLabel.setOpacity(1.0);
		this.setFontSize(16.0F);
		this.memorySliderLabel.setFont(this.customFont);
		this.memorySliderLabel.setStyle("-fx-text-fill: white;");
		this.memorySliderLabel.setSize(370, 30);
		this.memorySliderLabel.setPosition(this.gameEngine.getWidth() - 350, this.gameEngine.getHeight() - 105);
		this.memorySliderLabel.setOpacity(0.0D);
		this.memorySliderLabel.setVisible(false);

			/** ===================== SLIDER RAM ===================== */
			this.memorySlider = new Slider();
			this.memorySlider.setStyle("-fx-control-inner-background: rgba(46, 47, 48, 0.5);");
			this.memorySlider.setMin(1);
			this.memorySlider.setMax(10);
			if (config.getValue(EnumConfig.RAM) != null) {
				double d = Double.parseDouble((String) config.getValue(EnumConfig.RAM));
				this.memorySlider.setValue(d);
			}
			this.memorySlider.setLayoutX(this.gameEngine.getWidth() - 350);
			this.memorySlider.setLayoutY(this.gameEngine.getHeight() - 80);
			this.memorySlider.setPrefWidth(200);
			this.memorySlider.setBlockIncrement(1);
			this.memorySlider.setOpacity(0.0D);
			this.memorySlider.setVisible(false);
			this.memorySlider.valueProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
					memorySlider.setValue(Math.round(new_val.doubleValue()));
				}
			});
			this.memorySlider.valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					memorySliderLabel.setText("Ram allouee: " + newValue + "Gb");
				}
			});
			this.memorySliderLabel.setText("Ram allouee: " + this.memorySlider.getValue() + "Gb");
			root.getChildren().add(this.memorySlider);
		}

	private void populateSizeList() {
		for (GameSize size : GameSize.values()) {
			this.windowsSizeList.getItems().add(size.getDesc());
		}
	}

	private void updateGame(GameAuth auth) {		
		this.accountLabel.setText(gameAuthentication.getSession().getUsername());
		this.fadeOut(this.settingsButton, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
				settingsButton.setVisible(false);
		    }
		});
		this.fadeOut(this.loginButton, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
				loginButton.setVisible(false);
				microsoftButton.setVisible(false);
				updateLabel.setVisible(true);
				fadeIn(updateLabel, 300);
				
				progressBar.setVisible(true);
				fadeIn(progressBar, 300);
				
				loggedRectangle.setVisible(true);
				fadeIn(loggedRectangle, 300);
				
				headImage.setImage(new Image("https://minotar.net/helm/" + gameAuthentication.getSession().getUsername() + "/120.png"));
				headImage.setVisible(true);
				fadeIn(headImage, 300);
				
				updateRectangle.setVisible(true);
				fadeIn(updateRectangle, 300);
				
				loggedRectangle.setVisible(true);
				fadeIn(loggedRectangle, 300);
				
				accountLabel.setVisible(true);
				fadeIn(accountLabel, 300);
		    }
		});
		this.fadeOut(this.microsoftButton, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	microsoftButton.setVisible(false);
		    }
		});
		this.fadeOut(this.usernameField, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	usernameField.setVisible(false);
		    }
		});
		this.fadeOut(this.passwordField, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	passwordField.setVisible(false);
		    }
		});
		
		this.updater.reg(this.gameEngine);
		this.updater.reg(auth.getSession());
		this.gameEngine.reg(this.updater);
		this.updateThread = new Thread() {
			public void run() {
				Timeline t = new Timeline(new KeyFrame[] { new KeyFrame(Duration.seconds(0.0D), new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								double percent = (gameEngine.getGameUpdater().downloadedFiles * 100.0D / gameEngine.getGameUpdater().filesToDownload / 100.0D);
								progressBar.setProgress(percent);
							}
						}, new KeyValue[0]), new KeyFrame(Duration.seconds(0.1D), new KeyValue[0]) });
				t.setCycleCount(Animation.INDEFINITE);
				t.play();
				gameEngine.getGameUpdater().run();
			}
		};
		this.updateThread.start();
	}
	
	private void changeToSettings() {
		this.config.loadConfiguration();
		this.fadeOut(this.settingsButton, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
				settingsButton.setVisible(false);
		    }
		});
		this.fadeOut(this.loginButton, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
				loginButton.setVisible(false);
				saveButton.setVisible(true);
				autoLogin.setVisible(true);
				windowSizeLabel.setVisible(true);
				windowsSizeList.setVisible(true);
				memorySliderLabel.setVisible(true);
				memorySlider.setVisible(true);
				microsoftButton.setVisible(false);
				fadeIn(saveButton, 300);
				fadeIn(autoLogin, 300);
				fadeIn(windowSizeLabel, 300);
				fadeIn(windowsSizeList, 300);
				fadeIn(memorySliderLabel, 300);
				fadeIn(memorySlider, 300);
		    }
		});
		this.fadeOut(this.microsoftButton, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	microsoftButton.setVisible(false);
		    }
		});
		this.fadeOut(this.usernameField, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	usernameField.setVisible(false);
		    }
		});
		this.fadeOut(this.passwordField, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	passwordField.setVisible(false);
		    }
		});
	}
	
	private void changeToMain() {
		this.config.loadConfiguration();
		this.fadeOut(this.saveButton, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	saveButton.setVisible(false);
		    }
		});
		this.fadeOut(this.loginButton, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
				loginButton.setVisible(true);
				microsoftButton.setVisible(true);
				settingsButton.setVisible(true);
				passwordField.setVisible(true);
				usernameField.setVisible(true);
				fadeIn(loginButton, 300);
				fadeIn(microsoftButton, 300);
				fadeIn(settingsButton, 300);
				fadeIn(usernameField, 300);
				fadeIn(passwordField, 300);
				fadeIn(microsoftButton, 300);
		    }
		});
		this.fadeOut(this.autoLogin, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	autoLogin.setVisible(false);
		    }
		});
		this.fadeOut(this.windowSizeLabel, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	windowSizeLabel.setVisible(false);
		    }
		});
		this.fadeOut(this.windowsSizeList, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	windowsSizeList.setVisible(false);
		    }
		});
		this.fadeOut(this.memorySliderLabel, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	memorySliderLabel.setVisible(false);
		    }
		});
		this.fadeOut(this.memorySlider, 300).setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	memorySlider.setVisible(false);
		    }
		});
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
		LauncherPane contentPane = new LauncherPane(gameEngine);
		auth.connectMicrosoft(contentPane);
		return contentPane;
	}
	
	private void setFontSize(float size) {
		this.customFont = FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", size);
	}
}
