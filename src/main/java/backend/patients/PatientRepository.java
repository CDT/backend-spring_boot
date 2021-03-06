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
	
	public QueryResult getVisitsByOrg(
			String patientType,            // 患者类型：1.本科室患者 2.转出患者 3.转入患者 4.会诊患者 5.跨科协作患者，必填 
			String org,                    // 科室代码，必填
			String patientStatus,          // 患者状态，PTS0015，必填
			String medicalTeamId,          // 诊疗小组ID
			String doctorID,               // 医生工号，该患者对应诊疗小组中的三级医生
			Date dateOfAdmissionStart,     // 入院日期，起始值
			Date dateOfAdmissionEnd,       // 入院日期，结束值
			Date dateOfDischargeStart,     // 出院日期，起始值
			Date dateOfDischargeEnd,	   // 出院日期，结束值
			int page, int per_page, String filter, String sort // 分页、搜索、排序等参数
			) {
		String sqlTemplate = 
				"select * from (" +
				"select * from pts.pai_visit t\r\n" + 
				"$where " +
				"and t.current_status = '" + patientStatus + "' \r\n" + 
				"and (t.current_ward = '" + org + "' or t.current_dept = '" + org + "')\r\n" + 
				"order by bed_code asc" +
				"), \r\n" + 
				"(select count(*) as total_num_rows from  pca.pca_patient_info $where)\r\n" +
				"where rn BETWEEN ($pagenumber - 1) * $pagesize + 1 AND $pagenumber * $pagesize";
		
		if(filter != null && filter.length() > 0) {
			sqlTemplate = sqlTemplate.replaceAll("\\$where", 
					"where (patient_name like '%\\$filter%' or \r\n" +
					"patient_id like '%\\$filter%' or \r\n" + 
					"bed_code like '%\\$filter%')");
		} else {
			sqlTemplate = sqlTemplate.replaceAll("\\$where", "");
		}
		
		String sql = sqlTemplate.replaceAll("\\$pagenumber", Integer.toString(page))
				   				.replaceAll("\\$pagesize", Integer.toString(per_page))
								.replaceAll("\\$filter", filter);
		
		log.info("Ready to execute: \n" + sql);		
		
		AtomicInteger total = new AtomicInteger(-1);
		List<InpatientVisit> patients = jdbc.query(
                sql, new Object[] {},
                (rs, rowNum) -> {
                	
                	InpatientVisit inpVisit = new InpatientVisit(                
            			rs.getString("patient_id"), 
            			rs.getLong("visit_id"), 
            			rs.getLong("pai_visit_id"),
            			rs.getString("bed_code"),
            			rs.getLong("team_id"),
            			rs.getString("cost_type"), 
            			rs.getString("current_dept"), 
            			rs.getString("current_ward"),
            			rs.getString("current_status")
                	);
                	if(total.get() == -1) { total.set(rs.getInt("total_num_rows")); 
                	}                	
                	return inpVisit;
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
	
	public List<? extends Object> getVisits(String ID, String type, int start, int end) {
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
                				rs.getLong("visit_id"),
                				rs.getLong("pai_visit_id"),
                				rs.getString("bed_code"),
                				rs.getLong("team_id"),
                				rs.getString("cost_type"),
                				rs.getString("current_dept"),
                				rs.getString("current_ward"),
                				rs.getString("current_status")
                				); 
                	} else {
                		//TODO outpatient visit
                		return null;
                	}
                }
          	);
		
		return visits;
	}
	
	public List<? extends Object> getVisits(String org, String type, 
			Date admissionDateStart, Date admissionDateEnd, Date dischargeDateStart, Date dischargeDateEnd) {
		StringBuilder sql = new StringBuilder("select * from pts.pai_visit t where 1=1");
		if (type.equals("outpatient")) {
			// TODO
		} else if (type.equals("inpatient")) { // 在科
			sql.append(" and t.current_status = '4'");
		} else if (type.equals("transferredOut")) {
			// TODO
		} else if (type.equals("transferredIn")) {
			// TODO
		} else if (type.equals("discharged")) {
			// TODO
		} else if (type.equals("consultationApplied")) {
			// TODO
		} else if (type.equals("consultationInvited")) {
			// TODO
		}
		
		if(org != null && org.length() > 0) {
			sql.append(" and (t.current_dept = '" + org + "' or t.current_ward = '" + org + "')");
		}
		
		sql.append(" order by t.bed_code asc");
		
		log.info("Ready to execute: \n" + sql);		
				
		List<? extends Object> visits = jdbc.query(
                sql.toString(), new Object[] {},
                (rs, rowNum) -> {
                	if (type.equals("inpatient")) {
                		return new InpatientVisit(
                				rs.getString("patient_id"), 
                				rs.getLong("visit_id"),
                				rs.getLong("pai_visit_id"),
                				rs.getString("bed_code"),
                				rs.getLong("team_id"),
                				rs.getString("cost_type"),
                				rs.getString("current_dept"),
                				rs.getString("current_ward"),
                				rs.getString("current_status")
                				); 
                	} else {
                		//TODO outpatient visit
                		return null;
                	}
                }
          	);
		
		return visits;
	}
	
}
