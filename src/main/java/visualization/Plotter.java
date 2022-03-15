package visualization;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;

public class Plotter {

    public static final XYChart chart = new XYChartBuilder().width(600).height(400)
            .title("Packet Size vs Response Time")
            .xAxisTitle("Packet Size")
            .yAxisTitle("Response Time")
            .build();

    public static void initPlotter() {
        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

        chart.addSeries("a", new double[] { 0 }, new double[] { -3 });

        SwingUtilities.invokeLater(() -> {
            // Create and set up the window.
            JFrame frame = new JFrame("Profiling Network Conditions");
            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // chart
            JPanel chartPanel = new XChartPanel<XYChart>(chart);
            frame.add(chartPanel, BorderLayout.CENTER);

            // Display the window.
            frame.pack();
            frame.setVisible(true);
        });
    }

}
