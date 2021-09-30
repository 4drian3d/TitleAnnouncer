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
import net.dreamerzero.titleannouncer.velocity.Announcer;

public class SoundUtils {
    private static Sound titleSound;
    private static Sound actionbarSound;
    private static Sound bossbarSound;

    public static void setTitleSound(){
        titleSound = SoundTransformer.getSoundFromString(ConfigUtils.getTitleSound());
    }

    public static void setBossBarSound(){
        bossbarSound = SoundTransformer.getSoundFromString(ConfigUtils.getBossbarSound());
    }

    public static void setActionBarSound(){
        actionbarSound = SoundTransformer.getSoundFromString(ConfigUtils.getActionbarSound());
    }

    public static Sound getTitleSound(){
        return titleSound;
    }

    public static Sound getBossBarSound(){
        return bossbarSound;
    }

    public static Sound getActionBarSound(){
        return actionbarSound;
    }

    public static void playProxySound(Player player, ComponentType type){

        ProxyServer proxy = net.dreamerzero.titleannouncer.velocity.Announcer.getProxyServer();

        if(proxy.getPluginManager().isLoaded("protocolize")){
            UUID playeruuid = player.getUniqueId();
            ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);
            switch(type) {
                case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
                case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
                case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
            };
        }
    }

    public static void playProxySound(ComponentType type){
        ProxyServer proxy = Announcer.getProxyServer();

        if(!proxy.getPluginManager().isLoaded("protocolize")) return;

        for(Player player : proxy.getAllPlayers()){
            UUID playeruuid = player.getUniqueId();
            ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);

            switch(type) {
                case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
                case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
                case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
            };
        }
    }

    public static void playProxySound(RegisteredServer server, ComponentType type){
        ProxyServer proxy = Announcer.getProxyServer();

        if(!proxy.getPluginManager().isLoaded("protocolize")) return;

        for(Player player : server.getPlayersConnected()){
            UUID playeruuid = player.getUniqueId();
            ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);

            switch(type) {
                case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, ConfigUtils.getTitleSoundVolume(), ConfigUtils.getTitleSoundPitch());
                case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, ConfigUtils.getBossbarSoundVolume(), ConfigUtils.getBossbarSoundPitch());
                case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, ConfigUtils.getActionbarSoundVolume(), ConfigUtils.getActionbarSoundPitch());
            };
        }
    }
}
