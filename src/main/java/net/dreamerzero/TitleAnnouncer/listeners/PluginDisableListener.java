package net.dreamerzero.titleannouncer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import net.dreamerzero.titleannouncer.utils.PlaceholderUtil;

public class PluginDisableListener implements Listener {
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event){
        if(event.getPlugin().getName().equals("PlaceholderAPI")){
            PlaceholderUtil.setPAPIStatus(false);
        }
    }
}
