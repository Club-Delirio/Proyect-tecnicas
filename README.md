# Técnicas de Programación Orientada a Objetos — SIST1202A

Repositorio compartido del equipo para las actividades de práctica de campo del curso
**Técnicas de Programación Orientada a Objetos** (Ingeniería de Sistemas Computacionales,
UPN Cajamarca, periodo 2026-2).

**Proyecto:** *Academia Preuniversitaria San Fernando* — sistema de gestión de matrícula y control de asistencia.

## Integrantes

| Etiqueta | Apellidos y nombres | Usuario de GitHub | Correo de `git config user.email` |
|---|---|---|---|
| Integrante 1 | Chuquimango Verastegui Jhocep Jhanpier | JhocepGab | snkje123@gmail.com |
| Integrante 2 | CACHI TANTALEAN ANDERSON ALEXIS | Anderson-Cachi | n00461903@upn.pe |
| Integrante 3 | ROJAS VALDIVIA JHANN FRANK | Jhann-999 | rojasjhann7@gmail.com |
| Integrante 4 | RODRIGO ORTIZ SUMIL ARTURI | init57 | rubick042@gmail.com |

## Contenido del repositorio

| Carpeta / archivo | Descripción |
|---|---|
| `src/academia/` | Sistema de matrícula y asistencia. Entrega de las semanas 7 a 11: 14 clases |
| `src/semana5/` | Práctica de sobrecarga, manejo de errores y colecciones. Semanas 1 a 6 |
| `docs/restricciones-y-alternativas.md` | 12 restricciones del proyecto y sus alternativas de solución (semana 7) |
| `docs/objetivos-y-alcance.md` | Objetivos del proyecto y alcance de la solución (semana 7) |
| `docs/requerimientos-funcionales.md` | 45 requerimientos funcionales con 179 criterios de aceptación (semanas 8 y 10) |
| `docs/historias-usuario.md` | 15 historias de usuario con sus escenarios (semana 8) |
| `docs/Historias_Usuario_Academia.xlsx` | Matriz de historias de usuario en la plantilla del curso |
| `docs/manejo-archivos-java.md` | Investigación y práctica de manejo de archivos en Java (semana 9) |
| `docs/diagrama_clases.png` | Diagrama de clases UML del dominio, en imagen (semana 9) |
| `docs/diagrama_clases.svg` | El mismo diagrama en vectorial, para imprimir o ampliar sin perder nitidez |
| `docs/diagrama_clases.drawio` | El mismo diagrama en formato draw.io, para abrirlo y editarlo en app.diagrams.net. Muestra las ocho clases de dominio; la Figura 1 del informe incluye además los cuatro controladores, `ArchivoUtil` y la clase principal |
| `docs/Informe_Academia_APA7.docx` | Informe del proyecto en formato APA 7 |
| `docs/Informe_Responsabilidad_Social.docx` | Informe de la actividad de responsabilidad social (semana 11) |
| `docs/reparto-de-tareas.md` | Qué hizo cada integrante en esta entrega |
| `docs/GUIA_GIT_SEMANAS7-11.md` | Comandos de Git y GitHub que ejecuta cada integrante |
| `docs/CHECKLIST_ENTREGA.md` | Lista de comprobación antes de presentar |
| `.gitignore` | Excluye los compilados y la carpeta de datos generada en ejecución |

## Requisitos

- JDK 17 o superior
- Apache NetBeans 21 o superior

## Cómo ejecutarlo

### Desde la consola

```bash
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build academia.AcademiaSanFernando
```

Para la entrega anterior, la clase principal es `semana5.Semana5`.

### Desde Apache NetBeans

1. `File > Open Project...`
2. Seleccionar la carpeta del proyecto.
3. Pulsar **F6**. La clase principal configurada es `academia.AcademiaSanFernando`.

### Primer paso recomendado

Al iniciar, el programa pregunta si desea recuperar los datos guardados. En la primera ejecución
responder `n` y luego `s` a **cargar datos de prueba**: se crean 2 docentes, 6 estudiantes,
4 ciclos, 6 matrículas y 9 registros de asistencia, de modo que la oferta y los reportes se puedan
probar sin digitar nada.

## El flujo del programa

| Sección | Operación |
|---|---|
| 0 | Recuperar los datos guardados en los archivos |
| — | Cargar datos de prueba |
| 1 | Registro de ciclos regulares y cursos intensivos, con control de código duplicado |
| — | Catálogo de la oferta agrupado por categoría |
| 2 | Registro de docentes y horas dictadas |
| 3 | Registro de estudiantes, con reconocimiento del que ya existe |
| 4 | Configuración de la campaña de descuento del periodo |
| 5 | Cotización y registro de matrículas |
| 6 | Control de asistencia diaria |
| 7 | Listados, historial, estadísticas, reporte financiero y reporte de asistencia |
| 8 | Guardar la información y exportar el historial a CSV |

## Las catorce clases

| Clase | Tipo | Responsabilidad |
|---|---|---|
| `Persona` | abstracta | Datos y validaciones comunes del estudiante y del docente |
| `Estudiante` | extiende Persona | Código, fecha de nacimiento, colegio, carrera objetivo, apoderado e historial |
| `Docente` | extiende Persona | Código, especialidad, tarifa por hora, bonificación y acumulados |
| `Ciclo` | abstracta | Datos y validaciones comunes de la oferta, y el control de vacantes |
| `CicloRegular` | extiende Ciclo | Turno, meses de duración y número de simulacros |
| `CursoIntensivo` | extiende Ciclo | Área, semanas de duración y horas diarias |
| `Matricula` | asociación | Relaciona estudiante, docente tutor y ciclo, y calcula el precio final |
| `Asistencia` | asociación | Relaciona estudiante y ciclo en una fecha, con su estado y observación |
| `PersonaController` | controlador | Guarda la colección de personas y aplica las reglas de alta, búsqueda y conteo |
| `CicloController` | controlador | Lo mismo para la oferta académica |
| `MatriculaController` | controlador | Aplica el control de aforo y de matrícula duplicada, y calcula los totales |
| `AsistenciaController` | controlador | Impide el doble registro y calcula el porcentaje de asistencia |
| `ArchivoUtil` | utilitaria | Única clase que importa `java.io`: lectura y escritura de archivos |
| `AcademiaSanFernando` | principal | Flujo de trabajo de la academia; se apoya en los cuatro controladores |

## Persistencia

La sección 8 crea la carpeta `datos/` con un archivo de texto por colección, delimitado por punto
y coma: `ciclos.txt`, `personas.txt`, `matriculas.txt`, `asistencias.txt` y
`reporte_matriculas.csv`. Esa carpeta se genera en tiempo de ejecución y está excluida del control
de versiones.

El orden de carga es obligatorio: **ciclos → personas → matrículas → asistencias**, porque las
matrículas y las asistencias guardan identificadores que deben volver a enlazarse con objetos ya
cargados.

## Convención de trabajo

- Ramas: `main` (estable) y `develop` (integración).
- Ramas de tarea con los prefijos `feat/`, `fix/` y `docs/`.
- Mensajes de commit convencionales: `feat:`, `fix:`, `docs:`, `refactor:`.
- Todo cambio entra por **pull request con base en `develop`**, nunca directo a `main`.
- Atributos en minúsculas con guion bajo; `protected` en las clases abstractas y `private` en las
  hijas; métodos en minúscula inicial; validación dentro de los `set` con mensajes que empiezan
  por `Error:`; el comentario Javadoc de cada clase firmado por los cuatro integrantes.
