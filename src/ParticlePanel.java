import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Objects;
import java.util.PrimitiveIterator.OfDouble;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ParticlePanel extends JPanel {
    int WIDTH = 500;
    int HEIGHT = 500;
    int STARTANGLE = 0;
    int ARCANGLE = 360;
    private List<MotionParticle> MotionParticles = new ArrayList<MotionParticle>();
    private List<EField> fields = new ArrayList<EField>();

    public ParticlePanel() {
    }

    public void addMotionParticle(MotionParticle newMotionParticle) throws Exception {
        Objects.requireNonNull(newMotionParticle);
        MotionParticles.add(newMotionParticle);
    }

    public void addField(EField newField) throws Exception {
        Objects.requireNonNull(newField);
        fields.add(newField);
    }

    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Convert to graphics 2D
        Graphics2D g2d = (Graphics2D) g;
        int [][] arrowCoords = this.getArrowCoords();
        // For each field in the field list, draw lines from the field to every other field

        // Get the force vector for this field on each arrow
        System.out.println("Arrow coords");
        for (int [] coord: arrowCoords) {
            int x = coord[0];
            int y = coord[1];

            double direction = this.getArrowDirection(this.fields, x, y, g2d);

            this.drawArrowFromCoords(g2d, coord, direction);
        }
        for (MotionParticle currPart : this.MotionParticles) {
            g.setColor(currPart.getColor());
            g.fillArc(currPart.getXPos(), currPart.getYPos(), currPart.getRadius(), currPart.getRadius(), STARTANGLE, ARCANGLE);
        }
    }  

    public double getArrowDirection(List<EField> fields, int xarrow, int yarrow, Graphics2D g) {
        List<Double> xcomponents = new ArrayList<Double>();
        List<Double> ycomponents = new ArrayList<Double>();

        for (EField field: fields) {
            // How many lines to draw given by the radial angle of the line about the x axis
            double mag = field.getMagnitude();

            Boolean sign = field.getSign();
            int xpos = field.getXPos();
            int ypos = field.getYPos();
            double currDirection = this.getDirectionOfOneField(xpos, ypos, xarrow, yarrow, sign);
            
            // Want to get the individual x and y components of the vector, and add them together
            double dist = Math.sqrt(Math.pow(xarrow - xpos, 2) + Math.pow(yarrow - ypos, 2));
            double relStrength = 0;
            if (dist == 0) {
                relStrength = mag;
            } else {
                relStrength = (mag / dist);
            }
            // Magnitude of the vector is the relative strength
            // The x component is the relative strength times the cos(direction) and the y is the sin
            double xcomponent = relStrength * Math.cos(currDirection);
            double ycomponent = relStrength * Math.sin(currDirection);
            xcomponents.add(xcomponent);
            ycomponents.add(ycomponent);

            // Draw the center of the field with a circle
            // Positive is red, negative is blue
            if (sign) {
                g.setColor(new Color(255, 0, 0));
            }
            else {
                g.setColor(new Color(0, 0, 255));
            }
            g.fillOval(xpos - 6, ypos - 6, 12, 12);
        }

        double totalX = 0;
        double totalY = 0;
        for (int i = 0; i < xcomponents.size(); i++) {
            totalX = totalX + xcomponents.get(i);
            totalY = totalY + ycomponents.get(i);
        }
        double finalDirection = 0;
        if (totalX != 0) {
            finalDirection = Math.atan(totalY / totalX);
        }
        else {
            finalDirection = 0;
        }
        
        System.out.println(finalDirection);

        return finalDirection;
    }

    // Gets the direction of one field on a particular arrow
    public double getDirectionOfOneField(int xpos, int ypos, int x, int y, boolean sign) {
        double direction = 0;

        if (sign) { // Positive field
            if ((x - xpos) > 0 && (y - ypos) < 0) { // Top right quadrant
                //direction = (Math.PI / 2) - Math.atan((double) (y - ypos) / (xpos - x));
                direction = (3 * Math.PI / 2) + Math.atan((double) (y - ypos) / (x - xpos));
            } else if ((x - xpos < 0) && (y - ypos) < 0) { // Top left quadrant
                direction = (3 * Math.PI / 2) + Math.atan((double) (y - ypos) / (x - xpos));
            } else if ((x - xpos) > 0 && (y - ypos) > 0) { // Bottom right quadrant
                direction = (Math.PI / 2) - Math.atan((double) (y - ypos) / (xpos - x));
                //System.out.println(direction);
            } else if ((x - xpos) < 0 && (y - ypos) > 0) { // Bottom left quadrant
                direction = (3 * Math.PI / 2) + Math.atan((double) (y - ypos) / (x - xpos));
            }
            else if ((x - xpos) < 0 && (y - ypos) == 0) { // On the left horizontal axis
                direction = -1 * (Math.PI / 2);
            }
            else if ((x - xpos) > 0 && (y - ypos) == 0) { // On the right horizontal axis
                direction = (Math.PI / 2);
            }
            else if ((x - xpos) == 0 && (y - ypos) < 0) { // On the up vertical axis
                direction = 0;
            }
            else if ((x - xpos) == 0 && (y - ypos) > 0) { // On the down vetical axis
                direction = Math.PI;
            }
        }
        else { // Negative field
            if ((x - xpos) > 0 && (y - ypos) < 0) { // Top right quadrant
                direction = (3 * Math.PI / 2) + Math.atan((double) (y - ypos) / (x - xpos));
            } else if ((x - xpos < 0) && (y - ypos) < 0) { // Top left quadrant
                direction = (Math.PI / 2) - Math.atan((double) (y - ypos) / (xpos - x));
            } else if ((x - xpos) > 0 && (y - ypos) > 0) { // Bottom right quadrant
                direction = (3 * Math.PI / 2) + Math.atan((double) (y - ypos) / (x - xpos));
                //System.out.println(direction);
            } else if ((x - xpos) < 0 && (y - ypos) > 0) { // Bottom left quadrant
                direction = (Math.PI / 2) - Math.atan((double) (y - ypos) / (xpos - x));
            }
            else if ((x - xpos) < 0 && (y - ypos) == 0) { // On the left horizontal axis
                direction = (Math.PI / 2);
            }
            else if ((x - xpos) > 0 && (y - ypos) == 0) { // On the right horizontal axis
                direction = -1 * (Math.PI / 2);
            }
            else if ((x - xpos) == 0 && (y - ypos) < 0) { // On the up vertical axis
                direction = Math.PI;
            }
            else if ((x - xpos) == 0 && (y - ypos) > 0) { // On the down vetical axis
                direction = 0;
            }
        }
        return direction;
    }

    // Get the arrow coordinates
    public int [][] getArrowCoords() {
        int currXCoord;
        int currYCoord;
        int [][] coords = new int [625][2];
        int counter = 0;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if ((i % 20 == 0) && (j % 20 == 0)) {
                    currXCoord = i;
                    currYCoord = j;
                    int [] currCoords = { currXCoord, currYCoord };
                    coords[counter] = currCoords;
                    counter++;
                }
            }
        }
        return coords;
    }

    // Fills in an arrow
    public void drawArrowFromCoords(Graphics2D g, int [] coord, double rotationAngle) {
        for (int i = 0; i < coord.length; i++) {
            int x = coord[0];
            int y = coord[1];
            Path2D arrow = this.singleArrowPath(x, y, rotationAngle);
            g.fill(arrow);
        }
    }

    // Draws a path for a single arrow to be rotated
    public Path2D singleArrowPath(int x, int y, double rotationAngle) {
        double x1 = x + (-2 * Math.cos(rotationAngle));
        double y1 = y + (-2 * Math.sin(rotationAngle));

        double x2 = x1 + (7 * Math.sin(rotationAngle));
        double y2 = y1 + (-7 * Math.cos(rotationAngle));

        double x3 = x2 + (-2 * Math.cos(rotationAngle));
        double y3 = y2 + (-2 * Math.sin(rotationAngle));

        double x4 = x + (15 * Math.cos(Math.toRadians(90) - rotationAngle));
        double y4 = y - (15 * Math.sin(Math.toRadians(90) - rotationAngle));

        double x7 = x + (2 * Math.cos(rotationAngle));
        double y7 = y + (2 * Math.sin(rotationAngle));

        double x6 = x7 + (7 * Math.sin(rotationAngle));
        double y6 = y7 + (-7 * Math.cos(rotationAngle));

        double x5 = x6 + (2 * Math.cos(rotationAngle));
        double y5 = y6 + (2 * Math.sin(rotationAngle));

        Path2D singleArrow = new Path2D.Double();
        singleArrow.moveTo(x, y);
        singleArrow.lineTo(x1, y1);
        singleArrow.lineTo(x2, y2);
        singleArrow.lineTo(x3, y3);
        singleArrow.lineTo(x4, y4);
        singleArrow.moveTo(x, y);
        singleArrow.lineTo(x7, y7);
        singleArrow.lineTo(x6, y6);
        singleArrow.lineTo(x5, y5);
        singleArrow.lineTo(x4, y4);
        singleArrow.closePath();

        return singleArrow;
    }
}