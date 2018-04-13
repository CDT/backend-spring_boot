package backend.common;

import java.util.HashMap;
import java.util.Map;

public class Utilities {
	public static String convertModelColumnToDatabaseColumn(String sql, HashMap<String, String> mapper) {
		for(Map.Entry m:mapper.entrySet()){  
			sql = sql.replaceAll((String)m.getKey(), (String)m.getValue());  
		}  
		return sql;
	}
}
