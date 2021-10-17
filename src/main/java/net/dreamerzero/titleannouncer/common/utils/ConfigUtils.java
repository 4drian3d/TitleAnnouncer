package net.dreamerzero.titleannouncer.common.utils;

import de.leonhard.storage.Yaml;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ConfigUtils {
    private static Yaml config = new ConfigManager().getConfig();
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component getPrefix(){
        if (config.getOrDefault("messages.prefix.enabled", true)) {
            return mm.parse(config.getOrDefault(
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
        sender.sendMessage(getPrefix().append(mm.parse(
            config.getOrDefault(
                "messages.title.error",
                "<dark_red>An error occurred while sending the title. Be sure to use the ';' to separate the title and the subtitle.</dark_red>"))));
    }

    public static void sendNoArgumentMessage(Audience sender) {
        sender.sendMessage(getPrefix().append(mm.parse(
            config.getOrDefault(
                "messages.title.without-argument",
                "<red>You need to enter the title and subtitle arguments.</red>"))));
    }

    public static void noTitlePlayerArgumentProvided(Audience sender){
        sender.sendMessage(getPrefix().append(mm.parse(
            config.getOrDefault(
                "messages.title.only-player",
                "<gray>You must enter the title and subtitle after the player's name to send the message correctly.</gray>"))));
    }

    public static String getTitleSound(){
        return config.getOrDefault(
            "sounds.title.sound-id",
            "entity.experience_orb.pickup");
    }

    public static boolean isTitleSoundEnabled(){
        return config.getOrDefault("sounds.title.enabled", true);
    }

    public static float getTitleSoundVolume(){
        return config.getOrDefault("sounds.title.volume", 10);
    }

    public static float getTitleSoundPitch(){
        return config.getOrDefault("sounds.title.pitch", 2);
    }

    /*-----------------------------
    ACTIONBAR CONFIGURATION
    -----------------------------*/

    public static void noActionbarArgumentProvided(Audience sender){
        sender.sendMessage(
            getPrefix().append(mm.parse(
                config.getOrDefault(
                    "messages.actionbar.without-argument",
                    "<red>You need to enter the message to announce.</red>"))));
    }

    public static void noActionbarPlayerArgumentProvided(Audience sender){
        sender.sendMessage(
            getPrefix().append(mm.parse(
                config.getOrDefault(
                    "messages.actionbar.only-player",
                    "<gray>You must enter the message to be sent after the player's name.</gray>"))));
    }

    public static String getActionbarSound(){
        return config.getOrDefault(
            "sounds.actionbar.sound-id",
            "entity.experience_orb.pickup");
    }

    public static boolean isActionbarSoundEnabled(){
        return config.getOrDefault("sounds.actionbar.enabled", true);
    }

    public static float getActionbarSoundVolume(){
        return config.getOrDefault("sounds.actionbar.volume", 10);
    }

    public static float getActionbarSoundPitch(){
        return config.getOrDefault("sounds.actionbar.pitch", 2);
    }

    /*-----------------------------
    BOSSBAR CONFIGURATION
    -----------------------------*/

    public static String getBossbarSound(){
        return config.getOrDefault(
            "sounds.bossbar.sound-id",
            "entity.experience_orb.pickup");
    }

    public static boolean isBossbarSoundEnabled(){
        return config.getOrDefault("sounds.bossbar.enabled", true);
    }

    public static float getBossbarSoundVolume(){
        return config.getOrDefault("sounds.bossbar.volume", 10);
    }

    public static float getBossbarSoundPitch(){
        return config.getOrDefault("sounds.bossbar.pitch", 2);
    }

    /*
    GENERAL CONFIGURATION
    */
    public static void sendConfirmation(ComponentType type, Audience sender){
        sender.sendMessage(getPrefix().append(mm.parse(switch(type){
            case BOSSBAR -> config.getOrDefault(
                        "messages.bossbar.successfully",
                        "<green>Bossbar succesfully sended</green>");
            case ACTIONBAR -> config.getOrDefault(
                        "messages.actionbar.successfully",
                        "<green>Actionbar succesfully sended</green>");
            case TITLE -> config.getOrDefault(
                        "messages.title.successfully",
                        "<green>Title succesfully sended</green>");
        })));
    }

    public static void playPaperSound(ComponentType type, Audience audience){
        SoundUtil sUtils = new SoundUtil();
        switch(type){
            case TITLE -> {
                if(isTitleSoundEnabled()){
                    sUtils.playSound(
                        getTitleSound(),
                        audience,
                        getTitleSoundVolume(),
                        getTitleSoundPitch());
                }
            }
            case BOSSBAR -> {
                if(isBossbarSoundEnabled()){
                    sUtils.playSound(
                        getBossbarSound(),
                        audience,
                        getBossbarSoundVolume(),
                        getBossbarSoundPitch());
                }
            }
            case ACTIONBAR -> {
                if(isActionbarSoundEnabled()){
                    sUtils.playSound(
                        getActionbarSound(),
                        audience,
                        getActionbarSoundVolume(),
                        getActionbarSoundPitch());
                }
            }
        }
    }

    public static void reloadMessage(Audience sender){
        sender.sendMessage(mm.parse(
            config.getOrDefault(
                "messages.general.reload-config",
                "<green>Config Reloaded</green>")));
    }

    public static void invalidCommand(Audience sender){
        sender.sendMessage(getPrefix().append(mm.parse(
            config.getOrDefault(
                "messages.general.invalid-command",
                "<red>Unknown Command</red>"))));
    }

    public static void helpPrefix(Audience sender){
        sender.sendMessage(mm.parse(
            config.getOrDefault(
                "messages.general.help-message",
                "<white>Available Commands:</white>")));
    }

    public static void playerNotFoundMessage(Audience sender){
        sender.sendMessage(getPrefix().append(mm.parse(
            config.getOrDefault(
                "messages.general.player-not-found",
                "<red>Player not found</red>"))));
    }

    public static void onlyPlayerExecute(Audience sender){
        sender.sendMessage(getPrefix().append(mm.parse(
            config.getOrDefault(
                "messages.general.no-console",
                "<red>The console cannot execute this command</red>")
        )));
    }

    public static void noServerArgumentProvided(Audience sender){
        sender.sendMessage(getPrefix().append(mm.parse(
            config.getOrDefault(
                "messages.general.no-server-provided",
                "<red>No server provided to send the message</red>")
        )));
    }

    public static void noServerFound(Audience sender){
        sender.sendMessage(getPrefix().append(mm.parse(
            config.getOrDefault(
                "messages.general.server-not-found",
                "<red>Server not found</red>")
        )));
    }
}
