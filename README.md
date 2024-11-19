### Documentación del Proyecto: Sistema de Reservas de EveryoneFlies

---

#### **Nombre del Proyecto**
Sistema de Reservas de EveryoneFlies

---

#### **Descripción General**
El **Sistema de Reservas de EveryoneFlies** es una plataforma académica que refleja las capacidades técnicas y metodológicas aplicadas al desarrollo de un sistema completo de reservas de vuelos. Este proyecto demuestra habilidades avanzadas en diseño, desarrollo y despliegue de software utilizando tecnologías modernas. Su objetivo principal es simular la gestión operativa y comercial de una aerolínea, proporcionando una solución ágil, confiable y escalable para reservas de vuelos.

El sistema está diseñado para resolver problemas comunes en la industria, como la gestión manual de datos, procesos fragmentados y baja eficiencia en la atención al cliente. Ofrece funcionalidades completas para la creación y gestión de vuelos, reservas y tickets, con un enfoque en la experiencia del usuario y el cumplimiento de restricciones operativas.

---

#### **Características Principales**
1. **Gestión de Vuelos**
    - Creación, actualización y visualización de vuelos con atributos como aeropuerto de origen/destino, fechas, horarios y categorías de asientos.
    - Implementación de precios dinámicos ajustados a demanda, temporada y disponibilidad.

2. **Gestión de Reservas**
    - Creación y actualización de reservas individuales o grupales.
    - Asignación de un pasajero responsable del pago en reservas grupales.
    - Validación estricta de la coherencia entre el número de pasajeros y las sillas seleccionadas.
    - Estados de reservas gestionados automáticamente en función de acciones específicas (PENDING, PAID, CONFIRMED, CANCELLED, CANCELLED_PAID, REFUNDED).

3. **Gestión de Tickets**
    - Generación automática de tickets electrónicos tras confirmar el pago.
    - Actualización dinámica del estado de tickets según el progreso del vuelo (CONFIRMED, CHECK_IN, COMPLETED, NO_SHOW).

4. **Check-in y Pase de Abordar**
    - Proceso de check-in habilitado únicamente entre 24 y 1 hora antes del vuelo.
    - Generación automática de pases de abordar en formato PDF.
    - Restricciones estrictas para asegurar la consistencia del proceso.

5. **Datos Predefinidos**
    - Valores iniciales para las entidades **Airline**, **Location** y **Airport**, modificables conforme a las restricciones definidas en las clases correspondientes.
    - Recomendación para pruebas: crear vuelos como paso inicial para luego proceder con reservas.

---

#### **Tecnologías Utilizadas**
- **Lenguajes y Frameworks:**
    - Java
    - Spring Boot
- **Herramientas de Desarrollo:**
    - IntelliJ IDEA
    - Maven
    - Swagger
    - MySQL
- **Despliegue:**
    - El sistema está desplegado en la plataforma Railway y puede ser accedido a través del siguiente enlace:  
      [Swagger UI - Sistema de Reservas de EveryoneFlies](https://proyecto-everyoneflies-production.up.railway.app/doc/swagger-ui/index.html#/)

---

#### **Guía de Instalación**
1. **Requisitos Previos:**
    - Java JDK 21 o superior instalado.
    - MySQL configurado con una base de datos específica para el proyecto.
    - Maven instalado en el sistema.

2. **Pasos para Configurar el Proyecto:**
    - Clona el repositorio del proyecto.
    - Importa el proyecto en IntelliJ IDEA.
    - Configura las credenciales de la base de datos en el archivo `application-dev.yml`.
    - Ejecuta `mvn clean install` para descargar las dependencias.
    - Inicia la aplicación con `mvn spring-boot:run`.

---

#### **Guía de Uso**
1. **Flujo Básico de Reservas:**
    - **Paso 1:** Crear un vuelo con las configuraciones deseadas (origen, destino, fechas, sillas disponibles, etc.).
    - **Paso 2:** Crear una reserva asociando los pasajeros y especificando la cantidad y tipo de sillas requeridas.
        - Ejemplo: Si reservas para 5 pasajeros en clase económica, asegúrate de seleccionar 5 sillas económicas disponibles.
    - **Paso 3:** Confirmar el pago de la reserva para cambiar su estado a `PAID`.
    - **Paso 4:** Realizar el check-in dentro del rango de 24 a 1 hora antes del vuelo para generar los pases de abordar.

2. **Estados de las Reservas:**
    - **PENDING:** Estado inicial tras crear la reserva.
    - **CANCELLED:** Automáticamente si no se paga 48 horas antes del vuelo o se cancela manualmente.
    - **PAID:** Una vez confirmado el pago.
        - Subestados:
            - **CANCELLED_PAID:** Cuando se cancela una reserva pagada (elegible para reembolso).
            - **CONFIRMED:** Tras la validación del pago y confirmación final.
    - **COMPLETED / NO_SHOW:** Estados finales tras el vuelo, dependiendo de si el pasajero abordó o no.

3. **Check-in y Generación de Tickets:**
    - Sólo disponible entre 24 y 1 hora antes del vuelo.
    - Genera automáticamente un ticket con estado `CONFIRMED`.
    - Estados del ticket progresan a `CHECK_IN`, `COMPLETED` o `NO_SHOW` según corresponda.

---

#### **Contribuciones**
Este proyecto es académico y está destinado a demostrar las competencias del desarrollador. No se aceptan contribuciones externas, pero el código está disponible como referencia para aprendizaje o proyectos similares.

---

#### **Licencia**
Este proyecto no cuenta con licencia comercial y está orientado únicamente para fines educativos y de demostración.

---

#### **Notas Adicionales**
- Este proyecto demuestra un alto nivel técnico en diseño y desarrollo de sistemas complejos.
- **Advertencia:** Aunque es funcional, no está optimizado para producción y requeriría mejoras en seguridad y escalabilidad antes de implementarse en un entorno comercial.

---


