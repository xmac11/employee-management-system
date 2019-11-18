package com.team.ghana.employee;

import com.team.ghana.enums.Endpoint;
import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import com.team.ghana.searchEmployeeStrategy.SearchEmployeeStrategy;
import com.team.ghana.searchEmployeeStrategy.SearchEmployeeStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private SearchEmployeeStrategyFactory strategyFactory;

    public GenericResponse getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAll();

        return new GenericResponse<>(employeeMapper.mapEmployeeListToEmployeeResponseList(employeeList));
    }

    public GenericResponse getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);

        return employee == null ?
                new GenericResponse<>(
                        new CustomError(
                                0,
                                "Bad Input",
                                "Employee with ID: " + employeeId + " doesn't exist.") ) :
                new GenericResponse<>(employeeMapper.mapEmployeeToEmployeeResponse(employee));
    }

    public GenericResponse getEmployeesBySearchCriteria(String searchCriteria, Long id) {
        if(!enumContains(searchCriteria)) {
            return new GenericResponse(new CustomError(0, "Error", searchCriteria + " is not valid. Use " + Arrays.toString(Endpoint.values()).toLowerCase()));
        }

        List<Employee> allEmployees = employeeRepository.findAll();
        SearchEmployeeStrategy strategy = strategyFactory.makeStrategy(searchCriteria);

        if(!strategy.idExists(id)) {
            return new GenericResponse(new CustomError(0, "Error", searchCriteria + " with Id " + id + " does not exist."));
        }

        List<Employee> employees = strategy.execute(allEmployees, id);
        List<EmployeeResponse> employeeResponses = employeeMapper.mapEmployeeListToEmployeeResponseList(employees);

        return new GenericResponse<>(employeeResponses);
    }

    private boolean enumContains(String searchCriteria) {
        for(Endpoint endpoint: Endpoint.values()){
            if(String.valueOf(endpoint).equalsIgnoreCase(searchCriteria))
                return true;
        }

        return false;
    }
}
