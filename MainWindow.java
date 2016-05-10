import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;
public class MainWindow extends JFrame{
    JLabel nameN, nameE, nameS, nameW, cardsN , cardsS, cardsC;
    JButton button;
    JTextField textField;
    private JPanel nPanel, ePanel, sPanel, wPanel, cPanel;
    private JMenuBar menuBar;
    private JMenu file, help;
    JMenuItem exit, instructions, newGame;
    private JTextArea cardsE, cardsW;
    private JScrollPane scroll;
    JTextArea ta;
    String text;
    public MainWindow(){
        setLayout(new BorderLayout());
        
        ExitButton eb = new ExitButton();
        ButtonPressed bp = new ButtonPressed();
        //Make the menu bar
        menuBar = new JMenuBar();
        file = new JMenu("File");
        newGame = new JMenuItem("New Game");
        exit = new JMenuItem("Exit");
        help = new JMenu("Help");
        instructions = new JMenuItem("Instructions");
        
        //Add the menu bar
        setJMenuBar(menuBar);     
        menuBar.add(file);       
        file.add(newGame);      
        newGame.addActionListener(eb);
        file.add(exit);
        exit.addActionListener(eb);        
        menuBar.add(help);     
        help.add(instructions);
        
        //Make north panel
        nPanel = new JPanel();
        nameN = new JLabel("Player 3");
        cardsN = new JLabel("302 305 374 302 303");
        
        nPanel.add(nameN);
        nPanel.add(cardsN);
        nPanel.setBackground(new Color(125,50,0));
        
        //make east panel
        ePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 120));
        nameE = new JLabel("Player 4 ");        
        cardsE = new JTextArea("401\n402\n403\n404\n405");
        cardsE.setEditable(false);
                
        ePanel.add(nameE);
        ePanel.add(cardsE);
        ePanel.setBackground(new Color(125,50,0));
        
        //make south panel
        sPanel = new JPanel();
        nameS = new JLabel("Player 1");
        cardsS = new JLabel("102 105 174 102 103");
        
        sPanel.add(nameS);
        sPanel.add(cardsS);
        sPanel.setBackground(new Color(125,50,0));
        
        //make west panel
        wPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 120));
        nameW = new JLabel("Player 2 ");
        cardsW = new JTextArea("201\n202\n203\n204\n205");
        cardsW.setEditable(false);
        
        wPanel.add(nameW);
        wPanel.add(cardsW);
        wPanel.setBackground(new Color(125,50,0));
        
        //Make the central panel
        cPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,500, 10));
        cardsC = new JLabel("Cards Played");
        textField = new JTextField(10);
        button = new JButton("Play");
        button.addActionListener(bp);
        ta = new JTextArea(10,50);
        DefaultCaret caret = (DefaultCaret)ta.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        //ta.setOpaque(false);
        scroll = new JScrollPane(ta);
        scroll.getViewport().setOpaque(false);
        
        cPanel.add(cardsC);
        cPanel.add(textField);
        cPanel.add(button);
        cPanel.add(scroll);
        cPanel.setBackground(new Color(0,175,0));
        
        add(nPanel, BorderLayout.NORTH);  
        add(ePanel, BorderLayout.EAST);
        add(sPanel, BorderLayout.SOUTH);
        add(wPanel, BorderLayout.WEST);
        add(cPanel, BorderLayout.CENTER);
    }    
    public class ExitButton implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }    
    private class ButtonPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            text = textField.getText();
        }
    }
    public static void main(String[] args){
        MainWindow mw = new MainWindow();
        mw.setVisible(true);
        mw.setSize(1000, 400);
        mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mw.setTitle("Cribbage by David");
    }
}