package backend.translation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.common.Pair;
import backend.common.QueryResult;

@Service
public class TranslationService {
	
	private static Properties translationMapper = new Properties();
	private static Hashtable<String, String> reversedTranslationMapper = new Hashtable<String, String>();
	
	static {
		try {
			translationMapper.load(TranslationController.class.getResourceAsStream("translationMapper.properties"));
			for (Object key : translationMapper.keySet()) {
				reversedTranslationMapper.put((String)translationMapper.get(key), (String)key);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@Autowired
	TranslationRepository translationRepository;
	
	public Map translate(String keysString) throws JsonParseException, JsonMappingException, IOException {
		// 将JSON转换为HashMap
		HashMap<String, String> keys =
		        new ObjectMapper().readValue(keysString, HashMap.class);
		
		// 将要翻译的值的名称转换为码表
		List<Pair<String, String>> keysMappedToCodeList = new ArrayList<Pair<String, String>>();
		for (Map.Entry<String, String> entry : keys.entrySet())
		{		    
			String code = translationMapper.getProperty(entry.getKey());
			if(code != null) {
				keysMappedToCodeList.add(new Pair<String, String>(code, entry.getValue()));
			}
		}
		
		// 翻译码表值
		List<CodeTableEntry> codeTableEntries = translationRepository.translate(keysMappedToCodeList);
		
		// 将码表转换回翻译的值的名称
		for (Map.Entry<String, String> key : keys.entrySet())
		{		    
			for(int i = 0; i < codeTableEntries.size(); i++) {
				CodeTableEntry entry = codeTableEntries.get(i);
				if( translationMapper.getProperty( key.getKey() ).equals( entry.getCode() ) &&
					key.getValue().equals( entry.getValue() )) {
					keys.put(key.getKey(), entry.getTranslatedValue());
				}
			}
		}
		
		return keys;		
	}
}
