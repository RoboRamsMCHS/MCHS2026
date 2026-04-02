package frc.robot.commands;

import frc.robot.subsystems.ServoSS;




import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class Agitate extends Command {

    private final ServoSS servoSubsystem;
    private final Timer timer = new Timer();
    private boolean goingTo180; // tracks which direction we're heading

    public Agitate(ServoSS servoSubsystem) {
        this.servoSubsystem = servoSubsystem;
        addRequirements(servoSubsystem);
    }

    @Override
    public void initialize() {
        
        timer.reset();
        timer.start();
        // reset and start the timer here
        // set goingTo180 to true so it starts by going to 180
    }

    @Override
   public void execute() {
    if (timer.get() >= 0.5) {
        servoSubsystem.setAngle(goingTo180 ? 180 : 0); // pick angle based on state
        goingTo180 = !goingTo180;                       // flip for next time
        timer.reset();                                 
    }
}

    @Override
    public boolean isFinished() {
        return false; // runs until interrupted
    }

    @Override
    public void end(boolean interrupted) {
        // optionally park the servo at a neutral position like 90
    }
}