package net.dreamerzero.titleannouncer.velocity.utils;

import dev.simplix.protocolize.data.Sound;

public class SoundTransformer {
    public static Sound getSoundFromString(String sound){
        String soundReplaced = sound.replace("_", ".");
        for (Sound mcsound : Sound.values()){
            if(mcsound.toString().replace("_", ".").equalsIgnoreCase(soundReplaced)){
                return mcsound;
            }
        }
        return Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
    }

    private SoundTransformer(){}
}
