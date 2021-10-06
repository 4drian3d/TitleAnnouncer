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
    private Sound titleSound;
    private Sound actionbarSound;
    private Sound bossbarSound;
    private ConfigUtils config = new ConfigUtils();
    private ProxyServer proxy;

    public SoundUtils(ProxyServer server){
        this.proxy = server;
    }

    public void setTitleSound(){
        titleSound = SoundTransformer.getSoundFromString(config.getTitleSound());
    }

    public void setBossBarSound(){
        bossbarSound = SoundTransformer.getSoundFromString(config.getBossbarSound());
    }

    public void setActionBarSound(){
        actionbarSound = SoundTransformer.getSoundFromString(config.getActionbarSound());
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

    public void playProxySound(Player player, ComponentType type){
        if(!proxy.getPluginManager().isLoaded("protocolize")) return;

        UUID playeruuid = player.getUniqueId();
        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);
        switch(type) {
            case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, config.getTitleSoundVolume(), config.getTitleSoundPitch());
            case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, config.getBossbarSoundVolume(), config.getBossbarSoundPitch());
            case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, config.getActionbarSoundVolume(), config.getActionbarSoundPitch());
        };

    }

    public void playProxySound(ComponentType type){
        if(!proxy.getPluginManager().isLoaded("protocolize")) return;

        for(Player player : proxy.getAllPlayers()){
            UUID playeruuid = player.getUniqueId();
            ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);

            switch(type) {
                case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, config.getTitleSoundVolume(), config.getTitleSoundPitch());
                case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, config.getBossbarSoundVolume(), config.getBossbarSoundPitch());
                case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, config.getActionbarSoundVolume(), config.getActionbarSoundPitch());
            };
        }
    }

    public void playProxySound(RegisteredServer server, ComponentType type){
        if(!proxy.getPluginManager().isLoaded("protocolize")) return;

        for(Player player : server.getPlayersConnected()){
            UUID playeruuid = player.getUniqueId();
            ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);

            switch(type) {
                case TITLE -> protocolizePlayer.playSound(getTitleSound(), SoundCategory.MASTER, config.getTitleSoundVolume(), config.getTitleSoundPitch());
                case BOSSBAR -> protocolizePlayer.playSound(getBossBarSound(), SoundCategory.MASTER, config.getBossbarSoundVolume(), config.getBossbarSoundPitch());
                case ACTIONBAR -> protocolizePlayer.playSound(getActionBarSound(), SoundCategory.MASTER, config.getActionbarSoundVolume(), config.getActionbarSoundPitch());
            };
        }
    }
}
