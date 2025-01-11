// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShooterIntake extends Command {
  Shooter shooter;
  boolean isFinished;
  double speed;
  Intake intake;
  /** Creates a new ShooterIntake. */
  public ShooterIntake(Shooter shooter, Intake intake, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
        this.shooter = shooter;
        this.intake = intake;
    addRequirements(shooter);
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

    if (this.intake.shooterLimitSwitch.get()==false) {
      shooter.shooterIntake(0);
    }
    else {
    shooter.shooterIntake(speed);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.isFinished = true;
    this.shooter.shooterIntake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.isFinished;
  }
}
