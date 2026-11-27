/**
 * Representa a un estudiante y sus tres calificaciones.
 * Variante asignada: C (promedio minimo de aprobacion = 13).
 * Para cambiar de variante, modificar solo la constante LIMITE_APROBACION.
 */
public class Estudiante {

    public static final int LIMITE_APROBACION = 13;

    private String codigo;
    private String nombre;
    private int[] notas;

    public Estudiante(String codigo, String nombre, int[] notas) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.notas = new int[notas.length];
        for (int i = 0; i < notas.length; i++) {
            this.notas[i] = notas[i];
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int[] getNotas() {
        int[] copia = new int[notas.length];
        for (int i = 0; i < notas.length; i++) {
            copia[i] = notas[i];
        }
        return copia;
    }

    /** Promedio real (sin division entera). */
    public double calcularPromedio() {
        int suma = 0;
        for (int i = 0; i < notas.length; i++) {
            suma += notas[i];
        }
        return suma / (double) notas.length;
    }

    public int obtenerNotaMaxima() {
        int max = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] > max) {
                max = notas[i];
            }
        }
        return max;
    }

    public int obtenerNotaMinima() {
        int min = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] < min) {
                min = notas[i];
            }
        }
        return min;
    }

    public String obtenerEstado() {
        if (calcularPromedio() >= LIMITE_APROBACION) {
            return "APROBADO";
        }
        return "REPROBADO";
    }
}
