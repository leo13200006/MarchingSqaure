package com.java2d.pracs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarchingSquares extends JPanel implements ActionListener {

    public Timer timer = new Timer(10, this);
    public float[][] field;
    public int rez = 5;
    public int cols, rows;
    public OpenSimplexNoise noise;
    float increment = 0.1F;
    float zoff = 0;

    MarchingSquares() {
        int width = 1200;
        int height = 800;
        this.setSize(new Dimension(width, height));
        cols = 1 + width / rez;
        rows = 1 + height / rez;
        field = new float[cols][rows];
        noise = new OpenSimplexNoise();
    }

    @Override
    public void paintComponent(Graphics g) {

        Runnable runnable = () -> {
            timer.start();
            super.paintComponent(g);
            this.setBackground(Color.BLACK);

            float xoff = 0;
            float yoff = 0;
            for (int i = 0; i < cols; i++) {
                xoff += increment;
                yoff = 0;
                for (int j = 0; j < rows; j++) {
                    field[i][j] = noise.noise(xoff, yoff, zoff);
                    yoff += increment;
                }
            }
            zoff += 0.01F;

            Graphics2D graphics2D = (Graphics2D) g;

            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    float color = field[i][j];
                    graphics2D.setColor(new Color(Math.round(color * 255), Math.round(color * 255), Math.round(color * 255)));
                    graphics2D.setStroke(new BasicStroke(0));
                    graphics2D.fillRect(i*rez, j*rez, rez, rez);
                }
            }

            for (int i = 0; i < cols-1; i++) {
                for (int j = 0; j < rows-1; j++) {
                    int x = i * rez;
                    int y = j * rez;

                    Point a = new Point((int) Math.round(x + rez * 0.5 + (field[i][j] - field[i+1][j]) - 1.4), y);
                    Point b = new Point(x + rez, (int) Math.round(y + rez * 0.5 + (field[i+1][j] - field[i+1][j+1]) - 1.4));
                    Point c = new Point((int) Math.round(x + rez * 0.5 + (field[i+1][j+1] - field[i][j+1]) - 1.4), y + rez);
                    Point d = new Point(x, (int) Math.round(y + rez * 0.5 + (field[i][j+1] - field[i][j]) - 1.4));

                    int state = getState(
                            field[i][j] > 0.5F ? 0 : 1,
                            field[i+1][j] > 0.5F ? 0 : 1,
                            field[i+1][j+1] > 0.5F ? 0 : 1,
                            field[i][j+1] > 0.5F ? 0 : 1
                    );

                    graphics2D.setColor(Color.WHITE);
                    graphics2D.setStroke(new BasicStroke(1));

                    switch(state) {
                        case 1:
                        case 14:
                            drawLine(graphics2D, d, c);
                            break;
                        case 2:
                        case 13:
                            drawLine(graphics2D, b, c);
                            break;
                        case 3:
                        case 12:
                            drawLine(graphics2D, b, d);
                            break;
                        case 4:
                        case 11:
                            drawLine(graphics2D, a, b);
                            break;
                        case 5:
                            drawLine(graphics2D, a, d);
                            drawLine(graphics2D, b, c);
                            break;
                        case 6:
                        case 9:
                            drawLine(graphics2D, a, c);
                            break;
                        case 7:
                        case 8:
                            drawLine(graphics2D, a, d);
                            break;
                        case 10:
                            drawLine(graphics2D, a, b);
                            drawLine(graphics2D, d, c);
                            break;
                    }
                }
            }
        };

        runnable.run();
        Toolkit.getDefaultToolkit().sync();
    }


    public void drawLine(Graphics2D graphics2D, Point a, Point b) {
        graphics2D.drawLine(a.x, a.y, b.x, b.y);
    }

    public int getState(int a, int b, int c, int d) {
        return a * 8 + b * 4 + c * 2 + d;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mainFrame = new JFrame();
                MarchingSquares marchingSquares = new MarchingSquares();
                mainFrame.setLayout(new BorderLayout());
                mainFrame.setTitle("Marching Square Algorithm");
                mainFrame.add(marchingSquares, BorderLayout.CENTER);
                mainFrame.setSize(new Dimension(1200, 800));
                mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                mainFrame.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.repaint();
    }
}
