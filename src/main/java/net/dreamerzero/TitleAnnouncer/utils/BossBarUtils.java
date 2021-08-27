package net.dreamerzero.TitleAnnouncer.utils;

import org.bukkit.scheduler.BukkitRunnable;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class BossBarUtils {
    public static BossBar activeBar;
    public static float value = 1f;

    public static void sendBossBar (
        final Audience audience,
        final float time,
        final Component content, 
        final BossBar.Color color, 
        final BossBar.Overlay type) {
        
        float finalTime = time/100;
        if (finalTime > 1){
            finalTime = 1;
        }
        final BossBar bar = BossBar.bossBar(
            content, 
            finalTime, 
            color, 
            type);
        activeBar = bar;
        audience.showBossBar(activeBar);
        //float toReduce = finalTime/100;
        final float toReduce = finalTime;
		value = 1f;
        
        
        new BukkitRunnable() {
            @Override
            public void run() {
                value -= toReduce;
                try {
					bar.progress(value);
				} catch (IllegalArgumentException e) {
                    audience.hideBossBar(activeBar);
                    activeBar = null;
					cancel();
				}
				if (bar.progress() <= 0.02) {
                    audience.hideBossBar(activeBar);
                    activeBar = null;
					cancel();
				}
            }
        }.runTaskTimer(Announcer.getInstance(), 20, 20);

        activeBar = null;
        
    }

    public void hideBossBar(
        final Audience audience) {

        audience.hideBossBar(activeBar);
        activeBar = null;
    }

    public void updateBossbarName(Component newname){
        activeBar.name(newname);
    }
}
