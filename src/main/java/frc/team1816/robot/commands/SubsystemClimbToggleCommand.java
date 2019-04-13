package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CameraMount;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.Climber;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SubsystemClimbToggleCommand extends Command {
    private Climber climber;
    private CameraMount shifter;
    private CargoShooter shooter;

    public SubsystemClimbToggleCommand() {
        super("subsystemclimbtogglecommand");
        climber = Components.getInstance().climber;
        shifter = Components.getInstance().shifter;
        shooter = Components.getInstance().shooter;
        requires(climber);
        requires(shifter);
        requires(shooter);
    }

    @Override
    protected void initialize() {
        System.out.println("SUBSYSTEM climber toggle + camera retraction command");
    }

    @Override
    protected void execute() {
        climber.toggleClimberPiston();
        shifter.setCameraPistonState(Value.kReverse); // TODO: match electrical state
        shooter.setArmPosition(ArmPosition.ROCKET);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
