package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Chassis.InputStates;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TeleopStateControl extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Chassis m_drive;

  XboxController js1 = new XboxController(Constants.JoystickConstants.kDriverControllerPort);

  public TeleopStateControl(Chassis subsystem) {
    m_drive = subsystem;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(js1.getAButton()) m_drive.setStates(InputStates.A);
    if(js1.getBButton()) m_drive.setStates(InputStates.B);
    if(js1.getXButton()) m_drive.setStates(InputStates.X);
    if(js1.getYButton()) m_drive.setStates(InputStates.Y);
    if(Math.abs(js1.getLeftY())>=0.2 || Math.abs(js1.getRightX())>=0.2) {
      m_drive.setStates(InputStates.idle);
      m_drive.arcadeDrive(js1.getLeftY(), js1.getRightX());
    }

    if(js1.getLeftBumper()) m_drive.setIntakeMotorDoingSomething(Constants.Chassis.in);
    if(js1.getRightBumper()) m_drive.shootByState();
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
