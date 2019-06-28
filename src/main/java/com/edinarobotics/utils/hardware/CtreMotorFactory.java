package com.edinarobotics.utils.hardware;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.team1816.robot.Robot;

/**
 * A class to create TalonSRX, VictorSPX, and GhostTalonSRX objects.
 * Based on FRC Team 254 The Cheesy Poof's 2018 TalonSRXFactory.
 */
public class CtreMotorFactory {
    private final static int kTimeoutMs = 100;

    public static class Configuration {
        public NeutralMode NEUTRAL_MODE = NeutralMode.Coast;
        // This is factory default.
        public double NEUTRAL_DEADBAND = 0.04;

        public boolean ENABLE_CURRENT_LIMIT = false;
        public boolean ENABLE_SOFT_LIMIT = false;
        public boolean ENABLE_LIMIT_SWITCH = false;
        public int FORWARD_SOFT_LIMIT = 0;
        public int REVERSE_SOFT_LIMIT = 0;

        public boolean INVERTED = false;
        public boolean SENSOR_PHASE = false;

        public int CONTROL_FRAME_PERIOD_MS = 5;
        public int MOTION_CONTROL_FRAME_PERIOD_MS = 100;
        public int GENERAL_STATUS_FRAME_RATE_MS = 5;
        public int FEEDBACK_STATUS_FRAME_RATE_MS = 100;
        public int QUAD_ENCODER_STATUS_FRAME_RATE_MS = 100;
        public int ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 100;
        public int PULSE_WIDTH_STATUS_FRAME_RATE_MS = 100;

        public VelocityMeasPeriod VELOCITY_MEASUREMENT_PERIOD = VelocityMeasPeriod.Period_100Ms;
        public int VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW = 64;

        public double OPEN_LOOP_RAMP_RATE = 0.0;
        public double CLOSED_LOOP_RAMP_RATE = 0.0;
    }

    private static final Configuration kDefaultConfiguration = new Configuration();
    private static final Configuration kSlaveConfiguration = new Configuration();

    static {
        // This control frame value seems to need to be something reasonable to avoid the Talon's
        // LEDs behaving erratically.  Potentially try to increase as much as possible.
        kSlaveConfiguration.CONTROL_FRAME_PERIOD_MS = 100;
        kSlaveConfiguration.MOTION_CONTROL_FRAME_PERIOD_MS = 1000;
        kSlaveConfiguration.GENERAL_STATUS_FRAME_RATE_MS = 1000;
        kSlaveConfiguration.FEEDBACK_STATUS_FRAME_RATE_MS = 1000;
        kSlaveConfiguration.QUAD_ENCODER_STATUS_FRAME_RATE_MS = 1000;
        kSlaveConfiguration.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 1000;
        kSlaveConfiguration.PULSE_WIDTH_STATUS_FRAME_RATE_MS = 1000;
    }

    // Create a CANTalon with the default (out of the box) configuration.
    public static IMotorControllerEnhanced createDefaultTalon(int id) {
        return createTalon(id, kDefaultConfiguration);
    }

    public static IMotorControllerEnhanced createPermanentSlaveTalon(int id, IMotorController master) {
        final IMotorControllerEnhanced talon = createTalon(id, kSlaveConfiguration);
        System.out.println("Slaving talon on " + id + " to talon on " + master.getDeviceID());
        talon.follow(master);
        return talon;
    }

    private static IMotorControllerEnhanced createTalon(int id, Configuration config) {
        TalonSRX talon = new TalonSRX(id);
        configureMotorController(talon, config);

        talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen, kTimeoutMs);
        talon.enableCurrentLimit(config.ENABLE_CURRENT_LIMIT);

        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General,
                config.GENERAL_STATUS_FRAME_RATE_MS, kTimeoutMs);
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0,
                config.FEEDBACK_STATUS_FRAME_RATE_MS, kTimeoutMs);

        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_3_Quadrature,
                config.QUAD_ENCODER_STATUS_FRAME_RATE_MS, kTimeoutMs);
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_4_AinTempVbat,
                config.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS, kTimeoutMs);
        talon.setStatusFramePeriod(StatusFrameEnhanced.Status_8_PulseWidth,
                config.PULSE_WIDTH_STATUS_FRAME_RATE_MS, kTimeoutMs);
        talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);

        return talon;
    }

    public static IMotorControllerEnhanced createGhostTalon() {
        return new GhostTalonSRX();
    }

    public static IMotorController createDefaultVictor(int id) {
        return createVictor(id, kDefaultConfiguration);
    }

    public static IMotorController createPermanentSlaveVictor(int id, IMotorController master) {
        final IMotorController victor = createVictor(id, kSlaveConfiguration);
        System.out.println("Slaving victor on " + id + " to talon on " + master.getDeviceID());
        victor.follow(master);
        return victor;
    }

    public static IMotorController createVictor(int id, Configuration config) {
        VictorSPX victor = new VictorSPX(id);
        configureMotorController(victor, config);

        victor.configReverseLimitSwitchSource(RemoteLimitSwitchSource.Deactivated,
                LimitSwitchNormal.NormallyOpen, kTimeoutMs);

        victor.setStatusFramePeriod(StatusFrame.Status_1_General,
                config.GENERAL_STATUS_FRAME_RATE_MS, kTimeoutMs);
        victor.setStatusFramePeriod(StatusFrame.Status_2_Feedback0,
                config.FEEDBACK_STATUS_FRAME_RATE_MS, kTimeoutMs);
        victor.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat,
                config.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS, kTimeoutMs);

        return victor;
    }

    private static void configureMotorController(BaseMotorController motor, Configuration config) {
        motor.set(ControlMode.PercentOutput, 0.0);

        motor.changeMotionControlFramePeriod(config.MOTION_CONTROL_FRAME_PERIOD_MS);
        motor.clearMotionProfileHasUnderrun(kTimeoutMs);
        motor.clearMotionProfileTrajectories();

        motor.clearStickyFaults(kTimeoutMs);

        motor.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
                LimitSwitchNormal.NormallyOpen, kTimeoutMs);
        motor.overrideLimitSwitchesEnable(config.ENABLE_LIMIT_SWITCH);

        // Turn off re-zeroing by default.
        motor.configSetParameter(
                ParamEnum.eClearPositionOnLimitF, 0, 0, 0, kTimeoutMs);
        motor.configSetParameter(
                ParamEnum.eClearPositionOnLimitR, 0, 0, 0, kTimeoutMs);

        motor.configNominalOutputForward(0, kTimeoutMs);
        motor.configNominalOutputReverse(0, kTimeoutMs);
        motor.configNeutralDeadband(config.NEUTRAL_DEADBAND, kTimeoutMs);

        motor.configPeakOutputForward(1.0, kTimeoutMs);
        motor.configPeakOutputReverse(-1.0, kTimeoutMs);

        motor.setNeutralMode(config.NEUTRAL_MODE);

        motor.configForwardSoftLimitThreshold(config.FORWARD_SOFT_LIMIT, kTimeoutMs);
        motor.configForwardSoftLimitEnable(config.ENABLE_SOFT_LIMIT, kTimeoutMs);

        motor.configReverseSoftLimitThreshold(config.REVERSE_SOFT_LIMIT, kTimeoutMs);
        motor.configReverseSoftLimitEnable(config.ENABLE_SOFT_LIMIT, kTimeoutMs);
        motor.overrideSoftLimitsEnable(config.ENABLE_SOFT_LIMIT);

        motor.setInverted(config.INVERTED);
        if (Robot.factory.getConstant("sensorPhase") == 1){
                motor.setSensorPhase(true);
        } else {
                motor.setSensorPhase(config.SENSOR_PHASE);
        }
        motor.selectProfileSlot(0, 0);

        ErrorCode code = motor.configVelocityMeasurementPeriod(config.VELOCITY_MEASUREMENT_PERIOD, kTimeoutMs);
        if (code != ErrorCode.OK) {
            System.out.println("Error setting velocity measurement period: " + code.toString());
        }
        code = motor.configVelocityMeasurementWindow(config.VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW,
                kTimeoutMs);
        if (code != ErrorCode.OK) {
            System.out.println("Error setting velocity measurement window: " + code.toString());
        }

        motor.configOpenloopRamp(config.OPEN_LOOP_RAMP_RATE, kTimeoutMs);
        motor.configClosedloopRamp(config.CLOSED_LOOP_RAMP_RATE, kTimeoutMs);

        motor.configVoltageCompSaturation(0.0, kTimeoutMs);
        motor.configVoltageMeasurementFilter(32, kTimeoutMs);
        motor.enableVoltageCompensation(false);

        motor.setControlFramePeriod(ControlFrame.Control_3_General, config.CONTROL_FRAME_PERIOD_MS);
    }
}
