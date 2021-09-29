package net.dreamerzero.titleannouncer.velocity.utils;

import dev.simplix.protocolize.data.Sound;

import static dev.simplix.protocolize.data.Sound.*;

public class SoundTransformer {
    //TODO: Finish this
    static Sound getSoundFromString(String sound){
        return switch(sound){
            case "ambient.cave" -> AMBIENT_CAVE;
            case "ambient.underwater.enter" -> AMBIENT_UNDERWATER_ENTER;
            case "ambient.underwater.exit" -> AMBIENT_UNDERWATER_EXIT;
            case "ambient.underwater.loop" -> AMBIENT_UNDERWATER_LOOP;
            case "ambient.underwater.loop.additions" -> AMBIENT_UNDERWATER_LOOP_ADDITIONS;
            case "ambient.underwater.loop.additions.rare" -> AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE;
            case "ambient.underwater.loop.additions.ultra.rare" -> AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE;
            case "block.amethyst.block.break" -> BLOCK_AMETHYST_BLOCK_BREAK;
            case "block.amethyst.block.chime" -> BLOCK_AMETHYST_BLOCK_CHIME;
            case "block.amethyst.block.fall" -> BLOCK_AMETHYST_BLOCK_FALL;
            case "block.amethyst.block.hit" -> BLOCK_AMETHYST_BLOCK_HIT;
            case "block.amethyst.block.place" -> BLOCK_AMETHYST_BLOCK_PLACE;
            case "block.anvil.break" -> BLOCK_ANVIL_BREAK;
            case "block.anvil.destroy" -> BLOCK_ANVIL_DESTROY;
            case "block.anvil.fall" -> BLOCK_ANVIL_FALL;
            case "block.anvil.hit" -> BLOCK_ANVIL_HIT;
            case "block.anvil.land" -> BLOCK_ANVIL_LAND;
            case "block.anvil.place" -> BLOCK_ANVIL_PLACE;
            default -> ENTITY_EXPERIENCE_ORB_PICKUP;
        };
    }
}
