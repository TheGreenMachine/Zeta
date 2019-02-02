package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import frc.team1816.robot.subsystems.Drivetrain;

public class DriveXInchesCommand extends Command {

    private NetworkTable table;
    private double x, y = 0;
    private double distance;
    private Gyro gyro;
    private double targetAngle = 0;
    private double error, gyroAngle;
    private double threshold = 1; // 1-3 degrees


    @Override
    protected void initialize() {
        System.out.println("INIT DRIVE X INCHES VISION VIA NETWORK TABLES");

        gyro = new AnalogGyro(1);
        table = NetworkTable.getTable("SmartDashboard");
        //table.addTableListener();
        gyroAngle = gyro.getAngle();
        gyro.reset();


    }
    public void calculateRotateAngle(double targetAngle) {

        error = targetAngle - gyroAngle;
        if (error > threshold) {

        }

    }

        @Override
        protected void execute () {
            x = table.getNumber("visionX", -1);
            y = table.getNumber("visionY", -1);
            distance = table.getNumber("TARGET_DISTANCE", 0);
        }

        @Override
        protected void end () {


        }




    @Override
    protected boolean isFinished() {
        return false;
    }

}