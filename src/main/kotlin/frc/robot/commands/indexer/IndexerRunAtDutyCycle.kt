package frc.robot.commands.indexer

import edu.wpi.first.wpilibj2.command.CommandBase
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.subsystems.Indexer

// Run indexer at duty cycle

class IndexerRunAtDutyCycle(dutyCycleConfig: DutyCycleConfig = DutyCycleConfig(0.4)) : CommandBase() {
    val mDutyCycleConfig = dutyCycleConfig

    init {
        addRequirements(Indexer)
    }

    override fun initialize() {
        if (!Indexer.isBallTop) {
            Indexer.control(ControlMode.DutyCycle, mDutyCycleConfig)
        } else {
            end(true)
        }
    }

    override fun isFinished(): Boolean {
        return Indexer.isBallTop
    }

    override fun end(interrupted: Boolean) {
        Indexer.control(ControlMode.Disabled)
    }
}
