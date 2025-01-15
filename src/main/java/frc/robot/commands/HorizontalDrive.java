// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SwerveDrive;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class HorizontalDrive extends Command {
  /** Creates a new HorizontalDrive. */
  SwerveDrive dt;
  Joystick operator_r;
  double volt;
  public HorizontalDrive(SwerveDrive dt, Joystick operator_r) {
    this.dt = dt;
    this.operator_r = operator_r;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.dt);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    volt = this.operator_r.getX();
    this.dt.drive(new Translation2d(volt, 0), 0, false, true);
    SmartDashboard.putNumber("X Volt", volt);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
