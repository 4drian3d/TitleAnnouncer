package me.dreamerzero.titleannouncer.common.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.dreamerzero.titleannouncer.common.audience.TestAudience;
import me.dreamerzero.titleannouncer.common.exception.ChatException;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ChatCommandTest {
    private static CommandDispatcher<TestAudience> dispatcher = new CommandDispatcher<>();

    @BeforeAll
    static void prepareCommands() {
        dispatcher.register(new ChatCommands<TestAudience>(new TestCommandAdapter())
            .chat(LiteralArgumentBuilder.<TestAudience>literal("test")));
    }

    @Test
    @DisplayName("Self Chat Command")
    void selfChatTest(){
        TestAudience source = new TestAudience("selfchat");

        assertTrue(() -> {
            ChatException exception = assertThrows(
                ChatException.class,
                () -> dispatcher.execute(
                    "announcechat self \"<rainbow>message\"",
                    source
                )
            );
            String message = PlainTextComponentSerializer.plainText().serialize(exception.component());

            return message.equals("message");
        });
    }
}
