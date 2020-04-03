package fr.trxyy.launcher.template;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameSize;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import fr.trxyy.alternative.alternative_api_ui.IScreen;
import fr.trxyy.alternative.alternative_api_ui.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.LauncherRectangle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	public LauncherSettings(Pane root, GameEngine engine, LauncherPanel pane) {
		this.drawBackgroundImage(engine, root, "background.png");
		pane.userConfig.readConfig();
		engine.reg(pane.userConfig.convertMemory(pane.userConfig.getMemory()));
		engine.reg(pane.userConfig.getWindowSize(pane.userConfig.getWindowSize()));
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
		this.windowsSizeList = new ComboBox<>();
		this.populateSizeList();
		if (pane.userConfig.getWindowSize() != null) {
			this.windowsSizeList.setValue(pane.userConfig.getWindowSize());
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
		if (pane.userConfig.getRamString() != null) {
			this.memorySlider.setValue(pane.userConfig.getRam());
		}
		this.memorySlider.setLayoutX(50);
		this.memorySlider.setLayoutY(140);
		this.memorySlider.setPrefWidth(395);
		this.memorySlider.setBlockIncrement(1);
		this.memorySlider.valueProperty().addListener((obs, oldval, newVal) ->
		this.memorySlider.setValue(Math.round(newVal.doubleValue())));
		this.memorySlider.valueProperty().addListener(new ChangeListener<Number>() {
	         @Override
	         public void changed(ObservableValue<? extends Number> observable,
	               Number oldValue, Number newValue) {
	        	 memorySliderLabel.setText(newValue + "Gb");
	         }
	      });
		Platform.runLater(() -> root.getChildren().add(this.memorySlider));
		
		this.memorySliderLabel.setText(this.memorySlider.getValue() + "Gb");
		
		/** ===================== BOUTON DE VALIDATION ===================== */
		this.saveButton = new LauncherButton(root);
		this.saveButton.setText("Valider");
		this.saveButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.saveButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.saveButton.setPosition(190, 170);
		this.saveButton.setSize(130, 35);
		this.saveButton.setAction(eventAction -> {
			pane.userConfig.writeConfig("" + this.memorySlider.getValue(), this.windowsSizeList.getValue());
			Logger.log("" + this.memorySlider.getValue() + " " + this.windowsSizeList.getValue());
			engine.reg(pane.userConfig.getMemory(this.memorySlider.getValue()));
			engine.reg(pane.userConfig.getWindowSize(this.windowsSizeList.getValue()));
			Stage stage = (Stage)((LauncherButton)eventAction.getSource()).getScene().getWindow();
			stage.close();
		});
	}

	private void populateSizeList() {
		for (GameSize ver : GameSize.values()) {
			this.windowsSizeList.getItems().add(ver.getDesc());
		}
	}
}
