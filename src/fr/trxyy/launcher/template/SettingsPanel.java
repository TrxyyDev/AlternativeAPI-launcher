package fr.trxyy.launcher.template;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import fr.trxyy.alternative.alternative_apiv2.base.GameEngine;
import fr.trxyy.alternative.alternative_apiv2.settings.GameInfos;
import fr.trxyy.alternative.alternative_apiv2.settings.GameSaver;

public class SettingsPanel extends JPanel implements ActionListener {

	public JLabel titleLabel;
	private Font stratumFont;
	
	public JLabel resolutionLabel, vmLabel;
	public JTextField resolutionField, vmField;
	private JButton saveSettingsButton;
	private GameEngine engine;

	public SettingsPanel(GameEngine engin) {
		this.engine = engin;
		this.setLayout(null);
		this.setBackground(new Color(84, 89, 87, 200));

		InputStream stream2 = LauncherPanel.class.getClass().getResourceAsStream("/resources/StratumM.ttf");
		try {
			stratumFont = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(30f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GameSaver saver = new GameSaver(engine);
		GameInfos savedInfos = saver.readConfig();

		this.titleLabel = new JLabel();
		this.titleLabel.setText("Paramètres du launcher");
		this.titleLabel.setForeground(Color.black);
		this.titleLabel.setFont(stratumFont.deriveFont(35f));
		this.titleLabel.setBounds(17, 5, 350, 45);
		this.add(this.titleLabel);
		
		this.resolutionField = new JTextField();
		this.resolutionField.setForeground(Color.black);
		this.resolutionField.setBackground(new Color(255, 255, 255, 255));
		this.resolutionField.setBorder(null);
		this.resolutionField.setText(savedInfos.getResolution());
		this.resolutionField.setHorizontalAlignment(JTextField.CENTER);
		this.resolutionField.setBounds(20, 80, 130, 20);
		this.resolutionField.setFont(stratumFont.deriveFont(15f));
		this.add(this.resolutionField);
		
		this.resolutionLabel = new JLabel();
		this.resolutionLabel.setText("Resolution (WxH)");
		this.resolutionLabel.setForeground(Color.black);
		this.resolutionLabel.setFont(stratumFont.deriveFont(20f));
		this.resolutionLabel.setBounds(17, 40, 350, 45);
		this.add(this.resolutionLabel);
		
		this.vmLabel = new JLabel();
		this.vmLabel.setText("Arguments VM");
		this.vmLabel.setForeground(Color.black);
		this.vmLabel.setFont(stratumFont.deriveFont(20f));
		this.vmLabel.setBounds(20, 100, 350, 45);
		this.add(this.vmLabel);
		
		this.vmField = new JTextField();
		this.vmField.setForeground(Color.black);
		this.vmField.setBackground(new Color(255, 255, 255, 255));
		this.vmField.setBorder(null);
		this.vmField.setText(savedInfos.getVmArguments());
		this.vmField.setHorizontalAlignment(JTextField.CENTER);
		this.vmField.setBounds(20, 140, 575, 20);
		this.add(this.vmField);
		
		this.saveSettingsButton = new JButton("Sauvegarder");
		this.saveSettingsButton.setForeground(Color.BLACK);
		this.saveSettingsButton.setBounds(440, 100, 150, 30);
		this.saveSettingsButton.setFont(stratumFont.deriveFont(20F));
		this.saveSettingsButton.addActionListener(this);
		this.add(this.saveSettingsButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.saveSettingsButton)) {
			GameInfos gameInfo = new GameInfos();
			gameInfo.setResolution(resolutionField.getText());
			gameInfo.setVmArguments(vmField.getText());
			GameSaver gameSaver = new GameSaver(gameInfo, this.engine);
			gameSaver.saveSettings();
			JDialog topFrame = (JDialog) SwingUtilities.getWindowAncestor(this);
			topFrame.dispose();
		}
	}
}
