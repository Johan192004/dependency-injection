package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SPRING BOOT - Controlador REST
 * 
 * @RestController: Combina @Controller + @ResponseBody
 * Indica que esta clase maneja peticiones HTTP y devuelve JSON/XML
 * 
 * INYECCIÓN DE DEPENDENCIAS EN CADENA:
 * UserController → necesita → UserService → necesita → UserRepository
 * 
 * Spring resuelve toda esta cadena automáticamente:
 * 1. Crea UserRepository
 * 2. Lo inyecta en UserService
 * 3. Inyecta UserService en UserController
 * 
 * Todo esto sin escribir una sola línea de código de creación manual
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    /**
     * Constructor con inyección de dependencias
     * Spring inyecta automáticamente UserService
     */
    public UserController(UserService userService) {
        this.userService = userService;
        System.out.println("✓ UserController creado por Spring con UserService inyectado");
    }

    /**
     * GET /api/users - Obtener todos los usuarios
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * GET /api/users/{id} - Obtener un usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/users - Crear un nuevo usuario
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user.getName(), user.getEmail());
    }

    /**
     * DELETE /api/users/{id} - Eliminar un usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/users/{id}/info - Obtener información del usuario
     */
    @GetMapping("/{id}/info")
    public String getUserInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }
}
