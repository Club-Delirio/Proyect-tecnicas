# Requerimientos funcionales y criterios de aceptación

**Proyecto:** Sistema de gestión de matrícula y control de asistencia — Academia Preuniversitaria San Fernando
**Curso:** Técnicas de Programación Orientada a Objetos (SIST1202A) — UPN Cajamarca, 2026-2
**Equipo:** Cachi Tantaleán Anderson Alexis · Chuquimango Verástegui Jhocep Jhanpier · Rodrigo Ortiz Sumil Arturi · Rojas Valdivia Jhann Frank
**Repositorio:** `Club-Delirio/Proyect-tecnicas` · **Paquete:** `academia`

---

## 1. Convenciones

| Elemento | Significado |
|---|---|
| **RF-nn** | Código del requerimiento funcional. Es inmutable: una vez asignado no se reutiliza. |
| **Prioridad Alta** | Sin este requerimiento el sistema no cumple su propósito mínimo. |
| **Prioridad Media** | Agrega valor relevante, pero el sistema opera sin él. |
| **Prioridad Baja** | Mejora la experiencia o la presentación de la información. |
| **Implementado** | Codificado en Java, compilado y verificado en esta entrega. |
| **Pendiente** | Documentado y con criterios de aceptación definidos, previsto para las entregas siguientes. |

Los criterios de aceptación se redactan en el formato **Dado / Cuando / Entonces**. Cada criterio describe una condición verificable: o se cumple o no se cumple, sin interpretación intermedia.

---

## 2. Resumen por módulo

| Módulo | Códigos | Total | Implementados | Pendientes |
|---|---|---|---|---|
| M1. Oferta académica | RF-01 a RF-08 | 8 | 3 | 5 |
| M2. Plana docente | RF-09 a RF-14 | 6 | 1 | 5 |
| M3. Estudiantes | RF-15 a RF-21 | 7 | 3 | 4 |
| M4. Matrícula y pagos | RF-22 a RF-30 | 9 | 4 | 5 |
| M5. Control de asistencia | RF-31 a RF-34 | 4 | 1 | 3 |
| M6. Reportes y persistencia | RF-35 a RF-45 | 11 | 3 | 8 |
| **Total** | **RF-01 a RF-45** | **45** | **15** | **30** |

Los 45 requerimientos tienen en total **179 criterios de aceptación** documentados en la sección 4.

---

## 3. Catálogo de requerimientos

### M1. Oferta académica

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-01 | Registrar en la oferta un ciclo regular o un curso intensivo, con los datos comunes y los que son propios de su tipo. | Alta | Implementado |
| RF-02 | Impedir que el mismo ciclo o curso se registre dos veces, comparando su código. | Alta | Implementado |
| RF-03 | Clasificar automáticamente cada ciclo o curso en categoría básica, intermedia o premium según las reglas de su tipo. | Alta | Implementado |
| RF-04 | Modificar el costo base de un ciclo que todavía tiene vacantes abiertas. | Alta | Pendiente |
| RF-05 | Cancelar un ciclo que no alcanzó el mínimo de matriculados, notificando a los inscritos. | Media | Pendiente |
| RF-06 | Definir el plan de estudios de cada ciclo indicando qué asignaturas lo componen. | Alta | Pendiente |
| RF-07 | Registrar el horario semanal de cada ciclo, con día y hora de cada sesión. | Alta | Pendiente |
| RF-08 | Asignar un aula física a cada ciclo presencial y comprobar que su aforo cubra las vacantes. | Media | Pendiente |

### M2. Plana docente

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-09 | Registrar un docente con su especialidad, su tarifa por hora y su bonificación, y acumular las horas que dicta y las matrículas que tutorea. | Alta | Implementado |
| RF-10 | Asignar a cada docente las asignaturas que dicta según su especialidad. | Alta | Pendiente |
| RF-11 | Detectar y rechazar los cruces de horario de un mismo docente en dos ciclos. | Alta | Pendiente |
| RF-12 | Registrar la asistencia del docente a cada sesión dictada. | Alta | Pendiente |
| RF-13 | Dar de baja a un docente conservando el historial de los ciclos que dictó. | Media | Pendiente |
| RF-14 | Liquidar y registrar el pago efectivo de la planilla docente de cada periodo. | Alta | Pendiente |

### M3. Estudiantes

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-15 | Registrar un estudiante con su documento, sus datos de contacto, su colegio de procedencia, la carrera a la que postula y su apoderado. | Alta | Implementado |
| RF-16 | Reconocer al estudiante que ya está registrado y actualizar su fecha de última visita en lugar de duplicarlo. | Alta | Implementado |
| RF-17 | Guardar en la ficha del estudiante el historial de los ciclos en los que se matriculó. | Alta | Implementado |
| RF-18 | Registrar el resultado de los simulacros de cada estudiante y su puntaje acumulado. | Alta | Pendiente |
| RF-19 | Emitir el carné del estudiante con su código y su fotografía. | Baja | Pendiente |
| RF-20 | Registrar el resultado del examen de admisión al que postuló el estudiante. | Media | Pendiente |
| RF-21 | Clasificar a los estudiantes en nuevos y recurrentes según su historial de matrículas. | Media | Pendiente |

### M4. Matrícula y pagos

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-22 | Configurar la campaña de descuento vigente y aplicar además el descuento propio del tipo de ciclo. | Alta | Implementado |
| RF-23 | Cotizar el precio final de la matrícula mostrando el desglose completo, sin registrarla. | Alta | Implementado |
| RF-24 | Registrar la matrícula indicando la forma de pago: al contado o fraccionada en 2, 3 o 4 cuotas. | Alta | Implementado |
| RF-25 | Impedir matricular a un estudiante en un ciclo sin vacantes o en el que ya está matriculado. | Alta | Implementado |
| RF-26 | Emitir el comprobante de la matrícula en formato PDF con el desglose del precio. | Media | Pendiente |
| RF-27 | Registrar el cronograma de cuotas y el estado de pago de cada una. | Alta | Pendiente |
| RF-28 | Anular una matrícula y devolver la vacante al ciclo. | Alta | Pendiente |
| RF-29 | Trasladar a un estudiante de un ciclo a otro conservando lo pagado. | Media | Pendiente |
| RF-30 | Aplicar descuento por hermanos matriculados en el mismo periodo. | Media | Pendiente |

### M5. Control de asistencia

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-31 | Registrar la asistencia de un estudiante a una sesión de su ciclo, con su estado y una observación opcional. | Alta | Implementado |
| RF-32 | Registrar la asistencia de forma masiva para todos los estudiantes de un ciclo en una sola operación. | Media | Pendiente |
| RF-33 | Justificar una ausencia adjuntando el documento de sustento del apoderado. | Media | Pendiente |
| RF-34 | Notificar al apoderado el mismo día en que el estudiante registra una ausencia. | Alta | Pendiente |

### M6. Reportes y persistencia

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-35 | Mostrar la oferta académica agrupada por categoría, con el estado y las vacantes de cada ciclo. | Alta | Implementado |
| RF-36 | Emitir el listado de estudiantes y docentes, el historial de matrículas, las estadísticas del periodo, el reporte financiero y el reporte de asistencia con alerta de riesgo. | Alta | Implementado |
| RF-37 | Mostrar un tablero con los indicadores del periodo: matriculados, ingreso y ocupación. | Media | Pendiente |
| RF-38 | Comparar las matrículas del periodo contra las del periodo anterior. | Media | Pendiente |
| RF-39 | Guardar la información en archivos de texto, recuperarla al iniciar y exportar el historial de matrículas a un archivo CSV. | Alta | Implementado |
| RF-40 | Recuperar los archivos de forma automática al abrir el programa, sin que el usuario deba solicitarlo. | Alta | Pendiente |
| RF-41 | Generar una copia de respaldo cuyo nombre incluya la fecha y la hora de creación. | Media | Pendiente |
| RF-42 | Migrar el almacenamiento de archivos de texto a una base de datos relacional. | Alta | Pendiente |
| RF-43 | Ofrecer una interfaz gráfica de escritorio para el personal de la academia. | Alta | Pendiente |
| RF-44 | Controlar el acceso con usuario, contraseña y perfil diferenciado por cargo. | Alta | Pendiente |
| RF-45 | Registrar en una bitácora quién hizo cada matrícula, anulación y cambio de costo. | Media | Pendiente |

---

## 4. Criterios de aceptación

### RF-01 — Registrar un ciclo o curso en la oferta · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 1.1 | Alta de un ciclo regular | El operador elige el tipo `CICLO` | Se crea el objeto y se le asignan sus datos | Se instancia un `CicloRegular` y se guarda en la colección de `Ciclo` por upcasting |
| 1.2 | Alta de un curso intensivo | El operador elige el tipo `CURSO` | Se crea el objeto y se le asignan sus datos | Se instancia un `CursoIntensivo` y se guarda en la misma colección |
| 1.3 | Datos propios del tipo | El ciclo ya tiene sus datos comunes | El programa comprueba `c instanceof CicloRegular` y hace el downcasting | Al ciclo regular se le piden turno, meses y simulacros; al curso, área, semanas y horas diarias |
| 1.4 | Código con formato inválido | El código no tiene la forma `CIC-000` ni `CUR-000` | Se invoca `setCodigo()` | Se muestra el formato esperado y el atributo queda sin asignar |
| 1.5 | Orden de las fechas | Se intenta asignar la fecha de fin sin haber asignado la de inicio | Se invoca `setFec_fin()` | Se muestra `Error: primero debe ingresar la fecha de inicio` |
| 1.6 | Fecha de fin anterior al inicio | La fecha de fin no es posterior a la de inicio | Se invoca `setFec_fin()` | Se muestra `Error: la fecha de fin debe ser posterior a la de inicio` |
| 1.7 | Costo base fuera de rango | El costo es menor a 150 o mayor a 5000 soles | Se invoca `setCosto_base()` | Se muestra `Error: el costo base debe estar entre S/ 150 y S/ 5000` |
| 1.8 | Modalidad no reconocida | La modalidad no es `PRESENCIAL` ni `VIRTUAL` | Se invoca `setModalidad()` | Se muestra `Error: la modalidad debe ser PRESENCIAL o VIRTUAL` |
| 1.9 | Orden de las vacantes | Se intenta asignar las vacantes sin haber asignado la modalidad | Se invoca `setVacantes()` | Se muestra `Error: primero debe ingresar si es PRESENCIAL o VIRTUAL` |
| 1.10 | Aforo del presencial | La modalidad es `PRESENCIAL` y se piden más de 45 vacantes | Se invoca `setVacantes()` | Se muestra `Error: un ciclo PRESENCIAL no puede superar las 45 vacantes por aula` |
| 1.11 | Coherencia de los simulacros | El ciclo dura 5 meses y se indican 3 simulacros | Se invoca `setNro_simulacros()` | Se exige al menos un simulacro por mes y se vuelve a pedir el dato |
| 1.12 | Entrada no numérica | El operador escribe texto donde se espera un número | `Integer.parseInt()` lanza `NumberFormatException` | `leerEntero()` la captura, muestra el aviso y vuelve a pedir el dato |
| 1.13 | Fecha mal escrita | La fecha no tiene el formato `aaaa-mm-dd` | `LocalDate.parse()` lanza `DateTimeParseException` | `leerFecha()` la captura y vuelve a pedir la fecha |

**Clases involucradas:** `Ciclo`, `CicloRegular`, `CursoIntensivo`, `AcademiaSanFernando`

---

### RF-02 — Impedir el registro duplicado por código · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 2.1 | Código nuevo | El código no existe en la colección | Se recorre la colección antes de asignarlo | El código se asigna y el registro continúa |
| 2.2 | Código repetido | Ya hay un ciclo con ese código | El recorrido encuentra la coincidencia | Se muestra `Error: ya existe un ciclo con el codigo ...` y no se registra |
| 2.3 | Comparación sin distinguir mayúsculas | El código se escribe en minúsculas | La comparación usa `equalsIgnoreCase()` | Se detecta igual como duplicado |
| 2.4 | La colección no se altera | Se rechazó un código duplicado | Se consulta el tamaño de la colección | La cantidad de ciclos es la misma que antes del intento |

**Clases involucradas:** `Ciclo`, `AcademiaSanFernando`

---

### RF-03 — Clasificación automática por categoría · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 3.1 | Ciclo regular básico | El costo base del ciclo regular es menor a 600 soles | Se invoca `calcularCategoria()` | Devuelve `BASICO` |
| 3.2 | Ciclo regular intermedio | El costo base está entre 600 y 1200 soles | Se invoca `calcularCategoria()` | Devuelve `INTERMEDIO` |
| 3.3 | Ciclo regular premium | El costo base supera los 1200 soles | Se invoca `calcularCategoria()` | Devuelve `PREMIUM` |
| 3.4 | Curso intensivo por carga horaria | El curso tiene 6 o más horas diarias | Se invoca `calcularCategoria()` | Devuelve `PREMIUM` sin considerar el costo |
| 3.5 | Curso intensivo por costo | El curso tiene menos de 6 horas diarias | Se invoca `calcularCategoria()` | Clasifica por costo: menos de 400 básico, hasta 900 intermedio, más premium |
| 3.6 | Clasificación automática | Se termina de registrar un ciclo | El programa muestra la confirmación | Informa la categoría y el descuento asignados sin que el operador los haya ingresado |

**Clases involucradas:** `Ciclo`, `CicloRegular`, `CursoIntensivo`

---

### RF-04 — Modificar el costo base · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 4.1 | Cambio permitido | El ciclo sigue con vacantes abiertas | Se modifica su costo base | El nuevo costo se asigna y la categoría se recalcula |
| 4.2 | Cambio bloqueado | El ciclo ya tiene matriculados | Se intenta modificar su costo | La operación se rechaza para no alterar lo ya cobrado |
| 4.3 | Registro del cambio | Se modifica el costo | Se confirma la operación | Queda registrado el costo anterior, el nuevo y la fecha |

---

### RF-05 — Cancelar un ciclo sin quórum · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 5.1 | Cancelación | El ciclo no alcanzó el mínimo de matriculados antes de su fecha de inicio | Se cancela | El ciclo deja de aparecer en la oferta y se marca como cancelado |
| 5.2 | Notificación | El ciclo se cancela | Se confirma la operación | Se listan los estudiantes afectados para contactarlos |

---

### RF-06 — Plan de estudios del ciclo · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 6.1 | Asociación de asignaturas | Existen asignaturas registradas | Se agregan al plan de un ciclo | El ciclo muestra la lista de asignaturas que lo componen |
| 6.2 | Asignatura repetida | La asignatura ya está en el plan | Se intenta agregarla otra vez | Se rechaza indicando que ya pertenece al plan |

---

### RF-07 — Horario semanal · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 7.1 | Bloque válido | El día es de lunes a sábado y la hora corresponde al turno del ciclo | Se registra el bloque | El bloque queda asociado al ciclo |
| 7.2 | Bloque fuera del turno | La hora no corresponde al turno | Se registra el bloque | Se rechaza indicando el rango horario del turno |

---

### RF-08 — Aula física del ciclo presencial · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 8.1 | Aforo suficiente | El aula tiene capacidad igual o mayor a las vacantes del ciclo | Se asigna el aula | La asignación procede |
| 8.2 | Aforo insuficiente | El aula tiene menos capacidad que las vacantes | Se asigna el aula | Se rechaza indicando el aforo del aula y las vacantes del ciclo |

---

### RF-09 — Registrar docente y calcular su pago · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 9.1 | Código con el formato exigido | El código tiene la forma `DOC-000` | Se invoca `setCodigo()` | El código se asigna en mayúsculas |
| 9.2 | Código con formato inválido | El código es `D-1` o `DOCENTE1` | Se invoca `setCodigo()` | Se muestra `Error: el codigo debe tener el formato DOC-000 (ejemplo DOC-001)` |
| 9.3 | Especialidad no reconocida | La especialidad no está entre las seis que dicta la academia | Se invoca `setEspecialidad()` | Se muestra la lista de especialidades válidas |
| 9.4 | Tarifa fuera de rango | La tarifa es menor a 15 o mayor a 120 soles | Se invoca `setTarifa_hora()` | Se muestra `Error: la tarifa por hora debe estar entre S/ 15 y S/ 120` |
| 9.5 | Bonificación fuera de rango | El porcentaje es 0 o mayor a 5 | Se invoca `setBonificacion()` | Se muestra `Error: la bonificacion debe estar entre 1 y 5 por ciento` |
| 9.6 | Documento ya registrado | El documento pertenece a otra persona de la colección | El recorrido encuentra la coincidencia | Se muestra `Error: ya existe una persona con el documento ...` y no se registra |
| 9.7 | Horas fuera de rango | Se indican 0 o más de 200 horas en el mes | Se invoca `registrarHoras()` | Se muestra el rango permitido y el acumulado no cambia |
| 9.8 | Pago por dictado | La tarifa es 35 soles y dictó 80 horas | Se invoca `calcularPagoHoras()` | Devuelve 2800.00 |
| 9.9 | Bonificación por tutoría | El monto gestionado es 5096.30 y la bonificación 3 % | Se invoca `calcularBonificacion()` | Devuelve 152.89, redondeado a dos decimales |
| 9.10 | Pago total | Se suman el pago por dictado y la bonificación | Se invoca `calcularPagoTotal()` | Devuelve 2952.89 |

**Clases involucradas:** `Persona`, `Docente`, `AcademiaSanFernando`

---

### RF-10 — Asignaturas por especialidad · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 10.1 | Asignación coherente | La asignatura corresponde a la especialidad del docente | Se asigna | La asignación procede |
| 10.2 | Asignación incoherente | La asignatura no corresponde a su especialidad | Se asigna | Se emite un aviso y se pide confirmación |

---

### RF-11 — Cruce de horario del docente · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 11.1 | Sin cruce | El docente no dicta en otro ciclo en ese día y hora | Se asigna el bloque | La asignación procede |
| 11.2 | Con cruce | El docente ya dicta en otro ciclo en ese día y hora | Se asigna el bloque | Se rechaza indicando el ciclo en conflicto |

---

### RF-12 — Asistencia del docente · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 12.1 | Sesión dictada | El docente dicta la sesión | Se registra su asistencia | Las horas cuentan para el cálculo de su pago |
| 12.2 | Sesión no dictada | El docente no asiste | Se registra la ausencia | Las horas de esa sesión se descuentan de su pago |

---

### RF-13 — Baja de docente · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 13.1 | Baja lógica | El docente tiene ciclos dictados | Se da de baja | Deja de aparecer en la lista activa pero su historial se conserva |
| 13.2 | Bloqueo de nuevas tutorías | El docente está dado de baja | Se intenta asignarle una tutoría | La operación se rechaza |

---

### RF-14 — Liquidación de la planilla · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 14.1 | Liquidación del periodo | El periodo se cierra | Se liquida la planilla | Se registra el monto pagado y los acumulados vuelven a cero |
| 14.2 | Doble liquidación | El periodo ya fue liquidado | Se intenta liquidar otra vez | La operación se rechaza indicando la fecha anterior |

---

### RF-15 — Registrar un estudiante · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 15.1 | Registro válido | Todos los datos cumplen las validaciones de la clase base y de la clase `Estudiante` | Se completa el ingreso | El estudiante se guarda en la colección de `Persona` por upcasting |
| 15.2 | Tipo de documento no reconocido | El tipo no es `DNI` ni `CE` | Se invoca `setTipo_doc()` | Se muestra `Error: tipo de documento invalido, solo se acepta DNI o CE` |
| 15.3 | Orden de ingreso del documento | Se intenta asignar el número antes del tipo | Se invoca `setNro_doc()` | Se muestra `Error: primero debe ingresar el tipo de documento` |
| 15.4 | DNI con longitud incorrecta | El tipo es `DNI` y el número no tiene 8 dígitos | Se invoca `setNro_doc()` | Se muestra `Error: el DNI debe tener 8 digitos` |
| 15.5 | CE con longitud incorrecta | El tipo es `CE` y el número no tiene 9 dígitos | Se invoca `setNro_doc()` | Se muestra `Error: el CE debe tener 9 digitos` |
| 15.6 | Código con formato inválido | El código no tiene la forma `EST-000` | Se invoca `setCodigo()` | Se muestra el formato esperado con un ejemplo |
| 15.7 | Edad fuera del rango del postulante | La fecha de nacimiento da una edad menor a 14 o mayor a 30 | Se invoca `setFec_nacimiento()` | Se muestra el rango permitido y la edad calculada |
| 15.8 | Menor de edad sin apoderado | La edad calculada es menor a 18 y el apoderado tiene menos de 5 caracteres | Se invoca `setApoderado()` | Se muestra `Error: el postulante es menor de edad, el apoderado es obligatorio` |
| 15.9 | Teléfono inválido | El teléfono no tiene 9 dígitos o no empieza con 9 | Se invoca `setTelefono()` | Se muestra `Error: el telefono debe tener 9 digitos y empezar con 9` |
| 15.10 | Nombres con números | Los nombres contienen dígitos | Se invoca `setNombres()` | Se muestra `Error: los nombres solo deben tener letras (minimo 2)` |

**Clases involucradas:** `Persona`, `Estudiante`

---

### RF-16 — Reconocer al estudiante ya registrado · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 16.1 | Estudiante que vuelve | El documento ya pertenece a un estudiante | El recorrido lo encuentra y se hace el downcasting a `Estudiante` | Se invoca `registrarVisita()`, se avisa que ya estaba registrado y se muestran sus datos |
| 16.2 | Sin duplicado | El estudiante ya existía | Se consulta el tamaño de la colección | La cantidad de personas no aumenta |
| 16.3 | Documento de un docente | El documento pertenece a un docente, no a un estudiante | El recorrido lo encuentra pero el `instanceof` falla | Se muestra `Error: ese documento ya pertenece a un docente` |
| 16.4 | Actualización de la visita | El estudiante vuelve a la academia | Se invoca `registrarVisita()` | La fecha de última visita pasa a ser la del día |

**Clases involucradas:** `Estudiante`, `Persona`, `AcademiaSanFernando`

---

### RF-17 — Historial de matrículas del estudiante · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 17.1 | Registro de la matrícula | La matrícula se confirma | Se invoca `agregarMatricula()` | El ciclo entra en la lista del estudiante y se actualiza su última visita |
| 17.2 | Varias matrículas | El estudiante se matriculó en más de un ciclo | Se consulta su ficha | Se listan todos sus ciclos con tipo, código, nombre y categoría |
| 17.3 | Estudiante sin matrículas | El estudiante nunca se matriculó | Se consulta su ficha | Se muestra `No tiene matriculas` |
| 17.4 | La lista acumula | El estudiante acumula matrículas sucesivas | Se consulta la colección interna | Los elementos anteriores se conservan: la relación es de uno a muchos |

**Clases involucradas:** `Estudiante`, `Ciclo`

---

### RF-18 — Resultados de los simulacros · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 18.1 | Registro del puntaje | El estudiante rinde un simulacro | Se registra su puntaje | El puntaje queda asociado al estudiante y al simulacro |
| 18.2 | Promedio acumulado | El estudiante rindió varios simulacros | Se consulta su ficha | Se muestra su promedio y su evolución |

---

### RF-19 — Carné del estudiante · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 19.1 | Emisión | El estudiante está matriculado | Se solicita el carné | Se genera con su código, su nombre, su ciclo y su fotografía |
| 19.2 | Sin matrícula | El estudiante no está matriculado | Se solicita el carné | La emisión se rechaza |

---

### RF-20 — Resultado del examen de admisión · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 20.1 | Ingreso registrado | El estudiante ingresó a la universidad | Se registra el resultado | Queda asociado a su ficha y al ciclo que llevó |
| 20.2 | Tasa de ingreso | Varios estudiantes de un ciclo rindieron el examen | Se consulta el ciclo | Se muestra el porcentaje de ingresantes |

---

### RF-21 — Clasificación de estudiantes · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 21.1 | Estudiante recurrente | El estudiante tiene dos o más matrículas | Se consulta su clasificación | Se le identifica como recurrente |
| 21.2 | Estudiante nuevo | El estudiante no tiene matrículas previas | Se consulta su clasificación | Se le identifica como nuevo |

---

### RF-22 — Campaña de descuento y descuento del ciclo · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 22.1 | Campaña vigente | La administración indica que hay campaña | Se ingresa su nombre y su porcentaje | El descuento se aplica a todas las matrículas del periodo |
| 22.2 | Descuento fuera de rango | El porcentaje es negativo o mayor a 25 | El bucle `while` valida el valor | Se muestra `Error: el descuento debe estar entre 0 y 25%` y se vuelve a pedir |
| 22.3 | Sin campaña | No hay campaña vigente | Se omite la configuración | La campaña queda como `SIN CAMPANA` con 0 % de descuento |
| 22.4 | Descuento del ciclo virtual | El ciclo regular tiene modalidad `VIRTUAL` | Se invoca `calcularDescuento()` | Devuelve 8 % de descuento adicional |
| 22.5 | Descuento vacacional | El curso intensivo dura 4 semanas o menos | Se invoca `calcularDescuento()` | Devuelve 6 % de descuento adicional |
| 22.6 | Sin descuento aplicable | El ciclo no cumple ninguna condición | Se invoca `calcularDescuento()` | Devuelve 0 y el precio no se altera por este concepto |
| 22.7 | Descuento por polimorfismo | La matrícula no sabe si el ciclo es regular o intensivo | `Matricula` invoca `ciclo.calcularDescuento()` | Se ejecuta la versión de `CicloRegular` o la de `CursoIntensivo` según el objeto real |

**Clases involucradas:** `Ciclo`, `CicloRegular`, `CursoIntensivo`, `Matricula`, `AcademiaSanFernando`

---

### RF-23 — Cotizar sin registrar la matrícula · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 23.1 | Desglose completo | La matrícula está construida con su estudiante, tutor y ciclo | Se invoca `calcularPrecioFinal()` y luego `verDetallePrecio()` | Se muestran costo base, descuento de campaña, descuento del ciclo, subtotal, recargo y precio final |
| 23.2 | Recargo al contado | La forma de pago es `CONTADO` | Se invoca `calcularPorcentajeRecargo()` | Devuelve 0 y el precio final coincide con el subtotal |
| 23.3 | Recargo por cuotas | La forma de pago es `FRACCIONADO` | Se invoca `calcularPorcentajeRecargo()` | Devuelve 4 % a 2 cuotas, 7 % a 3 y 10 % a 4 |
| 23.4 | Cuota mensual | La matrícula es fraccionada | Se invoca `calcularPrecioFinal()` | Se calcula y se muestra la cuota mensual y la cantidad de cuotas |
| 23.5 | Redondeo a dos decimales | Los montos salen con más decimales | Se invoca el método privado `redondear()` | Todos los importes se muestran con dos decimales |
| 23.6 | Cotización sin efecto | El apoderado no confirma la matrícula | Se responde que no al confirmar | La vacante sigue disponible y no se agrega ninguna matrícula |

**Clases involucradas:** `Matricula`, `Ciclo`, `AcademiaSanFernando`

---

### RF-24 — Registrar la matrícula · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 24.1 | Matrícula confirmada | El estudiante, el tutor y el ciclo existen y el ciclo tiene vacantes | Se confirma la matrícula | Se asigna el código correlativo `MAT-n`, se descuenta una vacante, se agrega el ciclo al historial y se acumula al tutor |
| 24.2 | Estudiante inexistente | El código no corresponde a ningún estudiante | Se busca el estudiante | Se muestra `Error: no existe un estudiante con ese codigo. Primero debe registrarlo` |
| 24.3 | Docente inexistente | El código no corresponde a ningún docente | Se busca el tutor | Se muestra `Error: no existe un docente con ese codigo` |
| 24.4 | Ciclo inexistente | El código no corresponde a ningún ciclo | Se busca el ciclo | Se muestra `Error: no existe un ciclo con ese codigo` |
| 24.5 | Forma de pago no reconocida | La forma de pago no es `CONTADO` ni `FRACCIONADO` | Se invoca `setForma_pago()` | Se muestra `Error: la forma de pago debe ser CONTADO o FRACCIONADO` y se vuelve a pedir |
| 24.6 | Número de cuotas no permitido | Las cuotas no son 2, 3 ni 4 | Se invoca `setNro_cuotas()` | Se muestra `Error: solo se fracciona en 2, 3 o 4 cuotas` |
| 24.7 | Código correlativo | Ya existen cinco matrículas | Se confirma la sexta | El código asignado es `MAT-6` |
| 24.8 | Efectos de la matrícula | La matrícula se confirma | Se ejecutan las tres actualizaciones | El ciclo descuenta una vacante, el estudiante suma el ciclo y el tutor suma la matrícula y su monto |

**Clases involucradas:** `Matricula`, `Estudiante`, `Docente`, `Ciclo`, `AcademiaSanFernando`

---

### RF-25 — Impedir la matrícula sin vacantes o duplicada · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 25.1 | Ciclo con vacantes | El estado es `ABIERTO` y hay cupos libres | Se invoca `hayVacantes()` | Devuelve verdadero y la matrícula continúa |
| 25.2 | Ciclo sin vacantes | Los matriculados igualan a las vacantes | Se invoca `hayVacantes()` | Se muestra `SIN VACANTES: el ciclo ... ya esta CERRADO` y la matrícula se cancela |
| 25.3 | Cierre automático | Se matricula al último cupo disponible | Se invoca `registrarMatriculado()` | El estado pasa de `ABIERTO` a `CERRADO` sin intervención del operador |
| 25.4 | Matrícula duplicada | El estudiante ya figura en ese ciclo | Se recorre su historial antes de matricular | Se muestra `Error: el estudiante ya esta matriculado en ese ciclo` |

**Clases involucradas:** `Ciclo`, `Estudiante`, `AcademiaSanFernando`

---

### RF-26 — Comprobante en PDF · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 26.1 | Emisión | La matrícula se confirmó | Se solicita el comprobante | Se genera un PDF con el desglose completo del precio |
| 26.2 | Numeración | Ya se emitieron comprobantes | Se emite uno nuevo | El número es consecutivo y no se repite |

---

### RF-27 — Cronograma de cuotas · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 27.1 | Generación | La matrícula es fraccionada en 3 cuotas | Se registra | Se generan 3 cuotas con su monto y su fecha de vencimiento |
| 27.2 | Suma exacta | Se suman todas las cuotas | Se compara con el precio final | Los montos coinciden sin diferencia de redondeo |
| 27.3 | Cuota vencida | Una cuota pasó su fecha sin pago | Se consulta la matrícula | La cuota aparece marcada como vencida |

---

### RF-28 — Anular una matrícula · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 28.1 | Anulación válida | La matrícula existe y no fue anulada | Se anula | El ciclo recupera la vacante y vuelve a `ABIERTO` si estaba cerrado |
| 28.2 | Reversión de acumulados | La matrícula se anula | Se recalculan los acumulados | El monto y la bonificación del tutor se descuentan |
| 28.3 | Doble anulación | La matrícula ya está anulada | Se intenta anular otra vez | La operación se rechaza |

---

### RF-29 — Traslado entre ciclos · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 29.1 | Traslado válido | El ciclo destino tiene vacantes | Se solicita el traslado | El estudiante queda en el ciclo destino y libera la vacante del origen |
| 29.2 | Diferencia de costo | El ciclo destino cuesta más | Se confirma el traslado | Se calcula y se informa la diferencia a pagar |

---

### RF-30 — Descuento por hermanos · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 30.1 | Hermano matriculado | Otro estudiante con el mismo apoderado está matriculado en el periodo | Se calcula el precio | Se aplica el descuento configurado |
| 30.2 | Descuentos no acumulables | Corresponden el descuento por hermanos y el del ciclo | Se calcula el precio | Se aplica solo el mayor de los dos |

---

### RF-31 — Registrar la asistencia · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 31.1 | Registro válido | El estudiante está matriculado en el ciclo y la fecha no es futura | Se completa el ingreso | Se asigna el código correlativo `ASI-n` y la asistencia entra en la colección |
| 31.2 | Estudiante sin matrícula | El estudiante no tiene ninguna matrícula | Se intenta registrar su asistencia | Se muestra que el estudiante no tiene matrícula y no se pide más datos |
| 31.3 | Ciclo no cursado | El estudiante no está matriculado en el ciclo indicado | Se busca el ciclo en su historial | Se muestra `Error: el estudiante no esta matriculado en ese ciclo` |
| 31.4 | Fecha futura | La fecha de la sesión es posterior a hoy | Se invoca `setFecha()` | Se muestra `Error: no se puede registrar la asistencia de una fecha futura` |
| 31.5 | Estado no permitido | El estado no es uno de los cuatro admitidos | Se invoca `setEstado()` | Se muestra la lista de estados válidos y se vuelve a pedir |
| 31.6 | Registro duplicado | Ya existe la asistencia de ese estudiante, ciclo y fecha | Se recorre la colección antes de agregar | Se muestra `Error: ya existe la asistencia de ese estudiante en ese ciclo y fecha` |
| 31.7 | Observación opcional | El operador deja la observación vacía | Se registra la asistencia | La asistencia se registra y la observación no se muestra al listar |
| 31.8 | Cómputo de la asistencia | El estado es `PRESENTE`, `TARDANZA` o `JUSTIFICADO` | Se invoca `cuentaComoAsistio()` | Devuelve verdadero; con `AUSENTE` devuelve falso |

**Clases involucradas:** `Asistencia`, `Estudiante`, `Ciclo`, `AcademiaSanFernando`

---

### RF-32 — Registro masivo de asistencia · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 32.1 | Carga masiva | El ciclo tiene treinta matriculados | Se marca a todos como presentes | Se generan treinta registros en una sola operación |
| 32.2 | Excepciones | Dos estudiantes faltaron | Se marcan antes de confirmar | Esos dos quedan como `AUSENTE` y el resto como `PRESENTE` |

---

### RF-33 — Justificación de ausencia · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 33.1 | Justificación aceptada | Se adjunta el documento dentro del plazo | Se procesa | El estado cambia de `AUSENTE` a `JUSTIFICADO` |
| 33.2 | Sin documento | No se adjunta sustento | Se intenta justificar | La ausencia se mantiene |

---

### RF-34 — Notificación al apoderado · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 34.1 | Envío | Se registra una ausencia y el apoderado tiene teléfono | Termina la jornada | Se envía el mensaje y queda registrada la hora |
| 34.2 | Sin teléfono | El apoderado no tiene teléfono registrado | Termina la jornada | Se informa que no se pudo notificar |

---

### RF-35 — Oferta agrupada por categoría · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 35.1 | Agrupación correcta | Hay ciclos de las tres categorías | Se muestra la oferta | Cada ciclo aparece bajo el encabezado de la categoría que le corresponde |
| 35.2 | Reglas visibles | Se muestra la oferta | Se imprime el encabezado | Se explican las reglas de categoría de ciclos regulares y de cursos intensivos |
| 35.3 | Vacantes y estado | Hay ciclos abiertos y cerrados | Se muestra la oferta | Cada línea indica los matriculados sobre las vacantes y el estado |
| 35.4 | Categoría sin elementos | No hay ciclos de una categoría | Se muestra la oferta | El encabezado aparece sin elementos debajo, sin lanzar excepción |

**Clases involucradas:** `Ciclo`, `AcademiaSanFernando`

---

### RF-36 — Listados, estadísticas y reportes · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 36.1 | Listado polimórfico de personas | Hay estudiantes y docentes en la colección de `Persona` | Se recorre invocando `p.verDatos()` | Cada objeto responde con su propia versión: polimorfismo en tiempo de ejecución |
| 36.2 | Listado polimórfico de ciclos | Hay ciclos regulares y cursos intensivos | Se recorre la colección de `Ciclo` | El ciclo regular muestra turno, meses y simulacros; el curso, área, semanas y horas |
| 36.3 | Datos comunes reutilizados | Se muestra cualquier ciclo | La clase hija invoca `verDatosGenerales()` | Los datos comunes se imprimen una sola vez, definidos en la clase abstracta |
| 36.4 | Historial de matrículas | Hay matrículas registradas | Se muestra el historial | Cada línea trae código, fecha, estudiante, ciclo, forma de pago y precio final |
| 36.5 | Sin matrículas | No hay matrículas registradas | Se muestra el historial | Se informa `Aun no hay matriculas registradas` y no se calcula el promedio |
| 36.6 | Estadísticas del periodo | Hay matrículas de los dos tipos y las tres categorías | Se acumulan los contadores en un solo recorrido | Se muestra el conteo por tipo, por categoría y por forma de pago, más la matrícula promedio |
| 36.7 | Reporte financiero | Hay matrículas y docentes con bonificación | Se genera el reporte | Se muestran los descuentos, los recargos, el ingreso total, la planilla docente y el ingreso neto |
| 36.8 | Porcentaje de asistencia | El estudiante tiene tres registros y asistió a dos | Se calcula su porcentaje | Devuelve 67 % redondeado con `Math.round()` |
| 36.9 | Alerta de riesgo | El porcentaje de asistencia es menor a 70 | Se genera el reporte | La línea del estudiante muestra `<-- RIESGO DE DESERCION` |
| 36.10 | Totales por estado | Hay registros de los cuatro estados | Se genera el reporte | Se muestra el consolidado de presentes, tardanzas, ausentes y justificados |

**Clases involucradas:** `Persona`, `Estudiante`, `Docente`, `Ciclo`, `Matricula`, `Asistencia`, `AcademiaSanFernando`

---

### RF-37 — Tablero de indicadores · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 37.1 | Indicadores visibles | Hay matrículas en el periodo | Se abre el tablero | Se muestran matriculados, ingreso y ocupación de los ciclos |
| 37.2 | Actualización | Se registra una matrícula nueva | Se vuelve a abrir el tablero | Los indicadores reflejan el cambio |

---

### RF-38 — Comparación entre periodos · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 38.1 | Variación calculada | Hay datos de dos periodos | Se genera la comparación | Se muestra la diferencia absoluta y porcentual |
| 38.2 | Sin periodo anterior | Es el primer periodo registrado | Se genera la comparación | Se informa que no hay base de comparación |

---

### RF-39 — Persistencia en archivos de texto · **Implementado**

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 39.1 | Creación de la carpeta | La carpeta `datos` no existe | Se invoca `guardarLineas()` | La carpeta se crea con `mkdirs()` y se informa su ruta absoluta |
| 39.2 | Escritura de las cuatro colecciones | Hay ciclos, personas, matrículas y asistencias en memoria | Se invoca `guardarTodo()` | Se generan los cuatro archivos y se informa la cantidad guardada en cada uno |
| 39.3 | Formato de la línea | Un ciclo regular se serializa | Se invoca `convertirALinea()` | Se devuelve una línea con los campos separados por punto y coma, encabezada por el discriminador `CICLO` |
| 39.4 | Serialización por polimorfismo | La colección tiene ciclos regulares y cursos intensivos | Se recorre invocando `convertirALinea()` | Cada objeto arma su propia línea sin que el recorrido pregunte su tipo |
| 39.5 | Reutilización de la clase base | Se serializa cualquier ciclo | La clase hija invoca `datosComunesEnLinea()` | Los campos comunes se arman una sola vez, definidos en la clase abstracta |
| 39.6 | Lectura del archivo | El archivo existe y tiene contenido | Se invoca `leerLineas()` | Se devuelve la lista de líneas no vacías, leídas con `BufferedReader` |
| 39.7 | Archivo inexistente | El archivo todavía no existe | Se invoca `leerLineas()` | Se muestra el aviso y se devuelve una lista vacía, sin lanzar excepción |
| 39.8 | Error de entrada y salida | Ocurre un fallo de lectura o escritura | Se produce la `IOException` | Se captura en el `catch`, se muestra el mensaje y el `finally` cierra el flujo |
| 39.9 | Cierre garantizado | La operación termina con o sin error | Se ejecuta el bloque `finally` | El flujo se cierra y se imprime `[Operacion de lectura finalizada]` o su equivalente de escritura |
| 39.10 | Recuperación de los objetos | Los archivos tienen líneas válidas | Se invocan los métodos de carga | Se vuelven a crear los objetos `CicloRegular`, `CursoIntensivo`, `Estudiante` y `Docente` según el discriminador |
| 39.11 | Vacantes conservadas | Un ciclo tenía tres matriculados al guardar | Se recupera del archivo | Vuelve con `3/30` y su estado original |
| 39.12 | Relaciones reconstruidas | Las matrículas referencian estudiante, tutor y ciclo | Se invoca el método de carga | Se vuelven a enlazar los tres objetos, el historial del estudiante y el acumulado del docente |
| 39.13 | Orden de la carga | Los archivos se leen al iniciar | Se ejecuta la sección 0 | Primero los ciclos, después las personas y al final las matrículas y las asistencias |
| 39.14 | Línea corrupta | Una línea tiene menos campos de los esperados | Se invoca el método de carga | Esa línea se omite con un aviso y la carga continúa con las demás |
| 39.15 | Referencia rota | Una matrícula apunta a un ciclo que ya no existe | Se invoca el método de carga | Esa matrícula se omite con un aviso, sin interrumpir la carga |
| 39.16 | Exportación a CSV | Hay matrículas registradas | Se invoca `exportarReporteMatriculas()` | Se genera `reporte_matriculas.csv` con fila de encabezados y una fila por matrícula |
| 39.17 | Visualización del archivo | Se indica el nombre de un archivo existente | Se invoca `mostrarArchivo()` | Se muestran las líneas numeradas y el tamaño del archivo en bytes |

**Clases involucradas:** `ArchivoUtil`, `AcademiaSanFernando` y el método `convertirALinea()` de todas las entidades

---

### RF-40 — Carga automática al iniciar · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 40.1 | Carga silenciosa | Existen archivos de datos | Se inicia el programa | Los objetos se cargan sin preguntar y se informa el total |
| 40.2 | Primera ejecución | No existe la carpeta de datos | Se inicia el programa | El programa arranca con las colecciones vacías, sin error |

---

### RF-41 — Copia de respaldo con fecha · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 41.1 | Nombre con marca temporal | Se solicita el respaldo | Se genera el archivo | El nombre incluye la fecha y la hora, de modo que no sobrescribe respaldos anteriores |
| 41.2 | Contenido íntegro | Hay datos en memoria | Se genera el respaldo | Contiene la misma cantidad de registros que los archivos de trabajo |

---

### RF-42 — Migración a base de datos · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 42.1 | Persistencia equivalente | Los datos están en archivos de texto | Se ejecuta la migración | Las mismas entidades quedan en tablas sin pérdida de registros |
| 42.2 | Conexión fallida | La base de datos no está disponible | Se inicia el programa | Se informa el problema sin que el programa termine de forma abrupta |

---

### RF-43 — Interfaz gráfica de escritorio · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 43.1 | Equivalencia funcional | La consola ofrece quince operaciones | Se usa la interfaz gráfica | Las mismas quince operaciones están disponibles en ventanas |
| 43.2 | Validación en pantalla | Se ingresa un dato inválido | Se confirma el formulario | El error se muestra junto al campo, sin cerrar la ventana |

---

### RF-44 — Control de acceso · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 44.1 | Acceso válido | El usuario y la contraseña son correctos | Se inicia sesión | Se muestran solo las opciones de su perfil |
| 44.2 | Acceso inválido | La contraseña es incorrecta | Se inicia sesión | Se niega el acceso sin precisar dónde está el error |
| 44.3 | Perfiles diferenciados | El usuario es docente y no administrador | Se inicia sesión | No puede configurar la campaña ni ver el reporte financiero |

---

### RF-45 — Bitácora de operaciones · Pendiente

| # | Criterio | Dado | Cuando | Entonces |
|---|---|---|---|---|
| 45.1 | Registro de la operación | Se concreta una matrícula, una anulación o un cambio de costo | Se completa la operación | Queda registrado el usuario, la fecha, la hora y la operación |
| 45.2 | Bitácora no editable | Un registro ya existe | Se intenta modificarlo | La operación se rechaza |

---

## 5. Trazabilidad de los quince requerimientos implementados

| Requerimiento | Historia | Sección del programa | Clases de dominio |
|---|---|---|---|
| RF-01 | HU-01 | 1. Registro de ciclos y cursos | `Ciclo`, `CicloRegular`, `CursoIntensivo` |
| RF-02 | HU-02 | 1. Registro de ciclos y cursos | `Ciclo` |
| RF-03 | HU-03 | 1. Registro de ciclos y cursos | `CicloRegular`, `CursoIntensivo` |
| RF-09 | HU-04 | 2. Registro de docentes | `Persona`, `Docente` |
| RF-15 | HU-05 | 3. Registro de estudiantes | `Persona`, `Estudiante` |
| RF-16 | HU-06 | 3. Registro de estudiantes | `Estudiante` |
| RF-17 | HU-07 | 5. Matrículas | `Estudiante`, `Ciclo` |
| RF-22 | HU-08 | 4. Campaña de descuento | `CicloRegular`, `CursoIntensivo`, `Matricula` |
| RF-23 | HU-09 | 5. Matrículas | `Matricula` |
| RF-24 | HU-10 | 5. Matrículas | `Matricula`, `Estudiante`, `Docente` |
| RF-25 | HU-11 | 5. Matrículas | `Ciclo`, `Estudiante` |
| RF-31 | HU-12 | 6. Control de asistencia | `Asistencia`, `Estudiante`, `Ciclo` |
| RF-35 | HU-13 | Catálogo por categoría | `Ciclo` |
| RF-36 | HU-14 | 7. Listas y reportes | `Persona`, `Ciclo` y sus clases hijas, `Matricula`, `Asistencia` |
| RF-39 | HU-15 | 0. Recuperar y 8. Guardar | `ArchivoUtil` y todas las entidades |

---

## 6. Documentos relacionados

| Documento | Contenido |
|---|---|
| `docs/historias-usuario.md` | Las quince historias de usuario en formato narrativo |
| `docs/Historias_Usuario_Academia.xlsx` | La matriz de historias con la trazabilidad a las clases Java |
| `docs/restricciones-y-alternativas.md` | Las restricciones del proyecto y las alternativas de solución |
| `docs/objetivos-y-alcance.md` | Los objetivos del proyecto y el alcance de la solución |
| `docs/diagrama_clases.png` | El diagrama de clases UML del sistema |
| `docs/manejo-archivos-java.md` | La investigación y la práctica de manejo de archivos en Java |
