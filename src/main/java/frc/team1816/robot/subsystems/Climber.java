package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

@RunTest
public class Climber extends Subsystem implements Checkable {
    public static final String NAME = "climber";

    private IMotorControllerEnhanced climbMaster;
    private IMotorControllerEnhanced climbSlave;

    private DoubleSolenoid habPiston;
    private DoubleSolenoid.Value habPistonState = Value.kOff;

    private double motorPower;

    private boolean outputsChanged = false;

    private static final int kTimeoutMs = 100;

    public Climber() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        this.climbMaster = (IMotorControllerEnhanced) factory.getMotor(NAME, "climbMaster");
        this.climbSlave = (IMotorControllerEnhanced) factory.getMotor(NAME, "climbSlave", climbMaster);
        this.habPiston = factory.getDoubleSolenoid(NAME, "habPiston");

        this.climbSlave.setInverted(true);

        this.climbMaster.enableCurrentLimit(true);
        this.climbMaster.configContinuousCurrentLimit(30, kTimeoutMs);
        this.climbMaster.configPeakCurrentLimit(35, kTimeoutMs);
        this.climbMaster.configPeakCurrentDuration(500, kTimeoutMs);
    }

    public void setClimberPower(double motorPow) {
        this.motorPower = motorPow;
        outputsChanged = true;
    }

    public void setHabPiston(Value state) {
        this.habPistonState = state;
    }

    public Value getHabPistonState() {
        return habPistonState;
    }

    public double getMotorPower() {
        return motorPower;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            climbMaster.set(ControlMode.PercentOutput, motorPower);
            habPiston.set(habPistonState);
            outputsChanged = false;
        }
    }

    public void initDefaultCommand() {
    }

    @Override
    public boolean check() throws CheckFailException {
        System.out.println("Warning: mechanisms will move!");
        Timer.delay(3);
        setClimberPower(0.5);
        Timer.delay(3);
        setClimberPower(0);
        Timer.delay(0.5);
        setClimberPower(-0.5);
        Timer.delay(3);
        setClimberPower(0);
        Timer.delay(3);
        setHabPiston(Value.kForward);
        Timer.delay(3);
        setHabPiston(Value.kReverse);
        Timer.delay(3);
        setHabPiston(Value.kOff);
        return true;
    }
}
