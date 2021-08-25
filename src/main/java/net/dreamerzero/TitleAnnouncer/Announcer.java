package net.dreamerzero.TitleAnnouncer;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.dreamerzero.TitleAnnouncer.commands.AnnouncerCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.SendActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.SelfActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.actionbar.WorldActionbarCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.SendTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.SelfTitleCommand;
import net.dreamerzero.TitleAnnouncer.commands.title.WorldTitleCommand;
import net.dreamerzero.TitleAnnouncer.listeners.TabCompleteListener;
import net.dreamerzero.TitleAnnouncer.utils.MiniMessageUtil;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.text;
import net.kyori.adventure.text.TextComponent;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_RED;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;

public class Announcer extends JavaPlugin {
	private static Announcer instance;
	// Component to send the server name: Peruviankkit... in color... in console
	private static final TextComponent pvknet = text()
		.color(DARK_RED)
		.append(text("Peru"))
		.append(text()
			.append(text("vian", WHITE))
		)
		.append(text()
			.append(text("kkit", DARK_RED))
		)
		.append(space())
		.append(text()
			.append(text("Network", GREEN))
		)
		.build();


	// Plugin Name with color
	private static final Component eventannouncertext = 
		MiniMessageUtil.parse(
			"<gradient:yellow:blue>TitleAnnouncer</gradient>");
	// Line
	private static final TextComponent linelong = text("----------------------", DARK_GRAY);
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(linelong);
		Bukkit.getConsoleSender().sendMessage(text("Enabling ", AQUA).append(eventannouncertext));
		Bukkit.getConsoleSender().sendMessage(pvknet);
		Bukkit.getConsoleSender().sendMessage(linelong);
		pluginConfiguration();
		commandRegister();
		listenerRegister();
	}
	
	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(linelong);
		Bukkit.getConsoleSender().sendMessage(text("Disabling ", AQUA).append(eventannouncertext));
		Bukkit.getConsoleSender().sendMessage(pvknet);
		Bukkit.getConsoleSender().sendMessage(linelong);
	}
	
	// Registration of the commands that the plugin provides
	public void commandRegister() {
		// Main Command
		getCommand("announcer")
			.setExecutor(new AnnouncerCommand(this));
		// Title Commands
		getCommand("announcetitle")
			.setExecutor(new AnnouncerTitleCommand(this));
		getCommand("testtitle")
			.setExecutor(new SelfTitleCommand(this));
		getCommand("worldtitle")
			.setExecutor(new WorldTitleCommand(this));
		getCommand("sendtitle")
			.setExecutor(new SendTitleCommand(this));
		// ActionBar Commands
		getCommand("announceactionbar")
			.setExecutor(new AnnouncerActionbarCommand(this));
		getCommand("testactionbar")
			.setExecutor(new SelfActionbarCommand(this));
		getCommand("worldactionbar")
			.setExecutor(new WorldActionbarCommand(this));
		getCommand("sendactionbar")
			.setExecutor(new SendActionbarCommand(this));
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
