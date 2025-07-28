package views;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResultadosDialog extends JDialog {

    private final DefaultTableModel modelo;
    private final AlgorithmResultDAO resultDAO;
    private List<AlgorithmResult> resultados;

    public ResultadosDialog(JFrame paramJFrame, AlgorithmResultDAO paramAlgorithmResultDAO) {
        super(paramJFrame, "Resultados Guardados", true);
        this.resultDAO = paramAlgorithmResultDAO;
        setLayout(new BorderLayout());
        this.modelo = new DefaultTableModel((Object[])new String[] { "Algoritmo", "Celdas Camino", "Tiempo (ns)" }, 0);
        JTable jTable = new JTable(this.modelo);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        add(jScrollPane, "Center");
        JPanel jPanel = new JPanel();
        JButton jButton1 = new JButton("Limpiar Resultados");
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(ResultadosDialog.this, "¿Deseas borrar todos los resultados?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    paramAlgorithmResultDAO.clear();
                    ResultadosDialog.this.modelo.setRowCount(0);
                }
            }
        });

        JButton jButton2 = new JButton("Graficar Resultados");
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGrafica();
            }
        });

        jPanel.add(jButton1);
        jPanel.add(jButton2);
        add(jPanel, "South");
        setSize(500, 400);
        setLocationRelativeTo(paramJFrame);

    }

    private void mostrarGrafica() {

    }
}
