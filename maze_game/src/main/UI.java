package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import objects.OBJ_Key;

public class UI {
    GamePanel gp;
    Font arial_40;
    BufferedImage keyImage;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    public String message = "";
    boolean messageOn = false;
    int messageCounter = 0;
    BufferedImage clockImage;

    public UI(GamePanel gp) {
        this.gp = gp;
        this.arial_40 = new Font("Arial", 0, 40);
        OBJ_Key key = new OBJ_Key();
        this.keyImage = key.image;
        
        
        try {
            clockImage = ImageIO.read(getClass().getResourceAsStream("/tiles/clock.png"));
        } catch (IOException e) {
            e.printStackTrace();   
        }
     }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
        messageCounter = 0;
    }

    public void draw(Graphics2D g2) {
        if (gameFinished) {
            g2.setFont(arial_40);
            g2.setColor(Color.black);  
            String text = "Oyun Bitti!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength / 2;
            int y = gp.screenHeight / 2;
            g2.drawString(text, x, y);
            // threadi(panel oynanabilirliği) sonlandır oyun bitsin
        } else {
            g2.setFont(arial_40);
            g2.setColor(Color.black);  
            g2.drawImage(keyImage, 24, 24, 48, 48, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);
            
            //zaman sayacı güncellemesi
            playTime += 0.016666666666666666;
            String timeText = "" + dFormat.format(playTime);
            g2.drawString(timeText, gp.screenWidth - 130, 65);

            // Oyuncunun seviyesini göster
            g2.setColor(Color.black);  
            g2.drawString("Level: " + gp.player.level, 24, 120);
            
            
         // clock.png resmini zaman sayacının yanında çiz
            int clockX = gp.screenWidth - 180;
            int clockY = 24;
            g2.drawImage(clockImage, clockX, clockY, 48, 48, null);
          

            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.setColor(Color.black);  
                g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
                messageCounter++;
                if (messageCounter > 120) { // 2 seconds (60 FPS * 2)
                    messageOn = false;
                    messageCounter = 0;
                }
            }
        }
    }

    public void showGameFinished() {
        gameFinished = true;
    }
} 