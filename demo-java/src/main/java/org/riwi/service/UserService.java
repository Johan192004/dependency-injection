package org.riwi.service;

import org.riwi.model.User;
import org.riwi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * JAVA PURO - Capa de Servicio
 * 
 * INYECCIÓN MANUAL DE DEPENDENCIAS:
 * - NO usamos @Service ni @Autowired
 * - Recibimos las dependencias por el constructor
 * - Quien crea UserService debe proporcionar UserRepository manualmente
 * 
 * Patrón de Diseño:
 * Esto sigue el principio de "Dependency Injection" pero de forma MANUAL.
 * Es el mismo patrón que usa Spring, pero sin el framework.
 * 
 * Ventajas de la inyección por constructor (igual que en Spring):
 * ✓ Dependencias inmutables (final)
 * ✓ Fácil de testear
 * ✓ Las dependencias están claras
 * 
 * Desventajas respecto a Spring:
 * ✗ Debemos crear e inyectar todo manualmente
 * ✗ Más código boilerplate
 * ✗ Gestión manual del ciclo de vida
 */
public class UserService {
    
    // La dependencia se declara como final (inmutable)
    private final UserRepository userRepository;

    /**
     * Constructor para inyección manual de dependencias
     * Quien cree un UserService DEBE proporcionar un UserRepository
     * 
     * Ejemplo: new UserService(new UserRepository())
     */
    public UserService(UserRepository userRepository) {
        // Validación manual (Spring lo hace automáticamente)
        if (userRepository == null) {
            throw new IllegalArgumentException("UserRepository no puede ser null");
        }
        this.userRepository = userRepository;
        System.out.println("✓ UserService creado manualmente con UserRepository inyectado");
    }

    // Métodos de lógica de negocio (idénticos a la versión Spring Boot)
    public User createUser(String name, String email) {
        User user = new User(null, name, email);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public String getUserInfo(Long id) {
        return userRepository.findById(id)
                .map(user -> "Usuario encontrado: " + user.getName() + " (" + user.getEmail() + ")")
                .orElse("Usuario no encontrado");
    }
}
