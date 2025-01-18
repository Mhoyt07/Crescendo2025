// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Vision;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class XAllign extends Command {
  /** Creates a new XAllign. */
  SwerveDrive dt;
  Vision vis;
  PIDController pid;
  double volt;
  public XAllign(SwerveDrive dt, Vision vision) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.dt = dt;
    this.vis = vision;
    this.pid = new PIDController(Constants.vision.kp, Constants.vision.ki, Constants.vision.kd);
    this.volt = 0;
    addRequirements(this.dt, this.vis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    volt = MathUtil.clamp(this.pid.calculate(this.vis.x_offset(), 0), -3, 3);
    //this.dt.drive(new Translation2d(0, volt), 0, false, true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (this.vis.x_offset() == 0) {
      return true;
    } else {
      return false;
    }
  }
}
