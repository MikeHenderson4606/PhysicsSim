import java.util.Objects;

// Represents an electric field
public class EField implements Field{
    private int magnitude;
    // For sign, positive is true, negative is false
    private Boolean sign;
    private int xpos;
    private int ypos;

    public EField(int magnitude, Boolean sign, int xpos, int ypos) throws Exception {
        Objects.requireNonNull(magnitude);
        Objects.requireNonNull(sign);
        Objects.requireNonNull(xpos);
        Objects.requireNonNull(ypos);

        this.magnitude = magnitude;
        this.sign = sign;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public int getXPos() {
        return this.xpos;
    }

    public int getYPos() {
        return this.ypos;
    }

    public int getMagnitude() {
        return this.magnitude;
    }

    public Boolean getSign() {
        return this.sign;
    }

    public void changeXPosBy(int x) {
        this.xpos += x;
    }

    public void changeYPosBy(int y) {
        this.ypos += y;
    }
}
