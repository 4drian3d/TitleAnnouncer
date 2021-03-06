package net.dreamerzero.titleannouncer.paper.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import net.dreamerzero.titleannouncer.paper.Announcer;

public class PluginListener implements Listener {
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event){
        if(event.getPlugin().getName().equals("PlaceholderAPI")){
            Announcer.setPAPIStatus(false);
        }
    }
    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        if(event.getPlugin().getName().equals("PlaceholderAPI")){
            Announcer.setPAPIStatus(true);
        }
    }
}
