package com.gh.beaconberry.tjracer.service;

import com.gh.beaconberry.tjracer.domain.TypeEvent;
import com.gh.beaconberry.tjracer.domain.TypingSession;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class TypingService {

    public void storeKeystroke(char k, TypingSession session) {
        var event = TypeEvent.of(k);
        session.typeEvents().add(event);
    }

    public String typingSessionToString(TypingSession session) {
        var sb = new StringBuilder();
        session.typeEvents().stream()
                .map(TypeEvent::keystroke)
                .forEach(sb::append);
        return sb.toString();
    }

    public Optional<Character> replayNextChar(TypingSession session, int pos) {
        if (pos >= session.typeEvents().size() || pos < 0) {
            return Optional.empty();
        }
        var event = session.typeEvents().get(pos);
        long refTime;
        if (pos == 0) {
            refTime = session.startInstant().toEpochMilli();
        } else {
            var previousEvent = session.typeEvents().get(pos - 1);
            refTime = previousEvent.instant().toEpochMilli();
        }

        try {
            Thread.sleep(event.instant().toEpochMilli() - refTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(event.keystroke());
    }
}
