// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import com.ctre.phoenix6.configs.CANcoderConfiguration;

/** Add your docs here. */
public class CTREConfigs {
    public static CANcoderConfiguration swerveCanCoderConfig;

    public CTREConfigs() {
      swerveCanCoderConfig = new CANcoderConfiguration();
  
      /* Swerve CANCoder Configuration */
      /* 
      swerveCanCoderConfig.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
      swerveCanCoderConfig.sensorDirection = false;
      swerveCanCoderConfig.initializationStrategy =
          SensorInitializationStrategy.BootToAbsolutePosition;
      swerveCanCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond;
      */
    }
}
