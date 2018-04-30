package backend.patients;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import backend.common.QueryResult;
import backend.common.Utilities;
import backend.patients.entity.PatientCard;

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
				"select t.*, count(*) over()\r\n" + 
				"  from (select rownum rn,\r\n" + 
				"               patient_id,\r\n" + 
				"               name,\r\n" + 
				"               sex_code,\r\n" + 
				"               date_of_birth,\r\n" + 
				"               phone_number1,\r\n" + 
				"               phone_number2,\r\n" + 
				"               create_time\r\n" + 
				"          from pca.pca_patient_info\r\n" + 
				"         where (name like '%$filter%' or id_number like '%$filter%' or\r\n" + 
				"               patient_id like '%$filter%' or\r\n" + 
				"               phone_number1 like '%$filter%' or phone_number2 like '%%')) t\r\n" + 
				" where rn BETWEEN ($pagenumber - 1) * $pagesize + 1 AND $pagenumber * $pagesize;
				"SELECT res.* \r\n" + 
						// "       CEIL(total_num_rows/$pagesize) total_num_pages\r\n" + 
						"FROM   (SELECT o.*,\r\n" + 
						"               rownum rn,\r\n" + 
						"               COUNT(*) OVER () total_num_rows\r\n" + 
						"        FROM PCA.PCA_PATIENT_INFO o\r\n" + 
						"        WHERE (name like '%$filter%' or id_number like '%$filter%' " +
						"		 or patient_id like '%$filter%' or phone_number1 like '%$filter%' " +
						"		 or phone_number2 like '%$filter%')) res\r\n" + 
						"WHERE  rn BETWEEN ($pagenumber - 1) * $pagesize + 1 AND $pagenumber * $pagesize";
		
		String sql = sqlTemplate.replaceAll("\\$pagenumber", Integer.toString(page))
				   				.replaceAll("\\$pagesize", Integer.toString(per_page))
								.replaceAll("\\$filter", filter);
		
		log.info("Ready to execute: \n" + sql);		
		
		AtomicInteger total = new AtomicInteger(-1);
		List<PatientCard> patients = jdbc.query(
                sql, new Object[] {},
                (rs, rowNum) -> {             
                	PatientCard patient = new PatientCard(                
                		rs.getString("patient_id"), 
                		rs.getString("name"),
                		rs.getString("gender"),
                		rs.getDate("date_of_birth"),
                		rs.getString("phone_number1"),
                		rs.getString("phone_number2"),
                		rs.getDate("create_time"));
                	if(total.get() == -1) { total.set(rs.getInt("total_num_rows")); 
                	}                	
                	return patient;
                }
          	);
		
		return new QueryResult(total.get(), patients);
	}
	
}
