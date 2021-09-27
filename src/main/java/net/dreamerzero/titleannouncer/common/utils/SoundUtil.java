package net.dreamerzero.titleannouncer.common.utils;

import java.util.UUID;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import static dev.simplix.protocolize.data.Sound.*;

public class SoundUtil {
    /**
     * Will play the corresponding sound at the
     * specified volume and pitch to the specified audience.
     * @param sound
     * @param audience
     * @param volume
     * @param pitch
     */
    public static void playSound(
        final String sound,
        final Audience audience,
        final float volume,
        final float pitch) {

        audience.playSound(
            Sound.sound(
                Key.key(sound),
                Sound.Source.MUSIC,
                volume,
                pitch
            )
        );
    }

    public static void playProxySound(
        com.velocitypowered.api.proxy.Player player, 
        String sound,
        float volume,
        float pitch){

        ProxyServer proxy = net.dreamerzero.titleannouncer.velocity.Announcer.getProxyServer();

        if(proxy.getPluginManager().isLoaded("protocolize")){
            UUID playeruuid = player.getUniqueId();
            ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);
            protocolizePlayer.playSound(
                getSoundFromString(sound),
                SoundCategory.MASTER, volume, pitch);
        }
    }

    public static void playProxySound(
        String sound,
        float volume,
        float pitch){

        ProxyServer proxy = net.dreamerzero.titleannouncer.velocity.Announcer.getProxyServer();

        if(proxy.getPluginManager().isLoaded("protocolize")){
            for(com.velocitypowered.api.proxy.Player player : proxy.getAllPlayers()){
                UUID playeruuid = player.getUniqueId();
                ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(playeruuid);
                protocolizePlayer.playSound(
                    getSoundFromString(sound),
                    SoundCategory.MASTER, volume, pitch);
            }
        }
    }

    //TODO: Finish this
    static dev.simplix.protocolize.data.Sound getSoundFromString(String sound){
        return switch(sound){
            case "ambient.cave" -> AMBIENT_CAVE;
            case "ambient.underwater.enter" -> AMBIENT_UNDERWATER_ENTER;
            case "ambient.underwater.exit" -> AMBIENT_UNDERWATER_EXIT;
            case "ambient.underwater.loop" -> AMBIENT_UNDERWATER_LOOP;
            case "ambient.underwater.loop.additions" -> AMBIENT_UNDERWATER_LOOP_ADDITIONS;
            case "ambient.underwater.loop.additions.rare" -> AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE;
            case "ambient.underwater.loop.additions.ultra.rare" -> AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE;
            case "block.anvil.break" -> BLOCK_ANVIL_BREAK;
            case "block.anvil.destroy" -> BLOCK_ANVIL_DESTROY;
            case "block.anvil.fall" -> BLOCK_ANVIL_FALL;
            default -> ENTITY_EXPERIENCE_ORB_PICKUP;
        };
    }

    public static void playProxyTitleSound(com.velocitypowered.api.proxy.Player player){
        playProxySound(
            player,
            ConfigUtils.getTitleSound(),
            ConfigUtils.getTitleSoundVolume(),
            ConfigUtils.getTitleSoundPitch());
    }

    public static void playToServerProxyTitleSound(RegisteredServer server){
        for(com.velocitypowered.api.proxy.Player player : server.getPlayersConnected()){
            playProxySound(
                player,
                ConfigUtils.getTitleSound(),
                ConfigUtils.getTitleSoundVolume(),
                ConfigUtils.getTitleSoundPitch());
        }
    }

    public static void playToServerProxyBossbarSound(RegisteredServer server){
        for(com.velocitypowered.api.proxy.Player player : server.getPlayersConnected()){
            playProxySound(
                player,
                ConfigUtils.getBossbarSound(),
                ConfigUtils.getBossbarSoundVolume(),
                ConfigUtils.getBossbarSoundPitch());
        }
    }

    public static void playToAllProxyTitleSound(){
        playProxySound(
            ConfigUtils.getTitleSound(),
            ConfigUtils.getTitleSoundVolume(),
            ConfigUtils.getTitleSoundPitch());
    }

    public static void playToAllProxyBossbarSound(){
        playProxySound(
            ConfigUtils.getBossbarSound(),
            ConfigUtils.getBossbarSoundVolume(),
            ConfigUtils.getBossbarSoundPitch());
    }
}
