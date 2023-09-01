package io.github._4drian3d.titleannouncer.common.format;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class Formatter {

    public Component globalFormat(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    public Component globalWithExtraResolver(String string, TagResolver resolver) {
        return MiniMessage.miniMessage().deserialize(string, resolver);
    }

    public Component audienceFormat(String string, Audience audience) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    public Component audienceWithExtraResolver(String string, Audience audience, TagResolver resolver) {
        return MiniMessage.miniMessage().deserialize(string, resolver);
    }

}