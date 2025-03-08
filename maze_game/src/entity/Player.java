package entity;

import main.KeyHandler;
import main.GamePanel;
import objects.SuperObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    public int level = 1;
    private int[] keysRequired = {0, 1, 2, 3, 4, 5}; // Her seviyede gerekli anahtar sayısı

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }
    // AŞAĞIDAKi İKİ SAYIYI DEĞİŞTİREREK KARAKTERİN BAŞLANGIÇ KONUMUNU AYARLAYIN
    public void setDefaultValues() {
        worldX = gp.tileSize * 49;
        worldY = gp.tileSize * 7;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/on_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/on_2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/player/on_3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/player/on_4.png"));
            down5 = ImageIO.read(getClass().getResourceAsStream("/player/on_5.png"));
            down6 = ImageIO.read(getClass().getResourceAsStream("/player/on_6.png"));
            downidle1 = ImageIO.read(getClass().getResourceAsStream("/player/onidle1.png"));

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/arka_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/arka_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/player/arka_3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/player/arka_4.png"));
            up5 = ImageIO.read(getClass().getResourceAsStream("/player/arka_5.png"));
            up6 = ImageIO.read(getClass().getResourceAsStream("/player/arka_6.png"));
            upidle1 = ImageIO.read(getClass().getResourceAsStream("/player/arkaidle1.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/sol_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/sol_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/sol_3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/sol_4.png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/player/sol_5.png"));
            left6 = ImageIO.read(getClass().getResourceAsStream("/player/sol_6.png"));
            leftidle1 = ImageIO.read(getClass().getResourceAsStream("/player/solidle1.png"));

            right1 = ImageIO.read(getClass().getResourceAsStream("/player/sag_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/sag_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/sag_3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/sag_4.png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/player/sag_5.png"));
            right6 = ImageIO.read(getClass().getResourceAsStream("/player/sag_6.png"));
            rightidle1 = ImageIO.read(getClass().getResourceAsStream("/player/sagidle1.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // Hareket animasyonu
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            collision = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if (!collision) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum % 6) + 1;
                spriteCounter = 0;
            }
        } else {
            // Idle animasyonu
            spriteCounter++;
            if (spriteCounter > 30) {
                spriteNum = (spriteNum % 6) + 1;
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj.get(i).name;

            switch (objectName) {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj.set(i, null);
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey >= keysRequired[level]) {
                        gp.playSE(3);
                        gp.obj.set(i, null);
                        hasKey -= keysRequired[level];
                        gp.ui.showMessage("You opened the door!");
                        level++;
                        if (level < keysRequired.length) {
                            gp.ui.showMessage("Collect " + keysRequired[level] + " keys to open the next door.");
                        } else {
                            gp.ui.showMessage("You have completed all levels!");
                            gp.ui.gameFinished = false;
                        }
                    } else {
                        gp.ui.showMessage("You need " + keysRequired[level] + " keys to open this door!");
                    }
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj.set(i, null);
                    gp.ui.showMessage("Speed Up!");
                    break;
                case "Chest":
                    gp.playSE(4); // Play fanfare sound
                    gp.obj.set(i, null);
                    gp.ui.gameFinished = true;
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            switch (direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : (spriteNum == 3) ? up3 : (spriteNum == 4) ? up4 : (spriteNum == 5) ? up5 : up6;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : (spriteNum == 3) ? down3 : (spriteNum == 4) ? down4 : (spriteNum == 5) ? down5 : down6;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : (spriteNum == 3) ? left3 : (spriteNum == 4) ? left4 : (spriteNum == 5) ? left5 : left6;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : (spriteNum == 3) ? right3 : (spriteNum == 4) ? right4 : (spriteNum == 5) ? right5 : right6;
                    break;
            }
        } else {
            switch (direction) {
                case "down":
                    image = downidle1;
                    break;
                case "up":
                    image = upidle1;
                    break;
                case "left":
                    image = leftidle1;
                    break;
                case "right":
                    image = rightidle1;
                    break;
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}