// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  //PC Specs: 26.5 in 
  //

  //Maybe need maxVolts later?

  //PID or feedforward? BOTH!

  //Test Swerve Auto IDs and stuff
  //mod 3: FL
   /* 
  public static final int mod0DriveMotor = 59;        //59    //10
  public static final int mod0TurningMotor = 55;      //55    //11
  public static final int mod0CANCoder = 7;           //3   //2
  public static final Rotation2d mod0TurningOffset = Rotation2d.fromDegrees(226.0);
  
  //mod 0: FR
   
  public static final int mod1DriveMotor = 54;       //58    //9
  public static final int mod1TurningMotor = 58;       //54    //8
  public static final int mod1CANCoder = 5;       //0       //0
  public static final Rotation2d mod1TurningOffset = Rotation2d.fromDegrees(67.3);
  
  //mod 2: BL
   
  public static final int mod2DriveMotor = 53;        //53    //12
  public static final int mod2TurningMotor = 61;        //61    //13
  public static final int mod2CANCoder = 4;         //2       //1
  public static final Rotation2d mod2TurningOffset = Rotation2d.fromDegrees(103.8);

//mod 1: BR
 
  public static final int mod3DriveMotor = 36;       //36    //9
  public static final int mod3TurningMotor = 52;       //52    //8
  public static final int mod3CANCoder = 6;       //1       //3
  public static final Rotation2d mod3TurningOffset = Rotation2d.fromDegrees(271.2);
  */

  //NOTE: RENAME MOD NUMBERS TO POSITION ON BOT!!!!

  //EVERYTHING IS FLIP-FLOPPED HORIZONTALLY

  //Bevel gears should face towards the right when back if lifted up

  //Question marked module numbers are what to use for readouts

  //module 3 constants: Front Left    Module 0???                //MG    //PC

   //module 20 -> 21 ->23 -> 33
  public static final int mod0DriveMotor = 37;//59  //36  //54    //37 //59    //10
  public static final int mod0TurningMotor = 60; //55//52  //58   //60  //55    //11
  public static final int mod0CANCoder = 3; //7  //6 //5      //3   //2
  public static final Rotation2d mod0TurningOffset = Rotation2d.fromDegrees(148.2);//226.0//271.2//67.3 //148.2

  //module 21 -> 23 -> 20 ->31
  //module 0 constants: Front Right Module 1????
  public static final int mod1DriveMotor = 39;//54 //59 //36  //39   //58    //9
  public static final int mod1TurningMotor = 32;//58//5 //52 //32     //54    //8
  public static final int mod1CANCoder = 1; //5 //7  //6  //1  //0       //0
  public static final Rotation2d mod1TurningOffset = Rotation2d.fromDegrees(187.3);//67.3//226.0 //271.2 //97.3

  //mod22 ->32
  //module 2 constants: Back Left
  public static final int mod2DriveMotor = 45;  //53   //45   //53    //12
  public static final int mod2TurningMotor = 40; //61  //43     //61    //13
  public static final int mod2CANCoder = 2;      //4   //2       //1
  public static final Rotation2d mod2TurningOffset = Rotation2d.fromDegrees(166.1); //103.8 //166.1


  //mod23 -> 20 ->21 ->34
  //module 1 constants: Back Right mod 3???
  public static final int mod3DriveMotor = 42;//36 //54 //59   //42  //36    //9
  public static final int mod3TurningMotor = 31;  //52//58 //55 //31   //52    //8
  public static final int mod3CANCoder = 8;//6 //5 //7 //8    //1       //3
  public static final Rotation2d mod3TurningOffset = Rotation2d.fromDegrees(69.3); //271.2//67.3  //226.0 //156.8


  //PID Constants
  public static final double drivekP = 0.02; 
  public static final double drivekI = 0;
  public static final double drivekD = 0;

  public static final double turningkP = 0.00759; //init value .0066
  public static final double turningkI = 0.00069; //init value .0006
  public static final double turningkD = 0.0001; //init value 

  //gyro offset n degrees
  public static final double gyro_offset = 90;


  //Gear Ratios (for the conversion factors)
  public static final double driveMotorRatio = 6.12; //L3: 6.12, L2: 6.75
  public static final double turningMotorRatio = 150 / 7;

  //Conversion Factors
  public static final double driveMotorPosFactor = Math.PI * driveMotorRatio * 9.5;
  public static final double driveMotorVelFactor = driveMotorPosFactor / 60;
  public static final double turningMotorPosFactor = 360 / turningMotorRatio;

  
  //Max drive of the robot (in meters per second or radians per second)
  public static final double maxSpeed = 5;
  public static final double maxAutoSpeed = 2.5;
  public static final double maxAutoAcceleration = 0.5;
  public static final double maxAngularVelocity = 7.0;
  public static final double maxAutoAngularVelocity = Math.PI;
  

  


  //Lengths of the drivetrain, as well as the map of where the wheels are on the robot (like coordinates)
  public static final double robotLength = Units.inchesToMeters(23); //23.625? or .675?
  public static final double robotWidth = Units.inchesToMeters(23);

/* 
  public static final double robotLength = Units.inchesToMeters(23.675); 
  public static final double robotWidth = Units.inchesToMeters(23.675);
  */


  public static final SwerveDriveKinematics SwerveMap = new SwerveDriveKinematics(
    new Translation2d(robotLength / 2, robotWidth / 2), //++
    new Translation2d(robotLength / 2, -robotWidth /2),  //+-
    new Translation2d(-robotLength / 2, robotWidth / 2), //-+
    new Translation2d(-robotLength / 2, -robotWidth / 2) //--

    );



    //Intake Constants

    public static final int intakeMotorID = 44;

    public static final double intakeMotorSpeed = 1;

        //Pivot Angles -3

        public static final double intakePos = 73; //102 original
        public static final double subwooferPos = intakePos+15.5;
        public static final double ampPos = intakePos+39;
        public static final double podiumPos = intakePos+8.5;
        public static final double sourcePos = intakePos+10;
        public static final double backshotPos = intakePos+39.75;


        //test values
        // public static final double intakePos = 103.5;
        // public static final double subwooferPos = 118;
        // public static final double ampPos = 141.5;
        // public static final double podiumPos = 114.5;
        // public static final double sourcePos = 113;
        // public static final double backshotPos = 142.5;


    //Shooter Constants

    public static final double alignSpeed = 0.25;

    public static final int fastShooterMotorID = 41;
    public static final int slowShooterMotorID = 35;
    public static final int shooterHoldMotorID = 38;

    public static final int shooterPotID = 1;


    public static final double shooterIntakeSpeed = 0.5;

    public static final double shooterHighSpeed = 0.8;
    public static final double shooterLowSpeed = 0.2;
    public static final double shooterBumpSpeed = 0.2;

    public static final double shooterTargetVelocity = -4400;


    //Vision Constants

    public static final double cameraFOV = 42; //camera FOV in degrees
    public static final double cameraXRez = 640; //camera horizontal resolution in pixels
    public static final double cameraFOVRatio = cameraXRez / cameraFOV; //camera FOV in pixels

    public static final double alignkP = 0.2;

    public static final int speakerTagID = 1;



     


    //Pivot Constants

    public static final int pivotMotorID = 47;

    //test values
    // public static final double pivotkP = 1.5;
    // public static final double pivotkI = .2;
    // public static final double pivotkD = 0.6;

  //match values

    // public static final double pivotkP = .007; 
    // public static final double pivotkI = 0;
    // public static final double pivotkD = 0.002;

    public static final double pivotkP = 1.1; 
    public static final double pivotkI = 0.2;
    public static final double pivotkD = 0.05;


    public static final double pivotZeroValue = 177.2;
    public static final double pivotNinetyValue = 133.7;

    public static final double pivotPotToDegrees = (177.2-133.7)/90;




    //Climber Constants

    public static final int climberMotorLeftID = 40;
    public static final int climberMotorRightID = 29;

    public static final double climberUpSpeed = 0.5;
    public static final double climberSlowSpeed = 0.5;
    public static final double climberDownSpeed = 0.5;

    //

    //public static final int climberLeftServoID = 0;
    //public static final int climberRightServoID = 1;


  //auto constants
  //change PID for testing swerve auto

    public static final double autoRotateP = 0.25;
    public static final double autoRotateI = 0;
    public static final double autoRotateD = 0;

    public static final double autoTurningP = 0.5;
    public static final double autoTurningI = 0;
    public static final double autoTurningD = 0;

    public static final double autoXP = 0.5;
    public static final double autoXI = 0;
    public static final double autoXD = 0;

    public static final double autoYP = 0.5;
    public static final double autoYI = 0;
    public static final double autoYD = 0;

    public static final TrapezoidProfile.Constraints autoTurnController = new TrapezoidProfile.Constraints(maxAutoAngularVelocity, maxAutoAngularVelocity);

    //feedforward constants
  public static final double ffkS = 0.99;
  public static final double ffkV = 0.5; //5 m/s 2.44
  public static final double ffkA = 0.1; //accel to 5 m/s 0.27



  public static class vision {
    public static final double kp = 1;
    public static final double ki = 0;
    public static final double kd = 0;
  }


}

