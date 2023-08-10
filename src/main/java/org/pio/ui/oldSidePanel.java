package org.pio.ui;

import org.pio.entities.ally.Ally;
import org.pio.entities.factory.ally.AllyFactoryImpl;
import org.pio.scene.PlayScene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class oldSidePanel {
    private BufferedImage spriteSidePanel;
    private BufferedImage spriteButtonAtlas;
    private PlayScene playScene;
    private int width, height, posX, posY;
    Button bTower_0, editMode, startRound, speedUp;
    private List<Button> buttonTowerList;
    private AllyFactoryImpl allyFactory;

    public oldSidePanel(int posX, int posY, int width, int height, PlayScene playScene) {
        this.width =width;
        this.height =height;
        this.posX =posX;
        this.posY =posY;
        this.playScene=playScene;

        spriteButtonAtlas=playScene.getGame().getMainDatabase().spriteAtlasDatabase.get("Buttons");
        spriteSidePanel=playScene.getGame().getMainDatabase().spriteAtlasDatabase.get("SidePanel");

        allyFactory=new AllyFactoryImpl(playScene.getGame().getMainDatabase());

        initButtons();
    }

    // -------- INIT ------- //

    public void initButtons(){
        buttonTowerList = new ArrayList<>();

        int id =0;

        int posX=this.posX +10;
        int posY=29;
        int bWidth=80;
        int bHeight=59;
        int posYOffSet=bHeight+7;


        for (int i = 0; i < 5; i++) {
            int index=1;

            bTower_0 =new Button(playScene.getGame().getMainDatabase().allyDatabase.get(index).name, posX, posY+id*posYOffSet, bWidth, bHeight, id++, playScene.getGame().getMainDatabase().allyDatabase.get(index).cost, getButtonSprite(0,0,160,80),getButtonSprite(0,1,160,80), getButtonSprite(0,2,160,80));
            buttonTowerList.add(bTower_0);
        }

        editMode=new Button("Edit_Mode", posX, height -4*bHeight, bWidth, bHeight, id++, getButtonSprite(0,3,160,80),getButtonSprite(0,4,160,80), getButtonSprite(0,5,160,80));
        speedUp=new Button("Speed_Up", posX, height -3*bHeight, bWidth, bHeight, id++, getButtonSprite(0,3,160,80),getButtonSprite(0,4,160,80), getButtonSprite(0,5,160,80));
        startRound =new Button("Start_Round", posX, height -2*bHeight, bWidth, bHeight, id++, getButtonSprite(0,3,160,80),getButtonSprite(0,4,160,80), getButtonSprite(0,5,160,80));

    }

    // -------- RENDER ------- //

    public void drawButtons(Graphics g){

        for (Button button : buttonTowerList) {
            button.drawRectangleButton(g);
        }

        editMode.drawRectangleButton(g);
        speedUp.drawRectangleButton(g);
        startRound.drawRectangleButton(g);

    }

    public void draw(Graphics g){
        drawPanel(g);
        drawButtons(g);
    }

    private void drawPanel(Graphics g) {
        g.drawImage(spriteSidePanel, posX, posY, width, height,null);
    }

    // -------- INPUTS ------- //

    public void mouseClicked(int x, int y) {


        for (Iterator<Button> buttonIterator = buttonTowerList.iterator(); buttonIterator.hasNext(); ) {
            Button button = buttonIterator.next();

            if (button.getButtonsBounds().contains(x, y)) {

                if (button.getId() == 0) {

                    if(playScene.getGame().getMainDatabase().allyDatabase.get(1).cost>playScene.getPlayer().getGold()){
                        return;
                    }

                    playScene.getLvl().selectedTower = allyFactory.createAlly_1(x, y, null);
                }

                if (button.getId() == 1) {

                    if(playScene.getGame().getMainDatabase().allyDatabase.get(2).cost>playScene.getPlayer().getGold()){
                        return;
                    }

                    playScene.getLvl().selectedTower = allyFactory.createAlly_2(x, y, null);
                }

                if (button.getId() == 2) {

                    if(playScene.getGame().getMainDatabase().allyDatabase.get(3).cost>playScene.getPlayer().getGold()){
                        return;
                    }

                    playScene.getLvl().selectedTower = allyFactory.createAlly_3(x, y, null);
                }

                if (button.getId() == 3) {

                    if(playScene.getGame().getMainDatabase().allyDatabase.get(4).cost>playScene.getPlayer().getGold()){
                        return;
                    }

                    playScene.getLvl().selectedTower = allyFactory.createAlly_4(x, y, null);
                }

                if (button.getId() == 4) {

                    if(playScene.getGame().getMainDatabase().allyDatabase.get(5).cost>playScene.getPlayer().getGold()){
                        return;
                    }

                    playScene.getLvl().selectedTower = allyFactory.createAlly_5(x, y, null);
                }

            }
        }

        if (editMode.getButtonsBounds().contains(x,y)){

            if (!playScene.isMapEditMode()){
                playScene.setMapEditMode(true);
            }else {
                playScene.setMapEditMode(false);
            }

        }

        if (startRound.getButtonsBounds().contains(x,y)){
            playScene.startWave();
        }

        if (speedUp.getButtonsBounds().contains(x,y)){
            playScene.changeGameSpeed();
        }

    }
    public void mouseMoved(int x, int y) {

        for (Button button : buttonTowerList) {
            if (button.isMouseOver()){
                button.setMouseOver(false);
            }
        }

        if (startRound.isMouseOver()){
            startRound.setMouseOver(false);
        }

        if (speedUp.isMouseOver()){
            speedUp.setMouseOver(false);
        }

        if (editMode.isMouseOver()){
            editMode.setMouseOver(false);
        }

        for (Button button : buttonTowerList) {
            if (button.getButtonsBounds().contains(x,y)){
                button.setMouseOver(true);
            }
        }

        if (editMode.getButtonsBounds().contains(x,y)){
            editMode.setMouseOver(true);
        }

        if (startRound.getButtonsBounds().contains(x,y)){
            startRound.setMouseOver(true);
        }

        if (speedUp.getButtonsBounds().contains(x,y)){
            speedUp.setMouseOver(true);
        }

    }
    public void mousePressed(int x, int y) {

        for (Button button : buttonTowerList) {
            if (button.getButtonsBounds().contains(x,y)){
                button.setMousePressed(true);
            }
        }

        if (editMode.getButtonsBounds().contains(x,y)){
            editMode.setMousePressed(true);
        }

        if (startRound.getButtonsBounds().contains(x,y)){
            startRound.setMousePressed(true);
        }

        if (speedUp.getButtonsBounds().contains(x,y)){
            speedUp.setMousePressed(true);
        }
    }
    public void mouseReleased(int x, int y) {

        for (Button button : buttonTowerList) {
            button.resetBooleans();
        }

        editMode.resetBooleans();
        startRound.resetBooleans();
        speedUp.resetBooleans();

    }

    private BufferedImage getButtonSprite(int xCord, int yCord, int widthImg,int heightImg){
        return spriteButtonAtlas.getSubimage(xCord*160,yCord*80,widthImg,heightImg);
    }




}
