package dao.daoImpl;

import dao.AlgorithmResultDAO;
import models.AlgorithmResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmResultDAOFile implements AlgorithmResultDAO {

    private File archivo;

    public AlgorithmResultDAOFile(String nombreArchivo) {
        this.archivo = new File(nombreArchivo);
    }

    @Override
    public void save(AlgorithmResult resultado) {
        List<AlgorithmResult> resultados = findAll();
        boolean existe = false;

        for (int i = 0; i < resultados.size(); i++) {
            if (resultados.get(i).getAlgorithmName().equalsIgnoreCase(resultado.getAlgorithmName())) {
                resultados.set(i, resultado); // reemplazar
                existe = true;
                break;
            }
        }

        if (!existe) {
            resultados.add(resultado);
        }

        try {
            FileWriter escritor = new FileWriter(archivo, false);
            for (AlgorithmResult r : resultados) {
                escritor.write(r.toString() + "\n");
            }
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @Override
    public List<AlgorithmResult> findAll() {
        List<AlgorithmResult> lista = new ArrayList<>();

        if (!archivo.exists()) {
            return lista;
        }

        try {
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    String nombre = partes[0];
                    int pasos = Integer.parseInt(partes[1]);
                    long tiempo = Long.parseLong(partes[2]);
                    lista.add(new AlgorithmResult(nombre, pasos, tiempo));
                }
            }
            lector.close();
        } catch (Exception e) {
            System.out.println("Error al leer: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void clear() {
        try {
            FileWriter escritor = new FileWriter(archivo, false);
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error al limpiar archivo: " + e.getMessage());
        }
    }
}

