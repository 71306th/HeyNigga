package frc.robot;

import frc.robot.autos.Forward;
import frc.robot.autos.IntakeSettings;
import frc.robot.autos.IntakeTime;
import frc.robot.commands.Moveforward;
import frc.robot.commands.Turn;
import frc.robot.commands.TeleopStateControl;
import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class RobotContainer {
  private final Chassis m_drive = new Chassis();

  private final TeleopStateControl teleop = new TeleopStateControl(m_drive);

  public RobotContainer() {
    configureBindings();

    m_drive.setDefaultCommand(teleop);
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {

    return new SequentialCommandGroup(
      new Forward(m_drive, 1),
      new Turn(m_drive, 90),
      new Forward(m_drive, 2),
      new Turn(m_drive, -90),
      new Forward(m_drive, 1),
      new IntakeSettings(m_drive, "setLow"),
      new IntakeTime(m_drive, "getFloor"),
      new IntakeSettings(m_drive, "setIdle"),
      new Turn(m_drive, 180),
      new Forward(m_drive, 2),
      new IntakeSettings(m_drive, "setMid"),
      new IntakeTime(m_drive, "shootMid"),
      new IntakeSettings(m_drive, "setIdle"),
      new Turn(m_drive, 180)
    );

    // return new SequentialCommandGroup(
    //   new Moveforward(m_drive),
    //   new Turn(m_drive, 90),
    //   new Moveforward(m_drive),
    //   new Moveforward(m_drive),
    //   new Turn(m_drive, -90),
    //   new Moveforward(m_drive)
      
    // );
  }
}
