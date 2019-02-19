/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.LedManager;
import frc.team1816.robot.subsystems.LedManager.RobotStatus;

import java.util.Arrays;

public class BlinkLedCommand extends Command {
    private LedManager leds;

    private double period;
    private double prevTime;
    private int[] color;

    public BlinkLedCommand(double blinkRateS) {
        leds = Components.getInstance().ledManager;
        this.period = blinkRateS;

        setRunWhenDisabled(true);

        requires(leds);
    }

    @Override
    protected void initialize() {
        System.out.println("Starting LED Blinker Command");
        prevTime = timeSinceInitialized();
        color = leds.getLedRgbBlink();
    }

    @Override
    protected void execute() {
        int[] setColor = leds.getLedRgbBlink();
        if (!Arrays.equals(setColor, color)) {
            color = setColor;
        }

        if (timeSinceInitialized() - prevTime > period && leds.getBlinkMode()) {
            prevTime = timeSinceInitialized();
            leds.setLedColor(color[0], color[1], color[2]);
        } else if (timeSinceInitialized() - prevTime > period / 2) {
            leds.setLedColor(0, 0, 0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        leds.indicateStatus(RobotStatus.OFF);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
