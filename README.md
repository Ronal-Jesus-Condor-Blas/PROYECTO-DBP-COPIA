# "Foodtales: Discover, taste, and tell" 💬🍽️ 👥

## CS 2031 Desarrollo Basado en Plataformas. 💻

## Project Members 🤝


| Name                                  | GitHub User                                                           | Email                                                         |
|---------------------------------------|-----------------------------------------------------------------------|---------------------------------------------------------------|
| Ronal Jesus Condor Blas               | [Ronal-Jesus-Condor-Blas](https://github.com/Ronal-Jesus-Condor-Blas) | [ronal.condor@utec.edu.pe](mailto:ronal.condor@utec.edu.pe)   |
| Isaac Emanuel Javier Simeón Sarmiento | [Isaac181222](https://github.com/Isaac181222)                         | [isaac.simeon@utec.edu.pe](mailto:isaac.simeon@utec.edu.pe)   |
| Luis David Barahona Valdivieso        | [LuissBarahona](https://github.com/LuissBarahona)                     | [luis.barahona@utec.edu.pe](mailto:luis.barahona@utec.edu.pe) |

## Índice
- [Introducción](#introducción)
- [Identificación del Problema o Necesidad](#identificación-del-problema-o-necesidad)
- [Descripción de la Solución](#descripción-de-la-solución)
- [Modelo de Entidades](#modelo-de-entidades)
- [Testing y Manejo de Errores](#testing-y-manejo-de-errores)
- [Medidas de Seguridad Implementadas](#medidas-de-seguridad-implementadas)
- [Eventos y Asincronía](#eventos-y-asincronía)
- [GitHub](#github)
- [Conclusión](#conclusión)
- [Apéndices](#apéndices)

## Introducción. 📖
### Contexto: ✏️
En la actualidad, existen muchas plataformas que permiten a los usuarios buscar lugares para comer, pero pocas ofrecen un espacio donde se puedan compartir de forma auténtica las experiencias, reseñas y opiniones relacionadas con la comida. Las personas no solo buscan un lugar para comer, sino también un espacio donde puedan hablar sobre sus descubrimientos, compartir reseñas, expresar sus opiniones y explorar nuevas opciones culinarias a través de las experiencias de otros usuarios. Foodtales surge como una solución que une una red social con el mundo gastronómico, permitiendo a los usuarios encontrar lugares de comida y compartir sus vivencias con una comunidad activa.

### Objetivos del proyecto: 🎯
a) Permitir a los usuarios encontrar restaurantes, cafés, food trucks y otros lugares de comida en función de su ubicación y preferencias.  
b) Facilitar que los usuarios puedan escribir reseñas y compartir opiniones sobre sus experiencias gastronómicas.  
c) Crear un espacio para que los usuarios puedan debatir y compartir recomendaciones sobre diferentes tipos de cocina y platos.  
d) Ofrecer herramientas para que los usuarios puedan seguir a otros amantes de la comida y explorar sus recomendaciones.  

## Identificación del problema o necesidad. ❗
### Descripción del problema: 🤔
En la actualidad, existe una creciente demanda por experiencias gastronómicas únicas y auténticas. Sin embargo, muchas personas tienen dificultades para encontrar recomendaciones confiables y actualizadas sobre lugares de comida, especialmente en zonas menos populares. A pesar de que existen diversas plataformas de reseñas, estas suelen ser impersonales, dispersas o carecen de un enfoque específico en la experiencia completa alrededor de los alimentos. Además, los usuarios buscan más que simples calificaciones; desean una comunidad en la que puedan compartir historias y experiencias en torno a la comida.
### Justificación: ✅
Resolver esta necesidad es crucial porque la comida no solo satisface una necesidad básica, sino que también representa una forma de cultura, identidad y conexión social. Foodtales permitirá a los usuarios descubrir nuevos lugares, expresar opiniones auténticas y conectarse con otros amantes de la gastronomía en un espacio interactivo. Esta plataforma fomentará la creación de una comunidad donde cada experiencia culinaria sea valorada como parte de una historia, aportando así valor tanto a consumidores como a restaurantes. Conectar personas a través de las experiencias gastronómicas no solo enriquece las recomendaciones, sino que también contribuye al crecimiento de los negocios locales.

## Descripción de la solución. ✔️✔️
### Funcionalidades Implementadas: 🛠️
1. **Registro y Autenticación de Usuarios:**
    - Implementación de un sistema de autenticación basado en **JWT (JSON Web Tokens)**, permitiendo a los usuarios registrarse e iniciar sesión de forma segura.
    - Dos endpoints principales:
        - `/auth/login`: Permite a los usuarios iniciar sesión con sus credenciales.
        - `/auth/register`: Permite a los usuarios registrarse proporcionando los detalles necesarios.
    - Manejo seguro de contraseñas y generación de tokens de acceso para las sesiones de usuario.

2. **Gestión de Publicaciones (Posts):**
    - Los usuarios pueden crear, editar y eliminar publicaciones donde comparten sus experiencias gastronómicas.
    - Las publicaciones permiten la inclusión de texto y calificaciones relacionadas con comidas o restaurantes.
    - Cada publicación se asocia a un usuario y puede tener múltiples comentarios de otros usuarios.

3. **Sistema de Comentarios:**
    - Los usuarios pueden comentar en las publicaciones de otros usuarios, lo que fomenta la interacción y discusión en torno a los temas culinarios.
    - Los comentarios son gestionados a través de un controlador dedicado, permitiendo crear y eliminar comentarios asociados a publicaciones específicas.

4. **Gestión de Restaurantes:**
    - Los usuarios pueden agregar nuevos restaurantes a la plataforma.
    - Cada restaurante tiene atributos como nombre, ubicación y correo electrónico, y puede ser actualizado o eliminado por los administradores.
    - Funcionalidad para publicar eventos específicos cuando un nuevo restaurante es registrado, como el envío de correos electrónicos automáticos de bienvenida.

5. **Eventos Asíncronos:**
    - El sistema incluye eventos asíncronos, como el envío de correos electrónicos cuando un nuevo restaurante es registrado en la plataforma.
    - Implementación de la anotación `@EnableAsync` en el proyecto para permitir el procesamiento de tareas de manera asíncrona, mejorando el rendimiento y la escalabilidad.

6. **Manejo de Errores y Validación:**
    - Manejo adecuado de errores en los endpoints, devolviendo respuestas HTTP apropiadas (como `404 Not Found` o `400 Bad Request`) cuando es necesario.
    - Validación de los datos de entrada en las solicitudes de creación y actualización de publicaciones, comentarios y restaurantes.

### Tecnologías Utilizadas: 🤖
1. **Spring Boot 3.3.4:**
   - **Spring Boot Starter Web**: Utilizado para crear el backend de la aplicación, manejando las solicitudes HTTP y proporcionando APIs RESTful.
   - **Spring Boot Starter Data JPA**: Usado para la persistencia de datos mediante el mapeo objeto-relacional (ORM) con JPA/Hibernate.
   - **Spring Boot Starter Mail**: Para el envío de correos electrónicos de manera sencilla, como parte de la funcionalidad de notificaciones.

2. **Base de Datos:**
   - **PostgreSQL**: Base de datos relacional utilizada para almacenar la información de usuarios, publicaciones, comentarios y restaurantes.
   - **H2 Database**: Base de datos en memoria usada principalmente para pruebas en el entorno de desarrollo.
   - **Testcontainers (PostgreSQL)**: Utilizado para pruebas de integración, ejecutando contenedores Docker de PostgreSQL durante los tests.

3. **Seguridad:**
   - **JWT (JSON Web Tokens)**: Implementado para la autenticación y autorización de usuarios, garantizando sesiones seguras.
   - **Hibernate Validator**: Utilizado para la validación de datos y garantizar la consistencia de las solicitudes recibidas.

4. **Manejo de Eventos Asíncronos:**
   - **Spring Async**: Implementado para el procesamiento de tareas de manera asíncrona, mejorando la eficiencia del sistema en tareas como el envío de correos.

5. **Envío de Correos:**
   - **Jakarta Mail (JavaMail)**: Utilizado para enviar correos electrónicos automáticos, como notificaciones de bienvenida a los nuevos restaurantes registrados.

6. **Otras Tecnologías:**
   - **Lombok**: Usado para reducir el código repetitivo, proporcionando anotaciones que simplifican la creación de getters, setters y constructores.
   - **Gson**: Biblioteca de Google para el procesamiento de JSON, utilizada para la serialización y deserialización de objetos.
   - **OkHttp**: Cliente HTTP utilizado para realizar peticiones HTTP de manera eficiente.

## Modelo de Entidades. ⚙️
aqui va la imagen del diagrama
### Descripción: 📋
| Entidad          | Descripción                                                                                                                                               | Relaciones                                                                                                   |
|------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| **User**         | Representa a un usuario de la plataforma. Incluye atributos como nombre, correo electrónico, contraseña, biografía, y tipo de usuario (UserType).            | - Un usuario puede tener varios **posts** y **comentarios**.<br> - Relación muchos a muchos con otros **usuarios** (seguidores). |
| **Post**         | Representa una publicación realizada por un usuario, donde comparte su experiencia o reseña sobre algún restaurante o comida.                               | - Un **post** es creado por un **usuario**.<br> - Un **post** puede tener múltiples **comentarios**.         |
| **Comment**      | Representa un comentario hecho por un usuario en una publicación (post). Incluye el contenido del comentario y la fecha en que se realizó.                  | - Un **comentario** pertenece a un **post** y es hecho por un **usuario**.                                   |
| **Food**         | Representa un plato de comida. Incluye atributos como nombre, precio, calificación promedio y estado (disponible o no disponible).                          | - Un **plato** puede estar asociado con un **restaurante**.<br> - Puede recibir varias **valoraciones de comida**. |
| **Restaurant**   | Representa un restaurante en la plataforma, incluyendo su nombre, ubicación, y estado.                                                                     | - Un **restaurante** puede tener varios **platos**.<br> - Los **usuarios** pueden hacer valoraciones a los **restaurantes**.     |
| **TypeFood**     | Representa un tipo de comida, como italiana, mexicana, o peruana. Incluye el nombre y una descripción opcional.                                             | - Relación muchos a muchos con **restaurantes**.<br> Un restaurante puede tener varios tipos de comida.       |
| **FoodRating**   | Representa una calificación que un usuario realiza sobre un plato específico, con una puntuación del 1 al 5 y un comentario opcional.                        | - Relación muchos a uno con **plato (Food)** y **usuario**.                                                  |
| **RestaurantRating** | Representa una calificación que un usuario realiza sobre un restaurante, con una puntuación del 1 al 5 y un comentario opcional.                            | - Relación muchos a uno con **restaurante (Restaurant)** y **usuario**.                                       |
## Testing y Manejo de Errores. ❌➡️✅
### Niveles de Testing Realizados: 🛠️

### Resultados: 📊

### Manejo de Errores: ❌

## Medidas de Seguridad Implementadas 🔒
### Seguridad de Datos: 🛡️

### Prevención de Vulnerabilidades: 🚨

## Eventos y Asincronía. 📧
En **Foodtales**, los eventos y la asincronía juegan un papel importante para mejorar la eficiencia del sistema, especialmente en tareas que no requieren una respuesta inmediata. El envío de correos electrónicos es uno de los principales ejemplos de este enfoque. En lugar de procesar estas tareas de manera síncrona, lo cual podría generar demoras innecesarias para el usuario, se ejecutan en segundo plano, permitiendo que la experiencia sea más fluida.
### Casos de uso del envío de correos electrónicos:
1. **Registro de un Nuevo Usuario:**
   - Cuando un nuevo usuario se registra en la plataforma, se dispara un evento que envía de forma asíncrona un correo electrónico de bienvenida. Este correo confirma el registro del usuario y proporciona información útil para comenzar a interactuar en la plataforma. El envío de este correo en segundo plano permite que el usuario complete el proceso de registro sin esperas innecesarias.

2. **Registro de un Nuevo Restaurante:**
   - Cuando un restaurante es registrado por un usuario, también se genera un evento asíncrono que envía un correo electrónico de bienvenida al propietario del restaurante. Este correo está diseñado para proporcionar instrucciones sobre cómo gestionar el perfil del restaurante dentro de la plataforma, destacando herramientas que pueden ayudar a mejorar su visibilidad frente a otros usuarios. Este evento se ejecuta sin afectar la fluidez de la experiencia del usuario, mejorando la escalabilidad del sistema.
## GitHub 🖥️🔧
El desarrollo de **Foodtales** se gestionó de forma colaborativa utilizando **GitHub**, donde se emplearon ramas, issues y pull requests para organizar y revisar el trabajo de todo el equipo.

- **Ramas (Branches):** Cada nueva funcionalidad o corrección de errores se desarrolló en ramas independientes para evitar conflictos en la rama principal (`main`). Esto permitió que los miembros del equipo trabajaran en paralelo de manera eficiente.

- **Issues:** Se utilizaron issues para asignar tareas, reportar errores y gestionar el progreso del proyecto. Las etiquetas (`bug`, `enhancement`) ayudaron a priorizar las tareas y mantener el enfoque en lo más importante.

- **Pull Requests:** Antes de fusionar cualquier cambio en la rama principal, se creaba un pull request. Esto permitió que los cambios fueran revisados y discutidos por el equipo, asegurando la calidad del código antes de su integración.

Este flujo de trabajo colaborativo permitió mantener el proyecto organizado, mejorar la calidad del código mediante revisiones, y asegurar una integración continua sin problemas.
## Conclusiones: 📌
### Logros del Proyecto: 📝
El desarrollo de Foodtales ha permitido crear una plataforma que resuelve una necesidad significativa al ofrecer un espacio donde los usuarios pueden encontrar y compartir experiencias culinarias de forma auténtica. Se ha logrado integrar funcionalidades para la publicación de reseñas y discusión abierta entre amantes de la comida. Esto ha contribuido a la construcción de una comunidad activa en torno a la gastronomía, facilitando el descubrimiento de nuevos lugares y el crecimiento de pequeños negocios locales.
### Aprendizajes Clave: 📚
Durante el desarrollo de la aplicación, aprendimos la importancia de planificar bien la estructura del código para que fuera fácil de mantener y mejorar. Además, trabajar con retroalimentación constante nos ayudó a ajustar detalles de la interfaz, mejorando la usabilidad y asegurando una experiencia positiva para los usuarios.
### Trabajo Futuro: 🚀
En el futuro, nos gustaría agregar más funciones, como filtros para buscar comida según preferencias personales o recomendaciones en tiempo real. También planeamos ofrecer recompensas para los usuarios más activos y hacer alianzas con restaurantes para ofrecer descuentos exclusivos a la comunidad.
