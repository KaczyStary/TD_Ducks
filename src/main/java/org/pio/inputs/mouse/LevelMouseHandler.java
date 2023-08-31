package org.pio.inputs.mouse;

import org.pio.entities.ally.Ally;
import org.pio.scene.Level;

import java.util.Iterator;

public class LevelMouseHandler implements MouseHandler {
    Level level;

    public LevelMouseHandler(Level level) {
        this.level = level;
    }

    @Override
    public void leftMouseClicked(int x, int y) {
        if (level.allyPlacedTowers.isEmpty()){
            return;
        }

        for (Ally ally: level.allyPlacedTowers){
            ally.mouseHandler.leftMouseClicked(x,y);
        }

        for (Iterator<Ally> allyTowerPlacedIterator = level.allyPlacedTowers.iterator(); allyTowerPlacedIterator.hasNext();){
            Ally nextAlly = allyTowerPlacedIterator.next();

            if(nextAlly.bounds.contains(x,y)){
                nextAlly.pressed =true;
            }else {
                if (!nextAlly.sidePanelUpgrade.getSidePanelBounds().contains(x,y)){
                    nextAlly.pressed =false;
                    nextAlly.mouseOver=false;
                }

            }

        }
    }

    @Override
    public void rightMouseClicked(int x, int y) {
        if (level.allyPlacedTowers.isEmpty()){
            return;
        }

        for (Ally ally : level.allyPlacedTowers) {
            ally.mouseHandler.rightMouseClicked(x,y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        for (Ally ally : level.allyPlacedTowers) {
            ally.mouseHandler.mouseMoved(x,y);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (level.allyPlacedTowers.isEmpty()){
            return;
        }

        for (Ally ally : level.allyPlacedTowers) {
            ally.mouseHandler.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (level.allyPlacedTowers.isEmpty()){
            return;
        }

        for (Ally ally : level.allyPlacedTowers) {
            ally.mouseHandler.mouseReleased(x, y);
        }
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
