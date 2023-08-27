package com.gh.beaconberry.tjracer.domain;

import java.time.Instant;

public record TypeEvent(
        char keystroke,
        Instant instant
) {

    public static TypeEvent of(char k){
        return new TypeEvent(k, Instant.now());
    }
}
