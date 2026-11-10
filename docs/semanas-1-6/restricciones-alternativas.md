# Restricciones del proyecto y alternativas de solución

**Curso:** Técnicas de Programación Orientada a Objetos (SIST1202A) — 2026-2
**Proyecto:** Sistema de gestión de matrícula y control de asistencia para una academia preuniversitaria de Cajamarca

> ⚠️ Las cifras marcadas con **[CONFIRMAR]** deben validarse con la institución antes de entregar. Si no se puede validar alguna, se retira el dato en lugar de inventarlo.

---

## 1. Alcance

### Qué SÍ incluye esta primera versión

- Registro de estudiantes con validación de documento (DNI, CE, RUC, pasaporte)
- Registro de matrícula por ciclo
- Control de asistencia diaria por aula
- Consultas y listados: por tipo de documento, por rango de edad, ordenados por apellido
- Reportes de conteo por categoría
- Persistencia en archivos de texto (semana 9 del sílabo)

### Qué NO incluye

- Cobros, pagos ni facturación electrónica
- Notas ni evaluaciones
- Acceso desde varios equipos en simultáneo
- Aplicación web o móvil
- Envío de notificaciones a apoderados

La razón del recorte es de tiempo, no de dificultad: el ciclo tiene 16 semanas y el temario solo llega a conexión con base de datos en la semana 14.

---

## 2. Restricciones

### 2.1 Restricciones de datos

| Restricción | Justificación |
|---|---|
| El número de documento es único e identifica al estudiante | Es el criterio que usa la academia hoy en sus registros en papel. Se implementa con un `HashMap` que rechaza duplicados. |
| El DNI tiene exactamente 8 dígitos; CE 9; RUC 11; pasaporte 12 caracteres | Longitudes oficiales de RENIEC y SUNAT. Implementado en `Persona.validarDocumento()`. |
| El pasaporte admite letras; los demás documentos solo dígitos | Los pasaportes peruanos combinan letras y números. |
| La fecha de nacimiento no puede ser posterior a la fecha actual | Validado en `calcularEdad()`, que devuelve 0 en ese caso en lugar de un número negativo. |
| Los datos históricos de la academia están en cuadernos y hojas sueltas | No hay base de datos previa que migrar. La carga inicial será manual. **[CONFIRMAR]** |

### 2.2 Restricciones de tiempo

| Restricción | Justificación |
|---|---|
| El sistema debe estar operativo antes del inicio del siguiente ciclo | La matrícula es el momento de mayor carga; entregar después no resuelve nada. |
| El desarrollo está limitado a las 16 semanas del curso | El temario define qué se puede usar y cuándo: colecciones en la semana 5, archivos en la 9, interfaz gráfica en la 12–13, base de datos en la 14–15. |
| Solo se puede usar lo ya enseñado en cada entrega | Una solución con base de datos en la semana 6 sería inconsistente con lo evaluado. |

### 2.3 Restricciones de recursos

| Restricción | Justificación |
|---|---|
| El equipo son 4 estudiantes con carga académica completa | La disponibilidad real es de pocas horas semanales por integrante. |
| No hay presupuesto para licencias ni hosting | Se usa únicamente software libre: JDK, NetBeans/VS Code, Git, GitHub. |
| La academia dispone de computadoras de escritorio, no de servidor | Descarta cualquier arquitectura cliente-servidor. **[CONFIRMAR]** |
| El personal administrativo tiene manejo básico de computadora | La interfaz debe ser mínima y sin conceptos técnicos. **[CONFIRMAR]** |

### 2.4 Riesgos

| Riesgo | Probabilidad | Impacto | Mitigación |
|---|---|---|---|
| Pérdida de datos por falla del equipo | Media | Alto | Respaldo automático del archivo de datos al cerrar el programa |
| Un integrante se retrasa y bloquea al resto | Media | Medio | El código se reparte en clases independientes; el orden de integración está definido y documentado |
| Conflictos al trabajar en paralelo sobre los mismos archivos | Alta | Bajo | Flujo de ramas con `develop`, un archivo por responsable, revisión por PR |
| La institución cambia sus requisitos a mitad del ciclo | Baja | Alto | Diseño con clases desacopladas; el controlador no depende de la interfaz |
| Ingreso de datos erróneos por parte del usuario | Alta | Medio | Validación en el modelo y excepciones propias con mensajes en lenguaje claro |

---

## 3. Alternativas evaluadas

Se compararon tres formas de resolver el problema antes de decidir.

### Alternativa A — Seguir con registros en papel, mejorando los formatos

**En qué consiste:** rediseñar las fichas y cuadernos de asistencia, sin sistema informático.

- **A favor:** costo cero, sin curva de aprendizaje, no depende de que haya luz o computadora.
- **En contra:** no resuelve la causa raíz. La búsqueda de un estudiante sigue siendo manual, los reportes siguen siendo recuentos a mano, y la pérdida de una hoja sigue siendo pérdida de información.
- **Descartada porque** no elimina ninguno de los problemas identificados en el diagrama de Ishikawa.

### Alternativa B — Hoja de cálculo (Excel o Google Sheets)

**En qué consiste:** llevar matrícula y asistencia en libros de Excel con validación de datos.

- **A favor:** implementación inmediata, el personal ya conoce la herramienta, permite filtros y gráficos básicos.
- **En contra:** no impide duplicados de forma confiable, cualquiera puede sobrescribir una fórmula, no hay control de quién modificó qué, y a medida que crece se vuelve lento y frágil. Tampoco cumple el propósito académico del curso.
- **Descartada porque** traslada el problema en lugar de resolverlo: reemplaza el papel perdido por el archivo corrupto.

### Alternativa C — Aplicación de escritorio en Java con POO ✅ **SELECCIONADA**

**En qué consiste:** sistema orientado a objetos con validación en el modelo, colecciones en memoria y persistencia en archivos, con interfaz gráfica en las últimas semanas.

- **A favor:** la validación vive en las clases y no depende de la disciplina del usuario; el `HashMap` impide duplicados por construcción; las excepciones propias dan mensajes entendibles; el diseño permite crecer hacia base de datos sin rehacer el modelo; y coincide exactamente con el temario del curso.
- **En contra:** requiere desarrollo, tiene curva de aprendizaje para el usuario, y funciona en un solo equipo mientras no se migre a base de datos.
- **Seleccionada porque** es la única que ataca las causas raíz identificadas y a la vez es realizable dentro del ciclo con lo que el curso enseña.

---

## 4. Comparación resumida

| Criterio | A: Papel mejorado | B: Hoja de cálculo | C: Java POO |
|---|---|---|---|
| Costo | Nulo | Nulo | Nulo (tiempo del equipo) |
| Tiempo de implementación | Inmediato | Días | El ciclo completo |
| Impide duplicados | No | Parcial | Sí |
| Validación automática | No | Parcial | Sí |
| Trazabilidad de cambios | No | No | Sí |
| Reportes automáticos | No | Sí | Sí |
| Escalable a base de datos | No | No | Sí |
| Alineado al sílabo | No | No | Sí |

---

## 5. Decisión

Se adopta la **alternativa C**. Las alternativas A y B se descartan por no eliminar las causas raíz del problema, aunque se reconoce que B podría servir como solución provisional mientras el sistema se termina de desarrollar.
