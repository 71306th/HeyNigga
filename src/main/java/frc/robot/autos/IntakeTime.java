// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Chassis;

public class IntakeTime extends CommandBase {
  /** Creates a new IntakeTime. */

  private Chassis drive;
  private String todo;

  //Intake roller related
  private double time;
  private double timeCurrent;

  public IntakeTime(Chassis drive, String todo) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = drive;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    time = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timeCurrent = Timer.getFPGATimestamp();
    switch(todo){
      case "getFloor":
        drive.setIntakeMotorDoingSomething(Constants.Chassis.in);
        break;
      case "shootLow":
        drive.setIntakeMotorDoingSomething(Constants.Chassis.shootLow);
        break;
      case "shootMid":
        drive.setIntakeMotorDoingSomething(Constants.Chassis.shootMid);
        break;
      case "shootHigh":
        drive.setIntakeMotorDoingSomething(Constants.Chassis.shootHigh);
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(timeCurrent-time>0.75) return true;
    else return false;
  }
}
