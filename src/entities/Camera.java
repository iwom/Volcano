package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private float CAMERA_SPEED;
    private float YAW_SPEED;
    private float PITCH_SPEED;
    private Vector3f position = new Vector3f(0,0.6f,0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
        YAW_SPEED = 0.55f;
        CAMERA_SPEED = 0.05f;
        PITCH_SPEED = 0.55f;
    }

    public void move() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            //position.z -= 0.04f;
            position.x += Math.sin(Math.toRadians(yaw)) * CAMERA_SPEED;
            position.z -= Math.cos(Math.toRadians(yaw)) * CAMERA_SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.x -= Math.sin(Math.toRadians(yaw)) * CAMERA_SPEED;
            position.z += Math.cos(Math.toRadians(yaw)) * CAMERA_SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += Math.sin(Math.toRadians(yaw + 90)) * CAMERA_SPEED;
            position.z -= Math.cos(Math.toRadians(yaw + 90)) * CAMERA_SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x += Math.sin(Math.toRadians(yaw - 90)) * CAMERA_SPEED;
            position.z -= Math.cos(Math.toRadians(yaw - 90)) * CAMERA_SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            position.y += CAMERA_SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
            if(position.y > 0.3f) {
                position.y -= CAMERA_SPEED;
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            yaw -= YAW_SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            yaw += YAW_SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            pitch -= PITCH_SPEED;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            pitch += PITCH_SPEED;
        }

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.1f;

    }

    private void caluculatePitch() {
        if(Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * 0.1f;
        }
    }
}
