package Logic;


import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(int id) {
        super(id);
    }

    @Override
    public Card.Colors chooseColor() { // a function that make the user choose a color if the played a wilD Card
        Scanner scanner = new Scanner(System.in);
        System.out.println("PLAYER " + this.getId() + " Choose A Color Red Or Green Or Blue");
        return Card.Colors.valueOf(scanner.nextLine().toUpperCase() /* read a value from the user transform it to uppercase to mach the values of the Colors enum and give it to the array that hold the enum Values*/);
    }
    @Override
    public Card chooseCard(Card lastPlayableCard) { // a function that prompt the user to select one card from his Playable Cards
        ArrayList<Card> playableCards = this.getPlayableCards(lastPlayableCard); // get player PlayableCards
        System.out.println("Your Playable Cards :");
        for (int i = 0; i < playableCards.size(); i++) { // looping on cards
            String color = playableCards.get(i).getColor().toString(); // getting the card color
            String symbol = playableCards.get(i) instanceof ActionCard // this variable will hold the symbol written on the card this symbol can be a value from the actions xor a value from the numbers [0,9]
                    ? /* if the card is an action card so get the action of the card */ ((ActionCard) (playableCards.get(i))).getAction().name()
                    :/* else (valueCard) get the value of the card which mean the number*/ String.valueOf(((ValueCard) (playableCards.get(i))).getValue());
            System.out.println("+----" + i + "----+");
            System.out.printf("| %-10s |\n", playableCards.get(i).getColor()); // printing the color inside the card
            System.out.println("|          |");
            System.out.printf("|    %-5s  |\n", symbol); // printing the symbol inside the card
            System.out.println("|          |");
            System.out.println("+----------+");
        }
        System.out.println("Choose A Card From Your Playable Cards :");
        int choice;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose A Card By Its Number (0 to " + (playableCards.size() - 1) + ")");
            choice = scanner.nextInt(); // getting the number of the card that the player want to play
            if (choice >= 0 && choice <= playableCards.size() - 1) { // checking if the player a valid number
                break; // if so break the loop
            }
            System.out.println("Invalid Choice. Try again.");
        }
        return playableCards.get(choice);
    }
}
