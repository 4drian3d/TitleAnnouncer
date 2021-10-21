package net.dreamerzero.titleannouncer.paper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.leonhard.storage.Yaml;
import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.paper.listeners.PluginListener;
import net.dreamerzero.titleannouncer.paper.listeners.TabCompleteListener;
import net.dreamerzero.titleannouncer.paper.utils.RegisterCommands;
import net.kyori.adventure.text.minimessage.MiniMessage;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;

public class Announcer extends JavaPlugin {
	private static boolean isPlaceholderAPIPresent;
	private MiniMessage mm;
	@Override
	public void onEnable() {
		mm = MiniMessage.miniMessage();
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		Bukkit.getConsoleSender().sendMessage(text("Enabling ", AQUA).append(mm.deserialize(
			"<gradient:yellow:blue>TitleAnnouncer</gradient>")));
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		new ConfigManager(new Yaml("plugins/TitleAnnouncer", "config"));
		ConfigManager.defaultConfig();
		listenerRegister();
		placeholderAPICheck();
		new RegisterCommands(this, mm).registerCommands();
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		Bukkit.getConsoleSender().sendMessage(text("Disabling ", AQUA).append(mm.deserialize(
			"<gradient:yellow:blue>TitleAnnouncer</gradient>")));
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
	}

	public void listenerRegister() {
		getServer().getPluginManager().registerEvents(new TabCompleteListener(mm), this);
		getServer().getPluginManager().registerEvents(new PluginListener(), this);
	}

	public static void setPAPIStatus(boolean status){
        isPlaceholderAPIPresent = status;
    }

    public static boolean placeholderAPIHook(){
        return isPlaceholderAPIPresent;
    }

    public static void placeholderAPICheck(){
		isPlaceholderAPIPresent = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
	}
}
