package io.github._4drian3d.titleannouncer.common.format;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import static io.github.miniplaceholders.api.MiniPlaceholders.audienceGlobalPlaceholders;
import static io.github.miniplaceholders.api.MiniPlaceholders.globalPlaceholders;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public sealed interface Formatter {

    Component globalFormat(String string);

    Component globalWithExtraResolver(String string, TagResolver resolver);

    Component audienceFormat(String string, Audience audience);

    Component audienceWithExtraResolver(String string, Audience audience, TagResolver resolver);

    final class Basic implements Formatter {
      @Override
      public Component globalFormat(final String string) {
        return miniMessage().deserialize(string);
      }

      @Override
      public Component globalWithExtraResolver(final String string, final TagResolver resolver) {
        return miniMessage().deserialize(string, resolver);
      }

      @Override
      public Component audienceFormat(final String string, final Audience audience) {
        return miniMessage().deserialize(string, audience);
      }

      @Override
      public Component audienceWithExtraResolver(final String string, final Audience audience, final TagResolver resolver) {
        return miniMessage().deserialize(string, audience, resolver);
      }
    }

  final class MiniPlaceholdersFormatter implements Formatter {

    @Override
    public Component globalFormat(String string) {
      return miniMessage().deserialize(string, globalPlaceholders());
    }

    @Override
    public Component globalWithExtraResolver(String string, TagResolver resolver) {
      return miniMessage().deserialize(string, globalPlaceholders(), resolver);
    }

    @Override
    public Component audienceFormat(String string, Audience audience) {
      return miniMessage().deserialize(string, audience, audienceGlobalPlaceholders());
    }

    @Override
    public Component audienceWithExtraResolver(String string, Audience audience, TagResolver resolver) {
      return miniMessage().deserialize(string, audience, audienceGlobalPlaceholders(), resolver);
    }
  }

  static Formatter basic() {
      return new Formatter.Basic();
  }

  static Formatter miniPlaceholders() {
      return new MiniPlaceholdersFormatter();
  }
}