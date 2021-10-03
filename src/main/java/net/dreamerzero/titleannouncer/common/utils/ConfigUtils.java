package net.dreamerzero.titleannouncer.common.utils;

import de.leonhard.storage.Yaml;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

public class ConfigUtils {
    private Yaml config = new ConfigManager().getConfig();

    public Component getPrefix(){
        if (config.getOrDefault("messages.prefix.enabled", true)) {
            return new MiniMessageUtil().parse(config.getOrDefault(
                "messages.prefix.line",
                "<gray>[</gray><gradient:yellow:blue>TitleAnnouncer</gradient><gray>]</gray> "));
        } else {
            return Component.empty();
        }
    }

    /*-----------------------------
    TITLE CONFIGURATION
    -----------------------------*/

    public void sendTitleError(Audience sender){
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.title.error",
                "<dark_red>An error occurred while sending the title. Be sure to use the ';' to separate the title and the subtitle.</dark_red>"))));
    }

    public void sendNoArgumentMessage(Audience sender) {
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.title.without-argument",
                "<red>You need to enter the title and subtitle arguments.</red>"))));
    }

    public void noTitlePlayerArgumentProvided(Audience sender){
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.title.only-player",
                "<gray>You must enter the title and subtitle after the player's name to send the message correctly.</gray>"))));
    }

    public String getTitleSound(){
        return config.getOrDefault(
            "sounds.title.sound-id",
            "entity.experience_orb.pickup");
    }

    public boolean isTitleSoundEnabled(){
        return config.getOrDefault("sounds.title.enabled", true);
    }

    public float getTitleSoundVolume(){
        return config.getOrDefault("sounds.title.volume", 10);
    }

    public float getTitleSoundPitch(){
        return config.getOrDefault("sounds.title.pitch", 2);
    }

    /*-----------------------------
    ACTIONBAR CONFIGURATION
    -----------------------------*/

    public void noActionbarArgumentProvided(Audience sender){
        sender.sendMessage(
            getPrefix().append(new MiniMessageUtil().parse(
                config.getOrDefault(
                    "messages.actionbar.without-argument",
                    "<red>You need to enter the message to announce.</red>"))));
    }

    public void noActionbarPlayerArgumentProvided(Audience sender){
        sender.sendMessage(
            getPrefix().append(new MiniMessageUtil().parse(
                config.getOrDefault(
                    "messages.actionbar.only-player",
                    "<gray>You must enter the message to be sent after the player's name.</gray>"))));
    }

    public String getActionbarSound(){
        return config.getOrDefault(
            "sounds.actionbar.sound-id",
            "entity.experience_orb.pickup");
    }

    public boolean isActionbarSoundEnabled(){
        return config.getOrDefault("sounds.actionbar.enabled", true);
    }

    public float getActionbarSoundVolume(){
        return config.getOrDefault("sounds.actionbar.volume", 10);
    }

    public float getActionbarSoundPitch(){
        return config.getOrDefault("sounds.actionbar.pitch", 2);
    }

    /*-----------------------------
    BOSSBAR CONFIGURATION
    -----------------------------*/

    public String getBossbarSound(){
        return config.getOrDefault(
            "sounds.bossbar.sound-id",
            "entity.experience_orb.pickup");
    }

    public boolean isBossbarSoundEnabled(){
        return config.getOrDefault("sounds.bossbar.enabled", true);
    }

    public float getBossbarSoundVolume(){
        return config.getOrDefault("sounds.bossbar.volume", 10);
    }

    public float getBossbarSoundPitch(){
        return config.getOrDefault("sounds.bossbar.pitch", 2);
    }

    /*
    GENERAL CONFIGURATION
    */
    public void sendConfirmation(ComponentType type, Audience sender){
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(switch(type){
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

    public void playPaperSound(ComponentType type, Audience audience){
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

    public void reloadMessage(Audience sender){
        sender.sendMessage(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.general.reload-config",
                "<green>Config Reloaded</green>")));
    }

    public void invalidCommand(Audience sender){
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.general.invalid-command",
                "<red>Unknown Command</red>"))));
    }

    public void helpPrefix(Audience sender){
        sender.sendMessage(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.general.help-message",
                "<white>Available Commands:</white>")));
    }

    public void playerNotFoundMessage(Audience sender){
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.general.player-not-found",
                "<red>Player not found</red>"))));
    }

    public void onlyPlayerExecute(Audience sender){
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.general.no-console",
                "<red>The console cannot execute this command</red>")
        )));
    }

    public void noServerArgumentProvided(Audience sender){
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.general.no-server-provided",
                "<red>No server provided to send the message</red>")
        )));
    }

    public void noServerFound(Audience sender){
        sender.sendMessage(getPrefix().append(new MiniMessageUtil().parse(
            config.getOrDefault(
                "messages.general.server-not-found",
                "<red>Server not found</red>")
        )));
    }
}
