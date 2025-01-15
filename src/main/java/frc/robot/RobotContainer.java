// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.commands.Drive;
import frc.robot.commands.HorizontalDrive;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final Joystick driverL = new Joystick(0);
  private final Joystick driverR = new Joystick(1);
   private final Joystick operatorL = new Joystick(2);
   private final Joystick operatorR = new Joystick(3);

  private final JoystickButton resetYawButton = new JoystickButton(driverL, 1);

  //operator r
  //button 1
  private final JoystickButton horizontal_drive_button = new JoystickButton(operatorR, 1);
  
  //TODO: Bind climberBackButton
  //CLIMBBUTTON
//Climber up: low buttons left side, operatorL 11-16
//RESETBUTTON

  
  //Climber down: low buttons right side, operatorR 11-16

  

  
  

  public final SwerveDrive driveSwerve;
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  
  
   

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    this.driveSwerve = new SwerveDrive(driverL, driverR);
    //this.driveSwerve.setDefaultCommand(new SwerveTeleOp(driveSwerve,driverL,driverR));
    this.driveSwerve.setDefaultCommand(new Drive(driveSwerve, driverL, driverR, driverL.getRawAxis(0), this.driverL.getRawAxis(1), this.driverR.getRawAxis(0), false));
   // this.inTake.setDefaultCommand(new intakeSpin(inTake, 0.4));
    //this.noteShooter.setDefaultCommand(new ShooterIntake(noteShooter));
    configureButtonBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureButtonBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    // .onTrue(new ExampleCommand(m_exampleSubsystem));

     resetYawButton.onTrue(new InstantCommand(() -> driveSwerve.zeroGyro()));

     
     
      //dont like this, keep in mind while testing
      //shootButton.onTrue(new SpinUpShooter(noteShooter, Constants.shooterHighSpeed, 0, 1).andThen((new ShootCommand(noteShooter, shooterPivot, Constants.shooterHighSpeed, Constants.shooterBumpSpeed, true)).raceWith(new WaitCommand(0.25))).andThen(new ShootCommand(noteShooter, shooterPivot, 0, 0, false)));

      //shootButton.onFalse(new intakeSpin(inTake, Constants.intakeMotorSpeed));
      //shootButton.onFalse(new ShootCommand(noteShooter, shooterPivot, 0, 0, false));

      //intakeDumpButton.onTrue(new SpinUpShooter(noteShooter, 0.75, 0.75).alongWith(new intakeSpin(inTake, 0)));

      //intakePositionButton.onTrue(new PivotHoldCommand(shooterPivot, Constants.intakePos));
    //ampPositionButton.onTrue(new PivotHoldCommand(shooterPivot, Constants.ampPos).alongWith(new intakeSpin(inTake, 0)).alongWith(new intakeSpin(inTake, 0)).alongWith(((new ShootCommand(noteShooter, 0, 0)).raceWith(new WaitCommand(1))).andThen(new SpinUpShooter(noteShooter, Constants.shooterHighSpeed, 0))));
    


    //amp sequence: Maybe works?

    //ampPositionButton.onTrue(((new PivotHoldCommand(shooterPivot, Constants.ampPos).alongWith(new intakeSpin(inTake, 0)).alongWith(((new ShootCommand(noteShooter, 0, 0)).raceWith(new WaitCommand(1.5))).andThen(new SpinUpAmp(noteShooter)))).raceWith(new WaitCommand(4))).andThen(new PivotHoldCommand(shooterPivot, Constants.intakePos).alongWith(new intakeSpin(inTake, Constants.intakeMotorSpeed)).alongWith(((new ShootCommand(noteShooter, 0, 0)).raceWith(new WaitCommand(5))).andThen(new ShooterIntake(noteShooter, Constants.shooterIntakeSpeed)))));
    //ampPositionButton.onTrue((((new PivotHoldCommand(shooterPivot, Constants.ampPos).alongWith(new intakeSpin(inTake, 0)).alongWith((new SpinUpAmp(noteShooter)).raceWith(new WaitCommand(1.5)))).andThen(new AmpShootCommand(noteShooter)))));
    
    

    
    //alignButton.onTrue(new PodiumAlignment(driveSwerve, driverL, driverR, camera.getError(), this.camera));
    
     
    horizontal_drive_button.whileTrue(new HorizontalDrive(this.driveSwerve, operatorR));


    
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }
//gyro is subwoof
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return new SwerveAuto(driveSwerve, driverL, driverR);
    
    return null;
  }
}
