import java.util.*;

public class Connect4{

    final int COMPUTERCHIP = 2;
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

    public int [][] getGrid(){
        return grid;
    }

    public int [] getCol(){
        return col;
    }

    public boolean insert(int[][] board, int column, int [] cols, int t){
        boolean won = false;
        if(turn){
            board[cols[column]][column] = 1;
            won = gameWon(1);
        }else{
            board[cols[column]][column] = 2;
            won = gameWon(2);
        }
        cols[column]--;
        t++;
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

    public int howMany(int [] row){
        int count = 0;
        for(int i : row){
            if(i == 2){
                count++;
            }
        }
        return count;
    }

    public int scoreBoard(int [][] board){
        int score = 0;
        for(int [] rows : board){
            int twosCount = 0;
            int maxCount = 0;
            for(int i = 0; i < 4; i++){
                twosCount = howMany(Arrays.copyOfRange(rows, i, i+4));
                if(twosCount > maxCount) maxCount = twosCount;
            }
            if(maxCount == 4) score+=100;
            if(maxCount == 3) score+=75;
            if(maxCount == 2) score+=50;
        }
        return score;
    }

    public int generateMove(int [][] board){
        int column = 0;
        int maxScore = 0;
        for(int colm : col){
            if(colm != -1){
                int [][] tempGrid = generateCopyOfGrid(board);
                int [] tempColm = Arrays.copyOf(col, 7);
                int tempTurns = turns;
                insert(tempGrid, colm, tempColm, tempTurns);
                int value = scoreBoard(tempGrid);
                if(value > maxScore){
                    maxScore = value;
                    column = colm;
                }
            }
        }
        return column;
    }

    public int[][] generateCopyOfGrid(int [][] board){
        int[][] res = new int[7][7];
        for(int i = 0; i < board.length; i++){
            res[i] = Arrays.copyOf(board[i], 7);
        }
        return res;
    }

    public void display(){
        for(int[] row : grid){
            System.out.println(Arrays.toString(row));
        }
    }
}