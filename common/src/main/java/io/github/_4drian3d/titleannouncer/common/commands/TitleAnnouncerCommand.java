package io.github._4drian3d.titleannouncer.common.commands;

import com.google.inject.Inject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github._4drian3d.titleannouncer.common.Constants;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.configuration.Messages;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;
import net.william278.desertwell.util.Version;

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

  public LiteralCommandNode<C> buildCommand(final String prefix) {
    return LiteralArgumentBuilder.<C>literal(prefix + "titleannouncer")
        .requires(src -> platformAdapter.hasPermission(src, "titleannouncer.command"))
        .executes(ctx -> {
          platformAdapter.nativeToAudience(ctx.getSource()).sendMessage(INFO);
          return Command.SINGLE_SUCCESS;
        })
        .then(LiteralArgumentBuilder.<C>literal("reload")
            .executes(ctx -> {
              final Audience audience = platformAdapter.nativeToAudience(ctx.getSource());
              configurationContainer.reload()
                  .thenCombine(messagesContainer.reload(), (configurationReloaded, messagesReloaded) -> {
                    if (configurationReloaded && messagesReloaded) {
                      audience.sendMessage(formatter.globalFormat(messagesContainer.get().successfullyReloaded()));
                    } else {
                      audience.sendMessage(formatter.globalFormat(messagesContainer.get().errorWhileReloadingConfiguration()));
                    }
                    return null;
                  });
              return Command.SINGLE_SUCCESS;
            })
        )
        .build();
  }
}
