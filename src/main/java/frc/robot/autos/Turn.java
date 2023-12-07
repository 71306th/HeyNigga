// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.ChenryLib.MathUtility;
import frc.ChenryLib.PID;
import frc.robot.subsystems.Chassis;

public class Turn extends CommandBase {
  /** Creates a new Forward1m. */

  private Chassis drive;
  private PID turn90PID = new PID(0.012, 0.004, 0.0008, 0, 0.008);
  private PID turn180PID = new PID(0.008, 0.004, 0.0004, 0, 0.008);
  private double angle;
  private double goalAngle;
  private double currentAngle;

  public Turn(Chassis drive, double angle) {
    // Use addRequirements() here to declare subsystem dependencies.\
    this.drive = drive;
    addRequirements(drive);
    this.angle = angle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drive.setEncoderToZero();
    currentAngle = drive.getDistance();
    goalAngle = currentAngle + angle;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentAngle = drive.getDistance();
    if(angle==90 || angle==-90)drive.arcadeDrive(0, MathUtility.clamp(turn90PID.calculate(goalAngle-currentAngle), -1, 1));
    else if(angle==180)drive.arcadeDrive(0, MathUtility.clamp(turn180PID.calculate(goalAngle-currentAngle), -1, 1));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Math.abs(goalAngle-currentAngle)<3) return true;
    else return false;
  }
}
