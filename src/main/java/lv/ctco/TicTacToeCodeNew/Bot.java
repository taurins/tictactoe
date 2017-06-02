package lv.ctco.TicTacToeCodeNew;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot{
    private JButton[][] buttonArray;
    private boolean botTurn = false;
    private String botSymbol;
    private String opponentSymbol;
    private boolean randomMove = false;

    public void addButtons(JButton[][] arr){
        this.buttonArray = arr;
    }

    public void move(){
        if(isRandomMove()){
            Random ran = new Random(System.currentTimeMillis());
            int r = ran.nextInt();
            if(r%2==0) {
                randomMove();
            }
        }
        firstMove();
        checkRow(botSymbol);
        checkColumn(botSymbol);
        checkRow(opponentSymbol);
        checkColumn(opponentSymbol);
        checkDiagonalOpponent();
        checkDiagonalEmpty();
        randomMove();
     }

    public void go() {
            if (botTurn&&checkArray().size()>0) {
                move();
            }
    }

    public void setTurn(boolean turn){
         botTurn = turn;
    }

    private List<JButton> checkArray(){
        List<JButton> list = new ArrayList<>();
        for (int i = 0; i <buttonArray[0].length; i++) {
            for (int j = 0; j <buttonArray[1].length; j++) {
                if(buttonArray[i][j].getText().isEmpty()) {
                    list.add(buttonArray[i][j]);
                }
            }
        }
        return list;
    }

    public void setBotSymbol(String simbol){
        this.botSymbol = simbol;
        if("X".equals(simbol)) {
            this.opponentSymbol = "O";
        }
        else {
            this.opponentSymbol = "X";
        }
    }

    public String getBotSymbol(){
        return this.botSymbol;
    }

    public void firstMove(){
        if(botTurn && (buttonArray[0][0].getText().isEmpty()||buttonArray[1][1].getText().isEmpty())) {
            if (buttonArray[1][1].getText().isEmpty()) {
                buttonArray[1][1].doClick();
            }
            else
            if(buttonArray[0][0].getText().isEmpty()) {
                buttonArray[0][0].doClick();
            }
            botTurn = false;
        }
    }

    public void checkRow(String simbol) {
        int[] clickableCoords = {-1,-1};
        if (botTurn) {
            for (int i = 0; i < 3; i++) {
                if(buttonArray[i][1].getText().equals(simbol) && buttonArray[i][2].getText().equals(simbol) && buttonArray[i][0].getText().isEmpty()){
                    clickableCoords[0] = i;
                    clickableCoords[1] = 0;
                    break;
                }else
                    if(buttonArray[i][0].getText().equals(simbol) && buttonArray[i][1].getText().equals(simbol) && buttonArray[i][2].getText().isEmpty()){
                        clickableCoords[0] = i;
                        clickableCoords[1] = 2;
                        break;
                    }else
                        if (buttonArray[i][0].getText().equals(simbol) && buttonArray[i][2].getText().equals(simbol) && buttonArray[i][1].getText().isEmpty()) {
                            clickableCoords[0] = i;
                            clickableCoords[1] = 1;
                            break;
                        }
            }
            if(clickableCoords[0]>=0&&clickableCoords[1]>=0&&buttonArray[clickableCoords[0]][clickableCoords[1]].getText().isEmpty()) {
                buttonArray[clickableCoords[0]][clickableCoords[1]].doClick();
                botTurn = false;
            }
        }

    }

    public void checkColumn(String simbol){
        int[] clickableCoords = {-1,-1};
        if (botTurn) {
            for (int i = 0; i < 3; i++) {
                if(buttonArray[1][i].getText().equals(simbol) && buttonArray[2][i].getText().equals(simbol) && buttonArray[0][i].getText().isEmpty()) {
                    clickableCoords[0] = 0;
                    clickableCoords[1] = i;
                    break;
                }else
                    if(buttonArray[0][i].getText().equals(simbol) && buttonArray[1][i].getText().equals(simbol) && buttonArray[2][i].getText().isEmpty()){
                        clickableCoords[0] = 2;
                        clickableCoords[1] = i;
                        break;
                    }else
                if (buttonArray[0][i].getText().equals(simbol) && buttonArray[2][i].getText().equals(simbol) && buttonArray[1][i].getText().isEmpty()) {
                    clickableCoords[0] = 1;
                    clickableCoords[1] = i;
                    break;
                }
            }
            if(clickableCoords[0]>=0&&clickableCoords[1]>=0&&buttonArray[clickableCoords[0]][clickableCoords[1]].getText().isEmpty()) {
                buttonArray[clickableCoords[0]][clickableCoords[1]].doClick();
                botTurn = false;
            }
        }

    }

    private int[] coords(int i, int j){
        int arr[] = {-1,-1,-1,-1};
        if(i==0&&j==0) {
            arr[0] = 0;
            arr[1] = 2;
            arr[2] = 2;
            arr[3] = 0;
        }else if(i==0&&j==2){
            arr[0] = 0;
            arr[1] = 0;
            arr[2] = 2;
            arr[3] = 2;
        }else if(i==2&&j==0){
            arr[0] = 0;
            arr[1] = 0;
            arr[2] = 2;
            arr[3] = 2;
        }else if(i==2&&j==2){
            arr[0] = 2;
            arr[1] = 0;
            arr[2] = 0;
            arr[3] = 2;
        }
        return arr;
    }

    public void checkDiagonalEmpty(){
        if(botTurn) {
            int[] arr = {0, 2};
            int[] arr2 = {-1,-1};
            int[] checkC;
            for (int i = 0; i < 2; i++) {
                if(arr2[0]==-1&&arr2[1]==-1) {
                    for (int j = 0; j < 2; j++) {
                        checkC = coords(arr[i], arr[j]);
                        if (buttonArray[arr[i]][arr[j]].getText().isEmpty() && buttonArray[checkC[0]][checkC[1]].getText().equals(opponentSymbol) && buttonArray[checkC[2]][checkC[3]].getText().isEmpty()) {
                            arr2[0] = arr[i];
                            arr2[1] = arr[j];
                            break;
                        }
                    }
                }
                else {
                    break;
                }
            }
            if(arr2[0]!=-1 && arr2[1]!=-1&&buttonArray[arr2[0]][arr2[1]].getText().isEmpty()) {
                buttonArray[arr2[0]][arr2[1]].doClick();
                botTurn = false;
            }
        }
    }

    public void randomMove(){
        if(botTurn&& emptySpaces()>0) {
            List<JButton> emptyButtons = checkArray();
            Random ran = new Random(System.currentTimeMillis());
            int r = ran.nextInt(emptyButtons.size());
            emptyButtons.get(r).doClick();
            botTurn = false;
        }
    }

    private int emptySpaces(){
        int empty =0;
        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                if(buttonArray[i][j].getText().isEmpty()) {
                    empty++;
                }
            }
        }
        return empty;
    }

    public void checkDiagonalOpponent() {
        if (botTurn) {
            int[] arr = {0, 2};

            for (int i = 0; i <2; i++) {
                for (int j = 0; j <2; j++) {
                    if(buttonArray[arr[i]][arr[j]].getText().equals(opponentSymbol) && buttonArray[arr[j]][arr[i]].getText().isEmpty() && i!=j) {
                        buttonArray[arr[j]][arr[i]].doClick();
                        botTurn = false;
                        break;
                    }else
                        if(buttonArray[arr[i]][arr[j]].getText().equals(opponentSymbol)&& i==j) {
                            if (i == 0 && buttonArray[arr[1]][arr[1]].getText().isEmpty()) {
                                buttonArray[arr[1]][arr[1]].doClick();
                                botTurn = false;
                                break;
                            } else if (i == 1&& buttonArray[arr[0]][arr[0]].getText().isEmpty()) {
                                buttonArray[arr[0]][arr[0]].doClick();
                                botTurn = false;
                                break;
                            }
                        }
                }
            }

        }

    }

    public boolean isRandomMove() {
        return randomMove;
    }

    public void setRandomMove(boolean randomMove) {
        this.randomMove = randomMove;
    }
}
