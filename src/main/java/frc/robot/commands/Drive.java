// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.SwerveDrive;

public class Drive extends Command {
  private final Joystick driverL;
  private final Joystick driverR;

  private final SwerveDrive driveSwerve;
  // private final BooleanSupplier robotCentricSupply;

  private final SlewRateLimiter translationLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter rotationLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter strafeLimiter = new SlewRateLimiter(3);

  private double strafeVal;
  private double translationVal;
  private double rotationVal;

  private boolean isAuto;
  /** Creates a new SwerveTeleOp. */
  public Drive(SwerveDrive driveSwerve, Joystick driverL, Joystick driverR, double strafeVal, double translationVal, double rotationVal, boolean isAuto) {
    this.driveSwerve = driveSwerve;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSwerve);
    this.driverL = driverL;
    this.driverR = driverR;

    this.strafeVal = strafeVal;
    this.translationVal = translationVal;
    this.rotationVal = rotationVal;

    this.isAuto = isAuto;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double strafeValue;
    double translationValue;
    double rotationValue;

    

    //L0, L1, R0
    if (isAuto) {
    strafeValue = strafeLimiter.calculate(MathUtil.applyDeadband(strafeVal, 0.1));
    translationValue = translationLimiter.calculate(MathUtil.applyDeadband(translationVal, 0.1));
    rotationValue = rotationLimiter.calculate(MathUtil.applyDeadband(rotationVal, 0.1));
    
    }
    else {
    strafeValue = strafeLimiter.calculate(MathUtil.applyDeadband(this.driverL.getRawAxis(0), 0.1));
    translationValue = translationLimiter.calculate(MathUtil.applyDeadband(this.driverL.getRawAxis(1), 0.1));
    rotationValue = rotationLimiter.calculate(MathUtil.applyDeadband(this.driverR.getRawAxis(0), 0.1));

    
    }
    driveSwerve.drive(
      new Translation2d(translationValue, strafeValue).times(Constants.maxSpeed), 
      rotationValue * Constants.maxAngularVelocity, true, 
      false
    );
    

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
