package org.pio.database;

import org.pio.Entities.Enemy;
import org.pio.Entities.Enemy_1;
import org.pio.helpz.ReadFromFileImpl;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

public class MainDatabase {
    LinkedHashMap<Integer, Enemy> enemyDatabase = new LinkedHashMap<>();
    LinkedHashMap<Integer, BufferedImage> spriteAtlasDatabase = new LinkedHashMap<>();
    public MainDatabase(){

    }

    private Enemy getEnemyInfoFromTxtFile(String fileName){
        EnemyDatabaseImpl enemyDatabase = new EnemyDatabaseImpl();

        switch (fileName){
            case "src/main/resources/EnemiesInfo/FirstEnemy.txt":
                return new Enemy_1(enemyDatabase.getName(fileName), enemyDatabase.getId(fileName), enemyDatabase.getHealth(fileName), enemyDatabase.getDamage(fileName), enemyDatabase.getGold(fileName), enemyDatabase.getMovementSpeed(fileName), enemyDatabase.getWidth(fileName), enemyDatabase.getHeight(fileName), null);
            default:
                return null;
        }
    }

    private BufferedImage getSpriteAtlas(String fileName){
        ReadFromFileImpl readFromFile = new ReadFromFileImpl();

        return readFromFile.readBufferedImage(fileName);
    }

    public static void main(String[] args) {

    }
}
