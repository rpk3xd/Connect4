import java.util.*;

public class Connect4{

    final int COMPUTERCHIP = 2;
    final int PLAYERCHIP = 1;
    final int COLNUM = 7;
    final int ROWNUM = 6;
    int [][] grid;
    int [] col;
    int turns;
    boolean turn;

    public Connect4(){
        grid = new int[ROWNUM][COLNUM];
        col = new int[COLNUM];
        turns = 0;
        //Initialize
        for(int i = 0; i < ROWNUM; i++){
            
            for(int j = 0; j < COLNUM; j++){
                grid[i][j] = 0;
                col[j] = 5;
            }
        }
    }

    public int [][] getGrid(){
        return grid;
    }
    public boolean insert(int[][] board, int column, int [] colms, int piece){
        boolean won = false;
        board[colms[column]][column] = piece;
        won = gameWon(board, piece);
        colms[column]--;
        return won;
    }
    public int [] getCol(){
        return col;
    }
    //false if game has not been won
    public boolean gameWon(int [][] board, int playerOrComputer){
        //implement function to check if game has been won
        //Horizontal
        for(int x = 0; x < COLNUM-3; x ++){
            for(int y = 0; y < ROWNUM; y++){
                if(board[y][x] == playerOrComputer && board[y][x+1] == playerOrComputer && board[y][x+2] == playerOrComputer && board[y][x+3] == playerOrComputer){
                    return true;
                }
            }
        }
        //Vertical
        for(int y = 0; y < ROWNUM-3; y++){
            for(int x = 0; x < COLNUM; x++){
                if(board[y][x] == playerOrComputer && board[y+1][x] == playerOrComputer && board[y+2][x] == playerOrComputer && board[y+3][x] == playerOrComputer){
                    return true;
                }
            }
        }
        //Diagnol Increasing/Decreasing
        for(int x = 0; x < COLNUM-3; x++){
            for(int y = 0; y < ROWNUM-3; y++){
                if(board[y][x] == playerOrComputer && board[y+1][x+1] == playerOrComputer && board[y+2][x+2] == playerOrComputer && board[y+3][x+3] == playerOrComputer){
                    return true;
                }
            }
        }
       
        for(int y = 3; y < ROWNUM; y++){
            for(int x = 0; x < COLNUM-3; x++){
                if(board[y][x] == playerOrComputer && board[y-1][x+1] == playerOrComputer && board[y-2][x+2] == playerOrComputer && board[y-3][x+3] == playerOrComputer){
                    return true;
                }
            }
        }
        return false;
    }

    public int howMany(int [] row, int chip){
        int count = 0;
        for(int i : row){
            if(i == chip){
                count++;
            }
        }
        return count;
    }
    public int scoring(int [] row, int zeroOrOne){
        int score = 0;
        int player = howMany(row, PLAYERCHIP);
        int computer = howMany(row, COMPUTERCHIP);
        int empty = howMany(row, 0);
        if(computer == 4){
            score += 50;
        }else if(computer == 3 && empty == 1){
            score += 5;
        }else if(computer == 2 && empty == 2){
            score += 2;
        }
        if(player == 3 && empty == 1){
            score -= 20;
        }

        return score;
    }
    public int scoreBoard(int [][] board, int chip){
        int score = 0;

        //Center column
        int [] center = {board[0][3], board[1][3], board[2][3], board[3][3], board[4][3], board[5][3]};
        int c = howMany(center, chip);
        score += (c * 5);
        //Horizontal
        //display(board);
        //System.out.println();
        
        for(int i = 0; i < ROWNUM; i++){
            for(int j = 0; j < COLNUM-3; j++){
                int [] a = {board[i][j], board[i][j+1], board[i][j+2], board[i][j+3]};
                score += scoring(a, chip);
            }
        }
        
        //Vertical
        for(int i = 0; i < ROWNUM-3; i++){
            for(int j = 0; j < COLNUM; j++){
                int [] a = {board[i][j], board[i+1][j], board[i+2][j], board[i+3][j]};
                score += scoring(a, chip);
            }
        }
        
        //Diagnal Increasing Decreasing
        for(int i = 0; i < ROWNUM-3; i++){
            for(int j = 0; j < COLNUM-3; j++){
                int [] a = {board[i][j], board[i+1][j+1], board[i+2][j+2], board[i+3][j+3]};
                score += scoring(a, chip);
            }
        }
        for(int i = 3; i < ROWNUM; i++){
            for(int j = 0; j < COLNUM-3; j++){
                int [] a = {board[i][j], board[i-1][j+1], board[i-2][j+2], board[i-3][j+3]};
                score += scoring(a, chip);
            }
        }
        //
        return score;
    }
    public boolean endNode(int [][] board, ArrayList<Integer> val){
        //no more valid moves
        return gameWon(board, PLAYERCHIP) || gameWon(board, COMPUTERCHIP) || (val.size() == 0);
    }
    //maximizingPlayer true when comp false when player
    public int[] minimax(int [][] board, int[] colmn, int depth, boolean maximizingPlayer){
        ArrayList<Integer> val = validLocations(board);
        boolean end = endNode(board, val);
        if(depth == 0 || end){
            if(end){
                if(gameWon(board, COMPUTERCHIP)){
                    return new int [] {-1, Integer.MAX_VALUE};
                }else if(gameWon(board, PLAYERCHIP)){
                    return new int [] {-1, Integer.MIN_VALUE};
                }else{
                    return new int [] {-1, 0};
                }
            }else{
                return new int [] {-1, scoreBoard(board, COMPUTERCHIP)};
            }
        }
        if(maximizingPlayer){
            int value = Integer.MIN_VALUE;
            int index = (int)Math.random() * val.size();
            int column = val.get(index);
            for(int i : val){
                int [][] tempGrid = generateCopyOfGrid(board);
                int [] tempCol = Arrays.copyOf(colmn, 7);
                insert(tempGrid, i, tempCol, COMPUTERCHIP);
                int score = minimax(tempGrid, tempCol, depth-1,false)[1];
                if(score > value){
                    value = score;
                    column = i;
                }
            }   
            return new int[] {column, value};
        }else{
            int value = Integer.MAX_VALUE;
            int index = (int)Math.random() * val.size();
            int column = val.get(index);
            for(int i : val){ 
                int [][] tempGrid = generateCopyOfGrid(board);
                int [] tempCol = Arrays.copyOf(colmn, 7);
                insert(tempGrid, i, tempCol, PLAYERCHIP);
                int score = minimax(tempGrid, tempCol, depth-1, true)[1];
                if(score < value){
                    value = score;
                    column = i;
                }        
            }
            return new int[] {column, value};
        }
    }
    public boolean valid(int [][] board, int column){
        if(board[0][column] != 0){
            return false;
        }
        return true;
    }
    public ArrayList<Integer> validLocations(int [][] board){
        ArrayList<Integer> val = new ArrayList<>();
        for(int i = 0; i < COLNUM; i ++){
            if(valid(board, i)){
                val.add(i);
            }
        }
        return val;
    }
    public int[][] generateCopyOfGrid(int [][] board){
        int[][] res = new int[ROWNUM][COLNUM];
        for(int i = 0; i < board.length; i++){
            res[i] = Arrays.copyOf(board[i], COLNUM);
        }
        return res;
    }
    public void display(int [][] board){
        for(int[] row : board){
            System.out.println(Arrays.toString(row));
        }
    }
}