package org.pio.inputs.mouse;

import org.pio.main.GameScreen;
import org.pio.scene.PlayScene;

public class PlaySceneMouseHandler implements MouseHandler {
    PlayScene playScene;

    public PlaySceneMouseHandler(PlayScene playScene) {
        this.playScene = playScene;
    }

    @Override
    public void leftMouseClicked(int x, int y) {
        if (x>54*GameScreen.UNIT_SIZE&&! playScene.mapEditMode){
            playScene.lvl.sidePanelGame.gameSidePanelMouseHandler.leftMouseClicked(x,y);

        }else if (x>54*GameScreen.UNIT_SIZE&& playScene.mapEditMode){
            //playScene.editSidePanel.mouseHandler.leftMouseClicked(x,y);
        }else if (x<54*GameScreen.UNIT_SIZE&& playScene.mapEditMode) {
            //playScene.editSidePanel.mouseHandler.leftMouseClicked(x,y);
        }


        if (x<54*GameScreen.UNIT_SIZE){
            playScene.lvl.mouseHandler.leftMouseClicked(x,y);

            if ( playScene.lvl.selectedTower!=null){

                if (! playScene.containsBoundsOfOtherTower( playScene.lvl.selectedTower)){
                    playScene.lvl.allyPlacedTowers.add( playScene.lvl.selectedTower);
                    playScene.lvl.selectedTower.placed=true;

                    playScene.player.setGold( playScene.player.getGold()- playScene.lvl.selectedTower.cost);
                }

                playScene.lvl.selectedTower=null;

            }
        }
    }

    @Override
    public void rightMouseClicked(int x, int y) {
        playScene.lvl.mouseHandler.rightMouseClicked(x,y);
    }

    @Override
    public void mouseMoved(int x, int y) {
        playScene.mouseX=x;
        playScene.mouseY=y;

        if (x>54* GameScreen.UNIT_SIZE&&!playScene.mapEditMode){
            playScene.lvl.sidePanelGame.gameSidePanelMouseHandler.mouseMoved(x,y);
            //sidePanel.mouseMoved(x,y);
        }else if (x>54*GameScreen.UNIT_SIZE&&playScene.mapEditMode){
            //playScene.editSidePanel.mouseHandler.mouseMoved(x,y);
        }

        if (x<54*GameScreen.UNIT_SIZE){
            playScene.lvl.mouseHandler.mouseMoved(x,y);
        }

        if (playScene.lvl.selectedTower!=null){

            playScene.lvl.selectedTower.posX=x;
            playScene.lvl.selectedTower.posY=y;

            playScene.lvl.selectedTower.bounds.x= (int) playScene.lvl.selectedTower.posX;
            playScene.lvl.selectedTower.bounds.y= (int) playScene.lvl.selectedTower.posY;

            int ellipseOffset=20;
            playScene.lvl.selectedTower.rangeEllipse.setFrame(playScene.lvl.selectedTower.posX-playScene.lvl.selectedTower.range+ellipseOffset, playScene.lvl.selectedTower.posY-playScene.lvl.selectedTower.range+ellipseOffset, playScene.lvl.selectedTower.range*2, playScene.lvl.selectedTower.range*2);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (x>54*GameScreen.UNIT_SIZE&&!playScene.mapEditMode){
            playScene.lvl.sidePanelGame.gameSidePanelMouseHandler.mousePressed(x,y);
        }else if (x>54*GameScreen.UNIT_SIZE&&playScene.mapEditMode){
            //playScene.editSidePanel.mouseHandler.mousePressed(x,y);
        }

        if (x<54*GameScreen.UNIT_SIZE){
            playScene.lvl.mouseHandler.mousePressed(x,y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        playScene.lvl.sidePanelGame.gameSidePanelMouseHandler.mouseReleased(x,y);
        //playScene.editSidePanel.mouseHandler.mouseReleased(x,y);
        playScene.lvl.mouseHandler.mouseReleased(x,y);
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
