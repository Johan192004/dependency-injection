package org.riwi.controller;

import org.riwi.model.User;
import org.riwi.service.UserService;

import java.util.List;

/**
 * JAVA PURO - "Controlador" (Simulado)
 * 
 * En Java puro no tenemos un servidor web automático como en Spring Boot.
 * Esta clase simula un controlador que manejaría peticiones.
 * 
 * CADENA DE DEPENDENCIAS MANUAL:
 * UserController → necesita → UserService → necesita → UserRepository
 * 
 * En Java puro, debemos:
 * 1. Crear UserRepository manualmente: new UserRepository()
 * 2. Crear UserService con UserRepository: new UserService(repository)
 * 3. Crear UserController con UserService: new UserController(service)
 * 
 * Spring Boot hace todo esto automáticamente con su contenedor IoC.
 */
public class UserController {
    
    private final UserService userService;

    /**
     * Constructor con inyección manual de dependencias
     * Quien cree un UserController DEBE proporcionar un UserService
     */
    public UserController(UserService userService) {
        // Validación manual
        if (userService == null) {
            throw new IllegalArgumentException("UserService no puede ser null");
        }
        this.userService = userService;
        System.out.println("✓ UserController creado manualmente con UserService inyectado");
    }

    /**
     * Simula una petición GET para obtener todos los usuarios
     */
    public void handleGetAllUsers() {
        System.out.println("\n--- GET /api/users ---");
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
    }

    /**
     * Simula una petición GET para obtener un usuario por ID
     */
    public void handleGetUserById(Long id) {
        System.out.println("\n--- GET /api/users/" + id + " ---");
        userService.getUserById(id)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Usuario no encontrado")
                );
    }

    /**
     * Simula una petición POST para crear un usuario
     */
    public void handleCreateUser(String name, String email) {
        System.out.println("\n--- POST /api/users ---");
        User newUser = userService.createUser(name, email);
        System.out.println("Usuario creado: " + newUser);
    }

    /**
     * Simula una petición DELETE para eliminar un usuario
     */
    public void handleDeleteUser(Long id) {
        System.out.println("\n--- DELETE /api/users/" + id + " ---");
        userService.deleteUser(id);
        System.out.println("Usuario eliminado con ID: " + id);
    }

    /**
     * Simula una petición GET para obtener información del usuario
     */
    public void handleGetUserInfo(Long id) {
        System.out.println("\n--- GET /api/users/" + id + "/info ---");
        String info = userService.getUserInfo(id);
        System.out.println(info);
    }
}
