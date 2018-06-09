package backend.patients;

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

    @RequestMapping("/visit")
    public List<? extends Object> visit(
    		@RequestParam(name="id", required=true) String ID,
    		@RequestParam(name="type", required=true) String type,
    		@RequestParam(name="range", required=true) String range,
    		HttpServletRequest request
    		) {
    	log.info("request from: " + request.getRequestURI());
    	log.info("called /visit, querying...");
    	
    	List<? extends Object> visits = patientService.getVisit(ID, type.toLowerCase(), range);
    	    	
    	log.info("query finished, returned " + visits.size() + " records");
    	return visits;
   
    }
    
}