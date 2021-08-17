package com.salesforce.tests.fs;

public interface Command {

    void execute();

    void undo();

    String getName();

    void setOptions(String[] options);
    String[] getOptions();
}
