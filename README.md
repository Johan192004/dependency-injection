# Comparación: Inyección de Dependencias en Spring Boot vs Java Puro

Este proyecto demuestra la diferencia entre usar inyección de dependencias con **Spring Boot** vs **Java Puro**.

## 📁 Estructura del Proyecto

```
dependency-injection/
├── demo/              # Proyecto Spring Boot
│   └── src/main/java/com/example/demo/
│       ├── DemoApplication.java
│       ├── controller/
│       │   └── UserController.java
│       ├── service/
│       │   └── UserService.java
│       ├── repository/
│       │   └── UserRepository.java
│       └── model/
│           └── User.java
│
└── demo-java/         # Proyecto Java Puro
    └── src/main/java/org/riwi/
        ├── Main.java
        ├── controller/
        │   └── UserController.java
        ├── service/
        │   └── UserService.java
        ├── repository/
        │   └── UserRepository.java
        └── model/
            └── User.java
```

## 🎯 Objetivo

Mostrar cómo funciona la **Inyección de Dependencias** en ambos contextos:
- **Spring Boot**: Inyección automática con anotaciones (@Service, @Repository, @Autowired)
- **Java Puro**: Inyección manual a través de constructores

## 🚀 Cómo Ejecutar

### 1️⃣ Proyecto Spring Boot (demo/)

```bash
cd demo
mvnw spring-boot:run
```

O en Windows PowerShell:
```powershell
cd demo
.\mvnw.cmd spring-boot:run
```

**Prueba los endpoints:**
- GET `http://localhost:8080/api/users` - Listar usuarios
- GET `http://localhost:8080/api/users/1` - Obtener usuario por ID
- GET `http://localhost:8080/api/users/1/info` - Información del usuario
- POST `http://localhost:8080/api/users` - Crear usuario
- DELETE `http://localhost:8080/api/users/1` - Eliminar usuario

**Ejemplo con curl:**
```bash
# Listar usuarios
curl http://localhost:8080/api/users

# Crear usuario
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d "{\"name\":\"Ana López\",\"email\":\"ana@example.com\"}"
```

### 2️⃣ Proyecto Java Puro (demo-java/)

```bash
cd demo-java
mvn compile exec:java -Dexec.mainClass="org.riwi.Main"
```

O simplemente compila y ejecuta:
```bash
cd demo-java
mvn clean compile
mvn exec:java -Dexec.mainClass="org.riwi.Main"
```

## 🔑 Diferencias Clave

### Spring Boot (Automático)
```java
@Repository
public class UserRepository { }

@Service
public class UserService {
    private final UserRepository repository;
    
    // Spring inyecta automáticamente
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

### Java Puro (Manual)
```java
public class UserRepository { }

public class UserService {
    private final UserRepository repository;
    
    // Debemos pasar manualmente la dependencia
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}

// En el main:
UserRepository repo = new UserRepository();
UserService service = new UserService(repo);
```

## 📊 Comparación

| Aspecto | Spring Boot | Java Puro |
|---------|-------------|-----------|
| **Creación de objetos** | Automática (@Component, @Service, etc.) | Manual con `new` |
| **Inyección** | Automática con @Autowired o constructor | Manual por constructor |
| **Gestión de ciclo de vida** | Spring IoC Container | Desarrollador |
| **Configuración** | Anotaciones + properties | Código Java |
| **Servidor Web** | Tomcat embebido | No incluido |
| **Complejidad** | Más declarativo, menos código | Más código boilerplate |
| **Testing** | @MockBean, @SpringBootTest | Mocks manuales |
| **Cambio de implementación** | Cambiar configuración | Cambiar código |

## 💡 Conceptos Aprendidos

### Inyección de Dependencias (DI)
- Patrón de diseño donde las dependencias se pasan desde afuera
- Facilita el testing y el mantenimiento
- Reduce el acoplamiento entre clases

### Inversión de Control (IoC)
- El framework (Spring) controla la creación y gestión de objetos
- El desarrollador solo declara qué necesita

### Ventajas de DI con Spring Boot
✅ Menos código repetitivo
✅ Configuración automática
✅ Fácil cambio de implementaciones
✅ Soporte para AOP, transacciones, etc.
✅ Ecosistema robusto

### Ventajas de Java Puro
✅ Control total
✅ Sin dependencias externas
✅ Más ligero
✅ Startup más rápido
✅ Más fácil de entender

## 📚 Recursos Adicionales

- [Spring Framework - Dependency Injection](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)
- [Patrón Dependency Injection](https://refactoring.guru/es/design-patterns/dependency-injection)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

## 👨‍💻 Autor

Proyecto educativo para demostrar inyección de dependencias en Spring Boot vs Java Puro.