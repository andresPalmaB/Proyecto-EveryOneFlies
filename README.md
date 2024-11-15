### Documentación del Proyecto: Sistema de Reservas de EveryoneFlies

---

#### **Nombre del Proyecto**
Sistema de Reservas de EveryoneFlies

---

#### **Descripción General**
El **Sistema de Reservas de EveryoneFlies** es una solución integral diseñada para mejorar la experiencia de reservas de vuelos, tanto para los pasajeros como para el personal de la aerolínea. Este sistema tiene como objetivo modernizar y automatizar la gestión de vuelos, reservas, tickets y atención al cliente, mejorando la eficiencia operativa y optimizando el proceso de compra para los pasajeros.

El sistema aborda problemas como la gestión manual de datos, los tiempos de respuesta prolongados y la falta de integración en procesos esenciales, proporcionando una experiencia ágil y confiable.

---

#### **Características Principales**
1. **Gestión de Vuelos**
    - Creación, actualización y visualización de vuelos con detalles como aeropuertos de origen/destino, fechas, horarios y categorías de asientos.
    - Precios dinámicos basados en demanda, temporada y disponibilidad.

2. **Gestión de Reservas**
    - Creación y actualización de reservas individuales o grupales.
    - Asignación de un pasajero responsable del pago para reservas grupales.
    - Manejo de cambios en categorías de asientos.
    - Visualización de detalles de reservas.

3. **Gestión de Tickets**
    - Generación automática de tickets electrónicos al confirmar el pago de la reserva.
    - Estado del ticket ajustado según el flujo del viaje (CONFIRMED, CHECK_IN, COMPLETED, NO_SHOW).

4. **Check-in y Pase de Abordar**
    - Proceso de check-in en línea habilitado 24 horas antes del vuelo y cerrado una hora antes de la salida.
    - Generación automática de pases de abordar en formato PDF.

---

#### **Tecnologías Utilizadas**
- **Lenguajes y Frameworks:**
    - Java
    - Spring Boot (para el backend)
- **Herramientas de Desarrollo:**
    - IntelliJ IDEA (entorno de desarrollo)
    - Maven (gestión de dependencias)
    - Swagger (documentación de API)
    - MySQL (base de datos relacional)
- **Despliegue:**
    - Preparado para desplegar en la nube (Heroku, AWS, u otras plataformas similares).

---

#### **Instalación**
1. **Requisitos Previos:**
    - Tener Java JDK 21 o superior instalado.
    - Instalar MySQL y configurar una base de datos para el proyecto.
    - Maven configurado en el entorno.

2. **Pasos para Configurar el Proyecto:**
    - Clona el repositorio del proyecto.
    - Importa el proyecto en IntelliJ IDEA.
    - Configura las credenciales de la base de datos en el archivo `application-dev.yml` de Spring Boot.
    - Ejecuta el comando `mvn clean install` para instalar las dependencias.
    - Lanza la aplicación con el comando `mvn spring-boot:run`.

3. **Despliegue en la Nube:**
    - Empaqueta la aplicación en un archivo `.jar` usando `mvn package`.
    - Sube el archivo `.jar` a una plataforma como Heroku o AWS Elastic Beanstalk.
    - Configura las variables de entorno necesarias, como las credenciales de la base de datos.

---

#### **Uso**
- **Reservar un Vuelo:** El usuario puede buscar vuelos según el origen, destino y fechas, seleccionar el vuelo deseado, y realizar la reserva proporcionando la información de los pasajeros y el pago.
- **Check-in en Línea:** A través del sistema de check-in en línea, el pasajero puede seleccionar asientos y recibir el pase de abordar en un PDF.
- **Gestiones del Administrador:** Los administradores pueden gestionar vuelos, revisar reservas y obtener reportes para ayudar en la operación eficiente de la aerolínea.

---

#### **Contribuciones**
Este proyecto es de carácter académico, por lo que no se aceptan contribuciones externas. No obstante, cualquier persona interesada puede utilizar el código como referencia para su aprendizaje o como base para futuros desarrollos.

---

#### **Licencia**
Este proyecto no tiene licencia oficial, ya que es un proyecto académico y no está destinado para uso comercial.

---

#### **Notas Adicionales**
- **Advertencia:** Este sistema está diseñado para fines educativos y no está optimizado para un entorno de producción. Se recomienda realizar mejoras en seguridad y escalabilidad antes de considerarlo para uso comercial.

