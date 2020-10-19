package fr.trxyy.launcher.template;

import java.util.HashMap;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameMemory;
import fr.trxyy.alternative.alternative_api.GameSize;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LauncherSettings extends IScreen {
	
	private LauncherLabel titleLabel;
	private LauncherRectangle topRectangle;
	private LauncherButton saveButton;
	private LauncherLabel memorySliderLabel;
	private LauncherLabel sliderLabel;
	private Slider memorySlider;
	private LauncherLabel windowsSizeLabel;
	private ComboBox<String> windowsSizeList;
	private CheckBox autoLogin;
	private CheckBox useVMArguments;
	private LauncherTextField vmArguments;
	
	public LauncherSettings(final Pane root, final GameEngine engine, final LauncherPanel pane) {
		this.drawBackgroundImage(engine, root, "background.png");
		pane.config.loadConfiguration();
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, 500, 15);
		this.topRectangle.setOpacity(0.7);
		/** ===================== LABEL TITRE ===================== */
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("PARAMETRES");
		this.titleLabel.setStyle("-fx-text-fill: white;");
		this.titleLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 28F));
		this.titleLabel.setPosition(150, 20);
		this.titleLabel.setSize(230, 35);
		/** ===================== MC SIZE LABEL ===================== */
		this.windowsSizeLabel = new LauncherLabel(root);
		this.windowsSizeLabel.setText("Taille de la fenetre:");
		this.windowsSizeLabel.setOpacity(1.0);
		this.windowsSizeLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.windowsSizeLabel.setStyle("-fx-text-fill: white;");
		this.windowsSizeLabel.setSize(370, 30);
		this.windowsSizeLabel.setPosition(50, 60);
		/** ===================== MC SIZE LIST ===================== */
		this.windowsSizeList = new ComboBox<String>();
		this.populateSizeList();
		if (pane.config.getValue("gamesize") != null) {
			this.windowsSizeList.setValue(GameSize.getWindowSize(Integer.parseInt((String) pane.config.getValue("gamesize"))).getDesc());
		}
		this.windowsSizeList.setPrefSize(100, 20);
		this.windowsSizeList.setLayoutX(340);
		this.windowsSizeList.setLayoutY(65);
		this.windowsSizeList.setVisibleRowCount(5);
		root.getChildren().add(this.windowsSizeList);
		/** ===================== SLIDER RAM LABEL ===================== */
		this.sliderLabel = new LauncherLabel(root);
		this.sliderLabel.setText("RAM Allouee:");
		this.sliderLabel.setOpacity(1.0);
		this.sliderLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.sliderLabel.setStyle("-fx-text-fill: white;");
		this.sliderLabel.setSize(370, 30);
		this.sliderLabel.setPosition(50, 100);
		/** ===================== SLIDER RAM LABEL SELECTIONNED ===================== */
		this.memorySliderLabel = new LauncherLabel(root);
		this.memorySliderLabel.setOpacity(1.0);
		this.memorySliderLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.memorySliderLabel.setStyle("-fx-text-fill: white;");
		this.memorySliderLabel.setSize(370, 30);
		this.memorySliderLabel.setPosition(380, 100);
		/** ===================== SLIDER RAM ===================== */
		this.memorySlider = new Slider();
		this.memorySlider.setStyle("-fx-control-inner-background: rgba(46, 47, 48, 0.5);");
		this.memorySlider.setMin(1);
		this.memorySlider.setMax(10);
		if (pane.config.getValue("allocatedram") != null) {
			double d = Double.parseDouble((String) pane.config.getValue("allocatedram"));
			this.memorySlider.setValue(d);
		}
		this.memorySlider.setLayoutX(50);
		this.memorySlider.setLayoutY(140);
		this.memorySlider.setPrefWidth(395);
		this.memorySlider.setBlockIncrement(1);
		memorySlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    memorySlider.setValue(Math.round(new_val.doubleValue()));
            }
        });
		this.memorySlider.valueProperty().addListener(new ChangeListener<Number>() {
	         public void changed(ObservableValue<? extends Number> observable,
	               Number oldValue, Number newValue) {
	        	 memorySliderLabel.setText(newValue + "Gb");
	         }
	      });
        Platform.runLater(new Runnable() {
            public void run() {
            	 root.getChildren().add(memorySlider);
            }
        });
		
		this.memorySliderLabel.setText(this.memorySlider.getValue() + "Gb");
		
		/** ===================== VM ARGUMENTS TEXTFIELD ===================== */
		this.vmArguments = new LauncherTextField(root);
		this.vmArguments.setText((String) pane.config.getValue("vmarguments"));
		this.vmArguments.setSize(390, 20);
		this.vmArguments.setPosition(50, 195);
		/** ===================== CHECKBOX USE VM ARGUMENTS ===================== */
		this.useVMArguments = new CheckBox();
		this.useVMArguments.setText("Utiliser les Arguments JVM");
		this.useVMArguments.setSelected((Boolean)pane.config.getValue("usevmarguments"));
		this.useVMArguments.setOpacity(1.0);
		this.useVMArguments.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.useVMArguments.setStyle("-fx-text-fill: white;");
		this.useVMArguments.setLayoutX(50);
		this.useVMArguments.setLayoutY(165);
		this.useVMArguments.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (useVMArguments.isSelected()) {
					vmArguments.setDisable(false);
				}
				else {
					vmArguments.setDisable(true);
				}
			}
		});
		root.getChildren().add(useVMArguments);
		this.vmArguments.setDisable(!this.useVMArguments.isSelected());
		/** ===================== AUTO LOGIN CHECK BOX ===================== */
		this.autoLogin = new CheckBox();
		this.autoLogin.setText("Connexion auto (crack)");
		this.autoLogin.setSelected((Boolean)pane.config.getValue("autologin"));
		this.autoLogin.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.autoLogin.setStyle("-fx-text-fill: white;");
		this.autoLogin.setLayoutX(50);
		this.autoLogin.setLayoutY(240);
		root.getChildren().add(autoLogin);
		
		/** ===================== BOUTON DE VALIDATION ===================== */
		this.saveButton = new LauncherButton(root);
		this.saveButton.setText("Valider");
		this.saveButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.saveButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.saveButton.setPosition(310, 230);
		this.saveButton.setSize(130, 35);
		this.saveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				HashMap<String, String> configMap = new HashMap<String, String>();
				configMap.put("allocatedram", String.valueOf(memorySlider.getValue()));
				configMap.put("gamesize", "" + GameSize.getWindowSize(windowsSizeList.getValue()));
				configMap.put("autologin", "" + autoLogin.isSelected());
				configMap.put("usevmarguments", "" + useVMArguments.isSelected());
				configMap.put("vmarguments", "" + vmArguments.getText());
				pane.config.updateValues(configMap);
				engine.reg(GameMemory.getMemory(Double.parseDouble((String) pane.config.getValue("allocatedram"))));
				engine.reg(GameSize.getWindowSize(Integer.parseInt((String) pane.config.getValue("gamesize"))));
				Stage stage = (Stage)((LauncherButton)event.getSource()).getScene().getWindow();
				stage.close();
			}
		});
	}

	private void populateSizeList() {
		for (GameSize size : GameSize.values()) {
			this.windowsSizeList.getItems().add(size.getDesc());
		}
	}
}
