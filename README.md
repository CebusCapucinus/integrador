Trabajo practico integrador - Programacion 2 - Cerdan, Marcos Samuel - Food Store – Sistema de Gestión de Pedidos de Comida


Food Store es una aplicación de consola desarrollada en Java que permite gestionar categorías, productos, usuarios y pedidos de un negocio de comidas. El sistema sigue los principios de la Programación Orientada a Objetos y utiliza colecciones en memoria para almacenar los datos durante la ejecución. Todas las operaciones de eliminación son lógicas (borrado suave) para conservar el historial.

Requisitos previos

Tener instalado Java 21 o superior.

Tener configurada la variable de entorno JAVA_HOME.

Un editor de texto o un entorno de desarrollo integrado (opcional).

Estructura del proyecto:

src / integrado/prog2/

    ├── Main.java
    ├── entities/
    
    │   ├── Base.java
    │   ├── Categoria.java
    │   ├── Producto.java
    │   ├── Usuario.java
    │   ├── DetallePedido.java
    │   ├── Pedido.java
    │   └── Calculable.java
    
    ├── enums/
    │   ├── Rol.java
    │   ├── Estado.java
    │   └── FormaPago.java
    
    ├── exceptions/
    │   ├── EntidadNoEncontradaException.java
    │   ├── ValidacionException.java
    │   ├── StockInsuficienteException.java
    │   └── MailDuplicadoException.java
    
    └── services/
        ├── CategoryService.java
        ├── ProductService.java
        ├── UserService.java
        └── OrderService.java

        
Cómo ejecutar el proyecto:

Clonar o descargar el repositorio.

Abrir una terminal en la carpeta raíz del proyecto (donde se encuentra la carpeta src).

Compilar todos los archivos con el comando:

javac src/integrado/prog2/Main.java -d .

Esto generará los archivos .class en la misma estructura de paquetes.

Ejecutar la aplicación con:

java integrado.prog2.Main
Asegúrese de estar ubicado en la carpeta raíz (la que contiene la carpeta integrado).

Uso del menú:

Al iniciar, se muestra un menú principal con las siguientes opciones:

=== FOOD STORE SYSTEM ===
1. Categorias
2. Productos
3. Usuarios
4. Pedidos
0. Salir
   
Cada opción abre un submenú con las operaciones de listar, crear, editar y eliminar (lógicamente).



Recomendaciones para probar el flujo completo:


Crear una categoría (menú 1 → opción 2). Anote el identificador generado.

Crear un producto (menú 2 → opción 3). Use el identificador de la categoría creada.

Crear un usuario (menú 3 → opción 2). Anote su identificador.

Crear un pedido (menú 4 → opción 3). Ingrese el identificador del usuario y del producto, y una cantidad válida.

Todas las validaciones (precio no negativo, stock suficiente, correo electrónico único, etc.) se aplican automáticamente y se informan los errores mediante mensajes claros en la consola.


Reglas de negocio implementadas:

No se permite crear un producto con precio o stock negativos.

No se permite crear un pedido sin un usuario válido.

Cada detalle de pedido debe tener una cantidad mayor que cero.

El correo electrónico del usuario debe ser único en todo el sistema.

Las eliminaciones son lógicas: el campo eliminado se marca como true, pero el registro permanece en la colección.

Al crear un pedido, se calcula el total mediante la interfaz Calculable y se actualiza el stock de los productos involucrados.

Si ocurre un error al agregar un detalle (por ejemplo, stock insuficiente), la creación del pedido se cancela por completo para evitar datos inconsistentes.


Decisiones técnicas:

Almacenamiento en memoria: se utilizan HashMap para mantener las entidades, con identificadores numéricos autoincrementales.

Manejo de excepciones: se definieron excepciones propias para cada tipo de error (entidad no encontrada, validación, stock insuficiente, correo duplicado). Todas se capturan en el menú y se muestra un mensaje descriptivo.

Separación en capas:

Entidades: clases del modelo de dominio (heredan de Base).

Servicios: contienen la lógica de negocio y las validaciones.

Vista (menú): únicamente interactúa con el usuario y delega las operaciones a los servicios.

Interfaz Calculable: implementada por Pedido para calcular el total de forma uniforme.

Sin dependencias externas: el proyecto utiliza solo la biblioteca estándar de Java.


Posibles dificultades y soluciones:

Los productos no se guardan: suele deberse a que la categoría asociada no existe o fue eliminada. Se debe crear una categoría primero y usar su identificador al crear el producto.

Los pedidos no se crean: verificar que el usuario y el producto existan y que el producto tenga stock suficiente. Si algún detalle falla, el pedido completo se descarta.

El correo del usuario se rechaza: asegurarse de que no haya otro usuario con el mismo correo (incluso si fue eliminado lógicamente, la validación lo detecta).

Enlace al repositorio y documentación

Repositorio del proyecto: https://github.com/CebusCapucinus/integrador

Documentación en formato PDF: raiz del repositorio

Video demostrativo: https://youtu.be/CMIWvcNVQUU

Bibliografía y recursos
Documentación oficial de Java (Oracle)

Especificación del trabajo práctico integrador – Programación 2 (UTN)

Apuntes de clase sobre Programación Orientada a Objetos y manejo de colecciones
