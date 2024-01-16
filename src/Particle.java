
public interface Particle {

    // Sets the position of the particle
    void setPos(int x, int y) throws Exception;

    // Sets the X velocity of the particle
    void setXVel(double newxvel) throws Exception;

    // Sets the Y velocity of the particle
    void setYVel(double newyvel) throws Exception;

    // Gets the X position of the particle
    int getXPos();

    // Gets the y position of the particle
    int getYPos();

    // Gets the x velocity of the particle
    double getXVel();

    // Gets the y velocity of the particle
    double getYVel();
}