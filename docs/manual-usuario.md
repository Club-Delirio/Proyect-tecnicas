# Manual de usuario

Sistema de gestión de matrícula y control de asistencia — paquete `semana5`.

---

## 1. Requisitos previos

- JDK 17 o superior
- Apache NetBeans 21 o superior, si se desea abrir el proyecto en el entorno

Para comprobar que Java está disponible, abrir una terminal y escribir:

```
java -version
```

Debe responder con el número de versión. Si dice que el comando no se reconoce, el JDK no está instalado o no está en el PATH del sistema.

---

## 2. Abrir el proyecto en NetBeans

1. Abrir Apache NetBeans
2. Menú **File** → **Open Project**
3. Seleccionar la carpeta del proyecto
4. Clic derecho sobre el proyecto → **Run**

El programa se ejecuta en la ventana Output del propio NetBeans.

---

## 3. Compilar y ejecutar desde la terminal

Desde la carpeta raíz del proyecto:

```
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build semana5.Semana5
```

El parámetro `-encoding UTF-8` es necesario para que las tildes y la letra ñ se muestren correctamente.

---

## 4. El menú principal

Al iniciar se muestra:

```
=========================================
  SEMANA 5 - REGISTRO DE PERSONAS
=========================================
 1. Registrar persona
 2. Listar todas las personas
 3. Listar por tipo de documento
 4. Listar por rango de edad
 5. Buscar por numero de documento
 6. Eliminar por numero de documento
 7. Ordenar por apellido paterno
 8. Contar por tipo de documento
 0. Salir
Elija una opcion:
```

Se elige escribiendo el número y pulsando Enter. Si se escribe una opción fuera del rango, el sistema avisa y vuelve a mostrar el menú sin cerrarse.

Después de cada operación aparece el mensaje `[Operacion finalizada]`, que confirma que el bloque `finally` se ejecutó.

---

## 5. Descripción de cada opción

### Opción 1 — Registrar persona

Solicita los datos uno por uno:

| Dato | Qué aceptar |
|---|---|
| Tipo de documento | `DNI` o `CE`, en mayúsculas |
| Número de documento | 8 dígitos para DNI, 10 para CE |
| Nombre | Texto libre |
| Apellido paterno | Texto libre |
| Apellido materno | Texto libre |
| Fecha de nacimiento | Formato `aaaa-mm-dd`, por ejemplo `2004-03-15` |

Si todo es correcto, el registro se guarda y se muestran los datos bajo el encabezado `REGISTRO NUEVO`.

**Mensajes de error posibles:**

| Situación | Mensaje |
|---|---|
| Tipo distinto de DNI o CE | `Error: tipo de documento invalido. Use DNI o CE` |
| Número con longitud incorrecta | `Error: Para DNI el numero debe tener 8 digitos.` |
| Número ingresado antes del tipo | `Primero debe de ingresar el tipo de documento` |
| Fecha posterior a hoy | `Error: la fecha de nacimiento no puede ser futura` |
| Fecha mal escrita | `Error: la fecha debe tener el formato aaaa-mm-dd` |
| Documento ya registrado | `Error: el documento 70123456 ya esta registrado` |

Tras cualquiera de estos mensajes el programa continúa y no pierde los registros anteriores. Si el documento o la fecha quedaron sin asignar, la persona no se registra y se informa con `No se registro la persona por datos invalidos`.

### Opción 2 — Listar todas las personas

Recorre la lista y muestra los datos de cada persona. Si no hay ninguna, responde `No hay personas registradas`.

### Opción 3 — Listar por tipo de documento

Pide un tipo (`DNI` o `CE`) y muestra solo los registros de ese tipo. Si no hay ninguno, lo informa.

### Opción 4 — Listar por rango de edad

Pide una edad mínima y una máxima, y muestra las personas cuya edad calculada está dentro del rango.

Si se escribe texto en lugar de un número, responde `Error: debe ingresar solo numeros` y vuelve al menú.

### Opción 5 — Buscar por número de documento

Pide el número. Si existe, muestra los datos bajo el encabezado `RESULTADO DE BUSQUEDA`, más el nombre completo con los apellidos primero y la edad calculada.

Si no existe, responde `No se encontro esa persona`.

### Opción 6 — Eliminar por número de documento

Pide el número y elimina el registro. Confirma con `Persona eliminada. Quedan 3 persona(s)`.

Si el documento no existe, responde `Error: no se encontro el documento 99999999` y no modifica nada.

### Opción 7 — Ordenar por apellido paterno

Reordena la lista alfabéticamente por apellido paterno y a continuación la muestra ya ordenada. El orden se conserva para las siguientes consultas.

### Opción 8 — Contar por tipo de documento

Muestra el total de personas registradas y cuántas hay de cada tipo:

```
Total de personas: 4
Con DNI: 3
Con CE: 1
```

### Opción 0 — Salir

Cierra el programa con el mensaje `Programa finalizado`.

---

## 6. Advertencia sobre los datos

Esta versión guarda la información **en memoria**. Al cerrar el programa, todos los registros se pierden.

La persistencia en archivos está prevista para la semana 9 del curso, y el almacenamiento en base de datos para la semana 14.

---

## 7. Solución de problemas frecuentes

| Problema | Causa | Solución |
|---|---|---|
| `javac: command not found` | El JDK no está en el PATH | Agregar la carpeta `bin` del JDK al PATH del sistema |
| `Error: no se ha encontrado la clase principal` | El proyecto no se compiló o faltan archivos | Volver a ejecutar el comando de compilación y revisar que no haya errores |
| Las tildes se ven como símbolos raros | Falta el parámetro de codificación | Compilar con `-encoding UTF-8` |
| El menú se repite sin hacer nada | Se ingresó una opción fuera del rango 0-8 | Elegir un número del menú |
| Dice que primero se ingrese el tipo de documento | Se intentó asignar el número antes que el tipo | Ingresar primero DNI o CE |
