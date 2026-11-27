import java.util.ArrayList;

public class GestionAcademica {

    private ArrayList<Estudiante> estudiantes;

    public GestionAcademica() {
        estudiantes = new ArrayList<>();
    }


    /** Texto no vacio y sin punto y coma. */
    public static boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty() && !texto.contains(";");
    }

    public static boolean notaValida(int nota) {
        return nota >= 0 && nota <= 20;
    }

    public boolean existeCodigo(String codigo) {
        return buscar(codigo) != null;
    }

    public boolean registrar(Estudiante estudiante) {
        if (existeCodigo(estudiante.getCodigo())) {
            return false;
        }
        estudiantes.add(estudiante);
        return true;
    }

    public Estudiante buscar(String codigo) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getCodigo().equalsIgnoreCase(codigo.trim())) {
                return estudiantes.get(i);
            }
        }
        return null;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return new ArrayList<>(estudiantes);
    }

    public void reemplazarEstudiantes(ArrayList<Estudiante> lista) {
        estudiantes = new ArrayList<>(lista);
    }

    // ---------- Indicadores ----------

    public int getTotal() {
        return estudiantes.size();
    }

    public int contarAprobados() {
        int contador = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).obtenerEstado().equals("APROBADO")) {
                contador++;
            }
        }
        return contador;
    }

    public int contarReprobados() {
        return getTotal() - contarAprobados();
    }

    public double calcularPromedioGeneral() {
        if (estudiantes.isEmpty()) {
            return 0;
        }
        double acumulador = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            acumulador += estudiantes.get(i).calcularPromedio();
        }
        return acumulador / estudiantes.size();
    }

    /** Devuelve 0 si no hay estudiantes (evita division entre cero). */
    public double calcularPorcentajeAprobados() {
        if (estudiantes.isEmpty()) {
            return 0;
        }
        return contarAprobados() * 100.0 / getTotal();
    }

    /** En caso de empate se conserva el primero registrado. */
    public Estudiante obtenerMejorPromedio() {
        if (estudiantes.isEmpty()) {
            return null;
        }
        Estudiante mejor = estudiantes.get(0);
        for (int i = 1; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).calcularPromedio() > mejor.calcularPromedio()) {
                mejor = estudiantes.get(i);
            }
        }
        return mejor;
    }
    public Estudiante obtenerPeorPromedio() {
        if (estudiantes.isEmpty()) {
            return null;
        }
        Estudiante peor = estudiantes.get(0);
        for (int i = 1; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).calcularPromedio() < peor.calcularPromedio()) {
                peor = estudiantes.get(i);
            }
        }
        return peor;
    }

    public ArrayList<Estudiante> obtenerBajoPromedioGeneral() {
        ArrayList<Estudiante> resultado = new ArrayList<>();
        double general = calcularPromedioGeneral();
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).calcularPromedio() < general) {
                resultado.add(estudiantes.get(i));
            }
        }
        return resultado;
    }

    public ArrayList<Estudiante> ordenarPorPromedioDescendente() {
        ArrayList<Estudiante> orden = new ArrayList<>(estudiantes);
        for (int i = 1; i < orden.size(); i++) {
            Estudiante clave = orden.get(i);
            int j = i - 1;
            while (j >= 0 && orden.get(j).calcularPromedio() < clave.calcularPromedio()) {
                orden.set(j + 1, orden.get(j));
                j--;
            }
            orden.set(j + 1, clave);
        }
        return orden;
    }
}
