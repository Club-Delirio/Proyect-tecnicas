# Requerimientos funcionales

Sistema de gestión de matrícula y control de asistencia — academia preuniversitaria.

## Convenciones

- **RF**: requerimiento funcional
- **Prioridad**: Alta (indispensable para la primera versión), Media, Baja
- **Estado**: Implementado, En desarrollo, Pendiente

## Módulo de registro de personas

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-01 | Registrar una persona con tipo de documento, número, nombres, apellidos y fecha de nacimiento. | Alta | Implementado |
| RF-02 | Validar la longitud del documento según su tipo: 8 para DNI, 9 para CE, 11 para RUC y 12 para pasaporte. | Alta | Implementado |
| RF-03 | Rechazar el registro de un documento que ya existe. | Alta | Implementado |
| RF-04 | Aceptar letras solo en el pasaporte y exigir dígitos en los demás tipos. | Media | Implementado |
| RF-05 | Calcular la edad a partir de la fecha de nacimiento. | Media | Implementado |

## Módulo de consulta

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-06 | Buscar una persona por su número de documento. | Alta | Implementado |
| RF-07 | Informar con un mensaje claro cuando el documento buscado no existe. | Alta | Implementado |
| RF-08 | Listar todas las personas registradas. | Alta | Implementado |
| RF-09 | Filtrar el listado por tipo de documento. | Media | Implementado |
| RF-10 | Filtrar el listado por rango de edad. | Media | Implementado |
| RF-11 | Ordenar el listado por apellido paterno. | Media | Implementado |
| RF-12 | Mostrar un resumen con la cantidad de personas por tipo de documento. | Baja | Implementado |

## Módulo de mantenimiento

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-13 | Eliminar una persona por su número de documento. | Alta | Implementado |
| RF-14 | Confirmar la cantidad de registros restantes tras una eliminación. | Baja | Implementado |

## Manejo de errores

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-15 | Informar el motivo exacto del rechazo de un documento inválido, indicando la longitud esperada. | Alta | Implementado |
| RF-16 | No interrumpirse ante una entrada no numérica en el menú. | Alta | Implementado |
| RF-17 | No interrumpirse ante una fecha con formato incorrecto. | Alta | Implementado |
| RF-18 | No interrumpirse al solicitar un registro en una posición inexistente. | Media | Implementado |

## Requerimientos de las siguientes entregas

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-19 | Registrar la matrícula de un estudiante en un ciclo y aula determinados. | Alta | Pendiente |
| RF-20 | Registrar la asistencia diaria de cada estudiante por aula. | Alta | Pendiente |
| RF-21 | Guardar la información en archivos para que persista entre ejecuciones. | Alta | Pendiente |
| RF-22 | Ofrecer una interfaz gráfica para el personal administrativo. | Media | Pendiente |
| RF-23 | Generar un reporte de asistencia por estudiante y por periodo. | Media | Pendiente |
| RF-24 | Almacenar la información en una base de datos. | Media | Pendiente |
