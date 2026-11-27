import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        GestionAcademica gestion = new GestionAcademica();
        GestorArchivos gestor = new GestorArchivos();

        // Recuperacion automatica al iniciar
        gestion.reemplazarEstudiantes(gestor.cargar());
        System.out.println("Registros recuperados: " + gestion.getTotal());

        int opcion = 0;
        try {
            do {
                mostrarMenu();
                opcion = leerOpcion();
                switch (opcion) {
                    case 1:
                        registrarEstudiante(gestion);
                        break;
                    case 2:
                        listarEstudiantes(gestion);
                        break;
                    case 3:
                        buscarEstudiante(gestion);
                        break;
                    case 4:
                        mostrarReporte(gestion);
                        break;
                    case 5:
                        guardarRegistros(gestion, gestor);
                        break;
                    case 6:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion no valida. Ingrese un numero del 1 al 6.");
                }
            } while (opcion != 6);
        } catch (NoSuchElementException ex) {
            System.out.println("\nSe cerro la entrada de datos. Se guardaran los registros.");
        }

        // Antes de salir se guardan los registros
        guardarRegistros(gestion, gestor);
    }

    // ---------- Menu y lectura ----------

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=====================================");
        System.out.println(" SISTEMA AULALOGIC");
        System.out.println("=====================================");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Listar estudiantes");
        System.out.println("3. Buscar estudiante");
        System.out.println("4. Generar reporte academico");
        System.out.println("5. Guardar registros");
        System.out.println("6. Salir");
        System.out.println("=====================================");
        System.out.print("Seleccione una opcion: ");
    }

    /** Devuelve -1 si lo ingresado no es un numero. */
    private static int leerOpcion() {
        String texto = teclado.nextLine().trim();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    private static int leerNota(int numero) {
        while (true) {
            System.out.print("Calificacion " + numero + " (0 a 20): ");
            String texto = teclado.nextLine().trim();
            try {
                int nota = Integer.parseInt(texto);
                if (GestionAcademica.notaValida(nota)) {
                    return nota;
                }
                System.out.println("Error: la calificacion debe estar entre 0 y 20.");
            } catch (NumberFormatException ex) {
                System.out.println("Error: ingrese un numero entero.");
            }
        }
    }

    // ---------- Opciones del menu ----------

    private static void registrarEstudiante(GestionAcademica gestion) {
        System.out.println("\n--- REGISTRAR ESTUDIANTE ---");
        String codigo;
        while (true) {
            System.out.print("Codigo: ");
            codigo = teclado.nextLine().trim();
            if (GestionAcademica.textoValido(codigo)) {
                break;
            }
            System.out.println("Error: el codigo no puede estar vacio ni contener punto y coma.");
        }
        if (gestion.existeCodigo(codigo)) {
            System.out.println("Error: el codigo " + codigo + " ya esta registrado. Registro cancelado.");
            return;
        }

        String nombre;
        while (true) {
            System.out.print("Nombre completo: ");
            nombre = teclado.nextLine().trim();
            if (GestionAcademica.textoValido(nombre)) {
                break;
            }
            System.out.println("Error: el nombre no puede estar vacio ni contener punto y coma.");
        }

        int[] notas = new int[3];
        for (int i = 0; i < 3; i++) {
            notas[i] = leerNota(i + 1);
        }

        Estudiante estudiante = new Estudiante(codigo, nombre, notas);
        gestion.registrar(estudiante);
        System.out.println("Estudiante registrado correctamente.");
    }

    private static void listarEstudiantes(GestionAcademica gestion) {
        System.out.println("\n--- LISTADO DE ESTUDIANTES ---");
        ArrayList<Estudiante> lista = gestion.getEstudiantes();
        if (lista.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            Estudiante e = lista.get(i);
            int[] n = e.getNotas();
            System.out.printf("%s | %s | Notas: %d, %d, %d | Promedio: %.2f | %s%n",
                    e.getCodigo(), e.getNombre(), n[0], n[1], n[2],
                    e.calcularPromedio(), e.obtenerEstado());
        }
    }

    private static void buscarEstudiante(GestionAcademica gestion) {
        System.out.println("\n--- BUSCAR ESTUDIANTE ---");
        System.out.print("Codigo a buscar: ");
        String codigo = teclado.nextLine().trim();
        Estudiante e = gestion.buscar(codigo);
        if (e == null) {
            System.out.println("No existe un estudiante con el codigo " + codigo + ".");
            return;
        }
        int[] n = e.getNotas();
        System.out.println("Codigo: " + e.getCodigo());
        System.out.println("Nombre: " + e.getNombre());
        System.out.println("Calificaciones: " + n[0] + ", " + n[1] + ", " + n[2]);
        System.out.printf("Promedio: %.2f%n", e.calcularPromedio());
        System.out.println("Nota maxima: " + e.obtenerNotaMaxima());
        System.out.println("Nota minima: " + e.obtenerNotaMinima());
        System.out.println("Estado academico: " + e.obtenerEstado());
    }

    private static void mostrarReporte(GestionAcademica gestion) {
        System.out.println("\n--- REPORTE ACADEMICO ---");
        if (gestion.getTotal() == 0) {
            System.out.println("No hay estudiantes registrados. No se puede generar el reporte.");
            return;
        }
        System.out.println("Limite de aprobacion: " + Estudiante.LIMITE_APROBACION);
        System.out.println("Total de estudiantes: " + gestion.getTotal());
        System.out.println("Aprobados: " + gestion.contarAprobados());
        System.out.println("Reprobados: " + gestion.contarReprobados());
        System.out.printf("Promedio general del curso: %.2f%n", gestion.calcularPromedioGeneral());
        System.out.printf("Porcentaje de aprobados: %.2f%%%n", gestion.calcularPorcentajeAprobados());

        Estudiante mejor = gestion.obtenerMejorPromedio();
        Estudiante peor = gestion.obtenerPeorPromedio();
        System.out.printf("Promedio mas alto: %s (%s) - %.2f%n",
                mejor.getNombre(), mejor.getCodigo(), mejor.calcularPromedio());
        System.out.printf("Promedio mas bajo: %s (%s) - %.2f%n",
                peor.getNombre(), peor.getCodigo(), peor.calcularPromedio());

        System.out.println("\nEstudiantes con promedio inferior al general:");
        ArrayList<Estudiante> bajos = gestion.obtenerBajoPromedioGeneral();
        if (bajos.isEmpty()) {
            System.out.println("  Ninguno.");
        } else {
            for (int i = 0; i < bajos.size(); i++) {
                System.out.printf("  %s - %s (%.2f)%n", bajos.get(i).getCodigo(),
                        bajos.get(i).getNombre(), bajos.get(i).calcularPromedio());
            }
        }

        System.out.println("\nListado de mayor a menor promedio:");
        ArrayList<Estudiante> orden = gestion.ordenarPorPromedioDescendente();
        for (int i = 0; i < orden.size(); i++) {
            System.out.printf("  %d. %s - %s (%.2f) %s%n", i + 1, orden.get(i).getCodigo(),
                    orden.get(i).getNombre(), orden.get(i).calcularPromedio(),
                    orden.get(i).obtenerEstado());
        }
    }

    private static void guardarRegistros(GestionAcademica gestion, GestorArchivos gestor) {
        if (gestor.guardar(gestion.getEstudiantes())) {
            System.out.println("Registros guardados en " + GestorArchivos.RUTA + " (" + gestion.getTotal() + ").");
        }
    }
}
