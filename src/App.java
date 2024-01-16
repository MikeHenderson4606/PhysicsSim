import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;

public class App {
    private List<MotionParticle> motionParticles = new ArrayList<MotionParticle>();
    private List<EField> eFields = new ArrayList<EField>();
    private int width;
    private int height;

    public App(int width, int height) throws Exception {
        Objects.requireNonNull(width);
        Objects.requireNonNull(height);

        this.width = width;
        this.height = height;
    }

    public void addMotionParticle(MotionParticle particle) {
        Objects.requireNonNull(particle);

        this.motionParticles.add(particle);
    }

    public void addEField(EField field) {
        Objects.requireNonNull(field);

        this.eFields.add(field);
    }

    private void moveParticleTo(Particle particle, int x, int y) throws Exception {
        double xvel = Math.abs(particle.getXVel());
        double yvel = Math.abs(particle.getYVel());

        //System.out.println("X: " + x);
        //System.out.println("Y: " + y);
        if (x < (this.width - xvel) && x > xvel && y < (this.height - yvel) && y > yvel) {
            //System.out.println("Changing position to " + x + ", " + y);
            particle.setPos(x, y);
        }
        else {
            this.changeDirection(particle, x, y);
        }
    }

    public void moveParticleBy(Particle particle, double x, double y) throws Exception {
        int newx = (int) (particle.getXPos() + x);
        int newy = (int) (particle.getYPos() + y);

        this.moveParticleTo(particle, newx, newy);
    }

    public void changeDirection(Particle particle, int attemptedX, int attemptedY) throws Exception {
        double xvel = particle.getXVel();
        double yvel = particle.getYVel();
        int xpos = particle.getXPos();
        int ypos = particle.getYPos();

        if (xpos == ypos || (xpos == this.width && ypos == 0) || (xpos == 0 && ypos == this.height)) {
            //System.out.println("Inverting X and Y velocity");
            particle.setXVel(xvel * -1);
            particle.setYVel(yvel * -1);
        } else if (attemptedX <= Math.abs(xvel) || attemptedX >= this.width - Math.abs(xvel)) {
            //System.out.println("Inverting X velocity");
            particle.setXVel(xvel * -1);
        } else if (attemptedY <= Math.abs(yvel) || attemptedY >= this.height - Math.abs(yvel)) {
            //System.out.println("Inverting Y velocity");
            particle.setYVel(yvel * -1);
        }

        this.moveParticleBy(particle, particle.getXVel(), particle.getYVel());
    }


    public void run(ParticlePanel panel, View view) throws Exception {
        synchronized (this) {
            while (true) {
                for (MotionParticle motionParticle : this.motionParticles) {
                    double xvel = motionParticle.getXVel();
                    double yvel = motionParticle.getYVel();
                    this.moveParticleBy(motionParticle, xvel, yvel);
                    panel.repaint();
    
                    //System.out.println("X postion:" + particle.getXPos());
                    //System.out.println("Y position:" + particle.getYPos());
    
                    this.wait(3);
                }
            }
        }
        
    }

    public void animateField(ParticlePanel panel, View view) throws Exception {
        synchronized (this) {
            int counter = 0;
            while (true) {
                for (EField field: this.eFields) {
                    int xvel = (int) (3 * Math.cos(Math.toRadians(counter)));
                    int yvel = (int) (3 * Math.sin(Math.toRadians(counter)));
                    field.changeXPosBy(xvel);
                    field.changeYPosBy(yvel);

                    panel.repaint();
                    this.wait(30);
                }
                counter++;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            EField field = new EField(5, true, 200, 200);

            EField field2 = new EField(10, false, 300, 400);

            MotionParticle particle = new MotionParticle(5, new Color(255, 0, 0), 10, 10, 10, 10);
            MotionParticle particle2 = new MotionParticle(5, new Color(0, 255, 0), 250, 250, -9, 4);
            MotionParticle particle3 = new MotionParticle(5, new Color(255, 0, 255), 100, 150, -10, 8);
            MotionParticle particle4 = new MotionParticle(5, new Color(0, 133, 255), 10, 175, -9, 13);
            MotionParticle particle5 = new MotionParticle(5, new Color(133, 0, 250), 400, 300, 12, 6);

            App app = new App(800, 600);
            app.addMotionParticle(particle);
            app.addMotionParticle(particle2);
            app.addMotionParticle(particle3);
            app.addMotionParticle(particle4);
            app.addMotionParticle(particle5);

            //app.addEField(field);

            View view = new View(app.width, app.height);
            ParticlePanel panel = view.addMotionParticle();
            panel.addField(field);
            //panel.addField(field2);
            // panel.addMotionParticle(particle);
            // panel.addMotionParticle(particle2);
            // panel.addMotionParticle(particle3);
            // panel.addMotionParticle(particle4);
            // panel.addMotionParticle(particle5);
            

            app.animateField(panel, view);
        }
        catch (IOException e) {
            System.out.print(e);
        }
    }
}
