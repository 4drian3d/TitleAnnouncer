package net.dreamerzero.titleannouncer.velocity.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import net.dreamerzero.titleannouncer.common.utils.BossbarProvider;
import net.dreamerzero.titleannouncer.velocity.Announcer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class VelocityBossbar implements BossbarProvider {
    private Announcer plugin;
    private ProxyServer proxy;
    private float value;
    private ScheduledTask taskScheduled;
    private static Map<Audience, List<BossBar>> bossbarList = new HashMap<>();

    public VelocityBossbar(Announcer plugin, ProxyServer proxy){
        this.plugin = plugin;
        this.proxy = proxy;
        this.value = 1f;
    }

    @Override
    public void sendBossbar(
        @NotNull final Audience audience,
        @NotNull final float time,
        @NotNull final Component content,
        @NotNull final BossBar.Color color,
        @NotNull final BossBar.Overlay type){

        final float finalTime = 0.1f/time;

        BossBar bar = BossBar.bossBar(
            content,
            1,
            color,
            type);

        audience.showBossBar(bar);

        taskScheduled = proxy.getScheduler()
            .buildTask(plugin, () -> {
                value -= finalTime;
                    if (value <= 0.02) {
                        audience.hideBossBar(bar);
                        cancelTask();
                    }
                    try {
                        bar.progress(value);
                    } catch (IllegalArgumentException e) {
                        cancelTask();
                    }
            })
            .repeat(200L, TimeUnit.MILLISECONDS)
            .schedule();
    }

    void cancelTask(){
        taskScheduled.cancel();
    }

    @Override
    public Map<Audience, List<BossBar>> getPlayerBossbars() {
        return bossbarList;
    }

    @Override
    public void removePlayerBossbars(@NotNull Audience audience) {
        var playerbossbars = bossbarList.get(audience);
        playerbossbars.forEach(audience::hideBossBar);
        playerbossbars.clear();
    }

    @Override
    public void addBossbar(@NotNull Audience audience, @NotNull BossBar bossbar) {
        if(bossbarList.containsKey(audience)){
            List<BossBar> bossbars = new ArrayList<>(4);
            bossbars.add(bossbar);
            bossbarList.put(audience, bossbars);
        } else {
            var bossbars = bossbarList.get(audience);
            if(!bossbars.contains(bossbar)){
                bossbars.add(bossbar);
            }
        }
    }

    @Override
    public void sendPersistentBossbar(@NotNull Audience audience, @NotNull @Range(from = 1, to = 100) float percent,
            @NotNull Component content, @NotNull BossBar.Color color, @NotNull BossBar.Overlay type) {
                BossBar bar = BossBar.bossBar(
                    content,
                    percent,
                    color,
                    type);

                audience.showBossBar(bar);
                this.addBossbar(audience, bar);
    }

    @Override
    public Audience getObjetives(@NotNull String argument, @NotNull Audience sender) {
        try {
            String[] arguments = argument.split(":");
            switch(arguments[0]){
                case "server" -> {
                    var optionalserver = proxy.getServer(arguments[1]);
                    if(optionalserver.isPresent()){
                        return optionalserver.get();
                    }
                }
                case "player" -> {
                    var optionalplayer = proxy.getPlayer(arguments[1]);
                    if(optionalplayer.isPresent()){
                        return optionalplayer.get();
                    }
                }
                case "me", "myself" -> {return sender;}
                default -> {
                    sender.sendMessage(Component.text("This is not a valid objetive"));
                    return Audience.empty();
                }
            }
        } catch (Exception exception){
            sender.sendMessage(Component.text("This is not a valid argument, please insert a :"));
        }
        return null;
    }

    


}
