# Guía de Git y GitHub — Semanas 7 a 11

**Proyecto:** Academia Preuniversitaria San Fernando — sistema de matrícula y control de asistencia
**Repositorio:** `https://github.com/Club-Delirio/Proyect-tecnicas`
**Curso:** Técnicas de Programación Orientada a Objetos (SIST1202A) — UPN Cajamarca, 2026-2
**Equipo:** Cachi Tantaleán Anderson Alexis · Chuquimango Verástegui Jhocep Jhanpier · Rodrigo Ortiz Sumil Arturi · Rojas Valdivia Jhann Frank

Esta guía sirve a la vez como instructivo del equipo y como la **guía documentada de Git y GitHub** que el curso pide como evidencia. Está dividida en ocho partes. Las partes 1 y 2 las hace cada integrante una sola vez; de la 3 en adelante son el trabajo del día a día.

---

# PARTE 1 — Conectar la máquina con GitHub

Esta parte resuelve el problema que tuvimos antes: la máquina no lograba conectarse al repositorio. Hay **dos causas distintas** y conviene descartarlas en orden.

## 1.1. Comprobar que Git está instalado

```bash
git --version
```

Debe responder algo como `git version 2.45.2`. Si dice `command not found`:

- **Windows:** descargar Git for Windows desde `https://git-scm.com/download/win` e instalarlo con todas las opciones por defecto. Al terminar, abrir **Git Bash**, no el CMD.
- **Linux (Ubuntu):** `sudo apt install git`
- **macOS:** `xcode-select --install`

📸 **Capturar** la salida de `git --version`.

## 1.2. Causa número uno: no tienen permiso de escritura en el repositorio

Si alguien puede clonar pero al hacer `git push` recibe:

```
remote: Permission to Club-Delirio/Proyect-tecnicas.git denied to <usuario>.
fatal: unable to access '...': The requested URL returned error: 403
```

…el problema **no es su computadora**: es que su cuenta no figura como colaboradora del repositorio. Eso lo arregla el dueño de la organización, no el integrante.

**Quien administra `Club-Delirio` hace esto una sola vez:**

1. Entrar al repositorio → **Settings** → **Collaborators and teams**.
2. **Add people** → escribir el usuario de GitHub → **Add**.
3. En la columna *Role*, elegir **Write**. Con *Read* se puede ver el repositorio pero no subir nada.
4. Si el repositorio pertenece a una organización: **Organization settings** → **Member privileges** → *Base permissions* debe estar en **Write**, no en **Read**.
5. Cada invitado recibe un correo de invitación y **tiene que aceptarlo**. Hasta que no lo acepte, sigue sin poder subir.

| Usuario de GitHub | Integrante | Permiso necesario |
|---|---|---|
| `JhocepGab` | Chuquimango Verástegui Jhocep Jhanpier | Write |
| `Anderson-Cachi` | Cachi Tantaleán Anderson Alexis | Write |
| `Jhann-999` | Rojas Valdivia Jhann Frank | Write |
| `init57` | Rodrigo Ortiz Sumil Arturi | Write |

📸 **Capturar** la pantalla de *Collaborators* con los cuatro en **Write**.

## 1.3. Causa número dos: GitHub ya no acepta la contraseña

Desde agosto de 2021, GitHub **dejó de aceptar la contraseña de la cuenta** para operaciones de Git por HTTPS. Si alguien escribe su contraseña en el `push`, recibe:

```
remote: Support for password authentication was removed on August 13, 2021.
fatal: Authentication failed for 'https://github.com/...'
```

Hay tres formas de autenticarse. **Elijan una sola.** La A es la más simple en Windows; la B es la que funciona en cualquier sistema; la C es la más cómoda a largo plazo.

---

### Opción A — Git Credential Manager (recomendada en Windows)

Viene incluido con Git for Windows. Abre el navegador y uno inicia sesión como siempre.

```bash
# Comprobar que el gestor de credenciales esta activo
git config --global credential.helper

# Si no devuelve nada, activarlo
git config --global credential.helper manager

# En Linux, si no existe el manager:
git config --global credential.helper store
```

La primera vez que hagan `git push`, se abrirá una ventana del navegador pidiendo iniciar sesión en GitHub. Se autoriza una vez y queda guardado.

---

### Opción B — Token personal de acceso (PAT)

Un token es una contraseña larga que se usa **solo para Git** y que se puede revocar sin cambiar la contraseña de la cuenta.

**Generarlo:**

1. En GitHub: foto de perfil → **Settings**.
2. Abajo del todo: **Developer settings**.
3. **Personal access tokens** → **Tokens (classic)** → **Generate new token (classic)**.
4. *Note:* `Proyect-tecnicas UPN`
5. *Expiration:* 90 días.
6. Marcar la casilla **`repo`** (eso habilita todas las de abajo).
7. **Generate token**.
8. **Copiar el token ahora.** GitHub no lo vuelve a mostrar nunca. Empieza con `ghp_`.

**Probarlo antes de hacer `push`.** Este comando no sube nada y pide las credenciales igual, de modo que sirve para comprobar el token sin arriesgar nada:

```bash
git ls-remote https://github.com/Club-Delirio/Proyect-tecnicas.git
# Username for 'https://github.com': <su usuario de GitHub, NO el correo>
# Password for 'https://...':        <pegar el token, NO la contraseña>
```

Si imprime una lista de hashes con `refs/heads/main` y `refs/heads/develop`, el token funciona.

En Git Bash el token **no se ve** mientras se pega: el cursor no se mueve. Es normal, se pega y se pulsa Enter. Para pegar en Git Bash se usa **clic derecho → Paste** o **Shift + Insert**; `Ctrl + V` no funciona en esa ventana.

Para no tener que pegarlo cada vez:

```bash
git config --global credential.helper store
```

Luego, al subir una rama, el nombre va **tal cual se creó**. Si la rama es `feat/clase-persona`, el comando es:

```bash
git push -u origin feat/clase-persona
```

Para no equivocarse, este comando sube la rama en la que uno está parado, sea cual sea:

```bash
git push -u origin $(git branch --show-current)
```

> ⚠️ El token da permiso para escribir en el repositorio. **No se comparte por WhatsApp, no se pega en el chat ni se sube a ningún archivo.** Cada integrante genera el suyo.

---

### Si el comando devuelve error: qué dice y qué hacer

Lo primero es leer **la línea exacta** del error. Cada uno tiene una causa distinta y una solución distinta.

| Lo que dice el error | Qué pasó | Qué hacer |
|---|---|---|
| `src refspec mi-rama does not match any` | Se copió `mi-rama` literalmente. Es un marcador de posición, no un nombre de rama | `git branch --show-current` para ver el nombre real y usar ese. O `git push -u origin $(git branch --show-current)` |
| `fatal: not a git repository` | La terminal no está dentro de la carpeta del repositorio | `cd ~/Documents/Proyect-tecnicas` y volver a intentar |
| `Support for password authentication was removed` | Se escribió la contraseña de la cuenta en vez del token | Pegar el token en el campo *Password* |
| `Authentication failed` **y no pidió usuario ni contraseña** | Hay una credencial vieja guardada y Git la está reusando sin preguntar | Borrarla: ver el bloque de abajo |
| `Invalid username or password` | El usuario está mal escrito, o se puso el correo en vez del usuario de GitHub | En *Username* va el usuario de GitHub (`JhocepGab`, `Anderson-Cachi`, `Jhann-999`, `init57`), nunca el correo |
| `403` o `Permission to Club-Delirio/... denied` | El token sirve, pero esa cuenta no es colaboradora del repositorio | No es problema del token: sección 1.2. El dueño debe invitarla con permiso *Write* |
| `repository not found` | El token classic no tiene marcada la casilla `repo`, o es un token *fine-grained* sin acceso a ese repositorio | Generar uno nuevo **classic** con `repo` marcado |
| `Bad credentials` / `401` | El token expiró o se copió incompleto | Generar uno nuevo. Al copiarlo, no deben quedar espacios ni saltos de línea al inicio o al final |
| `could not read Username ... terminal prompts disabled` | Git no puede preguntar en esa terminal | `git config --global credential.helper manager` y repetir. En NetBeans, hacerlo desde Git Bash en vez de la ventana del IDE |

**Borrar una credencial vieja guardada.** Es la causa más común cuando el error aparece *sin* que Git pregunte nada. Según el gestor que esté activo:

```bash
# Ver cual esta activo
git config --global credential.helper

# Para cualquier gestor: rechazar la credencial guardada de github.com
printf "protocol=https\nhost=github.com\n\n" | git credential reject

# Si el gestor es "store", el archivo es este
cat ~/.git-credentials
rm ~/.git-credentials
```

En Windows también se puede a mano: **Panel de control → Administrador de credenciales → Credenciales de Windows** → buscar `git:https://github.com` → **Quitar**.

Después de borrarla, el siguiente comando vuelve a pedir usuario y token.

**Comprobación de que el token quedó bien guardado:**

```bash
git ls-remote https://github.com/Club-Delirio/Proyect-tecnicas.git
```

Si imprime los hashes **sin pedir nada**, la credencial quedó guardada y ya no hará falta pegar el token otra vez.

---

### Opción C — Llave SSH

```bash
# 1. Generar la llave (una sola vez por maquina)
ssh-keygen -t ed25519 -C "snkje123@gmail.com"
# Pulsar Enter tres veces para aceptar la ruta por defecto y sin contrasena

# 2. Mostrar la llave publica
cat ~/.ssh/id_ed25519.pub
```

Copiar todo lo que imprime (empieza con `ssh-ed25519`) y en GitHub: **Settings → SSH and GPG keys → New SSH key** → pegarlo → **Add SSH key**.

```bash
# 3. Probar la conexion
ssh -T git@github.com
# Debe responder: Hi <usuario>! You've successfully authenticated...

# 4. Cambiar el remoto de HTTPS a SSH
cd ~/Documents/Proyect-tecnicas
git remote set-url origin git@github.com:Club-Delirio/Proyect-tecnicas.git
git remote -v
```

📸 **Capturar** la salida de `ssh -T git@github.com`.

## 1.4. Configurar la identidad

El correo **debe ser el mismo de la cuenta de GitHub**. Si no coincide, los commits no se le atribuyen y en *Insights → Contributors* aparece como si no hubiera trabajado.

```bash
git config --global user.name "Chuquimango Verastegui Jhocep Jhanpier"
git config --global user.email "snkje123@gmail.com"
git config --global init.defaultBranch main
git config --global core.autocrlf true        # solo en Windows
git config --global pull.rebase false

# Comprobar
git config --list | grep -E "user|credential"
```

Cada uno con lo suyo:

| Integrante | `user.name` | `user.email` |
|---|---|---|
| Chuquimango Verástegui Jhocep Jhanpier | `Chuquimango Verastegui Jhocep Jhanpier` | `snkje123@gmail.com` |
| Cachi Tantaleán Anderson Alexis | `Cachi Tantalean Anderson Alexis` | `n00461903@upn.pe` |
| Rojas Valdivia Jhann Frank | `Rojas Valdivia Jhann Frank` | `rojasjhann7@gmail.com` |
| Rodrigo Ortiz Sumil Arturi | `Rodrigo Ortiz Sumil Arturi` | `rubick042@gmail.com` |

📸 **Capturar** la salida de `git config --list | grep user`.

## 1.5. Comprobar que la conexión funciona

```bash
# Prueba sin descargar nada: lista las ramas del repositorio remoto
git ls-remote https://github.com/Club-Delirio/Proyect-tecnicas.git
```

Si imprime una lista de hashes con `refs/heads/main` y `refs/heads/develop`, **la conexión ya funciona**. Si pide credenciales o devuelve 403, volver a la sección 1.2 o 1.3.

📸 **Capturar** esta salida. Es la evidencia de que la máquina quedó conectada.

---

# PARTE 2 — Traer el repositorio

## 2.1. Clonar (primera vez)

```bash
cd ~/Documents
git clone https://github.com/Club-Delirio/Proyect-tecnicas.git
cd Proyect-tecnicas

git remote -v
git branch -a
git log --oneline -10
```

- `git remote -v` debe mostrar la URL dos veces (fetch y push).
- `git branch -a` debe listar `main` y `remotes/origin/develop`.
- `git log --oneline -10` muestra los últimos diez commits del equipo.

📸 **Capturar** las tres salidas.

## 2.2. Si ya lo tenían clonado

```bash
cd ~/Documents/Proyect-tecnicas
git fetch --all --prune
git checkout develop
git pull origin develop
git status
```

`git status` debe decir `nothing to commit, working tree clean`. Si muestra archivos modificados que no son suyos, guardarlos antes de seguir:

```bash
git stash
git pull origin develop
git stash pop
```

## 2.3. Dónde están los archivos que hay que subir

Primero **descomprimir los dos zips**: `AcademiaSanFernando_NetBeans.zip` y `ENTREGA_ACADEMIA.zip`. No basta con abrirlos: en Windows hay que hacer clic derecho → **Extraer todo**, porque si se entra al zip con doble clic la terminal no ve esos archivos.

Después, **cada uno define dos variables** cada vez que abre la terminal. En vez de escribir la ruta a mano, conviene que la terminal la busque sola:

```bash
BASE=$(find ~ -type d -path "*/src/academia" 2>/dev/null | head -1)
DOCS=$(dirname "$(find ~ -name "requerimientos-funcionales.md" 2>/dev/null | head -1)")

echo "BASE = $BASE"
echo "DOCS = $DOCS"
ls "$BASE"
ls "$DOCS"
```

Los dos `echo` deben imprimir una ruta, y los dos `ls` deben listar archivos: catorce `.java` en `BASE` y los documentos en `DOCS`.

> ⚠️ Las variables se pierden al cerrar la terminal. Si la cierran, vuelvan a escribir las cuatro líneas de arriba.

### Si sale «No such file or directory» o el `echo` sale vacío

Son tres causas, en orden de frecuencia:

**1. La carpeta no se llama `Downloads`.** En Windows en español es **`Descargas`**, y si la cuenta usa OneDrive puede estar en `~/OneDrive/Descargas` o `~/OneDrive/Documentos`. Por eso el `find` de arriba busca en toda la carpeta del usuario en vez de dar por hecho el nombre. Para ver cómo se llama la suya:

```bash
ls ~
```

**2. El zip no se extrajo, o se extrajo con una carpeta de más.** Al extraer, Windows suele crear una carpeta con el nombre del zip y meter dentro otra con el mismo nombre, de modo que la ruta real queda así:

```
~/Descargas/AcademiaSanFernando_NetBeans/AcademiaSanFernando/src/academia
```

El `find` lo resuelve solo, porque busca la carpeta `src/academia` esté donde esté.

**3. Los archivos están en otro disco.** Si los descargaron a `D:` o a un USB, el `find ~` no los encuentra porque solo mira la carpeta del usuario. En Git Bash los discos se escriben `/d/` y `/e/`:

```bash
BASE=$(find /d /e -type d -path "*/src/academia" 2>/dev/null | head -1)
echo "BASE = $BASE"
```

### Comprobación antes de seguir

```bash
ls "$BASE" | wc -l     # debe decir 14
ls "$DOCS"             # deben aparecer los documentos de la entrega
```

Si `ls "$BASE" | wc -l` no dice **14**, falta algún archivo del proyecto y no hay que seguir: volver a extraer el zip.

> Las comillas alrededor de `"$BASE"` y `"$DOCS"` no son decorativas. Si la ruta contiene espacios —y `OneDrive - Universidad Privada del Norte` los tiene— sin comillas el comando falla con *No such file or directory* aunque la ruta sea correcta. Lo mismo vale para los `cp` de la parte 4: si usan rutas con espacios, pongan `cp "$BASE/Persona.java" src/academia/`.

---

# PARTE 3 — El ciclo de trabajo

Este es el único procedimiento. Cambia solo el nombre de la rama, el archivo y el mensaje.

```bash
# 1. Partir siempre de develop actualizado
git checkout develop
git pull origin develop

# 2. Crear la rama de la tarea
git checkout -b feat/clase-persona

# 3. Crear la carpeta destino (la primera vez)
mkdir -p src/academia

# 4. Copiar el archivo
mkdir -p src/academia
cp "$BASE/Persona.java" src/academia/

# 5. COMPROBAR antes de confirmar
ls -la src/academia/Persona.java
git status
git diff --stat

# 6. Preparar y confirmar
git add src/academia/Persona.java
git commit -m "feat: agrega la clase abstracta Persona con sus validaciones"

# 7. Publicar la rama
git push -u origin feat/clase-persona

# 8. Comprobar que el commit quedó a su nombre
git log -1 --pretty="%h | %an <%ae> | %s"
```

**El paso 5 no es opcional.** Es el que evita el error que más tiempo nos costó: subir una rama vacía porque el archivo nunca se copió. Si `git status` no muestra el archivo, el `cp` falló.

Después del `push`, GitHub devuelve un enlace:

```
remote: Create a pull request for 'feat/clase-persona' on GitHub by visiting:
remote:      https://github.com/Club-Delirio/Proyect-tecnicas/pull/new/feat/clase-persona
```

## 3.1. Crear y fusionar el pull request

1. Abrir ese enlace.
2. **Cambiar la base.** GitHub pone `main` por defecto. Abrir el desplegable izquierdo y elegir **`develop`**. Debe quedar `base: develop ← compare: feat/clase-persona`.
3. **Create pull request**.
4. **Otro integrante** revisa y pulsa **Merge pull request → Confirm merge**.
5. **Delete branch**.

> 🔴 **Subir la rama no es terminar.** Un pull request abierto no cuenta como trabajo entregado.

Después de que su PR se fusione:

```bash
git checkout develop
git pull origin develop
git branch -d feat/clase-persona        # borrar la rama local ya fusionada
git remote prune origin                 # limpiar las referencias remotas borradas
```

📸 **Capturar**: el PR con base en `develop`, y la pantalla de *Merged* en morado.

---
# PARTE 4 — Comandos por integrante

Cada integrante tiene **siete tareas**. Entre una y otra hay que esperar a que el pull request anterior esté fusionado y volver a hacer `git checkout develop && git pull origin develop`.

> Las cuatro clases que terminan en `Controller` son las que guardan las colecciones. Cada una necesita que la clase cuya colección guarda ya esté fusionada: `PersonaController` necesita `Persona`, `Estudiante` y `Docente`; `CicloController` necesita `Ciclo`; `MatriculaController` necesita `Matricula`; `AsistenciaController` necesita `Asistencia`.

---

## 4.1. CHUQUIMANGO VERÁSTEGUI, JHOCEP JHANPIER — `JhocepGab`

**Clases a su cargo:** `Persona`, `Estudiante`, `PersonaController`, `ArchivoUtil`, `AcademiaSanFernando`
**Historias de usuario:** HU-05, HU-06, HU-07, HU-15

```bash
# ───────── J1 — Persona.java (la clase base; va primero) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/clase-persona
mkdir -p src/academia
cp "$BASE/Persona.java" src/academia/
ls -la src/academia/Persona.java
git status
git add src/academia/Persona.java
git commit -m "feat: agrega la clase abstracta Persona con sus validaciones de documento"
git push -u origin feat/clase-persona
git log -1 --pretty="%h | %an | %s"
```

```bash
# ───────── J2 — Estudiante.java (despues de que J1 Persona este fusionado) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/clase-estudiante
mkdir -p src/academia
cp "$BASE/Estudiante.java" src/academia/
ls -la src/academia/Estudiante.java
git status
git add src/academia/Estudiante.java
git commit -m "feat: agrega la clase Estudiante con su edad, apoderado e historial de matriculas"
git push -u origin feat/clase-estudiante
```

```bash
# ───────── J3 — PersonaController.java (necesita Persona, Estudiante y Docente) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/controlador-personas
mkdir -p src/academia
cp "$BASE/PersonaController.java" src/academia/
ls -la src/academia/PersonaController.java
git status
git add src/academia/PersonaController.java
git commit -m "feat: agrega el controlador de personas con busqueda, orden y sobrecarga de listado"
git push -u origin feat/controlador-personas
```

```bash
# ───────── J4 — ArchivoUtil.java (manejo de archivos, semana 9) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/manejo-archivos
mkdir -p src/academia
cp "$BASE/ArchivoUtil.java" src/academia/
ls -la src/academia/ArchivoUtil.java
git status
git add src/academia/ArchivoUtil.java
git commit -m "feat: agrega el manejo de archivos con BufferedReader y BufferedWriter"
git push -u origin feat/manejo-archivos
```

```bash
# ───────── J5 — AcademiaSanFernando.java  ⚠️ AL FINAL, con las otras trece ya fusionadas ─────────
git checkout develop && git pull origin develop
git checkout -b feat/programa-principal
mkdir -p src/academia
cp "$BASE/AcademiaSanFernando.java" src/academia/
ls -la src/academia/AcademiaSanFernando.java
git status
git add src/academia/AcademiaSanFernando.java
git commit -m "feat: agrega el programa principal con recuperacion y guardado en archivos"
git push -u origin feat/programa-principal

# Comprobar que el proyecto compila antes de pedir la fusion
javac -encoding UTF-8 -d build $(find src -name "*.java")
```

```bash
# ───────── J6 — Informe del proyecto ─────────
git checkout develop && git pull origin develop
git checkout -b docs/informe-proyecto
mkdir -p docs
cp "$DOCS/Informe_Academia_APA7.docx" docs/
ls -la docs/Informe_Academia_APA7.docx
git status
git add docs/Informe_Academia_APA7.docx
git commit -m "docs: agrega el informe del proyecto de las semanas 7 a 11 en formato APA"
git push -u origin docs/informe-proyecto
```

```bash
# ───────── J7 — README ─────────
git checkout develop && git pull origin develop
git checkout -b docs/readme-academia
cp "$DOCS/README_NUEVO.md" README.md
git diff --stat
git add README.md
git commit -m "docs: actualiza el README con la estructura del sistema de la academia"
git push -u origin docs/readme-academia
```

---

## 4.2. ROJAS VALDIVIA, JHANN FRANK — `Jhann-999`

**Clases a su cargo:** `Ciclo`, `Docente`, `CicloController`
**Historias de usuario:** HU-01, HU-02, HU-04, HU-13

```bash
# ───────── R1 — Ciclo.java (clase base; en paralelo con J1) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/clase-ciclo
mkdir -p src/academia
cp "$BASE/Ciclo.java" src/academia/
ls -la src/academia/Ciclo.java
git status
git add src/academia/Ciclo.java
git commit -m "feat: agrega la clase abstracta Ciclo con el control de vacantes y aforo"
git push -u origin feat/clase-ciclo
git log -1 --pretty="%h | %an | %s"
```

```bash
# ───────── R2 — Docente.java (despues de que J1 Persona este fusionado) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/clase-docente
mkdir -p src/academia
cp "$BASE/Docente.java" src/academia/
ls -la src/academia/Docente.java
git status
git add src/academia/Docente.java
git commit -m "feat: agrega la clase Docente con el calculo de su pago y su bonificacion"
git push -u origin feat/clase-docente
```

```bash
# ───────── R3 — CicloController.java (necesita Ciclo fusionado) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/controlador-ciclos
mkdir -p src/academia
cp "$BASE/CicloController.java" src/academia/
ls -la src/academia/CicloController.java
git status
git add src/academia/CicloController.java
git commit -m "feat: agrega el controlador de ciclos con el control de codigo duplicado"
git push -u origin feat/controlador-ciclos
```

```bash
# ───────── R4 — Restricciones del proyecto (semana 7) ─────────
git checkout develop && git pull origin develop
git checkout -b docs/restricciones-proyecto
mkdir -p docs
cp "$DOCS/restricciones-y-alternativas.md" docs/
ls -la docs/restricciones-y-alternativas.md
git status
git add docs/restricciones-y-alternativas.md
git commit -m "docs: documenta las 12 restricciones del proyecto y sus alternativas"
git push -u origin docs/restricciones-proyecto
```

```bash
# ───────── R5 — Objetivos y alcance (semana 7) ─────────
git checkout develop && git pull origin develop
git checkout -b docs/objetivos-alcance
mkdir -p docs
cp "$DOCS/objetivos-y-alcance.md" docs/
ls -la docs/objetivos-y-alcance.md
git status
git add docs/objetivos-y-alcance.md
git commit -m "docs: documenta los objetivos del proyecto y el alcance de la solucion"
git push -u origin docs/objetivos-alcance
```

```bash
# ───────── R6 — Guia de Git y GitHub ─────────
git checkout develop && git pull origin develop
git checkout -b docs/guia-git
mkdir -p docs
cp "$DOCS/GUIA_GIT_SEMANAS7-11.md" docs/
ls -la docs/GUIA_GIT_SEMANAS7-11.md
git status
git add docs/GUIA_GIT_SEMANAS7-11.md
git commit -m "docs: agrega la guia documentada de Git y GitHub del equipo"
git push -u origin docs/guia-git
```

```bash
# ───────── R7 — Checklist de entrega ─────────
git checkout develop && git pull origin develop
git checkout -b docs/checklist-entrega
mkdir -p docs
cp "$DOCS/CHECKLIST_ENTREGA.md" docs/
ls -la docs/CHECKLIST_ENTREGA.md
git status
git add docs/CHECKLIST_ENTREGA.md
git commit -m "docs: agrega el checklist de entrega de las semanas 7 a 11"
git push -u origin docs/checklist-entrega
```

---

## 4.3. CACHI TANTALEÁN, ANDERSON ALEXIS — `Anderson-Cachi`

**Clases a su cargo:** `CicloRegular`, `CursoIntensivo`, `Asistencia`, `AsistenciaController`
**Historias de usuario:** HU-03, HU-08, HU-12, HU-14

```bash
# ───────── A1 — CicloRegular.java (despues de que R1 Ciclo este fusionado) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/clase-ciclo-regular
mkdir -p src/academia
cp "$BASE/CicloRegular.java" src/academia/
ls -la src/academia/CicloRegular.java
git status
git add src/academia/CicloRegular.java
git commit -m "feat: agrega la clase CicloRegular con su categoria y su descuento virtual"
git push -u origin feat/clase-ciclo-regular
```

```bash
# ───────── A2 — CursoIntensivo.java ─────────
git checkout develop && git pull origin develop
git checkout -b feat/clase-curso-intensivo
mkdir -p src/academia
cp "$BASE/CursoIntensivo.java" src/academia/
ls -la src/academia/CursoIntensivo.java
git status
git add src/academia/CursoIntensivo.java
git commit -m "feat: agrega la clase CursoIntensivo con su categoria por horas diarias"
git push -u origin feat/clase-curso-intensivo
```

```bash
# ───────── A3 — Asistencia.java (necesita Estudiante y Ciclo fusionados) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/clase-asistencia
mkdir -p src/academia
cp "$BASE/Asistencia.java" src/academia/
ls -la src/academia/Asistencia.java
git status
git add src/academia/Asistencia.java
git commit -m "feat: agrega la clase asociacion Asistencia con sus cuatro estados"
git push -u origin feat/clase-asistencia
```

```bash
# ───────── A4 — AsistenciaController.java (necesita A3 fusionado) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/controlador-asistencia
mkdir -p src/academia
cp "$BASE/AsistenciaController.java" src/academia/
ls -la src/academia/AsistenciaController.java
git status
git add src/academia/AsistenciaController.java
git commit -m "feat: agrega el controlador de asistencia con el calculo del porcentaje"
git push -u origin feat/controlador-asistencia
```

```bash
# ───────── A5 — Diagrama de clases (cuatro archivos, un solo commit) ─────────
git checkout develop && git pull origin develop
git checkout -b docs/diagrama-clases
mkdir -p docs
cp "$DOCS/diagrama_clases.png" "$DOCS/diagrama_clases.svg" "$DOCS/clases.dot" "$DOCS/diagrama_clases.drawio" docs/
ls -la docs/diagrama_clases.png docs/diagrama_clases.svg docs/clases.dot docs/diagrama_clases.drawio
git status
git add docs/diagrama_clases.png docs/diagrama_clases.svg docs/clases.dot docs/diagrama_clases.drawio
git commit -m "docs: agrega el diagrama de clases UML del sistema y su codigo fuente"
git push -u origin docs/diagrama-clases
```

```bash
# ───────── A6 — Requerimientos funcionales (semanas 8 y 10) ─────────
git checkout develop && git pull origin develop
git checkout -b docs/requerimientos-funcionales
mkdir -p docs
cp "$DOCS/requerimientos-funcionales.md" docs/
ls -la docs/requerimientos-funcionales.md
git status
git add docs/requerimientos-funcionales.md
git commit -m "docs: documenta 45 requerimientos funcionales con sus criterios de aceptacion"
git push -u origin docs/requerimientos-funcionales
```

```bash
# ───────── A7 — Manejo de archivos en Java (semana 9) ─────────
git checkout develop && git pull origin develop
git checkout -b docs/manejo-archivos-java
mkdir -p docs
cp "$DOCS/manejo-archivos-java.md" docs/
ls -la docs/manejo-archivos-java.md
git status
git add docs/manejo-archivos-java.md
git commit -m "docs: agrega la investigacion y practica de manejo de archivos en Java"
git push -u origin docs/manejo-archivos-java
```

---

## 4.4. RODRIGO ORTIZ, SUMIL ARTURI — `init57`

**Clases a su cargo:** `Matricula`, `MatriculaController`
**Historias de usuario:** HU-09, HU-10, HU-11

```bash
# ───────── S1 — Matricula.java (necesita Estudiante, Docente y Ciclo fusionados) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/clase-matricula
mkdir -p src/academia
cp "$BASE/Matricula.java" src/academia/
ls -la src/academia/Matricula.java
git status
git add src/academia/Matricula.java
git commit -m "feat: agrega la clase asociacion Matricula con el calculo del precio final"
git push -u origin feat/clase-matricula
```

```bash
# ───────── S2 — MatriculaController.java (necesita S1 fusionado) ─────────
git checkout develop && git pull origin develop
git checkout -b feat/controlador-matriculas
mkdir -p src/academia
cp "$BASE/MatriculaController.java" src/academia/
ls -la src/academia/MatriculaController.java
git status
git add src/academia/MatriculaController.java
git commit -m "feat: agrega el controlador de matriculas con el control de aforo y duplicados"
git push -u origin feat/controlador-matriculas
```

```bash
# ───────── S3 — Historias de usuario (semana 8) ─────────
git checkout develop && git pull origin develop
git checkout -b docs/historias-usuario
mkdir -p docs
cp "$DOCS/historias-usuario.md" docs/
ls -la docs/historias-usuario.md
git status
git add docs/historias-usuario.md
git commit -m "docs: agrega las 15 historias de usuario con sus escenarios de aceptacion"
git push -u origin docs/historias-usuario
```

```bash
# ───────── S4 — Matriz de historias en Excel ─────────
git checkout develop && git pull origin develop
git checkout -b docs/matriz-historias
mkdir -p docs
cp "$DOCS/Historias_Usuario_Academia.xlsx" docs/
ls -la docs/Historias_Usuario_Academia.xlsx
git status
git add docs/Historias_Usuario_Academia.xlsx
git commit -m "docs: agrega la matriz de historias de usuario en la plantilla del curso"
git push -u origin docs/matriz-historias
```

```bash
# ───────── S5 — Reparto de tareas ─────────
git checkout develop && git pull origin develop
git checkout -b docs/reparto-tareas
mkdir -p docs
cp "$DOCS/reparto-de-tareas.md" docs/
ls -la docs/reparto-de-tareas.md
git status
git add docs/reparto-de-tareas.md
git commit -m "docs: documenta el reparto de tareas de las semanas 7 a 11"
git push -u origin docs/reparto-tareas
```

```bash
# ───────── S6 — .gitignore ─────────
git checkout develop && git pull origin develop
git checkout -b chore/gitignore
cat > .gitignore <<'FIN'
build/
dist/
nbproject/private/
datos/
*.class
FIN
cat .gitignore
git add .gitignore
git commit -m "chore: excluye del control de versiones los compilados y la carpeta datos"
git push -u origin chore/gitignore
```

```bash
# ───────── S7 — Capturas de la ejecucion (al final, con el programa ya funcionando) ─────────
git checkout develop && git pull origin develop
git checkout -b docs/evidencias-ejecucion
mkdir -p evidencias
# copiar aqui las capturas de la corrida (run-01 a run-09)
ls -la evidencias/
git status
git add evidencias/
git commit -m "docs: agrega las capturas de la ejecucion del sistema en NetBeans"
git push -u origin docs/evidencias-ejecucion
```

---

## 4.5. Resumen del orden de fusión

| Paso | Qué se fusiona | Quién | Depende de |
|---|---|---|---|
| 1 | `Persona.java` (J1) y `Ciclo.java` (R1) | Jhocep y Jhann, en paralelo | — |
| 2 | `Estudiante.java` (J2), `Docente.java` (R2) | Jhocep y Jhann | paso 1 |
| 3 | `CicloRegular.java` (A1), `CursoIntensivo.java` (A2) | Anderson | `Ciclo` |
| 4 | `Matricula.java` (S1), `Asistencia.java` (A3) | Sumil y Anderson | pasos 2 y 3 |
| 5 | Los cuatro controladores: `PersonaController` (J3), `CicloController` (R3), `MatriculaController` (S2), `AsistenciaController` (A4) | los cuatro, en paralelo | la clase cuya colección guarda cada uno |
| 6 | `ArchivoUtil.java` (J4) | Jhocep | — (puede ir antes) |
| 7 | `AcademiaSanFernando.java` (J5) | Jhocep | todas las anteriores |

Los commits de documentos (J6, J7, R4 a R7, A5 a A7, S3 a S6) no dependen del código y se pueden fusionar en cualquier momento. El commit S7 va al final, cuando el programa ya corra.

---

# PARTE 5 — Sesión de conflicto provocado

El curso valora que el equipo sepa resolver un conflicto, no solo que no los tenga. Esta sesión lo provoca a propósito, se resuelve y queda la evidencia. **Participan los cuatro.**

## 5.1. Preparar el escenario

**Anderson** crea el archivo donde se producirá el conflicto:

```bash
git checkout develop && git pull origin develop
git checkout -b docs/bitacora-equipo
mkdir -p docs
cat > docs/BITACORA.md <<'FIN'
# Bitácora del equipo — Academia San Fernando

## Acuerdos

- Responsable de la integración: PENDIENTE
FIN
git add docs/BITACORA.md
git commit -m "docs: crea la bitacora del equipo"
git push -u origin docs/bitacora-equipo
```

Fusionar ese PR antes de seguir.

## 5.2. Provocar el conflicto

**Jhann y Sumil editan la misma línea, cada uno en su rama.** Los dos empiezan desde el mismo punto:

```bash
# JHANN
git checkout develop && git pull origin develop
git checkout -b docs/bitacora-jhann
sed -i 's/- Responsable de la integración: PENDIENTE/- Responsable de la integración: Rojas Valdivia Jhann Frank/' docs/BITACORA.md
cat docs/BITACORA.md
git add docs/BITACORA.md
git commit -m "docs: asigna el responsable de la integracion"
git push -u origin docs/bitacora-jhann
```

```bash
# SUMIL (sin hacer pull de lo de Jhann)
git checkout develop && git pull origin develop
git checkout -b docs/bitacora-sumil
sed -i 's/- Responsable de la integración: PENDIENTE/- Responsable de la integración: Rodrigo Ortiz Sumil Arturi/' docs/BITACORA.md
cat docs/BITACORA.md
git add docs/BITACORA.md
git commit -m "docs: asigna el responsable de la integracion"
git push -u origin docs/bitacora-sumil
```

Se fusiona **primero el de Jhann**. Al abrir el de Sumil, GitHub muestra:

```
This branch has conflicts that must be resolved
```

📸 **Capturar esa pantalla.**

## 5.3. Resolver el conflicto desde la terminal

**Sumil** lo resuelve en su máquina:

```bash
git checkout docs/bitacora-sumil
git pull origin develop
```

Git responde:

```
CONFLICT (content): Merge conflict in docs/BITACORA.md
Automatic merge failed; fix conflicts and then commit the result.
```

```bash
git status
cat docs/BITACORA.md
```

El archivo muestra los marcadores:

```
<<<<<<< HEAD
- Responsable de la integración: Rodrigo Ortiz Sumil Arturi
=======
- Responsable de la integración: Rojas Valdivia Jhann Frank
>>>>>>> develop
```

📸 **Capturar el archivo con los marcadores.**

Se edita el archivo dejando la versión acordada y **borrando las tres líneas de marcadores** (`<<<<<<<`, `=======`, `>>>>>>>`):

```bash
nano docs/BITACORA.md      # o abrirlo en NetBeans
cat docs/BITACORA.md       # comprobar que ya no quedan marcadores
grep -n "<<<<<<<\|=======\|>>>>>>>" docs/BITACORA.md   # no debe devolver nada

git add docs/BITACORA.md
git commit -m "fix: resuelve el conflicto de la bitacora dejando la version acordada"
git push origin docs/bitacora-sumil
```

En GitHub el PR pasa de *conflicts* a **Able to merge**. 📸 **Capturar.** Luego **Anderson** lo fusiona.

---

# PARTE 6 — Comandos de inspección que todos ejecutan

Estos no modifican nada. Cada integrante los corre en su máquina y captura la salida. Son los que demuestran que saben leer el historial.

```bash
cd ~/Documents/Proyect-tecnicas
git checkout develop && git pull origin develop

# 1. Historial en forma de grafico, con ramas y fusiones
git log --oneline --graph --all --decorate | head -40

# 2. Commits de cada autor
git log --pretty="%an" | sort | uniq -c | sort -rn

# 3. Mis propios commits
git log --author="$(git config user.name)" --oneline

# 4. Qué cambió en el último commit
git show --stat HEAD

# 5. Diferencias entre main y develop
git diff main..develop --stat

# 6. Quién escribió cada línea de una clase
git blame src/academia/Persona.java | head -20

# 7. Todas las ramas, locales y remotas
git branch -a

# 8. Buscar un commit por su mensaje
git log --grep="manejo de archivos" --oneline

# 9. Ver el contenido de un archivo tal como estaba en un commit
git show HEAD:src/academia/Persona.java | head -20

# 10. Resumen de archivos del proyecto
git ls-files src/academia/
```

📸 **Capturar las diez salidas.** Cada integrante las suyas: la número 3 da un resultado distinto para cada uno y es la que prueba su participación individual.

---

# PARTE 7 — Cierre de la entrega

Cuando los catorce pull requests de código estén fusionados en `develop`:

## 7.1. Comprobar que compila

```bash
git checkout develop && git pull origin develop
find src/academia -name "*.java" | sort        # deben ser catorce
javac -encoding UTF-8 -d build $(find src -name "*.java")
java -cp build academia.AcademiaSanFernando
```

📸 **Capturar** el `find`, el `javac` sin errores y el programa corriendo.

## 7.2. Pasar todo a `main`

**Uno solo** del equipo:

```bash
git checkout main
git pull origin main
git merge develop
git push origin main
git log --oneline --graph -15
```

Esto importa: **Insights → Contributors solo cuenta la rama por defecto**, que es `main`. Si el trabajo se queda en `develop`, en las estadísticas parece que nadie hizo nada.

## 7.3. Etiquetar la versión

```bash
git tag -a v2.0.0 -m "Entrega de las semanas 7 a 11: sistema de la Academia Preuniversitaria San Fernando"
git push origin v2.0.0
git tag -l
git show v2.0.0 --stat | head -20
```

## 7.4. Recalcular la participación

```bash
git log origin/main --pretty="%an" | sort | uniq -c | sort -rn
```

Con ese resultado se corrigen los porcentajes de la carátula del informe, que ahora están en 25 % cada uno.

## 7.5. Limpiar las ramas ya fusionadas

```bash
git branch --merged develop                    # ver cuáles ya se fusionaron
git branch -d feat/clase-persona               # borrar una rama local
git push origin --delete feat/clase-persona    # borrar la rama remota
git remote prune origin
git branch -a
```

---

# PARTE 8 — Problemas frecuentes y su solución

| Problema | Causa | Solución |
|---|---|---|
| `Support for password authentication was removed` | GitHub ya no acepta la contraseña | Usar token (PAT) o SSH: sección 1.3 |
| `src refspec ... does not match any` | Se copió un nombre de rama de ejemplo en vez del real | `git push -u origin $(git branch --show-current)` |
| `Authentication failed` sin que pregunte usuario ni contraseña | Hay una credencial vieja guardada | `printf "protocol=https\\nhost=github.com\\n\\n" \| git credential reject` y repetir |
| `Invalid username or password` con el token correcto | Se puso el correo en el campo *Username* | Ahí va el usuario de GitHub, no el correo |
| `repository not found` con un token recién creado | Es un token *fine-grained*, o el classic no tiene `repo` marcado | Generar uno **classic** con la casilla `repo` |
| `Permission to ... denied` / error 403 | La cuenta no es colaboradora con permiso *Write* | El dueño la agrega: sección 1.2 |
| `could not read Username` | No hay gestor de credenciales configurado | `git config --global credential.helper manager` |
| `fatal: not a git repository` | No está dentro de la carpeta del repositorio | `cd ~/Documents/Proyect-tecnicas` |
| `cp: cannot create regular file 'src/academia/': Not a directory` o `No such file or directory` | La carpeta `src/academia` no existe. Suele pasar después de un `git reset --hard`: al quedar vacía, Git la borra | `mkdir -p src/academia` antes del `cp`. Comprobar con `ls -ld src/academia`: debe empezar con `d` |
| `cp: No such file or directory` | La variable `BASE` o `DOCS` está vacía o mal, o la ruta tiene espacios y se escribió sin comillas | Rehacer las dos variables con el `find` de la sección 2.3 y comprobar con `echo "$BASE"`. Usar siempre `cp "$BASE/Archivo.java" src/academia/` |
| `ls: ~/Downloads: No such file or directory` | En Windows en español la carpeta se llama `Descargas`, o está dentro de OneDrive | `ls ~` para ver su nombre real, y usar el `find` de la sección 2.3 en vez de escribir la ruta |
| La rama se subió vacía | No se copió el archivo antes del commit | `git status` antes de cada `add`. Si ya pasó: copiar el archivo, `git add`, `git commit --amend --no-edit`, `git push -f` |
| Commité en `develop` en vez de en mi rama | Se olvidó el `git checkout -b` | `git log -1` para copiar el hash → `git checkout -b <nombre-de-la-rama>` → `git cherry-pick <hash>` → `git checkout develop` → `git reset --hard origin/develop` |
| `cannot lock ref ... File exists` | Quedó un archivo de bloqueo | `rm -f .git/refs/heads/<rama>.lock` |
| El PR salió con base `main` | GitHub lo pone por defecto | En el PR, lápiz ✏️ junto al título → cambiar base a `develop` |
| `This branch has conflicts` | Dos personas editaron lo mismo | Parte 5.3 |
| `Your local changes would be overwritten` | Hay cambios sin confirmar | `git stash` → `git pull` → `git stash pop` |
| `javac: command not found` | El JDK no está en el PATH | Windows con NetBeans: `export PATH="/c/Program Files/Apache NetBeans/jdk/bin:$PATH"` |
| Mis commits no aparecen en Insights | El `user.email` no coincide con el de GitHub | Corregirlo con `git config --global user.email` y rehacer los commits siguientes |
| Subí algo por error y quiero deshacerlo | — | `git revert <hash>` crea un commit que lo deshace, sin borrar el historial |
| Los acentos se ven mal en Windows | Salto de línea o codificación | `git config --global core.autocrlf true` y compilar con `-encoding UTF-8` |

---

# Glosario de todos los comandos usados en el proyecto

| Comando | Para qué se usó |
|---|---|
| `git --version` | Comprobar que Git está instalado |
| `git config --global user.name` / `user.email` | Identificar al autor de cada commit |
| `git config --global credential.helper` | Guardar las credenciales para no escribirlas cada vez |
| `git config --list` | Verificar la configuración activa |
| `ssh-keygen -t ed25519` | Generar la llave para conectarse por SSH |
| `ssh -T git@github.com` | Probar la conexión por SSH |
| `git ls-remote` | Comprobar la conexión al remoto sin descargar nada |
| `git clone` | Traer el repositorio a cada máquina |
| `git remote -v` | Ver a qué repositorio remoto apunta la copia local |
| `git remote set-url` | Cambiar el remoto de HTTPS a SSH |
| `git remote prune origin` | Limpiar referencias a ramas remotas ya borradas |
| `git fetch --all --prune` | Traer la información del remoto sin fusionar |
| `git checkout <rama>` | Cambiar de rama |
| `git checkout -b <rama>` | Crear una rama y cambiarse a ella |
| `git pull origin <rama>` | Traer y fusionar lo que hicieron los demás |
| `git status` | Ver qué cambió antes de confirmar |
| `git diff` / `git diff --stat` | Ver las diferencias línea por línea o resumidas |
| `git add <archivo>` | Preparar el archivo para el commit |
| `git commit -m "..."` | Confirmar el cambio con su mensaje |
| `git commit --amend --no-edit` | Corregir el último commit sin cambiar su mensaje |
| `git push -u origin <rama>` | Publicar la rama por primera vez |
| `git push -f` | Forzar la publicación tras un `amend` |
| `git push origin --delete <rama>` | Borrar una rama en el remoto |
| `git log --oneline --graph --all` | Ver el historial y la ramificación |
| `git log --author=` | Ver solo los commits de una persona |
| `git log --grep=` | Buscar un commit por su mensaje |
| `git show <hash>` | Ver el contenido de un commit |
| `git blame <archivo>` | Ver quién escribió cada línea |
| `git ls-files` | Listar los archivos versionados |
| `git branch -a` | Listar todas las ramas |
| `git branch --merged` | Ver qué ramas ya se fusionaron |
| `git branch -d <rama>` | Borrar una rama local ya fusionada |
| `git merge develop` | Integrar `develop` en `main` al cerrar |
| `git stash` / `git stash pop` | Guardar y recuperar cambios sin confirmar |
| `git cherry-pick <hash>` | Mover un commit hecho en la rama equivocada |
| `git reset --hard origin/develop` | Descartar cambios locales y volver al estado del remoto |
| `git revert <hash>` | Deshacer un commit creando otro que lo anula |
| `git tag -a v2.0.0 -m "..."` | Etiquetar la versión entregada |
