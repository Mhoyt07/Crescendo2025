// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;


/** Add your docs here. */
public class SwerveModule {
    public final int moduleNumber;

    public final Rotation2d turningOffset;



    //Establishing the motors (with their built-in encoders), CANCoder, and PID loops here
    private final SparkMax driveMotor;
    private final SparkMax turningMotor;
    private final SparkMaxConfig turn_config;
    private final SparkMaxConfig drive_config;

    private final RelativeEncoder driveEncoder;
    public final RelativeEncoder turningEncoder;

    public final CANCoder bestTurningEncoder;

    //SparkMaxPIDController speedPID;
    //SparkMaxPIDController turningPID;

    private final PIDController drivePID; 
    private final PIDController turningPID;

    private final LinearFilter driveVelFilter = LinearFilter.singlePoleIIR(0.1, 0.02);
    private final LinearFilter desiredDriveFilter = LinearFilter.singlePoleIIR(0.1, 0.02);

    private double driveVelocity;
    private double desiredDriveVel;

    //feedforward loop here: will use for auto
    private final SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(Constants.ffkS, Constants.ffkV, Constants.ffkA);

      //the skeleton of each individual swerve module
    public SwerveModule(int moduleNumber, int driveMotorId, int turningMotorId, int canCoderId, Rotation2d turningOffest) {
        this.moduleNumber = moduleNumber;
        this.turningOffset = turningOffest;

        this.bestTurningEncoder = new CANCoder(canCoderId);

        this.turningMotor = new SparkMax(turningMotorId, MotorType.kBrushless);
        this.turn_config = new SparkMaxConfig();

        this.turn_config
            .inverted(true)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(20)
            .voltageCompensation(12);
        this.turn_config.encoder
            .positionConversionFactor(Constants.turningMotorPosFactor);
        this.turn_config.signals
            .externalOrAltEncoderPosition(500);

        //this.turningMotor.restoreFactoryDefaults(); //as name implies  
        //this.turningMotor.setIdleMode(com.revrobotics.CANSparkBase.IdleMode.kBrake); 
        //this.turningMotor.setInverted(true); //invets motor diection
        //this.turningMotor.setSmartCurrentLimit(20); //limits current to motor
        //this.turningMotor.enableVoltageCompensation(12); 
        //this.turningMotor.burnFlash(); no substitute
    
    
        this.turningEncoder = this.turningMotor.getEncoder();
        //this.turningEncoder.setPositionConversionFactor(Constants.turningMotorPosFactor); 
        //adjusts the position values of the turning motor by a certain value, in this case to convert to degrees (ish)
        this.turningMotor.configure(turn_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        //turningPID = turningMotor.getPIDController();

        this.turningPID = new PIDController(Constants.turningkP, Constants.turningkI, Constants.turningkD); //creates a new PID Controller and sets values
        // this.turningPID.enableContinuousInput(-180, 180);
        
        //replaced by turn_config.signals.externaloraltencoderposiiton
        //CANSparkMaxUtil.setCANSparkMaxBusUsage(this.turningMotor, Usage.kPositionOnly);
        
        //turningPID.setP(Constants.turningkP);
        //turningPID.setI(Constants.turningkI);
        //turningPID.setD(Constants.turningkD);
        //turningPID.setFF(0);
        //resetToAbsolute();

        this.driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        this.drive_config = new SparkMaxConfig();

        this.drive_config
            .inverted(false)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(50)
            .voltageCompensation(12);
        this.drive_config.encoder
            .positionConversionFactor(Constants.driveMotorPosFactor)
            .velocityConversionFactor(Constants.driveMotorVelFactor);

        //this.driveMotor.restoreFactoryDefaults();
        //this.driveMotor.setSmartCurrentLimit(50);
        //this.driveMotor.enableVoltageCompensation(12);
        //this.driveMotor.setIdleMode(com.revrobotics.CANSparkBase.IdleMode.kBrake);
        //this.driveMotor.setInverted(false);
        //this.driveMotor.burnFlash();


        this.driveEncoder = this.driveMotor.getEncoder();
        //this.driveEncoder.setPositionConversionFactor(Constants.driveMotorPosFactor);
        //this.driveEncoder.setVelocityConversionFactor(Constants.driveMotorVelFactor);
        this.driveEncoder.setPosition(0);

        this.driveMotor.configure(drive_config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.drivePID = new PIDController(Constants.drivekP, Constants.drivekI, Constants.drivekD);

    }
    

    /**
     * Returns the current turning turning of the wheel
     * @return The turning in degrees.
     */
    // public Rotation2d getTurningAngle() {
    //     return Rotation2d.fromDegrees(this.turningEncoder.getPosition());
    // }

    /**
     * Returns the current voltage used by the turning motor
     * @return The voltage.
     */
    public double getTurningMotorVoltage() {
        //as the name implies
        return this.turningMotor.getBusVoltage();
    }


    /**
     * Depicts a new SwerveModuleState using the current velocity and turning of the wheel
     * @return
     */
    public SwerveModuleState getState() {
        //return new SwerveModuleState(this.driveEncoder.getVelocity(), getTurningAngle());
        return new SwerveModuleState(this.driveEncoder.getVelocity(), this.getCANCoder());
        //returns a swervemodulestate object using the velocity and current angle of the modules
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(this.driveEncoder.getPosition(), new Rotation2d(turningEncoder.getPosition()));
        //returns a different swervemodulestate object with the position and angle of the modules
    }
    

    /**
     * Return the postion of the better CAN encoder.
     * @return Current position in degrees.
     */
    public Rotation2d getCANCoder() {
        return Rotation2d.fromDegrees(this.bestTurningEncoder.getAbsolutePosition());
        //gets the position of the CANCoders in degrees
    }

    //Resets the position of the CANCoders to their default state
    /**
     * 
     */
    public void resetToAbsolute() {
        System.out.print(getCANCoder().getDegrees());
        // double absolutePosition =  getCANCoder().getDegrees() - turningOffset.getDegrees();
        this.turningEncoder.setPosition(turningOffset.getDegrees());
        //sets the position of the turning encoders to the absolute position of the CANCoders. idk if still relevant
      }
    

    //calculates the desired position of the swerve wheel
    public void setDesiredState(SwerveModuleState desiredState, boolean isAuto) {
        //Rotation2d encoderRotation = new Rotation2d(turningEncoder.getPosition());
        //desiredState = SwerveOpt.optimize(desiredState, getState().angle);
        //desiredState = SwerveModuleState.optimize(desiredState, encoderRotation);

        boolean invertDriveMotor = setAngle(desiredState);
        if (isAuto) {
            new WaitCommand(2);
        }


        setSpeed(desiredState, isAuto, invertDriveMotor);
    }
    //calculate the necessary speed for the speed motor and set the motor to that speed
    private void setSpeed(SwerveModuleState desiredState, boolean isAuto, boolean invertDriveMotor) {
        if (isAuto == false) {
            double driveMotorOutput = desiredState.speedMetersPerSecond / Constants.maxSpeed;
            driveMotor.set(invertDriveMotor ? driveMotorOutput * -1 : driveMotorOutput);
        }
        else {
            driveVelocity = driveVelFilter.calculate(driveEncoder.getVelocity());
            desiredDriveVel = desiredDriveFilter.calculate(desiredState.speedMetersPerSecond);
            //double driveAutoMotorVoltage = drivePID.calculate(driveEncoder.getVelocity(), desiredState.speedMetersPerSecond) + feedForward.calculate(desiredState.speedMetersPerSecond);
            double driveAutoMotorVoltage = drivePID.calculate(driveVelocity, desiredDriveVel);

            //double driveAutoMotorVoltage = feedForward.calculate(desiredState.speedMetersPerSecond);

            //double driveAutoMotorVoltage = feedForward.calculate(desiredState.speedMetersPerSecond) + drivePID.calculate(driveEncoder.getVelocity(), desiredState.speedMetersPerSecond);

            
            double drive_motor_volt_out = desiredState.speedMetersPerSecond / Constants.maxSpeed;
            driveMotor.setVoltage(invertDriveMotor ? drive_motor_volt_out * -1 : drive_motor_volt_out);
            SmartDashboard.putNumber("drive motor volt out", drive_motor_volt_out);
        }

    
    }
    //runs the PID loop for the turning motor, but only if the required speed is greater than 1% motor power, then powers the turning motor
    private boolean setAngle(SwerveModuleState desiredState) {
        boolean invertDriveMotor = false;
        // Get the current state of the swerve module (i.e. What direction is it facing?)
        SwerveModuleState currentState = this.getState();

        // What is the current direction (in degrees) the swerve module is facing?
        double currentDegrees = (currentState.angle.getDegrees() - this.turningOffset.getDegrees());
        
        // We want the current degrees between [-180, 180]. When looking at a circle
        // -190 is equivalent to 170 so the below ternaries convert numbers less than -180
        // or greater than 180 into [-180,180] 
        currentDegrees = currentDegrees < -180 ? currentDegrees + 360 : currentDegrees;
        currentDegrees = currentDegrees > 180 ? currentDegrees - 360 : currentDegrees;

        // Next figure out the what direction the service module should be pointing. We mode the number
        // (% 360) to make sure the number is between [-360,360]
        double desiredDegrees = desiredState.angle.getDegrees() % 360;
        // The following logic calculates the the different between the desired angle of the
        // swerve module to the desired angle. The final result will be some number
        // in the range [-180, 180]
        double diff = (currentDegrees - desiredDegrees + 180) % 360 - 180;
        diff = diff < -180 ? diff + 360 : diff;
        diff = diff > 180 ? diff - 360 : diff;

        // In theory, we could turn the swerve to this "diff" angle and everything would work but its
        // possible to be more efficient. For example, if the operator pushes the joystick forward,
        // then pulls it full back, the code above will turn the swerve module 180 degrees when
        // instead we could just reverse the direction of the drive motor.

        // If the `diff` is greater than 90 or less than -90 then invert the drive motor
        // and change the diff.
        // -120 is equivalent to 60 with an inverted drive direction.
        // 120 is equivalent to -60 with an inverted drive direction.

        // If the `diff` is greater than 90 or less than -90 then invert the drive motor
        // and change the diff.
        // -120 is equivalent to 60 with an inverted drive direction.
        // 120 is equivalent to -60 with an inverted drive direction.
        if (Math.abs(diff) > 90) {
            invertDriveMotor = true;
            if (diff < 0) {
                diff += 180;
            } else {
                diff -= 180;
            }
        }


        

        // If we are a degree or less off, the turning motor will not do anything, else, calculate the pid
        // values based off the diff and 0.
        double turningMotorValue = Math.abs(diff) < 1 ? 0 : turningPID.calculate(diff, 0);
        // Motors can only take a value in the range [-1,1] but the PID may output a value outside
        // this range, thus we "clamp" the output of the PID such that -1.5 would just be -1.
        turningMotorValue = turningMotorValue > 1 ? 1 : turningMotorValue;
        turningMotorValue = turningMotorValue < -1 ? -1 : turningMotorValue;

        // Turn the motor to the desired position
        turningMotor.set(turningMotorValue);

        // Return the direction the drive motor is expected to go.

        return invertDriveMotor;

    }

    public void gogogo() {
        turningMotor.set(.1);
    }



}