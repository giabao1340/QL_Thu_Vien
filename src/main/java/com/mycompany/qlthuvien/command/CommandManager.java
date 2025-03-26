/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.command;

import java.util.Stack;

/**
 *
 * @author luong
 */
public class CommandManager {
    private static CommandManager instance; 
    private Stack<Command> commandStack = new Stack<>();

    private CommandManager() {}

    public static CommandManager getInstance() {
        if (instance == null) instance = new CommandManager();
        return instance;
    }

    public void executeCommand(Command command) {
        command.execute();
        commandStack.push(command);
    }

    public void undoLastCommand() {
        if (!commandStack.isEmpty()) {
            Command command = commandStack.pop();
            command.undo();
        }
    }

    public boolean isEmpty() {
        return commandStack.isEmpty();
    }
}
