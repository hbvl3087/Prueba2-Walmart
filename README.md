# Walmart Retail Checkout Service

## 🚀 Descripción

Sistema de checkout para retail desarrollado con Spring Boot que permite procesar carritos de compras, aplicar promociones y descuentos, y calcular totales considerando diferentes métodos de pago.

### 🎯 Características Principales

- ✅ Cálculo de subtotal del carrito
- ✅ Aplicación de descuentos por producto y promociones
- ✅ Descuentos por medio de pago (ej. débito 10%)
- ✅ Desglose detallado de descuentos aplicados
- ✅ Cálculo del total final
- ✅ API REST bien documentada
- ✅ Diseño extensible para nuevas promociones y medios de pago
- ✅ Frontend simple para visualizar el checkout
- ✅ Simulación de confirmación de pagos

## 🛠️ Stack Tecnológico

- **Java 8+**
- **Spring Boot 2.7.18**
- **Maven** para gestión de dependencias
- **Springfox Swagger** para documentación de API
- **Lombok** para reducir código boilerplate
- **JUnit 5 + Mockito** para testing

## 📋 Requisitos del Sistema

- Java 8 o superior
- Maven 3.6+ (o usar Maven Wrapper incluido)
- Puerto 8080 disponible

## ⚡ Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone <repository-url>
cd checkout-service
```

### 2. Ejecutar la aplicación
```bash
# Opción 1: Con Maven
./mvnw spring-boot:run

# Opción 2: Con Maven wrapper en Windows  
mvnw.cmd spring-boot:run

# Opción 3: Compilar y ejecutar JAR
./mvnw clean package
java -jar target/checkout-service-1.0.0.jar
```

### 3. Verificar la instalación
- **Aplicación**: http://localhost:8080
- **Frontend**: http://localhost:8080/checkout.html
- **API Documentation**: http://localhost:8080/swagger-ui/
- **API Docs JSON**: http://localhost:8080/v2/api-docs

## 🎨 Frontend de Demo

El sistema incluye un frontend interactivo accesible en `http://localhost:8080/checkout.html` que permite:

- Agregar productos al carrito
- Seleccionar métodos de pago
- Ver promociones activas
- Procesar checkout en tiempo real
- Ver desglose detallado de descuentos

## 📡 API Endpoints

### Checkout
- `POST /api/v1/checkout/process` - Procesar checkout
- `GET /api/v1/checkout/payment-methods` - Obtener métodos de pago
- `GET /api/v1/checkout/products` - Obtener productos disponibles

### Promociones
- `GET /api/v1/promotions/active` - Obtener promociones activas
- `GET /api/v1/promotions/applicable` - Obtener promociones aplicables

### Ejemplo de Request para Checkout

```json
{
  "items": [
    {
      "product": {
        "id": "PROD001",
        "name": "Smartphone Samsung Galaxy",
        "price": 899.99,
        "category": "Electronics"
      },
      "quantity": 2
    },
    {
      "product": {
        "id": "PROD004",
        "name": "Organic Coffee Beans",
        "price": 24.99,
        "category": "Food"
      },
      "quantity": 3
    }
  ],
  "paymentMethod": "DEBIT_CARD"
}
```

### Ejemplo de Response

```json
{
  "transactionId": "550e8400-e29b-41d4-a716-446655440000",
  "subtotal": 1874.95,
  "productDiscounts": [
    {
      "discountName": "Electronics Sale",
      "discountAmount": 359.98,
      "discountType": "PROMOTION",
      "description": "20% off all Electronics"
    },
    {
      "discountName": "Coffee Special",
      "discountAmount": 24.99,
      "discountType": "PROMOTION", 
      "description": "Buy 2 Coffee get 1 free"
    }
  ],
  "paymentMethodDiscount": {
    "discountName": "Debit Card Discount",
    "discountAmount": 187.50,
    "discountType": "PAYMENT_METHOD",
    "description": "10% discount for Debit Card"
  },
  "totalDiscounts": 572.47,
  "finalTotal": 1302.48,
  "paymentStatus": "CONFIRMED"
}
```

## 🎁 Promociones Implementadas

### 1. Promociones por Categoría
- **Electronics Sale**: 20% de descuento en todos los productos de Electrónicos

### 2. Promociones por Producto Específico
- **Coffee Special**: Compra 2 café y llévate 1 gratis (Buy X Get Y Free)

### 3. Promociones por Monto Mínimo
- **Minimum Purchase**: $10 de descuento en compras mayores a $100

### 4. Descuentos Fijos por Categoría
- **Footwear Discount**: $15 de descuento en todos los productos de Calzado

## 💳 Métodos de Pago

| Método | Descuento | Descripción |
|--------|-----------|-------------|
| Tarjeta de Crédito | 0% | Sin descuento |
| Tarjeta de Débito | 10% | Descuento por débito |
| Efectivo | 5% | Descuento por pago en efectivo |
| Billetera Digital | 3% | PayPal, Apple Pay, etc. |
| Transferencia Bancaria | 7% | ACH Transfer |

## 🏗️ Arquitectura y Diseño

### Principios de Diseño Aplicados

1. **Separation of Concerns**: Cada servicio tiene una responsabilidad específica
2. **Open/Closed Principle**: Fácil agregar nuevas promociones sin modificar código existente
3. **Dependency Injection**: Bajo acoplamiento entre componentes
4. **Strategy Pattern**: Para diferentes tipos de promociones y métodos de pago

### Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/walmart/checkout/
│   │   ├── controller/          # Controladores REST
│   │   ├── model/              # Modelos de datos
│   │   ├── service/            # Lógica de negocio
│   │   └── CheckoutServiceApplication.java
│   └── resources/
│       ├── static/checkout.html # Frontend
│       └── application.properties
└── test/
    └── java/com/walmart/checkout/
        └── service/            # Tests unitarios
```

### Extensibilidad

#### Agregar Nueva Promoción
1. Definir nuevo tipo en `PromotionType` enum
2. Agregar lógica en `DiscountService.calculatePromotionDiscount()`
3. Configurar promoción en `PromotionService.initializePromotions()`

#### Agregar Nuevo Método de Pago
1. Agregar valor al enum `PaymentMethod` con su descuento
2. Actualizar `PaymentService.getPaymentProcessorName()` si es necesario

## 🧪 Testing

Ejecutar tests:
```bash
./mvnw test
```

El proyecto incluye tests unitarios para:
- Servicio de Checkout
- Servicio de Descuentos
- Cálculos de promociones
- Métodos de pago

## 🔧 Configuración

### Variables de Entorno (application.properties)

```properties
server.port=8080
spring.application.name=Walmart Checkout Service
logging.level.com.walmart.checkout=INFO
```

## 📝 Trade-offs y Decisiones de Diseño

### ✅ Decisiones Tomadas

1. **Almacenamiento en Memoria**: Para simplicidad, productos y promociones se mantienen en memoria
   - **Ventaja**: Arranque rápido, sin dependencias externas
   - **Trade-off**: No persiste datos entre reinicios

2. **Simulación de Pagos**: Mock del procesamiento de pagos
   - **Ventaja**: Desarrollo y testing más fácil
   - **Trade-off**: No integra con procesadores reales

3. **Validación de Negocio en Servicios**: Lógica de negocio separada de controladores
   - **Ventaja**: Código más limpio y testeable
   - **Trade-off**: Más clases, pero mejor organizado

4. **Promociones Configuradas por Código**: Promociones hardcoded en `PromotionService`
   - **Ventaja**: Implementación rápida
   - **Trade-off**: Requiere recompilación para cambios

### 🚀 Mejoras Futuras

1. **Base de Datos**: Migrar a JPA/Hibernate con H2/PostgreSQL
2. **Gestión de Promociones**: Admin panel para crear/editar promociones
3. **Integración de Pagos**: Stripe, PayPal, etc.
4. **Inventario**: Control de stock de productos
5. **Usuarios y Sesiones**: Autenticación y carritos persistentes
6. **Métricas**: Actuator + Micrometer para monitoreo
7. **Caché**: Redis para mejorar performance

## 🐳 Docker (Opcional)

```dockerfile
# Dockerfile
FROM openjdk:11-jre-slim
COPY target/checkout-service-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
# Build y run
docker build -t walmart-checkout .
docker run -p 8080:8080 walmart-checkout
```

## 🤝 Contribución

1. Fork el proyecto
2. Crear feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia MIT - ver [LICENSE.md](LICENSE.md) para detalles.

---

### 📞 Soporte

Para preguntas o soporte, crear un issue en el repositorio o contactar al equipo de desarrollo.

**¡Gracias por usar Walmart Checkout Service!** 🛒✨