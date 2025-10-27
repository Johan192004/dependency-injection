# 📘 Guía Detallada: Inyección de Dependencias

## 🤔 ¿Qué es la Inyección de Dependencias?

La **Inyección de Dependencias** (Dependency Injection - DI) es un patrón de diseño donde una clase recibe sus dependencias desde el exterior en lugar de crearlas ella misma.

### ❌ Sin Inyección de Dependencias (Acoplamiento Fuerte)

```java
public class UserService {
    // La clase crea su propia dependencia
    private UserRepository repository = new UserRepository();
    
    public User getUser(Long id) {
        return repository.findById(id);
    }
}
```

**Problemas:**
- ❌ Difícil de testear (no podemos usar un mock)
- ❌ Alto acoplamiento
- ❌ Difícil cambiar la implementación
- ❌ La clase tiene demasiadas responsabilidades

### ✅ Con Inyección de Dependencias (Bajo Acoplamiento)

```java
public class UserService {
    // La dependencia se inyecta desde afuera
    private final UserRepository repository;
    
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    
    public User getUser(Long id) {
        return repository.findById(id);
    }
}
```

**Ventajas:**
- ✅ Fácil de testear (inyectamos mocks)
- ✅ Bajo acoplamiento
- ✅ Fácil cambiar implementación
- ✅ Responsabilidad única

## 🔄 Flujo de Creación

### Spring Boot (Automático)

```
┌─────────────────────────────────────────┐
│    Spring IoC Container                 │
│                                         │
│  1. Escanea anotaciones                 │
│     @Repository, @Service, @Controller  │
│                                         │
│  2. Crea instancias (beans)            │
│     UserRepository repo = new ...       │
│     UserService service = new ...       │
│     UserController ctrl = new ...       │
│                                         │
│  3. Inyecta dependencias               │
│     service.repository = repo           │
│     ctrl.service = service              │
│                                         │
│  4. Gestiona ciclo de vida             │
│     - Init callbacks                    │
│     - Destroy callbacks                 │
└─────────────────────────────────────────┘
        ↓
    Tu aplicación lista para usar
```

### Java Puro (Manual)

```
┌─────────────────────────────────────────┐
│    Main.java                            │
│                                         │
│  1. Crear repositorio                   │
│     UserRepository repo = new ...       │
│                                         │
│  2. Crear servicio con repo            │
│     UserService service =               │
│         new UserService(repo)           │
│                                         │
│  3. Crear controlador con service      │
│     UserController ctrl =               │
│         new UserController(service)     │
│                                         │
│  4. Gestionar ciclo de vida            │
│     (tú decides cuándo crear/destruir)  │
└─────────────────────────────────────────┘
        ↓
    Tu aplicación lista para usar
```

## 🎯 Tipos de Inyección

### 1. Inyección por Constructor (Recomendada)

**Spring Boot:**
```java
@Service
public class UserService {
    private final UserRepository repository;
    
    // Spring inyecta automáticamente
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

**Java Puro:**
```java
public class UserService {
    private final UserRepository repository;
    
    // Debemos pasar manualmente
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}

// En el main:
UserRepository repo = new UserRepository();
UserService service = new UserService(repo);
```

**Ventajas:**
- ✅ Dependencias inmutables (final)
- ✅ Fácil de testear
- ✅ Las dependencias son obligatorias
- ✅ Thread-safe

### 2. Inyección por Setter (No Recomendada)

**Spring Boot:**
```java
@Service
public class UserService {
    private UserRepository repository;
    
    @Autowired
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
```

**Desventajas:**
- ❌ Dependencias mutables
- ❌ Pueden ser nulas
- ❌ Más difícil de testear

### 3. Inyección por Campo (No Recomendada)

**Spring Boot:**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
}
```

**Desventajas:**
- ❌ Difícil de testear (necesitas reflexión)
- ❌ No puedes hacer el campo final
- ❌ Dependencias ocultas

## 🏗️ Arquitectura de Capas

```
┌──────────────────────────────────┐
│  Controller (Capa de Presentación)│
│  - Recibe peticiones HTTP         │
│  - Valida entrada                 │
│  - Delega al Service              │
└───────────────┬──────────────────┘
                │ depende de
                ↓
┌──────────────────────────────────┐
│  Service (Capa de Negocio)       │
│  - Lógica de negocio             │
│  - Orquesta operaciones          │
│  - Delega al Repository          │
└───────────────┬──────────────────┘
                │ depende de
                ↓
┌──────────────────────────────────┐
│  Repository (Capa de Datos)      │
│  - Acceso a base de datos        │
│  - CRUD operations               │
│  - Abstracción de persistencia   │
└───────────────┬──────────────────┘
                │ accede a
                ↓
┌──────────────────────────────────┐
│  Database / Modelo               │
└──────────────────────────────────┘
```

## 🧪 Testing con Inyección de Dependencias

### Spring Boot (Con @MockBean)

```java
@SpringBootTest
class UserServiceTest {
    
    @MockBean
    private UserRepository repository;
    
    @Autowired
    private UserService service;
    
    @Test
    void testGetUser() {
        // Arrange
        User mockUser = new User(1L, "Test", "test@example.com");
        when(repository.findById(1L))
            .thenReturn(Optional.of(mockUser));
        
        // Act
        User result = service.getUserById(1L).orElse(null);
        
        // Assert
        assertNotNull(result);
        assertEquals("Test", result.getName());
    }
}
```

### Java Puro (Con Mockito)

```java
class UserServiceTest {
    
    @Test
    void testGetUser() {
        // Arrange - Creamos un mock manual
        UserRepository mockRepository = mock(UserRepository.class);
        UserService service = new UserService(mockRepository);
        
        User mockUser = new User(1L, "Test", "test@example.com");
        when(mockRepository.findById(1L))
            .thenReturn(Optional.of(mockUser));
        
        // Act
        User result = service.getUserById(1L).orElse(null);
        
        // Assert
        assertNotNull(result);
        assertEquals("Test", result.getName());
    }
}
```

## 📊 Comparación Lado a Lado

### Crear un Usuario

**Spring Boot:**
```java
// Controller
@RestController
public class UserController {
    private final UserService service;
    
    public UserController(UserService service) {
        this.service = service;
    }
    
    @PostMapping("/api/users")
    public User create(@RequestBody User user) {
        return service.createUser(user.getName(), user.getEmail());
    }
}

// Todo lo demás es automático:
// - Spring crea las instancias
// - Inyecta las dependencias
// - Maneja las peticiones HTTP
// - Serializa/deserializa JSON
```

**Java Puro:**
```java
// Main
public class Main {
    public static void main(String[] args) {
        // 1. Crear manualmente todas las dependencias
        UserRepository repo = new UserRepository();
        UserService service = new UserService(repo);
        UserController controller = new UserController(service);
        
        // 2. Simular la petición
        controller.handleCreateUser("Ana", "ana@example.com");
        
        // 3. No hay servidor HTTP automático
        // 4. No hay serialización JSON automática
        // 5. Debemos gestionar todo manualmente
    }
}
```

## 🎓 Conceptos Clave

### Inversión de Control (IoC)

**Definición:** El framework controla el flujo del programa, no tu código.

**Ejemplo:**
- **Sin IoC:** Tu código crea objetos → Tu código llama métodos
- **Con IoC:** Spring crea objetos → Spring llama tus métodos

### Contenedor IoC de Spring

Es el componente central de Spring que:
1. **Crea** instancias de clases (beans)
2. **Configura** esas instancias
3. **Inyecta** dependencias entre ellas
4. **Gestiona** su ciclo de vida

### Bean

Un **bean** es simplemente un objeto gestionado por Spring:
- Spring lo crea
- Spring lo configura
- Spring lo inyecta donde se necesite
- Spring lo destruye cuando ya no se necesita

### Scope de Beans

**Singleton (por defecto):**
- Una sola instancia compartida
- La misma instancia se inyecta en todas partes

```java
@Service // Por defecto es singleton
public class UserService { }
```

**Prototype:**
- Nueva instancia cada vez que se inyecta

```java
@Service
@Scope("prototype")
public class UserService { }
```

## 🚀 Mejores Prácticas

### ✅ Hacer

1. **Usar inyección por constructor**
   ```java
   public UserService(UserRepository repository) {
       this.repository = repository;
   }
   ```

2. **Declarar dependencias como final**
   ```java
   private final UserRepository repository;
   ```

3. **Programar hacia interfaces**
   ```java
   public interface UserRepository { }
   
   @Repository
   public class UserRepositoryImpl implements UserRepository { }
   ```

4. **Mantener clases con responsabilidad única**

5. **Usar anotaciones específicas**
   - `@Repository` para acceso a datos
   - `@Service` para lógica de negocio
   - `@Controller` o `@RestController` para controladores

### ❌ Evitar

1. **Inyección por campo**
   ```java
   @Autowired
   private UserRepository repository; // ❌
   ```

2. **Crear dependencias manualmente en clases gestionadas**
   ```java
   @Service
   public class UserService {
       private UserRepository repo = new UserRepository(); // ❌
   }
   ```

3. **Dependencias circulares**
   ```java
   @Service
   public class A {
       private final B b; // A depende de B
   }
   
   @Service
   public class B {
       private final A a; // B depende de A ❌
   }
   ```

## 💡 Conclusión

| Aspecto | Spring Boot | Java Puro |
|---------|-------------|-----------|
| **Complejidad inicial** | Alta | Baja |
| **Productividad** | Alta | Media |
| **Control** | Medio | Total |
| **Escalabilidad** | Alta | Media |
| **Curva de aprendizaje** | Pronunciada | Suave |
| **Tamaño del proyecto** | Grande/Empresarial | Pequeño/Mediano |
| **Mantenimiento** | Fácil | Más trabajo |
| **Testing** | Excelente soporte | Manual |

**¿Cuándo usar Spring Boot?**
- ✅ Aplicaciones empresariales
- ✅ APIs REST complejas
- ✅ Microservicios
- ✅ Necesitas muchas integraciones
- ✅ Equipo grande

**¿Cuándo usar Java Puro?**
- ✅ Aplicaciones pequeñas
- ✅ Scripts o herramientas
- ✅ Aprendizaje
- ✅ Recursos limitados
- ✅ Control total necesario
