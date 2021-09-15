import java.util.*;
public class App {
    public static int [][] grid = new int[7][7];
    public static void main(String[] args) throws Exception {
        //insert "empty" slots which are represented by -1
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                grid[i][j] = -1;
            }
        }
        for(int[] row : grid){
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
        //True signifies player first, False signifies computer first
        boolean turn = input();
        int turns = 1;
        insert(turn, turns);

    } 
    public static boolean input(){
        System.out.println("Player 1: player(p) or computer(c)");
        Scanner sc = new Scanner(System.in);
        String first = sc.nextLine();
        sc.close();
        if(first.equals("p")){
            return true;
        }else if(first.equals("c")){
            return false;
        }else{
            System.out.println("Invalid input, Try again");
            return input();
        }
    }
    public static void display(){
        for(int[] row : grid){
            System.out.println(Arrays.toString(row));
        }
    }
    public static void insert(boolean turn, int turns){
        display();
        if(turns > 49){
            System.out.println("Game Board is Full");
        }else{
            if(turn){
                System.out.println("Column: ");
                Scanner sc = new Scanner(System.in);
                int col = sc.nextInt();
                sc.close();
                if(col < 0 || col > 6){
                    System.out.println("Value is out of range, Try Again");
                    insert(turn, turns);
                }else{
                    int bottomOfCol = bottom(col);
                    if(bottomOfCol == -1){
                        System.out.println("Column is full, cannot place here, Try Again");
                        insert(turn, turns);
                    }else{
                        grid[bottomOfCol][col] = 1;
                        insert(false, turns+=1);
                    }
                }
            }else{
                int compCol = (int) Math.random()*7;
                int bottomOfCol = bottom(compCol);
                if(bottomOfCol == -1){
                    insert(turn, turns);
                }else{
                    grid[bottomOfCol][compCol] = 0;
                    insert(true, turns+=1);
                }
            }
        }  
    }
    public static int bottom(int col){
        if(grid[0][col] != -1) return -1;
        int res = 0;
        for(int i = 0; i < 7; i++){
            if(grid[i][col] != -1){
                return i-1;
            }
            res = i;
        }
        return res;
    }
}
