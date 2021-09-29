import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Connect4Game{
    public static void main(String[] args) throws InterruptedException{
        Connect4 game = new Connect4();
        System.out.println("------- Welcome to Connect4 -------");
        Scanner sc = new Scanner(System.in);
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
            boolean isWon = false;
            game.display();
            System.out.println("Here is the current game board");
            if(game.turn){
                System.out.println("Enter which column would you like to place a piece (0-6 inclusive): ");
                int columnP = sc.nextInt();
                if(columnP < 0 || columnP > 6){
                    System.out.println("Invalid Input, Try Again");
                }else if(game.col[columnP] == -1){
                    System.out.println("Column is Full, Try Again");
                }else{
                    isWon = game.insert(game.getGrid(), columnP, game.getCol(), game.turns);
                }
            }else{
                
                System.out.println("Computer is Calculating Best Move");
                TimeUnit.SECONDS.sleep(5);
                int columnC = 0;
                if(game.turns == 1 || game.turns == 0){
                    columnC = ThreadLocalRandom.current().nextInt(0, 7);
                }else{
                    columnC = game.generateMove(game.getGrid());
                }
                isWon = game.insert(game.getGrid(), columnC, game.getCol(), game.turns);
            }
            if(isWon) break;
            if(game.turns >= 49) break;
            game.turn = !game.turn;
        }
        if(game.turns > 49 && !game.gameWon(1) && !game.gameWon(2)){
            System.out.println("There was no winner, game board is full");
        }else if(game.turn == true){
            System.out.println("Computer Loses, Player has Won");
        }else{
            System.out.println("Player Lost, Computer Won");
        }
        game.display();
        sc.close();
    }
}