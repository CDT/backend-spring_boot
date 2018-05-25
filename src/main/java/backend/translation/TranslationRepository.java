package backend.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import backend.common.QueryResult;
import backend.common.Utilities;
import backend.employees.Employee;

@Repository
public class TranslationRepository {
	
	@Autowired
	JdbcTemplate jdbc;
	
	private static final Logger log = LoggerFactory.getLogger(TranslationRepository.class);
	

	public List<CodeTableEntry> translate(Map<String, String> map) {
		String sql = "select t1.standard_code, t2.item_value, t2.item_name\r\n" + 
				"  from bds.bds_code_table t1, bds.bds_code_table_item t2\r\n" + 
				" where t1.id = t2.t_id\r\n" + 
				"   and ($condition)";
		
		StringBuilder conditionString = new StringBuilder("");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			conditionString.append("(t1.standard_code=\'" + entry.getKey() + "\' and t2.item_value = \'" + entry.getValue() + "\') or ");
		}
		if(conditionString.toString().endsWith("or ")) {
			conditionString.setLength(conditionString.length() - 3);
		}
		sql = sql.replaceAll("\\$conditions", conditionString.equals("") ? "1=1" : conditionString.toString());
		
		log.info("Ready to execute: \n" + sql);		
		
		List<CodeTableEntry> translationResults = jdbc.query(
            sql, new Object[] {},
            (rs, rowNum) -> {
            	return new CodeTableEntry(                
            		rs.getString("STANDARD_CODE"), 
            		rs.getString("ITEM_VALUE"),
            		rs.getString("ITEM_NAME")
            	);            	
            }
      	);
		
		return translationResults;
	}
	
}
