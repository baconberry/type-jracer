package com.gh.beaconberry.tjracer.controller;

import com.gh.beaconberry.tjracer.domain.TypingSession;
import com.gh.beaconberry.tjracer.service.TypingService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.io.PrintStream;
import java.util.Optional;

@RequiredArgsConstructor
@Singleton
public class TypingController {
    private final TypingService typingService;


    public TypingSession startSession() {
        return TypingSession.create();
    }

    public void addKeyToSession(char k, TypingSession typingSession) {
        typingService.storeKeystroke(k, typingSession);
    }

    public String getText(TypingSession session) {
        return typingService.typingSessionToString(session);
    }

    public void replaySession(PrintStream out, TypingSession session) {
        int pos = 0;
        Optional<Character> charOpt;
        var buffer = new StringBuilder();
        do {
            charOpt = typingService.replayNextChar(session, pos);
            pos++;
            charOpt.ifPresent(buffer::append);
            out.println(buffer);
        } while (charOpt.isPresent());
    }

}
