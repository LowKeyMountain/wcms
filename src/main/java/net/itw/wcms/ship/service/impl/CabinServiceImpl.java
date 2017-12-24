package net.itw.wcms.ship.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.ship.entity.Cargo;
import net.itw.wcms.ship.exception.TaskException;
import net.itw.wcms.ship.repository.CabinRepository;
import net.itw.wcms.ship.repository.CargoRepository;
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
	private CargoRepository cargoRepository;
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
			map.put("mapping", new HashMap<>());
			map.put("rows", new ArrayList<String>());
			return map;
		}
		int total = page.size();
		
		Map<Integer, String> cargoMap = new HashMap<>();
		List<Cargo> cargos = cargoRepository.findAllByTaskId(taskId);
		for (Cargo cargo : cargos) {
			cargoMap.put(cargo.getId(), cargo.getCargoType());
		}
		
		Map<Integer, Object> mapping = new HashMap<>(); 
		List<Map<String, Object>> list = new ArrayList<>();
		for (Cabin cabin : page) {
			Map<String, Object> data = new HashMap<>();
			data.put("cabinNo", cabin.getCabinNo());
			String cargoName = cargoMap.get(cabin.getCargo().getId());
			data.put("cargoName", StringUtils.isNotEmpty(cargoName)?cargoName:"");
//			data.put("startPosition", cabin.getStartPosition() != null ? cabin.getStartPosition() : "");
//			data.put("endPosition", cabin.getEndPosition() != null ? cabin.getEndPosition() : "");
			data.put("preunloading", cabin.getPreunloading());
			data.put("id", cabin.getId());
			list.add(data);
			
			Map<String, Object> m = new HashMap<>();
			m.put("id", cabin.getId());
			m.put("cabinNo", cabin.getCabinNo());
			mapping.put(cabin.getCabinNo(), m);
		}
		
		map.put("cargos", cargoMap);
		map.put("mapping", mapping);
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
	public int updateCabin(Cabin cabin, User operator) throws Exception {
		try {
			cabin.setUpdateTime(new Date());
			cabin.setUpdateUser(operator.getUserName());
			Cabin po = cabinRepository.getOne(cabin.getId());
			if (po != null) {
				cabin.setStatus(po.getStatus());
				cabin.setRemarks(po.getRemarks());
				cabin.setClearTime(po.getClearTime());
			}
			List<Cabin> entities = new ArrayList<>();
			Cabin nextCabin = getCabinByCargoIdAndCabinNo(cabin.getCargo().getId(), cabin.getCabinNo() + 1);
			if (nextCabin != null) {
				nextCabin.setStartPosition(cabin.getEndPosition());
				entities.add(nextCabin);
			}
			entities.add(cabin);
			cabinRepository.save(entities);
//			cabinRepository.saveAndFlush(cabin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ConstantUtil.SuccessInt;
	}

	@Override
	public MessageOption delete(Cabin cabin, User operator) {
		return null;
	}
	
	/**
	 * 查询
	 * @param cargoId
	 * @param cabinNo
	 * @return
	 */
	public Cabin getCabinByCargoIdAndCabinNo(Integer cargoId, Integer cabinNo) {
		return cabinRepository.getCabinByCargoIdAndCabinNo(cargoId, cabinNo);
	}
	
}
