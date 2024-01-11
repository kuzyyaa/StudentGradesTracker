package org.example.type;

import org.example.Command;
import org.example.MenuFunction;

public class InfoStudentFunction implements MenuFunction {
    @Override
    public void apply() {
        System.out.println("InfoStudentFunction");
    }

    @Override
    public Command supportedAsType() {
        return Command.INFO_STUDENT;
    }
}
