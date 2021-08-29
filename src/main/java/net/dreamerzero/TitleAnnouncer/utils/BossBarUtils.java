package net.dreamerzero.TitleAnnouncer.utils;

import org.bukkit.scheduler.BukkitRunnable;

import net.dreamerzero.TitleAnnouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class BossBarUtils {
    public static float value = 1f;

    /**
     * It will send a bossbar to the specified audience, 
     * with the specified characteristics showing an 
     * emptying animation according to the specified 
     * time interval.
     * @param audience
     * @param time
     * @param content
     * @param color
     * @param type
     */
    public static void sendBossBar (
        final Audience audience,
        final float time,
        final Component content, 
        final BossBar.Color color, 
        final BossBar.Overlay type) {
        
        final float finalTime = 0.1f/time;

        final BossBar bar = BossBar.bossBar(
            content, 
            1, 
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
					cancel();
				}
            }
        }.runTaskTimerAsynchronously(Announcer.getInstance(), 20, 2);
    }
}
