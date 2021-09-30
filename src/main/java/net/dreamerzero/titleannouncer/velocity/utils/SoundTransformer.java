package net.dreamerzero.titleannouncer.velocity.utils;

import dev.simplix.protocolize.data.Sound;

public class SoundTransformer {
    public static Sound getSoundFromString(String sound){
        String soundReplaced = sound.replaceAll("_", ".");
        for (Sound mcsound : Sound.values()){
            if(mcsound.toString().replaceAll("_", ".").equalsIgnoreCase(soundReplaced)){
                return mcsound;
            }
        }
        return Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
    }
}
