package main;

import game.GameEngine;
import java.util.Scanner;
import strategy.DealerStrategy;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Blackjack Menu ===");
            System.out.println("1. Start new game");
            System.out.println("2. Show statistics");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1": playGame(scanner); break;
                case "2": StatsManager.getInstance().showStats(); break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void playGame(Scanner scanner) {
        GameEngine game = new GameEngine();
        game.setDealerStrategy(new DealerStrategy());
        String result = null;

        while (true) {
            System.out.println("\nPlayer hand: " + game.getPlayer().getHand() + " (score: " + game.getPlayer().getScore() + ")");
            System.out.print("Do you want to hit or stand? (h/s): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("h")) {
                game.playerHit();
                if (game.getPlayer().isBusted()) {
                    System.out.println("\nPlayer busted!");
                    printFinalHands(game);
                    result = "Dealer wins (player busted)";
                    System.out.println(result);
                    break;
                }
            } else if (choice.equalsIgnoreCase("s")) {
                game.dealerPlay();
                printFinalHands(game);
                result = game.determineWinner();
                System.out.println(result);
                break;
            } else {
                System.out.println("Invalid input. Type 'h' or 's'.");
            }
        }

        StatsManager.getInstance().saveResult(result);
    }

    private static void printFinalHands(GameEngine game) {
        System.out.println("\nFinal Player hand: " + game.getPlayer().getHand() + " (score: " + game.getPlayer().getScore() + ")");
        System.out.println("Final Dealer hand: " + game.getDealer().getHand() + " (score: " + game.getDealer().getScore() + ")");
    }
}