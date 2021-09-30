package net.dreamerzero.titleannouncer.common.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;

public class BossBarUtils {
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
        return switch (length) {
            case 0 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                yield false;
            }
            case 1 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                        ConfigManager.getConfig().getOrDefault(
                            "messages.bossbar.only-time",
                            "<gray>You must enter the color and the message arguments.</gray>"))));
                yield false;
            }
            case 2 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 3 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-message",
                        "<gray>You need to enter the message to announce.</gray>"))));
                yield false;
            }
            default -> true;
        };
    }

    public static boolean sendBossbarArgs(int length, Audience sender) {
        return switch (length) {
            case 0 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                yield false;
            }
            case 1 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.only-player",
                        "<gray>You must enter the message to be sent after the player's name.</gray>"))));
                yield false;
            }
            case 2 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color, overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 3 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 4 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-message",
                        "<gray>You need to enter the message to announce.</gray>"))));
                yield false;
            }
            default -> true;
        };
    }

    public static boolean proxyBossbarArgs(int length, Audience sender) {
        return switch (length) {
            case 0 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                yield false;
            }
            case 1 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.general.no-server-provided",
                        "<red>No server provided to send the message</red>"))));
                yield false;
            }
            case 2 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color, overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 3 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 4 -> {
                sender.sendMessage(
                ConfigUtils.getPrefix().append(MiniMessageUtil.parse(
                    ConfigManager.getConfig().getOrDefault(
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
