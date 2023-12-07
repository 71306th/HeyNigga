// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.ChenryLib.MathUtility;
import frc.ChenryLib.PID;
import frc.robot.subsystems.Chassis;

public class Forward extends InstantCommand {
  /** Creates a new Forward1m. */

  private Chassis drive;
  private PID forward1mPID = new PID(1.1, 0.25, 0.01, 0, 0.3);
  private PID forward2mPID = new PID(0.75, 0.25, 0.01, 0, 0.3);
  private double length;
  private double goalPos;
  private double currentPos;

  public Forward(Chassis drive, double length) {
    // Use addRequirements() here to declare subsystem dependencies.\
    this.drive = drive;
    addRequirements(drive);
    this.length = length;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setEncoderToZero();
    currentPos = drive.getDistance();
    goalPos = currentPos + length;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentPos = drive.getDistance();
    if(length==1)drive.arcadeDrive(MathUtility.clamp(forward1mPID.calculate(goalPos-currentPos), -1, 1), 0);
    else if(length==2)drive.arcadeDrive(MathUtility.clamp(forward2mPID.calculate(goalPos-currentPos), -1, 1), 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(goalPos-currentPos)<0.1) return true;
    else return false;
  }
}
