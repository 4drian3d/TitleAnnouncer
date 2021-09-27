package net.dreamerzero.titleannouncer.common.utils;

import org.bukkit.configuration.file.FileConfiguration;

import net.dreamerzero.titleannouncer.paper.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class ConfigUtils {
    private static final Announcer plugin = Announcer.getInstance();
    private static FileConfiguration config = plugin.getConfig();

    public static Component getPrefix(){
        if (config.getBoolean("messages.prefix.enabled", true)) {
            return MiniMessageUtil.parse(config.getString(
                "messages.prefix.line",
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        } else {
            return Component.empty();
        }
    }

    /*-----------------------------
    TITLE CONFIGURATION
    -----------------------------*/

    public static void sendTitleError(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.error",
                "<dark_red>An error occurred while sending the title. Be sure to use the ';' to separate the title and the subtitle.</dark_red>"))));
    }

    public static void sendTitleConfirmation(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.successfully",
                "<green>Title succesfully sended</green>"))));
    }

    public static void sendNoTitlePermission(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.no-permission",
                "<red>You do not have permission to execute this command</red>"))));
    }

    public static void sendNoArgumentMessage(Audience sender) {
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.without-argument",
                "<red>You need to enter the title and subtitle arguments.</red>"))));
    }

    public static void noTitlePlayerArgumentProvided(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.only-player",
                "<gray>You must enter the title and subtitle after the player's name to send the message correctly.</gray>"))));
    }

    public static void titlePlayerNotFoundMessage(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.title.player-not-found",
                "<red>Player not found</red>"))));
    }

    public static String getTitleSound(){
        return config.getString(
            "sounds.title.sound-id",
            "entity.experience_orb.pickup");
    }

    public static boolean isTitleSoundEnabled(){
        return config.getBoolean("sounds.title.enabled", true);
    }

    static float getTitleSoundVolume(){
        return config.getInt("sounds.title.volume", 10);
    }

    static float getTitleSoundPitch(){
        return config.getInt("sounds.title.pitch", 2);
    }

    public static void playTitleSound(Audience audience){
        if(isTitleSoundEnabled()){
            SoundUtil.playSound(
                getTitleSound(),
                audience,
                getTitleSoundVolume(),
                getTitleSoundPitch());
        }
    }

    /*-----------------------------
    ACTIONBAR CONFIGURATION
    -----------------------------*/
    public static void sendNoActionbarPermission(Audience sender){
        sender.sendMessage(
            getPrefix().append(MiniMessageUtil.parse(
                config.getString(
                    "messages.actionbar.no-permission",
                    "<red>You do not have permission to execute this command</red>"))));
    }

    public static void sendActionbarConfirmation(Audience sender){
        sender.sendMessage(
            getPrefix().append(MiniMessageUtil.parse(
                config.getString(
                    "messages.actionbar.successfully",
                    "<green>Actionbar succesfully sended</green>"))));
    }

    public static void noActionbarPlayerArgumentProvided(Audience sender){
        sender.sendMessage(
            getPrefix().append(MiniMessageUtil.parse(
                config.getString(
                    "messages.actionbar.only-player",
                    "<gray>You must enter the message to be sent after the player's name.</gray>"))));
    }

    public static void actionbarPlayerNotFoundMessage(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.actionbar.player-not-found",
                "<red>Player not found</red>"))));
    }

    public static String getActionbarSound(){
        return config.getString(
            "sounds.actionbar.sound-id",
            "entity.experience_orb.pickup");
    }

    public static boolean isActionbarSoundEnabled(){
        return config.getBoolean("sounds.actionbar.enabled", true);
    }

    static float getActionbarSoundVolume(){
        return config.getInt("sounds.actionbar.volume", 10);
    }

    static float getActionbarSoundPitch(){
        return config.getInt("sounds.actionbar.pitch", 2);
    }

    public static void playActionbarSound(Audience audience){
        if(isActionbarSoundEnabled()){
            SoundUtil.playSound(
                getActionbarSound(),
                audience,
                getActionbarSoundVolume(),
                getActionbarSoundPitch());
        }
    }

    /*-----------------------------
    BOSSBAR CONFIGURATION
    -----------------------------*/

    public static void sendNoBossbarPermission(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.bossbar.no-permission",
                "<red>You do not have permission to execute this command</red>"))));
    }

    public static void sendBossbarConfirmation(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.bossbar.successfully",
                "<green>Bossbar succesfully sended</green>"))));
    }

    public static void bossbarPlayerNotFoundMessage(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.bossbar.player-not-found",
                "<red>Player not found</red>"))));
    }

    public static String getBossbarSound(){
        return config.getString(
            "sounds.bossbar.sound-id",
            "entity.experience_orb.pickup");
    }

    public static boolean isBossbarSoundEnabled(){
        return config.getBoolean("sounds.bossbar.enabled", true);
    }

    static float getBossbarSoundVolume(){
        return config.getInt("sounds.bossbar.volume", 10);
    }

    static float getBossbarSoundPitch(){
        return config.getInt("sounds.bossbar.pitch", 2);
    }

    public static void playBossbarSound(Audience audience){
        if(isBossbarSoundEnabled()){
            SoundUtil.playSound(
                getBossbarSound(),
                audience,
                getBossbarSoundVolume(),
                getBossbarSoundPitch());
        }
    }

    /*
    GENERAL CONFIGURATION
    */
    public static void sendNoMainPermission(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.general.no-permission",
                "<red>You do not have permission to execute this command</red>"))));
    }

    public static void reloadMessage(Audience sender){
        sender.sendMessage(MiniMessageUtil.parse(
            config.getString(
                "messages.general.help-message", 
                "<white>Available Commands:</white>")));
    }

    public static void invalidCommand(Audience sender){
        sender.sendMessage(getPrefix().append(MiniMessageUtil.parse(
            config.getString(
                "messages.general.invalid-command",
                "<red>Unknown Command</red>"))));
    }

    public static void helpPrefix(Audience sender){
        sender.sendMessage(MiniMessageUtil.parse(
            config.getString(
                "messages.general.help-message", 
                "<white>Available Commands:</white>")));
    }
}
