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
	
	public List<? extends Object> getVisit(String ID, String type, int numberOfVisit) {
		return patientRepository.getVisit(ID, type, numberOfVisit);
	}
}
