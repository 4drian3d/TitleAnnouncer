package io.github._4drian3d.titleannouncer.common.commands.format;

import com.mojang.brigadier.Command;
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

import java.util.Optional;

public record ActionbarAnnounceNode<C>(
    Formatter formatter,
    PlatformAdapter<?, C> platformAdapter,
    ConfigurationContainer<Configuration> configurationContainer,
    ConfigurationContainer<Messages> messagesContainer
) implements AnnounceNode<C> {

  @Override
  public LiteralArgumentBuilder<C> provideNode(TargetSuggestions<C> targetSuggestions) {
    return LiteralArgumentBuilder.<C>literal("actionbar")
        .requires(src -> platformAdapter.hasPermission(src, "titleannouncer.command.actionbar"))
        .then(RequiredArgumentBuilder.<C, String>argument("target", StringArgumentType.string())
            .suggests(targetSuggestions)
            .then(RequiredArgumentBuilder.<C, String>argument("message", StringArgumentType.greedyString())
                .executes(ctx -> {
                  final Audience executor = platformAdapter.nativeToAudience(ctx.getSource());
                  final Optional<? extends Audience> optionalTarget = platformAdapter
                      .destinationFromString(StringArgumentType.getString(ctx, "target"), executor);
                  if (optionalTarget.isEmpty()) {
                    executor.sendMessage(formatter.globalFormat(messagesContainer.get().invalidTarget()));
                    return -1;
                  }
                  final Audience target = optionalTarget.get();
                  final Component message = formatter.audienceFormat(StringArgumentType.getString(ctx, "message"), target);
                  target.sendActionBar(message);
                  executor.sendMessage(formatter.audienceFormat(messagesContainer.get().actionbar().announceSent(), executor));

                  return Command.SINGLE_SUCCESS;
                })
            )
        );
  }
}
