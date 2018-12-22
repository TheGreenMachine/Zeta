package com.edinarobotics.utils.commands;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This {@link Command} maintains the current state of a
 * subsystem. It ensures that the subsystem does not time out and is
 * normally used as the default command of a subsystem.
 */
public class MaintainStateCommand extends Command {
    private Subsystem1816 subsystem;
    
    /**
     * Constructs a new MaintainStateCommand that will maintain
     * the state of the given {@code subsystem}.
     * @param subsystem The subsystem whose state is to be maintained.
     */
    public MaintainStateCommand(Subsystem1816 subsystem){
        super("MaintainState");
        requires(subsystem);
        this.subsystem = subsystem;
        setInterruptible(true);
    }

    protected void initialize() {
        
    }

    /**
     * This method maintains the state of the given subsystem
     * by calling {@link Subsystem1816#update()} each time it is called.
     */
    protected void execute() {
        subsystem.update();
    }

    /**
     * MaintainStateCommand is interruptible, but never completes.
     * This method always returns {@code false} as required by
     * {@link Command#isFinished()}.
     * @return 
     */
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        
    }

    protected void interrupted() {
        
    }
}
