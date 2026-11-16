# Contrato GUI — `Vista/UsaProyecto`

Guía para construir la interfaz gráfica de `UsaProyecto`. **Toda la lógica ya está implementada**: cada botón solo debe leer el filtro (si lo tiene), llamar al método indicado y mostrar el resultado. No se debe modificar ningún método de lógica ni las clases del paquete `Modelo`.

## 1. Lo que ya existe en `UsaProyecto.java`
- `losProyectos` (`ArrayList<Proyecto>`) y `losClientes` (`ArrayList<Cliente>`): colecciones del aplicativo.
- Constructor: llama a `initComponents()` y carga **4 proyectos de prueba** (2025-001, 2026-001, 2026-002, 2026-003).
- 20 métodos de lógica (ver sección 4).
- Clase principal del proyecto: `Vista.UsaProyecto` (se ejecuta con **Run Project / F6**).

Los métodos que **solicitan datos** (registrar proyecto, integrante, equipo, actualizar estado, eliminar integrante) abren sus propias ventanas (`JOptionPane`) con todas las validaciones. La GUI **no** necesita campos para esos datos.

## 2. Reglas de trabajo en el formulario
1. El diseño se hace en el modo **Design** de NetBeans sobre `UsaProyecto.form`. **Nunca** editar a mano el código entre `GEN-BEGIN` y `GEN-END`.
2. **Solo una persona a la vez** modifica `UsaProyecto.form` (los conflictos de git en archivos `.form` son muy difíciles de resolver). Antes de empezar: `git pull`; al terminar: commit y push.
3. Usar exactamente los nombres de variable de la sección 3 (clic derecho al componente → **Change Variable Name**).
4. Componentes permitidos (vistos en clase): `JPanel`, `JLabel`, `JTextField`, `JTextArea` (dentro de `JScrollPane`), `JComboBox`, `JButton`.
5. Los eventos se crean con doble clic sobre el botón en Design; el código del evento se escribe dentro del método `...ActionPerformed` generado.

## 3. Componentes
Distribución sugerida en 4 paneles (`JPanel` con borde) + área de resultados + botón Salir. Propiedad `title` del JFrame: **Gestión de proyectos**.

### Panel 1 — Proyectos
| Variable | Tipo | Texto |
|---|---|---|
| `jButtonRegistrarProyecto` | JButton | Registrar proyecto |
| `jButtonConsultarTodos` | JButton | Consultar todos |
| `jButtonConsultarEnCurso` | JButton | Proyectos en curso |
| `jButtonConsultarPrimero` | JButton | Primer proyecto |
| `jButtonConsultarUltimo` | JButton | Último proyecto |

### Panel 2 — Operaciones por código de proyecto
| Variable | Tipo | Texto |
|---|---|---|
| `jTextFieldCodigo` | JTextField | (vacío) — con `JLabel` "Código del proyecto (ej. 2026-001)" |
| `jButtonConsultarProyecto` | JButton | Consultar proyecto |
| `jButtonConsultarGerente` | JButton | Consultar gerente |
| `jButtonConsultarEquipo` | JButton | Consultar equipo |
| `jButtonRegistrarIntegrante` | JButton | Registrar integrante |
| `jButtonRegistrarEquipo` | JButton | Registrar equipo |
| `jButtonEliminarIntegrante` | JButton | Eliminar integrante |
| `jButtonActualizarEstado` | JButton | Actualizar estado |

### Panel 3 — Consultas por gerente y cliente
| Variable | Tipo | Texto |
|---|---|---|
| `jTextFieldNumIdeGerente` | JTextField | con `JLabel` "Número de identificación del gerente" |
| `jButtonConsultarPorGerente` | JButton | Proyectos del gerente |
| `jTextFieldNumIdeCliente` | JTextField | con `JLabel` "Número de identificación del cliente" |
| `jButtonConsultarPorCliente` | JButton | Proyectos del cliente |
| `jComboBoxTipoCliente` | JComboBox | modelo (propiedad `model`): **`Empresa`** y **`PersonaNatural`**, escritos exactamente así |
| `jButtonConsultarPorTipoCliente` | JButton | Proyectos por tipo de cliente |

### Panel 4 — Archivos
| Variable | Tipo | Texto |
|---|---|---|
| `jButtonGenerarArchivoProyectos` | JButton | Generar archivo de proyectos |
| `jButtonLeerArchivoProyectos` | JButton | Leer archivo de proyectos |
| `jButtonGenerarArchivoClientes` | JButton | Generar archivo de clientes |
| `jButtonLeerArchivoClientes` | JButton | Leer archivo de clientes |

### Resultados y salida
| Variable | Tipo | Notas |
|---|---|---|
| `jTextAreaResultados` | JTextArea | dentro de un `JScrollPane`; desmarcar la propiedad `editable` (solo para mostrar) |
| `jButtonSalir` | JButton | Salir |

## 4. Código de cada evento
`losProyectos` y `losClientes` se usan directamente (son atributos de la clase).

### 4.1 Botones sin filtro
```java
    private void jButtonRegistrarProyectoActionPerformed(java.awt.event.ActionEvent evt) {
        losProyectos = registrarProyecto(losProyectos);
    }

    private void jButtonConsultarTodosActionPerformed(java.awt.event.ActionEvent evt) {
        jTextAreaResultados.setText(consultarTodosProyectos(losProyectos));
    }
```
Igual para:
| Botón | Método |
|---|---|
| `jButtonConsultarEnCurso` | `consultarProyectosEnCurso(losProyectos)` |
| `jButtonConsultarPrimero` | `consultarPrimerProyecto(losProyectos)` |
| `jButtonConsultarUltimo` | `consultarUltimoProyecto(losProyectos)` |

### 4.2 Botones con filtro de texto
Se valida que el campo no esté vacío (mismo mensaje que usan los ejemplos del curso):
```java
    private void jButtonConsultarProyectoActionPerformed(java.awt.event.ActionEvent evt) {
        String codigoProyecto = jTextFieldCodigo.getText();
        if(codigoProyecto.isEmpty() || codigoProyecto.isBlank()){
            JOptionPane.showMessageDialog(null, "Entrada inválida: dato está vacío.\n", "CONSULTAR PROYECTO: código", JOptionPane.ERROR_MESSAGE);
        } else{
            jTextAreaResultados.setText(consultarProyectoDadoCodigo(losProyectos, codigoProyecto));
        }
    }
```
Igual para (cambiando campo, método y título del mensaje):
| Botón | Campo | Método |
|---|---|---|
| `jButtonConsultarGerente` | `jTextFieldCodigo` | `consultarGerenteProyectoDadoCodigo(losProyectos, codigoProyecto)` |
| `jButtonConsultarEquipo` | `jTextFieldCodigo` | `consultarEquipoProyectoDadoCodigo(losProyectos, codigoProyecto)` |
| `jButtonRegistrarIntegrante` | `jTextFieldCodigo` | `registrarIntegranteProyectoDadoCodigo(losProyectos, codigoProyecto)` |
| `jButtonRegistrarEquipo` | `jTextFieldCodigo` | `registrarEquipoProyectoDadoCodigo(losProyectos, codigoProyecto)` |
| `jButtonEliminarIntegrante` | `jTextFieldCodigo` | `eliminarIntegranteProyectoDadoCodigo(losProyectos, codigoProyecto)` |
| `jButtonActualizarEstado` | `jTextFieldCodigo` | `actualizarEstadoProyectoDadoCodigo(losProyectos, codigoProyecto)` |
| `jButtonConsultarPorGerente` | `jTextFieldNumIdeGerente` | `consultarProyectosDadoNumIDEGerente(losProyectos, numIDEGerente)` |
| `jButtonConsultarPorCliente` | `jTextFieldNumIdeCliente` | `consultarProyectosDadoNumIDECliente(losProyectos, numIDECliente)` |

Todos devuelven un `String` que se muestra con `jTextAreaResultados.setText(...)`.

### 4.3 Botón con combo
```java
    private void jButtonConsultarPorTipoClienteActionPerformed(java.awt.event.ActionEvent evt) {
        String tipoCliente = (String) jComboBoxTipoCliente.getSelectedItem();
        jTextAreaResultados.setText(consultarProyectosDadoTipoCliente(losProyectos, tipoCliente));
    }
```

### 4.4 Archivos
```java
    private void jButtonGenerarArchivoProyectosActionPerformed(java.awt.event.ActionEvent evt) {
        generarArchivoDeObjetosProyectos(losProyectos);
    }

    private void jButtonLeerArchivoProyectosActionPerformed(java.awt.event.ActionEvent evt) {
        ArrayList<Proyecto> proyectosLeidos = leerArchivoDeObjetosProyectos();
        //Solo se reemplaza la colección si se leyeron proyectos (si el archivo no existe se conservan los actuales)
        if(!proyectosLeidos.isEmpty()){
            losProyectos = proyectosLeidos;
            jTextAreaResultados.setText(consultarTodosProyectos(losProyectos));
        }
    }

    private void jButtonGenerarArchivoClientesActionPerformed(java.awt.event.ActionEvent evt) {
        generarArchivoDeObjetosClientes(losProyectos);
    }

    private void jButtonLeerArchivoClientesActionPerformed(java.awt.event.ActionEvent evt) {
        losClientes = leerArchivoDeObjetosClientes();
        String resultado = "Clientes leidos del archivo\n";
        for(Cliente objeto : losClientes){
            resultado += objeto.toString();
            resultado += "\n";
        }
        jTextAreaResultados.setText(resultado);
    }
```
Los archivos (`datosProyectos.obj`, `datosClientes.obj`) se crean en la carpeta del proyecto y **no se suben a GitHub** (están en `.gitignore`).

### 4.5 Salir
```java
    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(null, "El programa se cerrará. Gracias por utilizar nuestros servicios.");
        System.exit(0);
    }
```

## 5. Comportamientos esperados (para probar la GUI)
Con los datos de prueba:
| Acción | Resultado esperado |
|---|---|
| Consultar todos | 4 proyectos, cada uno con su fecha de finalización |
| Proyectos en curso | solo 2026-001 |
| Registrar proyecto | el código asignado es 2026-004 (en el año 2026) |
| Consultar equipo `2026-002` | "El proyecto no tiene integrantes registrados en su equipo." |
| Consultar equipo `2026-003` | 2 integrantes |
| Proyectos del gerente `66778899` | 2 proyectos |
| Proyectos del cliente `900123456` | 2 proyectos |
| Tipo `Empresa` / `PersonaNatural` | 3 / 1 proyectos |
| Código inexistente (ej. `1999-001`) | "No existe un proyecto con codigo 1999-001." |
| Cancelar en cualquier ventana de datos | la operación se cancela sin registrar nada |
| Leer archivo antes de generarlo | mensaje "no existe el archivo" y se conservan los datos actuales |

## 6. Flujo con Git
```
git checkout develop
git pull
git checkout -b feature/<tema>      # ej. feature/gui-diseno
# trabajar, luego:
git add .
git commit -m "feat: <descripción>"
git push -u origin feature/<tema>
```
Después se abre un **Pull Request** de `feature/<tema>` hacia `develop` en GitHub. Prefijos de commit: `feat:`, `fix:`, `docs:`, `test:`, `style:`, `refactor:`, `chore:`.
