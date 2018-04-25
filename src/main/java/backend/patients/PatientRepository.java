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
				"SELECT res.*, \r\n" + 
				"       CEIL(total_num_rows/$pagesize) total_num_pages\r\n" + 
				"FROM   (SELECT o.*,\r\n" + 
				"               row_number() OVER ($orderby) rn,\r\n" + 
				"               COUNT(*) OVER () total_num_rows\r\n" + 
				"        FROM (select c.patient_id, c.name, c.sex_code, c.id_number, c.phone_number1, c.phone_number2, c.date_of_birth, c.create_time, b.is_cancel, '%E?;' || a.track_data || '?;' as track\r\n" + 
				"from pca.pca_patient_service_card_info a, pca.pca_media_info b, pca.pca_patient_info c\r\n" + 
				"where a.card_no = b.identity_number and b.patient_id = c.patient_id) o\r\n" + 
				"        WHERE (id_number like '%$filter%' or name like '%$filter%' or patient_id like '%$filter%' or phone_number1 like '%$filter%' or phone_number2 like '%$filter%')) res\r\n" + 
				"WHERE  rn BETWEEN ($pagenumber - 1) * $pagesize + 1 AND $pagenumber * $pagesize";
		
		if(sort != null && sort.length() > 0) {
			sqlTemplate = sqlTemplate.replaceAll("\\$orderby", 
					"ORDER BY " + Utilities.convertModelColumnToDatabaseColumn(sort.replaceAll("\\|", " "), Patient.columnMapper));
		} else {
			sqlTemplate = sqlTemplate.replaceAll("\\$orderby", "ORDER BY PATIENT_ID ASC");
		}
		
		String sql = sqlTemplate.replaceAll("\\$pagenumber", Integer.toString(page))
				   				.replaceAll("\\$pagesize", Integer.toString(per_page))
								.replaceAll("\\$filter", filter);
		
		log.info("Ready to execute: \n" + sql);		
		
		AtomicInteger total = new AtomicInteger(-1);
		List<Patient> patients = jdbc.query(
                sql, new Object[] {},
                (rs, rowNum) -> {             
                	Patient patient = new Patient(                
                		rs.getString("patient_id"), 
                		rs.getString("name"),
                		rs.getString("gender"),
                		rs.getDate("date_of_birth"),
                		rs.getString("phone_number1"),
                		rs.getString("phone_number2"),
                		rs.getDate("create_time"),
                		rs.getString("is_cancel").equals("0") ? false : true, 
                		rs.getString("track"));
                	if(total.get() == -1) { total.set(rs.getInt("total_num_rows")); }
                	return patient;
                }
          	);
		
		return new QueryResult(total.get(), patients);
	}
	
}
