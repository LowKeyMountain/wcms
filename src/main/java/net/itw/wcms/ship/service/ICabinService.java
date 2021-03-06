package net.itw.wcms.ship.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import net.itw.wcms.ship.entity.Cabin;
import net.itw.wcms.toolkit.MessageOption;
import net.itw.wcms.x27.entity.User;

/**
 * 
 * 船舶货物业务类接口
 * 
 * @author Michael 16 Dec 2017 09:21:38
 */
public interface ICabinService {
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	Cabin findOne(Integer id);
	
	/**
	 * 查询
	 * @param cargoId
	 * @param cabinNo
	 * @return
	 */
	Cabin getCabinByCargoIdAndCabinNo(Integer cargoId, Integer cabinNo);
	
	/**
	 * 新增
	 * @param cabin
	 * @param operator
	 * @return
	 */
	int createCabin(Cabin cabin, User operator);

	/**
	 * 返回舱位信息列表
	 * 
	 * @param pageable
	 * @param taskId
	 * @param params
	 * @return
	 */
	Map<String, Object> getCabinPositionList(Pageable pageable, Integer taskId, Map<String, String> params);
	
	/**
	 * 获取列表
	 * 
	 * @param pageable
	 * @param taskId
	 * @param params
	 * @return
	 */
	Map<String, Object> getCabinList(Pageable pageable, Integer taskId, Map<String, String> params);
	
	/**
	 * 根据任务ID获取船舱编号
	 * @param taskId
	 * @return
	 */
	List<Cabin> findAllByTaskId(Integer taskId);
	
	/**
	 * 更新
	 * @param cabin
	 * @param operator
	 * @return
	 * @throws Exception 
	 */
	int updatePreunloadingAndCargo(Cabin cabin, User operator) throws Exception;
	
	/**
	 * 删除
	 * @param cabin
	 * @param operator
	 * @return
	 */
	MessageOption delete(Cabin cabin, User operator);

}
