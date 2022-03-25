package me.dreamerzero.titleannouncer.common;

import me.dreamerzero.titleannouncer.common.format.Formatter;

public final class TitleAnnouncer {
    private TitleAnnouncer(){}

    private static Formatter formatter;

    public static void setFormatter(Formatter newFormatter){
        formatter = newFormatter;
    }

    public static Formatter getFormatter(){
        return formatter;
    }
    
}
