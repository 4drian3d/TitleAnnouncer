package net.dreamerzero.TitleAnnouncer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;

import java.util.List;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import static com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion.completion;

public class TabCompleteListener implements Listener {
    @EventHandler
    public void onTabComplete(AsyncTabCompleteEvent event){
        String buffer = event.getBuffer();
        String input = buffer.startsWith("/") ? buffer.substring(1) : buffer;
        String[] tokens = input.split(" ");

        if (tokens[0].equalsIgnoreCase("announcer")){
            event.completions().addAll(
                List.of(
                    completion("reload", 
                        MiniMessageUtil.parse("<rainbow>Reload Command</rainbow>")), 
                    completion("help", 
                        MiniMessageUtil.parse("<rainbow>Help Command</rainbow>"))
            ));   
        }
    }
}
