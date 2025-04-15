package GameUno;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelGBLayout extends JPanel {

	private GridBagConstraints gbc;
	private String DirPath;
	private JFrame MainWindow;

	PanelGBLayout(JFrame Main) {
		this.setLayout(new GridBagLayout());
		this.gbc = new GridBagConstraints();
		this.DirPath = System.getProperty("user.dir") + "\\src\\resources"; // Set default path to include resources
		this.MainWindow = Main;
	}

	GridBagConstraints getGBC() {
		return this.gbc;
	}

	String getDirPath() {
		return DirPath;
	}

	void setDirPath(String dirPath) {
		this.DirPath = dirPath;
	}

	JFrame getMainWindow() {
		return this.MainWindow;
	}

	// GBC Setter Supercharge
	public void setGBC(int gx, int gy) {
		this.gbc.gridx = gx;
		this.gbc.gridy = gy;
	}

	public void setGBC(int gx, int gy, int width, int height) {
		this.gbc.gridx = gx;
		this.gbc.gridy = gy;
		this.gbc.gridwidth = width;
		this.gbc.gridheight = height;
	}

	public void setGBC(int gx, int gy, int width, int height, int weightX, int weightY) {
		this.gbc.gridx = gx;
		this.gbc.gridy = gy;
		this.gbc.gridwidth = width;
		this.gbc.gridheight = height;
		this.gbc.weightx = weightX;
		this.gbc.weighty = weightY;
	}

	public void setGBC(int gx, int gy, int width, int height, int weightX, int weightY, int fill, int anchor) {
		this.gbc.gridx = gx;        // Column (x-coordinate) of the grid
		this.gbc.gridy = gy;        // Row (y-coordinate) of the grid
		this.gbc.gridwidth = width; // How many columns the component spans
		this.gbc.gridheight = height; // How many rows the component spans
		this.gbc.weightx = weightX; // Horizontal resizing weight
		this.gbc.weighty = weightY; // Vertical resizing weight
		this.gbc.fill = fill;      // How the component should resize
		this.gbc.anchor = anchor;
	}

	public ImageIcon resizeIcon(String imagePath, int width, int height) {
		try {
			// Log the full file path for debugging
			System.out.println("Attempting to load image: " + imagePath);

			// Load the image
			File imageFile = new File(imagePath);
			if (!imageFile.exists()) {
				System.err.println("Error: Image file not found at path: " + imagePath);
				return null; // Return null if the file does not exist
			}

			BufferedImage originalImage = ImageIO.read(imageFile);
			// Resize the image
			Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			return new ImageIcon(resizedImage);
		} catch (IOException e) {
			System.err.println("Error reading image file: " + imagePath);
			e.printStackTrace();
			return null; // Return null if there's an error
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 600); // Match minimum size of JFrame
	}
}
