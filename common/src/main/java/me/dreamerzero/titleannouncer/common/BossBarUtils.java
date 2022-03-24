package me.dreamerzero.titleannouncer.common;

import net.kyori.adventure.audience.Audience;
import static net.kyori.adventure.bossbar.BossBar.*;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class BossBarUtils {
    private BossBarUtils(){}
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
            case "6", "notched_6" -> Overlay.NOTCHED_6;
            case "10", "notched_10" -> Overlay.NOTCHED_10;
            case "12", "notched_12" -> Overlay.NOTCHED_12;
            case "20", "notched_20" -> Overlay.NOTCHED_20;
            case "full", "progress" -> Overlay.PROGRESS;
            default -> null;
        };
    }

    public static float validBossbarNumber(String number, Audience sender){
        try {
            return Integer.parseInt(number);
        } catch (Exception e){
            sender.sendMessage(mm.deserialize("<dark_red>This is not a valid number"));
            return 0.1f;
        }
    }
}
