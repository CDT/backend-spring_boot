package backend.employees;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.common.QueryResult;
import backend.common.ResponseForTable;

@RestController
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeService employeeService;
	
    @RequestMapping("/employees")
    public ResponseForTable employees(
    		@RequestParam(name="id", required=false) String ID,
    		@RequestParam(name="name", required=false) String name,
    		@RequestParam(name="is_fuzzy", defaultValue="true") boolean isFuzzy,
    		@RequestParam(name="page", defaultValue="1") int page,
    		@RequestParam(name="per_page", defaultValue="15") int perPage,
    		@RequestParam(name="filter", defaultValue="") String filter,
    		@RequestParam(name="sort", defaultValue="") String sort,
    		HttpServletRequest request
    		) {
    	log.info("request from: " + request.getRequestURI());
    	log.info("called /employees, querying...");
    	
    	QueryResult queryResult = employeeService.getEmployees(ID, name, isFuzzy, page, perPage, filter, sort);
    	List<Employee> employees = (List<Employee>) queryResult.getData();
    	int total = queryResult.getTotal();
    	
    	//employees.forEach(customer -> log.info(customer.toString()));
    	log.info("query finished, returned " + employees.size() + " of total " + total + "records.");
    	
        return ResponseForTable.buildResponse(employees, request.getRequestURI(), page, perPage, total);
    }
    
}