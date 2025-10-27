package org.riwi.repository;

import org.riwi.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JAVA PURO - Capa de Repositorio
 * 
 * Sin Spring Boot:
 * - NO hay anotaciones como @Repository
 * - Debemos crear instancias manualmente con 'new'
 * - Nosotros controlamos el ciclo de vida del objeto
 * - Debemos pasar las dependencias manualmente
 * 
 * Esta clase es funcionalmente idéntica a la versión de Spring Boot,
 * pero la diferencia está en CÓMO se instancia y gestiona.
 */
public class UserRepository {
    
    // Simulamos una base de datos con una lista en memoria
    private List<User> database = new ArrayList<>();
    private Long nextId = 1L;

    /**
     * Constructor
     * En Java puro, nosotros decidimos cuándo llamar a 'new UserRepository()'
     */
    public UserRepository() {
        // Inicializamos con algunos datos de ejemplo
        database.add(new User(nextId++, "Juan Pérez", "juan@example.com"));
        database.add(new User(nextId++, "María García", "maria@example.com"));
        System.out.println("✓ UserRepository creado manualmente");
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
