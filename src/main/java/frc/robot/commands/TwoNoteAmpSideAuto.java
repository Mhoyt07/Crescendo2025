// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.SwerveDrive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TwoNoteAmpSideAuto extends SequentialCommandGroup {
  /** Creates a new TwoNoteRightAuto. */
  public TwoNoteAmpSideAuto(SwerveDrive driveSwerve, Joystick driverL, Joystick driverR, Shooter noteShooter, Intake inTake, Pivot shooterPivot) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new InstantCommand(() -> driveSwerve.zeroGyroAuto(-56)),
      (new PivotHoldCommand(shooterPivot, Constants.backshotPos).alongWith(new SpinUpShooter(noteShooter, Constants.shooterHighSpeed, 0, 1)).alongWith(new intakeSpin(inTake, 0))).raceWith(new WaitCommand(1.5)),
      (new ShootCommand(noteShooter, shooterPivot, Constants.shooterHighSpeed, Constants.shooterBumpSpeed, false).raceWith(new WaitCommand(0.5))),
      (new ShootCommand(noteShooter, shooterPivot, 0, 0, false).alongWith(new PivotHoldCommand(shooterPivot, Constants.intakePos))).raceWith(new WaitCommand(0.5)),
      ((new intakeSpin(inTake, Constants.intakeMotorSpeed).alongWith(new ShooterIntake(noteShooter, inTake, Constants.shooterIntakeSpeed)))).raceWith(new WaitCommand(1.5)),
      (new Drive(driveSwerve, driverL, driverR, 0, -.35, 0, true).alongWith(new ShooterIntake(noteShooter, inTake, Constants.shooterIntakeSpeed))).raceWith(new WaitCommand(2.1)),
      new Drive(driveSwerve, driverL, driverR, 0, 0, 0, true).raceWith(new WaitCommand(0.25)),
      new Drive(driveSwerve, driverL, driverR, 0, .35, -0.07, true).raceWith(new WaitCommand(2)),
      new Drive(driveSwerve, driverL, driverR, 0, 0, 0, true).raceWith(new WaitCommand(1.3)),
      (new PivotHoldCommand(shooterPivot, Constants.backshotPos).alongWith(new SpinUpShooter(noteShooter, Constants.shooterHighSpeed, 0, 1)).alongWith(new intakeSpin(inTake, 0))).raceWith(new WaitCommand(1.5)),
      (new ShootCommand(noteShooter, shooterPivot, Constants.shooterHighSpeed, Constants.shooterBumpSpeed, false).raceWith(new WaitCommand(1))),
      (new PivotHoldCommand(shooterPivot, Constants.intakePos).raceWith(new WaitCommand(1.5).alongWith(new intakeSpin(inTake, 0)).alongWith(((new ShootCommand(noteShooter, shooterPivot, 0, 0, false)).raceWith(new WaitCommand(1.5))).andThen(new ShooterIntake(noteShooter, inTake, 0))))).raceWith(new WaitCommand(1))
    );
  }
}
