package org.pio.scene;

import org.pio.Entities.AllyTowers.AllyTower;
import org.pio.Entities.Enemies.Enemy;
import org.pio.main.GameScreen;
import org.pio.manager.AllyTowerManager;
import org.pio.manager.PlayerManager;
import org.pio.player.Player;
import org.pio.tiles.tTile;
import org.pio.helpz.KeyPoint;
import org.pio.main.Game;
import org.pio.readers.ReadFromFile;
import org.pio.helpz.Helper;
import org.pio.helpz.Readers;
import org.pio.helpz.Writers;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public class Level extends GameScene {
    private final int START_ROUND=0;
    private final int NUM_OF_ROUNDS;
    public static int currentRound;
    private static int lvlHeight, lvlWidth;
    private static tTile [][] lvlArr;
    private static List<Round> roundList;
    private static List<KeyPoint> keyPointsList;
    public static Rectangle tempRect;

    public Level(int lvlWidth, int lvlHeight, Game game, int numOfRounds) {
        super(game);
        this.NUM_OF_ROUNDS=numOfRounds;
        this.lvlWidth = lvlWidth;
        this.lvlHeight = lvlHeight;

        lvlArr = new tTile[lvlHeight][lvlWidth];

        currentRound=START_ROUND;
        roundList = new ArrayList<>();

        createLevelRoundsAndAddEnemies();

        Writers.writeEmptyLevel();
        Readers.readLevelDataFromTxt(Path.of("src/main/resources/LevelInfo/lvl_1.txt"));
    }

    // -------- INIT ------- //

    public static void initKeypoints(){
        keyPointsList=new ArrayList<>();
        keyPointsList.add(new KeyPoint(-25,6*GameScreen.UNIT_SIZE)); // 0
        keyPointsList.add(new KeyPoint(10*GameScreen.UNIT_SIZE, 6*GameScreen.UNIT_SIZE));
        keyPointsList.add(new KeyPoint(10*GameScreen.UNIT_SIZE, 2*GameScreen.UNIT_SIZE));
        keyPointsList.add(new KeyPoint(18*GameScreen.UNIT_SIZE, 2*GameScreen.UNIT_SIZE));

        tempRect=new Rectangle(keyPointsList.get(2).getWidthX(),keyPointsList.get(2).getHeightY(),GameScreen.UNIT_SIZE,GameScreen.UNIT_SIZE);
    }
    private void createLevelRoundsAndAddEnemies(){

        String pathFile = "src/main/resources/";
        String fileName = pathFile+ "RoundsInfo/rounds_data.txt";

        for (int i = 0; i <= NUM_OF_ROUNDS; i++) {
            Round round= ReadFromFile.readEnemyFromRoundDataFile(fileName,i);
            roundList.add(round);
        }
    }

    // -------- UPDATE ------- //

    public void updateLevel(){
        updateRoundCounter();
        updateMoveEnemies();
    }

    private void updateRoundCounter() {
        if (Helper.isFirstValueSmallerThanSecond(currentRound,NUM_OF_ROUNDS)){
            if (Helper.isEnemyListEmpty(getRoundList().get(currentRound).getEnemies())){
                currentRound++;
            }
        }
    }
    private void updateMoveEnemies() {

        if (Helper.isFirstValueSmallerThanSecond(Level.currentRound,getNUM_OF_ROUNDS())){
            updateStartMoveEnemies(roundList.get(currentRound).getEnemies());
        }
    }
    private void updateStartMoveEnemies(List<Enemy> enemies){

        if (Helper.isEnemyListEmpty(enemies)){
            return;
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();

            if (i < enemies.size() - 1) {

                if (enemies.get(i).getPosWidthX()- enemies.get(i+1).getPosWidthX()>=50&&!enemies.get(i+1).isCanGo()){
                    enemies.get(i+1).setCanGo(true);
                }

                if (enemies.get(i).getPosWidthX()>=Level.getKeyPointsList().get(Level.getKeyPointsList().size()-1).getWidthX()){
                    PlayerManager.updateHealth(PlayScene.getPlayer(),enemies.get(i).getHealth());
                    enemies.remove(enemies.get(i));
                }

            } else {
                if (enemies.get(i).getPosWidthX()>=Level.getKeyPointsList().get(Level.getKeyPointsList().size()-1).getWidthX()){

                    PlayerManager.updateHealth(PlayScene.getPlayer(),enemies.get(i).getHealth());
                    enemies.remove(enemies.get(i));

                }
            }
        }

    }

    // -------- DRAW ------- //

    public void drawLevel(Graphics g){
        drawTiles(g);
        drawEnemies(g);
        drawAllyTowerPlaced(g);
        drawRoundInfo(g);
    }
    private void drawTiles(Graphics g) {
        for (int i = 0; i < lvlHeight; i++) {
            for (int j = 0; j < lvlWidth; j++) {
                lvlArr[i][j].draw(g);
            }
        }

        g.setColor(Color.RED);
        g.fillRect(tempRect.x,tempRect.y,tempRect.width,tempRect.height);
    }
    private void drawRoundInfo(Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Round: " + (currentRound) + "/" + (NUM_OF_ROUNDS-1), 10, 20);

        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Player Health: " + Player.getHealth(), 10, 40);
        g.drawString("Player Money: " + Player.getGold(), 10, 60);
    }
    private void drawEnemies(Graphics g){
        if (currentRound < NUM_OF_ROUNDS){
            if (!roundList.get(currentRound).getEnemies().isEmpty()) {
                for (Enemy enemy : getRoundList().get(currentRound).getEnemies()) {
                    //g.drawRect(enemy.getEntityBounds().x, enemy.getEntityBounds().y, enemy.getEntityBounds().width, enemy.getEntityBounds().height);
                    //g.drawImage(enemy.getSprite(), enemy.getPosWidthX(), enemy.getPosHeightY(), enemy.getWidth(), enemy.getHeight(), null);
                    enemy.drawEntity(g);
                }
            }
        }
    }
    private void drawAllyTowerPlaced(Graphics g){

        if (AllyTowerManager.allyTowersPlaced!=null){

            for (AllyTower allyTower: AllyTowerManager.allyTowersPlaced){
                allyTower.draw(g);
            }
        }

    }

    // -------- GET ------- //

    public int getNUM_OF_ROUNDS() {
        return NUM_OF_ROUNDS;
    }
    public static int getLvlHeight() {
        return lvlHeight;
    }
    public static int getLvlWidth() {
        return lvlWidth;
    }
    public static tTile[][] getLvlArr() {
        return lvlArr;
    }
    public static int getCurrentRound() {
        return currentRound;
    }
    public static List<Round> getRoundList() {
        return roundList;
    }

    // -------- SET ------- //

    public static List<KeyPoint> getKeyPointsList() {
        return keyPointsList;
    }
}
