package backend.patients;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import backend.common.QueryResult;
import backend.common.Utilities;
import backend.patients.entity.Patient;

@Repository
public class PatientRepository {
	
	@Autowired
	JdbcTemplate jdbc;
	
	private static final Logger log = LoggerFactory.getLogger(PatientRepository.class);
	
	/* See: 
	 * https://stackoverflow.com/questions/45239828/oracle-sql-pagination-with-total-pages-or-total-entries/45240659#45240659
	 */
	public QueryResult getPatients(String ID, String name, boolean isFuzzy, int page, int per_page, String filter, String sort) {
		String sqlTemplate = 
				"SELECT res.* \r\n" + 
				// "       CEIL(total_num_rows/$pagesize) total_num_pages\r\n" + 
				"FROM   (SELECT o.*,\r\n" + 
				"               row_number() OVER ($orderby) rn,\r\n" + 
				"               COUNT(*) OVER () total_num_rows\r\n" + 
				"        FROM UUM.UUM_USER o\r\n" + 
				"        WHERE (user_name like '%$filter%' or people_name like '%$filter%' or mobile like '%$filter%')) res\r\n" + 
				"WHERE  rn BETWEEN ($pagenumber - 1) * $pagesize + 1 AND $pagenumber * $pagesize";
		
		if(sort != null && sort.length() > 0) {
			sqlTemplate = sqlTemplate.replaceAll("\\$orderby", 
					"ORDER BY " + Utilities.convertModelColumnToDatabaseColumn(sort.replaceAll("\\|", " "), Patient.columnMapper));
		} else {
			sqlTemplate = sqlTemplate.replaceAll("\\$orderby", "ORDER BY USER_NAME ASC");
		}
		
		String sql = sqlTemplate.replaceAll("\\$pagenumber", Integer.toString(page))
				   				.replaceAll("\\$pagesize", Integer.toString(per_page))
								.replaceAll("\\$filter", filter);
		
		log.info("Ready to execute: \n" + sql);		
		
		AtomicInteger total = new AtomicInteger(-1);
		List<Patient> employees = jdbc.query(
                sql, new Object[] {},
                (rs, rowNum) -> {
                	Patient patient = new Patient(                
                		rs.getString("people_identifier"), 
                		rs.getString("people_name"),
                		rs.getString("mobile"),
                		rs.getString("ID_NO"),
                		rs.getString("user_password"));
                	if(total.get() == -1) { total.set(rs.getInt("total_num_rows")); }
                	return employee;
                }
          	);
		
		return new QueryResult(total.get(), employees);
	}
	
}
