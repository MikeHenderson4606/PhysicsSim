import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View {
    private JFrame frame;
    
    public View(int width, int height) {
        this.frame = new JFrame();

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(width, height);
    }

    public ParticlePanel addMotionParticle() {
        ParticlePanel panel = new ParticlePanel();
        this.frame.add(panel);

        this.frame.pack();
        this.frame.setVisible(true);

        return panel;
    }
}
