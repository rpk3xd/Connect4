import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.io.*;
import com.fazecast.jSerialComm.*;

public class Connect4Game{
    public static void main(String[] args) throws InterruptedException, IOException{
        Connect4 game = new Connect4();
        System.out.println("------- Welcome to Connect4 -------");
        int d = 1;

        // Initializes the map for human column moves
        Map<Character, Integer> human = Map.of(
            'h', 0,
            'i', 1,
            'j', 2,
            'k', 3,
            'l', 4,
            'm', 5,
            'n', 6
        );

        //Initializes the map for robot column moves
        Map<Integer, Character> robot = Map.of(
            0, 'p',
            1, 'q',
            2, 'r',
            3, 's',
            4, 't',
            5, 'u',
            6, 'v'
        );

        // Open SerialPort for serial communication with the MSP
        SerialPort sp = SerialPort.getCommPort("COM6");
        sp.openPort();
        sp.setBaudRate(115200);
        sp.setComPortTimeouts(4096, 10000000, 10000000);
        //Obtains output stream from the SerialPort
        OutputStream os = sp.getOutputStream();

        //Creates the Scanner that will read in from the command line
        Scanner sc = new Scanner(System.in);

        //Creates the Scanner that takes in input from the Comm Port
        Scanner sc2 = new Scanner(sp.getInputStream());

        while(true){

            /**
             * Asks the user to determine who will go first: Human or Robot
             * will continue to ask for the proper input until it receives either an h or r
             */

            System.out.println("Please choose who goes first: Human(h) or Robot(r)");
            String first = sc.nextLine();
            if(first.equals("h")){

                //Write to the Comm port that the human will go first
                byte [] a = {'G'};
                sp.writeBytes(a, 1);

                game.turn = true;
                break;

            }else if(first.equals("r")){
                
                //Write to the Comm port that the robot will go first
                byte [] a = {'@'};
                sp.writeBytes(a, 1);
                
                game.turn = false;
                break;

            }else{

                // Will continue to try and receive proper input from the user
                System.out.println("Invalid Input, Try Again");
            }

        }
        while(true){

            /** 
             * Asks the user to determine the level of difficulty, hard or easy
             */

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
        /**
         * BEGIN THE GAME
         */
        while(true){

            boolean isWon = false;
            game.display(game.getGrid());
            System.out.println("Here is the current game board");
            
            //If it is the Humans turn

            if(game.turn){
                
                //Take in string of column values
                byte [] humansTurn = new byte [22];
                int index = 0;
                System.out.println(sp.readBytes(humansTurn, 1));
                char ch = (char)humansTurn[index];
                int c = human.get(ch);
                index++;

                if(c < 0 || c > 6){
                    System.out.println("Invalid Input, Try Again");
                }else if(game.col[c] == -1){
                    System.out.println("Column is Full, Try Again");
                }else{
                    isWon = game.insert(game.getGrid(), c, game.getCol(), 1);
                    game.turns++;
                }
            }
            //If it is the Robots turn
            else
            {
                System.out.println("Computer is Calculating Best Move");
                //TimeUnit.SECONDS.sleep();
                int column = game.minimax(game.getGrid(),game.getCol(), d, true)[0];
                if(column == -1){
                    break;
                }
                char columnToMSP = robot.get(column);
                byte [] b = {(byte)columnToMSP};
                sp.writeBytes(b, 1);
                
                isWon = game.insert(game.getGrid(), column, game.getCol(), 2);
                game.turns++;
            }
            if(isWon) break;
            if(game.turns >= 49) break;
            byte [] c = {'H'};
            sp.writeBytes(c, 1);
            System.out.println("Sent");
            game.turn = !game.turn;
        }
        if(game.turns >= 49 && !game.gameWon(game.getGrid(), 1) && !game.gameWon(game.getGrid(), 2)){
            System.out.println("There was no winner, game board is full");
        }else if(game.turn == true){
            System.out.println("Computer Loses, Player has Won");
        }else{
            System.out.println("Player Lost, Computer Won");
        }
        byte [] e = {'O'};
        sp.writeBytes(e, 1);
        os.close();
        sp.closePort();
        game.display(game.getGrid());
        sc.close();
    }
}