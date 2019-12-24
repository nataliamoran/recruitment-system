package edu.csc207.recruitment.tools;

import edu.csc207.recruitment.tools.InputHandler;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

public class TestTools {

    public static void simulateInput(List<String> lines) {
        String input = String.join(System.lineSeparator(), lines);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        InputHandler.getInstance().setScanner(scanner);
    }
}
