package net.dreamerzero.TitleAnnouncer;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.dreamerzero.TitleAnnouncer.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.TestActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.TestTitleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class Announcer extends JavaPlugin {
	//Component to send the server name: Peruviankkit
	static final TextComponent pvknet = Component.text("Peru", NamedTextColor.DARK_RED)
		.append(Component.text("vian", NamedTextColor.WHITE))
		.append(Component.text("kkit", NamedTextColor.DARK_RED)).append(Component.text(" Network", NamedTextColor.GREEN));
	//Plugin Name
	static final TextComponent eventannouncertext = Component.text("Title", NamedTextColor.RED)
		.append(Component.text("Announcer", NamedTextColor.WHITE));
	//line
	static final TextComponent linelong = Component.text("----------------------", NamedTextColor.DARK_GRAY);
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(linelong);
		Bukkit.getConsoleSender().sendMessage(Component.text("Enabling ", NamedTextColor.AQUA).append(eventannouncertext));
		Bukkit.getConsoleSender().sendMessage(pvknet);
		Bukkit.getConsoleSender().sendMessage(linelong);
		pluginConfiguration();
		commandRegister();
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(linelong);
		Bukkit.getConsoleSender().sendMessage(Component.text("Disabling ", NamedTextColor.AQUA).append(eventannouncertext));
		Bukkit.getConsoleSender().sendMessage(pvknet);
		Bukkit.getConsoleSender().sendMessage(linelong);
	}
	
	//Registration of the 2 commands that the plugin has, for the moment.
	public void commandRegister() {
		this.getCommand("titleevento").setExecutor(new AnnouncerTitleCommand(this));
		this.getCommand("titlepevento").setExecutor(new TestTitleCommand(this));
		this.getCommand("actionbarevento").setExecutor(new AnnouncerActionbarCommand(this));
		this.getCommand("actionbarpevento").setExecutor(new TestActionbarCommand(this));
	}

	public void pluginConfiguration() {
		File config = new File(this.getDataFolder(), "config.yml");
		if(!config.exists()) {
			this.getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
	}
}
