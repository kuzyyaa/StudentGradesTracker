package org.example.type;

import org.example.Command;
import org.example.MenuFunction;

public class RemoveStudentFunction implements MenuFunction {
    @Override
    public void apply() {
        System.out.println("RemoveStudentFunction");
    }

    @Override
    public Command supportedAsType() {
        return Command.REMOVE_STUDENT;
    }
}
