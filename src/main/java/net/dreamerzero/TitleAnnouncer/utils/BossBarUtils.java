package net.dreamerzero.titleannouncer.utils;

import org.bukkit.scheduler.BukkitRunnable;

import net.dreamerzero.titleannouncer.Announcer;
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
    public static void sendBukkitBossBar (
        final Audience audience,
        final float time,
        final Component content,
        final BossBar.Color color,
        final BossBar.Overlay type) {

        float finalTime = 0.1f/time;

        BossBar bar = BossBar.bossBar(
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

    public static BossBar.Color bossbarColor(String color) {
        return switch (color.toLowerCase()) {
            case "red" -> BossBar.Color.RED;
            case "blue" -> BossBar.Color.BLUE;
            case "green" -> BossBar.Color.GREEN;
            case "pink" -> BossBar.Color.PINK;
            case "purple" -> BossBar.Color.PURPLE;
            case "white" -> BossBar.Color.WHITE;
            case "yellow" -> BossBar.Color.YELLOW;
            default -> null;
        };
    }

    public static BossBar.Overlay bossbarOverlay(String overlay){
        return switch (overlay.toLowerCase()){
            case "6" -> BossBar.Overlay.NOTCHED_6;
            case "10" -> BossBar.Overlay.NOTCHED_10;
            case "12" -> BossBar.Overlay.NOTCHED_12;
            case "20" -> BossBar.Overlay.NOTCHED_20;
            case "full", "progress" -> BossBar.Overlay.PROGRESS;
            default -> null;
        };
    }
}
