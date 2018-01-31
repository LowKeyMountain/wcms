package net.itw.wcms.ship.service.impl;

import java.util.Map;

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
import net.itw.wcms.x27.entity.User;


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
	public int addUnloader(UnloaderAll unloader, String tablename) throws Exception {
		int result = 0;
		try {
			String sql = "insert into " + tablename
					+ " (Time, Cmsid, PushTime, OneTask, direction, unloaderMove, "
					+ "operationType) values(?,?,?,?,?,?,?) ";
			Object[] args = new Object[] {unloader.getTime(), unloader.getCmsId(),
					unloader.getPushTime(),unloader.getOneTask(), unloader.getDirection(), 
					unloader.getUnloaderMove(), unloader.getOperationType() };
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