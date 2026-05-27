package com.recrutement.gestion_rh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Crée un chemin absolu propre vers le dossier "uploads" à la racine de ton projet Windows
        String uploadPath = Paths.get("uploads").toAbsolutePath().toUri().toString();

        // Fait le pont entre l'URL http://localhost:8080/uploads/... et le dossier de ton PC
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}