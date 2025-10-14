package io.github._4drian3d.titleannouncer.common.commands.format;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestions;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.configuration.Messages;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;

import java.time.Duration;
import java.util.Optional;

public record TitleAnnounceNode<C>(
    Formatter formatter,
    PlatformAdapter<?, C> platformAdapter,
    ConfigurationContainer<Configuration> configurationContainer,
    ConfigurationContainer<Messages> messagesContainer
) implements AnnounceNode<C> {

  @Override
  public LiteralArgumentBuilder<C> provideNode(final TargetSuggestions<C> targetSuggestions) {
    return LiteralArgumentBuilder.<C>literal("title")
        .requires(src -> platformAdapter.hasPermission(src, "titleannouncer.command.title"))
        .then(RequiredArgumentBuilder.<C, String>argument("target", StringArgumentType.string())
            .suggests(targetSuggestions)
            .then(RequiredArgumentBuilder.<C, String>argument("title", StringArgumentType.string())
                .then(RequiredArgumentBuilder.<C, String>argument("subtitle", StringArgumentType.string())
                    .executes(ctx -> {
                      final Audience executor = platformAdapter.nativeToAudience(ctx.getSource());
                      final Optional<? extends Audience> optionalTarget = platformAdapter
                          .destinationFromString(StringArgumentType.getString(ctx, "target"), executor);
                      if (optionalTarget.isEmpty()) {
                        executor.sendMessage(formatter.globalFormat(messagesContainer.get().invalidTarget()));
                        return -1;
                      }
                      final Audience target = optionalTarget.get();
                      final Component title = formatter.audienceFormat(StringArgumentType.getString(ctx, "title"), target);
                      final Component subtitle = formatter.audienceFormat(StringArgumentType.getString(ctx, "subtitle"), target);

                      final Configuration.Title titleConfiguration = configurationContainer.get().title();
                      target.sendTitlePart(TitlePart.TITLE, title);
                      target.sendTitlePart(TitlePart.SUBTITLE, subtitle);
                      target.sendTitlePart(TitlePart.TIMES, Title.Times.times(
                          Duration.ofMillis(titleConfiguration.defaultFadeIn()),
                          Duration.ofMillis(titleConfiguration.defaultStay()),
                          Duration.ofMillis(titleConfiguration.defaultFadeOut())
                      ));
                      return Command.SINGLE_SUCCESS;
                    })
                    .then(RequiredArgumentBuilder.<C, Integer>argument("fadein", IntegerArgumentType.integer(1))
                        .then(RequiredArgumentBuilder.<C, Integer>argument("stay", IntegerArgumentType.integer(1))
                            .then(RequiredArgumentBuilder.<C, Integer>argument("fadeout", IntegerArgumentType.integer(1))
                                .executes(ctx -> {
                                  final Audience executor = platformAdapter.nativeToAudience(ctx.getSource());
                                  final Optional<? extends Audience> optionalTarget = platformAdapter
                                      .destinationFromString(StringArgumentType.getString(ctx, "target"), executor);
                                  if (optionalTarget.isEmpty()) {
                                    executor.sendMessage(formatter.globalFormat(messagesContainer.get().invalidTarget()));
                                    return -1;
                                  }
                                  final Audience target = optionalTarget.get();
                                  final Component title = formatter.audienceFormat(StringArgumentType.getString(ctx, "title"), target);
                                  final Component subtitle = formatter.audienceFormat(StringArgumentType.getString(ctx, "subtitle"), target);
                                  final int fadeIn = IntegerArgumentType.getInteger(ctx, "fadein");
                                  final int stay = IntegerArgumentType.getInteger(ctx, "stay");
                                  final int fadeout = IntegerArgumentType.getInteger(ctx, "fadeout");

                                  target.sendTitlePart(TitlePart.TITLE, title);
                                  target.sendTitlePart(TitlePart.SUBTITLE, subtitle);
                                  target.sendTitlePart(TitlePart.TIMES, Title.Times.times(
                                      Duration.ofMillis(fadeIn),
                                      Duration.ofMillis(stay),
                                      Duration.ofMillis(fadeout)
                                  ));
                                  return Command.SINGLE_SUCCESS;
                                })
                            )
                        )
                    )
                )
            )
        );
  }
}
