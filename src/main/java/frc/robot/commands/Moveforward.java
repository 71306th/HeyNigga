// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.ChenryLib.PID;

public class Moveforward extends CommandBase {

  private final Chassis drive;

  private double currentPos;
  private double goalPos;

  private PID forwardPID;

  public Moveforward(Chassis subsystem) {
    drive = subsystem;
    addRequirements(drive);
  }

  @Override // 初始化
  public void initialize() {
    drive.setEncoderToZero(); // 紅線畫過深藏輪迴的秘密 我揮霍運氣
  }

  @Override // 執行
  public void execute() {
    goalPos = currentPos + 1;
    forwardPID.calculate(goalPos-currentPos);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
