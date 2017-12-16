package net.itw.wcms.ship.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import net.itw.wcms.ship.entity.Cargo;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;

/**
 * 
 * 船舶货物业务类接口
 * 
 * @author Michael 16 Dec 2017 09:21:38
 */
public interface ICargoService {
	
	/**
	 * 查询货物
	 * @param id
	 * @return
	 */
	Cargo findOne(Integer id);
	
	/**
	 * 新增货物
	 * 
	 * @param cargo
	 * @param operator
	 * @return
	 */
	int createCargo(Cargo cargo, User operator);

	/**
	 * 获取货物列表
	 * 
	 * @param pageable
	 * @param taskId
	 * @param params
	 * @return
	 */
	Map<String, Object> getCargoList(Pageable pageable, Integer taskId, Map<String, String> params);
	
	/**
	 * 更新货物
	 * 
	 * @param cargo
	 * @param operator
	 * @return
	 */
	int updateCargo(Cargo cargo, User operator);
	
	/**
	 * 删除货物
	 * @param cargo
	 * @param operator
	 * @return
	 */
	MessageOption delete(Cargo cargo, User operator);

}
