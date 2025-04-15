package Logic;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Please Enter How Many Human-Players : ");
        Scanner sc = new Scanner(System.in);
        int numberOfHumanPlayers = sc.nextInt();
        System.out.print("Please Enter How Many Bot-Players : ");
        int numberOfBotPlayers = sc.nextInt();
        Game game = new Game(numberOfHumanPlayers + numberOfBotPlayers ,numberOfHumanPlayers);
        while (!game.isGameOver()) {
            game.executeTurn();
        }

    }
}
