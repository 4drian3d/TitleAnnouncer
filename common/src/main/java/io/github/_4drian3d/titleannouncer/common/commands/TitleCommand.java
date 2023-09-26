package io.github._4drian3d.titleannouncer.common.commands;

import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.standard.DurationArgument;
import cloud.commandframework.arguments.standard.StringArgument;
import com.google.inject.Inject;
import io.github._4drian3d.titleannouncer.common.adapter.Commander;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

public final class TitleCommand implements AnnouncerCommand {
    @Inject
    private Formatter formatter;
    @Inject
    private ConfigurationContainer<Configuration> configurationContainer;

    @Override
    public void register(CommandManager<Commander> commandManager) {
        final var destinationArgument = StringArgument.<Commander>of("destination");
        final var destinationFlag = commandManager.flagBuilder("d")
                .withArgument(destinationArgument)
                .build();
        final var durationArgument = DurationArgument.of("duration");
        final var fadeInFlag = commandManager.flagBuilder("fadeIn")
                .withArgument(durationArgument)
                .build();
        final var stayFlag = commandManager.flagBuilder("stay")
                .withArgument(durationArgument)
                .build();
        final var fadeOutFlag = commandManager.flagBuilder("stay")
                .withArgument(durationArgument)
                .build();
        final var titleArgument = StringArgument.<Commander>quoted("title");
        final var subtitleArgument = StringArgument.<Commander>quoted("subtitle");
        commandManager.command(commandManager.commandBuilder("announcetitle")
                .permission("titleannouncer.announce.title")
                .argument(titleArgument)
                .argument(subtitleArgument)
                .flag(destinationFlag)
                .flag(fadeInFlag)
                .flag(stayFlag)
                .flag(fadeOutFlag)
                .handler(ctx -> {
                    final Commander sender = ctx.getSender();
                    final Component title = formatter.audienceFormat(ctx.get(titleArgument), sender);
                    final Component subtitle = formatter.audienceFormat(ctx.get(subtitleArgument), sender);

                    final var flags = ctx.flags();
                    final var config = configurationContainer.get().title();
                    final Duration fadeIn = requireNonNull(flags.getValue(fadeInFlag, config.defaultFadeIn()));
                    final Duration stay = requireNonNull(flags.getValue(fadeInFlag, config.defaultStay()));
                    final Duration fadeOut = requireNonNull(flags.getValue(fadeInFlag, config.defaultFadeOut()));

                    // TODO: Implement Destination parser
                    final String destinationString = ctx.flags().getValue(destinationFlag, "self");
                    Audience destinationAudience = Audience.empty();
                    destinationAudience.showTitle(Title.title(title, subtitle, Title.Times.times(fadeIn, stay, fadeOut)));
                }));

    }
}
