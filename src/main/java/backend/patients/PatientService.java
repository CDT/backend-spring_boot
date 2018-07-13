package backend.patients;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import backend.common.QueryResult;

@Service
public class PatientService {

	@Autowired
	PatientRepository patientRepository;
	
	public QueryResult getPatients(String ID, String name, boolean isFuzzy, int page, int per_page, String filter, String sort) {
		return patientRepository.getPatients(ID, name, isFuzzy, page, per_page, filter, sort);
	}
	
	public String getCardTrack(String ID) {
		return patientRepository.getCardTrack(ID);
	}
	
	public List<? extends Object> getVisits(
			String org, String ID, String type, String range,
			Date admissionDateStart, Date admissionDateEnd, Date dischargeDateStart, Date dischargeDateEnd) {
		
		if( org == null || org.length() == 0) {
		// 1. 查询单患者的就诊记录
		// 解析range，start表示起始次数，end表示结束次数：
			int start = 1;
			int end = -1;
			if (range.equals("latest")) {
				start = -1;
			} else if (range == null || range.equals("all") || range.equals("")) { // 默认情况
				// 保持默认值；
			} else {
				start = Integer.valueOf(range.split("-")[0]);
				end = range.contains("-") ? Integer.valueOf(range.split("-")[1]) : start;
			}
			return patientRepository.getVisits(ID, type, start, end);
		} else if( ID == null || ID.length() == 0 ) {
		// 2. 查询科室所有患者的就诊记录
			return patientRepository.getVisits(org, type, 
					admissionDateStart, admissionDateEnd, dischargeDateStart, dischargeDateEnd);
		} else {
		// 3. 其他情况，TODO
			return null;
		}
	}
}
