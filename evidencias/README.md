# Evidencias de cumplimiento — Semanas 12 y 13

**Proyecto:** Sistema de gestión de matrícula y control de asistencia — Academia Preuniversitaria San Fernando  
**Curso:** Técnicas de Programación Orientada a Objetos (SIST1202A) — UPN Cajamarca, 2026-2

Cada captura muestra el programa cumpliendo uno o más criterios de aceptación de `docs/requerimientos-funcionales.md`. Se tomaron ejecutando la interfaz gráfica de este repositorio (clase principal `academia.VentanaPrincipal`) con los datos de prueba, en el orden en que aparecen: los resultados de cada paso son el punto de partida del siguiente.

Dos datos se prepararon a propósito para poder mostrar un criterio:

- La matrícula **MAT-4** figura registrada el **2026-09-01**, con su primera cuota pagada ese día, para que sus otras cuotas aparezcan **vencidas** (criterio 27.3).
- Para llenar el curso **CUR-003** (10 vacantes) y mostrar el rechazo por falta de vacantes (criterios 25.2 y 25.3) se matricularon **nueve postulantes de prueba** (EST-101 a EST-109).

Además de las capturas, la carpeta trae tres registros de ejecución:

| Archivo | Contenido |
|---|---|
| `log_interfaz.txt` | Lo que el programa escribió en consola durante el recorrido de la interfaz: los mensajes `Error:` de los métodos `set`, los avisos y las operaciones de archivo |
| `log_consola.txt` | La ejecución de la versión de consola (`AcademiaSanFernando`) con los datos de prueba |
| `log_pruebas_dominio.txt` | Las pruebas de las clases de dominio sin ventanas: cuotas, anulación, reclasificación, respaldo e interfaz `Registrable` |

## Capturas por requerimiento

| Requerimiento | Capturas |
|---|---|
| RF-01 | [04_registro_ciclo_categoria](04_registro_ciclo_categoria.png), [06_validaciones_set](06_validaciones_set.png) |
| RF-02 | [05_codigo_duplicado](05_codigo_duplicado.png) |
| RF-03 | [04_registro_ciclo_categoria](04_registro_ciclo_categoria.png) |
| RF-09 | [10_registro_docente](10_registro_docente.png), [10b_plana_docente](10b_plana_docente.png) |
| RF-15 | [08_apoderado_obligatorio](08_apoderado_obligatorio.png), [09_registro_estudiante](09_registro_estudiante.png), [09b_lista_estudiantes](09b_lista_estudiantes.png) |
| RF-16 | [07_estudiante_reconocido](07_estudiante_reconocido.png) |
| RF-17 | [17b_historial_estudiante](17b_historial_estudiante.png) |
| RF-21 | [09_registro_estudiante](09_registro_estudiante.png), [09b_lista_estudiantes](09b_lista_estudiantes.png), [17_estudiante_recurrente](17_estudiante_recurrente.png), [17b_historial_estudiante](17b_historial_estudiante.png), [28_tablero](28_tablero.png) |
| RF-22 | [11_campana](11_campana.png) |
| RF-23 | [12_cotizacion_desglose](12_cotizacion_desglose.png) |
| RF-24 | [13_confirmar_matricula](13_confirmar_matricula.png), [14_matricula_registrada](14_matricula_registrada.png) |
| RF-25 | [15_matricula_duplicada](15_matricula_duplicada.png), [16_sin_vacantes](16_sin_vacantes.png) |
| RF-27 | [12_cotizacion_desglose](12_cotizacion_desglose.png), [14_matricula_registrada](14_matricula_registrada.png), [14b_cronograma_creado](14b_cronograma_creado.png), [18_cronograma_vencidas](18_cronograma_vencidas.png), [19_pago_cuota](19_pago_cuota.png), [20_pago_duplicado](20_pago_duplicado.png) |
| RF-28 | [21_anular_con_pago](21_anular_con_pago.png), [22_confirmar_anulacion](22_confirmar_anulacion.png), [23_anulacion_vacante](23_anulacion_vacante.png), [24_ciclo_reabierto](24_ciclo_reabierto.png) |
| RF-31 | [25_asistencia_registrada](25_asistencia_registrada.png), [26_asistencia_duplicada](26_asistencia_duplicada.png) |
| RF-35 | [03_oferta_por_categoria](03_oferta_por_categoria.png) |
| RF-36 | [27_reporte_riesgo](27_reporte_riesgo.png), [28_tablero](28_tablero.png) |
| RF-37 | [28_tablero](28_tablero.png) |
| RF-39 | [30_guardar](30_guardar.png), [31_salir](31_salir.png), [32_reinicio_recupera](32_reinicio_recupera.png) |
| RF-40 | [00_primer_inicio](00_primer_inicio.png), [00b_primer_inicio_vacio](00b_primer_inicio_vacio.png), [01_inicio_carga_automatica](01_inicio_carga_automatica.png), [32_reinicio_recupera](32_reinicio_recupera.png) |
| RF-41 | [29_respaldo](29_respaldo.png) |
| RF-43 | [01_inicio_carga_automatica](01_inicio_carga_automatica.png), [02_menu_navegacion](02_menu_navegacion.png), [06_validaciones_set](06_validaciones_set.png) |

## Detalle de cada captura

| Captura | Requerimiento | Criterios | Qué demuestra |
|---|---|---|---|
| [00_primer_inicio](00_primer_inicio.png) | RF-40 | 40.2 | Primera ejecución sin archivos: el sistema arranca y ofrece datos de prueba |
| [00b_primer_inicio_vacio](00b_primer_inicio_vacio.png) | RF-40 | 40.2 | Se respondió No: arranca con las colecciones vacías, sin error |
| [01_inicio_carga_automatica](01_inicio_carga_automatica.png) | RF-43 / RF-40 | 43.1, 40.1 | Ventana principal con menú y escritorio; los archivos se recuperaron al abrir sin preguntar (barra de estado) |
| [02_menu_navegacion](02_menu_navegacion.png) | RF-43 | 43.1 | Navegación por menú: Datos básicos > Ciclos, Estudiantes, Docentes |
| [03_oferta_por_categoria](03_oferta_por_categoria.png) | RF-35 | 35.1, 35.2 | Oferta académica agrupada por categoría con vacantes y estado |
| [04_registro_ciclo_categoria](04_registro_ciclo_categoria.png) | RF-01 / RF-03 | 1.x, 3.x | Registro de un curso intensivo; se clasifica solo como PREMIUM (6 horas diarias) con 6% por ser corto |
| [05_codigo_duplicado](05_codigo_duplicado.png) | RF-02 | 2.1 | El mismo código se rechaza |
| [06_validaciones_set](06_validaciones_set.png) | RF-01 / RF-43 | 1.x, 43.2 | Datos inválidos: la ventana muestra los mismos mensajes que imprimen los set y no se cierra |
| [07_estudiante_reconocido](07_estudiante_reconocido.png) | RF-16 | 16.1, 16.2, 16.4 | El DNI ya existe: no se duplica, se actualiza la última visita |
| [08_apoderado_obligatorio](08_apoderado_obligatorio.png) | RF-15 | 15.x | Postulante de 16 años sin apoderado: se rechaza |
| [09_registro_estudiante](09_registro_estudiante.png) | RF-15 / RF-21 | 15.1, 21.2 | Estudiante registrado; sin matrículas es NUEVO |
| [09b_lista_estudiantes](09b_lista_estudiantes.png) | RF-15 / RF-21 | 21.2 | La tabla muestra la edad, los ciclos y el tipo de cada estudiante |
| [10_registro_docente](10_registro_docente.png) | RF-09 | 9.x | Docente registrado con su pago por horas |
| [10b_plana_docente](10b_plana_docente.png) | RF-09 | 9.x | Plana docente con tutorías, monto gestionado y pago total |
| [11_campana](11_campana.png) | RF-22 | 22.1 | Campaña de 10% configurada; se aplica desde ahora |
| [12_cotizacion_desglose](12_cotizacion_desglose.png) | RF-23 / RF-27 | 23.1, 23.3, 23.4, 27.1 | Cotización con desglose (campaña, descuento del ciclo, recargo 7%) y cronograma de 3 cuotas; no se registra |
| [13_confirmar_matricula](13_confirmar_matricula.png) | RF-24 | 24.x | Se pide confirmación antes de registrar |
| [14_matricula_registrada](14_matricula_registrada.png) | RF-24 / RF-27 | 24.x, 27.1, 27.2 | Matrícula registrada; la matrícula creó sus 3 cuotas (composición) |
| [14b_cronograma_creado](14b_cronograma_creado.png) | RF-27 | 27.1, 27.2 | Cronograma de la matrícula registrada: las cuotas suman el precio final |
| [15_matricula_duplicada](15_matricula_duplicada.png) | RF-25 | 25.4 | La misma estudiante en el mismo ciclo: se rechaza |
| [16_sin_vacantes](16_sin_vacantes.png) | RF-25 | 25.2, 25.3 | CUR-003 llegó a 10/10 y se cerró solo; ya no admite matrículas |
| [17_estudiante_recurrente](17_estudiante_recurrente.png) | RF-21 | 21.1 | Lucía lleva su segundo ciclo y pasa a RECURRENTE |
| [17b_historial_estudiante](17b_historial_estudiante.png) | RF-17 / RF-21 | 17.x, 21.1 | Historial de ciclos de la estudiante seleccionada: dos ciclos, RECURRENTE |
| [18_cronograma_vencidas](18_cronograma_vencidas.png) | RF-27 | 27.3 | Matrícula de septiembre: cuota 1 pagada y las que pasaron su fecha sin pago aparecen VENCIDA |
| [19_pago_cuota](19_pago_cuota.png) | RF-27 | 27.4 | Pago de la cuota 2; el saldo se actualiza |
| [20_pago_duplicado](20_pago_duplicado.png) | RF-27 | 27.5 | La misma cuota no se puede pagar dos veces |
| [21_anular_con_pago](21_anular_con_pago.png) | RF-28 | 28.4 | MAT-1 tiene su cuota pagada: la anulación se rechaza |
| [22_confirmar_anulacion](22_confirmar_anulacion.png) | RF-28 | 28.1, 28.5 | Se avisa que la vacante vuelve y que las cuotas se eliminan con la matrícula |
| [23_anulacion_vacante](23_anulacion_vacante.png) | RF-28 | 28.1, 28.2 | Matrícula anulada: CUR-003 recupera la vacante |
| [24_ciclo_reabierto](24_ciclo_reabierto.png) | RF-28 | 28.1 | CUR-003 pasó de CERRADO 10/10 a ABIERTO 9/10 |
| [25_asistencia_registrada](25_asistencia_registrada.png) | RF-31 | 31.1 | Asistencia registrada para un estudiante matriculado en el ciclo |
| [26_asistencia_duplicada](26_asistencia_duplicada.png) | RF-31 | 31.x | La misma sesión no se registra dos veces |
| [27_reporte_riesgo](27_reporte_riesgo.png) | RF-36 | 36.8, 36.9, 36.10 | Reporte por matrícula: porcentaje redondeado, alerta bajo 70% y totales por estado |
| [28_tablero](28_tablero.png) | RF-37 / RF-21 / RF-36 | 37.1, 37.2, 21.1, 36.7 | Indicadores del periodo: matrículas, ingreso, recaudado, por cobrar, ocupación, planilla y neto |
| [29_respaldo](29_respaldo.png) | RF-41 | 41.1, 41.2 | Copia de respaldo en una carpeta con fecha y hora |
| [30_guardar](30_guardar.png) | RF-39 | 39.x | Los datos se guardan en la carpeta datos |
| [31_salir](31_salir.png) | RF-39 | 39.x | Al salir se pregunta si se guardan los cambios |
| [32_reinicio_recupera](32_reinicio_recupera.png) | RF-39 / RF-40 | 39.x, 40.1 | Se cerró y se volvió a abrir: los datos guardados se recuperan solos (ver barra de estado y tablero) |

Las capturas no muestran la barra de título de Windows porque se tomaron en un equipo sin gestor de ventanas; el contenido de cada ventana es el mismo que se ve en NetBeans.
