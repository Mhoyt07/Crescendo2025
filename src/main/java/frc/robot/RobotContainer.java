// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.AmpShootCommand;
import frc.robot.commands.TwoNoteMidAuto;
import frc.robot.commands.TwoNoteAmpSideAuto;
import frc.robot.commands.ClimberStop;
 import frc.robot.commands.ClimberUp;
import frc.robot.commands.CloseLeftAuto;
import frc.robot.commands.PivotHoldCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.ShootStayLeftAuto;
import frc.robot.commands.ShootStayMidAuto;
import frc.robot.commands.ShootStayRightAuto;
import frc.robot.commands.ShooterIntake;
import frc.robot.commands.SpinUpAmp;
import frc.robot.commands.SpinUpShooter;
import frc.robot.commands.intakeSpin;

 
 import frc.robot.commands.ShootCommand;
 import frc.robot.commands.ShooterIntake;

 import frc.robot.commands.intakeSpin;
 import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import frc.robot.commands.CloseMidAuto;
import frc.robot.commands.CloseRightAuto;
import frc.robot.commands.SwerveWeaver;
import frc.robot.commands.ThreeNoteLeftAuto;
import frc.robot.commands.TwoNoteLeftAuto;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.commands.Drive;
import frc.robot.commands.PickAndLeaveAmpAuto;
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

  private final JoystickButton shootButton = new JoystickButton(operatorR, 1);
  private final JoystickButton intakePositionButton = new JoystickButton(operatorR, 2);
  private final JoystickButton subwooferPositionButton = new JoystickButton(operatorR, 4);
  private final JoystickButton ampPositionButton = new JoystickButton(operatorR, 5);
  private final JoystickButton ampShootButton = new JoystickButton(operatorL, 1);
  private final JoystickButton podiumPositionButton = new JoystickButton(operatorL, 4);
  private final JoystickButton sourceIntakeButton = new JoystickButton(operatorR, 3);
  
  //TODO: Bind climberBackButton
  //CLIMBBUTTON
  private final JoystickButton climberSlowButton = new JoystickButton(operatorL, 11);
//Climber up: low buttons left side, operatorL 11-16
//RESETBUTTON
  private final JoystickButton climberUpButton = new JoystickButton(operatorR, 14);

  
  //Climber down: low buttons right side, operatorR 11-16
  private final JoystickButton climberBackButton = new JoystickButton(operatorL, 14);
  
  private final JoystickButton intakeDumpButton = new JoystickButton(operatorL, 1);

  private final JoystickButton alignButton = new JoystickButton(driverR, 1);

  

  
  

  public final SwerveDrive driveSwerve;
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
   
  
  public final Intake inTake = new Intake();
  public final Shooter noteShooter = new Shooter();
  public final Pivot shooterPivot = new Pivot();
  public final Climber climb = new Climber();
  
  
   

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
      shootButton.onTrue(((new ShootCommand(noteShooter, shooterPivot, Constants.shooterHighSpeed, Constants.shooterBumpSpeed, true)).raceWith(new WaitCommand(0.75))).andThen((new ShootCommand(noteShooter, shooterPivot, 0, 0, false)).alongWith(new PivotHoldCommand(shooterPivot, Constants.intakePos)).raceWith(new WaitCommand(0.5))).andThen((new intakeSpin(inTake, Constants.intakeMotorSpeed)).alongWith(new ShooterIntake(noteShooter, inTake, Constants.shooterIntakeSpeed))));
      //shootButton.onTrue(new SpinUpShooter(noteShooter, Constants.shooterHighSpeed, 0, 1).andThen((new ShootCommand(noteShooter, shooterPivot, Constants.shooterHighSpeed, Constants.shooterBumpSpeed, true)).raceWith(new WaitCommand(0.25))).andThen(new ShootCommand(noteShooter, shooterPivot, 0, 0, false)));

      //shootButton.onFalse(new intakeSpin(inTake, Constants.intakeMotorSpeed));
      //shootButton.onFalse(new ShootCommand(noteShooter, shooterPivot, 0, 0, false));

      //intakeDumpButton.onTrue(new SpinUpShooter(noteShooter, 0.75, 0.75).alongWith(new intakeSpin(inTake, 0)));

      intakeDumpButton.whileTrue((new intakeSpin(inTake, -Constants.intakeMotorSpeed)).alongWith(new ShooterIntake(noteShooter, inTake, -Constants.shooterIntakeSpeed)));
      intakeDumpButton.onFalse((new intakeSpin(inTake, Constants.intakeMotorSpeed)).alongWith(new ShooterIntake(noteShooter, inTake, -Constants.shooterIntakeSpeed)));
      
       
    intakePositionButton.onTrue(new PivotHoldCommand(shooterPivot, Constants.intakePos).alongWith(new intakeSpin(inTake, Constants.intakeMotorSpeed)).alongWith(new ShooterIntake(noteShooter, inTake, Constants.shooterIntakeSpeed)));
    //intakePositionButton.onTrue(new PivotHoldCommand(shooterPivot, Constants.intakePos));
    subwooferPositionButton.onTrue(new PivotHoldCommand(shooterPivot, Constants.subwooferPos).alongWith(new SpinUpShooter(noteShooter, Constants.shooterHighSpeed, 0, 1)).alongWith(new intakeSpin(inTake, 0)));
    //ampPositionButton.onTrue(new PivotHoldCommand(shooterPivot, Constants.ampPos).alongWith(new intakeSpin(inTake, 0)).alongWith(new intakeSpin(inTake, 0)).alongWith(((new ShootCommand(noteShooter, 0, 0)).raceWith(new WaitCommand(1))).andThen(new SpinUpShooter(noteShooter, Constants.shooterHighSpeed, 0))));
    podiumPositionButton.onTrue(new PivotHoldCommand(shooterPivot, Constants.podiumPos).alongWith(new SpinUpShooter(noteShooter, 0.4, 0, 0.4)).alongWith(new intakeSpin(inTake, 0)));
    sourceIntakeButton.onTrue(new PivotHoldCommand(shooterPivot, Constants.sourcePos).alongWith(new ShooterIntake(noteShooter, inTake, 0.15)));



    //amp sequence: Maybe works?

    //ampPositionButton.onTrue(((new PivotHoldCommand(shooterPivot, Constants.ampPos).alongWith(new intakeSpin(inTake, 0)).alongWith(((new ShootCommand(noteShooter, 0, 0)).raceWith(new WaitCommand(1.5))).andThen(new SpinUpAmp(noteShooter)))).raceWith(new WaitCommand(4))).andThen(new PivotHoldCommand(shooterPivot, Constants.intakePos).alongWith(new intakeSpin(inTake, Constants.intakeMotorSpeed)).alongWith(((new ShootCommand(noteShooter, 0, 0)).raceWith(new WaitCommand(5))).andThen(new ShooterIntake(noteShooter, Constants.shooterIntakeSpeed)))));
    //ampPositionButton.onTrue((((new PivotHoldCommand(shooterPivot, Constants.ampPos).alongWith(new intakeSpin(inTake, 0)).alongWith((new SpinUpAmp(noteShooter)).raceWith(new WaitCommand(1.5)))).andThen(new AmpShootCommand(noteShooter)))));
    ampPositionButton.onTrue((new PivotHoldCommand(shooterPivot, Constants.ampPos).alongWith(new intakeSpin(inTake, 0)).raceWith(new WaitCommand(1.9))).andThen(new AmpShootCommand(noteShooter)));

    

    climberBackButton.whileTrue(new ClimberUp(climb, -Constants.climberUpSpeed));
    climberBackButton.onFalse(new ClimberStop(climb));
    climberUpButton.whileTrue(new ClimberUp(climb, Constants.climberSlowSpeed));
    climberUpButton.onFalse(new ClimberStop(climb));
    climberSlowButton.whileTrue(new ClimberUp(climb, Constants.climberDownSpeed));
    climberSlowButton.onFalse(new ClimberStop(climb));
    //climberFastButton.onTrue((new InstantCommand(() -> climb.climbDown()).raceWith(new WaitCommand(2))).andThen((new InstantCommand(() -> climb.finalClimb())).raceWith(new WaitCommand(2))).andThen(new ClimberStop(climb)));

    //alignButton.onTrue(new PodiumAlignment(driveSwerve, driverL, driverR, camera.getError(), this.camera));
    
     



    
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
    
    return new ShootStayLeftAuto(driveSwerve, driverL, driverR, noteShooter, inTake, shooterPivot);
  }
}
