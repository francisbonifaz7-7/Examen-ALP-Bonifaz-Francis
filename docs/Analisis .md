# Análisis del problema – Sistema AulaLogic

## 1. Entrada, proceso y salida

**Entrada**
- Opción del menú (entero del 1 al 6).
- Por estudiante: código (String), nombre completo (String) y tres calificaciones (int, de 0 a 20).
- Código a buscar (String).
- Archivo `datos/estudiantes.txt` al iniciar el programa.

**Proceso**
- Validar código (único, no vacío, sin `;`), nombre (no vacío, sin `;`) y calificaciones (0 a 20).
- Calcular promedio = (n1 + n2 + n3) / 3.0, nota máxima, nota mínima y estado.
- Estado: APROBADO si promedio >= 13 (variante C), REPROBADO en caso contrario.
- Calcular indicadores del curso con contadores y acumuladores.
- Ordenar de mayor a menor promedio.
- Leer y escribir el archivo de texto.

**Salida**
- Menú, mensajes de validación y de error.
- Listado de estudiantes, ficha de un estudiante y reporte académico.
- Archivo `datos/estudiantes.txt` actualizado.

## 2. Datos del sistema

| Dato | Tipo | Observación |
|---|---|---|
| LIMITE_APROBACION | `int` (constante) | 13 (variante C) |
| codigo, nombre | `String` | únicos / sin `;` |
| notas | `int[3]` | 0 a 20 |
| promedio | `double` | división real con `3.0` |
| estudiantes | `ArrayList<Estudiante>` | lista dinámica |

## 3. Diseño de clases

- **Estudiante:** atributos privados `codigo`, `nombre`, `notas`. Métodos: constructor, getters, `calcularPromedio()`, `obtenerNotaMaxima()`, `obtenerNotaMinima()`, `obtenerEstado()`.
- **GestionAcademica:** administra `ArrayList<Estudiante>`. Métodos: `registrar`, `buscar`, `existeCodigo`, `getEstudiantes`, `contarAprobados`, `contarReprobados`, `calcularPromedioGeneral`, `calcularPorcentajeAprobados`, `obtenerMejorPromedio`, `obtenerPeorPromedio`, `obtenerBajoPromedioGeneral`, `ordenarPorPromedioDescendente`, y las validaciones estáticas `textoValido` y `notaValida`.
- **GestorArchivos:** `guardar(lista)` y `cargar()`, con control de errores de entrada y salida.
- **Main:** muestra el menú, lee las opciones y coordina las tres clases.

Colaboración: `Main` usa `GestionAcademica` y `GestorArchivos`; `GestionAcademica` contiene objetos `Estudiante`; `GestorArchivos` convierte líneas del archivo en objetos `Estudiante` y viceversa.

## 4. Algoritmo principal (pseudocódigo)

```
INICIO
  gestion <- nueva GestionAcademica
  gestion.estudiantes <- GestorArchivos.cargar()
  REPETIR
    mostrar menú
    leer opción
    SEGUN opción
      1: registrar estudiante
      2: listar estudiantes
      3: buscar estudiante
      4: generar reporte
      5: guardar registros
      6: salir
      otro: mostrar "Opcion no valida"
  HASTA opción = 6
  GestorArchivos.guardar(gestion.estudiantes)
FIN
```

Registro de un estudiante:

```
leer codigo HASTA que sea valido
SI codigo ya existe ENTONCES mostrar error y terminar
leer nombre HASTA que sea valido
PARA i <- 1 HASTA 3
  leer nota HASTA que este entre 0 y 20
FIN PARA
agregar estudiante a la lista
```

Reporte:

```
SI lista vacia ENTONCES mostrar mensaje y terminar
aprobados <- 0 ; acumulador <- 0
PARA cada estudiante
  acumulador <- acumulador + promedio
  SI promedio >= limite ENTONCES aprobados <- aprobados + 1
FIN PARA
promedioGeneral <- acumulador / total
porcentaje <- aprobados * 100 / total
```

## 5. Prueba de escritorio

Límite de aprobación: 13.

| Estudiante | Notas | Suma | Promedio | Máx | Mín | Estado |
|---|---|---|---|---|---|---|
| E002 Luis Perez | 8, 10, 12 | 30 | 30 / 3.0 = 10.00 | 12 | 8 | REPROBADO (10 < 13) |
| E004 Carlos Mena | 13, 13, 13 | 39 | 39 / 3.0 = 13.00 | 13 | 13 | APROBADO (13 >= 13) |

Datos del curso (E001 = 15.00, E002 = 10.00, E003 = 19.00, E004 = 13.00):

- Suma de promedios = 15 + 10 + 19 + 13 = 57
- Promedio general = 57 / 4 = 14.25
- Aprobados = 3 (E001, E003, E004), reprobados = 1 (E002)
- Porcentaje de aprobados = 3 × 100 / 4 = 75.00 %
- Inferiores a 14.25: E002 (10.00) y E004 (13.00)
- Orden descendente: E003, E001, E004, E002
