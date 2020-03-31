package fr.trxyy.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameMemory;
import fr.trxyy.alternative.alternative_api.GameSize;
import fr.trxyy.alternative.alternative_api.utils.Logger;
import javafx.scene.control.ChoiceDialog;

public class LauncherSettings {
	private GameEngine engine;

	public LauncherSettings(GameEngine e) {
		this.engine = e;
		List<String> choices = new ArrayList<String>();
		for (int i = 0; i < GameMemory.values().length; i++) {
			choices.add(GameMemory.values()[i].getCount());
		}

		ChoiceDialog<String> dialog = new ChoiceDialog<String>(null, choices);
		dialog.setTitle("Reglages 1/2");

		dialog.setHeaderText("Parametres de Lancement");
		dialog.setContentText("RAM allouee: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			GameMemory mem = getConvert((String) result.get());
			engine.reg(mem);
			displaySecondOptions();
		}
		result.ifPresent(letter -> Logger.log("RAM: " + letter));
	}

	private void displaySecondOptions() {
		List<String> choices = new ArrayList<String>();
		for (int i = 0; i < GameSize.values().length; i++) {
			choices.add(GameSize.values()[i].getDesc());
		}

		ChoiceDialog<String> dialog = new ChoiceDialog<String>(null, choices);
		dialog.setTitle("Reglages 2/2");

		dialog.setHeaderText("Parametres de Lancement");
		dialog.setContentText("Taille fenetre: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			GameSize size = getConvertSize((String) result.get());
			this.engine.reg(size);
		}
		result.ifPresent(letter -> Logger.log("SIZE: " + letter));
	}

	private GameSize getConvertSize(String size) {
		if (size.equals("854x480")) {
			return GameSize.DEFAULT;
		} else if (size.equals("1024x768")) {
			return GameSize.SIZE_1024x768;
		} else if (size.equals("1280x1024")) {
			return GameSize.SIZE_1280x1024;
		} else if (size.equals("1366x768")) {
			return GameSize.SIZE_1366x768;
		} else if (size.equals("1600x900")) {
			return GameSize.SIZE_1600x900;
		} else if (size.equals("1920x1080")) {
			return GameSize.SIZE_1920x1080;
		} else if (size.equals("2560x1440")) {
			return GameSize.SIZE_2560x1440;
		}
		return GameSize.DEFAULT;
	}

	private GameMemory getConvert(String s) {
		if (s.equals("1G")) {
			return GameMemory.DEFAULT;
		} else if (s.equals("2G")) {
			return GameMemory.RAM_2G;
		} else if (s.equals("3G")) {
			return GameMemory.RAM_3G;
		} else if (s.equals("4G")) {
			return GameMemory.RAM_4G;
		} else if (s.equals("5G")) {
			return GameMemory.RAM_5G;
		} else if (s.equals("6G")) {
			return GameMemory.RAM_6G;
		} else if (s.equals("7G")) {
			return GameMemory.RAM_7G;
		} else if (s.equals("8G")) {
			return GameMemory.RAM_8G;
		} else if (s.equals("9G")) {
			return GameMemory.RAM_9G;
		} else if (s.equals("10G")) {
			return GameMemory.RAM_10G;
		}
		return GameMemory.DEFAULT;
	}
}
