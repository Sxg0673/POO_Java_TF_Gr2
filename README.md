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

## Documentación
- [Contrato GUI](docs/CONTRATO_GUI.md): componentes, nombres y código de los eventos de la interfaz gráfica.
- [Roles y forma de trabajo](docs/ROLES.md): reparto de tareas y guía para trabajar con ramas y Pull Requests.

## Integrantes
- Santiago Castillo
