import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestorArchivos {

    public static final String CARPETA = "datos";
    public static final String RUTA = "datos/estudiantes.txt";

    /** Reescribe el archivo completo (no duplica registros). */
    public boolean guardar(ArrayList<Estudiante> lista) {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(RUTA))) {
            for (int i = 0; i < lista.size(); i++) {
                Estudiante e = lista.get(i);
                int[] n = e.getNotas();
                escritor.write(e.getCodigo() + ";" + e.getNombre() + ";" + n[0] + ";" + n[1] + ";" + n[2]);
                escritor.newLine();
            }
            return true;
        } catch (IOException ex) {
            System.out.println("Error al guardar el archivo: " + ex.getMessage());
            return false;
        }
    }

    /** Lee el archivo. Si no existe devuelve una lista vacia. */
    public ArrayList<Estudiante> cargar() {
        ArrayList<Estudiante> lista = new ArrayList<>();
        File archivo = new File(RUTA);
        if (!archivo.exists()) {
            System.out.println("No existe el archivo " + RUTA + ". Se inicia sin registros.");
            return lista;
        }
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int numeroLinea = 0;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                Estudiante e = convertirLinea(linea, numeroLinea, lista);
                if (e != null) {
                    lista.add(e);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo: " + ex.getMessage());
        }
        return lista;
    }

    /** Convierte una linea en Estudiante. Devuelve null si la linea es invalida. */
    private Estudiante convertirLinea(String linea, int numeroLinea, ArrayList<Estudiante> actuales) {
        String[] partes = linea.split(";");
        if (partes.length != 5) {
            System.out.println("Linea " + numeroLinea + " omitida: formato incorrecto.");
            return null;
        }
        String codigo = partes[0].trim();
        String nombre = partes[1].trim();
        if (!GestionAcademica.textoValido(codigo) || !GestionAcademica.textoValido(nombre)) {
            System.out.println("Linea " + numeroLinea + " omitida: codigo o nombre invalido.");
            return null;
        }
        for (int i = 0; i < actuales.size(); i++) {
            if (actuales.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                System.out.println("Linea " + numeroLinea + " omitida: codigo repetido.");
                return null;
            }
        }
        int[] notas = new int[3];
        try {
            for (int i = 0; i < 3; i++) {
                notas[i] = Integer.parseInt(partes[i + 2].trim());
                if (!GestionAcademica.notaValida(notas[i])) {
                    System.out.println("Linea " + numeroLinea + " omitida: nota fuera de rango.");
                    return null;
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println("Linea " + numeroLinea + " omitida: nota no numerica.");
            return null;
        }
        return new Estudiante(codigo, nombre, notas);
    }
}
