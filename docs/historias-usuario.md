# Historias de usuario

**Proyecto:** Sistema de gestión de matrícula y control de asistencia — Academia Preuniversitaria San Fernando
**Curso:** Técnicas de Programación Orientada a Objetos (SIST1202A) — UPN Cajamarca, 2026-2
**Semanas del sílabo:** 8 y 10

Cada historia corresponde a uno de los quince requerimientos funcionales implementados y tiene cuatro escenarios de aceptación verificables en la ejecución del programa. La versión en hoja de cálculo, con la trazabilidad completa hacia las clases Java y el integrante responsable, se encuentra en `docs/Historias_Usuario_Academia.xlsx`.

**Roles identificados:** directora académica, coordinador de admisión, asistente de admisión, auxiliar de aula y asistente de sistemas.

---

## HU-01 — RF-01 · Sección «1. Registro de ciclos y cursos» del programa

**Como coordinador de admisión de la academia**  
**Necesito registrar en la oferta cada ciclo regular o curso intensivo con los datos que son propios de su tipo**  
**Con la finalidad de que todos los asesores ofrezcan lo mismo y con las mismas condiciones.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Alta de un ciclo regular | En caso que el operador elija el tipo CICLO y todos los datos cumplan las validaciones | cuando se crea el objeto y el programa comprueba c instanceof CicloRegular para hacer el downcasting | el sistema pide turno, meses de duración y número de simulacros, y guarda el objeto en la colección de Ciclo | `CicloRegular / Ciclo / AcademiaSanFernando` |
| 2 | Alta de un curso intensivo | En caso que el operador elija el tipo CURSO | cuando se crea el objeto y se hace el downcasting a CursoIntensivo | el sistema pide área, semanas y horas diarias en lugar de los datos del ciclo regular | `CursoIntensivo / Ciclo` |
| 3 | Orden obligatorio de las fechas | En caso que se intente asignar la fecha de fin sin haber asignado la de inicio | cuando setFec_fin() encuentra que fec_inicio todavía es null | el sistema exige ingresar primero la fecha de inicio y vuelve a pedir el dato | `Ciclo` |
| 4 | Aforo del ciclo presencial | En caso que la modalidad sea PRESENCIAL y se pidan más de 45 vacantes | cuando se invoca setVacantes(), que primero comprueba que la modalidad esté asignada | el sistema rechaza el dato porque ningún aula de la academia pasa de 45 sitios | `Ciclo` |

**Integrante responsable:** Rojas Valdivia, Jhann Frank

---

## HU-02 — RF-02 · Sección «1. Registro de ciclos y cursos» del programa

**Como coordinador de admisión de la academia**  
**Necesito que el sistema impida registrar dos veces el mismo ciclo, comparando su código**  
**Con la finalidad de que la oferta no muestre ciclos repetidos con vacantes distintas.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Código nuevo aceptado | En caso que el código ingresado no exista en la colección de ciclos | cuando el recorrido termina sin encontrar coincidencia | el sistema asigna el código y continúa con el resto del registro | `AcademiaSanFernando / Ciclo` |
| 2 | Código repetido rechazado | En caso que ya exista un ciclo con ese código | cuando el recorrido encuentra la coincidencia y pone la bandera repetido en true | el sistema muestra el error con el código en mayúsculas y no registra el ciclo | `AcademiaSanFernando` |
| 3 | Comparación sin distinguir mayúsculas | En caso que el código se escriba en minúsculas | cuando la comparación se hace con equalsIgnoreCase() | el sistema lo detecta igualmente como duplicado | `AcademiaSanFernando` |
| 4 | La oferta no se altera | En caso que se haya rechazado un código duplicado | cuando se consulta el tamaño de la colección de ciclos | el sistema devuelve la misma cantidad que antes del intento, sin objetos a medio construir | `AcademiaSanFernando` |

**Integrante responsable:** Rojas Valdivia, Jhann Frank

---

## HU-03 — RF-03 · Sección «1. Registro de ciclos y cursos» del programa

**Como directora académica**  
**Necesito que el sistema clasifique solo cada ciclo en categoría básica, intermedia o premium**  
**Con la finalidad de que el asesor no decida la categoría a ojo y toda la oferta se ordene con el mismo criterio.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Categoría del ciclo regular por costo | En caso que el ciclo sea regular | cuando se invoca calcularCategoria() | el sistema devuelve BASICO bajo S/ 600, INTERMEDIO hasta S/ 1200 y PREMIUM por encima | `CicloRegular` |
| 2 | Categoría del curso por carga horaria | En caso que el curso intensivo tenga 6 o más horas diarias | cuando se invoca calcularCategoria() | el sistema devuelve PREMIUM sin llegar a evaluar el costo | `CursoIntensivo` |
| 3 | Categoría del curso por costo | En caso que el curso intensivo tenga menos de 6 horas diarias | cuando se invoca calcularCategoria() | el sistema clasifica por costo: bajo S/ 400 BASICO, hasta S/ 900 INTERMEDIO, por encima PREMIUM | `CursoIntensivo` |
| 4 | Clasificación sin intervención del operador | En caso que se termine de registrar un ciclo o curso | cuando el programa imprime la confirmación del registro | el sistema informa la categoría y el descuento asignados sin que el operador los haya ingresado | `Ciclo / AcademiaSanFernando` |

**Integrante responsable:** Cachi Tantaleán, Anderson Alexis

---

## HU-04 — RF-09 · Sección «2. Registro de docentes» del programa

**Como directora académica**  
**Necesito registrar a cada docente con su especialidad, su tarifa por hora y su bonificación, y que el sistema acumule sus horas y las matrículas que tutorea**  
**Con la finalidad de armar la planilla del mes sin rehacer la cuenta a mano.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Registro válido del docente | En caso que el código tenga el formato DOC-000, la tarifa esté entre 15 y 120 soles y la bonificación entre 1 y 5 por ciento | cuando se completa el ingreso y se agrega a la colección de Persona por upcasting | el sistema confirma el registro y el docente queda disponible para asignarlo como tutor | `Docente / Persona` |
| 2 | Especialidad no reconocida | En caso que la especialidad no sea una de las seis que dicta la academia | cuando se invoca setEspecialidad() | el sistema rechaza el valor, muestra la lista completa y vuelve a pedirlo | `Docente` |
| 3 | Documento ya registrado | En caso que el documento ya pertenezca a otra persona de la colección | cuando el recorrido sobre personas encuentra la coincidencia | el sistema no registra al docente e informa el documento duplicado | `AcademiaSanFernando` |
| 4 | Cálculo del pago del mes | En caso que el docente tenga 80 horas dictadas a S/ 35 y haya gestionado S/ 5096.30 en matrículas con 3 % de bonificación | cuando se invocan calcularPagoHoras(), calcularBonificacion() y calcularPagoTotal() | el sistema devuelve 2800.00, 152.89 y 2952.89 respectivamente, redondeados a dos decimales | `Docente` |

**Integrante responsable:** Rojas Valdivia, Jhann Frank

---

## HU-05 — RF-15 · Sección «3. Registro de estudiantes» del programa

**Como asistente de admisión**  
**Necesito registrar al postulante con su documento, sus datos de contacto, su colegio, la carrera a la que postula y su apoderado**  
**Con la finalidad de poder matricularlo y de poder ubicar a su familia durante el ciclo.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Registro válido del estudiante | En caso que todos los datos cumplan las validaciones de la clase base y de la clase Estudiante | cuando se completa el ingreso y se agrega a la colección de Persona por upcasting | el sistema confirma el registro y fija la fecha de hoy como su última visita | `Estudiante / Persona` |
| 2 | Documento con longitud incorrecta | En caso que el tipo sea DNI y el número no tenga 8 dígitos | cuando se invoca setNro_doc(), que antes comprueba que el tipo ya esté asignado | el sistema rechaza el dato, indica la longitud esperada y vuelve a pedirlo | `Persona` |
| 3 | Edad fuera del rango del postulante | En caso que la fecha de nacimiento dé una edad menor a 14 o mayor a 30 años | cuando se invoca setFec_nacimiento() | el sistema rechaza la fecha, informa el rango permitido y la edad que calculó | `Estudiante` |
| 4 | Menor de edad sin apoderado | En caso que la edad calculada sea menor a 18 y el nombre del apoderado tenga menos de 5 caracteres | cuando setApoderado() evalúa esMenorDeEdad() | el sistema bloquea el dato y exige el nombre del apoderado antes de continuar | `Estudiante` |

**Integrante responsable:** Chuquimango Verástegui, Jhocep Jhanpier

---

## HU-06 — RF-16 · Sección «3. Registro de estudiantes» del programa

**Como asistente de admisión**  
**Necesito que el sistema reconozca al postulante que ya está registrado y actualice su última visita en lugar de crearlo otra vez**  
**Con la finalidad de que un mismo estudiante no aparezca tres veces en el padrón con datos distintos.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Estudiante que vuelve a la academia | En caso que el documento ya pertenezca a un estudiante registrado | cuando el recorrido lo encuentra y se hace el downcasting a Estudiante para invocar registrarVisita() | el sistema avisa que ya estaba registrado, actualiza su última visita y muestra sus datos | `Estudiante / Persona / AcademiaSanFernando` |
| 2 | El padrón no se duplica | En caso que el estudiante ya existiera | cuando se consulta el tamaño de la colección de personas | el sistema devuelve la misma cantidad que antes del intento | `AcademiaSanFernando` |
| 3 | Documento que pertenece a un docente | En caso que el documento exista pero corresponda a un docente | cuando el recorrido lo encuentra pero la comprobación instanceof Estudiante falla | el sistema informa que ese documento pertenece a un docente y no registra nada | `AcademiaSanFernando` |
| 4 | Fecha de última visita actualizada | En caso que el estudiante vuelva a la academia | cuando se invoca registrarVisita() | el sistema reemplaza la fecha anterior por la del día y la muestra en su ficha | `Estudiante` |

**Integrante responsable:** Chuquimango Verástegui, Jhocep Jhanpier

---

## HU-07 — RF-17 · Sección «5. Matriculas» del programa

**Como asistente de admisión**  
**Necesito que el historial de los ciclos en los que se matriculó cada estudiante quede guardado en su propia ficha**  
**Con la finalidad de reconocer al estudiante que vuelve y de saber qué ya llevó antes de ofrecerle algo.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | La matrícula se registra en la ficha | En caso que la matrícula se confirme | cuando se invoca agregarMatricula() con el ciclo | el sistema guarda el ciclo en la lista del estudiante y actualiza su última visita | `Estudiante / Ciclo` |
| 2 | Varias matrículas del mismo estudiante | En caso que el estudiante se haya matriculado en más de un ciclo | cuando se consulta su ficha con verDatos() | el sistema lista todos sus ciclos con tipo, código, nombre y categoría | `Estudiante` |
| 3 | Estudiante sin matrículas | En caso que el estudiante nunca se haya matriculado | cuando se consulta su ficha | el sistema lo indica expresamente en lugar de mostrar una lista vacía | `Estudiante` |
| 4 | La lista acumula, no reemplaza | En caso que el estudiante acumule matrículas sucesivas | cuando se consulta la colección interna de matrículas | el sistema conserva los elementos anteriores: la relación es de uno a muchos | `Estudiante` |

**Integrante responsable:** Chuquimango Verástegui, Jhocep Jhanpier

---

## HU-08 — RF-22 · Sección «4. Campana de descuento» del programa

**Como directora académica**  
**Necesito configurar la campaña de descuento vigente y que el sistema aplique además el descuento propio de cada tipo de ciclo**  
**Con la finalidad de que todos los asesores cobren el mismo precio y nadie invente descuentos.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Campaña configurada | En caso que la dirección indique que hay una campaña vigente | cuando ingresa su nombre y un porcentaje entre 0 y 25 | el sistema guarda la campaña y la aplica a todas las matrículas del periodo | `AcademiaSanFernando` |
| 2 | Descuento fuera de rango | En caso que el porcentaje ingresado sea negativo o mayor a 25 | cuando el bucle while valida el valor | el sistema rechaza el dato, explica el rango permitido y vuelve a pedirlo | `AcademiaSanFernando` |
| 3 | Descuento del ciclo virtual | En caso que el ciclo regular tenga modalidad VIRTUAL | cuando Matricula invoca ciclo.calcularDescuento() | el sistema aplica 8 % adicional, ejecutando la versión de CicloRegular por polimorfismo | `CicloRegular / Matricula` |
| 4 | Descuento vacacional del curso | En caso que el curso intensivo dure 4 semanas o menos | cuando Matricula invoca el mismo método sin saber de qué tipo es el ciclo | el sistema aplica 6 % adicional, ejecutando la versión de CursoIntensivo | `CursoIntensivo / Matricula` |

**Integrante responsable:** Cachi Tantaleán, Anderson Alexis

---

## HU-09 — RF-23 · Sección «5. Matriculas» del programa

**Como asistente de admisión**  
**Necesito cotizar el precio final con todo el desglose a la vista y poder no concretar la matrícula**  
**Con la finalidad de que el apoderado vea de dónde sale cada sol antes de decidir, y de no bloquear la vacante si se arrepiente.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Desglose completo del precio | En caso que la matrícula esté construida con su estudiante, tutor y ciclo | cuando se invoca calcularPrecioFinal() y luego verDetallePrecio() | el sistema muestra costo base, descuento de campaña, descuento del ciclo, subtotal, recargo y precio final | `Matricula` |
| 2 | Recargo por fraccionamiento | En caso que la forma de pago sea FRACCIONADO | cuando se invoca calcularPorcentajeRecargo() | el sistema devuelve 4 % a 2 cuotas, 7 % a 3 y 10 % a 4, y lo suma al subtotal | `Matricula` |
| 3 | Cuota mensual calculada | En caso que la matrícula sea fraccionada | cuando calcularPrecioFinal() divide el precio final entre el número de cuotas | el sistema muestra la cuota mensual junto con la cantidad de cuotas | `Matricula` |
| 4 | Cotización que no se concreta | En caso que el apoderado no confirme la operación | cuando el operador responde que no al confirmar la matrícula | el sistema descarta la matrícula y la vacante sigue disponible | `AcademiaSanFernando` |

**Integrante responsable:** Rodrigo Ortiz, Sumil Arturi

---

## HU-10 — RF-24 · Sección «5. Matriculas» del programa

**Como asistente de admisión**  
**Necesito registrar la matrícula indicando si es al contado o fraccionada, y que quede asociada al estudiante, al docente tutor y al ciclo**  
**Con la finalidad de que la operación quede documentada y de que se actualicen solas las vacantes y la bonificación del tutor.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Matrícula registrada con sus tres efectos | En caso que el estudiante, el tutor y el ciclo existan y el ciclo tenga vacantes | cuando el operador confirma la matrícula | el sistema asigna el código correlativo, descuenta una vacante, agrega el ciclo al historial del estudiante y acumula el monto al tutor | `Matricula / Estudiante / Docente / Ciclo` |
| 2 | Estudiante inexistente | En caso que el código no corresponda a ningún estudiante registrado | cuando buscarEstudiante() recorre la colección y devuelve null | el sistema informa el error y no inicia la matrícula | `AcademiaSanFernando` |
| 3 | Número de cuotas no permitido | En caso que la forma de pago sea FRACCIONADO y las cuotas no sean 2, 3 ni 4 | cuando se invoca setNro_cuotas() | el sistema rechaza el valor, explica las opciones y vuelve a pedirlo | `Matricula` |
| 4 | Código correlativo de la matrícula | En caso que ya existan cinco matrículas registradas | cuando se confirma la sexta y se invoca setCodigo() con el tamaño de la colección más uno | el sistema asigna MAT-6, sin repetir códigos | `Matricula / AcademiaSanFernando` |

**Integrante responsable:** Rodrigo Ortiz, Sumil Arturi

---

## HU-11 — RF-25 · Sección «5. Matriculas» del programa

**Como coordinador de admisión de la academia**  
**Necesito que el sistema impida matricular a alguien en un ciclo sin vacantes o en el que ya está matriculado**  
**Con la finalidad de no comprometer más sitios de los que hay en el aula ni cobrar dos veces lo mismo.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Ciclo con vacantes | En caso que el estado del ciclo sea ABIERTO y queden cupos libres | cuando se invoca hayVacantes() | el sistema devuelve verdadero y la matrícula continúa su curso | `Ciclo` |
| 2 | Ciclo sin vacantes | En caso que los matriculados igualen al número de vacantes | cuando hayVacantes() devuelve falso | el sistema avisa y cancela la matrícula sin pedir más datos | `Ciclo / AcademiaSanFernando` |
| 3 | Cierre automático del ciclo | En caso que se matricule al último cupo disponible | cuando se invoca registrarMatriculado() | el sistema cambia el estado de ABIERTO a CERRADO sin que el operador lo indique | `Ciclo` |
| 4 | Matrícula duplicada | En caso que el estudiante ya figure en ese ciclo | cuando se recorre su historial de matrículas antes de matricular | el sistema rechaza la operación e informa el duplicado | `Estudiante / AcademiaSanFernando` |

**Integrante responsable:** Rodrigo Ortiz, Sumil Arturi

---

## HU-12 — RF-31 · Sección «6. Control de asistencia» del programa

**Como auxiliar de aula**  
**Necesito registrar la asistencia de cada estudiante a las sesiones de su ciclo, con su estado y una observación**  
**Con la finalidad de llevar el control diario sin las hojas sueltas que se extravían.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Asistencia registrada correctamente | En caso que el estudiante esté matriculado en el ciclo y la fecha no sea futura | cuando se completa el ingreso y se asigna el código correlativo | el sistema registra la asistencia y la incorpora al cálculo del porcentaje del estudiante | `Asistencia / Estudiante / Ciclo` |
| 2 | Estudiante sin matrícula | En caso que el estudiante no tenga ninguna matrícula registrada | cuando se comprueba que su lista de matrículas está vacía | el sistema informa el problema y no pide más datos | `Estudiante / AcademiaSanFernando` |
| 3 | Fecha futura | En caso que la fecha de la sesión sea posterior a la de hoy | cuando se invoca setFecha() | el sistema rechaza la fecha y la asistencia no se registra | `Asistencia` |
| 4 | Registro duplicado de la misma sesión | En caso que ya exista la asistencia del mismo estudiante, en el mismo ciclo y en la misma fecha | cuando se recorre la colección antes de agregar | el sistema rechaza el registro para que la misma sesión no se cuente dos veces | `AcademiaSanFernando` |

**Integrante responsable:** Cachi Tantaleán, Anderson Alexis

---

## HU-13 — RF-35 · Sección «Catalogo por categoria» del programa

**Como coordinador de admisión de la academia**  
**Necesito ver la oferta agrupada por categoría con las vacantes y el estado de cada ciclo**  
**Con la finalidad de saber de un vistazo qué puedo ofrecer en cada rango de precio.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Agrupación por las tres categorías | En caso que existan ciclos de categoría básica, intermedia y premium | cuando se recorre el arreglo de categorías y, dentro, la colección de ciclos | el sistema imprime cada ciclo bajo el encabezado de la categoría que le corresponde | `Ciclo / AcademiaSanFernando` |
| 2 | Reglas de clasificación visibles | En caso que se muestre el catálogo | cuando se imprime el encabezado de la sección | el sistema explica las reglas de categoría de ciclos regulares y de cursos intensivos | `AcademiaSanFernando` |
| 3 | Vacantes y estado de cada ciclo | En caso que haya ciclos abiertos y cerrados | cuando se imprime cada línea del catálogo | el sistema indica los matriculados sobre las vacantes y el estado al final de la línea | `Ciclo` |
| 4 | Categoría sin elementos | En caso que no haya ciclos de una categoría determinada | cuando se recorre esa categoría | el sistema imprime el encabezado sin elementos debajo, sin lanzar excepción | `AcademiaSanFernando` |

**Integrante responsable:** Rojas Valdivia, Jhann Frank

---

## HU-14 — RF-36 · Sección «7. Listas y reportes» del programa

**Como directora académica**  
**Necesito los listados, el historial de matrículas, las estadísticas, el reporte financiero y el reporte de asistencia**  
**Con la finalidad de saber cuánto entró realmente a la academia y qué estudiantes están en riesgo de dejarla.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Listado polimórfico de personas | En caso que haya estudiantes y docentes registrados en la colección de Persona | cuando se recorre la colección invocando p.verDatos() | el sistema ejecuta la versión de Estudiante o la de Docente según el objeto real: polimorfismo en tiempo de ejecución | `Persona / Estudiante / Docente` |
| 2 | Estadísticas del periodo | En caso que haya matrículas de los dos tipos y de distintas categorías y formas de pago | cuando se acumulan los contadores en un solo recorrido | el sistema muestra el conteo por tipo, por categoría y por forma de pago, más la matrícula promedio | `Matricula / Ciclo / AcademiaSanFernando` |
| 3 | Reporte financiero con la planilla | En caso que haya matrículas y docentes con horas y bonificación | cuando se acumulan los descuentos y los recargos, y se recorre a los docentes con downcasting | el sistema muestra el ingreso total, el pago de cada docente y el ingreso neto | `Matricula / Docente / AcademiaSanFernando` |
| 4 | Reporte de asistencia con alerta | En caso que un estudiante tenga tres registros y haya asistido a uno | cuando se calcula su porcentaje y se compara con el umbral del 70 % | el sistema muestra 33 % y marca la línea con la alerta de riesgo de deserción | `Asistencia / Estudiante / AcademiaSanFernando` |

**Integrante responsable:** Cachi Tantaleán, Anderson Alexis

---

## HU-15 — RF-39 · Sección «0. Recuperar / 8. Guardar» del programa

**Como asistente de sistemas de la academia**  
**Necesito guardar la información en archivos de texto y recuperarla al abrir el programa, además de exportar el historial de matrículas**  
**Con la finalidad de que el trabajo del día no se pierda al cerrar la aplicación.**

| # | Criterio | Contexto | Evento | Resultado esperado | Clase |
|---|---|---|---|---|---|
| 1 | Escritura de las cuatro colecciones | En caso que haya ciclos, personas, matrículas y asistencias en memoria | cuando se invoca guardarTodo(), que usa BufferedWriter sobre FileWriter en modo sobrescritura | el sistema crea la carpeta datos si no existe, genera los cuatro archivos y confirma la cantidad guardada en cada uno | `ArchivoUtil / AcademiaSanFernando` |
| 2 | Lectura de un archivo inexistente | En caso que se solicite leer un archivo que todavía no fue creado | cuando leerLineas() consulta existeArchivo() antes de abrir el flujo | el sistema muestra un aviso y devuelve una lista vacía, sin lanzar FileNotFoundException | `ArchivoUtil` |
| 3 | Recuperación con las relaciones reconstruidas | En caso que los cuatro archivos tengan contenido válido | cuando se invocan los métodos de carga en el orden ciclos, personas, matrículas y asistencias | el sistema recupera los objetos con sus vacantes ocupadas, el historial del estudiante y el acumulado del docente | `AcademiaSanFernando / ArchivoUtil` |
| 4 | Línea corrupta omitida | En caso que una línea del archivo tenga menos campos de los esperados o una referencia rota | cuando el método de carga comprueba la longitud del arreglo y captura la excepción por línea | el sistema omite esa línea con un aviso y continúa con las demás | `AcademiaSanFernando` |

**Integrante responsable:** Chuquimango Verástegui, Jhocep Jhanpier

---

## Trazabilidad

| Historia | Requerimiento | Sección del programa | Rol | Integrante responsable |
|---|---|---|---|---|
| HU-01 | RF-01 | 1. Registro de ciclos y cursos | Coordinador de admisión de la academia | Rojas Valdivia, Jhann Frank |
| HU-02 | RF-02 | 1. Registro de ciclos y cursos | Coordinador de admisión de la academia | Rojas Valdivia, Jhann Frank |
| HU-03 | RF-03 | 1. Registro de ciclos y cursos | Directora académica | Cachi Tantaleán, Anderson Alexis |
| HU-04 | RF-09 | 2. Registro de docentes | Directora académica | Rojas Valdivia, Jhann Frank |
| HU-05 | RF-15 | 3. Registro de estudiantes | Asistente de admisión | Chuquimango Verástegui, Jhocep Jhanpier |
| HU-06 | RF-16 | 3. Registro de estudiantes | Asistente de admisión | Chuquimango Verástegui, Jhocep Jhanpier |
| HU-07 | RF-17 | 5. Matriculas | Asistente de admisión | Chuquimango Verástegui, Jhocep Jhanpier |
| HU-08 | RF-22 | 4. Campana de descuento | Directora académica | Cachi Tantaleán, Anderson Alexis |
| HU-09 | RF-23 | 5. Matriculas | Asistente de admisión | Rodrigo Ortiz, Sumil Arturi |
| HU-10 | RF-24 | 5. Matriculas | Asistente de admisión | Rodrigo Ortiz, Sumil Arturi |
| HU-11 | RF-25 | 5. Matriculas | Coordinador de admisión de la academia | Rodrigo Ortiz, Sumil Arturi |
| HU-12 | RF-31 | 6. Control de asistencia | Auxiliar de aula | Cachi Tantaleán, Anderson Alexis |
| HU-13 | RF-35 | Catalogo por categoria | Coordinador de admisión de la academia | Rojas Valdivia, Jhann Frank |
| HU-14 | RF-36 | 7. Listas y reportes | Directora académica | Cachi Tantaleán, Anderson Alexis |
| HU-15 | RF-39 | 0. Recuperar / 8. Guardar | Asistente de sistemas de la academia | Chuquimango Verástegui, Jhocep Jhanpier |

Los treinta requerimientos restantes, con sus criterios de aceptación, se encuentran en `docs/requerimientos-funcionales.md`.