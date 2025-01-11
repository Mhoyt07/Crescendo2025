// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;

public class ShootCommand extends Command {
  Shooter shooter;
  boolean isFinished;
  double highSpeed;
  double kickSpeed;
  Pivot pivot;
  boolean condition;
  /** Creates a new ShootCommand. */
  public ShootCommand(Shooter shooter, Pivot pivot, double highSpeed, double kickSpeed, boolean needsCond) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;
    addRequirements(shooter);
    this.highSpeed = highSpeed;
    this.kickSpeed = kickSpeed;
    this.pivot = pivot;
    this.condition = needsCond;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     /* 
    if (condition) {
      if (this.pivot.pivotPot.get()<122 || this.pivot.pivotPot.get()>126) {
      shooter.spinUpShooter(highSpeed, highSpeed);
     }
     else {
      shooter.shootCommand(this.highSpeed, this.highSpeed, this.kickSpeed);
     }
    }
    */

    
/* 
    if (condition) {
      if (this.shooter.fastShooterMotor.getEncoder().getVelocity()<=Constants.shooterTargetVelocity) {
        shooter.shootCommand(this.highSpeed, this.highSpeed, -this.kickSpeed);
      }
      else {
        shooter.shootCommand(this.highSpeed, this.highSpeed, this.kickSpeed);
      }
     */
        shooter.shootCommand(this.highSpeed, this.highSpeed, this.kickSpeed);

    }
    //else {
      //shooter.shootCommand(this.highSpeed, this.highSpeed, this.kickSpeed);
    //}
  


  

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
