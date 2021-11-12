package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.UUID;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;

import org.jetbrains.annotations.NotNull;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.Sound;
import dev.simplix.protocolize.velocity.adventure.ProtocolizeAudience;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.kyori.adventure.audience.Audience;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;

public class SoundUtils {
    private static Sound titleSound;
    private static Sound actionbarSound;
    private static Sound bossbarSound;
    private static Sound chatSound;
    private final ProxyServer proxy;
    private static final String PROTOCOLIZE = "protocolize";

    public SoundUtils(ProxyServer server){
        this.proxy = server;
    }

    public static void setTitleSound(){
        titleSound = SoundTransformer.getSoundFromString(ConfigUtils.getTitleSound());
    }

    public static void setBossBarSound(){
        bossbarSound = SoundTransformer.getSoundFromString(ConfigUtils.getBossbarSound());
    }

    public static void setActionBarSound(){
        actionbarSound = SoundTransformer.getSoundFromString(ConfigUtils.getActionbarSound());
    }

    public static void setChatSound(){
        chatSound = SoundTransformer.getSoundFromString(ConfigUtils.getChatSound());
    }

    public Sound getTitleSound(){
        return titleSound;
    }

    public Sound getBossBarSound(){
        return bossbarSound;
    }

    public Sound getActionBarSound(){
        return actionbarSound;
    }

    public Sound getChatSound(){
        return chatSound;
    }

    /**
     * Plays a sound to a Player
     * @param player the player
     * @param type the component type
     */
    public void playProxySound(@NotNull Player player, ComponentType type){
        if(!proxy.getPluginManager().isLoaded(PROTOCOLIZE)) return;

        final UUID playeruuid = player.getUniqueId();
        final ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);
        switch(type) {
            case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
            case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
            case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
            case CHAT -> protocolizePlayer.playSound(getChatSound(), SoundCategory.MASTER, ConfigUtils.getChatSoundVolume(), ConfigUtils.getChatSoundPitch());
        }
    }

    /**
     * Plays a sound to the entire network
     * @param type the component type
     */
    public void playProxySound(ComponentType type){
        if(!proxy.getPluginManager().isLoaded(PROTOCOLIZE)) return;

        for(Player player : proxy.getAllPlayers()){
            final UUID playeruuid = player.getUniqueId();
            final ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);

            switch(type) {
                case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
                case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
                case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
                case CHAT -> protocolizePlayer.playSound(getChatSound(), SoundCategory.MASTER, ConfigUtils.getChatSoundVolume(), ConfigUtils.getChatSoundPitch());
            }
        }
    }

    /**
     * Plays a sound to an audience
     * @param audience the audience
     * @param type the component type
     */
    public void playProxySound(@NotNull Audience audience, ComponentType type){
        if(!proxy.getPluginManager().isLoaded(PROTOCOLIZE)) return;

        final ProtocolizeAudience pAudience = new ProtocolizeAudience(audience);

        switch(type) {
            case TITLE -> pAudience.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
            case BOSSBAR -> pAudience.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
            case ACTIONBAR -> pAudience.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
            case CHAT -> pAudience.playSound(getChatSound(), SoundCategory.MASTER, ConfigUtils.getChatSoundVolume(), ConfigUtils.getChatSoundPitch());
        }
    }
}
