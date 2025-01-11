// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class SpinUpShooter extends Command {
  Shooter shooter;
  double highSpeed;
  double lowSpeed;
  boolean isFinished;
  double fullSpeed;
  /** Creates a new SpinUpShooter. */
  public SpinUpShooter(Shooter shooter, double highSpeed, double lowSpeed, double fullSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
        this.shooter = shooter;
    addRequirements(this.shooter);
    this.highSpeed = highSpeed;
    this.lowSpeed = lowSpeed;
    this.fullSpeed = fullSpeed;
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.shooter.spinUpShooter(highSpeed, lowSpeed, fullSpeed);
/* 
    if (this.shooter.fastShooterMotor.getEncoder().getVelocity()==Constants.shooterTargetVelocity) {
      this.isFinished = true;
    }
*/
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.isFinished = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFinished;
  }
}
