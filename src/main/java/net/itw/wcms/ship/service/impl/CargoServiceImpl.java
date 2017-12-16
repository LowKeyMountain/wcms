package net.itw.wcms.ship.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.entity.Cargo;
import net.itw.wcms.ship.repository.CabinRepository;
import net.itw.wcms.ship.repository.CargoRepository;
import net.itw.wcms.ship.service.ICargoService;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.exception.X27Exception;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;

/**
 * 船舶货物业务实现类
 * 
 * @author Michael 16 Dec 2017 09:25:27
 */
@Service
@Transactional
public class CargoServiceImpl implements ICargoService {

	@Autowired
	private IUserService userService;
	@Autowired
	private CargoRepository cargoRepository;
	@Autowired
	private CabinRepository cabinRepository;
	@Override
	public int createCargo(Cargo cargo, User operator) {
		cargo.setUpdateUser(operator.getUserName());
		cargo.setUpdateTime(new Date());
		cargoRepository.saveAndFlush(cargo);
		return ConstantUtil.SuccessInt;
	}

	@Override
	public Map<String, Object> getCargoList(Pageable pageable, Integer taskId, Map<String, String> params) {
		Map<String, Object> map = new HashMap<>();
		List<Cargo> page = findAllByTaskId(taskId);
		if (page == null || page.size() == 0) {
			map.put("total", 0);
			map.put("rows", new ArrayList<String>());
			return map;
		}
		int total = page.size();
//		List<Cargo> list = new ArrayList<>();
//		for (Cargo t : page) {
//			list.add(t);
//		}
		
		List<Map<String, Object>> list = new ArrayList<>();
		for (Cargo cargo : page) {
			Map<String, Object> data = new HashMap<>();
			data.put("cargoType", cargo.getCargoType());
			data.put("cargoCategory", cargo.getCargoCategory());
			data.put("loadingPort", cargo.getLoadingPort());
			data.put("quality", cargo.getQuality());
			data.put("moisture", cargo.getMoisture());
			data.put("owner", cargo.getCargoOwner());
			data.put("stowage", cargo.getStowage());
			data.put("id", cargo.getId());
			list.add(data);
		}
		
		map.put("total", total);
		map.put("rows", list);
		return map;
	}

	public Page<Cargo> findAllByTaskId(Pageable pageable, Integer taskId) {
		return cargoRepository.findAllByTaskId(pageable, taskId);
	}
	
	public List<Cargo> findAllByTaskId(Integer taskId) {
		return cargoRepository.findAllByTaskId(taskId);
	}
	
	public Cargo findOne(Integer id) {
		return cargoRepository.findOne(id);
	}
	
	@Override
	public int updateCargo(Cargo cargo, User operator) {
		cargo.setUpdateUser(operator.getUserName());
		cargo.setUpdateTime(new Date());
		cargoRepository.saveAndFlush(cargo);
		return ConstantUtil.SuccessInt;
	}

	@Override
	public MessageOption delete(Cargo cargo, User operator) {
		MessageOption mo = new MessageOption(ConstantUtil.SuccessInt, "操作成功！");
		try {
			// 检查货物是否关联船舱
			List<Cabin> cabins = cabinRepository.getCabinByCargoId(cargo.getId());
			if (cabins != null && cabins.size() > 0) {
				throw new X27Exception("操作失败：该货物已被船舱关联，不能删除！");
			}
			cargoRepository.delete(cargo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mo;
	}
	
}
