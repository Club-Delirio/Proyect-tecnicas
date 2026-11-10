# Checklist de entrega

**Proyecto:** Academia Preuniversitaria San Fernando — sistema de matrícula y control de asistencia

Este archivo tiene dos partes. La **parte 1** es la entrega vigente, la T3 de las semanas 12 y 13. La **parte 2** es la lista de las semanas 7 a 11, que se conserva como registro de esa entrega.

---

# Parte 1. T3 — Semanas 12 y 13

**Lo que pide el curso:**

- **Semana 12:** implementar en Java los requerimientos declarados y personalizados en las historias de usuario.
- **Semana 13:** actualizar el estado de los requerimientos y de sus criterios de aceptación, implementar 10 requerimientos y evidenciar el cumplimiento de los criterios de aceptación.

## A. Rúbrica de la T3 y qué la cubre

| Criterio | Puntos | Qué lo cubre | Estado |
|---|---|---|---|
| Al menos 10 requerimientos funcionales implementados | 6 | 22 de los 45 requerimientos de `requerimientos-funcionales.md` están implementados. Los 7 nuevos de estas semanas son RF-21, RF-27, RF-28, RF-37, RF-40, RF-41 y RF-43 | ✅ |
| Criterios de aceptación con evidencias | 4 | 192 criterios de aceptación. Cada requerimiento implementado enlaza su evidencia en `evidencias/`: 38 capturas y 3 registros de ejecución, con el índice en `evidencias/README.md` | ✅ |
| Interfaz Swing con al menos 3 vistas, navegación, eventos, validaciones y mensajes | 3 | `VentanaPrincipal` con menú y escritorio, y 7 ventanas internas: ciclos, estudiantes, docentes, matrícula, pagos, asistencia y tablero. Su diagrama de clases está en `docs/diagrama_interfaz.png` | ✅ |
| Calidad orientada a objetos: herencia, interfaz, composición y poca duplicación | 3 | Herencia en `Persona` y `Ciclo`; la interfaz `Registrable`; la composición `Matricula ◆— Cuota`; la agregación `Estudiante → Ciclo`; `FormularioBase` reúne lo común de las 7 ventanas | ✅ |
| Informe de práctica de campo: bitácora y evidencias | 2 | `Informe_Practica_Campo_Bitacora.docx`: bitácora semana por semana de las tres entregas, con sus *commits*, *pull requests*, capturas de GitHub y del programa, pruebas y aporte de cada integrante | ✅ |
| Informe de responsabilidad social | 2 | `Informe_Responsabilidad_Social.docx`, de la semana 11 | ✅ En el repositorio. Confirmar con el profesor si la T3 pide uno nuevo |
| Presentación de máximo 15 diapositivas, que sube cada integrante | — | `T3_Academia_San_Fernando.pptx`: 15 diapositivas interactivas con notas del orador. No va al repositorio: cada integrante la sube a la plataforma | ✅ Lista |
| Sustentación individual | — | Lo que explica cada uno está en `reparto-de-tareas.md`, sección de las semanas 12 y 13 | ⬜ Preparar |

## B. Software en el repositorio

| Qué | Dónde | Quién lo sube |
|---|---|---|
| `src/academia/` con las 25 clases | GitHub, rama `main` | Los cuatro, cada uno las de su parte |
| `docs/` actualizados | GitHub, rama `main` | Chuquimango; Cachi el diagrama de clases, que Chuquimango completa al final con la página de la interfaz gráfica |
| `evidencias/` con las 38 capturas y los 3 registros | GitHub, rama `main` | Los cuatro, cada uno las capturas de su parte |
| `README.md` actualizado | GitHub, rama `main` | Chuquimango |

El detalle archivo por archivo y el orden están en `reparto-de-tareas.md`, sección "Qué sube cada uno y en qué orden".

## C. Comprobación final antes de presentar

```bash
git checkout main && git pull origin main
ls src/academia/*.java | wc -l        # deben ser 25
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build academia.AcademiaSanFernando
java -cp build academia.VentanaPrincipal
```

En NetBeans, la carpeta del repositorio se abre con `File > Open Project` (ya tiene `build.xml` y `nbproject/`) y la interfaz gráfica se abre con **F6** (Run Project) o con clic derecho sobre `VentanaPrincipal.java` → **Run File**.

Para comprobar los valores de la interfaz gráfica hay que partir sin la carpeta `datos/`: si quedó de una corrida anterior, borrarla o cambiarle el nombre. Al abrir, el programa ofrece cargar los datos de prueba; aceptar y abrir **Reportes → Tablero del periodo**.

| Comprobación | Valor esperado |
|---|---|
| Archivos en `src/academia/` | 25 |
| Consola: ciclos, personas, matrículas y asistencias | 4, 8 (2 docentes y 6 estudiantes), 6 y 9 |
| Consola: ingreso total, planilla docente e ingreso neto | S/ 6715.04, S/ 4785.36 y S/ 1929.68 |
| Consola: estudiantes en riesgo de deserción | 2 (EST-002 con 33 % y EST-004 con 67 %) |
| Interfaz: barra de estado al cargar los datos de prueba | Estudiantes: 6, Docentes: 2, Ciclos: 4, Matrículas: 6, Asistencias: 9 |
| Tablero: matrículas, ingreso total, recaudado y por cobrar | 6, S/ 6715.04, S/ 2246.08 y S/ 4468.96 |
| Tablero: planilla docente, resultado neto y riesgo de deserción | S/ 4785.36, S/ 1929.68 y 2 estudiantes |
| Tablero: ocupación de vacantes | 3.1 % (6/195) |
| **Archivo → Guardar datos** | Cinco archivos en `datos/`, entre ellos `cuotas.txt` con 11 cuotas |

Lo recaudado son las cuotas que los datos de prueba dejan pagadas: MAT-1 al contado (S/ 1450.00) y la primera cuota de MAT-2 (S/ 517.17) y de MAT-4 (S/ 278.91).

## D. Lo que falta para cerrar la T3

| Qué | Estado |
|---|---|
| Informe de práctica de campo, con la bitácora y las evidencias | ✅ En `docs/` |
| Presentación de máximo 15 diapositivas | ✅ Lista; falta que cada integrante la suba a la plataforma |
| Porcentajes de participación de las carátulas (los informes ponen 100 % a cada integrante) | ✅ Confirmado por el grupo: 100 % cada uno |

---

# Parte 2. Semanas 7 a 11 (entregada)

**Lo que pide el curso:** *Presentar informe y software que incluya los requerimientos y formatos de PC descritos + software en repositorio + Informe de responsabilidad social.*

Eso son cuatro cosas. Aquí está cada una con el archivo que la cubre y el estado.

---

## A. Informe del proyecto

| Archivo | Qué contiene | Estado |
|---|---|---|
| `Informe_Academia_APA7.docx` | 43 páginas en APA 7, solo sobre el proyecto de la academia: planteamiento del problema, 12 restricciones con sus alternativas, objetivos, alcance, los 45 requerimientos, las 15 historias, el diseño, el manejo de archivos, los 60 criterios de aceptación, la implementación con los cuatro controladores, el control de versiones y las conclusiones. El diagrama de clases va en página horizontal al final. | ✅ Listo |

**Falta:** reemplazar los porcentajes de participación de la carátula (están en 25 % cada uno) por los reales, que arroja `git log origin/main --pretty="%an" | sort | uniq -c | sort -rn`.

---

## B. Software con los requerimientos y los formatos de práctica de campo

| Archivo | Qué cubre | Estado |
|---|---|---|
| `AcademiaSanFernando_NetBeans.zip` | El software. Catorce clases (nueve de dominio, cuatro controladores y la clase principal), compila sin errores, se abre con `File → Open Project` y corre con F6. | ✅ Listo |
| `requerimientos-funcionales.md` | 45 requerimientos funcionales con 179 criterios de aceptación. Semanas 8 y 10. | ✅ Listo |
| `historias-usuario.md` | 15 historias de usuario en formato narrativo. Semana 8. | ✅ Listo |
| `Historias_Usuario_Academia.xlsx` | **El formato de PC**: la plantilla oficial del curso llena, con 15 historias y 60 escenarios, cada uno con su clase, su método, sus parámetros de prueba y el mensaje exacto que devuelve el programa. | ✅ Listo |
| `restricciones-y-alternativas.md` | 12 restricciones con sus alternativas evaluadas. Semana 7. | ✅ Listo |
| `objetivos-y-alcance.md` | Objetivos y alcance de la solución. Semana 7. | ✅ Listo |
| `diagrama_clases.png` + `diagrama_clases.svg` + `clases.dot` + `diagrama_clases.drawio` | Diagrama de clases UML, con la versión editable en draw.io. Semana 9. | ✅ Listo |
| `manejo-archivos-java.md` | Investigación y práctica de manejo de archivos en Java. Semana 9. | ✅ Listo |
| `GUIA_GIT_SEMANAS7-11.md` | Guía documentada de Git y GitHub: conexión, comandos de cada integrante, sesión de conflicto y glosario de los 38 comandos usados. | ✅ Listo |
| `reparto-de-tareas.md` | Qué archivo sube cada integrante y en qué orden. | ✅ Listo |

---

## C. Software en el repositorio

| Qué | Dónde | Estado |
|---|---|---|
| `src/academia/` con las catorce clases | GitHub, rama `main` | ⬜ **Por subir** |
| `docs/` con los nueve documentos | GitHub, rama `main` | ⬜ **Por subir** |
| `evidencias/` con las capturas | GitHub, rama `main` | ⬜ **Por subir** |
| `README.md` actualizado | GitHub, rama `main` | ⬜ **Por subir** |
| `.gitignore` actualizado | GitHub, rama `main` | ⬜ **Por subir** |

Los comandos exactos de cada integrante están en `GUIA_GIT_SEMANAS7-11.md`, parte 4.

---

## D. Informe de responsabilidad social

Este informe lo tiene el equipo por separado; no forma parte de los archivos de esta entrega. Solo hay que recordar subirlo al repositorio junto con lo demás, en `docs/`.

| Qué | Dónde | Estado |
|---|---|---|
| Informe de responsabilidad social del equipo | `docs/` del repositorio | ⬜ **Por subir** |

---

## E. Evidencias de Git — lo que el profesor pide ver

### Capturas de la terminal

| # | Comando | Archivo sugerido |
|---|---|---|
| 1 | `git config --list \| grep user` | `evidencias/git-00-identidad.png` |
| 2 | `git remote -v` y `git branch -a` | `evidencias/git-01-remoto-ramas.png` |
| 3 | `git status` antes de un commit | `evidencias/git-02-status.png` |
| 4 | `git log --oneline --graph --all --decorate` | `evidencias/git-03-historial.png` |
| 5 | `git log origin/main --pretty="%an" \| sort \| uniq -c \| sort -rn` | `evidencias/git-04-por-autor.png` |
| 6 | `find src/academia -name "*.java"` (catorce archivos) | `evidencias/git-05-archivos.png` |
| 7 | `javac -encoding UTF-8 -d build $(find src -name "*.java")` sin errores | `evidencias/git-06-compilacion.png` |
| 8 | `git tag -l` | `evidencias/git-07-tag.png` |

### Capturas de GitHub

| # | Pantalla | Archivo sugerido |
|---|---|---|
| 9 | Un pull request con la base en `develop` | `evidencias/github-01-pr-base-develop.png` |
| 10 | Un pull request en estado *Merged* (morado) | `evidencias/github-02-pr-merged.png` |
| 11 | **Pull requests → Closed**, la lista completa | `evidencias/github-03-prs-cerrados.png` |
| 12 | **Insights → Contributors**, con los cuatro | `evidencias/github-04-contributors.png` |
| 13 | **Insights → Network**, el gráfico de ramas | `evidencias/github-05-network.png` |

### Capturas de la ejecución

| # | Pantalla | Archivo sugerido |
|---|---|---|
| 14 | Menú inicial y carga de datos de prueba | `evidencias/run-01-inicio.png` |
| 15 | Catálogo de la oferta agrupado por categoría | `evidencias/run-02-catalogo.png` |
| 16 | Registro de una matrícula con el desglose del precio | `evidencias/run-03-matricula.png` |
| 17 | Intento de matricular en un ciclo sin vacantes o ya matriculado | `evidencias/run-04-sin-vacantes.png` |
| 18 | Registro de asistencia y reporte con alerta de riesgo de deserción | `evidencias/run-05-asistencia.png` |
| 19 | Historial, estadísticas y reporte financiero | `evidencias/run-06-reportes.png` |
| 20 | Guardado en archivos (las cinco líneas de `Se guardaron...`) | `evidencias/run-07-guardado.png` |
| 21 | Contenido de `personas.txt` con los discriminadores `DOCENTE` y `ESTUDIANTE` | `evidencias/run-08-archivo.png` |
| 22 | Recuperación en una ejecución posterior, con `Vacantes 3/30` conservadas | `evidencias/run-09-recuperado.png` |

Las capturas 20 a 22 son la evidencia de la semana 9 y conviene tomarlas seguidas, en una sola corrida.

---

## F. Comprobación final antes de presentar

```bash
git checkout main && git pull origin main
find src/academia -name "*.java" | sort        # deben ser catorce
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build academia.AcademiaSanFernando
```

Los catorce archivos que debe listar el `find`:

```
src/academia/AcademiaSanFernando.java
src/academia/ArchivoUtil.java
src/academia/Asistencia.java
src/academia/AsistenciaController.java
src/academia/Ciclo.java
src/academia/CicloController.java
src/academia/CicloRegular.java
src/academia/CursoIntensivo.java
src/academia/Docente.java
src/academia/Estudiante.java
src/academia/Matricula.java
src/academia/MatriculaController.java
src/academia/Persona.java
src/academia/PersonaController.java
```

Al correr con los datos de prueba, los números que deben salir son:

| Reporte | Valor esperado |
|---|---|
| Clases en `src/academia/` | 14 |
| Ciclos en la oferta | 4 |
| Personas registradas | 8 (2 docentes y 6 estudiantes) |
| Matrículas registradas | 6 |
| Registros de asistencia | 9 |
| Ingreso total | S/ 6715.04 |
| Planilla docente | S/ 4785.36 |
| Ingreso neto | S/ 1929.68 |
| Estudiantes en riesgo de deserción | 2 (EST-002 con 33 % y EST-004 con 67 %) |
| Vacantes del ciclo CIC-001 | 3/30 |

Si alguno de esos números no coincide, algún archivo quedó desactualizado en el repositorio.

---

## Orden recomendado para los próximos días

| Día | Qué hacer |
|---|---|
| 1 | Los cuatro suben su código. Fusionar en el orden de la sección 4.5 de la guía. |
| 1 | Los cuatro suben sus documentos, en paralelo con lo anterior. |
| 2 | Hacer la sesión de conflicto provocado (parte 5 de la guía) y capturarla. |
| 2 | Fusionar `develop` en `main`, etiquetar `v2.0.0`, tomar las capturas 1 a 13. |
| 3 | Correr el programa de principio a fin y tomar las capturas 14 a 22. |
| 3 | Ejecutar la actividad de responsabilidad social y llenar su informe. |
| 4 | Recalcular los porcentajes de participación y corregirlos en la carátula del informe. |
| 4 | Subir las evidencias al repositorio y presentar. |
