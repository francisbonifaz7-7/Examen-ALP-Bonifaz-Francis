# Casos de prueba – Sistema AulaLogic

Variante C (límite de aprobación = 13). Los resultados obtenidos corresponden a la ejecución real del programa (ver `evidencias/salida_consola.txt`).

| Caso | Situación | Entrada | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|---|
| CP01 | Registro correcto de un estudiante | Opción 1; código `E001`; nombre `Ana Torres`; notas 15, 18, 12 | Mensaje de registro correcto | "Estudiante registrado correctamente." Promedio 15.00 y estado APROBADO | Aprobado |
| CP02 | Código duplicado | Opción 1; código `E001` (ya existente) | Error y no se registra | "Error: el codigo E001 ya esta registrado. Registro cancelado." | Aprobado |
| CP03 | Calificación fuera de rango | Opción 1; `E003`; `Marta Ruiz`; primera nota `25` | Error y se vuelve a pedir la nota | "Error: la calificacion debe estar entre 0 y 20." y vuelve a solicitar la calificación 1 | Aprobado |
| CP04 | Promedio igual al límite | Opción 1; `E004`; `Carlos Mena`; notas 13, 13, 13 | Promedio 13.00 y estado APROBADO | Promedio 13.00, estado APROBADO | Aprobado |
| CP05 | Búsqueda de estudiante existente | Opción 3; código `E001` | Muestra datos completos | Código, nombre, notas 15, 18, 12, promedio 15.00, máx 18, mín 12, APROBADO | Aprobado |
| CP06 | Búsqueda de estudiante inexistente | Opción 3; código `E999` | Mensaje informativo | "No existe un estudiante con el codigo E999." | Aprobado |
| CP07 | Reporte sin estudiantes | Opción 4 con la lista vacía (sin archivo de datos) | Mensaje, sin error ni división entre cero | "No hay estudiantes registrados. No se puede generar el reporte." | Aprobado |
| CP08 | Guardado y recuperación al reiniciar | Opción 6 (guarda), cerrar, ejecutar de nuevo y opción 2 | Se recuperan los 4 estudiantes | "Registros recuperados: 4" y el listado es igual al anterior | Aprobado |

## Pruebas adicionales

| Situación | Entrada | Resultado obtenido |
|---|---|---|
| Opción incorrecta en el menú | `x` | "Opcion no valida. Ingrese un numero del 1 al 6." El programa continúa |
| Archivo inexistente al iniciar | Sin `datos/estudiantes.txt` | "No existe el archivo ... Se inicia sin registros." |
| Reporte con datos | Opción 4 con 4 estudiantes | Total 4, aprobados 3, reprobados 1, promedio general 14.25, 75.00 %, mejor Marta Ruiz (19.00), peor Luis Perez (10.00) |

Contenido final de `datos/estudiantes.txt`:

```
E001;Ana Torres;15;18;12
E002;Luis Perez;8;10;12
E003;Marta Ruiz;20;18;19
E004;Carlos Mena;13;13;13
```
