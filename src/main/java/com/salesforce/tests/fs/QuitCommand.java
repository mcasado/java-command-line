package com.salesforce.tests.fs;

public class QuitCommand extends AbstractCommand {

    private String name = "quit";

    public QuitCommand() {
    }

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public void undo() {
        System.out.println("Undo Action 1");
    }

    @Override
    public String getName() {
        return this.name;
    }
}
