package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.InvertType
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.commands.intake.IntakeRunJoystick
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.fusion.motion.FTalonFX
import frc.robot.fusion.motion.MotionCharacteristics
import frc.robot.fusion.motion.MotionConfig
import frc.robot.fusion.motion.MotorID
import frc.robot.fusion.motion.MotorModel
import mu.KotlinLogging

object Intake : SubsystemBase() {
    private val logger = KotlinLogging.logger("Intake")

    // Check if the interface is correct

    // object.apply{thing1() thing2() thing() } = object.thing1()  object.thing2()  object.thing3()

    private val victorSPXIntake = FTalonFX(MotorID(Constants.Intake.ID_VICTORSPX, "victorSPXIntake", MotorModel.VictorSPX)).apply {
        configFactoryDefault()

        setInverted(InvertType.InvertMotorOutput)

        control(DutyCycleConfig(Constants.Intake.TARGET_PERCENT))
    }

    init {
        defaultCommand = IntakeRunJoystick()

        Shuffleboard.getTab("Intake").add(victorSPXIntake)
        Shuffleboard.getTab("Intake").add(this)
    }

    val motionCharacteristics: MotionCharacteristics get() = victorSPXIntake.motionCharacteristics

    fun control(vararg config: MotionConfig) {
        victorSPXIntake.control(*config)
    }
}
