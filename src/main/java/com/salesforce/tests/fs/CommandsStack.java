package com.salesforce.tests.fs;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CommandsStack<T> {

    private List<T> commandsCollection;

    CommandsStack() {
        commandsCollection = new LinkedList<>();
    }

    void push(T command) {
        commandsCollection.add(command);
    }

    Optional<T> pop() {
        if (commandsCollection.size() > 0)
            return Optional.of(commandsCollection.remove(commandsCollection.size() - 1));
        else
            return Optional.empty();
    }

    void clear() {
        commandsCollection.clear();
    }
}
