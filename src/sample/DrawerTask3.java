package sample;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.awt.Color.BLACK;
import static java.awt.Color.YELLOW;

public class DrawerTask3 extends Task {
    private GraphicsContext gc;
    private int howManyIterations;
    private double varTrue = 0;
    private double varFalse = 0;
    private double varRes = 0;

    public DrawerTask3(GraphicsContext gc, int hmi) {
        this.gc = gc;
        this.howManyIterations = hmi;
    }

    @Override
    protected Object call() throws Exception {
        Random random = new Random();
        Equation equation = new Equation();
        BufferedImage bi= new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
        int interval = howManyIterations/100;
        int xmin = 0;
        int xmax = 150;
        int ymin = 0;
        int ymax = 100;
        int xzmin = -8;
        int xzmax = 0;
        int yzmin = -8;
        int yzmax = 0;
        for(int j = 0; j<4; j++) {
            for (int i = 0; i < howManyIterations/4; i++) {

                if(j==1) {
                    xmin = 150;
                    xmax = 300;
                    ymin = 0;
                    ymax = 100;
                    xzmin = 0;
                    xzmax = 8;
                    yzmin = -8;
                    yzmax = 0;
                }

                if(j==2) {
                    xmin = 0;
                    xmax = 150;
                    ymin = 100;
                    ymax = 200;
                    xzmin = -8;
                    xzmax = 0;
                    yzmin = 0;
                    yzmax = 8;
                }

                if(j==3) {
                    xmin = 150;
                    xmax = 300;
                    ymin = 100;
                    ymax = 200;
                    xzmin = 0;
                    xzmax = 8;
                    yzmin = 0;
                    yzmax = 8;
                }

                double x = (xmin) + (xmax - (xmin)) * random.nextDouble();
                double y = (ymin) + (ymax - (ymin)) * random.nextDouble();

                double xz = ((xzmax - (xzmin)) * (x - (xmin)) / (xmax - (xmin)) + (xzmin));
                double yz = ((yzmax - (yzmin)) * (y - (ymin)) / (ymax - (ymin)) + (yzmin));
                y = 200 - y;

                if (equation.calc(xz, yz)) {
                    bi.setRGB((int) x, (int) y, YELLOW.getRGB());
                    varTrue++;
                } else {
                    bi.setRGB((int) x, (int) y, BLACK.getRGB());
                    varFalse++;
                }

                if (interval >= 10) {
                    if (i % interval == 0) {
                        gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
                        updateProgress(i, howManyIterations);
                    }
                } else {
                    gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
                    updateProgress(i, interval);
                }

                if (isCancelled()) {
                    varRes = varTrue / i;
                    break;
                }
            }
        }
        System.out.println(varTrue);
        varRes = varTrue/howManyIterations;
        return null;
    }

    public double getVarRes() {
        return varRes;
    }
}

