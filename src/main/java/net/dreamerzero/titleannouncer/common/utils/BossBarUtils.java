package net.dreamerzero.titleannouncer.common.utils;

import net.kyori.adventure.audience.Audience;
import static net.kyori.adventure.bossbar.BossBar.*;

import java.util.Locale;
import java.util.regex.PatternSyntaxException;

import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.minimessage.MiniMessage;

public class BossBarUtils {
    private static final MiniMessage mm = MiniMessage.miniMessage();
    /**
     * Based on the argument given in the command, 
     * it will return the color of the specified bossbar.
     * @param color
     * @return the color of the specified bossbar.
     */
    public static Color bossbarColor(String color) {
        return switch (color.toLowerCase()) {
            case "red" -> Color.RED;
            case "blue" -> Color.BLUE;
            case "green" -> Color.GREEN;
            case "pink" -> Color.PINK;
            case "purple" -> Color.PURPLE;
            case "white" -> Color.WHITE;
            case "yellow" -> Color.YELLOW;
            default -> null;
        };
    }

    /**
     * Based on the argument given in the command,
     * it will return the overlay/style of the specified bossbar.
     * @param overlay
     * @return the style of the bossbar
     */
    public static Overlay bossbarOverlay(String overlay){
        return switch (overlay.toLowerCase()){
            case "6" -> Overlay.NOTCHED_6;
            case "10" -> Overlay.NOTCHED_10;
            case "12" -> Overlay.NOTCHED_12;
            case "20" -> Overlay.NOTCHED_20;
            case "full", "progress" -> Overlay.PROGRESS;
            default -> null;
        };
    }

    // It will return if the given arguments are correct for the command to work correctly.
    public static boolean regularBossbarArgs(int length, @NotNull Audience sender) {
        // The command requires arguments to work
        return switch (length) {
            case 0 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                yield false;
            }
            case 1 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                            "messages.bossbar.only-time",
                            "<gray>You must enter the color and the message arguments.</gray>"))));
                yield false;
            }
            case 2 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 3 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-message",
                        "<gray>You need to enter the message to announce.</gray>"))));
                yield false;
            }
            default -> true;
        };
    }

    public static boolean sendBossbarArgs(int length, @NotNull Audience sender) {
        return switch (length) {
            case 0 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                yield false;
            }
            case 1 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.only-player",
                        "<gray>You must enter the message to be sent after the player's name.</gray>"))));
                yield false;
            }
            case 2 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                    ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color, overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 3 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 4 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
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
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.without-argument",
                        "<red>You need to enter the time, color and message arguments.</red>"))));
                yield false;
            }
            case 1 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.general.no-server-provided",
                        "<red>No server provided to send the message</red>"))));
                yield false;
            }
            case 2 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.only-time",
                        "<gray>You must enter the color, overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 3 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
                        ConfigManager.getConfig().getOrDefault(
                        "messages.bossbar.overlay-missing",
                        "<gray>You must enter the overlay and the message arguments.</gray>"))));
                yield false;
            }
            case 4 -> {
                sender.sendMessage(
                    ConfigUtils.getPrefix().append(mm.deserialize(
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
            sender.sendMessage(ConfigUtils.getPrefix().append(
                mm.deserialize("<dark_red>This is not a valid number")));
            return 0.1f;
        }
    }

    public static boolean isValidArgument(@NotNull String argument){
        if(argument.equalsIgnoreCase("all")){
            return true;
        } else {
            try{
                var splittedargument = argument.split(":");
                return switch(splittedargument[0].toLowerCase(Locale.ROOT)){
                    case "server", "player", "self", "me" -> true;
                    default -> false;
                };
            } catch (PatternSyntaxException exception){
                return false;
            }
        }
    }

    private BossBarUtils(){}
}
