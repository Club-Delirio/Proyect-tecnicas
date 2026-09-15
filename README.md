# Técnicas de Programación Orientada a Objetos — SIST1202A

Repositorio compartido del equipo para las actividades de práctica de campo del curso
**Técnicas de Programación Orientada a Objetos** (Ingeniería de Sistemas Computacionales,
UPN, periodo 2026-2).

## Integrantes

| Etiqueta | Apellidos y nombres | Usuario de GitHub | Correo de `git config user.email` |
|---|---|---|---|
| Integrante 1 | | | |
| Integrante 2 |Rojas Valdivia |Jhann-999 | rojasjhann7@gmail.com |
| Integrante 3 | | | |
| Integrante 4 | | | |

## Contenido del repositorio

| Carpeta / archivo | Descripción |
|---|---|
| `src/semana5/` | Práctica de sobrecarga de métodos, manejo de errores y colecciones |
| `src/semana5/excepciones/` | Excepciones propias del proyecto |
| `docs/GUIA_GIT_GITHUB.md` | Guía documentada de Git y GitHub (semanas 1 a 4) |
| `docs/` | Informes y formatos de práctica de campo |
| `.gitignore` | Excluye del control de versiones los archivos compilados |

## Requisitos

- JDK 17 o superior
- Apache NetBeans 21 o superior

## Cómo abrir el proyecto en Apache NetBeans

1. `File > New Project > Java with Ant > Java Application`.
2. Nombre del proyecto: `Semana5`. Desmarcar *Create Main Class*.
3. Copiar la carpeta `semana5` (con `excepciones` dentro) a `Semana5/src/`.
4. Clic derecho sobre `Semana5.java` y elegir *Run File*.

## Cómo ejecutarlo desde la consola

```bash
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build semana5.Semana5          # menú interactivo
java -cp build semana5.Semana5 demo     # demostración automática
```

La opción `demo` recorre los tres temas de la práctica sin pedir datos por teclado. Es la
más cómoda para tomar las capturas de evidencia.

## Convenciones de trabajo

- **Ramas**: `feature/<descripción>`, `fix/<descripción>`, `docs/<descripción>`.
  Nunca se trabaja directamente sobre `main`.
- **Commits**: formato *Conventional Commits* — `tipo(alcance): descripción en imperativo`.
  Ejemplos: `feat(persona): agregar sobrecarga de calcularEdad`, `fix(controller): corregir búsqueda por documento`.
- **Integración**: todo cambio entra a `main` mediante Pull Request con al menos una
  aprobación de otro integrante.
- **Archivos compilados**: los `.class` no se suben. Están excluidos en `.gitignore`.

## Estado de las actividades

| Semana | Actividad | Estado |
|---|---|---|
| 1 | Repositorio local, clonación, primer commit | |
| 2 | Branches, merges y resolución de conflictos | |
| 3 | Colaboración en GitHub y Pull Requests | |
| 4 | Convenciones, Git Flow y comandos avanzados | |
| 5 | Sobrecarga, manejo de errores y colecciones | |
| 6 | Formulación del problema y antecedentes (APA) | |

Entrega de evidencias: **semana 6**.
