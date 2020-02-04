package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CameraMount;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.Climber;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SubsystemClimbToggleCommand extends CommandBase {
    private Climber climber;
    private CameraMount shifter;
    private CargoShooter shooter;

    public SubsystemClimbToggleCommand() {
        climber = Components.getInstance().climber;
        shifter = Components.getInstance().shifter;
        shooter = Components.getInstance().shooter;
        addRequirements(climber);
        addRequirements(shifter);
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        System.out.println("SUBSYSTEM climber toggle + camera retraction command");
    }

    @Override
    public void execute() {
        climber.toggleClimberPiston();
        shifter.setCameraPistonState(Value.kReverse); // TODO: match electrical state
        shooter.setArmPosition(ArmPosition.ROCKET);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
