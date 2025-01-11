// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
  CANSparkMax leftClimbMotor;
  CANSparkMax rightClimbMotor;

  double leftCurrent;
  double rightCurrent;

  double leftRawCurrent;
  double rightRawCurrent;

  double leftStopCurrent;
  double rightStopCurrent;

  LinearFilter leftFilter;
  LinearFilter rightFilter;

  LinearFilter leftStopFilter;
  LinearFilter rightStopFilter;

  boolean leftState;
  boolean rightState;

  Servo climberLeftServo;
  Servo climberRightServo;
  /** Creates a new Climber. */
  public Climber() {
    leftClimbMotor = new CANSparkMax(Constants.climberMotorLeftID, MotorType.kBrushless);
    rightClimbMotor = new CANSparkMax(Constants.climberMotorRightID, MotorType.kBrushless);

    leftClimbMotor.setIdleMode(IdleMode.kBrake);
    rightClimbMotor.setIdleMode(IdleMode.kBrake);

    leftClimbMotor.setInverted(false);
    rightClimbMotor.setInverted(false);

    leftFilter = LinearFilter.movingAverage(5);
    rightFilter = LinearFilter.movingAverage(5);

    leftStopFilter = LinearFilter.movingAverage(40);
    rightStopFilter = LinearFilter.movingAverage(40);

    leftRawCurrent = leftClimbMotor.getOutputCurrent();
    rightRawCurrent = rightClimbMotor.getOutputCurrent();

    leftCurrent = leftFilter.calculate(leftClimbMotor.getOutputCurrent());
    rightCurrent = rightFilter.calculate(rightClimbMotor.getOutputCurrent());

    leftStopCurrent = leftStopFilter.calculate(leftClimbMotor.getOutputCurrent());
    rightStopCurrent = rightStopFilter.calculate(rightClimbMotor.getOutputCurrent());

    //climberLeftServo = new Servo(Constants.climberLeftServoID);
    //climberRightServo = new Servo(Constants.climberRightServoID);

    leftState = false;
    rightState = false;
  }

  public void climbUp(double speed) {
    leftClimbMotor.set(speed);
    rightClimbMotor.set(speed);
/* 
    if (leftStopCurrent >= 12) {
      leftClimbMotor.set(0);
    }
    if (rightStopCurrent >= 12) {
      rightClimbMotor.set(0);
    }
    */
  }

  public void climbDown() {
    leftClimbMotor.set(Constants.climberDownSpeed);
    rightClimbMotor.set(Constants.climberDownSpeed);
/* 
    if (leftCurrent >= 12) {
      leftClimbMotor.set(0);
      leftState = true;
    }
    if (rightCurrent >= 12) {
      rightClimbMotor.set(0);
      rightState = true;
    }
*/
    

  }

  public void finalClimb() {
    //climberLeftServo.set(180);
    //climberRightServo.set(180);
    //leftClimbMotor.set(Constants.climberDownSpeed);
    //rightClimbMotor.set(Constants.climberDownSpeed);
  }

  public void stopClimb() {
    leftClimbMotor.set(0);
    rightClimbMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climber Left Current", leftClimbMotor.getOutputCurrent());
    SmartDashboard.putNumber("Climber Right Current", rightClimbMotor.getOutputCurrent());
  }
}
