package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SPRING BOOT - Capa de Servicio
 * 
 * @Service: Indica que esta clase contiene la lógica de negocio de la aplicación.
 * 
 * INYECCIÓN DE DEPENDENCIAS POR CONSTRUCTOR:
 * - Spring detecta que UserService necesita un UserRepository
 * - Automáticamente busca un bean de tipo UserRepository en su contenedor
 * - Lo inyecta a través del constructor
 * 
 * Ventajas:
 * 1. Las dependencias son inmutables (final)
 * 2. Facilita las pruebas unitarias
 * 3. El código es más limpio y mantenible
 * 4. Spring garantiza que las dependencias estén listas
 */
@Service
public class UserService {
    
    // La dependencia se declara como final (inmutable)
    private final UserRepository userRepository;

    /**
     * Constructor para inyección de dependencias
     * Spring automáticamente inyecta UserRepository aquí
     * 
     * NO necesitamos @Autowired en el constructor (desde Spring 4.3+)
     * si solo hay un constructor
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("✓ UserService creado por Spring con UserRepository inyectado");
    }

    // Métodos de lógica de negocio
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
