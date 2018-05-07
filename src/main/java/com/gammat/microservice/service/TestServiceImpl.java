package com.gammat.microservice.service;

import com.gammat.microservice.domain.Test;
import com.gammat.microservice.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by alejandro on 23/05/17.
 */
@Service
public class TestServiceImpl implements TestService {

   // private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestRepository repository;

    @Override
    public Iterable<Test> findAll() {
        return repository.findAll();
    }

    @Override
    public Test findById(Long id) {
        return repository.findOne(id);
    }

    @Override
    public Test save(Test test) {
        return repository.save(test);
    }

    @Override
    public void update(Long id, Test update) {
        repository.save(update);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
