// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.ChenryLib.PID;

public class Turn extends CommandBase {

  
  private final Chassis drive;

  private double currentAngle;
  private double goalAngle;
  private double targetangle;

  private PID turnPID;


  public Turn(Chassis subsystem, double targetangle) {
    drive = subsystem;
    addRequirements(drive);
  }

  @Override // 初始化
  public void initialize() {
    drive.resetGyro();
  }

  @Override // 執行
  public void execute() {
    goalAngle = currentAngle - targetangle;
    turnPID.calculate(goalAngle - currentAngle);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    if(Math.abs(goalAngle - currentAngle) < 2) {return true;}
    else return false;
  }
}
