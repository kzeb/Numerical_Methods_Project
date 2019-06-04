package sample;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    private DrawerTask task;
    private DrawerTask2 task2;
    private DrawerTask3 task3;
    private DrawerTask4 task4;
    private AsyncTask controlResult;
    private double integral = 0;
    private double integral1;
    private double integral2;
    private double integral3;
    private double integral4;
    private int com1 = 0;
    private int com2 = 0;
    private int com3 = 0;
    private int com4 = 0;
    private int com = 0;

    @FXML
    private Canvas canvas1;
    @FXML
    private Canvas canvas2;
    @FXML
    private Canvas canvas3;
    @FXML
    private Canvas canvas4;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextField howMany;

    @FXML
    private Label results;
    @FXML
    private Label results1;
    @FXML
    private Label results2;
    @FXML
    private Label results3;
    @FXML
    private Label results4;

    @FXML
    private TextArea history;
    @FXML
    private Label common_m;

    @FXML
    private Label w1;
    @FXML
    private Label w2;
    @FXML
    private Label w3;
    @FXML
    private Label w4;
    @FXML
    private Label runs;

    @FXML
    public void onEnter(ActionEvent ae){
        handleRunBtnAction();
    }

    private int howMany(){
        int temp = Integer.parseInt(howMany.getText());
        System.out.println("Number of points: " + temp);
        return temp;
    }

    @FXML
    private void handleRunBtnAction(){
        GraphicsContext gc = canvas1.getGraphicsContext2D();
        gc.clearRect(0, 0, 300, 200);
        results1.setText("");
        task = new DrawerTask(gc, howMany());
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double res = task.getVarRes();
                MonteCarlo mc = new MonteCarlo(res);
                integral1 = mc.calculateIntegral();
                System.out.println(integral1);
                results1.setText(Double.toString(integral1));
            }
        });
        new Thread(task).start();

        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
        gc2.clearRect(0, 0, 300, 200);
        results2.setText("");
        task2 = new DrawerTask2(gc2, howMany());
        task2.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double res = task2.getVarRes();
                MonteCarlo mc = new MonteCarlo(res);
                integral2 = mc.calculateIntegral();
                System.out.println(integral2);
                results2.setText(Double.toString(integral2));
            }
        });
        new Thread(task2).start();

        GraphicsContext gc3 = canvas3.getGraphicsContext2D();
        gc3.clearRect(0, 0, 300, 200);
        results3.setText("");
        task3 = new DrawerTask3(gc3, howMany());
        task3.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double res = task3.getVarRes();
                MonteCarlo mc = new MonteCarlo(res);
                integral3 = mc.calculateIntegral();
                results3.setText(Double.toString(integral3));
            }
        });
        new Thread(task3).start();

        GraphicsContext gc4 = canvas4.getGraphicsContext2D();
        gc4.clearRect(0, 0, 300, 200);
        results4.setText("");
        task4 = new DrawerTask4(gc4, howMany());
        task4.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double res = task4.getVarRes();
                MonteCarlo mc = new MonteCarlo(res);
                integral4 = mc.calculateIntegral();
                results4.setText(Double.toString(integral4));
            }
        });
        new Thread(task4).start();

        if (integral == 0) {
            controlResult = new AsyncTask(100000000);
        }else{
            controlResult = new AsyncTask(10);
        }
        progressBar.progressProperty().bind(controlResult.progressProperty());
        controlResult.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if (integral == 0) {
                    double res = controlResult.getVarRes();
                    MonteCarlo mc = new MonteCarlo(res);
                    integral = mc.calculateIntegral();
                    System.out.println("Control result: " + integral);
                    results.setText(Double.toString(integral));
                }
                double comp1 = Math.abs(integral - integral1);
                double comp2 = Math.abs(integral - integral2);
                double comp3 = Math.abs(integral - integral3);
                double comp4 = Math.abs(integral - integral4);
                double temp = 0;
                if (comp1>temp){
                    temp = comp1;
                }
                if (comp2<temp){
                    temp = comp2;
                }
                if (comp3<temp){
                    temp = comp3;
                }
                if (comp4<temp){
                    temp = comp4;
                }
                System.out.println("@@@@@@@@@@@@@@@@@@" + temp);
                System.out.println(comp1);
                System.out.println(comp2);
                System.out.println(comp3);
                System.out.println(comp4);
                String handle = "";
                if (temp == comp1){
                    handle = history.getText();
                    history.setText(handle + "\n(1)    " + integral1);
                    com1++;
                    w1.setText("" + com1);
                }else if(temp == comp2){
                    handle = history.getText();
                    history.setText(handle + "\n(2)    " + integral2);
                    com2++;
                    w2.setText("" + com2);
                }else if(temp == comp3){
                    handle = history.getText();
                    history.setText(handle + "\n(3)    " + integral3);
                    com3++;
                    w3.setText("" + com3);
                }else if(temp == comp4){
                    handle = history.getText();
                    history.setText(handle + "\n(4)    " + integral4);
                    com4++;
                    w4.setText("" + com4);
                }

                int to = com1+com2+com3+com4;
                runs.setText("" + to);

                if (com1>com)
                    com = com1;
                if (com2>com)
                    com = com2;
                if (com3>com)
                    com = com3;
                if (com4>com)
                    com = com4;

                if (com == com1){
                    common_m.setText("(1)");
                }else if(com == com2){
                    common_m.setText("(2)");
                }else if(com == com3){
                    common_m.setText("(3)");
                }else if(com == com4){
                    common_m.setText("(4)");
                }
            }
        });
        new Thread(controlResult).start();
    }

    @FXML
    private void handleStopBtnAction(){
        task.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double res = task.getVarRes();
                System.out.println("res " + res);
                MonteCarlo mc1 = new MonteCarlo(res);
                double integral = mc1.calculateIntegral();
                System.out.println("Result 1: " + integral);
                results1.setText(Double.toString(integral));
            }
        });
        task.cancel();

        task2.setOnCancelled(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                double res = task2.getVarRes();
                System.out.println("res " + res);
                MonteCarlo mc1 = new MonteCarlo(res);
                double integral = mc1.calculateIntegral();
                System.out.println("Result 2: " + integral);
                results2.setText(Double.toString(integral));
            }
        });
        task2.cancel();
    }

    @FXML
    private void handleClearBtnAction(){
        GraphicsContext gc = canvas1.getGraphicsContext2D();
        GraphicsContext gc2 = canvas2.getGraphicsContext2D();
        GraphicsContext gc3 = canvas3.getGraphicsContext2D();
        GraphicsContext gc4 = canvas4.getGraphicsContext2D();
        gc.clearRect(0, 0, 300, 200);
        gc2.clearRect(0, 0, 300, 200);
        gc3.clearRect(0, 0, 300, 200);
        gc4.clearRect(0, 0, 300, 200);
        results1.setText("");
        results2.setText("");
        results3.setText("");
        results4.setText("");
        howMany.setText("");
        progressBar.progressProperty().unbind();
        progressBar.setProgress(0);
    }
}
