package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

	public int worldX,worldY;
	public int speed;
	
	BufferedImage down1, down2, down3, down4, down5, down6, downidle1;
    BufferedImage up1, up2, up3, up4, up5, up6, upidle1;
    BufferedImage left1, left2, left3, left4, left5, left6, leftidle1;
    BufferedImage right1, right2, right3, right4, right5, right6, rightidle1;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1; 
	public Rectangle solidArea;
	public int solidAreaDefaultX,solidAreaDefaultY;
	public boolean collision = false;
}
