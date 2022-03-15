package visualization;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Plotter {

    public static final XYChart byteChart = new XYChartBuilder().width(600).height(400)
            .title("Packet Size vs Response Time")
            .xAxisTitle("Response Time")
            .yAxisTitle("Packet Size")
            .build();
    public static final XYChart rtChart = new XYChartBuilder().width(600).height(400)
            .title("Packet Size vs Response Time")
            .xAxisTitle("Response Time")
            .yAxisTitle("Packet Size")
            .build();
    public static final JPanel bytePanel = new XChartPanel<XYChart>(byteChart);
    public static final JPanel rtPanel = new XChartPanel<XYChart>(rtChart);
    public static Map<String, List <Double>> xs = new HashMap<>();
    public static Map<String, List <Double>> ys = new HashMap<>();

    public static void initPlotter() {
        // Customize Chart
        //chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        //chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        xs.put("bytes", new ArrayList<>());
        xs.put("response time", new ArrayList<>());
        ys.put("bytes", new ArrayList<>());
        ys.put("response time", new ArrayList<>());
        xs.get("bytes").add(0.0);
        ys.get("bytes").add(2048.0);
        xs.get("response time").add(0.0);
        ys.get("response time").add(0.0);
        byteChart.addSeries("bytes", xs.get("bytes"), ys.get("bytes"));
        rtChart.addSeries("response time", xs.get("response time"), ys.get("response time"));

        SwingUtilities.invokeLater(() -> {
            // Create and set up the window.
            JFrame frame = new JFrame("Profiling Network Conditions");
            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            mainPanel.add(bytePanel);
            mainPanel.add(rtPanel);

            // chart
            frame.add(mainPanel);

            // Display the window.
            frame.pack();
            frame.setVisible(true);
        });
    }

}
