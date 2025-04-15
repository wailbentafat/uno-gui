package GameUno;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class newGamePanel extends PanelGBLayout {
	private String[] Choices = {
			"2 players", "3 players", "4 players", "5 players", "6 players",
			"7 players", "8 players", "9 players"
	};
	int CurrentPlayer;

	newGamePanel(JFrame MainWindow) {
		super(MainWindow);

		// Initializing components
		Button backMainBtn = new Button("Back To Main Menu");
		Button soloGameBtn = new Button("Solo");
		Button multyPlayer = new Button("MultiPlayer");
		JComboBox<String> numberOfPlayers = new JComboBox<>(Choices);

		this.CurrentPlayer = numberOfPlayers.getSelectedIndex() + 2;
		numberOfPlayers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CurrentPlayer = numberOfPlayers.getSelectedIndex()+2;
			}
		});
		// Styling the Buttons
		backMainBtn.setBackground(new Color(255, 215, 0)); // Gold color
		backMainBtn.setFont(new Font("Arial", Font.BOLD, 14));
		backMainBtn.setForeground(Color.BLACK);

		soloGameBtn.setBackground(new Color(211, 211, 211)); // Light gray
		soloGameBtn.setFont(new Font("Arial", Font.BOLD, 14));
		soloGameBtn.setForeground(Color.BLACK);

		multyPlayer.setBackground(new Color(211, 211, 211)); // Light gray
		multyPlayer.setFont(new Font("Arial", Font.BOLD, 14));
		multyPlayer.setForeground(Color.BLACK);

		// Button click events
		soloGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoloEv();
			}

		});

		backMainBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BackEv();
			}
		});

		multyPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MultiEv();
			}
		});

		// Adding Spacers for layout control
		this.setGBC(0, 0, 3, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		this.add(new JPanel(), this.getGBC());

		this.setGBC(0, 1, 1, 6);
		this.add(new JPanel(), this.getGBC());

		this.setGBC(1, 5, 2, 1);
		this.add(new JPanel(), this.getGBC());

		this.setGBC(2, 1, 1, 4);
		this.add(new JPanel(), this.getGBC());

		// Adding the components to the panel
		this.setGBC(1, 1, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
		this.add(backMainBtn, this.getGBC());

		this.setGBC(1, 2, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		this.add(soloGameBtn, this.getGBC());

		this.setGBC(1, 4, 1, 1);
		this.add(multyPlayer, this.getGBC());

		this.setGBC(1, 3, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH);
		this.add(numberOfPlayers, this.getGBC());
	}

	void SoloEv() {
		if (this.getMainWindow() != null) {
			this.getMainWindow().remove(this);
			this.getMainWindow().add(new BotPlay(getMainWindow()));
			this.getMainWindow().revalidate();
			this.getMainWindow().repaint();
		}
	}
	void BackEv() {
		if (this.getMainWindow() != null) {
			this.getMainWindow().remove(this);
			this.getMainWindow().add(new WelcomePanel(getMainWindow()));
			this.getMainWindow().revalidate();
			this.getMainWindow().repaint();
		}
	}

	void MultiEv() {
		if (this.getMainWindow() != null) {
			this.getMainWindow().remove(this);
			this.getMainWindow().add(new MultiPlayer(getMainWindow(), this.CurrentPlayer));
			this.getMainWindow().revalidate();
			this.getMainWindow().repaint();
		}
	}
}
