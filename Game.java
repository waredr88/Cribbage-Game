import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Game
{
    // instance variables - replace the example below with your own
    int[] deck, cribHand;
    int numPlayers;
    private int deckIndex, starter; //Used when making hands
    boolean gameOver, paused;
    ArrayList<Player> playerArr; //To hold player classes
    Scanner keyboard = new Scanner(System.in);
    MainWindow mw; 
    public Game(int n)
    {
        // initialise instance variables
        gameOver = false;
        paused = false;
        deck = new int[52]; cribHand = new int[4];
        deckIndex = 0; numPlayers = n;
        makeDeck();
        setStarter();        
        playerArr = new ArrayList<Player>();
        makePlayerArr();
        
        ButtonPressed e = new ButtonPressed();
        EnterPressed k = new EnterPressed();
        mw = new MainWindow();
        mw.setVisible(true);
        mw.setSize(1000, 400);
        mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mw.setTitle("Cribbage by David"); 
        mw.textField.addKeyListener(k);
        mw.button.addActionListener(e);
    }
    public void addFinalCribCard(){
        cribHand[3] = deck[deckIndex++];
    }
    public int[] getCribHand(){
        return cribHand;
    }
    public void makeDeck(){
        //Makes a deck; unshuffled, no jokers.        
        int cardvalue = 0;
        for(int i = 1; i <= 4; i++){
            //cardvalue represents suit and value. first digit is suit, second 2 are number.
            for(int j = 1; j <= 13; j++){
                cardvalue = (100*i + j);
                deck[j + ((i-1) * 13)- 1] = cardvalue;
            }            
        }
        shuffleDeck();
    }
    public void shuffleDeck(){
        //Shuffles deck, swaps cards randomly 1000 times
        int tempCard,card1, card2;
        tempCard = card1 = card2 = 0;
        for(int i = 0; i < 1000; i++){
            card1 = (int) Math.floor(Math.random()*52);
            card2 = (int) Math.floor(Math.random()*52);
            
            tempCard = deck[card2];
            deck[card2] = deck[card1];
            deck[card1] = tempCard;
            
            deckIndex = 0;
        }
    }
    public void printDeck(){
        //Prints out deck on 4* 13 block
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                System.out.print(deck[j + (i * 13)] + "  ");
            }
            System.out.println(" ");
        }
    }  
    public void makePlayerArr(){
        Scanner keyboard = new Scanner(System.in);
        String name;
        for(int i = 1; i<= numPlayers; i++){
            name = JOptionPane.showInputDialog("What is player " + i + " name?");
            playerArr.add(new Player(name, new int[numPlayers]));
        }
    }
    //Not sure if this method should go here or in Player class
    public int[] makeHand(){
        int handSize = 5;        
        if(numPlayers == 2)handSize++;        
        int[] hand = new int[handSize];
        
        for(int i = 0; i < handSize; i++)
            hand[i] = deck[deckIndex++];
        return hand;
    }
    public void setStarter(){
        starter = deck[deckIndex++];
    }
    public int getStarter(){
        return starter;
    }
    public int[] shortenArray(int[] cardRegister){
        int j = 0;
        for( int i=0;  i<cardRegister.length;  i++ ){
            if (cardRegister[i] != 0 && cardRegister[i] != -1)
                cardRegister[j++] = cardRegister[i];
        }
        int [] shortArray = new int[j];
        System.arraycopy(cardRegister, 0, shortArray, 0, j);
        
        return shortArray;
    }
    public int[] shortenArray(int[] cardRegister, int value){
        int j = 0;
        for( int i=0;  i<cardRegister.length;  i++ ){
            if (cardRegister[i] != value)
                cardRegister[j++] = cardRegister[i];
        }
        int [] shortArray = new int[j];
        System.arraycopy(cardRegister, 0, shortArray, 0, j);
        
        return shortArray;
    }
    public void checkScores(){
        for(int i = 0;i < numPlayers;i++){
            if(playerArr.get(i).getScore() >= 121){
                gameOver= true; 
                mw.ta.append("\n"+playerArr.get(i).name + " has won the game, congratulations!");
            }
        }
    }
    public void dealCards(){
        for(int i =0; i < numPlayers; i++){
            playerArr.get(i).setHand(makeHand());
        }
    }
    public void displayHand(int index){
        mw.ta.append("\n" + playerArr.get(index).name +" your hand is: ");
        for(int j = 0; j < playerArr.get(0).getHand().length; j++)// prints hand
            mw.ta.append(playerArr.get(index).getHand(j) + " ");   
        mw.ta.append("\n");
    }
    public void pauseGame(){
        paused = true;
        while(paused){
            try{
                Thread.sleep(500);
            } catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }
    public int getCard(int i){
        try{}
        catch(Exception ex){}
        mw.ta.append("\nWhich card to put in crib? ");
        pauseGame();
        int cardIndex = (int) Integer.parseInt(mw.textField.getText()); //select which slot to take card from
        int card = playerArr.get(i).getHand()[cardIndex]; //gets value of chosen card
        playerArr.get(i).setHand(cardIndex, 0); //sets empty slot to value 0
        
        return card;
    }
    public java.util.List<int[]> getCombosOf(int[] input, int k /* sequence length */){        
        java.util.List<int[]> subsets = new ArrayList<>();
        
        int[] s = new int[k];                  // here we'll keep indices 
                                               // pointing to elements in input array
        
        if (k <= input.length) {
            // first index sequence: 0, 1, 2, ...
            for (int i = 0; (s[i] = i) < k - 1; i++);  
            subsets.add(getSubset(input, s));
            for(;;) {
                int i;
                // find position of item that can be incremented
                for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--); 
                if (i < 0) {
                    break;
                } else {
                    s[i]++;                    // increment this item
                    for (++i; i < k; i++) {    // fill up remaining items
                        s[i] = s[i - 1] + 1; 
                    }
                    subsets.add(getSubset(input, s));
                }
            }
        }
        return subsets;
    }
    public int[] getSubset(int[] input, int[] subset) {
        int[] result = new int[subset.length]; 
        for (int i = 0; i < subset.length; i++) 
            result[i] = input[subset[i]];
        return result;
    }
    public void setCrib(int index, int value){
        cribHand[index] = value;
    }
    public static int[] removeSuits(int[] cardRegister){
        int[] newRegister = new int[cardRegister.length];
        int i = 0;
        for(int value: cardRegister)
            newRegister[i++] = value % 100;
            
        return newRegister;
    }
    public static int[] removeValues(int[] cardRegister){
        int[] newRegister = new int[cardRegister.length];
        int i = 0;
        for(int value: cardRegister)
            newRegister[i++] = value / 100;
        
        return newRegister;
    }
    public int run(int[] cardRegister){
        cardRegister = removeSuits(cardRegister);
        Arrays.sort(cardRegister);
        boolean isRun = true;          
        int count = 0;
        for(int h = 5; h > 2; h--){                                     //creates subsets of length 5,4,3.
            java.util.List<int[]> subsets = getCombosOf(cardRegister,h);          //get all combinations of size h           
            for(int i = 0; i < subsets.size(); i++){                 //for each element in the subset, looks to see if any elements in array are non-sequential.
                for(int j = 0; j < subsets.get(i).length - 1; j++){
                    if(subsets.get(i)[j] != subsets.get(i)[j+1] -1)
                        isRun = false;}
                if(isRun) 
                    count += subsets.get(i).length;
                isRun = true;
            }       
            if(count > 0)                                            //breaks loop if points have been found. if there is a run of 5, there can't be a run of 4. same argument of 4s and 3s.
                return count;
        }
        return 0;
    }
    public int runWhilePlaying(int[] cardRegister){
        //While counting hand.
        int points = 0;
        int run = 1;
        cardRegister = shortenArray(removeSuits(cardRegister));
        if(cardRegister.length < 3) return 0;
        for(int i = 0; i < cardRegister.length - 2; i++){
            int[] tempRegister = Arrays.copyOfRange(cardRegister, i, cardRegister.length);
            Arrays.sort(tempRegister);
            for(int j = tempRegister.length -1; j > 0; j--){
                if(tempRegister[j] != tempRegister[j-1] +1)
                    break;
                run++;
            }
            if(run == tempRegister.length) return run;
            run = 1;
        }
        return 0;
    }
    public int multiplesFound(boolean whileCounting, int[] tempCardRegister){
        //inputs the played cards, returns number of multiples.
        int combo = 0, multiples = 0;
        int[] cardRegister = shortenArray(removeSuits(tempCardRegister));
        if(!whileCounting){       
            //loop counts from end of array first. 
            for(int i = cardRegister.length - 1; i > 0; i--){
                if(cardRegister[i] == cardRegister[i - 1]){
                    combo++;
                    multiples+= combo;
                }
                else break;
            }
        }
        if(whileCounting){
            Arrays.sort(cardRegister);
            java.util.List<int[]> twos = getCombosOf(cardRegister,2);
            for(int[] set: twos){
                if(set[0] == set[1]) multiples++;
            }
        }
        return multiples;
    }
    
    public int countFifteens(int[] cardRegister){
        // returns number of 15's counted.
        int fifteens = 0;
        int sum = 0;
        cardRegister = removeSuits(cardRegister);
        for(int i = 0; i < cardRegister.length; i++) // Hotfix J,Q,K to values of 10.
            if(cardRegister[i] > 10) cardRegister[i] = 10;
        
        for(int i = 2; i < 6; i++){  // iterate through combinations of i = 2,3,4, and 5 cards.
            for(int[] arr: getCombosOf(cardRegister, i)){ //for each array of size i
                for(int value: arr){ //for each value in said array
                    sum += value;
                }
                if(sum == 15) fifteens++;
                sum =  0;
            }
        }
        
        return fifteens;
    }
    public int countFlush(boolean isCrib,int[] tempCardRegister){//tempCardRegister doens't include starter.
        int[] cardRegister = removeValues(tempCardRegister);        
        Arrays.sort(cardRegister);// sort array into groups of suits. if flush, must be in format {x,x,x,x}.
        int firstCard = cardRegister[0];
        if(firstCard != starter/100 && isCrib) return 0;
        
        for(int card: cardRegister)
            if(card != firstCard)
                return 0;

        if(firstCard == starter/100) return 5;
        return 4;
    } 
    public int oneForNobs(int[] cardRegister){
        //inputs array, looks to see if cards are a jack and share suit with starter.
        for(int card: cardRegister)
            if((card % 100) == 11 && card/100 == starter/100) return 1;
        return 0;
    }
    public int addPoints(int[] cardRegister){
        //use for pegging mode where cardRegister could have length > 5. in pegging mode. 
        int sum = 0;
        int pointsEarned= 0;
        //remove suits and get rid of -1 and 0's in array.
        cardRegister = shortenArray(removeSuits(cardRegister));
        for(int card: cardRegister){
            if(card < 11)
                sum += card;
            else 
                sum += 10;
        }
        if(sum == 15 || sum == 31)pointsEarned += 2;
        pointsEarned += 2*multiplesFound(false,cardRegister);
        pointsEarned += runWhilePlaying(cardRegister);
        return pointsEarned;
    }
    public int countPoints(boolean isCrib, int[] tempCardRegister){
        //use only for crib hand % counting your hand
        int[] cardRegister = new int[5];
        int[] partialCardRegister = shortenArray(tempCardRegister.clone());        
        
            
        //method takes in players hand as partial array, adds on starter card.
        for(int i = 0; i < 4; i++) 
            cardRegister[i] = partialCardRegister[i];
        cardRegister[4] = starter;
        
        int pointsEarned= 0;
        pointsEarned += oneForNobs(cardRegister); //needs suit and value
        pointsEarned += 2*countFifteens(cardRegister); //needs value, no suits
        pointsEarned += 2*multiplesFound(true,cardRegister);// needs value, no suits
        pointsEarned += run(cardRegister); // needs value, no suits
        pointsEarned += countFlush(isCrib, partialCardRegister); //needs suits, no value
        
        return pointsEarned;
    }    
    private class ButtonPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            paused = false;
        }
    }
    private class EnterPressed implements KeyListener{
        public void keyPressed(KeyEvent k){
            if(k.getKeyCode() == KeyEvent.VK_ENTER){
                paused = false;
            }
        }
        public void keyReleased(KeyEvent ke){
            
        }
        public void keyTyped(KeyEvent kt){
            
        }
    }
}












//You've found the bonus level!