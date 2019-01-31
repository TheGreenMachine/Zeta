package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

public class Birdbeak extends Subsystem {
    private final static String SUBSYSTEM = "birdbeak";

    private Solenoid beak;
    private Solenoid hatchPuncher;
    private Solenoid intakeArm;

    private IMotorController hatchIntake;

    private double intakePow;

    private boolean beakOpen;
    private boolean armDown;
    private boolean puncherOut;
    private boolean outputsChanged = false;

    public Birdbeak(int pcmId, int beakId, int puncherId, int armId) {
        super(SUBSYSTEM);
        RobotFactory factory = Robot.FACTORY;

        this.beak = new Solenoid(pcmId, beakId);
        this.hatchPuncher = new Solenoid(pcmId, puncherId);
        this.intakeArm = new Solenoid(pcmId, armId);
        this.hatchIntake = factory.getMotor(SUBSYSTEM, "hatchIntake");

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
        if(outputsChanged) {
            beak.set(beakOpen);
            hatchPuncher.set(puncherOut);
            intakeArm.set(armDown);
            hatchIntake.set(ControlMode.PercentOutput, intakePow);

            outputsChanged = false;
        }
    }
}
