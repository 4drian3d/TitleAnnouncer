package net.dreamerzero.titleannouncer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;

public class PluginListener implements Listener {
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event){
        if(event.getPlugin().getName().equals("PlaceholderAPI")){
            PlaceholderUtil.setPAPIStatus(false);
        }
    }
    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        if(event.getPlugin().getName().equals("PlaceholderAPI")){
            PlaceholderUtil.setPAPIStatus(true);
        }
    }
}
