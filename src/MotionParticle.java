import java.awt.Color;
import java.util.Objects;

public class MotionParticle implements Particle{
    private int radius;
    private Color color;
    private int xpos;
    private int ypos;
    private double xvel;
    private double yvel;

    public MotionParticle(int radius, Color color, int xpos, int ypos, double xvel, double yvel) throws Exception {
        Objects.requireNonNull(radius);
        Objects.requireNonNull(color);
        Objects.requireNonNull(xpos);
        Objects.requireNonNull(ypos);
        Objects.requireNonNull(xvel);
        Objects.requireNonNull(yvel);

        this.radius = radius;
        this.color = color;
        this.xpos = xpos;
        this.ypos = ypos;
        this.xvel = xvel;
        this.yvel = yvel;
    }


    public void setPos(int x, int y) throws Exception {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);

        this.xpos = x;
        this.ypos = y;
    }

    public void setXVel(double newxvel) throws Exception{
        Objects.requireNonNull(newxvel);

        this.xvel = newxvel;
    }

    public void changeXVel(double changedBy) throws Exception {
        Objects.requireNonNull(changedBy);

        this.xvel += changedBy;
    }

    public void setYVel(double newyvel) {
        Objects.requireNonNull(newyvel);

        this.yvel = newyvel;
    }

    public void changeYVel(double changedBy) throws Exception {
        Objects.requireNonNull(changedBy);

        this.yvel += changedBy;
    }


    public int getXPos() {
        return this.xpos;
    }

    public int getYPos() {
        return this.ypos;
    }

    public double getXVel() {
        return this.xvel;
    }

    public double getYVel() {
        return this.yvel;
    }

    public int getRadius() {
        return this.radius;
    }

    public Color getColor() {
        return this.color;
    }
}
