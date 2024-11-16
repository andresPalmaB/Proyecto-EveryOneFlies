# Usa la imagen base de Java 21
FROM eclipse-temurin:21-jdk-jammy

# Define el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el código fuente al contenedor
COPY . /app

# Construye el proyecto con Maven (sin pruebas)
RUN chmod +x ./mvnw && ./mvnw -B -DskipTests clean package

# Comando para ejecutar el JAR generado
CMD ["java", "-jar", "target/your-app.jar"]
