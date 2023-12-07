// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.Chassis.InputStates;

public class IntakeSettings extends CommandBase {
  /** Creates a new Intake. */

  private Chassis drive;
  private String todo;


  public IntakeSettings(Chassis drive, String todo) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drive = drive;
    addRequirements(drive);
    this.todo = todo;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch(todo){
      case "setUp":
        drive.setStates(InputStates.Y);
        break;
      case "setMid":
        drive.setStates(InputStates.X);
        break;
      case "setLow":
        drive.setStates(InputStates.A);
        break;
      case "setIdle":
        drive.setStates(InputStates.idle);
        break;
    }
  }
}
