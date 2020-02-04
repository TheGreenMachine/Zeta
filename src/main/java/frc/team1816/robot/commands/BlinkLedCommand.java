package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.LedManager;
import frc.team1816.robot.subsystems.LedManager.RobotStatus;

import java.util.Arrays;

public class BlinkLedCommand extends CommandBase {
    private LedManager leds;

    private double period;
    private double prevTime;
    private int[] color;
    private double initializedTime;

    public BlinkLedCommand(double blinkRateS) {
        leds = Components.getInstance().ledManager;
        this.period = blinkRateS;

        runsWhenDisabled();
        addRequirements(leds);
    }

    @Override
    public void initialize() {
        prevTime = timeSinceInitialized();
        color = leds.getLedRgbBlink();
        initializedTime = Timer.getFPGATimestamp();
    }

    private double timeSinceInitialized(){
        return Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        int[] setColor = leds.getLedRgbBlink();
        if (!Arrays.equals(setColor, color)) {
            color = setColor;
        }

        if (leds.getBlinkMode()) {
            if (timeSinceInitialized() - prevTime > period) {
                prevTime = timeSinceInitialized();
                leds.setLedColor(color[0], color[1], color[2]);
            } else if (timeSinceInitialized() - prevTime > period / 2) {
                leds.setLedColor(0, 0, 0);
            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isFinished) {
        leds.indicateStatus(RobotStatus.OFF);
    }
}
