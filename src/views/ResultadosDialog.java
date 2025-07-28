package views;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ResultadosDialog extends JDialog {
    private final DefaultTableModel model;
    private final AlgorithmResultDAO resultDAO;
    private List<AlgorithmResult> results;

    public ResultadosDialog(JFrame parent, AlgorithmResultDAO resultDAO) {
        super(parent, "Resultados Guardados", true);
        this.resultDAO = resultDAO;

        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Algoritmo", "Celdas Camino", "Tiempo (ns)"}, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadData();

        JPanel buttonPanel = new JPanel();
        JButton clearButton = new JButton("Limpiar Resultados");
        JButton graphButton = new JButton("Graficar Resultados");

        clearButton.addActionListener((ActionEvent e) -> {
            int i = JOptionPane.showConfirmDialog(this, "¿Deseas borrar todos los resultados?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                resultDAO.clear();
                model.setRowCount(0);
            }
        });

        graphButton.addActionListener((ActionEvent e) -> mostrarGrafica());

        buttonPanel.add(clearButton);
        buttonPanel.add(graphButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(500, 400);
        setLocationRelativeTo(parent);
    }

    private void loadData() {
        List<AlgorithmResult> nuevosResultados = resultDAO.findAll();

        for (AlgorithmResult result : nuevosResultados) {
            boolean yaExiste = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(result.getAlgorithmName())) {
                    yaExiste = true;
                    break;
                }
            }

            if (!yaExiste) {
                model.addRow(new Object[]{
                        result.getAlgorithmName(),
                        result.getPathLength(),
                        result.getTimeMs()
                });
            }
        }

        this.results = nuevosResultados; // actualiza la lista interna solo con los más recientes
    }


    private void mostrarGrafica() {
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay datos para graficar.");
            return;
        }

        JPanel graficoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int ancho = getWidth();
                int alto = getHeight();
                int margin = 50;
                int barWidth = (ancho - 2 * margin) / results.size();

                long maxTiempo = results.stream().mapToLong(AlgorithmResult::getTimeMs).max().orElse(1);

                for (int i = 0; i < results.size(); i++) {
                    AlgorithmResult result = results.get(i);
                    int barHeight = (int) ((result.getTimeMs() * 1.0 / maxTiempo) * (alto - 2 * margin));
                    int x = margin + i * barWidth;
                    int y = alto - barHeight - margin;

                    g.setColor(new Color(150, 100 + i * 20 % 155, 220));
                    g.fillRect(x, y, barWidth - 10, barHeight);

                    g.setColor(Color.BLACK);
                    g.drawString(result.getAlgorithmName(), x, alto - 25);
                    g.drawString(result.getTimeMs() + "ns", x, y - 5);
                }
            }
        };

        graficoPanel.setPreferredSize(new Dimension(600, 400));

        JDialog graficoDialog = new JDialog(this, "Gráfico de Tiempos", true);
        graficoDialog.getContentPane().add(graficoPanel);
        graficoDialog.pack();
        graficoDialog.setLocationRelativeTo(this);
        graficoDialog.setVisible(true);
    }
}
