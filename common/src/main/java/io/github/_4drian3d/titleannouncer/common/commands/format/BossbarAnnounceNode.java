package io.github._4drian3d.titleannouncer.common.commands.format;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestions;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import io.github._4drian3d.titleannouncer.common.manager.BossBarManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public record BossbarAnnounceNode<C>(
    Formatter formatter,
    PlatformAdapter<?, C> platformAdapter,
    BossBarManager bossBarManager
) implements AnnounceNode<C> {

  @Override
  public LiteralArgumentBuilder<C> provideNode(TargetSuggestions<C> targetSuggestions) {
    return LiteralArgumentBuilder.<C>literal("bossbar")
        .requires(src -> platformAdapter.hasPermission(src, "titleannouncer.command.bossbar"))
        .then(RequiredArgumentBuilder.<C, String>argument("target", StringArgumentType.string())
            .suggests(targetSuggestions)
            .then(RequiredArgumentBuilder.<C, Integer>argument("seconds", IntegerArgumentType.integer(1))
                .then(RequiredArgumentBuilder.<C, String>argument("color", StringArgumentType.word())
                    .suggests((context, builder) -> {
                      BossBar.Color.NAMES.keys().forEach(builder::suggest);
                      return builder.buildFuture();
                    })
                    .then(RequiredArgumentBuilder.<C, String>argument("overlay", StringArgumentType.word())
                        .suggests((context, builder) -> {
                          BossBar.Overlay.NAMES.keys().forEach(builder::suggest);
                          return builder.buildFuture();
                        })
                        .then(RequiredArgumentBuilder.<C, String>argument("content", StringArgumentType.greedyString())
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
                              final int seconds = IntegerArgumentType.getInteger(ctx, "seconds");
                              final BossBar.Color color = BossBar.Color.NAMES.valueOrThrow(StringArgumentType.getString(ctx, "color"));
                              final BossBar.Overlay overlay = BossBar.Overlay.NAMES.valueOrThrow(StringArgumentType.getString(ctx, "overlay"));
                              final String content = StringArgumentType.getString(ctx, "content");

                              bossBarManager.sendBossBar(target, seconds, content, color, overlay);

                              return Command.SINGLE_SUCCESS;
                            })
                        )

                    )
                )
            )
        );
  }
}
