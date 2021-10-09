package net.dreamerzero.titleannouncer.paper.utils;

import org.bukkit.command.PluginCommand;

import de.leonhard.storage.Yaml;
import net.dreamerzero.titleannouncer.common.utils.ConfigManager;
import net.dreamerzero.titleannouncer.common.utils.ConfigUtils;
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
import net.kyori.adventure.text.minimessage.MiniMessage;

public record RegisterCommands(Announcer plugin, MiniMessage mm) {
	/**
	 * Register of each existing Paper plugin command
	 * @author Jonakls
	 */
	public void registerCommands() {
		initCommand(
			// BossBar
			new CommandFactory("announcebossbar", new AnnouncerBossbarCommand(plugin, mm)),
			new CommandFactory("selfbossbar", new SelfBossbarCommand(plugin, mm)),
			new CommandFactory("worldbossbar", new WorldBossbarCommand(plugin, mm)),
			new CommandFactory("sendbossbar", new SendBossbarCommand(plugin, mm)),
			// Title
			new CommandFactory("announcetitle", new AnnouncerTitleCommand(mm)),
			new CommandFactory("selftitle", new SelfTitleCommand(mm)),
			new CommandFactory("worldtitle", new WorldTitleCommand(mm)),
			new CommandFactory("sendtitle", new SendTitleCommand(mm)),
			// ActionBar
			new CommandFactory("announceactionbar", new AnnouncerActionbarCommand(mm)),
			new CommandFactory("selfactionbar", new SelfActionbarCommand(mm)),
			new CommandFactory("worldactionbar", new WorldActionbarCommand(mm)),
			new CommandFactory("sendactionbar", new SendActionbarCommand(mm)),
			// MainCommand
			new CommandFactory("announcer", new AnnouncerCommand(mm))
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
			PluginCommand command = this.plugin.getCommand(factory.command());
			command.setExecutor(factory.executor());
			command.permissionMessage(
				ConfigUtils.getPrefix().append(
					mm.parse(config.getString("messages.general.no-permission"))));
		}
	}
}
