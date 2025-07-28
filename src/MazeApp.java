import views.MazeFrame;

import javax.swing.*;

public class MazeApp {
    public static void main(String[] args) {
        int[] dimensiones = solicitarDimensiones();
        if (dimensiones == null) {
            System.out.println("Cancelado por el usuario o entrada inválida.");
            return;
        }

        int filas = dimensiones[0];
        int columnas = dimensiones[1];

        // Aquí simplemente abres la ventana, el usuario interactúa con ella.
        javax.swing.SwingUtilities.invokeLater(() -> new MazeFrame(filas, columnas));
    }

    // Método auxiliar para pedir dimensiones al usuario
    public static int[] solicitarDimensiones() {
        try {
            String str1 = JOptionPane.showInputDialog("Ingrese número de filas:");
            if (str1 == null) return null;

            String str2 = JOptionPane.showInputDialog("Ingrese número de columnas:");
            if (str2 == null) return null;

            int filas = Integer.parseInt(str1.trim());
            int columnas = Integer.parseInt(str2.trim());

            if (filas <= 4 || columnas <= 4) {
                JOptionPane.showMessageDialog(null, "Debe ingresar valores mayores a 4.");
                return null;
            }

            return new int[] { filas, columnas };
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar números válidos.");
            return null;
        }
    }
}
