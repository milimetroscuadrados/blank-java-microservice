package com.gammat.microservice.controller;

import com.gammat.microservice.domain.Test;
import com.gammat.microservice.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by alejandro on 23/05/17.
 */
@RestController
@Api(value="microservice",description = "Microservice")
public class TestController {

    @Autowired
    private TestService testService;

    @ApiOperation(value = "Obtener un Test por su id", response = Test.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Obtuvo el test exitosamente"),
            @ApiResponse(code = 401, message = "No está autorizado a ver el recurso"),
            @ApiResponse(code = 403, message = "Tiene prohibido el acceso al recurso"),
            @ApiResponse(code = 404, message = "No se puede encontrar el recurso")
    })
    @RequestMapping(path = "/{id:^[0-9]*$}", method = RequestMethod.GET)
    public Test getById(@PathVariable Long id) {
        return testService.findById(id);
    }


    @ApiOperation(value = "Obtener todos los tests", response = Test.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Obtuvo los tests exitosamente"),
            @ApiResponse(code = 401, message = "No está autorizado a ver el recurso"),
            @ApiResponse(code = 403, message = "Tiene prohibido el acceso al recurso"),
            @ApiResponse(code = 404, message = "No se puede encontrar el recurso")
    })
    @RequestMapping(path = "/getAll", method = RequestMethod.GET)
    public List<Test> getAll() {
        return StreamSupport.stream(testService.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Actualizar test", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El test fue actualizado exitosamente"),
            @ApiResponse(code = 201, message = "El test fue creado exitosamente"),
            @ApiResponse(code = 401, message = "No está autorizado a ver el recurso"),
            @ApiResponse(code = 403, message = "Tiene prohibido el acceso al recurso"),
            @ApiResponse(code = 404, message = "No se puede encontrar el recurso"),
            @ApiResponse(code = 500, message = "El test a actualizar no existe")
    })
    @RequestMapping(path = "/{id:^[0-9]*$}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @Valid @RequestBody Test test) {
        testService.update(id, test);
    }

    @ApiOperation(value = "Crear nuevo test", response = Test.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "El test fue creado exitosamente"),
            @ApiResponse(code = 401, message = "No está autorizado a ver el recurso"),
            @ApiResponse(code = 403, message = "Tiene prohibido el acceso al recurso"),
            @ApiResponse(code = 404, message = "No se puede encontrar el recurso"),
            @ApiResponse(code = 500, message = "Error interno al crear test")
    })
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Test store(@Valid @RequestBody Test test) {
        return testService.save(test);
    }

    @ApiOperation(value = "Eliminar test", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "El test fue eliminado exitosamente"),
            @ApiResponse(code = 401, message = "No está autorizado a ver el recurso"),
            @ApiResponse(code = 403, message = "Tiene prohibido el acceso al recurso"),
            @ApiResponse(code = 404, message = "No se puede encontrar el recurso")
    })
    @RequestMapping(path = "/{id:^[0-9]*$}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Long id) {
         testService.delete(id);
    }

}
