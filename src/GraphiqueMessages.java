import java.util.ArrayList;
import java.util.Map;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

public class GraphiqueMessages extends VBox{
    public GraphiqueMessages(TestJDBC testJDBC,ArrayList<Map.Entry<String,Integer>> donnees) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Jours");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nb Message");
        // Création du graphique
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Nombre de messages envoyés par jour");

        // Série de données
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String,Integer> entree: donnees){
            series.getData().add(new XYChart.Data<>(entree.getKey(),entree.getValue()));
        }

        // Ajout de la série au graphique
        barChart.getData().add(series);
        this.getChildren().addAll(barChart);
    }

}
