package lv.ctco.TicTacToeCodeNew;

import lv.ctco.TicTacToeCodeNew.dbEntitys.GameEntity;
import lv.ctco.TicTacToeCodeNew.dbEntitys.PlayerEntity;

import javax.persistence.*;
import javax.swing.*;
import java.util.List;

public class GameContainer{
    private Buttons[][] buttons;
    private Buttons callingButton;
    private boolean win = false;
    private String playerOne;
    private JLabel labelArray[];
    private int turnCounter=0;
    private Bot bot;
    private String textarea;
    private String persistenceUnitName = "playerEntity";
//    private String dbShutdownString = "jdbc:derby:;shutdown=true";
//    private String persistenceUnitName = "Entity";
    private String dbShutdownString = "jdbc:h2:tcp:;";

    public GameContainer(Buttons[][] buttonArr, JLabel[] labelArray){
        this.buttons = buttonArr;
        this.labelArray = labelArray;
        setPlayerOne("Player");
    }

    public int[] getCoords(String name){
        char[] arr = name.toCharArray();
        int[] intarr = new int[2];
        intarr[0] = Character.getNumericValue(arr[0]);
        intarr[1] = Character.getNumericValue(arr[1]);
        return intarr;
    }

    public boolean checkRow(){
        int[] arr = getCoords(callingButton.getName());
        for (int i = 0; i <3; i++) {
            if(buttons[arr[0]][i].getText().equals(callingButton.getText())) {
                setWin(true);
            }else{
                setWin(false);
                break;
            }
        }
            return isWin();
    }

    public boolean checkColumn(){
        int[] arr = getCoords(callingButton.getName());
        for (int i = 0; i <3; i++) {
            if(buttons[i][arr[1]].getText().equals(callingButton.getText())) {
                setWin(true);
            }else{
                setWin(false);
                break;
            }
        }
        return isWin();
    }

    public boolean checkDiagonal(){
        int[][] conditionArray = {{0,2},{1,1},{2,0}};
        boolean check1 = false;

        for (int i = 0; i <3; i++) {
            if(buttons[conditionArray[i][0]][conditionArray[i][1]].getText().equals(callingButton.getText())) {
                check1 = true;
            }
            else {
                check1 = false;
                break;
            }
        }

        if(!check1){
            for (int i = 0; i <3; i++) {
                if(buttons[i][i].getText().equals(callingButton.getText())) {
                    check1 = true;
                }
                else {
                    check1 = false;
                    break;
                }
            }
        }

        if(check1) {
            setWin(true);
        }
        else {
            setWin(false);
        }
        return isWin();
    }

    public boolean checkWin(){

        if(checkRow()||checkColumn()||checkDiagonal()) {
            if(("Player".equals(getPlayerOne()) &&"X".equals(callingButton.getText()))||("PC".equals(getPlayerOne())&&"O".equals(callingButton.getText()))){
                int score =Integer.parseInt(labelArray[0].getText());
                score++;
                labelArray[0].setText(""+score);
                dbConnection();
                resetField();
                return true;
            }else
                if(("PC".equals(getPlayerOne())&&"X".equals(callingButton.getText()))||("Player".equals(getPlayerOne())&&"O".equals(callingButton.getText()))){
                    int score =Integer.parseInt(labelArray[1].getText());
                    score++;
                    labelArray[1].setText(""+score);
                    dbConnection();
                    resetField();
                    return true;
                }
        }else
            if(!isWin()&&getTurnCounter()==9) {
                int score = Integer.parseInt(labelArray[2].getText());
                score++;
                labelArray[2].setText("" + score);
                dbConnection();
                resetField();
                return true;
            }
        return false;
    }

    public void setCallingButton(Buttons button){
        this.callingButton = button;
    }

    public void resetField(){
        resetTurnCounter();
        setWin(false);
        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                resetButtonsText(i,j);
            }
        }
        if("Player".equals(getPlayerOne())) {
            setPlayerOne("PC");
        }
        else {
            setPlayerOne("Player");
        }
        bot=null;
    }

    public void resetButtonsText(int i, int j){
        this.buttons[i][j].setText("");
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void setTurnCounter() {
        this.turnCounter++;
    }

    public void resetTurnCounter() {
        this.turnCounter=0;
    }

    public void botTurn(){
        if (bot == null) {
            bot = new Bot();
            if ("PC".equals(getPlayerOne())) {
                bot.setBotSymbol("X");
            }
            else {
                bot.setBotSymbol("O");
            }
        }

        bot.addButtons(buttons);
        if(("PC".equals(getPlayerOne())&&turnCounter%2==0)||("Player".equals(getPlayerOne())&&turnCounter%2!=0)) {
            bot.setTurn(true);

            String[] randomArr = {"Random","random","RANDOM"};
            for (int i = 0; i <randomArr.length; i++) {
                if(randomArr[i].equals(textarea)){
                    bot.setRandomMove(true);
                    break;
                }else{
                    bot.setRandomMove(false);
                }
            }

            bot.go();
        }
    }

    public void dbConnection(){
        DBConnection db = new DBConnection();
        db.connect(persistenceUnitName);

        saveToDB(db.getEntityManager());

        db.disconnect(dbShutdownString);

    }

    public PlayerEntity getPlayerForSaveGame(EntityManager em){
        Query q = em.createQuery("select p from PlayerEntity p where p.name = "+"'"+textarea+"'");
        List<PlayerEntity> playerList = q.getResultList();

        PlayerEntity player = new PlayerEntity();
        if(playerList.size()>0) {
            for (PlayerEntity player1 : playerList) {
                if (textarea.equals(player1.getName()) || ("playerOne".equals(player1.getName()) && textarea.isEmpty())) {
                    player = player1;
                    break;
                }
            }
        }
        return player;
    }

    public void saveToDB(EntityManager em){
        if(textarea == null) {
            textarea = "playerOne";
        }
        PlayerEntity player = getPlayerForSaveGame(em);

        em.getTransaction().begin();
        GameEntity game = new GameEntity();
        String moves="";

        for (int i = 0; i <3; i++) {
            for (int j = 0; j <3; j++) {
                if(buttons[i][j].getText().isEmpty()) {
                    moves += " ";
                }
                else{
                    moves+=buttons[i][j].getText();
                }
            }
        }
        game.setMoves(moves);

        if("X".equals(bot.getBotSymbol())) {
            game.setPlayerSimbol("O");
        }
        else{
            game.setPlayerSimbol("X");
        }

        if(player.getName()==null) {
            player.setName(textarea);
            player.addToGameList(game);
            em.persist(player);
        }else{
            player.addToGameList(game);
        }

        game.setPlayer(player);
        em.persist(game);
        em.getTransaction().commit();
    }

    public String getTextarea() {
        return textarea;
    }

    public void setTextarea(String textarea) {
        this.textarea = textarea;
    }

    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public void setDbShutdownString(String dbShutdownString) {
        this.dbShutdownString = dbShutdownString;
    }

    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }
}
