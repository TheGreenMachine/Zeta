package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1816.robot.paths.TrajectoryGenerator;

public class DriveTrajectoryAuto extends CommandGroup {
  private static final TrajectoryGenerator trajectoryGenerator = TrajectoryGenerator.getInstance();
  
  public DriveTrajectoryAuto(){
      // addSequential(new ResetGyroCommand());
      var trajectory = trajectoryGenerator.getTrajectorySet().centerStartToStairsTunable;
      //System.out.println(trajectoryGenerator.toString());
      //System.out.println(trajectoryGenerator.getTrajectorySet().toString());
      addSequential(new DriveTrajectoryCommand(trajectory, true));
  }
}
