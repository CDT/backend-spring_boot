package backend.employees;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public List<Employee> getEmployees(String ID, String name, boolean isFuzzy) {
		return employeeRepository.getEmployees(ID, name, isFuzzy);
	}
}
