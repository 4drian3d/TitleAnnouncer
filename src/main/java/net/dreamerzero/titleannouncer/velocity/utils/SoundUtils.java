package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.UUID;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.Sound;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.ComponentType;

public class SoundUtils {
    private static Sound titleSound;
    private static Sound actionbarSound;
    private static Sound bossbarSound;
    private static Sound chatSound;
    private final ProxyServer proxy;

    public SoundUtils(ProxyServer server){
        this.proxy = server;
    }

    public void setTitleSound(){
        titleSound = SoundTransformer.getSoundFromString(ConfigUtils.getTitleSound());
    }

    public void setBossBarSound(){
        bossbarSound = SoundTransformer.getSoundFromString(ConfigUtils.getBossbarSound());
    }

    public void setActionBarSound(){
        actionbarSound = SoundTransformer.getSoundFromString(ConfigUtils.getActionbarSound());
    }

    public void setChatSound(){
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

    public void playProxySound(Player player, ComponentType type){
        if(!proxy.getPluginManager().isLoaded("protocolize")) return;

        UUID playeruuid = player.getUniqueId();
        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);
        switch(type) {
            case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
            case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
            case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
            case CHAT -> protocolizePlayer.playSound(getChatSound(), SoundCategory.MASTER, ConfigUtils.getChatSoundVolume(), ConfigUtils.getChatSoundPitch());
        };

    }

    public void playProxySound(ComponentType type){
        if(!proxy.getPluginManager().isLoaded("protocolize")) return;

        for(Player player : proxy.getAllPlayers()){
            UUID playeruuid = player.getUniqueId();
            ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);

            switch(type) {
                case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
                case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
                case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
                case CHAT -> protocolizePlayer.playSound(getChatSound(), SoundCategory.MASTER, ConfigUtils.getChatSoundVolume(), ConfigUtils.getChatSoundPitch());
            };
        }
    }

    public void playProxySound(RegisteredServer server, ComponentType type){
        if(!proxy.getPluginManager().isLoaded("protocolize")) return;

        for(Player player : server.getPlayersConnected()){
            UUID playeruuid = player.getUniqueId();
            ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);

            switch(type) {
                case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
                case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
                case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
                case CHAT -> protocolizePlayer.playSound(getChatSound(), SoundCategory.MASTER, ConfigUtils.getChatSoundVolume(), ConfigUtils.getChatSoundPitch());
            };
        }
    }
}
