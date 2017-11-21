package net.itw.wcms.ship.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.itw.wcms.ship.entity.Ship;

/**
 * Description: 船舶表相关操作
 *
 * @author sparking 2017-11-17 下午7:40:10
 *
 */
public interface ShipService {


	/**
	 * 根据查询条件，返回DataTables控件需要的Json数据格式
	 * 
	 * @param pageable
	 * @return
	 */
	String getShipList(Ship ship, Pageable pageable, String status);
	
	/**
	 * 返回船舶基本信息
	 * 
	 * @param shipId
	 * @return
	 */
	Ship getShipDetail(Integer shipId);
	
}
