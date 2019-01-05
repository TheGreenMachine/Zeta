package com.edinarobotics.utils.commands;

import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This {@link Command} repeats the supplied {@link Command} or
 * {@link edu.wpi.first.wpilibj.command.CommandGroup} for the specified number
 * of repetitions. If it is interrupted, it will cancel the command that is running.
 */
public class RepeatCommand extends Command {
    private Command commandToRepeat;
    private final int REPETITIONS;
    private int timesStarted;
    private InternalButton button;
    private byte needToStartCommand;
    private static final byte START_COMMAND_THRESHOLD = 2;
    
    /**
     * Constructs a RepeatCommand to repeat the {@code commandToRepeat} for the
     * given {@code repetitions}.
     * @param commandToRepeat The command that is to be repeated.
     * @param repetitions The number of times to repeat the command.
     * @throws IllegalArgumentException when {@code repetitions} is negative.
     */
    public RepeatCommand(Command commandToRepeat, int repetitions) {
        super("Repeat*" + repetitions + ":" + commandToRepeat.getName());
        this.commandToRepeat = commandToRepeat;
        this.REPETITIONS = repetitions;
        timesStarted = 0;
        button = new InternalButton();
        button.whenPressed(this.commandToRepeat);
        this.needToStartCommand = 0;
        
        if(repetitions < 0) {
            throw new IllegalArgumentException("Repetitions cannot be negative.");
        }
    }

    /**
     * Resets the number of times the {@code commandToRepeat} has repeated to 0.
     * This method prepares RepeatCommand for another round of command repetition.
     */
    protected void initialize() {
        timesStarted = 0;
        button.setPressed(false);
        this.commandToRepeat.cancel();
        this.needToStartCommand = 0;
    }

    /**
     * Repeats the {@code commandToRepeat} if it hasn't reached the number of
     * repetitions specified.
     */
    protected void execute() {
        if(timesStarted < REPETITIONS) {
            if(!commandToRepeat.isRunning() && needToStartCommand < START_COMMAND_THRESHOLD) {
                needToStartCommand++;
                button.setPressed(false);
            }
            if(!commandToRepeat.isRunning() && needToStartCommand <= (START_COMMAND_THRESHOLD)){
                button.setPressed(true);
                needToStartCommand++;
            }
            else if(!commandToRepeat.isRunning() && needToStartCommand >= (START_COMMAND_THRESHOLD+1)){
                button.setPressed(false);
                timesStarted++;
                needToStartCommand = 0;
            }
        }
    }

    /**
     * @return Whether {@code commandToRepeat} has stopped and the number of repetitions
     * has completed.
     */
    protected boolean isFinished() {
        return timesStarted >= REPETITIONS && !commandToRepeat.isRunning();
    }

    /**
     * Shuts down RepeatCommand after it has repeated the command the requested
     * number of times.
     */
    protected void end() {
        button.setPressed(false);
        needToStartCommand = 0;
        timesStarted = 0;
    }

    /**
     * Calls the cancel method of {@code commandToRepeat} when RepeatCommand is
     * interrupted.
     */
    protected void interrupted() {
        if(commandToRepeat.isRunning()) {
            commandToRepeat.cancel();
        }
        end();
    }
}
