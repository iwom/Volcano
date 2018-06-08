package entities;

import game.DisplayManager;
import org.lwjgl.util.vector.Vector3f;
import toolbox.Maths;

public class Light {

    private Vector3f position;
    private Vector3f colour;
    private Vector3f initialColour;
    private float innerTimer;
    private boolean enabled;

    public Light(Vector3f position, Vector3f colour) {
        this(position, colour, -1);
    }

    public Light(Vector3f position, Vector3f colour, float lifeSpan) {
        this.position = position;
        this.colour = colour;
        this.initialColour = colour;
        this.innerTimer = 0.0f;
        this.enabled = true;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.initialColour = colour;
        this.colour = colour;
    }

    public void randomizeColour() {
        if(this.enabled) {
            float red = this.initialColour.getX();
            float green = this.initialColour.getY();
            innerTimer += 0.05f;
            if (innerTimer > 6.28f) {
                innerTimer = 0.00f;
            }
            this.colour.setX(Math.abs(red + ((float) (Math.sin(innerTimer)) / Maths.randomWithRange(475, 525))));
            this.colour.setY(Math.abs(green + ((float) (Math.sin(innerTimer)) / Maths.randomWithRange(425, 575))));
            System.out.println("Color : " + this.colour.toString());
        }

    }

    public void emit (boolean set) {
        if(!set) {
            this.colour = new Vector3f(0,0,0);
        }
        this.enabled = set;
    }


}
