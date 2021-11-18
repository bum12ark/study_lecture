package com.example.catalogservice.contorller;

import com.example.catalogservice.entity.Catalog;
import com.example.catalogservice.service.CatalogService;
import com.example.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@Slf4j @RequiredArgsConstructor
public class CatalogController {

    private final Environment environment;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s",
                environment.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity getUsers() {
        Iterable<Catalog> findCatalogs = catalogService.getAllCatalogs();

        List<ResponseCatalog> results = new ArrayList<>();
        findCatalogs.forEach(catalog -> {
            results.add(new ResponseCatalog(catalog.getProductId(), catalog.getProductName(),
                    catalog.getUnitPrice(), catalog.getStock(), catalog.getCreatedAt()));
        });

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

}
