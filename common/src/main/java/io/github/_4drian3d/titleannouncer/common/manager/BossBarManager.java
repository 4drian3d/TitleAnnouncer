package io.github._4drian3d.titleannouncer.common.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github._4drian3d.titleannouncer.common.format.Formatter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Singleton
public final class BossBarManager {
    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
    private static final Map<UUID, Map<BossBarTask, ScheduledFuture<?>>> tasks = new ConcurrentHashMap<>();

    @Inject
    private Formatter formatter;

    public void sendBossBar(
            final @NotNull Audience audience,
            final float seconds,
            final @NotNull String content,
            final @NotNull BossBar.Color color,
            final @NotNull BossBar.Overlay type
    ) {
        final BossBar bar = BossBar.bossBar(formatter.audienceFormat(content, audience), 1, color, type);
        audience.showBossBar(bar);

        tasks.compute(audience.get(Identity.UUID).orElseThrow(), (a, b) -> {
            if (b == null) {
                b = new HashMap<>();
            }
            final float finalTime = 1f/seconds;
            var task = new BossBarTask(null, content, bar, audience, finalTime);
            b.put(task, EXECUTOR.scheduleAtFixedRate(task::run, 0, 1, TimeUnit.SECONDS));
            return b;
        });
    }

    private record BossBarTask (
            Formatter formatter,
            String content,
            BossBar bossBar,
            Audience viewer,
            float toRest
    ) {
        void run() {
            calculateTitle();
            calculateProgress();
        }

        void calculateTitle() {
            bossBar.name(formatter.audienceFormat(content, viewer));
        }

        void calculateProgress() {
            final float progress = bossBar.progress();
            final float toRest = progress - this.toRest;
            if (toRest < 0f) {
                stop();
                return;
            }
            bossBar.progress(toRest);
        }

        void stop() {
            viewer.hideBossBar(bossBar);
            tasks.remove(viewer.get(Identity.UUID).orElseThrow()).get(this).cancel(false);
        }
    }
}
