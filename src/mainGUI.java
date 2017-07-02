import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;


public class mainGUI extends Application {
    private static ArrayList<ArrayList<Integer>> data = new ArrayList<>();

    @Override public void start(Stage stage) throws InterruptedException {
        stage.setTitle("LaBattagliaDeiSessi");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<>(xAxis,yAxis);

        lineChart.setTitle("BoS");

        ArrayList<String> name = new ArrayList<>();
        name.add("Mor");name.add("Avv");name.add("Pru");name.add("Spr");
        for(int d = 0; d < data.size(); d++){

            XYChart.Series series = new XYChart.Series();
            series.setName(name.get(d));

            int age = 0;
            for(Integer i : data.get(d)) series.getData().add(new XYChart.Data(age++,i));

            lineChart.getData().add(series);
        }

        Scene scene  = new Scene(lineChart,1000,600);

        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) throws NoSuchIndividuoException, InterruptedException {
        startSimulation fun = new startSimulation();
        data = fun.start();
        launch(args);
    }
}