package io.github._4drian3d.titleannouncer.common.commands.format;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github._4drian3d.titleannouncer.common.adapter.PlatformAdapter;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.SoundSuggestions;
import io.github._4drian3d.titleannouncer.common.commands.suggestions.TargetSuggestions;
import io.github._4drian3d.titleannouncer.common.configuration.Configuration;
import io.github._4drian3d.titleannouncer.common.configuration.ConfigurationContainer;
import io.github._4drian3d.titleannouncer.common.configuration.Messages;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import java.util.Locale;
import java.util.Optional;

public record SoundAnnounceNode<C>(
    Formatter formatter,
    PlatformAdapter<?, C> platformAdapter,
    ConfigurationContainer<Configuration> configurationContainer,
    ConfigurationContainer<Messages> messagesContainer
) implements AnnounceNode<C> {
  @SuppressWarnings("PatternValidation")
  @Override
  public LiteralArgumentBuilder<C> provideNode(TargetSuggestions<C> targetSuggestions) {
    return LiteralArgumentBuilder.<C>literal("sound")
        .requires(src -> platformAdapter.hasPermission(src, "titleannouncer.command.sound"))
        .then(RequiredArgumentBuilder.<C, String>argument("target", StringArgumentType.string())
            .suggests(targetSuggestions)
            .then(RequiredArgumentBuilder.<C, String>argument("sound", StringArgumentType.string())
                .suggests(new SoundSuggestions<>())
                .executes(ctx -> {
                  final Audience executor = platformAdapter.nativeToAudience(ctx.getSource());
                  final Optional<? extends Audience> optionalTarget = platformAdapter
                      .destinationFromString(StringArgumentType.getString(ctx, "target"), executor);
                  if (optionalTarget.isEmpty()) {
                    executor.sendMessage(formatter.globalFormat(messagesContainer.get().invalidTarget()));
                    return -1;
                  }
                  final Audience target = optionalTarget.get();
                  final String soundArgument = StringArgumentType.getString(ctx, "sound").trim().toLowerCase(Locale.ROOT);
                  if (!Key.parseable(soundArgument)) {
                    target.sendMessage(formatter.globalWithExtraResolver(
                        messagesContainer.get().sound().invalidSoundProvided(),
                        Placeholder.parsed("sound", soundArgument)
                    ));
                    return -1;
                  }

                  final Configuration.Sound soundConfiguration = configurationContainer.get().sound();
                  final Key soundKey = Key.key(soundArgument);
                  target.playSound(Sound.sound(
                      soundKey,
                      soundConfiguration.defaultSource(),
                      soundConfiguration.defaultVolume(),
                      soundConfiguration.defaultPitch()
                  ), Sound.Emitter.self());
                  executor.sendMessage(formatter.globalWithExtraResolver(
                      messagesContainer.get().sound().playingSound(),
                      Placeholder.parsed("sound", soundKey.asString())
                  ));
                  return Command.SINGLE_SUCCESS;
                })
                .then(RequiredArgumentBuilder.<C, String>argument("source", StringArgumentType.word())
                    .suggests((context, builder) -> {
                      Sound.Source.NAMES.keys().forEach(builder::suggest);
                      return builder.buildFuture();
                    })
                    .then(RequiredArgumentBuilder.<C, Float>argument("volume", FloatArgumentType.floatArg(0, 1))
                        .then(RequiredArgumentBuilder.<C, Float>argument("pitch", FloatArgumentType.floatArg(0, 1))
                            .executes(ctx -> {
                              final Audience executor = platformAdapter.nativeToAudience(ctx.getSource());
                              final Optional<? extends Audience> optionalTarget = platformAdapter
                                  .destinationFromString(StringArgumentType.getString(ctx, "target"), executor);
                              if (optionalTarget.isEmpty()) {
                                executor.sendMessage(formatter.globalFormat(messagesContainer.get().invalidTarget()));
                                return -1;
                              }
                              final Audience target = optionalTarget.get();
                              final String soundArgument = StringArgumentType.getString(ctx, "sound").trim().toLowerCase(Locale.ROOT);
                              if (!Key.parseable(soundArgument)) {
                                target.sendMessage(formatter.globalWithExtraResolver(
                                    messagesContainer.get().sound().invalidSoundProvided(),
                                    Placeholder.parsed("sound", soundArgument)
                                ));
                                return -1;
                              }
                              final Sound.Source source = Sound.Source.NAMES.valueOrThrow(StringArgumentType.getString(ctx, "source")
                                  .toLowerCase(Locale.ROOT));
                              final float volume = FloatArgumentType.getFloat(ctx, "volume");
                              final float pitch = FloatArgumentType.getFloat(ctx, "pitch");

                              final Key soundKey = Key.key(soundArgument);
                              target.playSound(Sound.sound(soundKey, source, volume, pitch), Sound.Emitter.self());
                              executor.sendMessage(formatter.globalWithExtraResolver(
                                  messagesContainer.get().sound().playingSound(),
                                  Placeholder.parsed("sound", soundKey.asString())
                              ));
                              return Command.SINGLE_SUCCESS;
                            })
                        )
                    )
                )
            )
        );
  }
}
