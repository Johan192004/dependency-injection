package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SPRING BOOT - Capa de Repositorio
 * 
 * @Repository: Esta anotación indica que esta clase es un componente de Spring
 * que se encarga de acceder a los datos (capa de persistencia).
 * 
 * Spring automáticamente:
 * 1. Crea una instancia de esta clase (bean)
 * 2. La gestiona en su contenedor IoC (Inversion of Control)
 * 3. La inyecta donde sea necesaria
 * 
 * NO necesitamos crear instancias manualmente con 'new UserRepository()'
 */
@Repository
public class UserRepository {
    
    // Simulamos una base de datos con una lista en memoria
    private List<User> database = new ArrayList<>();
    private Long nextId = 1L;

    // Constructor
    public UserRepository() {
        // Inicializamos con algunos datos de ejemplo
        database.add(new User(nextId++, "Juan Pérez", "juan@example.com"));
        database.add(new User(nextId++, "María García", "maria@example.com"));
        System.out.println("✓ UserRepository creado por Spring");
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        database.add(user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return database.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(database);
    }

    public void deleteById(Long id) {
        database.removeIf(user -> user.getId().equals(id));
    }
}
