package backend.translation;

public class CodeTableEntry {
	
	private String code;
	private String value;
	private String translatedValue;
	
	public CodeTableEntry(String code, String value, String translatedValue) {
		this.code = code;
		this.value = value;
		this.translatedValue = translatedValue;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTranslatedValue() {
		return translatedValue;
	}
	public void setTranslatedValue(String translatedValue) {
		this.translatedValue = translatedValue;
	}
	
	
}
