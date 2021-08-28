package net.dreamerzero.TitleAnnouncer.utils;

import org.bukkit.scheduler.BukkitRunnable;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class BossBarUtils {
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
        audience.showBossBar(bar);
        final float toReduce = finalTime;
		value = 1f;
        
        
        new BukkitRunnable() {
            @Override
            public void run() {
                value -= toReduce;
                if (value <= 0.02) {
                    audience.hideBossBar(bar);
					cancel();
				}
                try {
					bar.progress(value);
				} catch (IllegalArgumentException e) {
					// shhh
				}
            }
        }.runTaskTimer(Announcer.getInstance(), 20, 20);

        
    }
}
