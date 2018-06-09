package backend.patients;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import backend.common.QueryResult;
import backend.patients.entity.Address;
import backend.patients.entity.InpatientVisit;
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
				"select * from (select rownum rn, t.* from pca.pca_patient_info t $where), \r\n" + 
				"(select count(*) as total_num_rows from  pca.pca_patient_info $where)\r\n" +
				"where rn BETWEEN ($pagenumber - 1) * $pagesize + 1 AND $pagenumber * $pagesize";
//				"where rn BETWEEN ($pagenumber - 1) * $pagesize and $pagenumber * $pagesize"
//				"select *\r\n" + 
//				"  from (select rownum rn, t.*\r\n" + 
//				"  from (select tt1.*, tt2.in_register_date, tt2.current_status, tt2.current_dept, tt2.contact_address,\r\n" +
//				"  		   tt2.bloodtype\r\n" +
//				"          from pca.pca_patient_info tt1 left join\r\n" + 
//				"               pts.pai_visit tt2 on tt1.patient_id = tt2.patient_id, \r\n" + 
//				"               (select t1.patient_id,\r\n" + 
//				"                       max(t2.in_register_date) as max_in_reg_date\r\n" + 
//				"                  from pca.pca_patient_info t1 left join pts.pai_visit t2\r\n" + 
//				"                 on t1.patient_id = t2.patient_id\r\n" + 
//				"                 group by t1.patient_id) tt3\r\n" + 
//				"           where tt2.in_register_date = tt3.max_in_reg_date) t\r\n" + 
//				"           where $where),\r\n" +
//				"		 (select count(*) as total_num_rows from pca.pca_patient_info\r\n" +
//				"         where $where)\r\n" +
//				" where rn BETWEEN ($pagenumber - 1) * $pagesize + 1 AND $pagenumber * $pagesize";
		
		if(filter != null && filter.length() > 0) {
			sqlTemplate = sqlTemplate.replaceAll("\\$where", 
					"where name like '%\\$filter%' or id_number like '%\\$filter%' or \r\n" + 
					"patient_id like '%\\$filter%' or\r\n" + 
					"phone_number1 like '%\\$filter%' or phone_number2 like '%\\$filter%'");
		} else {
			sqlTemplate = sqlTemplate.replaceAll("\\$where", "");
		}
		
		String sql = sqlTemplate.replaceAll("\\$pagenumber", Integer.toString(page))
				   				.replaceAll("\\$pagesize", Integer.toString(per_page))
								.replaceAll("\\$filter", filter);
		
		log.info("Ready to execute: \n" + sql);		
		
		AtomicInteger total = new AtomicInteger(-1);
		List<PatientCard> patients = jdbc.query(
                sql, new Object[] {},
                (rs, rowNum) -> {
                	
                	String phone1 = rs.getString("phone_number1");
                	String phone2 = rs.getString("phone_number2");
                	String phone = 
                			phone1 == null ? 
                			(phone2 == null ? null : phone2) : 
                			(phone2 == null ? phone1 : (phone1.equals(phone2) ? phone1 : phone1 + " 或 " + phone2)); 
                	
                	PatientCard patient = new PatientCard(                
                		rs.getString("patient_id"), 
                		rs.getString("name"),
                		rs.getString("sex_code"),
                		rs.getString("id_number"),
                		rs.getDate("date_of_birth"),
                		phone,
                		rs.getDate("create_time")
                	);
                	if(total.get() == -1) { total.set(rs.getInt("total_num_rows")); 
                	}                	
                	return patient;
                }
          	);
		
		return new QueryResult(total.get(), patients);
	}
	
	public String getCardTrack(String ID) {
		String sql = "select '%E?;' || track_data || '?+E?' as cardTrack\r\n" + 
				"  from pca.pca_patient_service_card_info t\r\n" + 
				" where t.card_no = '" + ID + "'";
		
		log.info("Ready to execute: \n" + sql);		
		
		List<String> cardTracks = jdbc.query(
                sql, new Object[] {},
                (rs, rowNum) -> {              	
                	return rs.getString("cardTrack");
                }
          	);
		
		return cardTracks.isEmpty() ? null : cardTracks.get(0);
	}
	
	public List<? extends Object> getVisit(String ID, String type, int start, int end) {
		StringBuilder sql = new StringBuilder();
		if (type.equals("inpatient")) {
			sql.append("select * from pts.pai_visit t where t.patient_id = '" + ID + "'");
			if(start == -1) {
				sql.append(" and t.visit_id = "
						+ "(select max(visit_id) from pts.pai_visit t\r\n" 
						+ " where t.patient_id = '" + ID + "')");
			} else {
				sql.append(" and t.visit_id >= " + start  
						+ ( end == -1 ? "" : " and t.visit_id <= " + end));
			}					
		} else if (type.equals("outpatient")) {
			// TODO
		}
		
		log.info("Ready to execute: \n" + sql);		
		
		List<? extends Object> visits = jdbc.query(
                sql.toString(), new Object[] {},
                (rs, rowNum) -> {              	
                	if (type.equals("inpatient")) {
                		return new InpatientVisit(
                				rs.getString("patient_id"),
                				rs.getString("visit_id"),
                				rs.getString("pai_visit_id"),
                				rs.getString("cost_type"),
                				rs.getString("current_dept"),
                				rs.getString("current_ward")
                			);
                	} else {
                		return null;
                	}
                }
          	);
		return visits;
	}
	
}
