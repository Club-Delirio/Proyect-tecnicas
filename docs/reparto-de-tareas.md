# Reparto de tareas — Semanas 7 a 13

Este archivo tiene dos partes: el reparto de las semanas 7 a 11 y, al final, el de las semanas 12 y 13 (T3).

**Proyecto:** Academia Preuniversitaria San Fernando — sistema de matrícula y control de asistencia
**Repositorio:** `Club-Delirio/Proyect-tecnicas`
**Paquete:** `academia`

---

## Qué entra en esta entrega

El sistema se compone de **catorce clases** en el paquete `academia`: nueve de dominio (dos jerarquías de herencia y dos clases asociación), cuatro controladores y la clase principal.

| Archivo | Qué contiene |
|---|---|
| `Persona.java` | Clase abstracta: documento, nombres, teléfono y sus validaciones. Declara `verDatos()` y `convertirALinea()` |
| `Estudiante.java` | Extiende `Persona`. Código, fecha de nacimiento, colegio, carrera objetivo, apoderado e historial de matrículas |
| `Docente.java` | Extiende `Persona`. Código, especialidad, tarifa por hora, bonificación y acumulados del periodo |
| `Ciclo.java` | Clase abstracta: código, fechas, costo, modalidad, vacantes y estado. Declara cinco métodos abstractos |
| `CicloRegular.java` | Extiende `Ciclo`. Turno, meses de duración y número de simulacros |
| `CursoIntensivo.java` | Extiende `Ciclo`. Área, semanas de duración y horas diarias |
| `Matricula.java` | Clase asociación: estudiante, docente tutor y ciclo. Calcula el precio final |
| `Asistencia.java` | Clase asociación: estudiante, ciclo y fecha, con su estado y su observación |
| `PersonaController.java` | Controlador: guarda la colección de personas y aplica las reglas de alta, búsqueda, listado, eliminación, ordenamiento y conteo |
| `CicloController.java` | Controlador de la oferta académica, con las mismas operaciones sobre la colección de ciclos |
| `MatriculaController.java` | Controlador: aplica el control de aforo y de matrícula duplicada, y calcula los totales del reporte financiero |
| `AsistenciaController.java` | Controlador: impide el doble registro y calcula el porcentaje de asistencia de cada estudiante |
| `ArchivoUtil.java` | Única clase que importa `java.io`. Todo el manejo de archivos |
| `AcademiaSanFernando.java` | Clase principal. Las nueve secciones del flujo y nueve métodos estáticos de archivo |

---

## Reglas para los cuatro

1. **Todo se puede hacer desde la web de GitHub.** No hace falta la terminal. Los comandos equivalentes están en `docs/GUIA_GIT_SEMANAS7-11.md`.
2. **Todo pull request va con base `develop`**, nunca `main`. GitHub pone `main` por defecto: hay que cambiarlo a mano en el desplegable antes de crear el PR.
3. **Subir la rama no es terminar.** Después de crear el PR hay que entrar y darle **Merge pull request → Confirm merge**. Un PR abierto no cuenta como trabajo entregado.
4. **Captura de pantalla de cada commit fusionado.** Esa es la evidencia de participación.
5. Avisar al grupo después de cada fusión, para que el siguiente pueda continuar.

---

## Cómo subir un archivo desde la web

### Para un archivo nuevo

1. Entrar al repositorio en GitHub.
2. **Add file → Upload files**.
3. Arrastrar el archivo.
4. **Importante:** GitHub lo deja en la raíz del repositorio. Para que caiga en su carpeta, hacer clic sobre el nombre del archivo en la lista que aparece y escribir la carpeta delante, por ejemplo:

```
src/academia/Persona.java
```

5. Abajo: escribir el mensaje del commit → marcar **"Create a new branch for this commit and start a pull request"** → **Propose changes**.
6. Cambiar la base de `main` a **`develop`** → **Create pull request** → **Merge pull request** → **Confirm merge**.

Se pueden arrastrar varios archivos a la vez y subirlos en un solo commit, siempre que cada uno lleve su carpeta delante.

### Para reemplazar un archivo que ya existe

Entrar al archivo → clic en el lápiz ✏️ → seleccionar todo y borrar → pegar el contenido nuevo → mismo procedimiento de commit.

---

## De dónde salen los archivos

Todos están en `AcademiaSanFernando_NetBeans.zip` y en la carpeta de entrega. Descomprimirlo una vez y arrastrar desde ahí los que a cada uno le corresponden.

---

# CHUQUIMANGO VERÁSTEGUI, JHOCEP JHANPIER — `JhocepGab`

**Clases a su cargo:** `Persona`, `Estudiante`, `PersonaController`, `ArchivoUtil`, `AcademiaSanFernando`.
**Historias de usuario:** HU-05 (RF-15), HU-06 (RF-16), HU-07 (RF-17), HU-15 (RF-39).

| # | Archivo | Mensaje del commit |
|---|---|---|
| J1 | `src/academia/Persona.java` | `feat: agrega la clase abstracta Persona con sus validaciones de documento` |
| J2 | `src/academia/Estudiante.java` | `feat: agrega la clase Estudiante con su edad, apoderado e historial de matriculas` |
| J3 | `src/academia/PersonaController.java` | `feat: agrega el controlador de personas con busqueda, orden y sobrecarga de listado` |
| J4 | `src/academia/ArchivoUtil.java` | `feat: agrega el manejo de archivos con BufferedReader y BufferedWriter` |
| J5 | `src/academia/AcademiaSanFernando.java` | `feat: agrega el programa principal con recuperacion y guardado en archivos` |
| J6 | `docs/Informe_Academia_APA7.docx` | `docs: agrega el informe del proyecto de las semanas 7 a 11 en formato APA` |
| J7 | `README.md` (reemplazar) | `docs: actualiza el README con la estructura del sistema de la academia` |

**J7 en detalle.** Entrar a `README.md` → lápiz ✏️ → borrar todo → pegar el contenido de `README_NUEVO.md`.

**J5 va al final.** `AcademiaSanFernando.java` llama a todas las demás clases: hasta que las trece estén fusionadas, el proyecto no compila.

---

# ROJAS VALDIVIA, JHANN FRANK — `Jhann-999`

**Clases a su cargo:** `Ciclo`, `Docente`, `CicloController`.
**Historias de usuario:** HU-01 (RF-01), HU-02 (RF-02), HU-04 (RF-09), HU-13 (RF-35).

| # | Archivo | Mensaje del commit |
|---|---|---|
| R1 | `src/academia/Ciclo.java` | `feat: agrega la clase abstracta Ciclo con el control de vacantes y aforo` |
| R2 | `src/academia/Docente.java` | `feat: agrega la clase Docente con el calculo de su pago y su bonificacion` |
| R3 | `src/academia/CicloController.java` | `feat: agrega el controlador de ciclos con el control de codigo duplicado` |
| R4 | `docs/restricciones-y-alternativas.md` | `docs: documenta las 12 restricciones del proyecto y sus alternativas` |
| R5 | `docs/objetivos-y-alcance.md` | `docs: documenta los objetivos del proyecto y el alcance de la solucion` |
| R6 | `docs/GUIA_GIT_SEMANAS7-11.md` | `docs: agrega la guia documentada de Git y GitHub del equipo` |
| R7 | `docs/CHECKLIST_ENTREGA.md` | `docs: agrega el checklist de entrega de las semanas 7 a 11` |

**R1 va primero**, junto con J1: `Ciclo` y `Persona` son las clases base y sin ellas no compilan las demás.

---

# CACHI TANTALEÁN, ANDERSON ALEXIS — `Anderson-Cachi`

**Clases a su cargo:** `CicloRegular`, `CursoIntensivo`, `Asistencia`, `AsistenciaController`.
**Historias de usuario:** HU-03 (RF-03), HU-08 (RF-22), HU-12 (RF-31), HU-14 (RF-36).

| # | Archivo | Mensaje del commit |
|---|---|---|
| A1 | `src/academia/CicloRegular.java` | `feat: agrega la clase CicloRegular con su categoria y su descuento virtual` |
| A2 | `src/academia/CursoIntensivo.java` | `feat: agrega la clase CursoIntensivo con su categoria por horas diarias` |
| A3 | `src/academia/Asistencia.java` | `feat: agrega la clase asociacion Asistencia con sus cuatro estados` |
| A4 | `src/academia/AsistenciaController.java` | `feat: agrega el controlador de asistencia con el calculo del porcentaje` |
| A5 | `docs/diagrama_clases.png`, `docs/diagrama_clases.svg`, `docs/clases.dot`, `docs/diagrama_clases.drawio` | `docs: agrega el diagrama de clases UML del sistema y su codigo fuente` |
| A6 | `docs/requerimientos-funcionales.md` | `docs: documenta 45 requerimientos funcionales con sus criterios de aceptacion` |
| A7 | `docs/manejo-archivos-java.md` | `docs: agrega la investigacion y practica de manejo de archivos en Java` |

**A5 en detalle.** Los cuatro archivos van en un solo commit: arrastrarlos juntos y escribir `docs/` delante del nombre de cada uno. El `.drawio` es la versión editable del diagrama: se abre en `app.diagrams.net` con **File → Open From → Device**.

---

# RODRIGO ORTIZ, SUMIL ARTURI — `init57`

**Clases a su cargo:** `Matricula`, `MatriculaController`.
**Historias de usuario:** HU-09 (RF-23), HU-10 (RF-24), HU-11 (RF-25).

| # | Archivo | Mensaje del commit |
|---|---|---|
| S1 | `src/academia/Matricula.java` | `feat: agrega la clase asociacion Matricula con el calculo del precio final` |
| S2 | `src/academia/MatriculaController.java` | `feat: agrega el controlador de matriculas con el control de aforo y duplicados` |
| S3 | `docs/historias-usuario.md` | `docs: agrega las 15 historias de usuario con sus escenarios de aceptacion` |
| S4 | `docs/Historias_Usuario_Academia.xlsx` | `docs: agrega la matriz de historias de usuario en la plantilla del curso` |
| S5 | `docs/reparto-de-tareas.md` | `docs: documenta el reparto de tareas de las semanas 7 a 11` |
| S6 | `.gitignore` (reemplazar) | `chore: excluye del control de versiones los compilados y la carpeta datos` |
| S7 | `evidencias/` | `docs: agrega las capturas de la ejecucion del sistema en NetBeans` |

---

## Orden de fusión

El proyecto solo compila cuando las catorce clases están. Para no quedar bloqueados, este es el orden:

1. **J1** `Persona.java` y **R1** `Ciclo.java` — las dos clases abstractas, en paralelo.
2. **J2** `Estudiante.java`, **R2** `Docente.java`, **A1** `CicloRegular.java`, **A2** `CursoIntensivo.java` — las cuatro hijas, en paralelo.
3. **S1** `Matricula.java` y **A3** `Asistencia.java` — necesitan `Estudiante`, `Docente` y `Ciclo`.
4. **J3** `PersonaController.java`, **R3** `CicloController.java`, **S2** `MatriculaController.java`, **A4** `AsistenciaController.java` — cada controlador necesita la clase cuya colección guarda, de modo que van después del paso 3.
5. **J4** `ArchivoUtil.java` — no depende de nadie, puede ir antes si se quiere.
6. **J5** `AcademiaSanFernando.java` — el último, cuando las trece anteriores estén fusionadas.

Los documentos (J6, J7, R4 a R7, A5 a A7, S3 a S6) son independientes del código y se pueden fusionar en cualquier momento. El commit S7 va al final, cuando el programa ya corra y haya capturas que subir.

---

## Verificación final

Cuando los catorce pull requests de código estén fusionados en `develop`, fusionar `develop` en `main` y comprobar:

```bash
git checkout main
git pull origin main
find src/academia -name "*.java"
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build academia.AcademiaSanFernando
```

El `find` debe listar **exactamente catorce archivos**. El `javac` no debe devolver ningún error. Al ejecutar, responder `n` a recuperar los datos y `s` a cargar los datos de prueba, y recorrer el programa hasta el final.

📸 **Capturar la compilación sin errores, el catálogo por categoría, el reporte financiero y el reporte de asistencia con la alerta de riesgo de deserción.** Esas capturas van como evidencia.

---

## Prueba de la persistencia

Es la evidencia de la semana 9 y conviene capturarla por separado:

1. Ejecutar, responder `n` a recuperar y `s` a cargar datos de prueba.
2. Responder `n` a todas las secciones de registro, matrícula y asistencia.
3. Responder `s` a guardar la información. 📸 **Capturar** las cinco líneas de `Se guardaron n registro(s)`.
4. Responder `s` a ver el contenido de un archivo e indicar `personas.txt`. 📸 **Capturar** el contenido, donde se ven los discriminadores `DOCENTE` y `ESTUDIANTE`.
5. Cerrar el programa y volver a ejecutarlo. Responder `s` a recuperar los datos. 📸 **Capturar** `Se recuperaron 8 personas, 4 ciclos, 6 matriculas y 9 asistencias de los archivos`.
6. Llegar al catálogo por categoría y comprobar que el ciclo anual sigue marcando `Vacantes 3/30`. 📸 **Capturar**.

---

## Después de fusionar: recalcular la participación

```bash
git log origin/main --pretty="%an" | sort | uniq -c | sort -rn
```

Los porcentajes de la carátula del informe están puestos en **25 % para cada uno**, que es el reparto planificado. Hay que reemplazarlos por los porcentajes reales que arroje ese comando antes de presentar el trabajo.

---

## Los comandos exactos

Están en `docs/GUIA_GIT_SEMANAS7-11.md`, bloque por bloque para copiar y pegar, junto con la configuración de la conexión a GitHub, la sesión de conflicto provocado y los comandos de inspección que cada uno debe ejecutar y capturar.

---

# Semanas 12 y 13 — interfaz gráfica, composición e interfaz `Registrable`

En la sustentación de la T3 cada integrante explica, además de lo suyo de las semanas 7 a 11, las ventanas y las clases que extienden su parte del modelo. El reparto sigue al de las semanas anteriores: cada uno toma la ventana de las clases que ya conoce.

| Integrante | Historias | Requerimientos | Clases y ventanas que explica |
|---|---|---|---|
| Cachi Tantaleán, Anderson Alexis | HU-19 | RF-37 | `FrmAsistencia`, `FrmTablero` y los métodos de conteo de `AsistenciaController` y `MatriculaController` |
| Chuquimango Verástegui, Jhocep Jhanpier | HU-16, HU-21 | RF-21, RF-41 | `FrmEstudiantes`, `Estudiante.calcularTipoEstudiante()`, `ArchivoUtil.crearRespaldo()` y `guardarRegistros()` |
| Rodrigo Ortiz, Sumil Arturo | HU-17, HU-18 | RF-27, RF-28 | `Cuota`, la composición dentro de `Matricula`, `FrmMatricula`, `FrmPagos` y `MatriculaController.eliminarMatricula()` |
| Rojas Valdivia, Jhann Frank | HU-20, HU-22 | RF-40, RF-43 | `VentanaPrincipal` (menú, escritorio y carga automática), `FormularioBase`, `FrmCiclos` y `FrmDocentes` |

La interfaz `Registrable` y la relación de agregación entre `Estudiante` y `Ciclo` las explican los cuatro: son las preguntas de relaciones entre clases que la rúbrica de la T3 evalúa a todos.

## Qué sube cada uno y en qué orden

Cada integrante sube su parte desde su propia cuenta, en la rama `feat/semanas-12-13`, para que GitHub registre el aporte de cada uno. Los archivos salen del paquete `SEMANAS_12_13.zip`, que ya trae todo revisado: nadie tiene que modificar nada, solo copiarlo y subirlo.

| Turno | Integrante | Archivos que sube |
|---|---|---|
| 1 | Chuquimango Verástegui, Jhocep Jhanpier | `Registrable.java`, `Persona.java`, `Estudiante.java`, `ArchivoUtil.java`, `FrmEstudiantes.java`; `docs/requerimientos-funcionales.md`, `docs/historias-usuario.md`, `docs/Historias_Usuario_Academia.xlsx`, `docs/reparto-de-tareas.md`, `docs/objetivos-y-alcance.md`, `docs/restricciones-y-alternativas.md`, `docs/ishikawa.png`, `docs/ishikawa.svg`, `README.md`, `evidencias/README.md`, `evidencias/log_consola.txt`, `evidencias/log_interfaz.txt`, `evidencias/log_pruebas_dominio.txt`; 7 capturas (07, 08, 09, 09b, 17, 17b, 29) |
| 2 | Rodrigo Ortiz, Sumil Arturo | `Cuota.java`, `Matricula.java`, `MatriculaController.java`, `Ciclo.java`, `Docente.java`, `FrmMatricula.java`, `FrmPagos.java`; 14 capturas (11, 12, 13, 14, 14b, 15, 16, 18, 19, 20, 21, 22, 23, 24) |
| 3 | Cachi Tantaleán, Anderson Alexis | `Asistencia.java`, `AsistenciaController.java`, `FrmAsistencia.java`, `FrmTablero.java`; `docs/diagrama_clases.drawio`, `docs/diagrama_clases.png`, `docs/diagrama_clases.svg`; 4 capturas (25, 26, 27, 28) |
| 4 | Rojas Valdivia, Jhann Frank | `FormularioBase.java`, `VentanaPrincipal.java`, `FrmCiclos.java`, `FrmDocentes.java`, `AcademiaSanFernando.java`; 13 capturas (00, 00b, 01, 02, 03, 04, 05, 06, 10, 10b, 30, 31, 32) |

El turno 1 crea la rama, así que va primero; los otros tres pueden subir en cualquier orden. La rama solo compila cuando están las cuatro partes, porque `VentanaPrincipal` abre todas las ventanas y estas usan las clases de los demás; por eso se trabaja en una rama aparte y no directamente en `develop`. El orden real en que subió cada uno queda en el historial de la rama (`git log feat/semanas-12-13`).

Las clases `CicloController`, `CicloRegular`, `CursoIntensivo` y `PersonaController` no cambiaron en estas semanas y no se vuelven a subir.

Chuquimango sube además el cierre de la entrega: el informe de práctica de campo, el diagrama de clases con su segunda página (la interfaz gráfica, sin controladores, por convención del curso), la lista de comprobación actualizada y los archivos del proyecto de NetBeans (`build.xml` y `nbproject/`), para que el repositorio se abra directamente con `File > Open Project`. El diagrama va después del turno 3 porque reemplaza la exportación que sube Cachi. La rama se fusionó en `main` con el pull request #81 (09/11) cuando todavía faltaba la parte de Rodrigo; con las cuatro partes y el cierre, se integra en `develop` y luego en `main` con dos pull requests más, de modo que ambas ramas terminan con el mismo contenido.

## Verificación final de las semanas 12 y 13

```bash
git checkout main
git pull origin main
ls src/academia/*.java | wc -l
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build academia.AcademiaSanFernando
java -cp build academia.VentanaPrincipal
```

El `ls` debe contar **25 archivos** y el `javac` no debe devolver ningún error. La versión de consola debe mostrar los mismos resultados de la verificación de las semanas 7 a 11. Para la interfaz gráfica, ejecutar en una carpeta sin `datos/`, aceptar los datos de prueba y abrir **Reportes → Tablero del periodo**; los valores que debe mostrar están en `docs/CHECKLIST_ENTREGA.md`, parte 1.
