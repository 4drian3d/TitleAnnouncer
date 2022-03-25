package me.dreamerzero.titleannouncer.common.format;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public interface Formatter {
    Component globalFormat(String string);

    Component globalWithExtraResolver(String string, TagResolver resolver);

    Component audienceFormat(String string, Audience audience);

    Component audienceWithExtraResolver(String string, Audience audience, TagResolver resolver);
}
