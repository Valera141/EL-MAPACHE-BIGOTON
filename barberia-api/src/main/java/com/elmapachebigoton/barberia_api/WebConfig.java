package com.elmapachebigoton.barberia_api;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 1. Importa la clase Paths
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        
        // 2. Obtiene la ruta del directorio actual donde se está ejecutando el proyecto
        // (Ej: C:\Users\corla\Desktop\EL-MAPACHE-BIGOTON)
        String currentDir = System.getProperty("user.dir");

        // 3. Construye la RUTA ABSOLUTA a tu carpeta 'images'
        // Esto crea una ruta como: file:///C:/Users/corla/Desktop/EL-MAPACHE-BIGOTON/barberia-api/images/
        String imagesPath = Paths.get(currentDir, "barberia-api", "images").toUri().toString();
        
        // 4. Construye la RUTA ABSOLUTA a tu carpeta 'uploads'
        String uploadsPath = Paths.get(currentDir, "barberia-api", "uploads").toUri().toString();

        // 5. Registra los manejadores con las rutas absolutas
        
        // Sirve la carpeta /uploads/
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadsPath);
        
        // Sirve la carpeta /images/ (la que contiene barbero1.jpeg, etc.)
        registry.addResourceHandler("/images/**")
                .addResourceLocations(imagesPath);
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) { 
        registry.addMapping("/**") // Permite todos los endpoints
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500") // tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}