package org.pio.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract class Entity {
    public int width, height, posX, posY, id;
    public String name;
    public Rectangle bounds;
    private BufferedImage sprite;

    protected Entity(String name, int id, int width, int height){
        this.name = name;
        this.id = id;
        this.width = width;
        this.height = height;
    }

    protected Entity(Entity entity, int posX, int posY){
        this.name = entity.name;
        this.id = entity.id;
        this.width = entity.width;
        this.height = entity.height;
        this.posX = posX;
        this.posY = posY;

        bounds=initRectangleBounds();
    }

    private Rectangle initRectangleBounds(){
        return new Rectangle(posX, posY, width, height);
    }

    public void draw(Graphics g){
        g.setColor(Color.red);
        g.fillRect(bounds.getBounds().x, bounds.getBounds().y, bounds.getBounds().width, bounds.getBounds().height);
    }

}