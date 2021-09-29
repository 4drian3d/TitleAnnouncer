package net.dreamerzero.titleannouncer.utils;

import net.dreamerzero.titleannouncer.Announcer;
import net.dreamerzero.titleannouncer.commands.AnnouncerCommand;
import net.dreamerzero.titleannouncer.commands.actionbar.AnnouncerActionbarCommand;
import net.dreamerzero.titleannouncer.commands.actionbar.SelfActionbarCommand;
import net.dreamerzero.titleannouncer.commands.actionbar.SendActionbarCommand;
import net.dreamerzero.titleannouncer.commands.actionbar.WorldActionbarCommand;
import net.dreamerzero.titleannouncer.commands.bossbar.AnnouncerBossbarCommand;
import net.dreamerzero.titleannouncer.commands.bossbar.SelfBossbarCommand;
import net.dreamerzero.titleannouncer.commands.bossbar.SendBossbarCommand;
import net.dreamerzero.titleannouncer.commands.bossbar.WorldBossbarCommand;
import net.dreamerzero.titleannouncer.commands.title.AnnouncerTitleCommand;
import net.dreamerzero.titleannouncer.commands.title.SelfTitleCommand;
import net.dreamerzero.titleannouncer.commands.title.SendTitleCommand;
import net.dreamerzero.titleannouncer.commands.title.WorldTitleCommand;

public class RegisterCommands {

	static final Announcer plugin = Announcer.getInstance();

    public static void registerCommands() {
		initCommand(
				// BossBar
				new CommandFactory("announcebossbar", new AnnouncerBossbarCommand()),
				new CommandFactory("selfbossbar", new SelfBossbarCommand(plugin)),
				new CommandFactory("worldbossbar", new WorldBossbarCommand(plugin)),
				new CommandFactory("sendbossbar", new SendBossbarCommand()),
				// Title
				new CommandFactory("announcetitle", new AnnouncerTitleCommand()),
				new CommandFactory("selftitle", new SelfTitleCommand(plugin)),
				new CommandFactory("worldtitle", new WorldTitleCommand(plugin)),
				new CommandFactory("sendtitle", new SendTitleCommand()),
				// ActionBar
				new CommandFactory("announceactionbar", new AnnouncerActionbarCommand()),
				new CommandFactory("selfactionbar", new SelfActionbarCommand(plugin)),
				new CommandFactory("worldactionbar", new WorldActionbarCommand(plugin)),
				new CommandFactory("sendactionbar", new SendActionbarCommand()),
				// MainCommand
				new CommandFactory("announcer", new AnnouncerCommand(plugin))

		);
    }

	private static void initCommand(CommandFactory ...factories) {
		for(CommandFactory factory : factories) {
			plugin.getCommand(factory.getCommand()).setExecutor(factory.getExecutor());
		}
	}
}
