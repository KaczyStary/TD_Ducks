package org.pio.entities.ally;

import org.pio.helpz.Directions;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Ally_4 extends Ally{

    public Ally_4(String name, int id, int width, int height, int cost, int range, LinkedHashMap<Directions, LinkedList<String>> sprites) {
        super(name, id, width, height, cost, range, sprites);
    }

    public Ally_4(Ally ally, int posX, int posY, Directions direction) {
        super(ally, posX, posY, direction);

        this.shotUpdatesPerSec = 2.5;
        this.update =new AllyUpdate(this);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        super.draw(g);
    }
}
