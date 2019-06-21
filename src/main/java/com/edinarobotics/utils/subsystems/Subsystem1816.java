package com.edinarobotics.utils.subsystems;

import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.common.Updatable;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This abstract class is used to define a Subsystem that provides an
 * {@link #update()} method.
 */
public abstract class Subsystem1816 extends Subsystem implements Updatable, Checkable {
    
    /**
     * Constructs a new Subsystem1816 with no given name.
     */
    public Subsystem1816(){
        super();
    }
    
    /**
     * Constructs a new Subsystem1816 with the given {@code name}.
     * @param name The name of the new subsystem.
     */
    public Subsystem1816(String name){
        super(name);
    }
    
    /**
     * Sets the default command of the subsystem to be a MaintainStateCommand.
     */
    protected void initDefaultCommand() {
        //setDefaultCommand(new MaintainStateCommand(this));
    }
    
    /**
     * Updates all subcomponents of this subsystem. Calling this method
     * ensures that the watchdog timers for the subcomponents will not
     * time out.
     */
    @Override
    public abstract void update();

    @Override
    public abstract boolean check();
}
