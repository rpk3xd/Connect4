import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Connect4Game{
    public static void main(String[] args) throws InterruptedException{
        Connect4 game = new Connect4();
        System.out.println("------- Welcome to Connect4 -------");
        Scanner sc = new Scanner(System.in);
        int d = 1;
        while(true){
            System.out.println("Please choose who goes first: Player(p) or Computer(c)");
            String first = sc.nextLine();
            if(first.equals("p")){
                game.turn = true;
                break;
            }else if(first.equals("c")){
                game.turn = false;
                break;
            }else{
                System.out.println("Invalid Input, Try Again");
            }
        }
        while(true){
            System.out.println("Please choose your difficulty: Hard (h) or Easy (e)");
            String diff = sc.nextLine();
            if(diff.equals("h")){
                d = 7;
                break;
            }else if(diff.equals("e")){
                d = 1;
                break;
            }else{
                System.out.println("Invalid Input, Try Again");
            }
        }
        while(true){
            boolean isWon = false;
            game.display(game.getGrid());
            System.out.println("Here is the current game board");
            if(game.turn){
                System.out.println("Enter which column would you like to place a piece (0-6 inclusive): ");
                int columnP = sc.nextInt();
                if(columnP < 0 || columnP > 6){
                    System.out.println("Invalid Input, Try Again");
                }else if(game.col[columnP] == -1){
                    System.out.println("Column is Full, Try Again");
                }else{
                    isWon = game.insert(game.getGrid(), columnP, game.getCol(), 1);
                    game.turns++;
                }
            }else{
                System.out.println("Computer is Calculating Best Move");
                //TimeUnit.SECONDS.sleep();
                int column = game.minimax(game.getGrid(),game.getCol(), d, true)[0];
                if(column == -1){
                    break;
                }
                isWon = game.insert(game.getGrid(), column, game.getCol(), 2);
                game.turns++;
            }
            if(isWon) break;
            if(game.turns > 49) break;
            game.turn = !game.turn;
        }
        if(game.turns >= 49 && !game.gameWon(game.getGrid(), 1) && !game.gameWon(game.getGrid(), 2)){
            System.out.println("There was no winner, game board is full");
        }else if(game.turn == true){
            System.out.println("Computer Loses, Player has Won");
        }else{
            System.out.println("Player Lost, Computer Won");
        }
        game.display(game.getGrid());
        sc.close();
    }
}