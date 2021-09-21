package net.dreamerzero.titleannouncer;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.dreamerzero.titleannouncer.listeners.TabCompleteListener;
import net.dreamerzero.titleannouncer.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.utils.RegisterCommands;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;

public class Announcer extends JavaPlugin {
	private static Announcer instance;
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		Bukkit.getConsoleSender().sendMessage(text("Enabling ", AQUA).append(MiniMessageUtil.parse(
			"<gradient:yellow:blue>TitleAnnouncer</gradient>")));
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		instance = this;
		pluginConfiguration();
		commandRegister();
		listenerRegister();
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		Bukkit.getConsoleSender().sendMessage(text("Disabling ", AQUA).append(MiniMessageUtil.parse(
			"<gradient:yellow:blue>TitleAnnouncer</gradient>")));
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
	}

	// Registration of the commands that the plugin provides
	public void commandRegister() {
		// Main Command
		RegisterCommands.registerMainCommand();
		// Title Commands
		RegisterCommands.registerTitle();
		// ActionBar Commands
		RegisterCommands.registerActionbar();
		// BossBar Commands
		RegisterCommands.registerBossbar();
	}

	public void listenerRegister() {
		getServer().getPluginManager().registerEvents(new TabCompleteListener(), this);
	}

	public void pluginConfiguration() {
		var config = new File(this.getDataFolder(), "config.yml");
		if (!config.exists()) {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
	}

	public static Announcer getInstance() {
		return instance;
	}
}
