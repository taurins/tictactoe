package lv.ctco.TicTacToeCodeNew;

import lv.ctco.TicTacToeCodeNew.dbEntitys.GameEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class HistoryTable extends JTable implements MouseInputListener {
    private JButton[][] buttonArray = new JButton[3][3];
    private String persistenceUnitName = "playerEntity";
    private String dbShutdownString = "jdbc:derby:;shutdown=true";

    public HistoryTable(Object[][] game, String[] names, JButton[][] buttons){
        super(game,names);
        this.buttonArray = buttons;
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.addMouseListener(this);

        this.getColumnModel().getColumn(0).setMinWidth(0);
        this.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        DBConnection db = new DBConnection();
        db.connect(persistenceUnitName);

        int row =(int)this.getSelectedRow();
        int gameid = Integer.parseInt(this.getValueAt(row,0).toString());
        Query q = db.getEntityManager().createQuery("select g.moves from GameEntity g where g.idgame = "+gameid);
        List<GameEntity> game = q.getResultList();

        String movesString = game.toString();
        String movesInSize = movesString.substring(1,10);
        char[] movesBad = movesInSize.toCharArray();
        char[] moves = new char[9];
        for (int i = 0; i <9; i++) {
            moves[i] =movesBad[i];
        }

        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                if(i==0) {
                    buttonArray[i][j].setText("" + moves[j]);
                }
                else
                if(i==1) {
                    buttonArray[i][j].setText("" + moves[3 + j]);
                }
                else {
                    buttonArray[i][j].setText("" + moves[6 + j]);
                }
            }
        }
        db.disconnect(dbShutdownString);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
