package me.dreamerzero.titleannouncer.common.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.dreamerzero.titleannouncer.common.audience.TestAudience;
import me.dreamerzero.titleannouncer.common.exception.TitleException;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class TitleCommandTest {
    private static CommandDispatcher<TestAudience> dispatcher = new CommandDispatcher<>();

    @BeforeAll
    static void prepareCommands() {
        dispatcher.register(new TitleCommands<TestAudience>(new TestCommandAdapter())
            .title(LiteralArgumentBuilder.<TestAudience>literal("test")));
    }

    @Test
    @DisplayName("Self Title Command")
    void testSelfCommand(){
        final TestAudience audience = new TestAudience("selftitle");

        assertTrue(() -> {
            TitleException exception = assertThrows(
                TitleException.class,
                () -> dispatcher.execute("announcetitle self \"hello\" \"world\"", audience)
            );
            String title = PlainTextComponentSerializer.plainText().serialize(exception.title().title());
            String subtitle = PlainTextComponentSerializer.plainText().serialize(exception.title().subtitle());

            return title.equals("hello") && subtitle.equals("world");
        });
    }

}
