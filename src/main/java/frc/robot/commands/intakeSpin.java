// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;

public class intakeSpin extends Command {
  Intake inTake;
  boolean isFinished;
  double speed;
  /** Creates a new intakeSpin. */
  public intakeSpin(Intake intakeArm, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
        inTake = intakeArm;
    addRequirements(intakeArm);
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    inTake.intakeSpin(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.isFinished = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.isFinished;
  }
}
