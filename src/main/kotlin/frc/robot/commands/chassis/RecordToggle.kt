package frc.robot.commands.chassis

import edu.wpi.first.wpilibj2.command.InstantCommand
import frc.robot.fusion.motion.InputRecording.isRecording
import frc.robot.subsystems.Chassis

class RecordToggle: InstantCommand() {

    init {
        addRequirements(Chassis)
    }

    override fun initialize() {
        if (isRecording) {
            println("No longer recording")
            isRecording = false
        } else {
            println("Recording")
            isRecording = true
        }
    }

}