package me.dreamerzero.titleannouncer.paper;

import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class PaperPlugin extends JavaPlugin {
    
    @Override
    public void onEnable(){
        this.getMCServer();
    }

    private CraftServer getMCServer(){
        return (CraftServer)this.getServer();
    }
}
