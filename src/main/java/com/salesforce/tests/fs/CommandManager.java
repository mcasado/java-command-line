package com.salesforce.tests.fs;

import java.util.*;

public class CommandManager {
    private static CommandManager instance = null;
    private CommandsStack<List<Command>> commandStackNormal;
    private CommandsStack<List<Command>> commandStackReverse;

    public static Map<String, Command> commands;
    static {
        commands = new HashMap<>();
        commands.put("quit", new QuitCommand());
        commands.put("pwd", new PwdCommand());
        commands.put("ls", new LsCommand());
        commands.put("mkdir", new MkdirCommand());
        commands.put("cd", new CdCommand());
        commands.put("touch", new TouchCommand());
    }

    private List<String> commandHistory;

    static CommandManager getInstance() {
        if (instance != null)
            return instance;
        return new CommandManager();
    }

    private CommandManager() {
        commandStackNormal = new CommandsStack<>();
        commandStackReverse = new CommandsStack<>();
        commandHistory = new ArrayList<>();
    }

    void execute(List<Command> commandList) {
        commandList.forEach(Command::execute);
        commandStackNormal.push(commandList);
        commandList.forEach(a -> commandHistory.add(a.getName()));
    }

    void execute(Command command) {
        command.execute();
        List<Command> commands = new ArrayList<Command>() {{
            add(command);
        }};

        commandStackNormal.push(commands);
        commandHistory.add(command.getName());
    }

    void undo() {
        Optional<List<Command>> optionalActions = commandStackNormal.pop();
        optionalActions.ifPresent(aList -> {
            aList.forEach(Command::undo);
            commandStackReverse.push(aList);
            aList.forEach(a -> commandHistory.add(a.getName() + " - undo"));
        });
    }

    void redo() {
        Optional<List<Command>> optionalActions = commandStackReverse.pop();
        optionalActions.ifPresent(aList -> {
            aList.forEach(Command::execute);
            commandStackNormal.push(aList);
            aList.forEach(a -> commandHistory.add(a.getName() + " - redo"));
        });
    }

    void clearNormal() {
        commandStackNormal.clear();
    }

    void clearReverse() {
        commandStackReverse.clear();
    }

    List<String> getActionHistory() {
        return commandHistory;
    }

    public Command getCommand(String command) {
        return commands.get(command);
    }

    public static void main(String[] args) {
        CommandManager manager = CommandManager.getInstance();
        List<Command> actionList = new ArrayList<>();
        actionList.add(new QuitCommand());
        System.out.println("===ACTIONS===");
        manager.execute(actionList);

        manager.undo();
        manager.redo();

        manager.clearNormal();
        manager.undo();
        manager.redo();

        System.out.println("===HISTORY===");
        System.out.println(manager.getActionHistory().toString());
    }


}