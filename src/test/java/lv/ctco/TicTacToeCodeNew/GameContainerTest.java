package lv.ctco.TicTacToeCodeNew;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import javax.swing.*;
import java.awt.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameContainerTest{

    private Buttons[][] buttonArray= new Buttons[3][3];
    private JLabel[] labelArray = new JLabel[3];

    @InjectMocks
    private GameContainer gContainer = new GameContainer(buttonArray,labelArray);

    @Mock
    DBConnection db;

    @Spy
    Bot bot = new Bot();

    @Before
    public void setUp() throws Exception {
        String[] textNames = {"Wins: ", "Losses", "Draws: "};
        for (int i = 0; i <3 ; i++) {
            JLabel l = new JLabel(textNames[i]);
            JLabel l2 = new JLabel("0");
            l2.setName(textNames[i]);
            labelArray[i] = l2;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Buttons b = new Buttons(i,j,gContainer);
                buttonArray[i][j]=b;
            }
        }

        gContainer.setPersistenceUnitName("Entity");
        gContainer.setDbShutdownString("jdbc:h2:tcp:;");
    }

    @Test
    public void checkIfGetCoordsWorks() throws Exception {
        int[] coords = gContainer.getCoords(buttonArray[0][0].getName());
        int[] check = {0,0};
        assertArrayEquals(check,coords);
    }

    @Test
    public void checkIfCheckRowWorks() throws Exception {
        gContainer.setPlayerOne("Player");
        for (int i = 0; i <3; i++) {
            buttonArray[1][i].setText("X");
        }
        gContainer.setCallingButton(buttonArray[1][1]);
        assertTrue(gContainer.checkRow());
    }

    @Test
    public void checkIfCheckColumnWorks() throws Exception {
        gContainer.setPlayerOne("Player");
        for (int i = 0; i <3; i++) {
            buttonArray[i][1].setText("X");
        }
        gContainer.setCallingButton(buttonArray[1][1]);
        assertTrue(gContainer.checkColumn());
    }

    @Test
    public void CheckifCheckDiagonalWorks() throws Exception {
        gContainer.setPlayerOne("Player");
        for (int i = 0; i <3; i++) {
            buttonArray[i][i].setText("X");
        }
        gContainer.setCallingButton(buttonArray[1][1]);
        assertTrue(gContainer.checkDiagonal());
    }

    //integration test
//    @Test
//    public void CheckIfCheckWinWorksAsPlayer() throws Exception {
//        gContainer.setPlayerOne("Player");
//        for (int i = 0; i <3; i++) {
//            buttonArray[i][i].setText("X");
//        }
//        gContainer.setCallingButton(buttonArray[1][1]);
//        gContainer.botTurn();
//        assertTrue(gContainer.checkWin());
//    }

    //integration test
//    @Test
//    public void CheckIfCheckWinWorksAsPC() throws Exception {
//        gContainer.botTurn();
//        gContainer.setPlayerOne("PC");
//        for (int i = 0; i <3; i++) {
//            buttonArray[1][i].setText("X");
//        }
//        gContainer.setCallingButton(buttonArray[1][1]);
//
//        assertTrue(gContainer.checkWin());
//    }

    //integration test
//    @Test
//    public void CheckIfCheckWinWorksAsDraw() throws Exception {
//        gContainer.setPlayerOne("Player");
//        buttonArray[1][1].setText("X");
//        gContainer.setTurnCounter();
//        gContainer.botTurn();
//
//        buttonArray[2][0].setText("X");
//        gContainer.setTurnCounter();
//        buttonArray[0][2].setText("O");
//        gContainer.setTurnCounter();
//
//        buttonArray[0][1].setText("X");
//        gContainer.setTurnCounter();
//        buttonArray[2][1].setText("O");
//        gContainer.setTurnCounter();
//
//        buttonArray[1][0].setText("X");
//        gContainer.setTurnCounter();
//        buttonArray[1][2].setText("O");
//        gContainer.setTurnCounter();
//
//        buttonArray[2][2].setText("X");
//        gContainer.setTurnCounter();
//
//        gContainer.setCallingButton(buttonArray[2][2]);
//        assertTrue(gContainer.checkWin());
//    }

    @Test
    public void checkIfResetFieldWorksAsPlayer() throws Exception {
        gContainer.setPlayerOne("Player");
        for (int i = 0; i <3; i++) {
            buttonArray[i][i].setText("X");
        }
        gContainer.setCallingButton(buttonArray[1][1]);

        gContainer.resetField();

        gContainer.setTurnCounter();
        assertThat(gContainer.getPlayerOne(),is("PC"));
        assertTrue(!gContainer.isWin());
        assertThat(gContainer.getTurnCounter(),is(1));
        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                assertTrue(buttonArray[i][j].getText().isEmpty());
            }
        }
    }

    @Test
    public void checkTextarea() throws Exception {
        gContainer.setTextarea("Player");
        assertThat(gContainer.getTextarea(),is("Player"));
    }

    @Test
    public void checkBotTurn() throws Exception {
        gContainer.setPlayerOne("Player");
        buttonArray[0][0].doClick();
//        buttonArray[0][2].doClick();
        assertThat(buttonArray[1][1].getText(),is("O"));
    }

    @Test
    public void checkBotCoords(){
        gContainer.setPlayerOne("Player");
        bot.addButtons(buttonArray);
        bot.setBotSymbol("O");
        bot.setTurn(true);
        bot.checkDiagonalEmpty();
        buttonArray[1][1].doClick();
        buttonArray[0][2].doClick();
        assertThat(buttonArray[2][0].getText(),is("O"));
    }

//    @Test
//    public void mockingCheckWin(){
//        String persistenceUnitName = gContainer.getPersistenceUnitName();
//        for (int i = 0; i <3; i++) {
//            buttonArray[i][i].setText("X");
//        }
//
////        Mockito.doNothing().when(db).connect(persistenceUnitName);
//
////        Mockito.when(db.connect(persistenceUnitName)).thenReturn("First");
//
//        gContainer.setCallingButton(buttonArray[1][1]);
//        gContainer.botTurn();
//
//        gContainer.checkWin();
//        verify(db).connect(persistenceUnitName);
////        assertTrue(gContainer.checkWin());
////        Mockito.verify(db).connect(persistenceUnitName);
//    }
}