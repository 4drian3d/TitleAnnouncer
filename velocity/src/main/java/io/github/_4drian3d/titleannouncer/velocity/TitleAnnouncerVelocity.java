package io.github._4drian3d.titleannouncer.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github._4drian3d.titleannouncer.common.TitleAnnouncer;

@Singleton
public final class TitleAnnouncerVelocity extends TitleAnnouncer {
    @Inject
    public TitleAnnouncerVelocity(Injector injector) {
        super(injector);
    }

    @Override
    public void stop() {

    }
}
