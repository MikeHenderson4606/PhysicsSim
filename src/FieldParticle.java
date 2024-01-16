import java.util.Objects;

// Represents a particle influenced by a field
public class FieldParticle implements Particle {
    private int xpos;
    private int ypos;
    private double xvel;
    private double yvel;
    private final int magnitude;
    // For sign, positive is true, negative is false
    private final Boolean sign;


    public FieldParticle(int xpos, int ypos, double xvel, double yvel, int magnitude, Boolean sign) {
        Objects.requireNonNull(xpos);
        Objects.requireNonNull(ypos);
        Objects.requireNonNull(xvel);
        Objects.requireNonNull(yvel);
        Objects.requireNonNull(magnitude);
        Objects.requireNonNull(sign);

        this.xpos = xpos;
        this.ypos = ypos;
        this.xvel = xvel;
        this.yvel = yvel;
        this.magnitude = magnitude;
        this.sign = sign;
    }

    @Override
    public void setPos(int x, int y) throws Exception {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);

        this.xpos = x;
        this.ypos = y;
    }

    @Override
    public void setXVel(double newxvel) throws Exception {
        Objects.requireNonNull(newxvel);

        this.xvel = newxvel;
    }

    @Override
    public void setYVel(double newyvel) throws Exception {
        Objects.requireNonNull(newyvel);

        this.yvel = newyvel;
    }

    @Override
    public int getXPos() {
        return this.xpos;
    }

    @Override
    public int getYPos() {
        return this.ypos;
    }

    @Override
    public double getXVel() {
        return this.xvel;
    }

    @Override
    public double getYVel() {
        return this.yvel;
    }

    public int getMagnitude() {
        return this.magnitude;
    }

    public Boolean getSign() {
        return this.sign;
    }
}
