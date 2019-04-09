package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
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
public class CameraShifter extends Subsystem implements Checkable {
    public static final String NAME = "camerashifter";

    private DoubleSolenoid shifter;

    private boolean isRetracted = false;
    private boolean outputsChanged = false;
    private Value shifterState = Value.kOff;

    public CameraShifter() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        this.shifter = factory.getDoubleSolenoid(NAME, "shifter");
    }

    public void setShifterPistonState(boolean isRetracted) {
        this.isRetracted = isRetracted;
        outputsChanged = true;
    }

    public void toggleCameraShifter() {
        if (isRetracted) {
            shifterState = Value.kForward;
        } else {
            shifterState = Value.kReverse;
        }
    }

    @Override
    public void periodic() {
        if (outputsChanged){
            shifter.set(shifterState);
        }
    }

    public void initDefaultCommand() {
    }

    @Override
    public boolean check() throws CheckFailException {
        return true;
    }
}
