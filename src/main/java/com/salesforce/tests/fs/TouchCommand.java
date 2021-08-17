package com.salesforce.tests.fs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class TouchCommand extends AbstractCommand  {

    private String name = "pwd";

    @Override
    public void execute() {
        ProcessBuilder builder = new ProcessBuilder();
        if (getOptions() != null) {
            List<String> optionsList = new ArrayList<>(Arrays.asList(getOptions()));
            List<String> baseCommand = new ArrayList<>();
            baseCommand.add("sh");
            baseCommand.add("-c");
            String lsCommand = "touch ".concat(String.join(" ", optionsList));
            baseCommand.add(lsCommand);
            //System.out.println(baseCommand.toString());
            builder.command(baseCommand);
        } else {
            throw new RuntimeException("Invalid touch command. Missing path.");
        }

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
