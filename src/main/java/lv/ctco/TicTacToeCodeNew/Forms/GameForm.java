package lv.ctco.TicTacToeCodeNew.Forms;

import lv.ctco.TicTacToeCodeNew.Buttons;
import lv.ctco.TicTacToeCodeNew.GameContainer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameForm extends JFrame {
    private JPanel container = new JPanel(new GridLayout(1,2));
    private Buttons[][] buttonArray = new Buttons[3][3];
    private JLabel[] labelArray = new JLabel[3];
    private GameContainer gContainer = new GameContainer(buttonArray, labelArray);

    public GameForm(String name){
        this.setTitle(name);
    }

    public void setUp(){
        gameBoardSetUp();
        dataFieldsSetUp();
        formSetUp();
        this.add(container);
    }

    private void formSetUp(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,300);
        this.setVisible(true);
        this.setResizable(false);

        Dimension windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2);
    }

    private void gameBoardSetUp(){
        JPanel buttonContainer = new JPanel(new GridLayout(3,3,2,2));
        buttonContainer.setBackground(Color.black);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Buttons b = new Buttons(i,j,gContainer);
                buttonArray[i][j]=b;
                buttonContainer.add(b);
            }
        }
        container.add(buttonContainer);
    }

    private void dataFieldsSetUp(){
        JPanel sideStuff = new JPanel(new GridLayout(3,1,10,10));
        sideStuff.setBorder(new EmptyBorder(10, 10, 10, 10));
        String[] textNames = {"Wins: ", "Losses", "Draws: "};

        JLabel nameLabel = new JLabel("Name");
        JTextField nameText = new JTextField();
        nameText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                getText();
            }

            public void getText(){
                if(nameText.getText().isEmpty()) {
                    gContainer.setTextarea("playerOne");
                }
                else {
                    gContainer.setTextarea(nameText.getText());
                }
            }
        });

        JPanel nameContainer= new JPanel(new GridLayout(3,2));
        nameContainer.add(nameLabel);
        nameContainer.add(nameText);

        JPanel scoreContainer = new JPanel(new GridLayout(3,2));
        for (int i = 0; i <3 ; i++) {
            JLabel l = new JLabel(textNames[i]);
            JLabel l2 = new JLabel("0");
            l2.setName(textNames[i]);
            scoreContainer.add(l);
            scoreContainer.add(l2);
            labelArray[i] = l2;
        }

        JPanel buttonContainer = new JPanel();
        JButton history = new JButton("History");
        history.setText("History");
        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryForm historyform = new HistoryForm();
                historyform.formSetup();
            }
        });
        buttonContainer.add(history);

        sideStuff.add(nameContainer);
        sideStuff.add(scoreContainer);
        sideStuff.add(buttonContainer);

        container.add(sideStuff);
    }
}
