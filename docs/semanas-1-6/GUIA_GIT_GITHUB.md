# Guía de primeros pasos con Git y GitHub

Evidencia de práctica de campo, semanas 1 a 4 — Técnicas de Programación Orientada a Objetos (SIST1202A).

---

## Semana 1 — Primeros pasos

### 1.1 Configurar la identidad (una sola vez por equipo)

```bash
git config --global user.name "Apellidos Nombres"
git config --global user.email "correo@upn.pe"
git config --list
```

El correo debe ser el mismo que está registrado en la cuenta de GitHub. Si no coincide, los
commits se suben pero no quedan vinculados al perfil y no aparecen en *Insights > Contributors*.

### 1.2 Crear un repositorio local

```bash
mkdir proyecto-poo && cd proyecto-poo
git init
git status
```

`git init` crea la carpeta oculta `.git`, donde Git almacena todo el historial.

### 1.3 Clonar un repositorio de GitHub

```bash
git clone https://github.com/<usuario>/<repositorio>.git
cd <repositorio>
ls -a
```

### 1.4 Primer commit

```bash
git add .                       # prepara todos los cambios
git add src/semana5/Persona.java # o un archivo específico
git commit -m "feat: agregar clase Persona con sobrecarga de constructores"
```

`git add` mueve los cambios al área de preparación (*staging*); `git commit` los registra en el
historial con autor, fecha y mensaje.

### 1.5 Ver el historial

```bash
git log
git log --oneline
git log --oneline --graph
```

---

## Semana 2 — Branches y merges

### 2.1 Crear y cambiar de rama

```bash
git branch feature/persona          # crear
git checkout feature/persona        # cambiar
git checkout -b feature/persona     # crear y cambiar en un solo paso
git branch -a                       # listar (el * marca la rama activa)
```

### 2.2 Fusionar ramas

```bash
git checkout main
git merge feature/persona
git branch -d feature/persona       # eliminar la rama ya fusionada
git log --oneline --graph --all
```

Si `main` no cambió desde que se creó la rama, la fusión es *fast-forward*. Si ambas ramas
tienen commits distintos, Git genera un commit de fusión.

### 2.3 Resolver conflictos

Cuando dos ramas modifican las mismas líneas del mismo archivo, Git detiene la fusión:

```
CONFLICT (content): Merge conflict in Persona.java
Automatic merge failed; fix conflicts and then commit the result.
```

Procedimiento:

```bash
git status                    # muestra los archivos en "Unmerged paths"
# abrir el archivo y eliminar las marcas <<<<<<< ======= >>>>>>>
git add Persona.java          # marcar el conflicto como resuelto
git commit                    # confirmar la fusión
```

Si se decide no continuar:

```bash
git merge --abort
```

---

## Semana 3 — Colaboración en GitHub

### 3.1 Invitar colaboradores

En el repositorio: `Settings > Collaborators > Add people`. Se busca al compañero por su
usuario de GitHub y se envía la invitación, que queda como *Awaiting response* hasta que la acepte.

### 3.2 Trabajar sobre el repositorio compartido

```bash
git clone https://github.com/<equipo>/<repositorio>.git
cd <repositorio>
git checkout -b feature/mi-aporte
# ... realizar cambios ...
git add .
git commit -m "feat(controller): agregar búsqueda por documento"
git push -u origin feature/mi-aporte
```

Si el envío es rechazado porque el remoto tiene commits más nuevos:

```bash
git pull --rebase
git push
```

### 3.3 Pull Request y revisión de código

1. En GitHub aparece el aviso **Compare & pull request**. Se completa título y descripción.
2. El PR muestra los commits y las diferencias en la pestaña *Files changed*.
3. Un compañero revisa: comenta las líneas y elige **Approve** o **Request changes**.
4. Corregidas las observaciones, se pulsa **Merge pull request**.

La conversación del PR queda como evidencia de la revisión de código.

---

## Semana 4 — Mejores prácticas y comandos avanzados

### 4.1 Convención de mensajes de commit

Formato `tipo(alcance): descripción`:

| Tipo | Uso |
|---|---|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de un error |
| `docs` | Documentación |
| `refactor` | Reorganización del código sin cambiar el comportamiento |
| `test` | Pruebas |

```bash
git commit -m "feat(persona): agregar sobrecarga de calcularEdad"
git commit -m "fix(controller): corregir índice fuera de rango"
```

### 4.2 Convención de nombres de ramas

```bash
git checkout -b feature/registro-personas
git checkout -b fix/validacion-documento
git branch -m prueba1 fix/validacion-documento   # renombrar
```

### 4.3 Git Flow

```bash
git checkout -b develop main                     # línea de desarrollo
git checkout -b feature/reportes develop         # nueva funcionalidad
git checkout develop && git merge --no-ff feature/reportes
git checkout -b release/1.0 develop              # preparar la versión
git checkout main && git merge --no-ff release/1.0
git tag -a v1.0.0 -m "Versión 1.0.0"
git push origin --tags
git checkout -b hotfix/error-login main          # corrección urgente
```

| Rama | Propósito |
|---|---|
| `main` | Versión estable, lista para entregar |
| `develop` | Integración del trabajo en curso |
| `feature/*` | Una funcionalidad, nace y muere en `develop` |
| `release/*` | Preparación de una entrega |
| `hotfix/*` | Corrección urgente sobre `main` |

### 4.4 Comandos avanzados

```bash
git stash push -m "avance formulario"   # guardar cambios sin confirmar
git stash list
git stash pop                            # recuperarlos

git revert a1b2c3d                       # deshacer un commit ya publicado
git cherry-pick b2c3d4e                  # traer un commit puntual de otra rama
```

`git revert` crea un commit nuevo que invierte los cambios: es seguro en ramas compartidas
porque no reescribe el historial.

---

## Verificar el aporte individual

```bash
git log --author="Apellidos Nombres" --oneline
git shortlog -s -n          # commits por integrante
git log --stat              # líneas añadidas y eliminadas
```

En GitHub: pestaña **Insights > Contributors**.
