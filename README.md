# Examen-ALP-Bonifaz-Francis
## 1. Identificación

| Campo | Detalle |
|---|---|
| Universidad | Universidad Técnica de Ambato – FISEI |
| Carrera | Ingeniería de Software |
| Asignatura | Algoritmos y Lógica de Programación |
| Estudiante | **[APELLIDO NOMBRE]** |
| Fecha | 27/11/2026 |
| Variante asignada | **C – promedio mínimo de aprobación: 13** |
| Lenguaje | Java |

> Si tu variante es otra, cambia solo la constante `LIMITE_APROBACION` en `src/Estudiante.java` y actualiza `docs/CasosDePrueba.md`.

## 2. Descripción del problema

La Universidad Técnica de Ambato necesita un sistema por consola que permita registrar, consultar, procesar y almacenar las calificaciones de los estudiantes de una asignatura. El sistema conserva los registros al cerrar el programa (archivo de texto) y genera indicadores del rendimiento académico del curso.

## 3. Estructura del repositorio

```
Examen-ALP-Apellido-Nombre/
|-- README.md
|-- src/
|   |-- Main.java
|   |-- Estudiante.java
|   |-- GestionAcademica.java
|   `-- GestorArchivos.java
|-- datos/
|   `-- estudiantes.txt
|-- docs/
|   |-- Analisis.md
|   `-- CasosDePrueba.md
`-- evidencias/
    |-- Ejecucion.png
    `-- salida_consola.txt
```

## 4. Compilación y ejecución

Desde la carpeta raíz del proyecto (importante, porque el programa usa la ruta relativa `datos/estudiantes.txt`):

```
javac -d out src/*.java
java -cp out Main
```

Requiere JDK 8 o superior.

## 5. Funcionalidades implementadas

- **Registro de estudiantes** con validación: código único, código y nombre no vacíos ni con `;`, calificaciones de 0 a 20 (se vuelve a pedir si son incorrectas). Las notas se guardan en un arreglo `int[]` y los estudiantes en un `ArrayList<Estudiante>`.
- **Procesamiento:** promedio sin división entera, nota máxima, nota mínima y estado (APROBADO / REPROBADO según el límite de la variante).
- **Consulta** por código, con mensaje informativo si no existe.
- **Reporte académico:** total, aprobados, reprobados, promedio general, porcentaje de aprobados, promedio más alto y más bajo, estudiantes bajo el promedio general y listado ordenado de mayor a menor (ordenamiento por inserción). Controla lista vacía y evita divisiones entre cero.
- **Persistencia** en `datos/estudiantes.txt` con `FileWriter`/`BufferedWriter` y `FileReader`/`BufferedReader`, recuperación automática al iniciar, manejo de excepciones y tratamiento de archivo inexistente. El archivo se reescribe completo, sin duplicar registros.
- **Menú** que se repite hasta elegir la opción 6; antes de salir se guardan los registros. Las opciones incorrectas no cierran el programa.

## 6. Limitaciones

- El programa debe ejecutarse desde la carpeta raíz del proyecto.
- Si el código ya existe al registrar, se cancela el registro y se vuelve al menú (no se vuelve a pedir el código).
- Las líneas inválidas del archivo se omiten con un aviso en pantalla.
- En caso de empate en el promedio máximo o mínimo se muestra el primer estudiante registrado.

## 7. Control de versiones

1. Análisis y estructura inicial.
2. Implementación de clases y registro.
3. Procesamiento y persistencia.
4. Pruebas y documentación final.
