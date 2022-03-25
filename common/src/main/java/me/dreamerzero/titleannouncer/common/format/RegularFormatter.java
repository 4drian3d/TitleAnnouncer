package me.dreamerzero.titleannouncer.common.format;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class RegularFormatter implements Formatter {

    @Override
    public Component globalFormat(String string) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    @Override
    public Component globalWithExtraResolver(String string, TagResolver resolver) {
        return MiniMessage.miniMessage().deserialize(string, resolver);
    }

    @Override
    public Component audienceFormat(String string, Audience audience) {
        return MiniMessage.miniMessage().deserialize(string);
    }

    @Override
    public Component audienceWithExtraResolver(String string, Audience audience, TagResolver resolver) {
        return MiniMessage.miniMessage().deserialize(string, resolver);
    }
    
}
