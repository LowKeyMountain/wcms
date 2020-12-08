package net.itw.wcms.ship.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Unloader;
import net.itw.wcms.ship.entity.UnloaderAll;
import net.itw.wcms.ship.repository.UnloaderRepository;
import net.itw.wcms.ship.service.IUnloaderService;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.toolkit.sql.SqlMap;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.StringUtil;


@Service
@Transactional
public class UnloaderServiceImpl implements IUnloaderService {
	
	@Autowired
	private UnloaderRepository unloaderRepository;
	
	@Autowired
	private  JdbcTemplate jdbcTemplate;
	
	public Page<UnloaderAll> findAll(Pageable pageable, Map<String , String > params)
	{
		return unloaderRepository.findAllByParams(pageable, params);
	}

	public Page<UnloaderAll> findHistoryAllParam(Pageable pageable, Map<String , String > params)
	{
		return unloaderRepository.findHistoryAllParam(pageable, params);
	}
	
	public List<UnloaderAll> findListByParams(Map<String , String > params)
	{
		return unloaderRepository.findListByParams(params);
	}
	
	@Override
	public String getUnloaderList(Pageable pageable, Map<String, String> params) {
		Page<UnloaderAll> page = findAll(pageable, params);
		JSONObject jo=null;
		if (page == null || page.getTotalPages() == 0) {
			return "{\"total\":0,\"rows\":[],}";
		}
		long total = page.getTotalElements();
		JSONArray jsonArray = new JSONArray();
		for (UnloaderAll t : page) {
			jo = new JSONObject();
			jo.put("id", t.getId());
			jo.put("Cmsid", t.getCmsId());
			jo.put("operationType", t.getOperationType());
			
			jo.put("time", t.getTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getTime()));
			jo.put("pushTime", t.getPushTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getPushTime()));
			jo.put("direction", t.getDirection());
			jo.put("unloaderMove", t.getUnloaderMove());
			jo.put("OneTask", t.getOneTask());
			jsonArray.add(jo);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", total);
		jsonObject.put("rows", jsonArray);
		return jsonObject.toString();
	}
	
	@Override
	public String getHistoryParamDatas(Pageable pageable, Map<String, String> params) {
		Page<UnloaderAll> page = findHistoryAllParam(pageable, params);
		JSONObject jo=null;
		if (page == null || page.getTotalPages() == 0) {
			return "{\"total\":0,\"rows\":[],}";
		}
		long total = page.getTotalElements();
		JSONArray jsonArray = new JSONArray();
		for (UnloaderAll t : page) {
			jo = new JSONObject();
			jo.put("id", t.getId());
			jo.put("Cmsid", t.getCmsId());
			jo.put("operationType", t.getOperationType());
			
			jo.put("time", t.getTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getTime()));
			jo.put("pushTime", t.getPushTime() == null ? "" : DateTimeUtils.date2StrDateTime(t.getPushTime()));
			jo.put("deliveryRate", t.getDeliveryRate());
			jo.put("doumenOpeningDegree", t.getDoumenOpeningDegree());
			jo.put("hopperLoad", t.getHopperLoad());
			jsonArray.add(jo);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", total);
		jsonObject.put("rows", jsonArray);
		return jsonObject.toString();
	}
	
	private static SqlMap sqlMap;

	static {
		try {
			sqlMap = SqlMap.load(SqlMap.class.getResourceAsStream("./queryInterfaceConfig.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取卸船机参数数据
	 * 
	 * @param pageable
	 * @param params
	 * @return
	 */
	public String getParamListDatas(Pageable pageable, Map<String, String> params) {
		String sql = sqlMap.getSql("doGetUnloaderPlcParamDetailList");
		List<Map<String, Object>> page = this.jdbcTemplate.queryForList(sql.toString());
		if (page == null || page.size() == 0) {
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"iTotalRecords\":%d,\"iTotalDisplayRecords\":%d,\"aaData\":[", page.size(), page.size()));
		int i = 0;
		for (Map<String, Object> t : page) {
			if (i != 0)
				sb.append(",");
			sb.append("[");
			sb.append("\"").append(t.get("unloaderId")).append("\"");
			sb.append(",\"").append(t.get("deliveryRate")).append("\"");
			sb.append(",\"").append(t.get("doumenOpeningDegree")).append("\"");
			sb.append(",\"").append(t.get("hopperLoad")).append("\"");
			Object o = t.get("updateTime");
			String updateTimeStr = "";
			if (o != null && o instanceof Timestamp) {
				updateTimeStr = DateTimeUtils.date2StrDateTime((Date) o);
			}
			sb.append(",\"").append(updateTimeStr).append("\"");
			String id = t.get("unloaderId") + "";
			String cmsid = "ABB_GSU_" + id.substring(1);
			sql = " select TIMESTAMPDIFF(MINUTE,t.Time,now()) MinuteDifference from tab_unloader_all t where t.operationType = '2' and t.Cmsid = ? ";
			Map<String, Object> map = this.jdbcTemplate.queryForMap(sql, cmsid);
			long minuteDifference = (Long)map.get("MinuteDifference");
			if (minuteDifference > 10) {
				sb.append(",\"<font color='red'>").append("异常").append("</font>\"");
			} else {
				sb.append(",\"").append("在线").append("\"");
			}
			sb.append("]");
			
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}
	
	@Override
	public int addUnloader(UnloaderAll unloader, String tablename) throws Exception {
		int result = 0;
		try {
			String sql = "insert into " + tablename
					+ " (Time, Cmsid, PushTime, OneTask, direction, unloaderMove, "
					+ "operationType) values(?,?,?,?,?,?,?) ";
			Object[] args = new Object[] {unloader.getTime(), unloader.getCmsId(),
					unloader.getPushTime(),unloader.getOneTask(), 0, 
					unloader.getUnloaderMove(), unloader.getOperationType() };
			result = jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} 
		return result;
	}
	
	@Override
	public int addUnloaderParam(UnloaderAll unloader, String tablename) throws Exception {
		int result = 0;
		try {
			String sql = "insert into " + tablename + " (Time, Cmsid, PushTime, OneTask, direction, unloaderMove, "
					+ "operationType, deliveryRate, doumenOpeningDegree, hopperLoad) values(?,?,?,?,?,?,?,?,?,?) ";
			Object[] args = new Object[] { unloader.getTime(), unloader.getCmsId(), unloader.getPushTime(),
					unloader.getOneTask(), 0, unloader.getUnloaderMove(), unloader.getOperationType(),
					unloader.getDeliveryRate(), unloader.getDoumenOpeningDegree(), unloader.getHopperLoad() };
			result = jdbcTemplate.update(sql, args);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return result;
	}
	
	@Override
	public void createUnloader(Unloader unloader, User operator) throws Exception {
//		try {
//			Integer id = -1;
//			String tableName = "";
//			String sql = "insert into " + tableName
//					+ " (id, Time, Cmsid, PushTime, OneTask, direction, unloaderMove, "
//					+ "operationType) values(?,?,?,?,?,?,?,?) ";
//			Object[] args = new Object[] { id, unloader.getTime(), unloader.getCmsId(),
//					unloader.getTime(),unloader.getOneTask(), unloader.getDirection(), 
//					unloader.getUnloaderMove(), unloader.getOperationType() };
//			this.getJdbcTemplate().update(sql, args);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
}