import java.util.ArrayList;
import java.util.Map;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

public class GraphiqueMain extends VBox{
    public GraphiqueMain(TestJDBC testJDBC,ArrayList<Map.Entry<String,Integer>> donnees) {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Répartition Gaucher-droitier");
        for (Map.Entry<String,Integer> entree: donnees){
            pieChart.getData().add(new PieChart.Data(entree.getKey(),entree.getValue()));
        }

        this.getChildren().addAll(pieChart);
    }

}
