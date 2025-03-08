package main;

import objects.OBJ_Boots;
import objects.OBJ_Chest;
import objects.OBJ_Door;
import objects.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        // Level 1
        gp.obj.add(new OBJ_Key());
        gp.obj.get(0).worldX = 46 * gp.tileSize;
        gp.obj.get(0).worldY = 7 * gp.tileSize;

        gp.obj.add(new OBJ_Door());
        gp.obj.get(1).worldX = 49 * gp.tileSize;
        gp.obj.get(1).worldY = 12 * gp.tileSize;

        // Level 2
        gp.obj.add(new OBJ_Key());
        gp.obj.get(2).worldX = 42 * gp.tileSize;
        gp.obj.get(2).worldY = 17 * gp.tileSize;
        
        gp.obj.add(new OBJ_Key());
        gp.obj.get(3).worldX = 56 * gp.tileSize;
        gp.obj.get(3).worldY = 20 * gp.tileSize;

        gp.obj.add(new OBJ_Door());
        gp.obj.get(4).worldX = 49 * gp.tileSize;
        gp.obj.get(4).worldY = 26 * gp.tileSize;

        // Level 3
        gp.obj.add(new OBJ_Key());
        gp.obj.get(5).worldX = 49 * gp.tileSize;
        gp.obj.get(5).worldY = 33 * gp.tileSize;

        gp.obj.add(new OBJ_Key());
        gp.obj.get(6).worldX = 42 * gp.tileSize;
        gp.obj.get(6).worldY = 40 * gp.tileSize;
        
        gp.obj.add(new OBJ_Key());
        gp.obj.get(7).worldX = 55 * gp.tileSize;
        gp.obj.get(7).worldY = 38 * gp.tileSize;
        
        gp.obj.add(new OBJ_Boots());
        gp.obj.get(8).worldX = 49 * gp.tileSize;
        gp.obj.get(8).worldY = 27 * gp.tileSize;

        gp.obj.add(new OBJ_Door());
        gp.obj.get(9).worldX = 49 * gp.tileSize;
        gp.obj.get(9).worldY = 44 * gp.tileSize;
        
        // Level 4

        gp.obj.add(new OBJ_Key());
        gp.obj.get(10).worldX = 42 * gp.tileSize;
        gp.obj.get(10).worldY = 50 * gp.tileSize;

        gp.obj.add(new OBJ_Key());
        gp.obj.get(11).worldX = 58 * gp.tileSize;
        gp.obj.get(11).worldY = 49 * gp.tileSize;
        
        gp.obj.add(new OBJ_Key());
        gp.obj.get(12).worldX = 43 * gp.tileSize;
        gp.obj.get(12).worldY = 60 * gp.tileSize;
        
        gp.obj.add(new OBJ_Key());
        gp.obj.get(13).worldX = 40 * gp.tileSize;
        gp.obj.get(13).worldY = 63 * gp.tileSize;
        
        gp.obj.add(new OBJ_Door());
        gp.obj.get(14).worldX = 49 * gp.tileSize;
        gp.obj.get(14).worldY = 68 * gp.tileSize;
        
        // Level 5
        gp.obj.add(new OBJ_Key());
        gp.obj.get(15).worldX = 39 * gp.tileSize;
        gp.obj.get(15).worldY = 80 * gp.tileSize;

        gp.obj.add(new OBJ_Key());
        gp.obj.get(16).worldX = 57 * gp.tileSize;
        gp.obj.get(16).worldY = 83 * gp.tileSize;

        gp.obj.add(new OBJ_Key());
        gp.obj.get(17).worldX = 43 * gp.tileSize;
        gp.obj.get(17).worldY = 87 * gp.tileSize;
        
        gp.obj.add(new OBJ_Key());
        gp.obj.get(18).worldX = 35 * gp.tileSize;
        gp.obj.get(18).worldY = 91 * gp.tileSize;
        
        gp.obj.add(new OBJ_Key());
        gp.obj.get(19).worldX = 67 * gp.tileSize;
        gp.obj.get(19).worldY = 90 * gp.tileSize;
        
        gp.obj.add(new OBJ_Door());
        gp.obj.get(20).worldX = 49 * gp.tileSize;
        gp.obj.get(20).worldY = 95 * gp.tileSize;
        
        gp.obj.add(new OBJ_Chest());
        gp.obj.get(21).worldX = 49 * gp.tileSize;
        gp.obj.get(21).worldY = 97 * gp.tileSize;
    }
}