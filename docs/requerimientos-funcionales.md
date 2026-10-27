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
| RF-02 | Aceptar únicamente los tipos de documento DNI y CE, rechazando cualquier otro valor. | Alta | Implementado |
| RF-03 | Validar la longitud del número de documento según su tipo: 8 dígitos para DNI y 10 para CE. | Alta | Implementado |
| RF-04 | Exigir que el tipo de documento se ingrese antes que el número. | Alta | Implementado |
| RF-05 | Rechazar el registro de un documento que ya existe en la colección. | Alta | Implementado |
| RF-06 | Rechazar fechas de nacimiento posteriores a la fecha actual. | Media | Implementado |
| RF-07 | Calcular la edad de la persona a partir de su fecha de nacimiento. | Media | Implementado |

## Módulo de consulta

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-08 | Buscar una persona por su número de documento. | Alta | Implementado |
| RF-09 | Informar con un mensaje claro cuando el documento buscado no existe. | Alta | Implementado |
| RF-10 | Listar todas las personas registradas. | Alta | Implementado |
| RF-11 | Filtrar el listado por tipo de documento. | Media | Implementado |
| RF-12 | Filtrar el listado por rango de edad. | Media | Implementado |
| RF-13 | Ordenar el listado por apellido paterno. | Media | Implementado |
| RF-14 | Mostrar el total de personas registradas y la cantidad por cada tipo de documento. | Baja | Implementado |
| RF-15 | Mostrar el nombre completo en formato de nómina, con los apellidos antes del nombre. | Baja | Implementado |

## Módulo de mantenimiento

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-16 | Eliminar una persona por su número de documento. | Alta | Implementado |
| RF-17 | Confirmar la cantidad de registros restantes tras una eliminación. | Baja | Implementado |

## Manejo de errores

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-18 | Informar el motivo exacto del rechazo cuando un documento es inválido, indicando la longitud esperada. | Alta | Implementado |
| RF-19 | No interrumpirse ante una entrada no numérica donde se espera un número. | Alta | Implementado |
| RF-20 | No interrumpirse ante una fecha con formato distinto de aaaa-mm-dd. | Alta | Implementado |
| RF-21 | No interrumpirse al solicitar un registro en una posición inexistente de la lista. | Media | Implementado |
| RF-22 | Confirmar el cierre de cada operación del menú, se haya producido un error o no. | Baja | Implementado |

## Requerimientos de las siguientes entregas

| Código | Requerimiento | Prioridad | Estado |
|---|---|---|---|
| RF-23 | Registrar la matrícula de un estudiante en un ciclo y aula determinados. | Alta | Pendiente |
| RF-24 | Registrar la asistencia diaria de cada estudiante por aula. | Alta | Pendiente |
| RF-25 | Guardar la información en archivos para que persista entre ejecuciones. | Alta | Pendiente |
| RF-26 | Ofrecer una interfaz gráfica para el personal administrativo. | Media | Pendiente |
| RF-27 | Generar un reporte de asistencia por estudiante y por periodo. | Media | Pendiente |
| RF-28 | Almacenar la información en una base de datos. | Media | Pendiente |
