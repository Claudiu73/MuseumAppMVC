package View;

import Repo.ArtWorkRepository;
import Repo.DAOException;
import Model.ArtWork;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ArtworkStatisticsLinearView extends JFrame {
    private ArtWorkRepository artWorkRepository;

    public ArtworkStatisticsLinearView(ArtWorkRepository repository) {
        this.artWorkRepository = repository;

        setTitle("Statistici Opere de Artă");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JFreeChart barChart = createChart();
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        setVisible(true);
    }

    private JFreeChart createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            List<ArtWork> artworks = artWorkRepository.getAllArtworks();
            Map<String, Integer> typeCounts = new HashMap<>();
            for (ArtWork artwork : artworks) {
                String type = artwork.getType();
                typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
                dataset.addValue(entry.getValue(), "Opere de Artă", entry.getKey());
            }
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(this, "Eroare la accesarea bazei de date: " + e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }

        return ChartFactory.createBarChart(
                "Numărul de Opere de Artă pe Tipuri",
                "Tipul Operei",
                "Numărul de Opere",
                dataset
        );
    }
}
