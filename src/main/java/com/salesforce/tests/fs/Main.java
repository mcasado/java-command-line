package com.salesforce.tests.fs;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The entry point for the Test program
 */
public class Main {

    public static void main(String[] args) {

        CommandManager commandManager = CommandManager.getInstance();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String commandText = scanner.nextLine().trim();
            String[] commandAndOptions = commandText.split(" ");
            String[] options = null;
            if (commandAndOptions.length > 1) {
                //System.out.println(commandAndOptions[1]);
                options = Arrays.copyOfRange(commandAndOptions, 1, commandAndOptions.length);
                //System.out.println(options[0]);
            }
            try {
                Command command = commandManager.getCommand(commandAndOptions[0].trim());
                if (command != null) {
                    command.setOptions(options);
                    commandManager.execute(command);
                } else {
                    System.out.println("Invalid command was provided: " + commandText);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

        //scanner.close();
    }
}
