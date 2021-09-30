package net.dreamerzero.titleannouncer.paper.utils;

import java.util.List;

import org.bukkit.command.PluginCommand;

import de.leonhard.storage.Yaml;
import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
import net.dreamerzero.titleannouncer.common.utils.MiniMessageUtil;
import net.dreamerzero.titleannouncer.paper.Announcer;
import net.dreamerzero.titleannouncer.paper.commands.AnnouncerCommand;
import net.dreamerzero.titleannouncer.paper.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.actionbar.SelfActionbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.actionbar.SendActionbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.actionbar.WorldActionbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.bossbar.AnnouncerBossbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.bossbar.SelfBossbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.bossbar.SendBossbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.bossbar.WorldBossbarCommand;
import net.dreamerzero.titleannouncer.paper.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.titleannouncer.paper.commands.title.SelfTitleCommand;
import net.dreamerzero.titleannouncer.paper.commands.title.SendTitleCommand;
import net.dreamerzero.titleannouncer.paper.commands.title.WorldTitleCommand;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class RegisterCommands {
    public static void registerBossbar() {
		Announcer plugin = Announcer.getInstance();
        plugin.getCommand("announcebossbar")
			.setExecutor(new AnnouncerBossbarCommand());
		plugin.getCommand("selfbossbar")
			.setExecutor(new SelfBossbarCommand());
		plugin.getCommand("worldbossbar")
			.setExecutor(new WorldBossbarCommand());
		plugin.getCommand("sendbossbar")
			.setExecutor(new SendBossbarCommand());
    }
    public static void registerTitle() {
		Announcer plugin = Announcer.getInstance();
        plugin.getCommand("announcetitle")
			.setExecutor(new AnnouncerTitleCommand());
		plugin.getCommand("selftitle")
			.setExecutor(new SelfTitleCommand());
		plugin.getCommand("worldtitle")
			.setExecutor(new WorldTitleCommand());
		plugin.getCommand("sendtitle")
			.setExecutor(new SendTitleCommand());
    }
    public static void registerActionbar() {
		Announcer plugin = Announcer.getInstance();
        plugin.getCommand("announceactionbar")
			.setExecutor(new AnnouncerActionbarCommand());
		plugin.getCommand("selfactionbar")
			.setExecutor(new SelfActionbarCommand());
		plugin.getCommand("worldactionbar")
			.setExecutor(new WorldActionbarCommand());
		plugin.getCommand("sendactionbar")
			.setExecutor(new SendActionbarCommand());
    }
    public static void registerMainCommand(){
		Announcer plugin = Announcer.getInstance();
        plugin.getCommand("announcer")
			.setExecutor(new AnnouncerCommand());
    }
	public static void setCustomNoPermissionMessage(){
		Announcer plugin = Announcer.getInstance();
		Yaml config = ConfigManager.getConfig();

		List<PluginCommand> commands = List.of(
			plugin.getCommand("announcebossbar"),
			plugin.getCommand("selfbossbar"),
			plugin.getCommand("worldbossbar"),
			plugin.getCommand("sendbossbar"),
			plugin.getCommand("announcetitle"),
			plugin.getCommand("selftitle"),
			plugin.getCommand("worldtitle"),
			plugin.getCommand("sendtitle"),
			plugin.getCommand("announceactionbar"),
			plugin.getCommand("selfactionbar"),
			plugin.getCommand("sendactionbar"),
			plugin.getCommand("announcer"));

		commands.forEach(command -> {
			//Waiting for https://github.com/PaperMC/Paper/pull/6676
			command.setPermissionMessage(
				LegacyComponentSerializer.legacyAmpersand().serialize(
					ConfigUtils.getPrefix().append(
						MiniMessageUtil.parse(config.getString("messages.general.no-permission")))));
		});
	}
}
