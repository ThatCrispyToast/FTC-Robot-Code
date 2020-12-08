package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Tank")
public class Tank extends OpMode
{
    // Declare OpMode members.
    float conveyorSpeed = 50.0;

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor topLeftDrive = null;
    private DcMotor topRightDrive = null;
    private DcMotor bottomLeftDrive = null;
    private DcMotor bottomRightDrive = null;
    private DcMotor conveyor = null;
    private DcMotor leftIntake = null;
    private DcMotor rightIntake = null;
    private DcMotor Shooter = null;
    private Servo leftIntakeServo = null;
    private Servo rightIntakeServo = null;
    // static final double HDHEX_ULTRAPLANETARY_TICK_COUNT = 28 / 2.89;

    // Code to run ONCE when the driver hits INIT
    @Override
    public void init() {

        // Initialize the hardware variables.
        topLeftDrive  = hardwareMap.get(DcMotor.class, "front_left_motor");
        topRightDrive = hardwareMap.get(DcMotor.class, "front_right_motor");
        bottomLeftDrive  = hardwareMap.get(DcMotor.class, "back_left_motor");
        bottomRightDrive = hardwareMap.get(DcMotor.class, "back_right_motor");
        conveyor  = hardwareMap.get(DcMotor.class, "conveyor");
        leftIntake  = hardwareMap.get(DcMotor.class, "left_intake_rot");
        rightIntake  = hardwareMap.get(DcMotor.class, "right_intake_rot");
        leftIntakeServo  = hardwareMap.get(Servo.class, "left_intake");
        rightIntakeServo  = hardwareMap.get(Servo.class, "right_intake");
        Shooter = hardwareMap.get(DcMotor.class, "shooter");

        // Reverse the motors that face towards the back of the robot
        topLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        bottomLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        topRightDrive.setDirection(DcMotor.Direction.FORWARD);
        bottomRightDrive.setDirection(DcMotor.Direction.FORWARD);
        
        // Run Appropriate Motors w/ Encoders

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    // Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {
    }

    // Code to run ONCE when the driver hits PLAY
    @Override
    public void start() {
        runtime.reset();
    }

    // Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {
        // Setup a variable for each drive wheel to save power level for telemetry
        double topLeftPower;
        double topRightPower;
        double bottomLeftPower;
        double bottomRightPower;
        double conveyorPower;
        double leftIntakePower;
        double rightIntakePower;
        double shooterPower;
        
        // Gamepad 1 Controls
        // Drivetrain (Tank w/ Strafing)
        bottomLeftPower = -gamepad1.left_stick_y + -gamepad1.left_stick_x/2 + -gamepad1.right_stick_x/2;
        topLeftPower  = -gamepad1.left_stick_y + gamepad1.left_stick_x/2 + gamepad1.right_stick_x/2;
        bottomRightPower = -gamepad1.right_stick_y + gamepad1.left_stick_x/2 + gamepad1.right_stick_x/2;
        topRightPower = -gamepad1.right_stick_y + -gamepad1.left_stick_x/2 + -gamepad1.right_stick_x/2;
        
        // bottomLeftPower = -gamepad1.left_stick_y + gamepad1.right_trigger + -gamepad1.left_trigger;
        // topLeftPower  = -gamepad1.left_stick_y + gamepad1.left_trigger + -gamepad1.right_trigger;
        // bottomRightPower = -gamepad1.right_stick_y + gamepad1.left_trigger + -gamepad1.right_trigger;
        // topRightPower = -gamepad1.right_stick_y + gamepad1.right_trigger + -gamepad1.left_trigger;
        
        // bottomLeftPower = -gamepad1.left_stick_y;
        // topLeftPower  = -gamepad1.left_stick_y;
        // bottomRightPower = -gamepad1.right_stick_y;
        // topRightPower = -gamepad1.right_stick_y;
        
        
        // Gamepad 1 Controls
        // Conveyor
        if (gamepad1.y) {
            conveyorPower = conveyorSpeed / 100;
        } else if (gamepad1.a) {
            conveyorPower = -conveyorSpeed / 100;
        } else {
            conveyorPower = 0.0;
        }
        
        // Intake
        if (gamepad1.left_bumper) {
            leftIntakePower = 1.0;
            rightIntakePower = -1.0;
        } else if (gamepad1.right_bumper) {
            leftIntakePower = -1.0;
            rightIntakePower = 1.0;
        } else {
            leftIntakePower = 0.0;
            rightIntakePower = 0.0;
        }
        
        // Intake Servos
        if (gamepad1.dpad_up) {
            leftIntakeServo.setPosition(1.0);
            rightIntakeServo.setPosition(0.0);
            
        } else if (gamepad1.dpad_down) {
            leftIntakeServo.setPosition(0.0);
            rightIntakeServo.setPosition(1.0);
        }
        
        // Gamepad 2 Controls
        
        if (gamepad2.left_bumper) {
            Shooter.setPower(-1.0);
        } else if (gamepad2.right_bumper) {
            Shooter.setPower(1.0);
        } else {
            Shooter.setPower(0);
            
        }

        // Caps Powers at 1.0 or -1.0 (There's gotta be a better way to do this cause looking at this block of single-line if statements makes me want to vomit)
        if (topLeftPower > 1.0) topLeftPower = 1.0;
        if (topLeftPower < -1.0) topLeftPower = -1.0;
        if (bottomRightPower > 1.0) bottomRightPower = 1.0;
        if (bottomRightPower < -1.0) bottomRightPower = -1.0;
        if (bottomLeftPower > 1.0) bottomLeftPower = 1.0;
        if (bottomLeftPower < -1.0) bottomLeftPower = -1.0;
        if (topRightPower > 1.0) topRightPower = 1.0;
        if (topRightPower < -1.0) topRightPower = -1.0;

        // Send calculated power to wheels
        topLeftDrive.setPower(topLeftPower);
        topRightDrive.setPower(topRightPower);
        bottomLeftDrive.setPower(bottomLeftPower);
        bottomRightDrive.setPower(bottomRightPower);
        conveyor.setPower(conveyorPower);
        leftIntake.setPower(leftIntakePower);
        rightIntake.setPower(rightIntakePower);
        

        // Show the elapsed game time, wheel powers, and control award shit that ill add later
        telemetry.addData("Status", "Running, Run Time: " + runtime.toString());
        telemetry.addData("Motors", "top left (%.2f) top right (%.2f)\nbottom left (%.2f) bottom right (%.2f)", topLeftPower, topRightPower, bottomLeftPower, bottomRightPower);
        telemetry.addData("Status", "Running...");
        telemetry.update();
    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop() {
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

}
