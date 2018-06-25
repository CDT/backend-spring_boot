package backend.common;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.Application;
import backend.common.QueryResult;
import backend.common.ResponseForTable;
import backend.employees.Employee;
import oracle.jdbc.pool.OracleDataSource;

@RestController
public class CommonQueriesController {

	private static final Logger log = LoggerFactory.getLogger(CommonQueriesController.class);
	
	@Autowired
	CommonQueriesService commonQueriesService;
	
    @RequestMapping("/getDisplay")
    public String getDisplay(
    		@RequestParam(name="fieldName", required=true) String fieldName,
    		@RequestParam(name="fieldValue", required=true) String fieldValue,
    		HttpServletRequest request
    		) {
    	log.info("request from: " + request.getRequestURI());
    	log.info("called /getDisplay, querying...");
    	
    	String display = commonQueriesService.getDisplay(fieldName, fieldValue);
    	
    	log.info("query finished, returned " + display + " for " + fieldName + ":" + fieldValue);
    	
        return display;
    }
    
}