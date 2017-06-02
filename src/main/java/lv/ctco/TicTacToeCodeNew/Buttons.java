package lv.ctco.TicTacToeCodeNew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Buttons extends JButton implements ActionListener {
    private GameContainer gContainer;

    public Buttons(int i, int j, GameContainer container) {
        this.gContainer = container;
        this.setName(""+i+j);
        this.setFont(new Font("Arial", Font.PLAIN, 70));
        this.setFocusable(false);
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Buttons b = (Buttons)e.getSource();
        if(b.getText().isEmpty()) {
            gContainer.setTurnCounter();
            if (gContainer.getTurnCounter() % 2 == 0 && b.getText().isEmpty()) {
                b.setText("O");
            } else if (gContainer.getTurnCounter() % 2 != 0 && b.getText().isEmpty()) {
                b.setText("X");
            }
            gContainer.setCallingButton(b);
            gContainer.checkWin();
            gContainer.botTurn();
        }
    }
}
