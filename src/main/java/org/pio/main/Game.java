package org.pio.main;

import org.pio.database.MainDatabase;
import org.pio.manager.*;
import org.pio.scene.Level;
import org.pio.scene.PlayScene;
import org.pio.scene.PreGameScene;
import org.pio.tiles.TileManager;

import javax.swing.*;

public class Game extends JFrame implements Runnable {
    private MainDatabase mainDatabase;
    private PreGameScene preGameScene;
    public static GameStates gameStates;
    public Update update;
    private GameScreen gameScreen;
    private PlayerManager playerManager;
    private TileManager tileManager;
    private Render render;
    private PlayScene playScene;
    Thread gameThread_1;
    private double timePerUpdatePlayerAnimation;

    public static void main(String[] args) {
        Game game=new Game();
        game.gameScreen.initInputs();
        game.startThread();
    }

    public Game() {
        initClass();
        initWindow();

    }

    private void initClass(){
        mainDatabase=new MainDatabase();

        gameScreen=new GameScreen(this);
        gameStates=GameStates.GAME;

        render=new Render(this);
        update=new Update(this);

        Level.initKeypoints();

        tileManager=new TileManager();
        playerManager=new PlayerManager();

        playScene=new PlayScene(this);
        preGameScene=new PreGameScene(this);
    }

    private void initWindow(){
        add(gameScreen);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startThread(){
        gameThread_1 =new Thread(this){};
        gameThread_1.start();

        new Thread(() -> {

            while (true){
                update.gameUpdate();
            }
        }).start();

        new Thread(() -> {

            timePerUpdatePlayerAnimation =1_000_000_000.0/11.0;
            long lastUpdate=System.nanoTime();
            long now;
            while (true){
                now = System.nanoTime();

                if (now-lastUpdate>= timePerUpdatePlayerAnimation){
                    updateAnimations();
                    lastUpdate=now;
                }
            }
        }).start();

    }

    private void updateAnimations() {
        update.updateAnimations();
    }

    @Override
    public void run() {

        double timePerFrame=1_000_000_000.0/60.0;

        long lastFrame=System.nanoTime();

        long lastTimeCheck=System.currentTimeMillis();

        int frames=0;

        long now;

        while (true){
            now = System.nanoTime();

            if (now-lastFrame>=timePerFrame){
                repaint();
                lastFrame=now;
                frames++;
            }

            if (System.currentTimeMillis()-lastTimeCheck>=1000){
                System.out.println("T1, FPS: "+frames);
                frames=0;
                lastTimeCheck=System.currentTimeMillis();
            }

        }
    }


    // ----------- GET ----------- //

    public Render getRender() {
        return render;
    }
    public PlayScene getPlayScene() {
        return playScene;
    }

    public static GameStates getGameStates() {
        return gameStates;
    }

    public PreGameScene getPreGameScene() {
        return preGameScene;
    }

    public MainDatabase getMainDatabase() {
        return mainDatabase;
    }

    // ----------- SET ----------- //


    public static void setGameStates(GameStates gameStates) {
        Game.gameStates = gameStates;
    }

}