package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

@RunTest
public class CargoCollector extends Subsystem implements Checkable {
    public static final String NAME = "cargocollector";

    private Solenoid armPiston;
    private IMotorController intake;

    private double intakePow;

    private boolean armDown;
    private boolean outputsChanged = false;

    public CargoCollector() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        this.intake = factory.getMotor(NAME, "intake");
        this.armPiston = factory.getSolenoid(NAME, "arm");
    }

    public void setArm(boolean down) {
        this.armDown = down;
        outputsChanged = true;
        periodic();
    }

    public void setIntake(double intakePower) {
        this.intakePow = intakePower;
        outputsChanged = true;
    }

    public boolean isArmDown() {
        return armDown;
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

    public void initDefaultCommand() {
    }

    @Override
    public boolean check() throws CheckFailException {
        System.out.println("Warning: mechanisms will move!");
        Timer.delay(3);

        setIntake(1.0);
        Timer.delay(0.5);
        setIntake(0.0);

        return true;
    }
}
