package backend.common;

import java.util.List;
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
public class CommonQueriesRepository {
	
	@Autowired
	JdbcTemplate jdbc;
	
	private static final Logger log = LoggerFactory.getLogger(CommonQueriesRepository.class);
	
	public String translateCodeTable(String codeTable, String value) {
		String sql = 
				"select t2.item_name \r\n" + 
				"from bds.bds_code_table t1, bds.bds_code_table_item t2\r\n" + 
				"where t1.id = t2.t_id\r\n" + 
				"and t1.standard_code = '" + codeTable + "'\r\n" + 
				"and t2.item_value = '" + value + "'";
		
		log.info("Ready to execute: \n" + sql);		
		
		List<String> translated = jdbc.query(
                sql, new Object[] {},
                (rs, rowNum) -> {
                	return rs.getString("item_name");
                }
          	);
		
		return translated.size() == 0 ? null : translated.get(0);
	}
	
}
