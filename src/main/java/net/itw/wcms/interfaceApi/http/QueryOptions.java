package net.itw.wcms.interfaceApi.http;

public class QueryOptions {
	public String order;
	public String sort;
	public Integer page;
	private Integer total;
	public Integer rows;
	public String searchString;
	public String replacement;
	public Object[] args;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * 设置查询入参值。
	 * 
	 * @param param
	 * @param value
	 */
	public void setQueryInParamValue(String param, Object value) {
		if ("order".equalsIgnoreCase(param)) {
			order = value.toString();
		} else if ("sort".equalsIgnoreCase(param)) {
			sort = value.toString();
		} else if ("page".equalsIgnoreCase(param)) {
			page = Integer.valueOf(value.toString());
		} else if ("rows".equalsIgnoreCase(param)) {
			rows = Integer.valueOf(value.toString());
		}
	}

}
