package net.itw.wcms.ship.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.repository.CabinRepository;
import net.itw.wcms.ship.service.ICabinService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;

/**
 * 船舱业务实现类
 * 
 * @author Michael 16 Dec 2017 18:03:52
 */
@Service
@Transactional
public class CabinServiceImpl implements ICabinService {

	@Autowired
	private IUserService userService;
	@Autowired
	private CabinRepository cabinRepository;
	@Override
	public int createCabin(Cabin cabin, User operator) {
		cabin.setUpdateUser(operator.getUserName());
		cabin.setUpdateTime(new Date());
		cabinRepository.saveAndFlush(cabin);
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Map<String, Object> getCabinList(Pageable pageable, Integer taskId, Map<String, String> params) {
		Map<String, Object> map = new HashMap<>();
		List<Cabin> page = findAllByTaskId(taskId);
		if (page == null || page.size() == 0) {
			map.put("total", 0);
			map.put("rows", new ArrayList<String>());
			return map;
		}
		int total = page.size();
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (Cabin cabin : page) {
			Map<String, Object> data = new HashMap<>();
			data.put("cabinNo", cabin.getCabinNo());
			data.put("cargoId", cabin.getCargoId());
			data.put("startPosition", cabin.getStartPosition());
			data.put("endPosition", cabin.getEndPosition());
			data.put("preunloading", cabin.getPreunloading());
			data.put("id", cabin.getId());
			list.add(data);
		}
		
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	public List<Cabin> findAllByTaskId(Integer taskId) {
		return cabinRepository.findAllByTaskId(taskId);
	}
	
	public Cabin findOne(Integer id) {
		return cabinRepository.findOne(id);
	}
	
	@Override
	public int updateCabin(Cabin cabin, User operator) {
		cabin.setUpdateUser(operator.getUserName());
		cabin.setUpdateTime(new Date());
		cabinRepository.saveAndFlush(cabin);
		return ConstantUtil.SuccessInt;
	}

	@Override
	public MessageOption delete(Cabin cabin, User operator) {
		return null;
	}
	
}
