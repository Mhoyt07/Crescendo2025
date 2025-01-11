// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  
  public CANSparkMax fastShooterMotor;
  public CANSparkMax slowShooterMotor;
  CANSparkMax shooterHoldMotor;


  PIDController shooterPID;

  BangBangController shooterVelocityController;
    

  boolean intakeState;

  double shooterCurrent;

  double test;


  /** Creates a new Shooter. */
  public Shooter() {
    
    fastShooterMotor = new CANSparkMax(Constants.fastShooterMotorID, MotorType.kBrushless);
    slowShooterMotor = new CANSparkMax(Constants.slowShooterMotorID, MotorType.kBrushless);
    shooterHoldMotor = new CANSparkMax(Constants.shooterHoldMotorID, MotorType.kBrushless);

    //shooterVelocityController = new BangBangController(0);



    fastShooterMotor.setIdleMode(IdleMode.kBrake);
    slowShooterMotor.setIdleMode(IdleMode.kBrake);
    shooterHoldMotor.setIdleMode(IdleMode.kBrake);

    fastShooterMotor.setInverted(false);
    slowShooterMotor.setInverted(false);
    shooterHoldMotor.setInverted(false);

    fastShooterMotor.setSmartCurrentLimit(50);
    slowShooterMotor.setSmartCurrentLimit(50);


    

    shooterCurrent = fastShooterMotor.getOutputCurrent();

    test = fastShooterMotor.getEncoder().getVelocity();

  }
 
  

  public void shooterIntake(double speed) {

  
    
      
      fastShooterMotor.set(speed);
      slowShooterMotor.set(speed);
      shooterHoldMotor.set(0.15);
      
    
  }

  public void spinUpShooter(double highSpeed, double lowSpeed, double fullSpeed) {
    //double fastSpeed = shooterVelocityController.calculate(fastShooterMotor.getEncoder().getVelocity(), -highSpeed);
    //double test = shooterVelocityController.cal
    shooterHoldMotor.set(Constants.shooterBumpSpeed);
    //fastShooterMotor.set(-highSpeed);
    //slowShooterMotor.set(-highSpeed);
     
    if (fastShooterMotor.getEncoder().getVelocity()>-4400) {
      fastShooterMotor.set(-fullSpeed);
      slowShooterMotor.set(-fullSpeed);
    }
    else {
      fastShooterMotor.set(-highSpeed);
      slowShooterMotor.set(-highSpeed);
    }
    
  }

  public void shootCommand(double highSpeed, double lowSpeed, double bumpSpeed) {
    
    fastShooterMotor.set(-highSpeed);
    slowShooterMotor.set(-highSpeed);
    shooterHoldMotor.set(-bumpSpeed);
  }

  public void spinUpAmp(double highSpeed, double lowSpeed) {
    fastShooterMotor.set(-highSpeed);
    slowShooterMotor.set(-lowSpeed);
    //shooterHoldMotor.set(-Constants.shooterBumpSpeed);
  }
  public void ampShootCommand(double highSpeed, double lowSpeed) {
    fastShooterMotor.set(-highSpeed);
    slowShooterMotor.set(-lowSpeed);
    shooterHoldMotor.set(-0.2);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //SmartDashboard.putNumber("Shooter Current", shooterCurrent);
    SmartDashboard.putNumber("Shooter Velocity", fastShooterMotor.getEncoder().getVelocity());
  }
}
