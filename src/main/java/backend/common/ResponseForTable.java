package backend.common;

import java.util.List;

import backend.employees.Employee;

public class ResponseForTable {
	// 总记录数
	private int total;
	// 每页记录数
	private int per_page;
	// 当前页码（本次Response对应的页码）
	private int current_page;
	// 最后一页的页码
	private int last_page;
	// 下一页的请求地址
	private String next_page_url;
	// 上一页的请求地址
	private String prev_page_url;
	// 起始页码
	private int from;
	// 结束页码
	private int to;
	// 所有记录数据
	private List<? extends Object> data;
	
	public static ResponseForTable buildResponse(List<? extends Object> data, String uri, int currentPage, int perPage, int total) {
    	ResponseForTable resp = new ResponseForTable();

    	int lastPage = total / perPage + (total % perPage == 0 ? 1 : 0);
    	
    	resp.setData(data);
    	resp.setPer_page(perPage);
    	resp.setCurrent_page(currentPage);
    	resp.setLast_page(lastPage);
    	resp.setTotal(total);
    	
    	resp.setFrom(perPage * (currentPage - 1) + 1);
    	resp.setTo(perPage * currentPage);
    	
    	resp.setPrev_page_url(currentPage == 1 ? null : uri + "?page=" + (currentPage - 1) );
    	resp.setNext_page_url(currentPage == lastPage ? null : uri + "?page=" + (currentPage + 1));
    	
    	return resp;
    }
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPer_page() {
		return per_page;
	}
	public void setPer_page(int per_page) {
		this.per_page = per_page;
	}
	public int getCurrent_page() {
		return current_page;
	}
	public void setCurrent_page(int current_page) {
		this.current_page = current_page;
	}
	public int getLast_page() {
		return last_page;
	}
	public void setLast_page(int last_page) {
		this.last_page = last_page;
	}
	public String getNext_page_url() {
		return next_page_url;
	}
	public void setNext_page_url(String next_page_url) {
		this.next_page_url = next_page_url;
	}
	public String getPrev_page_url() {
		return prev_page_url;
	}
	public void setPrev_page_url(String prev_page_url) {
		this.prev_page_url = prev_page_url;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public List<? extends Object> getData() {
		return data;
	}
	public void setData(List<? extends Object> data) {
		this.data = data;
	}
	
}