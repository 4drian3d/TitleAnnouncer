package me.dreamerzero.titleannouncer.common.exception;

import net.kyori.adventure.title.Title;

public class TitleException extends RuntimeException {
    private final Title title;

    public TitleException(Title title) {
        super();
        this.title = title;
    }

    public Title title(){
        return this.title;
    }
}
