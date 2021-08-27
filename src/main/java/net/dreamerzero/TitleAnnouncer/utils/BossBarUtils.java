package net.dreamerzero.TitleAnnouncer.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class BossBarUtils {
    public static BossBar activeBar;

    public static void sendBossBar (
        final Audience audience,
        final int time,
        final Component content, 
        final BossBar.Color color, 
        final BossBar.Overlay type) {
        
        float finalTime = time/100;
        if (finalTime > 100){
            finalTime = 100;
        }
        final BossBar bar = BossBar.bossBar(
            content, 
            finalTime, 
            color, 
            type);

        audience.showBossBar(bar);
        activeBar = bar;

        while (finalTime > 1){
            bar.progress(finalTime);
            finalTime--;
        }

        activeBar = null;
        
    }

    public void hideBossBar(
        final Audience audience) {

        audience.hideBossBar(activeBar);
        activeBar = null;
    }

    public void updateBossbarName(Component newname){
        activeBar.name(newname);
    }
}
