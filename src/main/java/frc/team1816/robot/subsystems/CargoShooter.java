package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.edinarobotics.utils.math.Math1816;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for the cargo shooter.
 */
public class CargoShooter extends Subsystem {

    private TalonSRX arm;
    private TalonSRX intake;

    private double armPosition;
    private double armVelocity = 0.5;
    private double intakeVelocity;

    private boolean outputsChanged = false;

    public CargoShooter(int armTalonId, int intakeTalonId) {
        super("CargoShooter");

        this.arm = new TalonSRX(armTalonId);
        this.intake = new TalonSRX(intakeTalonId);
        this.armPosition = 0;
        this.intakeVelocity = 0;
        this.outputsChanged = false;

        arm.set(ControlMode.Position, 0.0);
        intake.set(ControlMode.PercentOutput, 0.0);
        arm.configOpenloopRamp(6.0);
        intake.configOpenloopRamp(3.0);
    }

    public void setArmPosition(double armPosition) {
        this.armPosition = Math1816.clip(armPosition, -1, 1);
        outputsChanged = true;
    }

    public double getArmPosition() {
        return armPosition;
    }

    public double getArmEncoderPosition() {
        return arm.getSensorCollection().getPulseWidthPosition();
    }

    public void setArmVelocity(double armVelocity) {
        this.armVelocity = Math1816.clip(armVelocity, -1, 1);
        outputsChanged = true;
    }

    public double getArmVelocity() {
        return armVelocity;
    }

    public void setIntakeVelocity(double intakeVelocity) {
        this.intakeVelocity = Math1816.clip(intakeVelocity, -1, 1);
        outputsChanged = true;
    }

    public double getIntakeVelocity() {
        return intakeVelocity;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            arm.set(ControlMode.Position, armPosition);
            intake.set(ControlMode.PercentOutput, intakeVelocity);
            outputsChanged = false;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

}
