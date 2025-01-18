// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  /** Creates a new Vision. */
  NetworkTable table;
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;
  NetworkTableEntry priority_id;
  public Vision() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    priority_id = table.getEntry("priorityid");
  }

  //sets which target to focus on for ligning up
  public void set_target(double id) {
    priority_id.setDouble(id);
  }

  public double x_offset() {
    return tx.getDouble(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //put x_offset
    SmartDashboard.putNumber("x offset", x_offset());
    //gets target. tbh don't think this works
    SmartDashboard.putNumber("Priority id", priority_id.getDouble(0.5));

    
  }
}
