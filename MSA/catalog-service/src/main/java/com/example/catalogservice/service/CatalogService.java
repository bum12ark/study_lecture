package com.example.catalogservice.service;

import com.example.catalogservice.entity.Catalog;

public interface CatalogService {
    Iterable<Catalog> getAllCatalogs();
}
