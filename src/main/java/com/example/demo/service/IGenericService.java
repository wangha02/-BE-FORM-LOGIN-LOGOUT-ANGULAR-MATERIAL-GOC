package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenericService<T> {
    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    T save(T t);

    void deleteById(Long id);

    T findById(Long id);
}
