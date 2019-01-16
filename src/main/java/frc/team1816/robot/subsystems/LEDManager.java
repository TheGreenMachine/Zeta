package frc.team1816.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Components;

@RunTest
public class LEDManager extends Subsystem implements Checkable {
    private CANifier canifier;
    private boolean outputsChanged = true;
    private int ledR, ledG, ledB;

    public LEDManager(int canifierId) {
        super("LEDManager");
        this.canifier = new CANifier(canifierId);
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
        setLedColor(status.getR(), status.getG(), status.getB());
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

        int r, g, b;

        RobotStatus(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int getR() { return r; }
        public int getG() { return g; }
        public int getB() { return b; }
    }
}
