package frc.team1816.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

@RunTest
public class LedManager extends Subsystem implements Checkable {
    public static final String NAME = "ledmanager";

    private CANifier canifier;
    private boolean outputsChanged = true;

    private int ledR;
    private int ledG;
    private int ledB;

    public LedManager() {
        super(NAME);
        this.canifier = Robot.factory.getCanifier(NAME);
        this.ledR = 0;
        this.ledG = 0;
        this.ledB = 0;
    }

    public void setLedColor(int r, int g, int b) {
        this.ledR = r;
        this.ledG = g;
        this.ledB = b;
        outputsChanged = true;
    }

    public void indicateStatus(RobotStatus status) {
        System.out.println("Robot Status: " + status.name());
        setLedColor(status.getRed(), status.getGreen(), status.getBlue());
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            canifier.setLEDOutput(ledB, CANifier.LEDChannel.LEDChannelA);
            canifier.setLEDOutput(ledG, CANifier.LEDChannel.LEDChannelB);
            canifier.setLEDOutput(ledR, CANifier.LEDChannel.LEDChannelB);
            outputsChanged = false;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public boolean check() throws CheckFailException {
        setLedColor(255, 0, 0);
        periodic();
        Timer.delay(1); // TODO: Best way to delay?
        setLedColor(0, 255, 0);
        periodic();
        Timer.delay(1);
        setLedColor(0, 0, 255);
        periodic();
        Timer.delay(1);
        setLedColor(0,0,0);
        periodic();
        return true;
    }

    public enum RobotStatus {
        RUNNING(0, 0, 255),
        SUCCESS(0, 255, 0),
        FAILURE(255, 0, 0),
        WARNING(255, 128, 0);

        int red;
        int green;
        int blue;

        RobotStatus(int r, int g, int b) {
            this.red = r;
            this.green = g;
            this.blue = b;
        }

        public int getRed() { return red; }
        public int getGreen() { return green; }
        public int getBlue() { return blue; }
    }
}
