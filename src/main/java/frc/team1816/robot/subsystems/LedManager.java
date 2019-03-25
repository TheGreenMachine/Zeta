package frc.team1816.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunTest
public class LedManager extends Subsystem implements Checkable {
    public static final String NAME = "ledmanager";

    private CANifier canifier;

    private boolean blinkMode;
    private boolean outputsChanged = true;

    private int ledR;
    private int ledG;
    private int ledB;

    private int ledBlinkR;
    private int ledBlinkG;
    private int ledBlinkB;

    private Set<RobotStatus> statuses = new HashSet<>();
    private RobotStatus currentStatus;

    public LedManager() {
        super(NAME);
        this.canifier = Robot.factory.getCanifier(NAME);
        this.ledR = 0;
        this.ledG = 0;
        this.ledB = 0;
        statuses.add(RobotStatus.DISABLED);
    }

    public void setLedColor(int r, int g, int b) {
        if (this.ledR != r || this.ledG != g || this.ledB != b) {
            this.ledR = r;
            this.ledG = g;
            this.ledB = b;
            outputsChanged = true;
        }
    }

    public void setLedColorBlink(int r, int g, int b) {
        blinkMode = true;
        this.ledBlinkR = r;
        this.ledBlinkG = g;
        this.ledBlinkB = b;
    }

    public void indicateStatus(RobotStatus status) {
        statuses.add(status);
        updateLed();
    }

    private void updateLed() {
        currentStatus = Collections.max(statuses);
        if (currentStatus.blink) {
            blinkStatus(currentStatus);
        } else {
            blinkMode = false;
            setLedColor(currentStatus.getRed(), currentStatus.getGreen(), currentStatus.getBlue());
        }
    }

    public void clearStatus(RobotStatus status) {
        statuses.remove(status);
        updateLed();
    }

    private void blinkStatus(RobotStatus status) {
        blinkMode = true;
        this.ledBlinkR = status.getRed();
        this.ledBlinkG = status.getGreen();
        this.ledBlinkB = status.getBlue();
    }

    public RobotStatus getCurrentStatus() {
        return currentStatus;
    }

    public int[] getLedRgbBlink() {
        return new int[] {ledBlinkR, ledBlinkG, ledBlinkB};
    }

    public boolean getBlinkMode() {
        return blinkMode;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            canifier.setLEDOutput((ledG / 255.0), CANifier.LEDChannel.LEDChannelA);
            canifier.setLEDOutput((ledR / 255.0), CANifier.LEDChannel.LEDChannelB);
            canifier.setLEDOutput((ledB / 255.0), CANifier.LEDChannel.LEDChannelC);
            outputsChanged = false;
        }
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public boolean check() throws CheckFailException {
        System.out.println("Warning: checking LED systems");
        for (RobotStatus status : RobotStatus.values()) {
            indicateStatus(status);
            periodic();
            Timer.delay(0.4);
            clearStatus(status);
        }
        return true;
    }

    public enum RobotStatus {
        // Sorted by priority, do not change order!
        DISABLED(255, 103, 0), // orange
        ENABLED(0, 255, 0), // green
        ENDGAME(0, 0, 255, true), // blue
        DRIVETRAIN_FLIPPED(223, 255, 0), // yellow-green
        SEEN_TARGET(255, 0, 255, true), // magenta
        ON_TARGET(255, 0, 20, true), // deep magenta
        OFF(0, 0, 0), // off
        ERROR(255, 0, 0, true); // red


        int red;
        int green;
        int blue;
        boolean blink;

        RobotStatus(int r, int g, int b, boolean blink) {
            this.red = r;
            this.green = g;
            this.blue = b;
            this.blink = blink;
        }

        RobotStatus(int r, int g, int b) {
            this(r, g, b, false);
        }

        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }
    }
}
