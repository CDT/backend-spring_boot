package backend.employees;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
	
	@Autowired
	JdbcTemplate jdbc;	
	
	public List<Employee> getEmployees(String ID, String name, boolean isFuzzy) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM UUM.UUM_USER");
		if( ID != null && !ID.equals("") ) { 
			sqlBuilder.append(" WHERE PEOPLE_IDENTIFIER ")
					  .append( isFuzzy ? "LIKE '%" + ID + "%'" : "= '" + ID + "'"); 
		}
		if( name != null && !name.equals("") ) { 
			sqlBuilder.append(" WHERE PEOPLE_NAME ")
					  .append( isFuzzy ? "LIKE '%" + name + "%'" : "= '" + name + "'"); 
		}
		
		List<Employee> employees = jdbc.query(
                sqlBuilder.toString(), new Object[] {},
                (rs, rowNum) -> new Employee(rs.getString("PEOPLE_IDENTIFIER"), rs.getString("PEOPLE_NAME"))
          	);
		
		return employees;
	}
	
}
