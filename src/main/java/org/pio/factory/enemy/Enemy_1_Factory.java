package org.pio.factory.enemy;

import org.pio.database.MainDatabase;
import org.pio.entities.enemy.Enemy;
import org.pio.entities.enemy.Enemy_1;
import org.pio.helpz.Directions;
import org.pio.helpz.KeyPoint;

public interface Enemy_1_Factory{

    default public Enemy getInfoFromDatabaseEnemy_1(int enemyIndex) {
        return new Enemy_1(
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).name,
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).id,
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).health,
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).damage,
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).gold,
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).movementSpeed,
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).width,
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).height,
                MainDatabase.getMainDatabase().enemyDatabase.get(enemyIndex).sprites);
    }

    default public Enemy createWithImageEnemy_1(int enemyIndex, int posX, int posY, Directions direction, KeyPoint startKeyPoint){
        return new Enemy_1(getInfoFromDatabaseEnemy_1(enemyIndex), posX, posY, direction, startKeyPoint);
    }

}
