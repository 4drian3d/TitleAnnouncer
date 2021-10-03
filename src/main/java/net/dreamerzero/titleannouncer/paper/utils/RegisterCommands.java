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
	private Announcer plugin;
	public RegisterCommands(Announcer plugin){
		this.plugin = plugin;
	}

    public void registerBossbar() {
        plugin.getCommand("announcebossbar")
			.setExecutor(new AnnouncerBossbarCommand(plugin));
		plugin.getCommand("selfbossbar")
			.setExecutor(new SelfBossbarCommand(plugin));
		plugin.getCommand("worldbossbar")
			.setExecutor(new WorldBossbarCommand(plugin));
		plugin.getCommand("sendbossbar")
			.setExecutor(new SendBossbarCommand(plugin));
    }
    public void registerTitle() {
        plugin.getCommand("announcetitle")
			.setExecutor(new AnnouncerTitleCommand());
		plugin.getCommand("selftitle")
			.setExecutor(new SelfTitleCommand());
		plugin.getCommand("worldtitle")
			.setExecutor(new WorldTitleCommand());
		plugin.getCommand("sendtitle")
			.setExecutor(new SendTitleCommand());
    }
    public void registerActionbar() {
        plugin.getCommand("announceactionbar")
			.setExecutor(new AnnouncerActionbarCommand());
		plugin.getCommand("selfactionbar")
			.setExecutor(new SelfActionbarCommand());
		plugin.getCommand("worldactionbar")
			.setExecutor(new WorldActionbarCommand());
		plugin.getCommand("sendactionbar")
			.setExecutor(new SendActionbarCommand());
    }
    public void registerMainCommand(){
        plugin.getCommand("announcer")
			.setExecutor(new AnnouncerCommand());
    }
	public void setCustomNoPermissionMessage(){
		Yaml config = new ConfigManager().getConfig();

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
					new ConfigUtils().getPrefix().append(
						new MiniMessageUtil().parse(config.getString("messages.general.no-permission")))));
		});
	}
}
