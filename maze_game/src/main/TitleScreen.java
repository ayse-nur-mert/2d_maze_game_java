package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TitleScreen {
    private GamePanel gp;
    private BufferedImage backgroundImage;
    private Font titleFont;
    private Font normalFont;
    public int commandNum = 0;

    public TitleScreen(GamePanel gp) {
        this.gp = gp;
        titleFont = new Font("Arial", Font.BOLD, 60);
        normalFont = new Font("Arial", Font.PLAIN, 30);
        getTitleImage();
    }

    public void getTitleImage() {
        backgroundImage = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = backgroundImage.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.dispose();

        File file = new File("res/background/title_bg.png");
        if (file.exists()) {
            try {
                backgroundImage = ImageIO.read(file);
            } catch (IOException ignored) {}
        }
    }

    public void draw(Graphics2D g2) {
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            // Eğer resim hala null ise, siyah arka plan çiz
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        // Menü seçenekleri
        g2.setFont(normalFont);
        String text = "NEW GAME";
        int x = getXforCenteredText(text, g2);
        int y = gp.tileSize * 7;

        // NEW GAME yazısı
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // QUIT yazısı ve gölgesi
        text = "QUIT";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize;
        
        // QUIT yazısı
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    private int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
}
