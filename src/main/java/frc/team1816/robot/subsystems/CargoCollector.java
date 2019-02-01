package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

public class CargoCollector extends Subsystem {
    private static final String NAME = "cargocollector";

    private Solenoid armPiston;
    private IMotorController intake;

    private double intakePow;

    private boolean armDown;
    private static boolean outputsChanged = false;

    public CargoCollector() {
        super(NAME);
        RobotFactory factory = Robot.FACTORY;

        this.intake = factory.getMotor(NAME, "intake");
        this.armPiston = factory.getSolenoid(NAME, "arm");
    }

    public void setArmPiston(boolean down) {
        this.armDown = down;
        outputsChanged = true;
    }

    public void setIntake(double intakePower) {
        this.intakePow = intakePower;
        outputsChanged = true;
    }

    public boolean getArmPistonState() {
        return armDown;
    }

    public double getIntakePow() {
        return this.intakePow;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            this.intake.set(ControlMode.PercentOutput, intakePow);
            this.armPiston.set(armDown);
            outputsChanged = false;
        }
    }

    public void initDefaultCommand() { }
}
