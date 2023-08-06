package org.pio.entities.others;

import org.pio.database.MainDatabase;
import org.pio.entities.Enemy_5;
import org.pio.player.Directions;

public interface Enemy_5_Factory_Interface {

    default public Enemy_5 getInfoFromDatabase(MainDatabase mainDatabase, int enemyIndex) {
        return new Enemy_5(mainDatabase.enemyDatabase.get(enemyIndex).name,mainDatabase.enemyDatabase.get(enemyIndex).id,mainDatabase.enemyDatabase.get(enemyIndex).health,mainDatabase.enemyDatabase.get(enemyIndex).damage,mainDatabase.enemyDatabase.get(enemyIndex).gold,mainDatabase.enemyDatabase.get(enemyIndex).movementSpeed,mainDatabase.enemyDatabase.get(enemyIndex).width,mainDatabase.enemyDatabase.get(enemyIndex).height,mainDatabase.enemyDatabase.get(enemyIndex).sprites);
    }

    default public Enemy_5 createWithImage(Enemy_5 enemy_5, int posX, int posY, Directions direction){
        return new Enemy_5(enemy_5, posX, posY, direction);
    }

}