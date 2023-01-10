import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static final int boardSize = 9;
    public static int old;
    public static final char PlayerX = 'X'; 
    public static final char Player0 = '0';
    public static final char nun = ' ';
    public static char chip; //assign X or O
    public static char AIchip;
    public static char []board = new char[boardSize];
    public static char curwin = 'N';
    public static char maxPlayer;
    public static int [] userRows = new int[boardSize/3]; //keep track of winning results
    public static int [] userCols = new int[boardSize/3];
    public static int [] aiRows = new int[boardSize/3];
    public static int [] aiCols = new int[boardSize/3];
    public static int [] userDiag = new int[(boardSize/3)];
    public static int [] aiDiag = new int[(boardSize/3)];
    public static Scanner user = new Scanner(System.in); 
    public static Random random = new Random();
    public static boolean empty = true; //check if board is empty for first move
    public static boolean done = false; //end game
    public static boolean turn = true; //select who goes first 
    public static HashMap <String,Integer> pos= new HashMap <String,Integer>();
    
    public static void main(String[] args) throws Exception {
        do{
        initialize();
        displayBoard();
        do{
            System.out.println("Would you like to be X or O ?"); 
            chip = user.next().charAt(0); 
            chip = Character.toUpperCase(chip); //incase user enters lowercase char
            if(chip == 'X' || chip == 'O') break;//must enter X or O
            else System.out.println("Invalid input. Please try again");; //leave loop if condition met
        }while(true);
        if(chip == 'X') AIchip = 'O'; //assign X and O to user and AI
        else AIchip = 'X';

        System.out.println("Great choice! Lets begin. You'll be asked to select a number (1-9) for each of your turns. Remember that numbers 1-9 correspond to each cell in left to right "); //explain game to user
        flip(); 
        do
        {   
            if(turn) //true if user wins coinflip
                Playerturn(chip); 

            if(boardFull()) {
                System.out.println("It's a tie!");
                break;
            } 
            turn = true; // set turn to true so AI can go first then we can move on at this order
            if(turn){ 
                if(AIturn(AIchip)){
                    System.out.println(AIchip + " Wins!");
                    break;
            }
            if(boardFull()){
                System.out.println("It's a tie!");
                break;
            }
        }
        }while(true); //game will break loop if AI wins or tie

        System.out.println("Would you like to play again? Select either 'Y' or 'N'. ");
        do{
            char reply = user.next().charAt(0);
            if(Character.toUpperCase(reply)=='N'){
                System.out.println("Better luck next time :)");
                done = true;
                break;
            }
            else if(Character.toUpperCase(reply)=='Y') break;
            else System.out.println("Invalid entry, select either 'Y' or 'N' ");
        }while(true);
    }while(!done);
    }
    public static void initialize(){ //for initializing and game reset
        Arrays.fill(board, nun);
        Arrays.fill(userRows, 0);
        Arrays.fill(userCols, 0);
        Arrays.fill(userDiag, 0);
        Arrays.fill(aiRows, 0);
        Arrays.fill(aiCols, 0);
        Arrays.fill(aiDiag, 0);
        turn = false;  
    }
    public static void displayBoard(){
        System.out.println();
        for(int i=0;i<2;i++){
            System.out.println(board[i*3] + "|" + board[i*3+1] + "|" + board[i*3+2]);
            System.out.println("-----");
        }
        System.out.println(board[6] + "|" + board[7] + "|" + board[8]);
        System.out.println();
    }
    public static void Playerturn(char piece){ //user 
        int move;
        do{
            System.out.println("Select a number (1-9) for your next move: ");
            try{
                move = user.nextInt();
                if(board[move-1]!=nun){
                    System.out.println("A piece already exists here...please choose a new square");
                    continue;
                }
                else break;
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Invalid entry.");
            }
            catch(InputMismatchException e){
                System.out.println("Invalid entry. Enter a valid number.");
                    user.next();
            }
        }while(true);
        addDelete(piece,move, 'a'); //add move to arrays 
        displayBoard();
    }
    public static boolean AIturn(char piece){ 
        System.out.println("You're opponent is taking its move");
        int move = getMove(piece);
        board[move-1]=piece;
        addDelete(piece, move, 'a');
        displayBoard();
        if(checkW(piece)) return true; //check if they've won
        else return false;
    }
    public static void addDelete(char piece, int move, char d){
        int i;
        if(d== 'a') i=1;
        else i= -1;
        if(piece==AIchip){
        aiRows[(move-1)/3] +=i;
            if(move == 1 || move == 4 || move == 7){
                aiCols [0] +=i;
                if(move==1)
                    aiDiag[0] +=i; //increment right diagnol array
                if(move==7)
                    aiDiag[1] +=i;
            }
            else if(move == 2 || move == 5 || move == 8){
                aiCols [1] +=i;
                if(move==5){
                    aiDiag[0] +=i;
                    aiDiag[1] +=i;
                }
            }
            else{ //if 3,6,9
                aiCols[2]+=i;
                if(move==3)
                    aiDiag[1]+=i;
                if(move==9)
                    aiDiag[0]+=i;
            }
        }
        else{
            board[move-1] = piece; 
            userRows[(move-1)/3] +=i ;
            if(move == 1 || move == 4 || move == 7){
                userCols [0] +=i;
                if(move==1)
                    userDiag[0] +=i; //increment right diagnol array
                if(move==7)
                    userDiag[1] +=i;
            }
            else if(move == 2 || move == 5 || move == 8){
                userCols [1] +=i;
                if(move==5){
                    userDiag[0] +=i;
                    userDiag[1] +=i;
                }
            }
            else{ //if 3,6,9
                userCols[2]+=i;
                if(move==3)
                    userDiag[1]+=i;
                if(move==9)
                    userDiag[0]+=i;
            }
        }
    }
    public static int getMove(char piece){
        int move;
        if(empty){ //if board is empty just assign a random square
            move = random.nextInt(9);
            empty = false; 
        }
        else{
            miniMax(piece, 0); //AI solution
            move = pos.get("square");
        }
        return move+1;
    }
    public static int miniMax(char player, int depth){
        int best; //keep track of best move
        char other; 
        if(depth==0) //only set maxplayer for AI
            maxPlayer = player; 
        int score;
        if(player == 'X') other = 'O'; //set pieces for players 
        else other = 'X'; 
        if(curwin==other){ //check if previous turn in recursion is a winner
            if(other == maxPlayer)
                return (1*(emptySquares()+1)); //score negative if user won, postive if AI won
            else return (-1*(emptySquares()+1));
        }
        else if(boardFull())
            return 0; //return score of a draw

        if(player == maxPlayer)
            best = -1000; //super low number
        else best = 1000; //super high number
        for(int i=0;i<emptySquares();i++){ 
            makeMove(player, i);
            int temp = old;
            score = miniMax(other, depth+1); //recurse to next move
            board[temp]=nun; //restore board
            curwin = 'N'; //reset current winner
            if(player==maxPlayer){ //if the AI is player we want to take the highest score we can
                if(score>best){
                    best= score;
                    pos.put("square", temp); //store position of move when taking the highest score
                }
            }
            else{ //if the user is the player we want to take the lowest score
                if(score<best){ 
                    best=score;
                }
            }
        }
        return best;
    }
    public static int emptySquares(){
        int empty = 0;
        for(int c:board){
            if(c == nun)
                empty+=1;
        }
        return empty;
    }
    public static void makeMove(char piece, int depth){
        int check = 0; //ensure that we're trying new squares each time 
        int temp=0; //helper to delete from arrays after checking winners
        for(int i=0;i<boardSize;i++){ //look for empty space that we have used before
            if(board[i]== nun){
                if(check == depth){
                    board[i]= piece;
                    temp = i;
                    old = i;
                    addDelete(piece, i+1,'a');
                    break;
                }
                else{
                    check+=1;
                }
            }
        }
        if(checkW(piece))
            curwin=piece;
        addDelete(piece,temp+1,'d');
    }
    public static boolean checkW(char piece){ //3 in any of these arrays means 3 in a row
        for(int i=0; i<boardSize/3;i++){
            if(userRows[i] == 3||userCols[i]==3||userDiag[i]==3)
                return true;
            else if(aiRows[i] == 3||aiCols[i]==3||aiDiag[i]==3)
                return true;
        }
        return false;
    }
    public static boolean boardFull(){
        for(char c : board){
            if(c == nun){
                return false;
            }
        }
        return true;
    }
    public static void flip(){
        int pick;
        int result;
        do{
        try{
        System.out.println("Lets flip a coin to see who will go first...Choose '1' for heads or '2' for tails");
        pick = user.nextInt();
        result = random.nextInt(2);
        break;
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid entry.");
        }
        catch(InputMismatchException e){
            System.out.println("Invalid entry. Enter a valid number.");
                user.next();
        } 
        }while(true);
        if(result == pick){
            System.out.println("Congrats, you won the coin toss, you'll go first");
            turn = true;
            empty = false;
        }
        else System.out.println("Ratz, you lost the coin toss, the opponent will go first");
    }
}

