package com.gammat.microservice.repository;

import com.gammat.microservice.domain.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alejandro on 23/05/17.
 */
@Repository
public interface TestRepository extends CrudRepository<Test, Long> {

}
