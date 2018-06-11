package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /**
     * Creates the Compensation for an employee on the basis of an employee's id.
     *
     * @param employeeId Employee Id
     * @return Compensation
     */
    @PostMapping("/compensation/{id}")
    public Compensation create(@PathVariable(value = "id") String employeeId) {
        LOG.debug("Received compensation create request for employeeId [{}]", employeeId);

        return compensationService.create(employeeId);
    }

    /**
     * Reads the compensation for an employee on the basis of an employee's id.
     *
     * @param employeeId Employee Id
     * @return Compensation
     */
    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable(value = "id") String employeeId) {
        LOG.debug("Received employee read request for employeeId [{}]", employeeId);

        return compensationService.read(employeeId);
    }

}
