import java.util.*;

public class Connect4{

    int [][] grid;
    int [] col;
    int turns;
    boolean turn;
    public Connect4(){
        grid = new int[7][7];
        col = new int[7];
        turns = 0;
        //Initialize
        for(int i = 0; i < 7; i++){
            col[i] = 6;
            for(int j = 0; j < 7; j++){
                grid[i][j] = 0;
            }
        }
    }

    public boolean insert(int column){
        boolean won = false;
        if(turn){
            grid[col[column]][column] = 1;
            won = gameWon(1);
        }else{
            grid[col[column]][column] = 2;
            won = gameWon(2);
        }
        col[column]--;
        turns++;
        return won;
    }

    //false if game has not been won
    public boolean gameWon(int playerOrComputer){
        //implement function to check if game has been won
        //Horizontal
        for(int x = 0; x < 4; x ++){
            for(int y = 0; y < 7; y++){
                if(grid[y][x] == playerOrComputer && grid[y][x+1] == playerOrComputer && grid[y][x+2] == playerOrComputer && grid[y][x+3] == playerOrComputer){
                    return true;
                }
            }
        }
        //Vertical
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 7; x++){
                if(grid[y][x] == playerOrComputer && grid[y+1][x] == playerOrComputer && grid[y+2][x] == playerOrComputer && grid[y+3][x] == playerOrComputer){
                    return true;
                }
            }
        }
        //Diagnol Increasing/Decreasing
        for(int x = 3; x < 7; x++){
            for(int y = 3; y < 7; y++){
                if(grid[y][x] == playerOrComputer && grid[y-1][x-1] == playerOrComputer && grid[y-2][x-2] == playerOrComputer && grid[y-3][x-3] == playerOrComputer){
                    return true;
                }
            }
        }
        for(int y = 3; y < 7; y++){
            for(int x = 0; x < 4; x++){
                if(grid[y][x] == playerOrComputer && grid[y-1][x+1] == playerOrComputer && grid[y-2][x+2] == playerOrComputer && grid[y-3][x+3] == playerOrComputer){
                    return true;
                }
            }
        }
        return false;
    }
    

    public void display(){
        for(int[] row : grid){
            System.out.println(Arrays.toString(row));
        }
    }


}