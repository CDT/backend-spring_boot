package backend.patients;

import java.util.Date;
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
import backend.patients.entity.InpatientVisit;
import backend.patients.entity.PatientCard;

@RestController
public class PatientController {

	private static final Logger log = LoggerFactory.getLogger(PatientController.class);
	
	@Autowired
	PatientService patientService;
	
    @RequestMapping("/patients")
    public ResponseForTable patients(
    		@RequestParam(name="id", required=false) String ID,
    		@RequestParam(name="name", required=false) String name,
    		@RequestParam(name="is_fuzzy", defaultValue="true") boolean isFuzzy,
    		@RequestParam(name="page", defaultValue="1") int page,
    		@RequestParam(name="per_page", defaultValue="20") int perPage,
    		@RequestParam(name="filter", defaultValue="") String filter,
    		@RequestParam(name="sort", defaultValue="") String sort,
    		HttpServletRequest request
    		) {
    	log.info("request from: " + request.getRequestURI());
    	log.info("called /patients, querying...");
    	
    	QueryResult queryResult = patientService.getPatients(ID, name, isFuzzy, page, perPage, filter, sort);
    	List<PatientCard> patients = (List<PatientCard>) queryResult.getData();
    	int total = queryResult.getTotal();
    	
    	//employees.forEach(customer -> log.info(customer.toString()));
    	log.info("query finished, returned " + patients.size() + " of total " + total + " records.");
    	
        return ResponseForTable.buildResponse(patients, request.getRequestURI(), page, perPage, total);
    }
    
    @RequestMapping("/cardTrack")
    public String cardTrack(
    		@RequestParam(name="id", required=false) String ID,
    		HttpServletRequest request
    		) {
    	log.info("request from: " + request.getRequestURI());
    	log.info("called /cardTrack, querying...");
    	
    	String cardTrack = patientService.getCardTrack(ID);
    	
    	log.info("query finished, returned cardTrack " + cardTrack);
    	return cardTrack;
   
    }

    @RequestMapping("/visits")
    public List<? extends Object> visits(
    		// org不为空，id为空时，表示查询某个科室的所有就诊记录
    		@RequestParam(name="org", required=false) String org,
    		@RequestParam(name="id", required=false) String ID,   
    		/* 当org为空时，type分为门诊、住院、全部；
    		 * 当org不为空时，type分为门诊、当前在科、已转他科、他科转入、本科出院、本科邀请会诊、本科参与会诊 */
    		@RequestParam(name="type", required=false) String type, 
    		@RequestParam(name="status", required=false) String status, // 患者在院状态PTS0015
    		// 次数范围，如“1-3”表示第1到3次就诊记录
    		@RequestParam(name="range", required=false) String range,
    		// 入院、出院时间范围
    		@RequestParam(name="admissionDateStart", required=false) Date admissionDateStart,
    		@RequestParam(name="admissionDateEnd", required=false) Date admissionDateEnd,
    		@RequestParam(name="dischargeDateStart", required=false) Date dischargeDateStart,
    		@RequestParam(name="dischargeDateEnd", required=false) Date dischargeDateEnd,
    		HttpServletRequest request
    		) {
    	log.info("request from: " + request.getRequestURI());
    	log.info("called /visit, querying...");
    	
    	List<? extends Object> visits = 
    			patientService.getVisits(org, ID, type.toLowerCase(), range, 
    					admissionDateStart, admissionDateEnd, dischargeDateStart, dischargeDateEnd);
    	    	
    	log.info("query finished, returned " + visits.size() + " records");
    	return visits;
   
    }
    
}