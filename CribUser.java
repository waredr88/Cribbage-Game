import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class CribUser{      
    public static void main(String args[]){  
        newGame();
    }
    public static void newGame(){
        int nPlayers, dealerIndex;
        nPlayers = Integer.parseInt(JOptionPane.showInputDialog("How many Players are there?"));
               
        
        Game crib = new Game(nPlayers);
        crib.mw.nameS.setText(crib.playerArr.get(0).name);
        crib.mw.nameW.setText(crib.playerArr.get(1).name);
        NewButton e = new NewButton();
        crib.mw.newGame.addActionListener(e);
        if(nPlayers >= 3)crib.mw.nameN.setText(crib.playerArr.get(2).name);
        if(nPlayers == 4)crib.mw.nameE.setText(crib.playerArr.get(3).name);
        dealerIndex = -1;
        
        while(!crib.gameOver){
            dealerIndex = (dealerIndex + 1) % nPlayers;
            int currentPlayerIndex = (dealerIndex + 1) % nPlayers;
            crib.shuffleDeck();
            crib.dealCards();
            //next loops make a crib hand
            int tempIndex = 0;
            for(int i = 0; i < nPlayers; i++){// for each player
                crib.displayHand(i);
                int numCards;
                if(nPlayers == 2)numCards = 2;
                else numCards = 1;
                for(int j = 0; j < numCards; j++){ //for 2 cards to go into crib
                    int cribCard = crib.getCard(i);
                    crib.setCrib(tempIndex++,cribCard); // adds card to crib
                }
            }
            if(nPlayers == 3) crib.addFinalCribCard();
            crib.mw.ta.setText("Crib hand has been made. \nLet the first round begin!");            
            crib.setStarter();
            crib.mw.ta.append("\nThe starter is "+crib.getStarter());
            if(crib.getStarter() % 100 == 11){
                crib.mw.ta.append("Jack for 2! Adding 2 points to " + crib.playerArr.get(dealerIndex).name);
                crib.playerArr.get(dealerIndex).addToScore(2);
                crib.checkScores();
            }
            //count points in hand + crib before cards in hand get played.            
            int[] pointsInHand = new int[nPlayers];
            int pointsInCrib = crib.countPoints(true, crib.getCribHand());
            for(int i = 0; i < nPlayers; i++)
                pointsInHand[i] = crib.countPoints(false, crib.playerArr.get(i).getHand());
            
            
            //Start of card-playing loop
            int numCardsPlayed = 0;
            int maxCardsPerRound = 4*nPlayers;
            while(numCardsPlayed < maxCardsPerRound){//keep playing until each player has played 4 cards. 
                int cardsPlayedIndex = 0; 
                int cardsTotal = 0; 
                int passCount = 0; 
                int[] cardsPlayed = new int[16];                
                int chosenCard = 0, chosenCardIndex;
                //Start of each "to 31" round                 
                while(cardsTotal < 31){
                    //player Chooses a card from 0-5 or -1 to pass
                    crib.mw.ta.append("\nThe cards total is " +cardsTotal);
                    crib.mw.ta.append("\n" + crib.playerArr.get(currentPlayerIndex).name + " you are the current player. \nPick a card to play (from 0-5), or type -1 to pass. \nYour available cards are: ");
                    for(int value: crib.playerArr.get(currentPlayerIndex).getHand()){
                        crib.mw.ta.append(value + " ");
                    }  
                    crib.mw.ta.append("\n");
                    crib.pauseGame();
                    chosenCardIndex = (int) Integer.parseInt(crib.mw.textField.getText()); 
                    if(chosenCardIndex != -1){
                        chosenCard = crib.playerArr.get(currentPlayerIndex).getHand(chosenCardIndex) %100;
                        int chosenCardValue;
                        if(chosenCard < 11)
                            chosenCardValue = chosenCard;
                        else
                            chosenCardValue = 10;
                        if(cardsTotal + chosenCardValue > 31){
                            crib.mw.ta.append("\n"+"Too high!");
                            continue;
                        }
                        //If the card isn't "Pass" Remove the card from the player's hand
                        crib.playerArr.get(currentPlayerIndex).setHand(chosenCardIndex, 0);
                        passCount = 0;
                        numCardsPlayed++;
                        cardsPlayed[cardsPlayedIndex++] = chosenCard;
                        cardsTotal += chosenCardValue;
                        //add potential points to players hand
                        if(crib.addPoints(cardsPlayed) != 0){
                            crib.mw.ta.append("\n"+"Adding " + crib.addPoints(cardsPlayed) + " points to " + crib.playerArr.get(currentPlayerIndex).name);
                            crib.playerArr.get(currentPlayerIndex).addToScore(crib.addPoints(cardsPlayed));
                        }
                    }
                    else chosenCard = -1;
                    //if a player types -1 for a pass
                    if(chosenCard == -1){
                        currentPlayerIndex = (currentPlayerIndex + 1) % nPlayers;                       
                        passCount += 1;
                        if(passCount == nPlayers){
                            crib.mw.ta.append("\n"+"adding 1 to score of " + crib.playerArr.get(currentPlayerIndex).name);
                            crib.playerArr.get(currentPlayerIndex).addToScore(1);
                            break;
                        }
                        continue;
                    }
                    currentPlayerIndex = (currentPlayerIndex + 1) % nPlayers;
                }
            }
            crib.mw.ta.append("\n"+"End of round!");
            //end of round count all players hands, checks for winner, adds crib to score ----------------------------------------------------------------------------------------------------------------
            for(int i = (dealerIndex + 1); i <= (dealerIndex + nPlayers); i++){
                int j = i % nPlayers;
                crib.mw.ta.append("\n"+"Adding to score of " + crib.playerArr.get(j).name + ": " + pointsInHand[j]);
                crib.playerArr.get(j).addToScore(pointsInHand[j]);
                if(j == dealerIndex){
                    crib.mw.ta.append("\n"+crib.playerArr.get(dealerIndex).name + " your crib adds on another " + pointsInCrib + " points!");
                    crib.playerArr.get(dealerIndex).addToScore(pointsInCrib);
                }
                crib.mw.ta.append("\n"+crib.playerArr.get(j).name + " your score is now " + crib.playerArr.get(j).getScore());
                if(crib.playerArr.get(j).getScore() >= 121){
                    crib.gameOver = true;
                    crib.mw.ta.append("\n"+"Congratulations " + crib.playerArr.get(j).name + " you have won!");
                    break;
                }
            }                                                            
            crib.checkScores();                
        }
        System.exit(0);
    }
    private static class NewButton implements ActionListener{
        public void actionPerformed(ActionEvent e){
            newGame();
        }
    }
}