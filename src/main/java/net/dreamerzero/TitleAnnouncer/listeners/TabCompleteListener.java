package net.dreamerzero.titleannouncer.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;

import java.util.List;

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent;
import static com.destroystokyo.paper.event.server.AsyncTabCompleteEvent.Completion.completion;

public class TabCompleteListener implements Listener {
    @EventHandler
    public void onTabComplete(final AsyncTabCompleteEvent event) {
        String buffer = event.getBuffer();
        String input = buffer.startsWith("/") ? buffer.substring(1) : buffer;
        String[] tokens = input.split(" ");

        var players = Bukkit.getOnlinePlayers();

        switch (tokens[0].toLowerCase()) {
            // Main Command
            case "announcer", "titleannouncer" -> {
                if (tokens.length == 1) {
                    event.completions().addAll(
                        List.of(
                            completion("reload",
                                MiniMessageUtil.parse("<gradient:#FBB244:#23FDFD>Reload Command</gradient>")),
                            completion("help",
                                MiniMessageUtil.parse("<gradient:#FBB244:#23FDFD>Help Command</gradient>"))
                    ));
                } else if(tokens.length == 2 && tokens[1].equalsIgnoreCase("help")) {
                    event.completions().addAll(
                        List.of(
                            completion("title",
                                MiniMessageUtil.parse("<gradient:#6486FB:#69FD44>Title Help Command</gradient>")),
                            completion("actionbar",
                                MiniMessageUtil.parse("<gradient:#6486FB:#69FD44>ActionBar Help Command</gradient>")),
                            completion("bossbar",
                                MiniMessageUtil.parse("<gradient:#6486FB:#69FD44>BossBar Help Command</gradient>"))
                    ));
                }
            }
            // Title Commands
            case "announcetitle", "worldtitle", "selftitle" -> {
                if (!input.contains(";")){
                    event.completions().add(completion("[Title]", MiniMessageUtil.parse("<gradient:#41FB6A:#6AA5FD>Title</gradient>")));
                } else {
                    event.completions().add(completion("[SubTitle]", MiniMessageUtil.parse("<gradient:#6AA5FD:#41FB6A>SubTitle</gradient>")));
                }
            }
            case "sendtitle" -> {
                if (tokens.length <= 2){
                    for (Player player : players) {
                        event.completions().add(completion(player.getName(), MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Player in the server</gradient>")));
                    }
                } else if (!input.contains(";")){
                    event.completions().add(completion("[Title]", MiniMessageUtil.parse("<gradient:#41FB6A:#6AA5FD>Title</gradient>")));
                } else {
                    event.completions().add(completion("[SubTitle]", MiniMessageUtil.parse("<gradient:#6AA5FD:#41FB6A>SubTitle</gradient>")));
                }
            }
            // Actionbar Commands
            case "announceactionbar", "worldactionbar", "selfactionbar" -> event.completions().add(completion("[message]",
                    MiniMessageUtil.parse("<gradient:#FB5DB2:#B3FD14>Message to announce</gradient>")));
            case "sendactionbar" -> {
                if (tokens.length <= 2){
                    for (Player player : players) {
                        event.completions().add(completion(player.getName(), MiniMessageUtil.parse("<gradient:#99FB1C:#D3FDAA>Players in the server</gradient>")));
                    }
                } else {
                    event.completions().add(
                        completion("[message]",
                        MiniMessageUtil.parse("<gradient:#FA5BFD:#D3FDAA>Message to announce</gradient>")));
                }
            }
            // Bossbar Commands
            case "announcebossbar", "worldbossbar", "selfbossbar" -> {
                event.completions().add(switch (tokens.length) {
                    case 1 -> completion("[Time]", MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Bossbar display time</gradient>"));
                    case 2 -> completion("[Color]", MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Bossbar color</gradient>"));
                    case 3 -> completion("[Type]", MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Bossbar style</gradient>"));
                    default -> completion("[message]", MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Message to announce</gradient>"));
                });
            }
            case "sendbossbar" -> {
                switch (tokens.length){
                    case 1 -> {
                        for (Player player : players) {
                            event.completions().add(completion(player.getName(), MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Player in the server</gradient>")));
                        }
                    }
                    case 2 -> event.completions().add(completion("[Time]", MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Bossbar display time</gradient>")));
                    case 3 -> event.completions().add(completion("[Color]", MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Bossbar color</gradient>")));
                    case 4 -> event.completions().add(completion("[Type]", MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Bossbar style</gradient>")));
                    default -> event.completions().add(completion("[message]", MiniMessageUtil.parse("<gradient:#94EFFB:#D3FDAA>Message to announce</gradient>")));
                }
            }
        }
    }
}
