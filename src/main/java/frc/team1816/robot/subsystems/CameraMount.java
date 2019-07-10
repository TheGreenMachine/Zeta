package frc.team1816.robot.subsystems;

import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

@RunTest
public class CameraMount extends Subsystem implements Checkable {
    public static final String NAME = "cameramount";

    private DoubleSolenoid camRetractor;

    private boolean outputsChanged = false;
    private Value camState = Value.kOff;

    public CameraMount() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        this.camRetractor = factory.getDoubleSolenoid(NAME, "shifter");
    }

    public void setCameraPistonState(Value pistonState) {
        camState = pistonState;
        outputsChanged = true;
    }

    public Value getCameraPistonState() {
        return camState;
    }

    public void toggleCameraShifter() {
        if (camState == Value.kReverse) {
            setCameraPistonState(Value.kOff);
            setCameraPistonState(Value.kForward);
            System.out.println("set camera piston: k_fwd");
        } else {
            setCameraPistonState(Value.kOff);
            setCameraPistonState(Value.kReverse);
            System.out.println("set camera piston: k_rev");
        }
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            camRetractor.set(camState);
            outputsChanged = false;
        }
    }

    public void initDefaultCommand() {
    }

    @Override
    public boolean check() throws CheckFailException {
        return true;
    }
}
