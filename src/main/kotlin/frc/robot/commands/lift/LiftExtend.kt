package frc.robot.commands.lift

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode
import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.subsystems.Lift
import mu.KotlinLogging

class LiftExtend(lift: Lift) : CommandBase() { // Run the extension motor at 10%
    private val mLift = lift

    init {
        addRequirements(mLift)
    }

    override fun initialize() {
        mLift.setExtend(TalonSRXControlMode.PercentOutput, 0.4)
    }

    override fun execute() {
        KotlinLogging.logger("LiftExtend").info { "Running" }
    }

    override fun end(interrupted: Boolean) {
        mLift.extendStop()
    }
}