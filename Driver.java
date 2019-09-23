/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CircleIntersect;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author ianmahoney
 */
public class Driver extends Canvas {

    static JFrame frame;
    int centerX = 0, centerY = 0, radius = 20; //location of the circle
    int x1 = (int) (Math.random() * 50) - 25, y1 = (int) (Math.random() * 50) - 25, 
            x2 = (int) (Math.random() * 50) - 25, y2 = (int) (Math.random() * 50) - 25; //randomly creates a line

    double xI1 = 0, xI2 = 0, yI1 = 0, yI2 = 0; //the endpoints of the line enclosed within the circle (if any)

    public Driver() {
        circleIntersect(centerX, centerY, radius, x1, y1, x2, y2);
        repaint();
    }

    public void paint(Graphics g) {

        g.setFont(new Font("Courier", Font.PLAIN, 7));
        
        for (int x = 0; x < frame.getWidth(); x += 10) { //these for loops create and number the x and y axes 
            g.setColor(Color.GRAY);
            g.drawLine(x, 0, x, frame.getHeight());

            for (int y = 0; y < frame.getWidth(); y += 10) {
                g.setColor(Color.GRAY);
                g.drawLine(0, y, frame.getWidth(), y);
                
                g.setColor(Color.BLACK);
                
                if (y == frame.getHeight() / 2 + (frame.getHeight() / 2 % 10)) {
                    g.drawString(String.valueOf((x - frame.getWidth() / 2) / 10), x, y + 7);
                }

                if (x == frame.getWidth() / 2 + (frame.getWidth() / 2 % 10)) {
                    g.drawString(String.valueOf((-y - frame.getHeight() / 2) / 10), x, y);
                }

            }
        }
        //draw axes
        g.setColor(Color.BLUE);
        g.drawLine(0, frame.getHeight() / 2 + (frame.getHeight() / 2 % 10) - 1, frame.getWidth(), frame.getHeight() / 2 + (frame.getHeight() / 2 % 10) - 1);
        g.drawLine(frame.getWidth() / 2 + (frame.getWidth() / 2 % 10) - 1, 0, frame.getWidth() / 2 + (frame.getWidth() / 2 % 10) - 1, frame.getHeight());
        
        //draw circle
        g.setColor(Color.RED);
        g.drawArc((centerX - radius) * 10 + frame.getWidth() / 2, (centerY - radius) * 10 + frame.getHeight() / 2, radius * 20, radius * 20, 0, 360);
        
        //draw original line
        g.drawLine(x1 * 10 + frame.getWidth() / 2, -y1 * 10 + frame.getHeight() / 2, x2 * 10 + frame.getWidth() / 2, -y2 * 10 + frame.getHeight() / 2);
        
        //draw enclosed line
        g.setColor(Color.YELLOW);
        g.drawLine((int) Math.round(xI1 * 10) + frame.getWidth() / 2, -(int) Math.round(yI1 * 10) + frame.getHeight() / 2, (int) Math.round(xI2 * 10) + frame.getWidth() / 2, -(int) Math.round(yI2) * 10 + frame.getHeight() / 2);
    }

    public void circleIntersect(int h, int k, int r, int x1, int y1, int x2, int y2) {
        
        
        if (x1 > x2) {
            int temp = x2;
            x2 = x1;
            x1 = temp;
            temp = y2;
            y2 = y1;
            y1 = temp;
        }
        
        //e1 and e2 represent whether end 1 or end 2 are enclosed within the circle
        boolean e1 = false, e2 = false;
        
        //lA, lB, and lC represent the coeficients of the equation of a line
        int lA = y1 - y2;
        int lB = x2 - x1;
        int lC = 1 * (x1 * y2 - x2 * y1);
        
        System.out.println(x1 + ", " + y1 + ", " + x2 + ", " + y2);
        //System.out.println(lA + ", " + lB + ", " + lC);
        
        //t represents the distance from the closest point on the line to the center of the circle
        double t = Math.abs(lA * h + lB * k + lC) / (Math.sqrt(lA * lA + lB * lB));
        //System.out.println(t);
        
        //if the infinite line crosses the circle
        if (t < r) {
            
            //cA, cB, cC are the coeficients for the equation of the circle
            int cA = -2 * h;
            int cB = -2 * k;
            int cC = h * h + k * k - r * r;
            
            //qA, aB, qC are the coeficients for the quadratic equation
            int qA = 1 + (lA * lA) / (lB * lB);
            int qB = cA + ((2 * lC * lA) / (lB * lB)) - (lA * cB / lB);
            int qC = ((lC * lC) / (lB * lB)) - (cB * lC / lB) + cC;
            
            xI1 = (-qB + Math.sqrt(qB * qB - 4 * qA * qC)) / (2 * qA);
            xI2 = (-qB - Math.sqrt(qB * qB - 4 * qA * qC)) / (2 * qA);

            yI1 = (-lA * xI1 - lC) / lB;
            yI2 = (-lA * xI2 - lC) / lB;

            if (xI1 > xI2) {
                double temp = xI2;
                xI2 = xI1;
                xI1 = temp;
                temp = yI2;
                yI2 = yI1;
                yI1 = temp;
            }
            
            // if both endpoints are outside the circle
            if (((!(xI1 > x1 && xI1 < x2)) && ((Math.sqrt(Math.pow(x1 - h, 2)) + Math.sqrt(Math.pow(y1 - k, 2))) > r)) && ((!(xI2 > x1 && xI2 < x2)) && ((Math.sqrt(Math.pow(x2 - h, 2)) + Math.sqrt(Math.pow(y2 - k, 2))) > r))) {
                System.out.println("False");
                return;
            }

            // if either of the endpoints are inside the circle
            if ((Math.sqrt(Math.pow(x1 - h, 2) + Math.pow(y1 - k, 2))) < r) {
                xI1 = x1;
                yI1 = y1;
            }
            if ((Math.sqrt(Math.pow(x2 - h, 2) + Math.pow(y2 - k, 2))) < r) {
                xI2 = x2;
                yI2 = y2;
            }

            System.out.println(xI1 + ", " + yI1 + ", " + xI2 + ", " + yI2);
        
        } else if (t == r) {
            System.out.println("Tangent");
        } else {
            System.out.println("False");
        }

    }

    public static void main(String args[]) {
        frame = new JFrame("Ray Casting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.add(new Driver(), BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

}
