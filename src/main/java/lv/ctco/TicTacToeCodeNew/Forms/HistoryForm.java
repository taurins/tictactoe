package lv.ctco.TicTacToeCodeNew.Forms;

import lv.ctco.TicTacToeCodeNew.DBConnection;
import lv.ctco.TicTacToeCodeNew.HistoryTable;
import lv.ctco.TicTacToeCodeNew.dbEntitys.GameEntity;
import javax.persistence.Query;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class HistoryForm extends JFrame {
    private JPanel container = new JPanel(new GridLayout(1,2));
    private JButton[][] buttonArray = new JButton[3][3];

    public void formSetup(){
        createButtons();
        dataSide();
        setup();
        this.add(container);

    }

    private void setup(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600,300);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("History");

        Dimension windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2);
    }

    private void createButtons(){
        JPanel buttonContainer = new JPanel(new GridLayout(3,3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton b = new JButton();
                b.setFont(new Font("Arial", Font.PLAIN, 70));
                b.setFocusable(false);
                buttonArray[i][j]=b;
                buttonContainer.add(b);
            }
        }
        container.add(buttonContainer);
        container.add(buttonContainer);
    }

    private void dataSide(){
        String[] names = {"idGame","Player","PlayerSymbol"};
        Object[][] games = getGames();
        HistoryTable table = new HistoryTable(games,names, buttonArray);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel data = new JPanel( new GridLayout(1,1));
        data.add(scrollPane);
        container.add(data);
    }

    private Object[][] getGames(){
        String persistenceUnitName = "playerEntity";
        String dbShutdownString = "jdbc:derby:;shutdown=true";
        DBConnection db = new DBConnection();

        db.connect(persistenceUnitName);
        Query q = db.getEntityManager().createQuery("select g from GameEntity g");

        List<GameEntity> gameList = q.getResultList();



        Object[][] games = new Object[gameList.size()][3];
        for (int i = 0; i <gameList.size(); i++) {
            games[i][0] = (Integer) gameList.get(i).getIdgame();
            games[i][1] = (String) gameList.get(i).getPlayer().getName();
            games[i][2] = (String)gameList.get(i).getPlayerSimbol();
        }
//        db.disconnect(dbShutdownString);
        return games;
    }

}
