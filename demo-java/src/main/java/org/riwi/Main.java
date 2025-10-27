package org.riwi;

import org.riwi.controller.UserController;
import org.riwi.repository.UserRepository;
import org.riwi.service.UserService;

/**
 * JAVA PURO - Aplicación Principal
 * 
 * INYECCIÓN MANUAL DE DEPENDENCIAS:
 * 
 * Aquí vemos claramente la diferencia con Spring Boot:
 * 1. Creamos manualmente cada objeto con 'new'
 * 2. Pasamos las dependencias explícitamente en cada constructor
 * 3. Controlamos completamente el orden de creación
 * 4. Somos responsables de la gestión del ciclo de vida
 * 
 * COMPARACIÓN CON SPRING BOOT:
 * 
 * JAVA PURO:                          SPRING BOOT:
 * --------------------------------------------------------------------------------------------------------
 * new UserRepository()                @Repository → Spring crea automáticamente
 * new UserService(repository)         @Service + @Autowired → Spring inyecta automáticamente
 * new UserController(service)         @RestController + @Autowired → Spring inyecta automáticamente
 * 
 * VENTAJAS DE SPRING BOOT:
 * ✓ Menos código boilerplate
 * ✓ Configuración automática
 * ✓ Gestión automática del ciclo de vida
 * ✓ Servidor web incluido
 * ✓ Fácil cambio de implementaciones
 * ✓ Soporte para AOP, transacciones, seguridad, etc.
 * 
 * VENTAJAS DE JAVA PURO:
 * ✓ Control total sobre el código
 * ✓ Sin dependencias externas (framework)
 * ✓ Más ligero en recursos
 * ✓ Más fácil de entender para principiantes
 * ✓ Startup más rápido
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== APLICACIÓN JAVA PURO (Sin Spring Boot) ===");
        System.out.println("Creando e inyectando dependencias MANUALMENTE\n");
        
        // PASO 1: Crear el repositorio (capa más baja)
        System.out.println("PASO 1: Creando UserRepository...");
        UserRepository userRepository = new UserRepository();
        
        // PASO 2: Crear el servicio e inyectar el repositorio
        System.out.println("\nPASO 2: Creando UserService e inyectando UserRepository...");
        UserService userService = new UserService(userRepository);
        
        // PASO 3: Crear el controlador e inyectar el servicio
        System.out.println("\nPASO 3: Creando UserController e inyectando UserService...");
        UserController userController = new UserController(userService);
        
        System.out.println("\n=== CADENA DE DEPENDENCIAS CREADA ===");
        System.out.println("UserController → UserService → UserRepository");
        System.out.println("\nEn Spring Boot, todo esto lo hace el framework automáticamente!");
        
        // Simulamos algunas peticiones HTTP
        System.out.println("\n========================================");
        System.out.println("SIMULANDO PETICIONES HTTP");
        System.out.println("========================================");
        
        // Listar todos los usuarios
        userController.handleGetAllUsers();
        
        // Buscar usuario por ID
        userController.handleGetUserById(1L);
        
        // Obtener información del usuario
        userController.handleGetUserInfo(2L);
        
        // Crear un nuevo usuario
        userController.handleCreateUser("Carlos Rodríguez", "carlos@example.com");
        
        // Listar todos después de crear
        userController.handleGetAllUsers();
        
        // Eliminar un usuario
        userController.handleDeleteUser(2L);
        
        // Listar todos después de eliminar
        userController.handleGetAllUsers();
        
        // Buscar un usuario que no existe
        userController.handleGetUserById(999L);
        
        System.out.println("\n========================================");
        System.out.println("FIN DE LA DEMOSTRACIÓN");
        System.out.println("========================================");
        
        imprimirComparacion();
    }
    
    /**
     * Imprime una comparación detallada entre ambos enfoques
     */
    private static void imprimirComparacion() {
        System.out.println("\n┌─────────────────────────────────────────────────────────────────────┐");
        System.out.println("│           COMPARACIÓN: JAVA PURO vs SPRING BOOT                    │");
        System.out.println("└─────────────────────────────────────────────────────────────────────┘");
        
        System.out.println("\n📋 ASPECTO: Creación de Objetos");
        System.out.println("   Java Puro:    new UserRepository(), new UserService(repo), etc.");
        System.out.println("   Spring Boot:  @Repository, @Service → Spring lo hace automáticamente");
        
        System.out.println("\n📋 ASPECTO: Inyección de Dependencias");
        System.out.println("   Java Puro:    Manual a través del constructor");
        System.out.println("   Spring Boot:  Automática con @Autowired o constructor");
        
        System.out.println("\n📋 ASPECTO: Gestión del Ciclo de Vida");
        System.out.println("   Java Puro:    El desarrollador controla creación y destrucción");
        System.out.println("   Spring Boot:  Spring gestiona todo (singleton por defecto)");
        
        System.out.println("\n📋 ASPECTO: Configuración");
        System.out.println("   Java Puro:    Todo en código Java");
        System.out.println("   Spring Boot:  Anotaciones + application.properties");
        
        System.out.println("\n📋 ASPECTO: Servidor Web");
        System.out.println("   Java Puro:    Debes implementar o usar bibliotecas externas");
        System.out.println("   Spring Boot:  Tomcat embebido, listo para usar");
        
        System.out.println("\n📋 ASPECTO: Complejidad del Código");
        System.out.println("   Java Puro:    Más código boilerplate para configuración");
        System.out.println("   Spring Boot:  Menos código, más declarativo");
        
        System.out.println("\n📋 ASPECTO: Testing");
        System.out.println("   Java Puro:    Fácil crear mocks manualmente");
        System.out.println("   Spring Boot:  @MockBean, @SpringBootTest facilitan las pruebas");
        
        System.out.println("\n📋 ASPECTO: Cambio de Implementaciones");
        System.out.println("   Java Puro:    Cambiar el código donde se crea el objeto");
        System.out.println("   Spring Boot:  Cambiar la anotación o configuración");
        
        System.out.println("\n💡 CONCLUSIÓN:");
        System.out.println("   Spring Boot simplifica enormemente el desarrollo de aplicaciones");
        System.out.println("   empresariales, pero agrega complejidad y dependencias al proyecto.");
        System.out.println("   Java puro da control total pero requiere más código manual.");
        System.out.println("   La elección depende de las necesidades del proyecto.");
    }
}
