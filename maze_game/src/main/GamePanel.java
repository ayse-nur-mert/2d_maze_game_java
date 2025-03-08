package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import entity.Player;
import objects.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // MAP BOYUTUNU AŞAĞIDAKİ İKİ DEĞER İLE DEĞİŞİN
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 110;

    // FPS
    int FPS = 60;

    // SYSTEM
    public KeyHandler keyH;
    public Sound music = new Sound();
    public Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public TitleScreen titleScreen = new TitleScreen(this);
    Thread gameThread;
    public TileManager tileM = null;

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public ArrayList<SuperObject> obj = new ArrayList<>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.keyH = new KeyHandler(this);
        this.player = new Player(this, keyH);  
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        gameState = titleState;
        tileM = new TileManager(this, "/maps/output_matrix.txt");
        aSetter.setObject(); // Objeleri ayarla
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            player.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if(gameState == titleState) {
            titleScreen.draw(g2);
        }
        else if(gameState == playState) {
            // TILE
            tileM.draw(g2);

            // OBJECT
            for (SuperObject object : obj) {
                if (object != null) {
                    object.draw(g2, this);
                }
            }
            // PLAYER
            player.draw(g2);

            // UI
            ui.draw(g2);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}