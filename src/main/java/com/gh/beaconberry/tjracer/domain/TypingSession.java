package com.gh.beaconberry.tjracer.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record TypingSession(
        List<TypeEvent> typeEvents,
        Instant startInstant
) {
    public static TypingSession create() {
        return new TypingSession(new ArrayList<>(), Instant.now());
    }
}
