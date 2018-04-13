package backend.employees;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import backend.common.QueryResult;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public QueryResult getEmployees(String ID, String name, boolean isFuzzy, int page, int per_page, String filter, String sort) {
		return employeeRepository.getEmployees(ID, name, isFuzzy, page, per_page, filter, sort);
	}
}
