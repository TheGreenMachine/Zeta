package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.edinarobotics.utils.hardware.RobotFactory;
import com.edinarobotics.utils.math.Math1816;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.team1816.robot.Robot;

/**
 * Subsystem for the cargo shooter.
 */
public class CargoShooter extends Subsystem {
    private static final String NAME = "cargoshooter";

    private IMotorControllerEnhanced arm;
    private IMotorController intake;

    private double armPosition;
    private double armVelocity;
    private double intakeVelocity;

    // TODO: Measure true min and max
    public static final double ARM_POSITION_MIN = 0;
    public static final double ARM_POSITION_MAX = 2048;

    private boolean outputsChanged;

    public CargoShooter() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        this.arm = (IMotorControllerEnhanced) factory.getMotor(NAME, "arm");
        this.intake = factory.getMotor(NAME, "intake");
        this.armPosition = 0;
        this.armVelocity = 0.5;
        this.intakeVelocity = 0;
        this.outputsChanged = false;

        arm.set(ControlMode.Position, 0.0);
        intake.set(ControlMode.PercentOutput, 0.0);
        arm.configOpenloopRamp(6.0, 0);
    }

    public void setArmPosition(double armPosition) {
        this.armPosition = armPosition;
        outputsChanged = true;
    }

    public double getArmPosition() {
        return armPosition;
    }

    public double getArmEncoderPosition() {
        return arm.getSensorCollection().getPulseWidthPosition();
    }

    public void setArmVelocity(double armVelocity) {
        this.armVelocity = armVelocity;
        outputsChanged = true;
    }

    public double getArmVelocity() {
        return armVelocity;
    }

    public void setIntakeVelocity(double intakeVelocity) {
        this.intakeVelocity = intakeVelocity;
        outputsChanged = true;
    }

    public double getIntakeVelocity() {
        return intakeVelocity;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            // armPosition is a number from [0.0, 1.0] representing a
            // proportion of the total rotational range of the arm.
            double armEncoderTicks = Math1816.coerceValue(
                    ARM_POSITION_MAX,
                    ARM_POSITION_MIN,
                    armPosition * ARM_POSITION_MAX
            );
            arm.set(ControlMode.Position, armEncoderTicks);
            intake.set(ControlMode.PercentOutput, intakeVelocity);
            outputsChanged = false;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("ArmPosition",
                this::getArmPosition, this::setArmPosition);
        builder.addDoubleProperty("IntakeVelocity",
                this::getIntakeVelocity, this::setIntakeVelocity);
    }
}
