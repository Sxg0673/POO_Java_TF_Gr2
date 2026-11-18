# POO_Java_TF_Gr2

Aplicativo en Java con interfaz gráfica para la gestión de proyectos: registro y consulta de proyectos, sus clientes (empresas y personas naturales), gerentes y equipos de trabajo.

## Requisitos
- JDK 21 o superior.
- Apache NetBeans (proyecto Java with Ant).

## Descarga y ejecución
1. Clonar el repositorio:
   ```
   git clone https://github.com/Sxg0673/POO_Java_TF_Gr2.git
   ```
2. En NetBeans: **File → Open Project** y seleccionar la carpeta `POO_Java_TF_Gr2`.
3. Ejecutar con **Run Project** (F6). La clase principal es `Vista.UsaProyecto`.

Al iniciar, el aplicativo carga 4 proyectos de prueba para poder realizar consultas sin registrar datos.

## Estructura del proyecto
```
src/
├── Modelo/   clases del dominio
└── Vista/    interfaz gráfica y funcionalidades del aplicativo
test/         pruebas unitarias
docs/         documentación del proyecto
```

### Paquete Modelo
| Clase | Descripción |
|---|---|
| `Cliente` | Clase abstracta con los datos comunes de un cliente: número de identificación, nombre y ciudad. |
| `Empresa` | Cliente de tipo empresa; tiene un contacto. |
| `PersonaNatural` | Cliente de tipo persona natural; tiene teléfono y correo electrónico. |
| `Contacto` | Persona de contacto de una empresa. |
| `Empleado` | Empleado que puede ser gerente o integrante del equipo de un proyecto. |
| `Proyecto` | Proyecto con su cliente, gerente, equipo y estado. Determina su fecha de finalización y permite agregar o eliminar integrantes del equipo. |

### Paquete Vista
`UsaProyecto` es la ventana principal del aplicativo (JFrame). Contiene las colecciones de proyectos y clientes y las funcionalidades:

| Grupo | Funcionalidades |
|---|---|
| Registro | Registrar un proyecto (el código se genera automáticamente: año y consecutivo anual, ej. 2026-001). |
| Consultas | Todos los proyectos, proyectos en curso, primer y último proyecto, proyecto por código, gerente y equipo de un proyecto, proyectos por gerente, por cliente y por tipo de cliente. |
| Equipo y estado | Registrar un integrante, registrar el equipo completo, eliminar un integrante y actualizar el estado de un proyecto. |
| Archivos | Generar y leer archivos de objetos de proyectos (`datosProyectos.obj`) y de clientes (`datosClientes.obj`). |

Los datos se solicitan mediante ventanas de diálogo con validación de cada campo; cualquier operación puede cancelarse sin afectar los datos registrados.

## Cómo usar el aplicativo

La ventana **Gestión de proyectos** tiene cuatro paneles, un área de resultados y el botón Salir. Los resultados de las consultas se muestran en el área de texto inferior.

![Ventana principal](docs/01_ventana_principal.png)

### Proyectos
Los botones **Registrar proyecto**, **Consultar todos**, **Proyectos en curso**, **Primer proyecto** y **Último proyecto** no necesitan ningún dato previo. Registrar proyecto abre ventanas de diálogo que piden los datos uno a uno.

![Consultar todos](docs/02_consultar_todos.png)

### Operaciones por código de proyecto
Escribir el código del proyecto en el campo (por ejemplo `2026-001`) y pulsar el botón de la operación: consultar proyecto, gerente o equipo, registrar integrante o equipo, eliminar integrante o actualizar el estado. Si el campo está vacío, el aplicativo muestra un mensaje de error.

![Consultar equipo](docs/03_consultar_equipo.png)

### Consultas por gerente y cliente
Escribir el número de identificación del gerente o del cliente y pulsar el botón correspondiente. También se pueden consultar los proyectos por tipo de cliente (`Empresa` o `PersonaNatural`) eligiendo la opción en la lista.

![Proyectos por tipo de cliente](docs/04_proyectos_por_tipo_cliente.png)

### Archivos
Los cuatro botones del panel **Archivos** generan y leen los archivos de objetos de proyectos y de clientes.

### Salir
El botón **Salir** muestra un mensaje de despedida y cierra el programa.

## Documentación
- [Contrato GUI](docs/CONTRATO_GUI.md): componentes, nombres y código de los eventos de la interfaz gráfica.
- [Roles y forma de trabajo](docs/ROLES.md): reparto de tareas y guía para trabajar con ramas y Pull Requests.

## Integrantes
- Santiago Castillo
- Jacob José Daniells Janna
- Juan Sebastián Herrera Benavides
- Juan David Pino López