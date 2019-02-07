package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Timer;
import frc.team1816.robot.Robot;

@RunTest
public class Climber extends Subsystem implements Checkable {
    public static final String NAME = "climber";

    private IMotorController climbMaster;
    private IMotorController climbSlave;

    private DoubleSolenoid habPiston;
    private DoubleSolenoid.Value habPistonState;

    private double motorPower;

    private boolean outputsChanged = false;

    public Climber() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        this.climbMaster = factory.getMotor(NAME, "climbMaster");
        this.climbSlave = factory.getMotor(NAME, "climbSlave", "climbMaster");
        // this.habPiston = factory.getDoubleSolenoid(NAME, "habPiston"); // TODO: wire

        this.climbSlave.setInverted(true);
    }

    public void setClimberPower(double motorPow) {
        this.motorPower = motorPow;
        outputsChanged = true;
        periodic();
    }

    public void setHabPiston(DoubleSolenoid.Value state) {
        this.habPistonState = state;
        periodic();
    }

    public String getHabPistonState() {
        return habPistonState.toString();
    }

    public double getMotorPower() {
        return motorPower;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            climbMaster.set(ControlMode.PercentOutput, motorPower);
            // habPiston.set(habPistonState); // TODO: wire
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
        setHabPiston(DoubleSolenoid.Value.kForward);
        Timer.delay(3);
        setHabPiston(DoubleSolenoid.Value.kReverse);
        Timer.delay(3);
        setHabPiston(DoubleSolenoid.Value.kOff);
        return true;
    }
}
