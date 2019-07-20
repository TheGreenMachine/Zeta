package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team1816.robot.Robot;

/**
 * Subsystem for the cargo shooter.
 */
@RunTest
public class CargoShooter extends Subsystem implements Checkable {
    public static final String NAME = "cargoshooter";

    private IMotorControllerEnhanced armTalon;
    private IMotorController intakeMotor;

    private ArmPosition armPosition;

    private double armPower;
    private double intakePower;

    private static final boolean kSensorPhase = true; // these two booleans should always match
    private static final boolean kMotorInverted = true;

    public static final int ARM_POSITION_MIN = Robot.factory.getConstant(NAME, "minPos").intValue();
    public static final int ARM_POSITION_MID = Robot.factory.getConstant(NAME, "midPos").intValue();
    public static final int ARM_POSITION_MAX = Robot.factory.getConstant(NAME, "maxPos").intValue();
    private static final int ALLOWABLE_CLOSED_LOOP_ERROR = 50;

    private static final int kPIDLoopIdx = 0;
    private static final int kTimeoutMs = 30;

    private double kP;
    private double kI;
    private double kD;
    private double kF;

    private boolean outputsChanged;
    private boolean isPercentOutput;

    public CargoShooter() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        this.armTalon = (IMotorControllerEnhanced) factory.getMotor(NAME, "arm");
        this.intakeMotor = factory.getMotor(NAME, "intake");
        this.armPower = 0;
        this.intakePower = 0;
        this.outputsChanged = true;
        this.isPercentOutput = true;

        this.kP = factory.getConstant(NAME, "kP");
        this.kI = factory.getConstant(NAME, "kI");
        this.kD = factory.getConstant(NAME, "kD");
        this.kF = factory.getConstant(NAME, "kF");

        this.intakeMotor.setInverted(false);

        configureArmTalon();

        // Calibrate quadrature encoder with absolute mag encoder
        int absolutePosition = getArmPositionAbsolute();

        /* Set the quadrature (relative) sensor to match absolute */
        this.armTalon.setSelectedSensorPosition(absolutePosition, kPIDLoopIdx, kTimeoutMs);

        armTalon.configOpenloopRamp(0.25, 0);
    }

    private void configureArmTalon() {
        armTalon.setNeutralMode(NeutralMode.Brake);
        armTalon.setInverted(kMotorInverted);
        armTalon.setSensorPhase(kSensorPhase);
        armTalon.enableCurrentLimit(true);
        armTalon.configContinuousCurrentLimit(3, kTimeoutMs);
        armTalon.configPeakCurrentLimit(5, kTimeoutMs);
        armTalon.configPeakCurrentDuration(75, kTimeoutMs);
        armTalon.configSelectedFeedbackSensor(
                FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs);

        /* Config the peak and nominal outputs, 12V means full */
        armTalon.configNominalOutputForward(0, kTimeoutMs);
        armTalon.configNominalOutputReverse(0, kTimeoutMs);
        armTalon.configPeakOutputForward(1, kTimeoutMs);
        armTalon.configPeakOutputReverse(-1, kTimeoutMs);

        this.setPid(kP, kI, kD);

        armTalon.configAllowableClosedloopError(
                kPIDLoopIdx, ALLOWABLE_CLOSED_LOOP_ERROR, kTimeoutMs);

        // Both overrides must be true to enable soft limits
        armTalon.overrideLimitSwitchesEnable(true);
        armTalon.overrideSoftLimitsEnable(true);

        armTalon.configForwardSoftLimitEnable(true, kTimeoutMs);
        armTalon.configReverseSoftLimitEnable(true, kTimeoutMs);
        armTalon.configForwardSoftLimitThreshold(ARM_POSITION_MAX, kTimeoutMs);
        armTalon.configReverseSoftLimitThreshold(ARM_POSITION_MIN, kTimeoutMs);
    }

    public void setPid(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        armTalon.config_kF(kPIDLoopIdx, kF, kTimeoutMs);
        armTalon.config_kP(kPIDLoopIdx, kP, kTimeoutMs);
        armTalon.config_kI(kPIDLoopIdx, kI, kTimeoutMs);
        armTalon.config_kD(kPIDLoopIdx, kD, kTimeoutMs);
    }

    public enum ArmPosition {
        DOWN(ARM_POSITION_MIN),
        ROCKET(ARM_POSITION_MID),
        UP(ARM_POSITION_MAX);

        private double armPos;

        ArmPosition(double pos) {
            this.armPos = pos;
        }

        public double getPos() {
            return armPos;
        }
    }

    public void setArmPosition(ArmPosition pos) {
        this.armPosition = pos;
        outputsChanged = true;
        isPercentOutput = false;
        periodic();
    }

    public ArmPosition getArmPosition() {
        return armPosition;
    }

    public int getArmPositionAbsolute() {
        /* Mask out overflows, keep bottom 12 bits */
        return armTalon.getSensorCollection().getPulseWidthPosition() & 0xFFF;
    }

    public double getArmEncoderPosition() {
        return armTalon.getSelectedSensorPosition(kPIDLoopIdx);
    }

    public boolean isBusy() {
        if (armTalon.getControlMode() == ControlMode.Position) {
            return (armTalon.getClosedLoopError(kPIDLoopIdx) <= ALLOWABLE_CLOSED_LOOP_ERROR);
        }
        return false;
    }

    public void setArmPower(double armPow) {
        isPercentOutput = true;

        System.out.println(
                new StringBuilder("Nominal range\tSet value: ").append(armPow)
                        .append(" Arm Pos Abs: ").append(getArmPositionAbsolute())
                        .append(" Arm Pos Rel: ").append(getArmEncoderPosition())
                        .toString()
        );

        this.armPower = armPow * 0.50;

        outputsChanged = true;
    }

    public double getArmPower() {
        return armPower;
    }

    public void setIntake(double intakePow) {
        this.intakePower = intakePow;
        outputsChanged = true;
    }

    public double getIntakePower() {
        return intakePower;
    }

    public boolean isPercentOutput() {
        return isPercentOutput;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            if (isPercentOutput) {
                armTalon.set(ControlMode.PercentOutput, armPower);
            } else {
                System.out.println("Setting Arm to " + armPosition.getPos());
                armTalon.set(ControlMode.Position, armPosition.getPos());
            }
            intakeMotor.set(ControlMode.PercentOutput, intakePower);
            outputsChanged = false;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

     @Override
     public void initSendable(SendableBuilder builder) {
         builder.addStringProperty("ControlMode", () -> armTalon.getControlMode().toString(), null);
         builder.addDoubleProperty("CurrentPosition", this::getArmEncoderPosition, null);
         builder.addDoubleProperty("ClosedLoop/TargetPosition",
                 () -> (armTalon.getControlMode() == ControlMode.Position ? armTalon.getClosedLoopTarget(kPIDLoopIdx) : 0), null);
         builder.addDoubleProperty("ClosedLoop/Error",
                 () -> (armTalon.getControlMode() == ControlMode.Position ? armTalon.getClosedLoopError(kPIDLoopIdx) : 0), null);
         builder.addDoubleProperty("MotorOutput", armTalon::getMotorOutputPercent, null);
         builder.addBooleanProperty("Busy", this::isBusy, null);
         builder.addDoubleProperty("IntakePower", this::getIntakePower, this::setIntake);
         builder.addDoubleProperty("Absolute Arm Position", this::getArmPositionAbsolute, null);
         SmartDashboard.putNumber("max_thresh", ARM_POSITION_MAX);
         SmartDashboard.putNumber("min_thresh", ARM_POSITION_MIN);
     }

    @Override
    public boolean check() throws CheckFailException {
        System.out.println("Warning: mechanisms will move!");
        Timer.delay(3);

        setIntake(1.0);
        Timer.delay(0.5);
        setIntake(0);

        return true;
    }
}
