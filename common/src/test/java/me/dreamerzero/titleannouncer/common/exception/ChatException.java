package me.dreamerzero.titleannouncer.common.exception;

import net.kyori.adventure.text.Component;

public class ChatException extends RuntimeException {
    private final Component component;
    public ChatException(Component component){
        super();
        this.component = component;
    }

    public Component component(){
        return this.component;
    }
}
