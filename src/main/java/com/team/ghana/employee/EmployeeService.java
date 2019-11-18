package com.team.ghana.employee;

import com.team.ghana.errorHandling.CustomError;
import com.team.ghana.errorHandling.GenericResponse;
import com.team.ghana.strategy.SearchEmployeeStrategy;
import com.team.ghana.strategy.SearchEmployeeStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Employee> allEmployees = employeeRepository.findAll();

        SearchEmployeeStrategy strategy = strategyFactory.makeStrategy(searchCriteria);
        List<Employee> employees = strategy.execute(allEmployees, id);
        List<EmployeeResponse> employeeResponses = employeeMapper.mapEmployeeListToEmployeeResponseList(employees);

        return new GenericResponse<>(employeeResponses);
    }
}
