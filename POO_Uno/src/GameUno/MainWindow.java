package GameUno;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.*;

class MainWindow {
	private JFrame MainWindow;
	private String IconPath = System.getProperty("user.dir") + "\\src\\resources\\Icon.png";

	MainWindow() {
		MainWindow = new JFrame();
		MainWindow.setTitle("Uno Game");
		MainWindow.setResizable(false);
		MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon myImg = new ImageIcon(this.IconPath);
		WelcomePanel welcomePanel = new WelcomePanel(MainWindow);

		// Set the resources path for the WelcomePanel
		welcomePanel.setDirPath("C:\\Users\\wail\\IdeaProjects\\POO_Uno\\src\\resources\\");

		MainWindow.setIconImage(myImg.getImage());
		MainWindow.add(welcomePanel);

		MainWindow.setMinimumSize(new Dimension(500, 650));
		MainWindow.setSize(600, 650);
		MainWindow.setVisible(true);
	}
}
