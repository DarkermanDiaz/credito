# Docker Configuration Guide - Credit Application

## 📋 Archivos Creados

| Archivo | Descripción |
|---------|-------------|
| `Dockerfile` | Configuración multi-stage para construir la imagen Docker |
| `.dockerignore` | Archivos excluidos del contexto de construcción |
| `docker-compose.yml` | Configuración para ejecutar la aplicación localmente con Docker |

## 🚀 Construcción de la Imagen Docker

### Opción 1: Construir manualmente

```bash
# Construir la imagen (linux/amd64)
docker build -t credit-app:v1 .

# Listar imágenes
docker images | grep credit-app
```

### Opción 2: Construir con específicas plataforma

```bash
# Para sistemas ARM64 (Apple Silicon)
docker build --platform linux/arm64 -t credit-app:v1 .

# Para amd64 (Intel/AMD)
docker build --platform linux/amd64 -t credit-app:v1 .
```

## 🐳 Ejecución con Docker

### Opción 1: Con docker-compose (Recomendado para desarrollo)

```bash
# Iniciar la aplicación y la base de datos
docker-compose up -d

# Ver logs
docker-compose logs -f credit-app

# Detener los servicios
docker-compose down

# Detener y eliminar volúmenes
docker-compose down -v
```

### Opción 2: Con Docker directamente

```bash
# Ejecutar la imagen sin base de datos
docker run -d \
  --name credit-app \
  -p 8080:8080 \
  -e SPRING_APPLICATION_NAME=credit \
  credit-app:v1

# Ver logs
docker logs -f credit-app

# Detener el contenedor
docker stop credit-app

# Eliminar el contenedor
docker rm credit-app
```

## 📝 Configuración de Variables de Entorno

Para una configuración sin base de datos local:

```bash
docker run -d \
  --name credit-app \
  -p 8080:8080 \
  -e SPRING_APPLICATION_NAME=credit \
  -e SERVER_PORT=8080 \
  credit-app:v1
```

Para conectar a una base de datos remota:

```bash
docker run -d \
  --name credit-app \
  -p 8080:8080 \
  -e SPRING_APPLICATION_NAME=credit \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db-host:5432/credit_db \
  -e SPRING_DATASOURCE_USERNAME=credit_user \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  credit-app:v1
```

## ✅ Verificación de la Aplicación

```bash
# Comprobar si la aplicación está en ejecución
curl http://localhost:8080/actuator/health

# Ver logs de Docker
docker logs credit-app

# Inspeccionar la imagen
docker inspect credit-app:v1
```

## 🏗️ Características del Dockerfile

- **Multi-stage build**: Reduce el tamaño final de la imagen (70-90% más pequeño)
- **Base image**: `eclipse-temurin:17-jre-alpine` (ligero y seguro)
- **Usuario no-root**: La aplicación se ejecuta con usuario `app` (UID 1000)
- **Health check**: Verifica la salud de la aplicación cada 30 segundos
- **Seguridad**: Implementa mejores prácticas de seguridad en contenedores

## 📦 Tamaño de la Imagen

Después de construir, puedes ver el tamaño:

```bash
docker images | grep credit-app
```

Esperado: ~200-300 MB (dependiendo de las dependencias del proyecto)

## 🚢 Despliegue en Azure

Para desplegar en Azure Container Apps o AKS:

1. **Etiquetar la imagen** para Azure Container Registry (ACR):
   ```bash
   docker tag credit-app:v1 <your-acr-name>.azurecr.io/credit-app:v1
   ```

2. **Empujar la imagen** a ACR:
   ```bash
   docker push <your-acr-name>.azurecr.io/credit-app:v1
   ```

3. **Desplegar** en Azure Container Apps o AKS usando la imagen de ACR

## 🔧 Configuración de application.properties

Para un entorno containerizado, asegúrate de que `application.properties` lea la configuración desde variables de entorno:

```properties
spring.application.name=${SPRING_APPLICATION_NAME:credit}
spring.datasource.url=${SPRING_DATASOURCE_URL:}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:}
server.port=${SERVER_PORT:8080}
```

## ⚠️ Paso Recomendado: Actualizar application.properties

Reemplaza el contenido actual de `src/main/resources/application.properties` con:

```properties
spring.application.name=${SPRING_APPLICATION_NAME:credit}
spring.datasource.url=${SPRING_DATASOURCE_URL:}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:}
server.port=${SERVER_PORT:8080}
management.endpoints.web.exposure.include=health
```

## 🐛 Solución de Problemas

| Problema | Solución |
|----------|----------|
| La imagen no se construye | Asegúrate de que Docker está en ejecución y tienes espacio suficiente |
| Puerto 8080 ya está en uso | Cambia el mapeo: `docker run -p 8081:8080 ...` |
| La aplicación no inicia | Revisa los logs con `docker logs credit-app` |
| Problemas de base de datos | Verifica que docker-compose está iniciando correctamente con `docker-compose logs` |

---

**Próximos pasos**:
- Actualizar `application.properties` para usar variables de entorno
- Construir y probar la imagen localmente
- Empujar a un registro Docker (Docker Hub, ACR, etc.)
- Desplegar en Azure Container Apps o Kubernetes
