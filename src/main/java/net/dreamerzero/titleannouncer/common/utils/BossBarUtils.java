package net.dreamerzero.titleannouncer.common.utils;

import org.bukkit.scheduler.BukkitRunnable;

import net.dreamerzero.titleannouncer.paper.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class BossBarUtils {
    private static float value = 1f;

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

    /**
     * Based on the argument given in the command, 
     * it will return the color of the specified bossbar.
     * @param color
     * @return the color of the specified bossbar.
     */
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

    /**
     * Based on the argument given in the command,
     * it will return the overlay/style of the specified bossbar.
     * @param overlay
     * @return the style of the bossbar
     */
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

    // It will return if the given arguments are correct for the command to work correctly.
    public static boolean regularBossbarArgs(int length, Audience sender) {
        // The command requires arguments to work
        switch (length) {
            case 0 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    Announcer.getInstance().getConfig().getString(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                return false;
            }
            case 1 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    Announcer.getInstance().getConfig().getString(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color and the message arguments.</gray>"))));
                return false;
            }
            case 2 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    Announcer.getInstance().getConfig().getString(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
                return false;
            }
            case 3 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    Announcer.getInstance().getConfig().getString(
                        "messages.bossbar.without-message",
                        "<gray>You need to enter the message to announce.</gray>"))));
                return false;
            }
            default -> {return true;}
        }
    }

    public static boolean sendBossbarArgs(int length, Audience sender) {
        Announcer plugin = Announcer.getInstance();
        return switch (length) {
            case 0 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                yield false;
            }
            case 1 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.only-player",
                        "<gray>You must enter the message to be sent after the player's name.</gray>"))));
                yield false;
            }
            case 2 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color, overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 3 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 4 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    plugin.getConfig().getString(
                        "messages.bossbar.without-message",
                        "<gray>You need to enter the message to announce.</gray>"))));
                yield false;
            }
            default -> true;
        };
    }

    public static float validBossbarNumber(String number, Audience sender){
        try {
            return Integer.parseInt(number);
        } catch (Exception e){
            sender.sendMessage(
                ConfigUtils.getPrefix().append(
                    MiniMessageUtil.parse("<dark_red>This is not a valid number")));
            return 0.1f;
        }
    }
}
