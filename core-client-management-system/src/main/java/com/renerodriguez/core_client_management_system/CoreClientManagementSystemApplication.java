package com.renerodriguez.core_client_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Clase principal de configuración y arranque de la aplicación Spring Boot.
 * <p>
 * Esta clase inicializa el contexto de la aplicación, activa la autoconfiguración de Spring
 * y habilita el escaneo de componentes dentro del paquete base del sistema de gestión de clientes.
 * </p>
 * * @author Rene Rodriguez
 * @version 1.0
 */

/**
 * Punto de entrada principal para la ejecución de la aplicación.
 * <p>
 * Lanza la aplicación utilizando {@link SpringApplication#run(Class, String...)},
 * lo que inicia el servidor embebido y prepara el entorno de ejecución.
 * </p>
 * * @param args Argumentos de línea de comandos pasados durante el inicio.
 */
@SpringBootApplication
public class CoreClientManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreClientManagementSystemApplication.class, args);

	}

}
