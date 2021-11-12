package net.dreamerzero.titleannouncer.common.utils;

import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.template.TemplateResolver;

public interface PlaceholderProvider {
    public default MiniMessage minimessage(){
        return MiniMessage.miniMessage();
    }
    /**
     * Replace Placeholders in Components
     * for the context of Console/Global
     * @return Placeholders for console
     */
    public TemplateResolver replacePlaceholders();

    public Component applyPlaceholders(@NotNull String string);
}
