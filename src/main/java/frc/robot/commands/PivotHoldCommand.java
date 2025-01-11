// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import javax.sound.sampled.Line;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pivot;

public class PivotHoldCommand extends Command {
  Pivot pivot;
  double position;
  LinearFilter filter;
  boolean isFinished;
  
  /** Creates a new PivotHoldCommand. */
  public PivotHoldCommand(Pivot pivot, double position) {
    // Use addRequirements() here to declare subsystem dependencies.
        this.pivot = pivot;
    addRequirements(this.pivot);
    this.position = position;

    filter = LinearFilter.singlePoleIIR(0.1, 0.02);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double shooterPivotValue = this.pivot.pivotPot.get();

    double motorSpeed = MathUtil.clamp(this.pivot.pivotPID.calculate(this.filter.calculate(shooterPivotValue), position), -100, 100);
    motorSpeed /= 100;
    // double motorSpeed = this.pivot.pivotPID.calculate(this.filter.calculate(shooterPivotValue), position)+this.pivot.calcFeedforward();
    // SmartDashboard.putNumber("DesiredPos", position);
    this.pivot.setSpeed(motorSpeed);
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
