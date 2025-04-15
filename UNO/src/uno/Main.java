package uno;

import model.user.User;
import util.session.CurrentUserManager;
import view.LoginPageView;
import view.MainMenu;

/**
 * The entry point of the Uno application. This class is responsible for
 * initializing the application and managing user sessions.
 */
public class Main {

	/***************************** Pledge of Honor ***************************
	 * I hereby certify that I have completed this programming project on my own 
	 * without any help from anyone else. The effort in the project thus belongs 
	 * completely to me. I did not search for a solution, or I did not consult 
	 * any program written by others or did not copy any program from other sources.
	 * I read and followed the guidelines provided in the project description. 
	 						 <Aykhan Ahmadzada, 86004>
	 *************************************************************************/

	/**
	 * The main method of the Uno application. It loads user data, checks if a user
	 * is logged in, and displays the appropriate view (login page or main menu). It
	 * also registers a shutdown hook to save user data when the application exits.
	 *
	 * @param args The command-line arguments (not used in this application).
	 */
	public static void main(String[] args) {
		// Load user data
		CurrentUserManager.getInstance().loadUserData();

		// Check if user is logged in
		if (CurrentUserManager.getInstance().isLoggedIn()) {
			User currentUser = CurrentUserManager.getInstance().getCurrentUser();
			System.out.println("Welcome back, " + currentUser.getUsername() + "!");
			new MainMenu(); // Display the main menu
		} else {
			System.out.println("No user logged in.");
			new LoginPageView(); // Display the login page
		}

		System.out.println("Program ended!");

		// Save user data when the application exits
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			CurrentUserManager.getInstance().saveUserData(CurrentUserManager.getInstance().getCurrentUser());
		}));
	}
}
