package com.salesforce.tests.fs;

import java.io.IOException;
import java.util.concurrent.Executors;

public class PwdCommand extends AbstractCommand  {

    private String name = "pwd";

    public PwdCommand() {
    }

    @Override
    public void execute() {

        ProcessBuilder builder = new ProcessBuilder();
        builder.command("sh", "-c", "pwd");

        Process process;
        try {
            process = builder.start();
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            assert exitCode == 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void undo() {
        System.out.println("no-op");
    }

    @Override
    public String getName() {
        return this.name;
    }
}
