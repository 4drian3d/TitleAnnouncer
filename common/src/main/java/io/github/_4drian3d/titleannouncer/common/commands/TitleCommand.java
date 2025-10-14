package io.github._4drian3d.titleannouncer.common.commands;

import com.google.inject.Inject;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.format.Formatter;

public class TitleCommand implements AnnouncerCommand {
    @Inject
    private Formatter formatter;
    @Inject
    private ConfigurationContainer<Configuration> configurationContainer;
    @Inject
    private PlatformAdapter<?> platformAdapter;


    @Override
    public void register() {
//        final var destinationArgument = StringArgument.<Commander>of("destination");
//        final var destinationFlag = commandManager.flagBuilder("d")
//                .withArgument(destinationArgument)
//                .build();
//        final var fadeInArgument = DurationArgument.<Commander>builder("fadeIn")
//                .asOptional()
//                .build();
//        final var stayArgument = DurationArgument.<Commander>builder("stay")
//                .asOptional()
//                .build();
//        final var fadeOutArgument = DurationArgument.<Commander>builder("stay")
//                .asOptional()
//                .build();
//        final var titleArgument = StringArgument.<Commander>quoted("title");
//        final var subtitleArgument = StringArgument.<Commander>quoted("subtitle");
//        commandManager.command(commandManager.commandBuilder("announcetitle")
//                .permission("titleannouncer.announce.title")
//                .argument(titleArgument)
//                .argument(subtitleArgument)
//                .argument(fadeInArgument)
//                .argument(stayArgument)
//                .argument(fadeOutArgument)
//                .flag(destinationFlag)
//                .handler(ctx -> {
//                    final Commander sender = ctx.getSender();
//                    final Component title = formatter.audienceFormat(ctx.get(titleArgument), sender);
//                    final Component subtitle = formatter.audienceFormat(ctx.get(subtitleArgument), sender);
//
//                    final var config = configurationContainer.get().title();
//                    final Duration fadeIn = requireNonNull(ctx.getOrDefault(fadeInArgument, config.defaultFadeIn()));
//                    final Duration stay = requireNonNull(ctx.getOrDefault(fadeInArgument, config.defaultStay()));
//                    final Duration fadeOut = requireNonNull(ctx.getOrDefault(fadeInArgument, config.defaultFadeOut()));
//
//                    final String destinationString = ctx.flags().getValue(destinationFlag, "self");
//                    assert destinationString != null;
//                    platformAdapter.destinationFromString(destinationString, sender)
//                            .ifPresent(destination -> destination.showTitle(Title.title(title, subtitle, Title.Times.times(fadeIn, stay, fadeOut))));
//                }));

    }
}
