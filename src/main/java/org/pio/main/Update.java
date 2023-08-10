package org.pio.main;

import org.pio.entities.Bullet;
import org.pio.entities.Entity;
import org.pio.entities.ally.Ally;
import org.pio.entities.enemy.Enemy;
import org.pio.helpz.Helper;
import org.pio.manager.PlayerManager;
import org.pio.scene.Level;
import org.pio.scene.PlayScene;

import java.util.Iterator;
import java.util.List;

import static org.pio.helpz.Helper.distanceBetweenTwoPoints;
import static org.pio.scene.Level.currentRound;

public class Update {
    private Game game;
    public double timePerUpdateGame, timePerUpdateAllyShot;
    private long lastGameUpdate, lastAllyShotUpdate;
    private long lastTimeGameUpdateCheck;
    private long now;
    private int updateCounter;

    public Update(Game game) {
        this.game = game;

        this.timePerUpdateGame =1_000_000_000.0/120.0;
        this.timePerUpdateAllyShot=1_000_000_000.0/1.0;
        this.lastGameUpdate =System.nanoTime();
        this.lastAllyShotUpdate=System.nanoTime();
        this.lastTimeGameUpdateCheck =System.currentTimeMillis();
        this.updateCounter=0;
    }

    public void update(){

        now = System.nanoTime();

        if (now- lastGameUpdate >= timePerUpdateGame){

            if (game.getGameStates() == GameStates.PREGAME){
                game.getPreGameScene().update();
            }

            if (game.getGameStates() == GameStates.GAME){
                updateMoveEnemies();

                if (now-lastAllyShotUpdate>=timePerUpdateAllyShot){
                    updateAllyTowerPlaced(Level.allyPlacedTowers);
                    lastAllyShotUpdate=now;
                }

                bulletsUpdatePos(Level.allyPlacedTowers);
                checkIfEnemyIsHitByBullet(Level.rounds.get(currentRound).getEnemies(),Level.allyPlacedTowers);

                //game.getPlayScene().getLvl().checkIfEnemyIsHitByBullet();

                game.getPlayScene().update();
            }

            lastGameUpdate = now;
            updateCounter++;
        }

        if (System.currentTimeMillis()- lastTimeGameUpdateCheck >=1000){
            System.out.println("T2, GAME UPDATE: " + updateCounter);
            updateCounter =0;
            lastTimeGameUpdateCheck =System.currentTimeMillis();
        }


    }

    public void updateAnimations(){
        if (game.getGameStates() == GameStates.PREGAME){
            game.getPreGameScene().updateAnimations();
        }

        if (game.getGameStates() == GameStates.GAME){

        }
    }

    private void updateMoveEnemies() {

        if (Helper.isFirstValueSmallerThanSecond(Level.currentRound,game.getPlayScene().getLvl().getNUM_OF_ROUNDS())){
            updateStartMoveEnemies(Level.rounds.get(Level.currentRound).getEnemies());
        }
    }
    private void updateStartMoveEnemies(List<Enemy> enemies){

        if (enemies.isEmpty()){
            return;
        }

        for (int i = 0; i < enemies.size(); i++) {

            enemies.get(i).update();

            if (i < enemies.size() - 1) {

                if (enemies.get(i).posX>=Level.getKeyPointsList().get(Level.getKeyPointsList().size()-1).getWidthX()){
                    PlayerManager.updateHealth(PlayScene.getPlayer(),enemies.get(i).health);
                    enemies.remove(enemies.get(i));
                }

            } else {
                if (enemies.get(i).posX>=Level.getKeyPointsList().get(Level.getKeyPointsList().size()-1).getWidthX()){

                    PlayerManager.updateHealth(PlayScene.getPlayer(),enemies.get(i).health);
                    enemies.remove(enemies.get(i));

                }
            }
        }

    }
    private void updateAllyTowerPlaced(List<Ally> allies){

        if (allies.isEmpty()){
            return;
        }

        for (Ally ally: allies) {
            ally.lookForEnemiesInRange(Level.rounds.get(currentRound).getEnemies());
            ally.update();
        }

    }
    private void bulletsUpdatePos(List<Ally> allies) {

        if (allies.isEmpty()){
            return;
        }

        // ITERATE THROUGH ALLY TOWER PLACED

        for (Iterator<Ally> allyTowerIterator = allies.iterator(); allyTowerIterator.hasNext();) {
            Ally nextAlly = allyTowerIterator.next();

            if (!Helper.isBulletListEmpty(nextAlly.bulletList)){
                // ITERATE THROUGH BULLET LIST OF EACH ALLY TOWER

                for (Iterator<Bullet> bulletIterator = nextAlly.bulletList.iterator(); bulletIterator.hasNext();) {
                    Bullet nextBullet = bulletIterator.next();

                    // UPDATE BULLET
                    nextBullet.bulletUpdate();

                    // CHECK IF BULLET IS OUT OF RANGE OF ALLY TOWER
                    if (limitBulletRange(nextAlly, nextBullet)){
                        bulletIterator.remove();
                    }

                }
            }

        }

    }
    private Boolean limitBulletRange(Ally ally, Bullet Bullet){
        return distanceBetweenTwoPoints(ally.posX, ally.posY, Bullet.getPosX(), Bullet.getPosY()) >= ally.range + 15;
    }
    private void checkIfEnemyIsHitByBullet(List<Enemy> enemies, List<Ally> allies) {

        if (enemies.isEmpty()){
            return;
        }

        if (allies.isEmpty()){
            return;
        }

        // CHECK IF ANY ENEMY FROM CURRENT ROUND IS HIT BY ANY BULLET FROM ANY TOWER
        // REMOVE ENEMY FROM CURRENT ROUND ENEMY LIST
        // REMOVE BULLET FROM TOWER BULLET LIST AND REMOVE ENEMY FROM ALL TOWER ENEMY IN RANGE LIST

        // GO THROUGHT ALL ENEMIES FROM CURRENT ROUND
        for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
            Enemy nextEnemy = enemyIterator.next();

            // GO THROUGHT ALL PLACED TOWERS
            for (Iterator<Ally> allyTowerIterator = allies.iterator(); allyTowerIterator.hasNext();){
                Ally nextAlly = allyTowerIterator.next();

                // CHECK IF TOWER HAS ANY BULLETS
                if (!nextAlly.bulletList.isEmpty()){

                    // GO THROUGHT ALL BULLETS FROM TOWER
                    for (Iterator<Bullet> bulletIterator = nextAlly.bulletList.iterator(); bulletIterator.hasNext();){
                        Bullet nextBullet = bulletIterator.next();

                        // CHECK IF ENEMY IS HIT BY BULLET
                        if (nextEnemy.bounds.getBounds().intersects(nextBullet.getBulletHitBox())){

                            nextEnemy.health=nextEnemy.health-1;

                            // REMOVE BULLET FROM TOWER BULLET LIST
                            bulletIterator.remove();

                            if (nextEnemy.health<=0){
                                // REMOVE ENEMY FROM CURRENT ROUND ENEMY LIST
                                enemyIterator.remove();

                                // ADD GOLD TO PLAYER
                                PlayerManager.updateGoldAfterKill(PlayScene.getPlayer(), nextEnemy.gold);

                                // GO THROUGH ALL PLAYERS TOWERS
                                for (Iterator<Ally> allyTowerIterator1 = allies.iterator(); allyTowerIterator1.hasNext();){
                                    Ally nextOldAllyTower1 = allyTowerIterator1.next();

                                    // GO THROUGH ALL ENEMIES IN RANGE LIST
                                    for (Iterator<Entity> enemyIterator1 = nextOldAllyTower1.enemiesInRangeList.iterator(); enemyIterator1.hasNext();){
                                        Entity nextEnemy_1 = enemyIterator1.next();

                                        // REMOVE ENEMY FROM ALL TOWER ENEMY IN RANGE LIST
                                        if (nextOldAllyTower1.enemiesInRangeList.contains(nextEnemy_1)){
                                            enemyIterator1.remove();
                                        }

                                    }
                                }

                            }


                            return;


                        }

                    }
                }
            }

        }
    }

}
