package me.dreamerzero.titleannouncer.paper.arguments;

import java.util.Collection;
import java.util.Set;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldArgument implements ArgumentType<World> {
    private static final Collection<String> EXAMPLES = Set.of("world", "world_the_nether");
    @Override
    public World parse(StringReader reader) throws CommandSyntaxException {
        World world = Bukkit.getWorld(reader.readUnquotedString());

        if (world != null) {
            return world;
        } else {
            throw new CommandSyntaxException(new SimpleCommandExceptionType(() -> "Invalid World"), () -> "Invalid World");
        }
            
    }

    @Override
    public Collection<String> getExamples() {
        return WorldArgument.EXAMPLES;
    }

    public static WorldArgument world(){
        return new WorldArgument();
    }
    
}
