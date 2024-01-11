package org.example.type;

import org.example.Command;
import org.example.MenuFunction;

public class AddStudentFunction implements MenuFunction {
    @Override
    public void apply() {
        System.out.println("AddStudentFunction");
    }

    @Override
    public Command supportedAsType() {
        return Command.ADD_STUDENT;
    }
}
