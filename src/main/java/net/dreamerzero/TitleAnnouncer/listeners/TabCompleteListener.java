package net.dreamerzero.TitleAnnouncer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;

import java.util.List;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import static com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion.completion;

public class TabCompleteListener implements Listener {
    @EventHandler
    public void onTabComplete(
        final AsyncTabCompleteEvent event) {
            
        final var buffer = event.getBuffer();
        final var input = buffer.startsWith("/") ? buffer.substring(1) : buffer;
        final String[] tokens = input.split(" ");

        if (tokens[0].equalsIgnoreCase("announcer")){
            if (tokens.length == 1) {
                event.completions().addAll(
                    List.of(
                        completion("reload", 
                            MiniMessageUtil.parse("<gradient:#FBB244:#23FDFD>Reload Command</gradient>")), 
                        completion("help", 
                            MiniMessageUtil.parse("<gradient:#FBB244:#23FDFD>Help Command</gradient>"))
                )); 
            } else if(tokens.length == 2 && tokens[1].equalsIgnoreCase("help")){
                event.completions().addAll(
                    List.of(
                        completion("title", 
                            MiniMessageUtil.parse("<gradient:#6486FB:#69FD44>Title Help Command</gradient>")), 
                        completion("actionbar", 
                            MiniMessageUtil.parse("<gradient:#6486FB:#69FD44>ActionBar Help Command</gradient>"))
                )); 
            }
        }
        
    }
}
