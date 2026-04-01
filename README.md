# Credit Risk Engine - Sistema de Evaluación de Riesgo Crediticio

## 📋 Descripción General

Sistema integral de evaluación y gestión de riesgo crediticio desarrollado con **Spring Boot 4.0.4**. Permite evaluar solicitudes de crédito, gestionar información de solicitantes, historial de pagos, datos contables y procesos de verificación legal.

## ✨ Características Principales

- ✅ **Evaluación de Riesgo**: Motor de cálculo de riesgo basado en múltiples factores
- ✅ **Gestión de Solicitantes**: CRUD completo para solicitantes
- ✅ **Datos Contables**: Almacenamiento y consulta de información financiera
- ✅ **Historial de Pagos**: Registro detallado de pagos por acreedor
- ✅ **Verificación Legal**: Control de procesos legales asociados
- ✅ **API RESTful**: Interfaz moderna basada en REST
- ✅ **Docker**: Despliegue mediante contenedores

## 🛠️ Stack Tecnológico

| Componente | Versión |
|-----------|---------|
| **Java** | 17 |
| **Spring Boot** | 4.0.4 |
| **Spring Cloud** | 2025.1.1 |
| **Base de Datos** | MySQL 8.0 |
| **Maven** | 3.9+ |
| **Docker** | 20.10+ |

### Dependencias Principales
- **Spring Boot Starter Data JPA**: ORM y persistencia
- **Spring Boot Starter Web**: Desarrollo REST
- **Spring Boot Starter Actuator**: Monitoring y health checks
- **Spring Cloud OpenFeign**: Llamadas HTTP declarativas
- **MySQL Connector**: Driver JDBC para MySQL

## 🚀 Instalación y Configuración

### Opción 1: Ejecución con Docker (Recomendado)

**Requisitos previos:**
- Docker 20.10+
- Docker Compose 2.0+

**Pasos:**

1. **Clonar o descargar el proyecto**
   ```bash
   cd credit
   ```

2. **Construir e iniciar los contenedores**
   ```bash
   docker-compose up -d --build
   ```

   Esto iniciará tres servicios:
   - **credit-app** (puerto 8080): Aplicación Spring Boot
   - **credit-db** (puerto 3306): Base de datos MySQL
   - **phpmyadmin** (puerto 8081): Herramienta de administración de BD

3. **Verificar que la aplicación está activa**
   ```bash
   curl http://localhost:8080/actuator/health
   ```

4. **Detener los contenedores**
   ```bash
   docker-compose down
   ```

### Opción 2: Ejecución Local

**Requisitos previos:**
- Java 17 JDK
- Maven 3.9+
- MySQL 8.0
- Git

**Pasos:**

1. **Instalar y configurar MySQL**
   ```bash
   # En Windows
   mysql -u root -p < init-db.sql
   
   # En Linux/Mac
   mysql -u root -p credit_db < init-db.sql
   ```

2. **Configurar la conexión a base de datos**
   
   Editar `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/credit_db
   spring.datasource.username=root
   spring.datasource.password=tu_contraseña
   ```

3. **Compilar y ejecutar**
   ```bash
   # Compilar
   mvn clean install
   
   # Ejecutar
   mvn spring-boot:run
   ```

   La aplicación estará disponible en `http://localhost:8080`

## 📚 Documentación de APIs

### URL Base
```
http://localhost:8080/api/v1
```

---

## 1️⃣ Gestión de Solicitantes

### GET /solicitantes
Obtener lista de todos los solicitantes

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "empresaId": "EMP001",
    "montoSolicitado": 50000.00,
    "productoFinanciero": "LÍNEA_CRÉDITO",
    "fechaSolicitud": "2024-01-15"
  }
]
```

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/solicitantes \
  -H "Content-Type: application/json"
```

---

### GET /solicitantes/{id}
Obtener un solicitante por ID

**Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Integer | ID del solicitante |

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "empresaId": "EMP001",
  "montoSolicitado": 50000.00,
  "productoFinanciero": "LÍNEA_CRÉDITO",
  "fechaSolicitud": "2024-01-15"
}
```

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/solicitantes/1 \
  -H "Content-Type: application/json"
```

---

### POST /solicitantes
Crear un nuevo solicitante

**Request Body:**
```json
{
  "empresaId": "EMP002",
  "montoSolicitado": 75000.00,
  "productoFinanciero": "CRÉDITO_COMERCIAL",
  "fechaSolicitud": "2024-03-20"
}
```

**Respuesta (201 Created):**
```json
{
  "mensaje": "Solicitante creado exitosamente",
  "codigo": "200"
}
```

**Comando curl:**
```bash
curl -X POST http://localhost:8080/api/v1/solicitantes \
  -H "Content-Type: application/json" \
  -d '{
    "empresaId": "EMP002",
    "montoSolicitado": 75000.00,
    "productoFinanciero": "CRÉDITO_COMERCIAL",
    "fechaSolicitud": "2024-03-20"
  }'
```

---

### PATCH /solicitantes/{id}
Actualizar parcialmente un solicitante

**Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Integer | ID del solicitante |

**Request Body:**
```json
{
  "montoSolicitado": 85000.00
}
```

**Respuesta (200 OK):**
```json
{
  "mensaje": "Solicitante actualizado exitosamente",
  "codigo": "200"
}
```

**Comando curl:**
```bash
curl -X PATCH http://localhost:8080/api/v1/solicitantes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "montoSolicitado": 85000.00
  }'
```

---

### DELETE /solicitantes/{id}
Eliminar un solicitante

**Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Integer | ID del solicitante |

**Respuesta (200 OK):**
```json
{
  "mensaje": "Solicitante eliminado exitosamente",
  "codigo": "200"
}
```

**Comando curl:**
```bash
curl -X DELETE http://localhost:8080/api/v1/solicitantes/1 \
  -H "Content-Type: application/json"
```

---

## 2️⃣ Gestión de Datos Contables

### GET /datoscontables
Obtener todos los registros de datos contables

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "solicitanteId": 1,
    "ingresoAnual": 500000.00,
    "gastosOperacionales": 150000.00,
    "deudaTotal": 50000.00,
    "capitalSocial": 200000.00,
    "activos": 800000.00,
    "pasivos": 200000.00
  }
]
```

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/datoscontables \
  -H "Content-Type: application/json"
```

---

### GET /datoscontables/{id}
Obtener datos contables por ID

**Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Integer | ID del registro |

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/datoscontables/1 \
  -H "Content-Type: application/json"
```

---

### GET /datoscontables/solicitante/{id}
Obtener datos contables de un solicitante

**Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Integer | ID del solicitante |

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/datoscontables/solicitante/1 \
  -H "Content-Type: application/json"
```

---

### POST /datoscontables
Crear nuevo registro de datos contables

**Request Body:**
```json
{
  "solicitanteId": 1,
  "ingresoAnual": 600000.00,
  "gastosOperacionales": 180000.00,
  "deudaTotal": 60000.00,
  "capitalSocial": 250000.00,
  "activos": 900000.00,
  "pasivos": 250000.00
}
```

**Respuesta (201 Created):**
```json
{
  "mensaje": "Datos contables creados exitosamente",
  "codigo": "200"
}
```

**Comando curl:**
```bash
curl -X POST http://localhost:8080/api/v1/datoscontables \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "ingresoAnual": 600000.00,
    "gastosOperacionales": 180000.00,
    "deudaTotal": 60000.00,
    "capitalSocial": 250000.00,
    "activos": 900000.00,
    "pasivos": 250000.00
  }'
```

---

### PUT /datoscontables/{id}
Actualizar completamente un registro de datos contables

**Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Integer | ID del registro |

**Request Body:**
```json
{
  "solicitanteId": 1,
  "ingresoAnual": 650000.00,
  "gastosOperacionales": 190000.00,
  "deudaTotal": 65000.00,
  "capitalSocial": 260000.00,
  "activos": 950000.00,
  "pasivos": 260000.00
}
```

**Comando curl:**
```bash
curl -X PUT http://localhost:8080/api/v1/datoscontables/1 \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "ingresoAnual": 650000.00,
    ...
  }'
```

---

### DELETE /datoscontables/{id}
Eliminar un registro de datos contables

**Comando curl:**
```bash
curl -X DELETE http://localhost:8080/api/v1/datoscontables/1 \
  -H "Content-Type: application/json"
```

---

## 3️⃣ Gestión de Historial de Pagos

### GET /historialpagos
Obtener todos los registros de historial de pagos

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "solicitanteId": 1,
    "acreedor": "Banco A",
    "monto": 5000.00,
    "fechaPago": "2024-02-15",
    "estado": "PAGADO"
  }
]
```

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/historialpagos
```

---

### GET /historialpagos/{id}
Obtener un registro específico del historial

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/historialpagos/1
```

---

### GET /historialpagos/acreedor?acreedor={acreedor}
Obtener pagos por acreedor

**Parámetros Query:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| acreedor | String | Nombre del acreedor |

**Comando curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/historialpagos/acreedor?acreedor=Banco%20A"
```

---

### GET /historialpagos/solicitante?id={id}
Obtener historial de pagos de un solicitante

**Parámetros Query:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Integer | ID del solicitante |

**Comando curl:**
```bash
curl -X GET "http://localhost:8080/api/v1/historialpagos/solicitante?id=1"
```

---

### POST /historialpagos
Crear nuevo registro de pago

**Request Body:**
```json
{
  "solicitanteId": 1,
  "acreedor": "Banco B",
  "monto": 7500.00,
  "fechaPago": "2024-03-10",
  "estado": "PAGADO"
}
```

**Comando curl:**
```bash
curl -X POST http://localhost:8080/api/v1/historialpagos \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "acreedor": "Banco B",
    "monto": 7500.00,
    "fechaPago": "2024-03-10",
    "estado": "PAGADO"
  }'
```

---

### PUT /historialpagos/{id}
Actualizar un registro de pago

**Comando curl:**
```bash
curl -X PUT http://localhost:8080/api/v1/historialpagos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "acreedor": "Banco B",
    "monto": 8000.00,
    "fechaPago": "2024-03-10",
    "estado": "PAGADO"
  }'
```

---

### DELETE /historialpagos/{id}
Eliminar un registro de pago

**Comando curl:**
```bash
curl -X DELETE http://localhost:8080/api/v1/historialpagos/1
```

---

## 4️⃣ Gestión de Verificación Legal

### GET /verificacionlegal
Obtener todos los registros de verificación legal

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "solicitanteId": 1,
    "demandas": 0,
    "embargos": false,
    "incumplimientos": 0,
    "estado": "SIN_ANTECEDENTES",
    "fechaVerificacion": "2024-01-20"
  }
]
```

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/verificacionlegal
```

---

### GET /verificacionlegal/{id}
Obtener un registro específico de verificación

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/verificacionlegal/1
```

---

### GET /verificacionlegal/solicitante/{id}
Obtener verificaciones legales de un solicitante

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/verificacionlegal/solicitante/1
```

---

### POST /verificacionlegal
Crear nuevo registro de verificación

**Request Body:**
```json
{
  "solicitanteId": 1,
  "demandas": 0,
  "embargos": false,
  "incumplimientos": 0,
  "estado": "VERIFICADO",
  "fechaVerificacion": "2024-03-15"
}
```

**Comando curl:**
```bash
curl -X POST http://localhost:8080/api/v1/verificacionlegal \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "demandas": 0,
    "embargos": false,
    "incumplimientos": 0,
    "estado": "VERIFICADO",
    "fechaVerificacion": "2024-03-15"
  }'
```

---

### PUT /verificacionlegal/{id}
Actualizar un registro de verificación

**Comando curl:**
```bash
curl -X PUT http://localhost:8080/api/v1/verificacionlegal/1 \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "demandas": 1,
    "embargos": false,
    "incumplimientos": 0,
    "estado": "CON_ANTECEDENTES",
    "fechaVerificacion": "2024-03-15"
  }'
```

---

### DELETE /verificacionlegal/{id}
Eliminar un registro de verificación

**Comando curl:**
```bash
curl -X DELETE http://localhost:8080/api/v1/verificacionlegal/1
```

---

## 5️⃣ Motor de Evaluación de Riesgo

### GET /risk/{id}
Calcular riesgo de una solicitud

**Parámetros:**
| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | Integer | ID del solicitante |

**Respuesta (200 OK):**
```json
{
  "solicitanteId": 1,
  "nivelRiesgo": "MEDIO",
  "puntuacion": 65.5,
  "recomendacion": "EVALUACIÓN_MANUAL",
  "factoresAnalisis": {
    "ratioDeuda": 0.15,
    "historiaPagos": "BUENA",
    "verificacionLegal": "LIMPIA"
  },
  "fechaCalculo": "2024-03-20T10:30:00"
}
```

**Comando curl:**
```bash
curl -X GET http://localhost:8080/api/v1/risk/1
```

---

## 🗄️ Base de Datos

### Estructura de la Base de Datos

La base de datos `credit_db` contiene las siguientes tablas:

| Tabla | Descripción |
|-------|-------------|
| `solicitante` | Información del solicitante |
| `datos_contables` | Datos financieros del solicitante |
| `historial_pagos` | Registro de pagos realizados |
| `verificacion_legal` | Información de procesos legales |

### Credenciales Predeterminadas (Docker)

| Variable | Valor |
|----------|-------|
| Host | credit-db |
| Puerto | 3306 |
| Usuario | credit_user |
| Contraseña | credit_password |
| Base de Datos | credit_db |

### Ver/Administrar Base de Datos

**Opción 1: PhpMyAdmin**
- URL: `http://localhost:8081`
- Servidor: credit-db
- Usuario: root
- Contraseña: root_password

**Opción 2: MySQL CLI**
```bash
# Acceder al contenedor MySQL
docker exec -it credit-db mysql -u credit_user -p

# Contrasena: credit_password

# Ver base de datos
mysql> USE credit_db;
mysql> SHOW TABLES;
```

---

## 🏥 Health Check y Monitoring

### Verificar Estado de la Aplicación

```bash
curl -X GET http://localhost:8080/actuator/health
```

**Respuesta (200 OK):**
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    }
  }
}
```

### Métricas de la Aplicación

```bash
curl -X GET http://localhost:8080/actuator/metrics
```

---

## 📝 Ejemplos de Flujo Completo

### Ejemplo 1: Crear Solicitante y Calcular Riesgo

```bash
# 1. Crear solicitante
curl -X POST http://localhost:8080/api/v1/solicitantes \
  -H "Content-Type: application/json" \
  -d '{
    "empresaId": "TECHCORP001",
    "montoSolicitado": 100000.00,
    "productoFinanciero": "CRÉDITO_EMPRESARIAL",
    "fechaSolicitud": "2024-03-20"
  }'

# 2. Agregar datos contables
curl -X POST http://localhost:8080/api/v1/datoscontables \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "ingresoAnual": 1000000.00,
    "gastosOperacionales": 300000.00,
    "deudaTotal": 150000.00,
    "capitalSocial": 500000.00,
    "activos": 2000000.00,
    "pasivos": 500000.00
  }'

# 3. Verificar antecedentes legales
curl -X POST http://localhost:8080/api/v1/verificacionlegal \
  -H "Content-Type: application/json" \
  -d '{
    "solicitanteId": 1,
    "demandas": 0,
    "embargos": false,
    "incumplimientos": 0,
    "estado": "LIMPIO",
    "fechaVerificacion": "2024-03-20"
  }'

# 4. Calcular riesgo
curl -X GET http://localhost:8080/api/v1/risk/1
```

---

## 🔍 Solución de Problemas

### La aplicación no inicia
```bash
# Verificar logs del contenedor
docker logs credit-app

# Verificar si MySQL está disponible
docker exec credit-db mysqladmin ping -u credit_user -p
```

### Error de conexión a base de datos
```bash
# Verificar estado de los contenedores
docker ps

# Reiniciar servicios
docker-compose restart
```

### Puerto 8080 ya en uso
```bash
# Cambiar puerto en docker-compose.yml
# Modificar: "8080:8080" por "8090:8080"

docker-compose down
docker-compose up -d
```

---

## 📖 Recursos Adicionales

- 📚 [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- 🐳 [Documentación de Docker](https://docs.docker.com/)
- 🐬 [Documentación de MySQL](https://dev.mysql.com/doc/)
- 🚀 [REST API Best Practices](https://restfulapi.net/)

---

## 📧 Información de Contacto

Para reportar problemas o sugerencias, contactar al equipo de desarrollo.

---

**Última actualización:** Abril 2024  
**Versión:** 1.0.0  
**Estado:** ✅ Producción
