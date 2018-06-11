package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingStructureController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    /**
     * Obtains the reporting structure for a given employee i.e. along with total number of reporting employees
     *
     * @param employeeId Employee Id
     * @return ReportingStructure
     */
    @PostMapping("/reporting-structure/{id}")
    public ReportingStructure getReportingStructure(@PathVariable(value = "id") String employeeId) {
        LOG.debug("Received reporting structure request for employeeId [{}]", employeeId);

        return reportingStructureService.getReportingStructure(employeeId);
    }

}
