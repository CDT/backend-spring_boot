package backend.patients;

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
	
	public List<? extends Object> getVisit(String ID, String type, String range) {
		int start = 1;
		int end = -1;
		if (range.equals("newest")) {
			start = -1;
		} else if (range == null || range.equals("all") || range.equals("")) { // 默认情况
			// 保持默认值；
		} else if (range.equals("-1")) {
			start = end = -1;
		} else {
			start = Integer.valueOf(range.split("-")[0]);
			end = range.contains("-") ? Integer.valueOf(range.split("-")[1]) : start;
		}
		return patientRepository.getVisit(ID, type, start, end);
	}
}
