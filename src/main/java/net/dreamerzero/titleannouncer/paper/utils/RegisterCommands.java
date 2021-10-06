package net.dreamerzero.titleannouncer.paper.utils;

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

	/**
	 * Register of each existing Paper plugin command
	 * @author Jonakls
	 */
	public void registerCommands() {
		initCommand(
			// BossBar
			new CommandFactory("announcebossbar", new AnnouncerBossbarCommand(plugin)),
			new CommandFactory("selfbossbar", new SelfBossbarCommand(plugin)),
			new CommandFactory("worldbossbar", new WorldBossbarCommand(plugin)),
			new CommandFactory("sendbossbar", new SendBossbarCommand(plugin)),
			// Title
			new CommandFactory("announcetitle", new AnnouncerTitleCommand()),
			new CommandFactory("selftitle", new SelfTitleCommand()),
			new CommandFactory("worldtitle", new WorldTitleCommand()),
			new CommandFactory("sendtitle", new SendTitleCommand()),
			// ActionBar
			new CommandFactory("announceactionbar", new AnnouncerActionbarCommand()),
			new CommandFactory("selfactionbar", new SelfActionbarCommand()),
			new CommandFactory("worldactionbar", new WorldActionbarCommand()),
			new CommandFactory("sendactionbar", new SendActionbarCommand()),
			// MainCommand
			new CommandFactory("announcer", new AnnouncerCommand())
		);
	}

	/**
	 * Applying properties to each command to be registered
	 * @author Jonakls
	 * @param factories command factories
	 */
	private void initCommand(CommandFactory ...factories) {
		Yaml config = new ConfigManager().getConfig();
		for(CommandFactory factory : factories) {
			PluginCommand command = this.plugin.getCommand(factory.getCommand());
			command.setExecutor(factory.getExecutor());
			// Waiting for https://github.com/PaperMC/Paper/pull/6676
			command.setPermissionMessage(
				LegacyComponentSerializer.legacySection().serialize(
					new ConfigUtils().getPrefix().append(
						new MiniMessageUtil().parse(
							config.getString("messages.general.no-permission")))));
		}
	}
}
