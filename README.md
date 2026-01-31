# Walmart Retail Checkout Service

## 🚀 Descripción

Sistema de checkout para retail desarrollado con Spring Boot que permite procesar carritos de compras, aplicar promociones y descuentos, y calcular totales considerando diferentes métodos de pago. **Actualizado para cumplir con todos los requisitos técnicos especificados, adaptado para trabajar con estructura JSON personalizada, y configurado con precios en pesos chilenos (CLP).**

## ✅ Cumplimiento de Requisitos

| Requisito | Status | Implementación |
|-----------|---------|----------------|
| **Java 11 o superior** | ✅ **Java 17** | Configurado y ejecutándose con OpenJDK 17 |
| **Framework web Java (Spring Boot)** | ✅ **Spring Boot 2.7.18** | Implementado con todas las funcionalidades |
| **API REST bien definida** | ✅ **Completa** | Endpoints REST con documentación Swagger |
| **Diseño orientado a extensibilidad** | ✅ **Implementado** | Arquitectura modular para nuevas promociones y métodos de pago |

### 🎯 Características Principales

- ✅ **API REST dual**: Compatibilidad con estructura JSON personalizada y interfaz web
- ✅ Cálculo de subtotal del carrito con múltiples productos
- ✅ Sistema de promociones automático y extensible
- ✅ Descuentos por método de pago configurables
- ✅ Desglose detallado de todos los descuentos aplicados
- ✅ Validación robusta de datos de entrada
- ✅ Frontend interactivo para pruebas
- ✅ Documentación API con Swagger
- ✅ Testing automatizado

## 🛠️ Stack Tecnológico

- **Java 17** (OpenJDK Eclipse Adoptium)
- **Spring Boot 2.7.18** 
- **Maven 3.9+** con Maven Wrapper
- **Springfox Swagger 3.0.0** para documentación de API
- **Lombok** para reducir código boilerplate
- **Jackson** para serialización JSON avanzada
- **JUnit 5 + Mockito** para testing
- **Spring Boot DevTools** para desarrollo

## 📋 Requisitos del Sistema

- **Java 17 o superior** ✅
- Maven 3.6+ (Maven Wrapper incluido)
- Puerto 8080 disponible

## ⚡ Instalación y Ejecución

### 🚀 **Inicio Rápido** (3 pasos)

#### **Paso 1**: Configurar Java 17
```powershell
# En PowerShell (Windows) - Configurar variables de entorno
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.14.7-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Verificar instalación
java -version
# Debe mostrar: openjdk version "17.0.14"
```

#### **Paso 2**: Descargar y Compilar
```bash
# Clonar repositorio (si aplica)
git clone <repository-url>
cd "Prueba2 Walmart"

# O simplemente navegar al directorio del proyecto
cd "C:\Users\tu-usuario\Documents\Prueba2 Walmart"

# Limpiar y compilar
.\mvnw.cmd clean compile
```

#### **Paso 3**: Ejecutar la Aplicación
```bash
# Iniciar el servidor Spring Boot
.\mvnw.cmd spring-boot:run

# La aplicación iniciará en el puerto 8080
# Verás el mensaje: "Tomcat started on port(s): 8080 (http)"
```

### ✅ **Verificación de la Instalación**

Una vez iniciada la aplicación, verifica que todo funciona:

| URL | Descripción | Estado esperado |
|-----|-------------|-----------------|
| http://localhost:8080 | Página principal | Redirección a checkout.html |
| http://localhost:8080/checkout.html | **Frontend Interactivo** | Interfaz de pruebas completa |
| http://localhost:8080/swagger-ui/ | **Documentación API** | Swagger UI funcional |
| http://localhost:8080/api/v1/checkout/products | API de productos | JSON con catálogo |

### 🧪 **Prueba Rápida de la API**

```powershell
# Probar endpoint con PowerShell
$body = @'
{
  "cartId": "test-001",
  "items": [{"sku": "p-001", "quantity": 1}],
  "paymentMethod": "DEBIT"
}
'@

Invoke-RestMethod -Uri "http://localhost:8080/api/v1/checkout/process" `
  -Method POST -ContentType "application/json" -Body $body
```

### 🔧 **Comandos Adicionales**

```bash
# Ejecutar solo los tests
.\mvnw.cmd test

# Compilar sin ejecutar
.\mvnw.cmd clean package

# Ver información del proyecto
.\mvnw.cmd --version

# Detener la aplicación: Ctrl+C en la terminal
```

### ⚠️ **Troubleshooting**

| Problema | Solución |
|----------|----------|
| "Java no encontrado" | Verificar JAVA_HOME y PATH |
| "Puerto 8080 ocupado" | Cambiar puerto en application.properties o cerrar aplicación que usa 8080 |
| "Tests fallan" | Verificar Java 17 y ejecutar `.\mvnw.cmd clean test` |
| "Maven wrapper no funciona" | Usar `mvn` directamente si Maven está instalado globalmente |

## 📡 API REST - Estructura Adaptada

### 🆕 **Endpoint Principal** (Estructura JSON Personalizada)

**`POST /api/v1/checkout/process`**

Acepta la estructura JSON personalizada con SKUs y direcciones de envío:

```json
{
  "cartId": "cart-1001",
  "items": [
    {"sku": "p-001", "quantity": 1},
    {"sku": "p-010", "quantity": 2},
    {"sku": "p-003", "quantity": 1}
  ],
  "shippingAddress": {
    "street": "Av. Falsa 123",
    "city": "Ciudad",
    "zoneId": "zone-1"
  },
  "paymentMethod": "DEBIT"
}
```

### 🔄 **Endpoint Legacy** (Compatibilidad Web UI)

**`POST /api/v1/checkout/process-legacy`**

Mantiene compatibilidad con la interfaz web existente:

```json
{
  "items": [
    {
      "product": {
        "id": "p-001",
        "name": "Smartphone Samsung Galaxy", 
        "price": 809991,
        "category": "Electronics"
      },
      "quantity": 1
    }
  ],
  "paymentMethod": "DEBIT_CARD"
}
```

### 📋 **Otros Endpoints**

- `GET /api/v1/checkout/payment-methods` - Métodos de pago disponibles
- `GET /api/v1/checkout/products` - Catálogo de productos  
- `GET /api/v1/promotions/active` - Promociones activas
- `GET /api/v1/promotions/applicable` - Promociones por producto/categoría

### 🧪 **Ejemplo de Prueba con PowerShell**

```powershell
# Prueba con estructura personalizada
Invoke-RestMethod -Uri "http://localhost:8080/api/v1/checkout/process" `
  -Method POST -ContentType "application/json" `
  -Body (Get-Content "test-request.json" -Raw)
```

### 📊 **Respuesta Completa**

```json
{
  "transactionId": "135d1600-8f26-423a-b2ed-5bac40fb8b96",
  "subtotal": 3269991,
  "productDiscounts": [
    {
      "discountId": "PROMO001",
      "discountName": "Electronics Sale", 
      "discountAmount": 161998,
      "description": "20% off all Electronics",
      "applicableItem": "p-001"
    }
  ],
  "promotionDiscounts": [
    {
      "discountId": "PROMO003",
      "discountName": "Minimum Purchase Discount",
      "discountAmount": 9000,
      "description": "$9000 CLP off on purchases over $90000 CLP"
    }
  ],
  "paymentMethodDiscount": {
    "discountId": "PAYMENT_DEBIT", 
    "discountName": "Debit Discount",
    "discountAmount": 326999,
    "description": "10.0% discount for Debit"
  },
  "totalDiscounts": 979996,
  "finalTotal": 2289995,
  "paymentStatus": "CONFIRMED",
  "processedAt": "2026-01-30T21:01:06.7142629",
  "summary": "Checkout Summary:\nSubtotal: $3269991 CLP\nDiscounts Applied:\n- Electronics Sale: -$161998 CLP\n- Minimum Purchase Discount: -$9000 CLP\n- Debit Discount: -$326999 CLP\nTotal Discounts: -$979996 CLP\nFinal Total: $2289995 CLP"
}
```

## 🛍️ Productos Disponibles (SKUs compatibles)

| SKU | Nombre | Precio | Categoría |
|-----|---------|---------|-----------|
| `p-001` | Smartphone Samsung Galaxy | $809,991 CLP | Electronics |
| `p-010` | Laptop Dell XPS 13 | $1,169,991 CLP | Electronics |
| `p-003` | Nike Air Max Sneakers | $116,991 CLP | Footwear |
| `PROD004` | Organic Coffee Beans | $22,491 CLP | Food |
| `PROD005` | Wireless Headphones | $179,991 CLP | Electronics |

## 💳 Métodos de Pago Soportados

| Método | Enum | Descuento | Descripción |
|--------|------|-----------|-------------|
| Tarjeta de Crédito | `CREDIT_CARD` | 0% | Sin descuento |
| **Débito** | `DEBIT` | 10% | **Compatibilidad con estructura personalizada** |
| Efectivo | `CASH` | 5% | Descuento por efectivo |
| Billetera Digital | `DIGITAL_WALLET` | 3% | PayPal, Apple Pay, etc. |
| Transferencia | `BANK_TRANSFER` | 7% | ACH Transfer |

## 🎁 Sistema de Promociones Automático

### Promociones por Categoría
- **Electronics Sale** (PROMO001): 20% descuento en Electrónicos
- **Footwear Discount** (PROMO004): $13500 CLP descuento fijo en Calzado

### Promociones por Compra Mínima  
- **Minimum Purchase** (PROMO003): $9000 CLP descuento si subtotal > $90000 CLP

### Promociones Buy X Get Y
- **Coffee Special** (PROMO002): Compra 2 cafés, llévate 1 gratis

## 🏗️ Arquitectura y Extensibilidad

### Diseño Modular

```
src/main/java/com/walmart/checkout/
├── controller/
│   ├── CheckoutController.java      # Endpoints REST duales
│   ├── PromotionController.java     # Gestión de promociones
│   └── WebController.java           # Redirección web
├── model/
│   ├── ShoppingCartRequest.java     # Estructura personalizada (SKU)
│   ├── ShoppingCart.java            # Estructura interna
│   ├── CartItemRequest.java         # Item con SKU
│   ├── ShippingAddress.java         # Direcciones de envío
│   ├── PaymentMethod.java           # Métodos de pago extendidos
│   └── CheckoutResult.java          # Respuesta completa
└── service/
    ├── CheckoutService.java         # Orquestación principal
    ├── DiscountService.java         # Motor de descuentos
    ├── PromotionService.java        # Gestión de promociones
    ├── PaymentService.java          # Procesamiento de pagos  
    └── ProductService.java          # Catálogo con SKUs
```

### Extensibilidad Implementada

#### ✅ **Agregar Nueva Promoción**
1. Definir en `PromotionService.initializePromotions()`
2. Lógica en `DiscountService.calculatePromotionDiscount()`
3. Configuración de aplicabilidad por producto/categoría

#### ✅ **Agregar Nuevo Método de Pago** 
1. Agregar enum en `PaymentMethod` con porcentaje de descuento
2. Automáticamente disponible en toda la aplicación

## 🧪 Testing y Validación

### Ejecutar Tests Completos
```bash
.\mvnw.cmd test
```

**Tests Incluidos:**
- ✅ **CheckoutServiceTest**: Procesamiento completo de checkout
- ✅ **DiscountServiceTest**: Cálculos de descuentos y promociones
- ✅ Validación de estructura JSON con `@Valid`
- ✅ Manejo de errores y excepciones
- ✅ Tests de integración de controladores

### Validación de Entrada
- **Campos requeridos**: SKU, cantidad, método de pago
- **Validación de rangos**: Cantidad mínima 1
- **Productos existentes**: Verificación de SKUs válidos  
- **Manejo de errores**: Respuestas JSON estructuradas

## 🎨 Frontend Interactivo

**URL**: http://localhost:8080/checkout.html

### Funcionalidades del Frontend:
- 🛒 **Agregar productos** del catálogo al carrito
- 💳 **Seleccionar método de pago** con preview de descuentos
- 📊 **Ver promociones activas** automáticamente aplicadas
- 💰 **Calcular totales** en tiempo real
- ✅ **Procesar checkout** con desglose completo
- 📱 **Responsive design** para móviles

## 🔧 Configuración Avanzada

### Variables de Entorno
```properties
# application.properties
server.port=8080
spring.application.name=Walmart Checkout Service
logging.level.com.walmart.checkout=INFO

# Configuración JSON
spring.jackson.property-naming-strategy=SNAKE_CASE
spring.jackson.default-property-inclusion=NON_NULL
```

### Configuración de CORS (si es necesario)
```java
@CrossOrigin(origins = "*") // Ya incluido en controladores
```

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
- **Minimum Purchase**: $9000 CLP de descuento en compras mayores a $90000 CLP

### 4. Descuentos Fijos por Categoría
- **Footwear Discount**: $13500 CLP de descuento en todos los productos de Calzado

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


## 📄 Licencia

Este proyecto está bajo la licencia MIT - ver [LICENSE.md](LICENSE.md) para detalles.