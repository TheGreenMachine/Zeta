package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.edinarobotics.utils.math.Math1816;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Subsystem for the cargo shooter.
 */
public class CargoShooter extends Subsystem {

    private static final String NAME = "CargoShooter";

    private TalonSRX arm;
    private TalonSRX intake;

    private double armPosition;
    private double armVelocity;
    private double intakeVelocity;

    public static final double ARM_POSITION_MIN = 0;
    public static final double ARM_POSITION_MAX = 2048;

    private boolean outputsChanged = false;

    public CargoShooter(int armTalonId, int intakeTalonId) {
        super(NAME);

        this.arm = new TalonSRX(armTalonId);
        this.intake = new TalonSRX(intakeTalonId);
        this.armPosition = 0;
        this.armVelocity = 0.5;
        this.intakeVelocity = 0;
        this.outputsChanged = false;

        arm.set(ControlMode.Position, 0.0);
        intake.set(ControlMode.PercentOutput, 0.0);
        arm.configOpenloopRamp(6.0);
    }

    public void setArmPosition(double armPosition) {
        this.armPosition = Math1816.coerceValue(
                ARM_POSITION_MAX,
                ARM_POSITION_MIN,
                armPosition * ARM_POSITION_MAX
        );
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
            arm.set(ControlMode.Position, armPosition);
            intake.set(ControlMode.PercentOutput, intakeVelocity);
            outputsChanged = false;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty(NAME + "/ArmPosition",
                this::getArmPosition, this::setArmPosition);
        builder.addDoubleProperty(NAME + "/IntakeVelocity",
                this::getIntakeVelocity, this::setIntakeVelocity);
    }
}
