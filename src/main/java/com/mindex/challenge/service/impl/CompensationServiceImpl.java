package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Creates a compensation and associates it with the provided employee id.
     *
     * @param employeeId Employee Id
     * @return Compensation object
     */
    @Override
    public Compensation create(final String employeeId) {
        LOG.debug("Creating compensation with employeeId [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);

        // Check to see if the employee object is not null
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        // Adds the employee to the compensation structure
        Compensation compensation = new Compensation();
        compensation.setEmployee(employee);

        // Randomly computes the salary which is assumed to be a type double in the range 25,000$ to 2,50,000$
        Random random = new Random();
        double randomMin = 25000;
        double randomMax = 250000;
        double salary = randomMin + (randomMax - randomMin) * random.nextDouble();
        compensation.setSalary((double) Math.round(salary * 100) / 100);

        // Adds the effective date as today's date in the below mentioned format.
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        compensation.setEffectiveDate(dateFormat.format(new Date()));

        // insert compensation into the compensation repository
        compensationRepository.insert(compensation);

        return compensation;
    }

    /**
     * Reads the compensation from the compensation repository using employee id
     *
     * @param employeeId Employee Id
     * @return Compensation object
     */
    @Override
    public Compensation read(final String employeeId) {
        LOG.debug("Creating employee with employeeId [{}]", employeeId);
        // Finds the compensation based on the employee id and employee object
        Compensation compensation = compensationRepository.findCompensationByEmployee_EmployeeId(employeeId);

        // check to see if the compensation is not null
        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        return compensation;
    }
}
