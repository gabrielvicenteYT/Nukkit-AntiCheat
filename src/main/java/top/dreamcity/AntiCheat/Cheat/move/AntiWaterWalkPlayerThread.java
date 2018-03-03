package top.dreamcity.AntiCheat.Cheat.move;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;
import top.dreamcity.AntiCheat.Cheat.Study.Study;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/12/9.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class AntiWaterWalkPlayerThread implements Runnable{
    private Thread thread;
    private Player player;
    private boolean onGround;

    public AntiWaterWalkPlayerThread(Player player,boolean onGround) {
        thread = new Thread(this);
        this.player = player;
        thread.start();
        this.onGround = onGround;
    }

    @Override
    public void run() {
        try {
            boolean flag = false;
            if (onGround) {
                double y = player.y;
                thread.sleep(3000);
                if ((player.y- y)> -0.01 && (player.y- y) < 0.01 && player.getLevel().getBlockIdAt((int)player.x, (int) player.y - 1, (int) player.z) == 0 && !player.isOnGround()) {
                    flag = true;
                    //System.out.println("water walk a");
                }
            } else {
                double y = player.y;
                thread.sleep(3000);
                if((player.y- y)> -0.01 && (player.y- y) < 0.01 && player.isOnGround()) {
                    int id =player.getLevel().getBlockIdAt((int)player.x, (int) player.y - 1, (int) player.z);
                    if (id == Block.WATER || id == Block.WATER_LILY || id == Block.STILL_WATER) {
                        //System.out.println("water walk b");
                        player.move(0, -1, 0);
                        y = player.y;
                        thread.sleep(1000);
                        if((player.y- y)> -0.01 && (player.y- y) < 0.01 && player.isOnGround()) {
                            int waterY = 1;
                            id = player.getLevel().getBlockIdAt((int) player.x, (int) player.y - waterY, (int) player.z);
                            while (id == 0 || id == Block.WATER || id == Block.WATER_LILY || id == Block.STILL_WATER) {
                                waterY++;
                            }
                            waterY -= 1;
                            player.teleport(new Vector3(player.x, player.y - waterY, player.z));
                        }
                    }
                }
            }
            if (flag) {
                //System.out.println("c");
                player.setMotion(new Vector3(0, 0, 0));
                player.teleport(new Vector3(player.x,player.y-1,player.z));
                player.move(0, -1, 0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
