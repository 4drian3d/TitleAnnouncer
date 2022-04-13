package me.dreamerzero.titleannouncer.common;

import me.dreamerzero.titleannouncer.common.format.Formatter;
import me.dreamerzero.titleannouncer.common.format.RegularFormatter;

public final class TitleAnnouncer {
    private TitleAnnouncer(){}

    private static Formatter formatter = new RegularFormatter();

    public static void formatter(Formatter newFormatter){
        formatter = newFormatter;
    }

    public static Formatter formatter(){
        return formatter;
    }
    
}
