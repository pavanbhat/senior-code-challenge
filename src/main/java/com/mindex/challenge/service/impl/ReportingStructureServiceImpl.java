package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Obtains the reporting structure for the given employee using the employee's id.
     *
     * @param employeeId Employee Id
     * @return Reporting Structure object
     */
    @Override
    public ReportingStructure getReportingStructure(final String employeeId) {
        // Queue maintains the order for popping employees for the reporting structure
        Queue<String> queue = new LinkedList<>();
        // Set maintains a list of employees that were previously visited to avoid circular connections.
        Set<String> visited = new HashSet<>();
        queue.add(employeeId);
        visited.add(employeeId);
        // Operates until all the employees connected are traversed atleast once
        while (!queue.isEmpty()) {
            Employee employee = employeeService.read(queue.poll());
            // Check to see if the employee is not empty
            if (employee == null) {
                throw new RuntimeException("Invalid employeeId: " + employeeId);
            }
            // Orders the queue based on the direct reporting employees traversed
            if (employee.getDirectReports() != null) {
                for (Employee emp : employee.getDirectReports()) {
                    if (!visited.contains(emp.getEmployeeId())) {
                        visited.add(emp.getEmployeeId());
                        queue.add(emp.getEmployeeId());
                    }
                }
            }
        }

        // Removes the employee himself and adds the remaining visited employees as the total number of reporting employees
        int numberOfReports = visited.size() - 1;
        
        // Creates a reporting structure for the given employee
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmpolyee(employeeService.read(employeeId));
        reportingStructure.setNumberOfReports(numberOfReports);


        return reportingStructure;
    }
}
