package edu.ntnu.idatt1002.frontend.utility;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DoughnutChart extends PieChart {
    private final Circle innerCircle;
    //private final PieChart pieChart;


    public DoughnutChart(ObservableList<Data> pieData) {
        super(pieData);

        innerCircle = new Circle();

        // just styled in code for demo purposes,
        // use a style class instead to style via css.
        innerCircle.setFill(Color.WHITESMOKE);
        innerCircle.setStroke(Color.WHITE);
        innerCircle.setStrokeWidth(3);

        //pieChart = new PieChart(pieData);

        //changePieChartColor(pieData);

    }

//    public static void changePieChartColor(ObservableList<Data> pieData) {
//        final String[] pieColors = new String[] {"#3F403F", "#475841", "#9FB8AD", "#CED0CE", "#E6E8E6"};
//
//        for (int i = 0; i < pieData.size(); i++) {
//            PieChart.Data data = pieData.get(i);
//            Node node = data.getNode();
//            String color = pieColors[i % pieColors.length];
//            node.setStyle("-fx-pie-color: " + color + ";");
//        }
//    }


    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        super.layoutChartChildren(top, left, contentWidth, contentHeight);

        addInnerCircleIfNotPresent();
        updateInnerCircleLayout();
    }

    private void addInnerCircleIfNotPresent() {
        if (getData().size() > 0) {
            Node pie = getData().get(0).getNode();
            if (pie.getParent() instanceof Pane) {
                Pane parent = (Pane) pie.getParent();

                if (!parent.getChildren().contains(innerCircle)) {
                    parent.getChildren().add(innerCircle);
                }
            }
        }
    }

    private void updateInnerCircleLayout() {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        for (PieChart.Data data: getData()) {
            Node node = data.getNode();

            Bounds bounds = node.getBoundsInParent();
            if (bounds.getMinX() < minX) {
                minX = bounds.getMinX();
            }
            if (bounds.getMinY() < minY) {
                minY = bounds.getMinY();
            }
            if (bounds.getMaxX() > maxX) {
                maxX = bounds.getMaxX();
            }
            if (bounds.getMaxY() > maxY) {
                maxY = bounds.getMaxY();
            }
        }

        innerCircle.setCenterX(minX + (maxX - minX) / 2);
        innerCircle.setCenterY(minY + (maxY - minY) / 2);

        innerCircle.setRadius((maxX - minX) / 4);
    }
}