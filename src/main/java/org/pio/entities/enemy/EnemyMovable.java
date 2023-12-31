package org.pio.entities.enemy;

import org.pio.entities.entityInterfaces.Movable;
import org.pio.helpz.Directions;
import org.pio.level.Level;
import org.pio.scene.PlayScene;

public class EnemyMovable implements Movable {
    Enemy enemy;

    public EnemyMovable(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void move() {
            enemy.direction = calcDirection(enemy);

            if (enemy.direction != null) {

                switch (enemy.direction) {
                    case LEFT -> {
                        enemy.posX = enemy.posX - enemy.movementSpeed;
                        updateHitBox(enemy);
                    }

                    case RIGHT -> {
                        enemy.posX = enemy.posX + enemy.movementSpeed;
                        updateHitBox(enemy);
                    }

                    case UP -> {
                        enemy.posY = enemy.posY - enemy.movementSpeed;
                        updateHitBox(enemy);
                    }

                    case DOWN -> {
                        enemy.posY = enemy.posY + enemy.movementSpeed;
                        updateHitBox(enemy);
                    }

                }

            }
    }

    private Directions calcDirection(Enemy enemy){

        if (enemy.keyPointsStack.peek().getPosX()-enemy.posX>0){
            return Directions.RIGHT;
        }else if (enemy.keyPointsStack.peek().getPosX()-enemy.posX<0){
            return Directions.LEFT;
        }else if (enemy.keyPointsStack.peek().getPosY()-enemy.posY>0){
            return Directions.DOWN;
        }else if (enemy.keyPointsStack.peek().getPosY()-enemy.posY<0){
            return Directions.UP;
        }else {
            if (enemy.keypointIndex== PlayScene.lvl.getKeyPointsList().size()-1){
                return null;
            }else {
                enemy.keypointIndex++;
            }
            enemy.keyPointsStack.push(PlayScene.lvl.getKeyPointsList().get(enemy.keypointIndex));
            return calcDirection(enemy);
        }
    }

    private void updateHitBox(Enemy enemy){
        enemy.bounds.setBounds((int) enemy.posX, (int) enemy.posY,enemy.width,enemy.height);
    }
}
