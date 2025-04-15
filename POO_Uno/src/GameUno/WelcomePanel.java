package GameUno;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class WelcomePanel extends PanelGBLayout {

	private String IconPath;

	WelcomePanel(JFrame MainWindow) {
		super(MainWindow);
		this.IconPath = this.getDirPath() + "\\src\\resources\\Icon.png";

		// Initializing Components
		JPanel Spacer = new JPanel();
		Button newGameBtn = new Button("Start a new Game");
		Button loadOldGameBtn = new Button("Load old Game results");
		JLabel imgLabel = new JLabel(new ImageIcon(IconPath));

		// Button Click Events
		newGameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGameEv();
			}
		});

		loadOldGameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadOldEv();
			}
		});

		// Setting up Layout and Component Placement:
		this.setGBC(0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		// Adding the Image
		this.add(imgLabel, this.getGBC());

		// Adding the buttons with spacing
		this.setGBC(0, 1, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		newGameBtn.setBackground(new Color(34, 193, 195));
		newGameBtn.setFont(new Font("Arial", Font.BOLD, 14));
		newGameBtn.setForeground(Color.WHITE);
		this.add(newGameBtn, this.getGBC());

		this.setGBC(0, 2, 1, 1, 0, 0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		loadOldGameBtn.setBackground(new Color(255, 159, 28));
		loadOldGameBtn.setFont(new Font("Arial", Font.BOLD, 14));
		loadOldGameBtn.setForeground(Color.WHITE);
		this.add(loadOldGameBtn, this.getGBC());

		// Adding Spacer to push buttons to the top
		this.getGBC().weighty = 0.5;
		this.setGBC(0, 3);
		this.add(Spacer, this.getGBC());
	}

	private void newGameEv() {
		if (this.getMainWindow() != null) {
			this.getMainWindow().remove(this);
			this.getMainWindow().add(new newGamePanel(getMainWindow()));
			this.getMainWindow().revalidate();
			this.getMainWindow().repaint();
		}
	}

	private void loadOldEv() {
		System.out.println("not set yet");
	}

}
