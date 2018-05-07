package com.gammat.microservice.service;

import com.gammat.microservice.domain.Test;

/**
 * Created by alejandro on 23/05/17.
 */
public interface TestService {

    Iterable<Test> findAll();

    Test findById(Long id);

    Test save(Test test);

    void update(Long id, Test update);

    void delete(Long id);

}
