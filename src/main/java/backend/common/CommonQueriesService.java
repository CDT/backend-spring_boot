package backend.common;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import backend.Application;
import backend.common.QueryResult;

@Service
public class CommonQueriesService {

	@Autowired
	CommonQueriesRepository commonQueriesRepository;

	private static Properties fieldToCodeTable = new Properties();

	static {
		try {
			fieldToCodeTable.load(CommonQueriesService.class.getResourceAsStream("FieldToCodeTableMapping.properties"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDisplay(String fieldName, String fieldValue) {
		
		String codeTable = fieldToCodeTable.getProperty(fieldName);
		
		if (codeTable == null) {
			return null;
		}
		
		return commonQueriesRepository.translateCodeTable(codeTable, fieldValue);
	}
}
