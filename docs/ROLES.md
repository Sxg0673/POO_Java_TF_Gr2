# Roles y forma de trabajo

Este documento explica **qué hace cada rol** y **cómo trabajar con Git y GitHub** en el proyecto. Léelo completo antes de empezar.

La lógica del aplicativo ya está implementada (paquetes `Modelo` y `Vista`). Lo que falta es la interfaz gráfica, las pruebas, las evidencias y la entrega final.

Documentos relacionados:
- [Contrato GUI](CONTRATO_GUI.md): componentes, nombres y código de los eventos de la interfaz.
- [README](../README.md): descripción general del proyecto.

---

## 1. Reglas generales
1. **Nunca** se trabaja directamente sobre `main` ni sobre `develop`. Cada tarea se hace en una rama propia (`feature/...`) y entra a `develop` mediante un **Pull Request** (PR). Todo PR se **revisa y aprueba en grupo** antes de fusionarse (ver sección 3.7).
2. **No se modifican** las clases del paquete `Modelo` ni los métodos de lógica de `UsaProyecto`. Si encuentras un error en ellos, avisa en el grupo en lugar de corregirlo.
3. El archivo `UsaProyecto.form` (diseño de la ventana) lo modifica **una sola persona a la vez**: primero el Rol 1 y, cuando su PR esté fusionado, el Rol 2.
4. Commits pequeños y con mensaje claro (ver sección 3.4).
5. Ante cualquier duda o conflicto con Git, **no forzar nada**: avisar en el grupo.

---

## 2. Preparación (una sola vez)
1. **Aceptar la invitación** al repositorio que llegó al correo de GitHub.
2. **Tener instalado**: JDK 21 o superior, Apache NetBeans y Git.
3. **Configurar Git con tu nombre** (para que los commits salgan a tu nombre):
   ```
   git config --global user.name "Tu Nombre Apellido"
   git config --global user.email "tu-correo-de-github@ejemplo.com"
   ```
4. **Clonar el repositorio** en la carpeta donde guardes tus proyectos:
   ```
   git clone https://github.com/Sxg0673/POO_Java_TF_Gr2.git
   ```
   Si al clonar o subir cambios Git pide usuario y contraseña, la "contraseña" es un **token** de GitHub (GitHub → *Settings → Developer settings → Personal access tokens*), no la contraseña de la cuenta.
5. **Abrir el proyecto en NetBeans**: *File → Open Project* → carpeta `POO_Java_TF_Gr2`.
6. **Probar que funciona**: *Run Project* (F6). Se abre la ventana del aplicativo (vacía hasta que se construya la GUI).

---

## 3. Cómo trabajar en tu rama

### 3.1 Antes de empezar cualquier tarea
Abre una terminal **dentro de la carpeta del proyecto** y trae lo último de `develop`:
```
git checkout develop
git pull
```

### 3.2 Crear tu rama
```
git checkout -b feature/nombre-de-la-tarea
```
El nombre de la rama de cada rol está indicado en la sección 5. NetBeans muestra la rama actual junto al nombre del proyecto, por ejemplo `POO_Java_TF_Gr2 [feature/gui-diseno]`.

### 3.3 Trabajar y guardar cambios (commit)
Trabaja normalmente en NetBeans. Cada vez que termines una parte con sentido propio:
```
git status                      # ver qué archivos cambiaron
git add .                       # preparar todos los cambios
git commit -m "feat: agrega panel de proyectos a la GUI"
```

### 3.4 Mensajes de commit
Formato: `prefijo: descripción corta en español`.

| Prefijo | Cuándo se usa | Ejemplo |
|---|---|---|
| `feat:` | algo nuevo en el aplicativo | `feat: agrega eventos de consultas por código` |
| `fix:` | corrección de un error | `fix: corrige nombre del campo de código` |
| `test:` | pruebas unitarias | `test: agrega pruebas de la clase Proyecto` |
| `docs:` | documentación y evidencias | `docs: agrega evidencias de depuración` |
| `style:` | cambios visuales sin cambiar el funcionamiento | `style: ajusta tamaño de los paneles` |
| `chore:` | configuración del proyecto | `chore: agrega librería JUnit al proyecto` |

### 3.5 Subir la rama a GitHub (push)
La **primera vez** que subes una rama:
```
git push -u origin feature/nombre-de-la-tarea
```
Las siguientes veces basta con:
```
git push
```

### 3.6 Abrir el Pull Request
1. En GitHub: pestaña **Pull requests → New pull request**.
2. **base: `develop`** ← **compare: tu rama**. Revisa que la base sea `develop` y **no** `main`.
3. Título con el mismo formato de los commits (ej. `feat: agrega diseño de la GUI`) y una descripción corta de lo que hiciste.
4. **Create pull request** y **envía el enlace del PR al grupo** para que lo revisen. **No** lo fusiones todavía: primero necesita la aprobación del grupo (sección 3.7).

### 3.7 Revisión y aprobación del grupo
1. Los demás integrantes abren el enlace, entran a **Files changed** y revisan que los cambios correspondan a la tarea (que no haya archivos ajenos, que compile, que respete el contrato).
2. Cada revisor responde en el PR: **Review changes → Approve** (o comenta lo que hay que corregir).
3. Si piden cambios, corrige en la **misma rama**, haz commit y push: el PR se actualiza solo. Vuelve a avisar en el grupo.
4. Cuando el grupo haya dado su aprobación, el autor lo fusiona con **Merge pull request** (merge commit, **no** *Squash*).
5. Un PR nunca se fusiona sin esa aprobación del grupo, ni siquiera el propio.

### 3.8 Después de que tu PR sea fusionado
```
git checkout develop
git pull
git branch -d feature/nombre-de-la-tarea
```
Para la siguiente tarea, vuelve al paso 3.1.

> También puedes hacer commit y push desde NetBeans (clic derecho al proyecto → **Git → Commit…** y **Git → Remote → Push…**). Lo importante es seguir el mismo orden: rama propia → commits → push → Pull Request.

---

## 4. Evidencias
Todas las evidencias se guardan **dentro del repositorio**, en la carpeta `evidencias/` (en la raíz del proyecto):
```
evidencias/
├── versionamiento/      capturas de GitHub (Rol 3)
├── pruebas_unitarias/   resultados de JUnit en NetBeans (Rol 3)
├── pruebas/             pruebas funcionales de la GUI (Rol 2)
└── depuracion/          capturas de depuración con breakpoints (Rol 2)
```
- Capturas en **PNG**, con nombre numerado y descriptivo: `01_prueba_proyecto_ok.png`, `02_breakpoint_generar_codigo.png`.
- Cada carpeta tiene un archivo `descripcion.md` con **una línea por captura**: nombre del archivo y qué muestra.
- Las evidencias se suben con commits `docs:` desde la rama del rol correspondiente.

---

## 5. Roles

### Rol 1 — Diseño de la GUI y entrega final
**Objetivo:** construir la ventana del aplicativo y preparar la entrega.

| Tarea | Rama | Cuándo |
|---|---|---|
| 1. Diseñar la ventana completa en `UsaProyecto.form` siguiendo las secciones 2 y 3 del [Contrato GUI](CONTRATO_GUI.md): 4 paneles, campos, combo, botones, área de resultados, botón Salir y título. Usar **exactamente** los nombres de variable del contrato. **Sin** programar eventos. | `feature/gui-diseno` | Desde ya (**prioridad**: el Rol 2 depende de esta tarea) |
| 2. Completar el README: sección **Integrantes** (los 4 nombres) y sección **Cómo usar el aplicativo** con capturas de la ventana funcionando. | `feature/readme-final` | Cuando el Rol 2 termine los eventos |
| 3. Armar el **ZIP de entrega** a partir de `main` (proyecto completo con la carpeta `evidencias/`, sin `build/` ni `dist/`) y verificar que se abre y ejecuta en NetBeans. | — | Al final, cuando todo esté en `develop` y el grupo apruebe pasar `develop` a `main` |

**Terminado cuando:** la ventana tiene todos los componentes del contrato con sus nombres, el README está completo y el ZIP abre y ejecuta.

### Rol 2 — Eventos de la GUI, pruebas funcionales y depuración
**Objetivo:** hacer que la ventana funcione y documentar su comportamiento.

| Tarea | Rama | Cuándo |
|---|---|---|
| 1. **Depuración, parte 1:** con *breakpoints* en el constructor de `UsaProyecto` (carga de datos de prueba), ejecutar *Debug Project* (Ctrl+F5) y capturar la ventana **Variables** mientras se crean los proyectos y se agregan integrantes. | `feature/evidencias-depuracion` | Desde ya |
| 2. **Eventos:** programar los 20 eventos según la sección 4 del [Contrato GUI](CONTRATO_GUI.md) (doble clic al botón en Design → pegar el código). | `feature/gui-eventos` | Cuando el PR del Rol 1 (diseño) esté fusionado |
| 3. **Pruebas funcionales:** recorrer la tabla de la sección 5 del contrato, botón por botón, y guardar una captura del resultado de cada uno en `evidencias/pruebas/`. | `feature/gui-eventos` | Después de los eventos |
| 4. **Depuración, parte 2:** con la GUI funcionando, breakpoints en `generarCodigoProyecto`, `determinarFechaFinalizacion` y `eliminarIntegrante`, avanzando paso a paso (F8) y capturando las variables. | `feature/evidencias-depuracion` | Después de los eventos |

**Cómo depurar en NetBeans:** clic sobre el número de línea para poner el breakpoint (punto rojo) → *Debug Project* (Ctrl+F5) → cuando el programa se detiene, abrir *Window → Debugging → Variables* → avanzar línea por línea con **F8** (Step Over) o entrar al método con **F7** (Step Into).

**Terminado cuando:** todos los botones funcionan como indica el contrato y están las capturas de pruebas funcionales y de depuración con su `descripcion.md`.

### Rol 3 — Pruebas unitarias y evidencias de versionamiento
**Objetivo:** demostrar con pruebas que la lógica funciona y documentar el trabajo en GitHub.

| Tarea | Rama | Cuándo |
|---|---|---|
| 1. **Pruebas del Modelo** con **JUnit 4**, en la carpeta `test/` (ver detalle abajo). | `feature/pruebas-unitarias` | Desde ya |
| 2. **Pruebas de `UsaProyecto`** (solo métodos que no abren ventanas, ver detalle abajo). | `feature/pruebas-unitarias` | Desde ya |
| 3. Capturas de NetBeans con **todas las pruebas pasando** (ventana *Test Results* en verde) en `evidencias/pruebas_unitarias/`. | `feature/pruebas-unitarias` | Al terminar 1 y 2 |
| 4. **Evidencias de versionamiento** en `evidencias/versionamiento/`: lista de commits, ramas, Pull Requests (abiertos y cerrados), gráfica de ramas (*Insights → Network*) y aportes por integrante (*Insights → Contributors*). | `feature/evidencias-versionamiento` | Al final, cuando estén fusionados los PR de todos |

**Crear las pruebas en NetBeans:** clic derecho sobre la clase (ej. `Proyecto.java`) → *Tools → Create/Update Tests* → elegir **JUnit 4** → se crea `test/Modelo/ProyectoTest.java`. Ejecutar todas: clic derecho al proyecto → **Test** (Alt+F6). Al crear la primera prueba, NetBeans agrega JUnit al proyecto: ese cambio también se sube.

**Qué probar como mínimo:**
- `Proyecto`: estado inicial `INICIADO` con ambos constructores; equipo vacío con el constructor sin equipo; `determinarFechaFinalizacion` con `semanas`, `meses` y `anios`; `agregarIntegrante`; `eliminarIntegrante` cuando el integrante existe (lo devuelve y el equipo disminuye) y cuando no existe (devuelve `null`).
- `Empresa` y `PersonaNatural`: que son `Cliente` (herencia), que los getters heredados funcionan y que el `toString` incluye los datos propios.
- `Empleado` y `Contacto`: constructor, getters y setters.
- `UsaProyecto` (crear el objeto con `new UsaProyecto()` y pasar listas propias): `generarCodigoProyecto` (lista vacía → `AAAA-001`, con el año actual), consultas con lista vacía ("No hay proyectos registrados."), consulta con código inexistente, y filtros por gerente, cliente y tipo de cliente.

**Terminado cuando:** todas las pruebas pasan en verde y están las capturas de pruebas y de versionamiento con su `descripcion.md`.

---

## 6. Orden de trabajo
```
Rol 1: [diseño GUI] ──PR──▶ ··········· [README final] ─────────────▶ [ZIP]
Rol 2: [depuración 1] ─────▶ [eventos + pruebas funcionales] ──PR──▶ [depuración 2]
Rol 3: [pruebas unitarias] ──PR──────────────────────────────────▶ [evidencias GitHub]
```
El diseño del Rol 1 es lo primero que debe entrar a `develop`, porque el Rol 2 lo necesita para programar los eventos.
