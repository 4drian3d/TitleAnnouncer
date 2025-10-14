package io.github._4drian3d.titleannouncer.common.commands.format;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestions;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public record ChatAnnounceNode<C>(
    Formatter formatter,
    PlatformAdapter<?, C> platformAdapter
) implements AnnounceNode<C> {

  @Override
  public LiteralArgumentBuilder<C> provideNode(TargetSuggestions<C> targetSuggestions) {
    return LiteralArgumentBuilder.<C>literal("chat")
        .requires(src -> platformAdapter.hasPermission(src, "titleannouncer.command.chat"))
        .then(RequiredArgumentBuilder.<C, String>argument("target", StringArgumentType.string())
            .suggests(targetSuggestions)
            .then(RequiredArgumentBuilder.<C, String>argument("message", StringArgumentType.greedyString())
                .executes(ctx -> {
                  final Audience executor = platformAdapter.nativeToAudience(ctx.getSource());
                  final Optional<? extends Audience> optionalTarget = platformAdapter
                      .destinationFromString(StringArgumentType.getString(ctx, "target"), executor);
                  if (optionalTarget.isEmpty()) {
                    // TODO: send error message to executor
                    executor.sendMessage(Component.text("error"));
                    return -1;
                  }
                  final Audience target = optionalTarget.get();
                  final Component message = formatter.audienceFormat(StringArgumentType.getString(ctx, "message"), target);
                  target.sendMessage(message);

                  return Command.SINGLE_SUCCESS;
                })
            )
        );
  }
}
