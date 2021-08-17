package com.salesforce.tests.fs;

public abstract class AbstractCommand implements Command {

    public String[] options;
    public void setOptions(String[] options) {
      this.options = options;
    }

    public String[] getOptions() {
      return this.options;
    }
}
