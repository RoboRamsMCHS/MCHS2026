package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrainSS;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrainSS;
import frc.robot.subsystems.ShooterSS;




public class shootWhileMoving extends Command {
    private final DriveTrainSS dt;
    private final ShooterSS sht;


    // Constructor
    public shootWhileMoving(DriveTrainSS subsystem, ShooterSS subsystem2) {
        this.dt = subsystem;
        addRequirements(subsystem);
        this.sht = subsystem2;
        addRequirements(subsystem2);

    }

    public void initialize() {
        System.out.println("shootWhileMoving test.");
    }

    public void execute() {


        System.out.println("Executing.");


        dt.setMotorSpeeds(0.5, 0.5);
        sht.setShooterSpeed(1);
    }


    public void end(boolean interrupted) {
    dt.setMotorSpeeds(0, 0);
    sht.setShooterSpeed(0);
        }



    public boolean isFinished() {
        return false;

    }


    

}