package backend.common;

import java.util.List;

public class QueryResult {
	// 总记录数
	private int total;
	// 所有记录数据
	private List<? extends Object> data;
	
	public QueryResult(int total, List<? extends Object> data) {
		this.total = total;
		this.data = data;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public List<? extends Object> getData() {
		return data;
	}
	public void setData(List<? extends Object> data) {
		this.data = data;
	}
	
}