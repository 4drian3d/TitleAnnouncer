package me.dreamerzero.titleannouncer.common.audience;

import me.dreamerzero.titleannouncer.common.exception.ChatException;
import me.dreamerzero.titleannouncer.common.exception.TitleException;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

public final class TestAudience implements Audience {
    private final String name;
    public TestAudience(String name) {
        this.name = name;
    }
    @Override
    public void showTitle(Title title){
        throw new TitleException(title);
    }

    @Override
    public void sendMessage(Component component){
        throw new ChatException(component);
    }
}
