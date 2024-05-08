package View;

import Model.ArtWork;
import Repo.ArtWorkRepository;
import Repo.DAOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtworkStatisticsInelarView extends JFrame {
    private ArtWorkRepository artWorkRepository;

    public ArtworkStatisticsInelarView(ArtWorkRepository repository) {
        this.artWorkRepository = repository;

        setTitle("Statistici Opere de Artă - Structură Inelară");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JFreeChart doughnutChart = createChart();
        ChartPanel chartPanel = new ChartPanel(doughnutChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        setVisible(true);
    }

    private JFreeChart createChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            List<ArtWork> artworks = artWorkRepository.getAllArtworks();
            Map<String, Integer> typeCounts = new HashMap<>();
            for (ArtWork artwork : artworks) {
                String type = artwork.getType();
                typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Eroare la accesarea bazei de date: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        JFreeChart chart = ChartFactory.createRingChart(
                "Numărul de Opere de Artă pe Tipuri",
                dataset,
                true,
                true,
                false
        );
        RingPlot plot = (RingPlot) chart.getPlot();
        plot.setSectionDepth(0.30); // Ajustează adâncimea secțiunii pentru a modifica aspectul gogoașei
        return chart;
    }
}
