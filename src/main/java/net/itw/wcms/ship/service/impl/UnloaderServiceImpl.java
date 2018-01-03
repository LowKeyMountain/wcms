package net.itw.wcms.ship.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.itw.wcms.ship.entity.Unloader1;
import net.itw.wcms.ship.entity.UnloaderAll;
import net.itw.wcms.ship.repository.UnloaderRepository;
import net.itw.wcms.ship.service.IUnloaderService;
import net.itw.wcms.toolkit.DateTimeUtils;


@Service
@Transactional
public class UnloaderServiceImpl implements IUnloaderService {
	
	@Autowired
	private UnloaderRepository unloaderRepository;

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
	
}
