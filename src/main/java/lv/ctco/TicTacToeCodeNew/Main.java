package lv.ctco.TicTacToeCodeNew;

import lv.ctco.TicTacToeCodeNew.Forms.GameForm;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                GameForm f = new GameForm("TicTacToe");
                f.setUp();
            }
        });
    }
}
