package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import frc.team1816.robot.subsystems.Drivetrain;

public class DriveXInchesCommand extends Command {

private NetworkTable table;
double x, y = 0;



    @Override
    protected void initialize() {
        System.out.println("INIT DRIVE X INCHES VISION VIA NETWORK TABLES");

        table = NetworkTable.getTable("SmartDashboard");

    }



    @Override
    protected void execute() {
        x = table.getNumber("visionX", -1);
        y = table.getNumber("visionY", -1);

    }

    @Override
    protected void end() {



    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
