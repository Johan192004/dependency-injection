package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SPRING BOOT - Aplicación Principal
 * 
 * @SpringBootApplication: Combina tres anotaciones importantes:
 * 1. @Configuration: Indica que esta clase puede definir beans
 * 2. @EnableAutoConfiguration: Configura automáticamente Spring según las dependencias
 * 3. @ComponentScan: Escanea el paquete actual y subpaquetes buscando componentes
 * 
 * CONTENEDOR IoC DE SPRING:
 * - Spring crea y gestiona todos los objetos anotados con @Component, @Service, @Repository, @Controller
 * - Resuelve automáticamente las dependencias entre ellos
 * - Inyecta las dependencias donde sean necesarias
 * 
 * VENTAJAS DE LA INYECCIÓN DE DEPENDENCIAS CON SPRING:
 * ✓ Código desacoplado: Las clases no crean sus propias dependencias
 * ✓ Fácil de testear: Podemos inyectar mocks en las pruebas
 * ✓ Configuración automática: Spring Boot configura todo por nosotros
 * ✓ Gestión del ciclo de vida: Spring crea, configura y destruye los objetos
 * ✓ Singleton por defecto: Una sola instancia compartida (configurable)
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.out.println("=== INICIANDO APLICACIÓN SPRING BOOT ===");
		System.out.println("Spring Boot gestionará automáticamente la creación e inyección de dependencias\n");
		
		SpringApplication.run(DemoApplication.class, args);
		
		System.out.println("\n=== APLICACIÓN INICIADA ===");
		System.out.println("Prueba los endpoints en: http://localhost:8080/api/users");
		System.out.println("- GET    /api/users         → Listar todos los usuarios");
		System.out.println("- GET    /api/users/{id}    → Obtener usuario por ID");
		System.out.println("- GET    /api/users/{id}/info → Información del usuario");
		System.out.println("- POST   /api/users         → Crear nuevo usuario");
		System.out.println("- DELETE /api/users/{id}    → Eliminar usuario");
	}

}
