package backend.translation;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
public class TranslationController {

	private static final Logger log = LoggerFactory.getLogger(TranslationController.class);
	
	@Autowired
	TranslationService translationService;
	
    @RequestMapping("/translation")
    public Object translations(
    		@RequestParam(name="entries", required=true) String entries, 
    		HttpServletRequest request    		
    		) throws JsonParseException, JsonMappingException, IOException {
    	log.info("request from: " + request.getRequestURI());
    	log.info("called /translation, querying...");
    	
    	Map queryResult = translationService.translate(entries);
    	
    	log.info("query finished, returned " + queryResult.size() + " records.");
    	
        return queryResult;
    }
    
}