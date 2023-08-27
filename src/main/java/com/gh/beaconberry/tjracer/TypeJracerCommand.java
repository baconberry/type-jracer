package com.gh.beaconberry.tjracer;

import com.gh.beaconberry.tjracer.controller.TypingController;
import com.gh.beaconberry.tjracer.domain.TypingSession;
import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.time.Duration;

@Command(name = "type-jracer", description = "...",
        mixinStandardHelpOptions = true)
@RequiredArgsConstructor
@Singleton
public class TypeJracerCommand implements Runnable {

    private final TypingController typingController;

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(TypeJracerCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
        start();
    }

    @SneakyThrows
    private void start() {
        System.out.println("Start typing in");
        Thread.sleep(Duration.ofMillis(500));
        System.out.println("3");
        Thread.sleep(Duration.ofSeconds(1));
        System.out.println("2");
        Thread.sleep(Duration.ofSeconds(1));
        System.out.println("1");
        Thread.sleep(Duration.ofSeconds(1));
        System.out.println("GO!");
        readKeyboard();
    }

    private void readKeyboard() throws IOException {
        var typingSession = typingController.startSession();
        char k, p=0;
        byte[] buffer = new byte[1];

        //FIXME: read is reading until enter is hit, use some TUI
        while (System.in.read(buffer) != -1) {
            k = (char) buffer[0];
            typingController.addKeyToSession(k, typingSession);
            if (k == '\n' && p == k) {
                break;
            }
            p = k;
        }
        printSession(typingSession);
    }

    private void printSession(TypingSession session) {
        System.out.println("------");
        System.out.println("TYPING SESSION");
        System.out.println("------");
        typingController.replaySession(System.out, session);
    }
}
