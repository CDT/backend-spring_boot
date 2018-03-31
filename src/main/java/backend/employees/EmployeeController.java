package backend.employees;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;;

@RestController
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;
	
    @RequestMapping("/employees")
    public List<Employee> greeting(
    		@RequestParam(name="id", required=false) String ID,
    		@RequestParam(name="name", required=false) String name,
    		@RequestParam(name="isFuzzy", defaultValue="false") boolean isFuzzy
    		) {
    	log.info("called /employees, querying...");
    	List<Employee> employees = employeeService.getEmployees(ID, name, isFuzzy);
    	//employees.forEach(customer -> log.info(customer.toString()));
    	log.info("query finished, returned " + employees.size() + " records.");
        return employees;
    }
}