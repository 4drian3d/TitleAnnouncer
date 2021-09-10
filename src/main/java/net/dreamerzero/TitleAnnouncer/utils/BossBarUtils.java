package net.dreamerzero.titleannouncer.utils;

import org.bukkit.scheduler.BukkitRunnable;

import net.dreamerzero.titleannouncer.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

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

    public static BossBar.Color bossbarColor(String color, Audience sender, Component prefix) {
        switch (color) {
            case "RED": case "red": return BossBar.Color.RED;
            case "BLUE": case "blue": return BossBar.Color.BLUE;
            case "GREEN": case "green": return BossBar.Color.GREEN;
            case "PINK": case "pink": return BossBar.Color.PINK;
            case "PURPLE": case "purple": return BossBar.Color.PURPLE;
            case "WHITE": case "white": return BossBar.Color.WHITE;
            case "YELLOW": case "yellow": return BossBar.Color.YELLOW;
            default: sender.sendMessage(prefix.append(Component.text("Invalid Color Argument", NamedTextColor.DARK_RED))); return null;
        }
    }

    public static BossBar.Overlay bossbarOverlay(String overlay, Audience sender, Component prefix){
        switch (overlay){
            case "6": return BossBar.Overlay.NOTCHED_6;
            case "10": return BossBar.Overlay.NOTCHED_10;
            case "12": return BossBar.Overlay.NOTCHED_12;
            case "20": return BossBar.Overlay.NOTCHED_20;
            case "full": case "progress": return BossBar.Overlay.PROGRESS;
            default: sender.sendMessage(prefix.append(Component.text("Invalid Argument", NamedTextColor.DARK_RED))); return null;
        }
    }
}
