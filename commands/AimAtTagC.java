package frc.robot.commands;

import java.util.Optional;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.math.MathUtil;

import frc.robot.subsystems.driveTrainSS;
import frc.robot.subsystems.VisionSubsystem;

public class AimAtTagC extends Command {

    private final DriveTrainSS drive;
    private final VisionSubsystem vision;

    private static final double kP = 0.02;
    private static final double MAX_TURN = 0.5;
    private static final double DEAD_BAND = 1.5;

    public AimAtTagC(DriveTrainSS drive, VisionSubsystem vision) {
        this.drive = drive;
        this.vision = vision;

        addRequirements(drive);
    }

    @Override
    public void execute() {
        Optional<Double> yawOpt = vision.getTargetYaw();

        if (yawOpt.isPresent()) {
            double yaw = yawOpt.get();


            if (Math.abs(yaw) < DEAD_BAND) {
                drive.drive(0,0,0);
                return;
            }

            double turn = -yaw * kP;

            turn = MathUtil.clamp(turn, -MAX_TURN, MAX_TURN);

            drive.drive(0,0, turn);
        }
        else {
            // No target
            drive.drive(0,0,0);
        }
    }
        
        





    @Override
    public boolean isFinished() {
        return false;
    }

    







}