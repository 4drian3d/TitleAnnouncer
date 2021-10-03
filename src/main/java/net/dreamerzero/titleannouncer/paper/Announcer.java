package net.dreamerzero.titleannouncer.paper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.common.utils.PlaceholderUtil;
import net.dreamerzero.titleannouncer.paper.listeners.PluginListener;
import net.dreamerzero.titleannouncer.paper.listeners.TabCompleteListener;
import net.dreamerzero.titleannouncer.paper.utils.RegisterCommands;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;

public class Announcer extends JavaPlugin {
	private Announcer instance;
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		Bukkit.getConsoleSender().sendMessage(text("Enabling ", AQUA).append(new MiniMessageUtil().parse(
			"<gradient:yellow:blue>TitleAnnouncer</gradient>")));
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		instance = this;
		ConfigManager cManager = new ConfigManager();
		cManager.defaultConfig();
		listenerRegister();
		PlaceholderUtil.placeholderAPICheck();
		RegisterCommands rCommands = new RegisterCommands(this);
		rCommands.setCustomNoPermissionMessage();
		// Main Command
		rCommands.registerMainCommand();
		// Title Commands
		rCommands.registerTitle();
		// ActionBar Commands
		rCommands.registerActionbar();
		// BossBar Commands
		rCommands.registerBossbar();
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
		Bukkit.getConsoleSender().sendMessage(text("Disabling ", AQUA).append(new MiniMessageUtil().parse(
			"<gradient:yellow:blue>TitleAnnouncer</gradient>")));
		Bukkit.getConsoleSender().sendMessage(text("----------------------", DARK_GRAY));
	}

	public void listenerRegister() {
		getServer().getPluginManager().registerEvents(new TabCompleteListener(), this);
		getServer().getPluginManager().registerEvents(new PluginListener(), this);
	}

	public Announcer getInstance() {
		return instance;
	}
}
