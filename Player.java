import java.util.*;
/**
 */
public class Player
{
    
    private int[] hand;
    private int score;
    String name;

    public Player(String s, int[] h)
    {
        name = s;
        score = 0;
        hand =  new int[h.length];
        for(int i = 0; i < h.length; i++){
            hand[i] = h[i];
        }
    }
    public void displayHand(){
        for(int i = 0; i < hand.length; i++){
        System.out.print(hand[i] + " ");   
        }
        System.out.println(); 
    }
    public void pickCribCards(){
        displayHand();
    }
    public void addToScore(int value){
        score += value;
    }
    public int getScore(){
        return score;
    }
    public int getHand(int index){
        return hand[index];
    }
    public int[] getHand(){
        return hand;
    }
    public void setHand(int[] cards){
        hand = new int[cards.length];
        for(int i = 0; i < cards.length; i++){
            hand[i] = cards[i];
        }
    }
    public void setHand(int index,int value){
        hand[index] = value;
    }
}
