# Manual de usuario

Sistema de gestión de matrícula y control de asistencia — módulo de la semana 5.

---

## 1. Requisitos previos

- JDK 17 o superior instalado
- El código fuente del repositorio descargado

Para comprobar que Java está disponible, abrir una terminal y escribir:

```
java -version
```

Debe responder con el número de versión. Si dice que el comando no se reconoce, el JDK no está instalado o no está en el PATH del sistema.

---

## 2. Compilar el programa

Desde la carpeta raíz del proyecto:

```
javac -encoding UTF-8 -d build $(find src -name "*.java")
```

El parámetro `-encoding UTF-8` es necesario para que las tildes y la letra ñ se muestren correctamente. Si la compilación termina sin mensajes, todo está correcto.

---

## 3. Ejecutar el programa

### Modo interactivo

```
java -cp build semana5.Semana5
```

Abre el menú principal y espera las indicaciones del usuario.

### Modo demostración

```
java -cp build semana5.Semana5 demo
```

Ejecuta un recorrido automático por los tres temas del módulo, sin pedir datos. Es el modo recomendado para mostrar el funcionamiento en clase o capturar evidencias.

---

## 4. El menú principal

Al iniciar en modo interactivo se muestra este menú:

```
=========================================
  SEMANA 5 - REGISTRO DE PERSONAS
=========================================
 1. Registrar persona
 2. Listar todas las personas
 3. Listar por tipo de documento
 4. Buscar por número de documento
 5. Eliminar por número de documento
 6. Ordenar por apellido paterno
 7. Resumen por tipo de documento
 8. Probar sobrecarga (Calculadora)
 0. Salir
```

Se elige escribiendo el número y pulsando Enter. Si se escribe una letra o un símbolo, el sistema avisa que debe ingresarse un número y vuelve a mostrar el menú, sin cerrarse.

---

## 5. Descripción de cada opción

### Opción 1 — Registrar persona

Solicita, uno por uno:

| Dato | Qué aceptar |
|---|---|
| Tipo de documento | DNI, CE, RUC o PASAPORTE |
| Número de documento | 8 dígitos para DNI, 9 para CE, 11 para RUC, 12 caracteres para pasaporte |
| Nombre | Texto libre |
| Apellido paterno | Texto libre |
| Apellido materno | Texto libre |
| Fecha de nacimiento | Formato dd/MM/yyyy, por ejemplo 15/03/2004 |

Si todo es correcto, el registro se guarda y se muestran los datos bajo el encabezado REGISTRO NUEVO.

**Mensajes de error posibles:**

| Situación | Mensaje |
|---|---|
| Documento con longitud incorrecta | "Para DNI el número debe tener 8 caracteres. Se recibieron 3." |
| Tipo de documento no reconocido | "Tipo de documento no reconocido: LICENCIA. Use DNI, CE, RUC o PASAPORTE." |
| Documento ya registrado | "El documento 70123456 ya está registrado." |
| Fecha mal escrita | "Fecha inválida. Use el formato dd/MM/yyyy, por ejemplo 15/03/2004." |

Tras cualquiera de estos mensajes el programa continúa; no se cierra ni pierde los registros previos.

### Opción 2 — Listar todas las personas

Muestra todos los registros numerados, con el total al inicio. Si no hay ninguno, responde "No hay registros para mostrar."

### Opción 3 — Listar por tipo de documento

Pide un tipo (DNI, CE, RUC o PASAPORTE) y lista solo los registros de ese tipo, con la cantidad encontrada. Si no hay ninguno de ese tipo, lo informa.

### Opción 4 — Buscar por número de documento

Pide el número y, si existe, muestra los datos completos bajo el encabezado RESULTADO DE BÚSQUEDA.

Si el documento no está registrado, responde "No se encontró ninguna persona con el documento 99999999." Si se pulsa Enter sin escribir nada, pide que se indique un número.

### Opción 5 — Eliminar por número de documento

Pide el número y elimina el registro. Confirma con "Registro eliminado. Quedan 3 persona(s)."

Si el documento no existe, lo informa y no modifica nada.

### Opción 6 — Ordenar por apellido paterno

Reordena la lista alfabéticamente por apellido paterno y, cuando dos personas comparten apellido, por nombre. Tras ordenar, muestra la lista ya ordenada.

El orden se conserva para las siguientes consultas.

### Opción 7 — Resumen por tipo de documento

Muestra cuántas personas hay por cada tipo de documento y el total de objetos Persona creados durante la sesión. Ejemplo:

```
Resumen por tipo de documento:
  DNI: 2
  CE: 1
  RUC: 1
Total de objetos Persona creados: 6
```

Si no hay registros, responde "No hay registros para resumir."

### Opción 8 — Probar sobrecarga (Calculadora)

Ejecuta las cinco versiones sobrecargadas del método `calcular()` y muestra sus resultados:

```
calcular(5, 3)            = 8
calcular(5, 3, 2)         = 10
calcular(5.5, 3.2)        = 8.7
calcular("Hola", "UPN")   = Hola UPN
calcular(1,2,3,4,5)       = 15
```

Luego pide un divisor para el número 10. Si se ingresa 0, responde "No es posible dividir entre cero." Si se ingresa texto, responde "Ingrese solo números." En ambos casos el programa continúa.

### Opción 0 — Salir

Cierra el programa con el mensaje "Programa finalizado."

---

## 6. Advertencia sobre los datos

Esta versión del sistema **guarda la información en memoria**. Al cerrar el programa, todos los registros se pierden.

La persistencia en archivos está prevista para la semana 9 del curso, y el almacenamiento en base de datos para la semana 14.

---

## 7. Solución de problemas frecuentes

| Problema | Causa | Solución |
|---|---|---|
| `javac: command not found` | El JDK no está en el PATH | Agregar la carpeta `bin` del JDK al PATH del sistema |
| `Error: no se ha encontrado la clase principal` | El proyecto no se compiló, o falta algún archivo | Volver a ejecutar el comando de compilación y revisar que no haya errores |
| Las tildes se ven como símbolos raros | Falta el parámetro de codificación | Compilar con `-encoding UTF-8` |
| El menú se repite sin hacer nada | Se ingresó una opción fuera del rango 0-8 | Elegir un número del menú |
