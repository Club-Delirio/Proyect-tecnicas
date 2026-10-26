# Restricciones del proyecto y alternativas de solución

**Proyecto:** Sistema de gestión de matrícula y control de asistencia — Academia Preuniversitaria San Fernando
**Curso:** Técnicas de Programación Orientada a Objetos (SIST1202A) — UPN Cajamarca, 2026-2
**Equipo:** Cachi Tantaleán Anderson Alexis · Chuquimango Verástegui Jhocep Jhanpier · Rodrigo Ortiz Sumil Arturi · Rojas Valdivia Jhann Frank
**Semana del sílabo:** 7

---

## 1. Propósito de este documento

Una restricción es una condición que el equipo no puede cambiar y que limita las decisiones de diseño. No es un problema a resolver: es un límite dentro del cual hay que construir. Confundir una restricción con un requerimiento lleva a prometer lo que no se puede entregar.

Este documento identifica las restricciones reales del proyecto, evalúa las alternativas disponibles frente a cada una y deja constancia de la decisión adoptada y de su consecuencia. Las decisiones registradas aquí explican por qué el sistema está construido como está, y son la base de los objetivos y del alcance definidos en `docs/objetivos-y-alcance.md`.

---

## 2. Clasificación de las restricciones

| Tipo | Restricciones | Origen |
|---|---|---|
| Técnicas | R-01, R-02, R-03 | Herramientas y conocimientos disponibles |
| De tiempo | R-04, R-05 | Calendario académico del curso |
| De recursos | R-06, R-07 | Equipo humano y datos disponibles |
| De alcance | R-08, R-09 | Decisión académica del curso |
| Del negocio | R-10, R-11 | Realidad operativa de una academia preuniversitaria |
| Legales | R-12 | Normativa peruana de protección de datos |

---

## 3. Restricciones técnicas

### R-01 — El lenguaje y el paradigma están fijados por el curso

**Descripción.** El sistema debe desarrollarse en Java, bajo el paradigma orientado a objetos, y debe demostrar clases abstractas, herencia, polimorfismo, encapsulamiento, upcasting y downcasting, colecciones y manejo de excepciones. No se puede elegir otro lenguaje ni otro paradigma.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Java de consola con clases propias | Cumple el sílabo íntegramente; sin dependencias externas | La interfaz de texto es menos cómoda para el usuario final |
| B. Java con interfaz gráfica Swing desde el inicio | Interfaz más cercana a la de un sistema real | El diseño de ventanas consume el tiempo que debe dedicarse al modelado de clases |
| C. Java con un marco de trabajo web | Resultado vistoso y accesible desde cualquier equipo | Exige conocimientos que el curso todavía no ha cubierto |

**Decisión.** Alternativa A. El sistema se construye como aplicación de consola con diez clases propias en el paquete `academia`, organizadas en dos jerarquías de herencia que parten de las clases abstractas `Persona` y `Ciclo`. La interfaz gráfica queda documentada como RF-43 para una entrega posterior.

**Consecuencia.** El esfuerzo se concentra en el modelo de clases, que es lo que el curso evalúa. La usabilidad se compensa con secciones guiadas, validación dato por dato dentro de los métodos `set` y un juego de datos de prueba cargable al iniciar.

---

### R-02 — No hay servidor de base de datos disponible

**Descripción.** El equipo no cuenta con un servidor de base de datos instalado ni con permisos para instalarlo en los laboratorios. Además, el curso aborda la persistencia en base de datos recién en las últimas semanas.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Mantener todo en memoria | Implementación inmediata | Los datos se pierden al cerrar el programa; el padrón se rehace cada vez |
| B. Archivos de texto plano con separador | Persistencia real; se alinea con el tema de manejo de archivos de la semana 9 | No ofrece consultas complejas ni control de concurrencia |
| C. Base de datos embebida | Consultas con SQL sin instalar servidor | Agrega una dependencia externa y un tema no cubierto aún |

**Decisión.** Alternativa B. Se implementa la clase `ArchivoUtil`, única del proyecto que importa `java.io`, y cada entidad expone un método `convertirALinea()` que la serializa en una línea con campos separados por punto y coma.

**Consecuencia.** La oferta académica, el padrón, las matrículas y la asistencia sobreviven al cierre del programa, con las vacantes ya ocupadas de cada ciclo y la bonificación acumulada de cada docente. Cuando se disponga de base de datos, bastará con sustituir `ArchivoUtil` sin tocar las nueve clases restantes. Esa migración está documentada como RF-42.

---

### R-03 — El equipo recién está aprendiendo el paradigma

**Descripción.** Los cuatro integrantes están en su primer curso de programación orientada a objetos. Patrones de diseño, pruebas automatizadas e inyección de dependencias no forman parte del conocimiento disponible.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Aplicar patrones de diseño avanzados | Arquitectura más flexible | Alto riesgo de código que el equipo no sepa explicar ni mantener |
| B. Modelar el dominio con clases abstractas y concentrar el flujo en la clase principal | Estructura clara y fiel a lo enseñado en clase | Menos flexible que una arquitectura por capas completa |
| C. Escribir toda la lógica en una sola clase | Rápido de escribir | Imposible de mantener y contrario a lo que el curso evalúa |

**Decisión.** Alternativa B. Las clases de dominio contienen sus atributos y sus validaciones; la clase `AcademiaSanFernando` contiene el flujo de trabajo de la academia, dividido en secciones, y los métodos auxiliares de archivo y de búsqueda.

**Consecuencia.** Cada integrante puede explicar la clase que escribió. La validación vive dentro de cada `set`, de modo que ningún objeto llega a la colección con datos inválidos, sea cual sea el camino por el que se haya creado.

---

## 4. Restricciones de tiempo

### R-04 — El plazo es el calendario del curso

**Descripción.** El desarrollo debe caber entre las semanas 7 y 11 del ciclo 2026-2, en paralelo con los demás cursos de la carrera. No hay posibilidad de extender el plazo.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Implementar los 45 requerimientos documentados | Sistema completo | Imposible en el plazo; el resultado sería código sin terminar |
| B. Documentar los 45 e implementar los 15 de mayor prioridad | Entrega funcional y verificable; el resto queda especificado | El sistema entregado no cubre todo el proceso de la academia |
| C. Implementar solo lo mínimo sin documentar el resto | Menor esfuerzo inmediato | No cumple el requisito de documentar al menos cuarenta requerimientos |

**Decisión.** Alternativa B. Se documentan 45 requerimientos funcionales con 179 criterios de aceptación y se implementan los 15 de prioridad alta que cubren el ciclo completo: oferta académica, plana docente, padrón de estudiantes, matrícula, asistencia, reportes y persistencia.

**Consecuencia.** Lo entregado funciona de principio a fin para el caso de uso central. Los 30 requerimientos restantes quedan con criterios de aceptación escritos, de modo que quien continúe el proyecto no tenga que volver a analizarlos.

---

### R-05 — El trabajo es asincrónico y en distintos equipos

**Descripción.** Los integrantes no coinciden en horario ni trabajan en la misma máquina, y usan versiones distintas de NetBeans.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Un solo integrante escribe todo el código | Evita conflictos de versiones | Contradice el carácter grupal del trabajo y no deja registro de participación |
| B. Repositorio compartido con ramas por tarea y pull requests | Cada aporte queda registrado con su autor; el historial es auditable | Exige disciplina con las ramas y resolver conflictos |
| C. Intercambio de archivos por mensajería | Simple de empezar | Se pierde el historial y aparecen versiones duplicadas del mismo archivo |

**Decisión.** Alternativa B. Se trabaja sobre el repositorio `Club-Delirio/Proyect-tecnicas` con las ramas `main` y `develop`, ramas de tarea con los prefijos `feat/`, `fix/` y `docs/`, y fusión mediante pull request con base en `develop`.

**Consecuencia.** El historial de commits sirve como evidencia objetiva de la participación de cada integrante. El procedimiento completo, incluida la conexión de cada máquina con GitHub, quedó documentado en `docs/GUIA_GIT_SEMANAS7-11.md`.

---

## 5. Restricciones de recursos

### R-06 — El equipo son cuatro estudiantes sin dedicación exclusiva

**Descripción.** No hay analista, ni diseñador, ni encargado de pruebas. Los cuatro integrantes cubren todos los roles en paralelo con sus otros cursos.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Reparto por capas: uno el modelo, otro el flujo, otro la documentación | Especialización | Quien tenga retraso bloquea a los demás |
| B. Reparto por clase: cada uno sus clases y la documentación que las describe | Trabajo paralelo sin bloqueos; cada uno entiende su parte de punta a punta | Riesgo de estilos distintos entre archivos |
| C. Programación en pareja permanente | Mejor calidad y conocimiento compartido | Duplica el tiempo necesario, que es precisamente el recurso escaso |

**Decisión.** Alternativa B, con una convención de estilo acordada por escrito: atributos en minúsculas con guion bajo, atributos `protected` en las clases abstractas y `private` en las hijas, métodos en minúscula inicial, validación dentro de los `set` con mensajes que empiezan por `Error:`, y el comentario Javadoc de cada clase firmado por los cuatro integrantes.

**Consecuencia.** Los cuatro pueden avanzar en paralelo y el código no parece escrito por cuatro personas distintas. El reparto detallado queda en `docs/reparto-de-tareas.md`.

---

### R-07 — No hay acceso a datos reales de una academia

**Descripción.** El equipo no tiene autorización para usar el padrón de estudiantes ni la planilla docente de ninguna academia. Trabajar con datos reales de menores de edad sería además una infracción de la normativa de protección de datos.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Pedir datos reales a una academia | Pruebas con información auténtica | Trámite incierto y exposición de datos personales de menores |
| B. Construir un juego de datos ficticio verosímil | Disponible de inmediato y sin riesgo legal | No reproduce casos extremos que solo aparecen en la operación real |
| C. Generar datos al azar | Gran volumen de pruebas | Los datos sin sentido no revelan errores de reglas de negocio |

**Decisión.** Alternativa B. El programa incorpora un juego de datos de prueba con dos docentes, seis estudiantes, cuatro ciclos, seis matrículas y nueve registros de asistencia, construido para que queden representadas las tres categorías, las dos modalidades, las dos formas de pago, los dos descuentos propios del tipo de ciclo, un estudiante menor de edad y dos estudiantes en riesgo de deserción.

**Consecuencia.** Cualquier integrante o el docente del curso puede reproducir el mismo escenario respondiendo que sí a una sola pregunta al iniciar el programa, y verificar los reportes sin digitar datos.

---

## 6. Restricciones de alcance

### R-08 — El sistema debe ser una aplicación de escritorio autónoma

**Descripción.** No se dispone de hospedaje web, ni de dominio, ni de presupuesto para contratarlos. El sistema debe ejecutarse en la máquina del usuario.

**Decisión.** El proyecto se entrega como proyecto de NetBeans que compila con `javac` y se ejecuta con `java`, sin dependencias externas.

**Consecuencia.** El sistema funciona en cualquier equipo con JDK 17 o superior. Los archivos de datos quedan en la carpeta `datos` del propio proyecto, lo que simplifica el respaldo pero limita el uso a un solo puesto de trabajo a la vez.

---

### R-09 — Sin interfaz gráfica ni documentos impresos en esta entrega

**Descripción.** Las semanas 7 a 11 del sílabo cubren restricciones, requerimientos, historias de usuario, diagrama de clases, manejo de archivos e implementación. La interfaz gráfica y la generación de documentos no forman parte de este bloque.

**Decisión.** Los reportes se muestran por consola y el historial de matrículas se exporta a un archivo CSV, que cualquier hoja de cálculo abre. La interfaz gráfica queda documentada como RF-43 y el comprobante de matrícula en PDF como RF-26.

**Consecuencia.** La dirección puede llevar el historial de matrículas a Excel sin que el sistema tenga que generar ese formato. Es la solución de menor costo que cubre la necesidad real.

---

## 7. Restricciones del negocio

### R-10 — Quien atiende no es informático y lo hace con el apoderado delante

**Descripción.** Quien usa el sistema es el personal de admisión, atendiendo en el mostrador con el postulante y su apoderado al lado. Un sistema que exija interpretar mensajes técnicos o que se cierre ante un dato mal escrito no se usaría.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Lanzar excepciones y mostrar el mensaje de Java | Fiel al manejo de excepciones del lenguaje | `DateTimeParseException` no significa nada para una asistente de admisión |
| B. Validar en los `set` y volver a pedir el dato hasta que sea correcto | El usuario entiende qué corregir y no pierde lo ya ingresado | Requiere escribir cada mensaje con cuidado |
| C. Aceptar el dato y validarlo al final | Menos interrupciones | El error se descubre cuando ya es tarde para corregirlo |

**Decisión.** Alternativa B. Cada `set` valida y explica el error en términos del dato. El bucle `while (objeto.getAtributo() == null)` vuelve a pedir el dato mientras no se haya asignado, de modo que el usuario no pueda avanzar con un objeto incompleto. Los métodos `leerEntero()`, `leerDecimal()` y `leerFecha()` capturan `NumberFormatException` y `DateTimeParseException` y vuelven a pedir el valor.

**Consecuencia.** Ningún error de escritura cierra el programa ni obliga a empezar de nuevo. La asistente corrige el dato en el momento, con el apoderado delante.

---

### R-11 — Los precios y los descuentos cambian en cada campaña

**Descripción.** Una academia no cobra siempre el precio de lista: hay campañas de pronto pago que la dirección fija, descuentos que dependen de la modalidad o de la duración, y recargos cuando el apoderado fracciona el pago. Escribir esos porcentajes dentro del código obligaría a recompilar en cada campaña.

**Decisión.** El descuento de campaña se ingresa al iniciar la jornada, con su nombre y su porcentaje validado entre 0 y 25 por ciento, y queda guardado en cada matrícula junto con el monto que representó. Los descuentos propios del tipo de ciclo se resuelven por polimorfismo: `Matricula` invoca `calcularDescuento()` sin saber si el objeto es un `CicloRegular` o un `CursoIntensivo`.

**Consecuencia.** Cambiar la campaña no exige tocar el código. Agregar un tercer tipo de oferta —un taller de reforzamiento, por ejemplo— solo exige crear una clase que extienda `Ciclo`: ni `Matricula` ni los reportes cambian.

---

## 8. Restricción legal

### R-12 — Ley de Protección de Datos Personales

**Descripción.** El sistema almacena nombres, documentos de identidad, fechas de nacimiento, teléfonos y datos de apoderados. Buena parte de los postulantes son menores de edad, lo que agrava la obligación. La Ley 29733 y su reglamento regulan el tratamiento de esos datos en el Perú.

**Alternativas evaluadas.**

| Alternativa | Ventaja | Desventaja |
|---|---|---|
| A. Almacenar todo en texto plano sin control de acceso | Implementación inmediata | Cualquiera con acceso al equipo lee el padrón completo, incluidos datos de menores |
| B. Cifrar los archivos de datos | Protege la información en reposo | El cifrado no está cubierto en el curso y complica la depuración |
| C. Texto plano en esta entrega, con control de acceso documentado y datos de prueba ficticios | Viable en el plazo, sin exponer datos reales | La protección efectiva queda para una entrega posterior |

**Decisión.** Alternativa C. En esta entrega se trabaja exclusivamente con datos ficticios; el control de acceso con usuario, contraseña y perfil queda documentado como RF-44, y la bitácora de operaciones como RF-45. Además, el sistema exige el nombre del apoderado cuando el postulante es menor de edad, lo que deja constancia del adulto responsable.

**Consecuencia.** El sistema no expone datos personales reales en ninguna de sus pruebas ni en el repositorio. La obligación legal queda registrada como requerimiento de prioridad alta y no como un detalle olvidado.

---

## 9. Síntesis de las decisiones

| Restricción | Decisión adoptada | Requerimiento pendiente que genera |
|---|---|---|
| R-01 Lenguaje y paradigma fijados | Aplicación de consola con diez clases y dos jerarquías de herencia | RF-43 |
| R-02 Sin base de datos | Persistencia en archivos de texto mediante `ArchivoUtil` | RF-42 |
| R-03 Equipo en formación | Dominio en clases abstractas, flujo en la clase principal | — |
| R-04 Plazo del curso | 45 requerimientos documentados, 15 implementados | RF-04 a RF-45 según prioridad |
| R-05 Trabajo asincrónico | Repositorio con ramas, pull requests y guía de Git documentada | — |
| R-06 Cuatro integrantes sin dedicación exclusiva | Reparto por clase con convención de estilo escrita | — |
| R-07 Sin datos reales | Juego de datos de prueba ficticio cargable al iniciar | — |
| R-08 Aplicación autónoma | Proyecto de NetBeans sin dependencias externas | — |
| R-09 Sin interfaz gráfica en esta entrega | Reportes por consola y exportación a CSV | RF-26, RF-43 |
| R-10 Usuario no informático | Validación en los `set` con bucle de reintento y captura de excepciones | — |
| R-11 Precios y descuentos variables | Campaña ingresada en ejecución; descuentos resueltos por polimorfismo | RF-27, RF-30 |
| R-12 Ley 29733 | Datos ficticios, apoderado obligatorio para menores y control de acceso documentado | RF-44, RF-45 |

Ninguna de estas restricciones se resolvió ignorándola. Cada una produjo una decisión de diseño y, cuando correspondía, un requerimiento pendiente con sus criterios de aceptación ya escritos en `docs/requerimientos-funcionales.md`.
