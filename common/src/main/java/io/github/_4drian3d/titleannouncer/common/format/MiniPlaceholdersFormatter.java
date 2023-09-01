package io.github._4drian3d.titleannouncer.common.format;

import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public final class MiniPlaceholdersFormatter extends Formatter {

    @Override
    public Component globalFormat(String string) {
        return miniMessage().deserialize(string,
            MiniPlaceholders.getGlobalPlaceholders()
        );
    }

    @Override
    public Component globalWithExtraResolver(String string, TagResolver resolver) {
        return miniMessage().deserialize(string,
            MiniPlaceholders.getGlobalPlaceholders(),
            resolver
        );
    }

    @Override
    public Component audienceFormat(String string, Audience audience) {
        return miniMessage().deserialize(string,
            MiniPlaceholders.getAudienceGlobalPlaceholders(audience)
        );
    }

    @Override
    public Component audienceWithExtraResolver(String string, Audience audience, TagResolver resolver) {
        return miniMessage().deserialize(string,
            MiniPlaceholders.getAudienceGlobalPlaceholders(audience),
            resolver
        );
    }
    
}
