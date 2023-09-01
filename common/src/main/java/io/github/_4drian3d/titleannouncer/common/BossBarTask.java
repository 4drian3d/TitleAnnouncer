package io.github._4drian3d.titleannouncer.common;

import com.google.inject.Inject;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class BossBarTask {
    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();

    @Inject
    private Formatter formatter;

    public void sendBossBar(
            final @NotNull Audience audience,
            final float time,
            final @NotNull String content,
            final @NotNull BossBar.Color color,
            final @NotNull BossBar.Overlay type
    ) {
        final float[] value = new float[] {1f};
        final float finalTime = 0.1f/time;

        final BossBar bar = BossBar.bossBar(formatter.audienceFormat(content, audience), time, color, type);

        audience.showBossBar(bar);

        final ScheduledFuture[] taskArray = new ScheduledFuture[1];
        taskArray[0] = EXECUTOR.scheduleAtFixedRate(() -> {
            value[0] -= finalTime;
            if (value[0] <= 0.02) {
                audience.hideBossBar(bar);
                taskArray[0].cancel(false);
            }
            try {
                bar.name(formatter.audienceFormat(content, audience));
                bar.progress(value[0]);
            } catch (IllegalArgumentException e) {
                audience.hideBossBar(bar);
                taskArray[0].cancel(false);
            }
        }, 0, 200L, TimeUnit.MILLISECONDS);
    }
}
