# Manejo de Archivos en Java - Academia

## Investigación y Práctica (Semana 9)
En este módulo del proyecto se implementa la persistencia de datos utilizando flujos de entrada y salida (`java.io` y `java.nio`) para almacenar registros de asistencias y catálogos de ciclos en archivos de texto plano o formato JSON.

### Características implementadas:
1. **Escritura de datos:** Almacenamiento de nuevas asistencias en archivos externos para evitar pérdida de información al cerrar el sistema.
2. **Lectura de datos:** Carga inicial de los catálogos de ciclos y estudiantes al arrancar la aplicación.
3. **Manejo de excepciones:** Uso de bloques `try-catch` para controlar errores de archivos no encontrados o fallos de lectura/escritura.