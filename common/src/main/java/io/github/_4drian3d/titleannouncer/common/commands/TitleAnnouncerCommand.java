package io.github._4drian3d.titleannouncer.common.commands;

import com.google.inject.Inject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github._4drian3d.titleannouncer.common.Constants;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.PlayerSuggestionType;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestionType;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestions;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.configuration.Messages;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import io.github._4drian3d.titleannouncer.common.manager.BossBarManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import net.william278.desertwell.util.Version;

import java.util.Optional;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class TitleAnnouncerCommand<P extends Audience, C> {
  public static final Component INFO = AboutMenu.builder()
      .title(miniMessage().deserialize("<b><gradient:green:aqua>TitleAnnouncer</b>"))
      .description(text("A lightweight Paper and Velocity plugin to send Titles, Actionbars, Bossbars and Chat announces"))
      .credits("Author", AboutMenu.Credit.of("4drian3d").url("https://github.com/4drian3d"))
      .buttons(
          AboutMenu.Link.of("https://discord.gg/5NMMzK5mAn").text("Discord").color(TextColor.color(0x7289da)).icon("⭐"),
          AboutMenu.Link.of("https://modrinth.com/plugin/titleannouncer").text("Downloads").color(TextColor.color(0xff496e)).icon("↓")
      ).version(Version.fromString(Constants.VERSION))
      .build()
      .toComponent();

  @Inject
  private PlatformAdapter<P, C> platformAdapter;
  @Inject
  private ConfigurationContainer<Configuration> configurationContainer;
  @Inject
  private ConfigurationContainer<Messages> messagesContainer;
  @Inject
  private Formatter formatter;
  @Inject
  private BossBarManager bossBarManager;

  public LiteralCommandNode<C> buildCommand(final String prefix, TargetSuggestionType nativeTargetSuggestions) {
    final PlayerSuggestionType playerSuggestionType = new PlayerSuggestionType(platformAdapter);
    final TargetSuggestions<C> targetSuggestions = new TargetSuggestions<>(playerSuggestionType, nativeTargetSuggestions);

    return LiteralArgumentBuilder.<C>literal(prefix + "titleannouncer")
        .requires(src -> platformAdapter.hasPermission(src, "titleannouncer.command.admin"))
        .executes(ctx -> {
          platformAdapter.nativeToAudience(ctx.getSource()).sendMessage(INFO);
          return Command.SINGLE_SUCCESS;
        })
        .then(LiteralArgumentBuilder.<C>literal("reload")
            .executes(ctx -> {
              final Audience audience = platformAdapter.nativeToAudience(ctx.getSource());
              configurationContainer.reload()
                  .thenCombine(messagesContainer.reload(), (configurationReloaded, messagesReloaded) -> {
                    final Messages.Reload reloadMessages = messagesContainer.get().reload();
                    if (configurationReloaded && messagesReloaded) {
                      audience.sendMessage(formatter.globalFormat(reloadMessages.successfullyReloaded()));
                    } else {
                      audience.sendMessage(formatter.globalFormat(reloadMessages.errorWhileReloadingConfiguration()));
                    }
                    return null;
                  });
              return Command.SINGLE_SUCCESS;
            })
        )
        .then(LiteralArgumentBuilder.<C>literal("clear")
            .then(RequiredArgumentBuilder.<C, String>argument("target", StringArgumentType.string())
                .suggests(targetSuggestions)
                .then(LiteralArgumentBuilder.<C>literal("bossbar")
                    .executes(ctx -> {
                      final Audience audience = platformAdapter.nativeToAudience(ctx.getSource());
                      final Optional<? extends Audience> optionalTarget = platformAdapter
                          .destinationFromString(StringArgumentType.getString(ctx, "target"), audience);
                      if (optionalTarget.isEmpty()) {
                        audience.sendMessage(formatter.globalFormat(messagesContainer.get().invalidTarget()));
                        return -1;
                      }
                      final Audience target = optionalTarget.get();
                      if (target instanceof ForwardingAudience forwardingAudience) {
                        //noinspection OverrideOnly
                        forwardingAudience.audiences()
                            .forEach(single -> single.get(Identity.UUID)
                                .ifPresent(bossBarManager::cancelTasksByUUID));
                      } else {
                        target.get(Identity.UUID)
                            .ifPresent(bossBarManager::cancelTasksByUUID);
                      }
                      return Command.SINGLE_SUCCESS;
                    })
                )
                .then(LiteralArgumentBuilder.<C>literal("title")
                    .executes(ctx -> {
                      final Audience audience = platformAdapter.nativeToAudience(ctx.getSource());
                      final Optional<? extends Audience> optionalTarget = platformAdapter
                          .destinationFromString(StringArgumentType.getString(ctx, "target"), audience);
                      if (optionalTarget.isEmpty()) {
                        audience.sendMessage(formatter.globalFormat(messagesContainer.get().invalidTarget()));
                        return -1;
                      }
                      optionalTarget.get().clearTitle();
                      return Command.SINGLE_SUCCESS;
                    })
                )
            )
        )
        .build();
  }
}
