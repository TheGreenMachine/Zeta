package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

public class Birdbeak extends Subsystem {
    private static final String NAME = "birdbeak";

    private Solenoid beak;
    private Solenoid hatchPuncher;
    private Solenoid intakeArm;

    private IMotorController hatchIntake;

    private double intakePow;

    private boolean beakOpen;
    private boolean armDown;
    private boolean puncherOut;
    private boolean outputsChanged = false;

    public Birdbeak() {
        super(NAME);
        RobotFactory factory = Robot.FACTORY;

        this.beak = factory.getSolenoid(NAME, "beak");
        this.hatchPuncher = factory.getSolenoid(NAME, "puncher");
        this.intakeArm = factory.getSolenoid(NAME, "arm");
        this.hatchIntake = factory.getMotor(NAME, "hatchIntake");

        hatchIntake.setNeutralMode(NeutralMode.Brake);
    }

    public void setBeak(boolean open) {
        beakOpen = open;
        outputsChanged = true;
    }

    public void setPuncher(boolean out) {
        puncherOut = out;
        outputsChanged = true;
    }

    public void setArm(boolean down) {
        armDown = down;
        outputsChanged = true;
    }

    public void setIntake(double power) {
        intakePow = power;
        outputsChanged = true;
    }

    public boolean getBeakState() {
        return beak.get();
    }

    public boolean getPuncherState() {
        return hatchPuncher.get();
    }

    public boolean getArmState() {
        return intakeArm.get();
    }

    public double getIntakePow() {
        return intakePow;
    }

    @Override
    protected void initDefaultCommand() { }

    @Override
    public void periodic() {
        if (outputsChanged) {
            beak.set(beakOpen);
            hatchPuncher.set(puncherOut);
            intakeArm.set(armDown);
            hatchIntake.set(ControlMode.PercentOutput, intakePow);

            outputsChanged = false;
        }
    }
}
