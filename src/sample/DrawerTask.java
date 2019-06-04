package sample;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.awt.Color.BLACK;
import static java.awt.Color.YELLOW;

public class DrawerTask extends Task {
    private GraphicsContext gc;
    private int howManyIterations;
    private double varTrue = 0;
    private double varFalse = 0;
    private double varRes = 0;

    public DrawerTask(GraphicsContext gc, int hmi) {
        this.gc = gc;
        this.howManyIterations = hmi;
    }

    @Override
    protected Object call() throws Exception {
        Random random = new Random();
        Equation equation = new Equation();
        BufferedImage bi= new BufferedImage(300, 200, BufferedImage.TYPE_INT_ARGB);
        int interval = howManyIterations/100;
        for(int i = 0; i< howManyIterations; i++){

            double x = (0) + (300 - (0)) * random.nextDouble();
            double y = (0) + (200 - (0)) * random.nextDouble();

            double xz = ((8-(-8)) * (x-(0)) / (300-(0)) + (-8));
            double yz= ((8-(-8)) * (y-(0)) / (200-(0)) + (-8));
            y = 200-y;
//            x = 600-x;

            if(equation.calc(xz,yz)){
                bi.setRGB((int)x, (int)y, YELLOW.getRGB());
                varTrue++;
            }else{
                bi.setRGB((int)x, (int)y, BLACK.getRGB());
                varFalse++;
            }

            if (interval>=10) {
                if (i % interval == 0) {
                    gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
                    updateProgress(i, howManyIterations);
                }
            }else{
                gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
                updateProgress(i, interval);
            }

            if(isCancelled()) {
                varRes = varTrue/i;
                break;
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

