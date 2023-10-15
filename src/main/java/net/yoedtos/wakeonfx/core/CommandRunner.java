package net.yoedtos.wakeonfx.core;

import net.yoedtos.wakeonfx.exceptions.CoreException;

import java.io.IOException;
import java.util.List;

public class CommandRunner {

    public int execute(List<String> command) throws CoreException {
        try {
            return new ProcessBuilder(command).start().waitFor();
        } catch (InterruptedException | IOException e) {
            throw new CoreException(e.getMessage());
        }
    }
}
