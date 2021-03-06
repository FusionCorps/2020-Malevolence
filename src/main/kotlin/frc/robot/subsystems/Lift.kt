package frc.robot.subsystems

import com.ctre.phoenix.motorcontrol.InvertType
import com.revrobotics.CANSparkMaxLowLevel
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import frc.robot.Constants
import frc.robot.fusion.motion.AssistedMotionConfig
import frc.robot.fusion.motion.ControlMode
import frc.robot.fusion.motion.DutyCycleConfig
import frc.robot.fusion.motion.FCANSparkMax
import frc.robot.fusion.motion.FPIDConfig
import frc.robot.fusion.motion.FTalonFX
import frc.robot.fusion.motion.MotionCharacteristics
import frc.robot.fusion.motion.MotionConfig
import frc.robot.fusion.motion.MotorID
import frc.robot.fusion.motion.MotorModel
import frc.robot.fusion.motion.VelocityConfig
import java.lang.Math.atan
import java.lang.Math.sin

object Lift : SubsystemBase() { // Important note: Spark Max Encoders count 4096 ticks per rev by default
    private val talonSRXExtend = FTalonFX(MotorID(Constants.Lift.ID_TALONSRX_EXTEND, "talonSRXExtend", MotorModel.TalonSRX)).apply { // Higher speed motor
        setInverted(InvertType.InvertMotorOutput)

        control(ControlMode.Disabled, FPIDConfig())

        selectedSensorPosition = 0
    }

    private val sparkMaxRetract = FCANSparkMax(
        MotorID(
            Constants.Lift.ID_SPARKMAX_RETRACT,
            "sparkMaxRetract", MotorModel.CANSparkMax
        ),
        CANSparkMaxLowLevel.MotorType.kBrushless
    ).apply {
        control(AssistedMotionConfig(293), VelocityConfig(586), FPIDConfig(0.5, allowedError = 15), DutyCycleConfig(0.8))
        inverted = true
    }

    init {
        Shuffleboard.getTab("Lift").add(talonSRXExtend)
        Shuffleboard.getTab("Lift").add(this)
        Shuffleboard.getTab("Lift").add(sparkMaxRetract)
    }

    val extendMotionCharacteristics: MotionCharacteristics get() = talonSRXExtend.motionCharacteristics

    fun extendControl(vararg config: MotionConfig) {
        talonSRXExtend.control(*config)
    }

    val extendVelocity: Double get() = talonSRXExtend.selectedSensorVelocity.toDouble()
    val retractVelocity: Double get() = sparkMaxRetract.get()

    fun retractControl(vararg config: MotionConfig) {
        sparkMaxRetract.control(*config)
    }

    fun locationCalculator(reading1: Double, reading2: Double, m_6672: Int, m_1: Int, m_2: Int, m_bar: Int, delta_sensor: Double): Double {
        val alpha = atan(0.66 / 1.41)
        val theta = atan((reading1 - reading2) / delta_sensor)

        val distance = (0.66 * (reading1 - reading2) * (m_1 + m_2 + m_bar) / (m_6672 * delta_sensor))
        val height = 2.83 - 1.56 * sin(alpha + theta) + (1.41 + distance) * sin(theta)
        return height
    }
}
