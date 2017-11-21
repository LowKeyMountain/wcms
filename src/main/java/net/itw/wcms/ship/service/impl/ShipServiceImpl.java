package net.itw.wcms.ship.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.repository.ShipRepository;
import net.itw.wcms.ship.service.ShipService;
import net.itw.wcms.toolkit.security.PasswordUtils;
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.repository.UserRepository;
import net.itw.wcms.x27.service.IUserService;
import net.itw.wcms.x27.utils.ConstantUtil;
import net.itw.wcms.x27.utils.PageUtils;
import net.itw.wcms.x27.utils.StringUtil;


@Service
@Transactional
public class ShipServiceImpl implements ShipService {
	
    @Autowired
    private ShipRepository shipRepository;
    

	
	public Page<Ship> findAllByStatus(Pageable pageable, String status)
	{
		return shipRepository.findAllByStatus(pageable, status);
	}
	
	@Override
	public String getShipList(Ship ship, Pageable pageable, String status)
	{
		Page<Ship> page = findAllByStatus(pageable, status);		
		if(page==null || page.getTotalPages()==0)
		{
			return "{\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
		}
		
		int total = page.getTotalPages();
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("{\"total\":%d,\"rows\":[", total, total));
		int i= 0;
		for(Ship sp:page)
		{
			if(i != 0) sb.append(",");
			addDataRow(sb,sp);
			i++;
		}
		sb.append("]}");
		return sb.toString();
	}

	private void addDataRow(StringBuilder sb,Ship sp)
	{
		sb.append("{\"id\":");
		sb.append(sp.getId()).append(",\"shipName\":\"")
		.append(sp.getShipName()).append("\"")
		.append(",\"updateUser\":\"").append(sp.getUpdateUser()).append("\"")
		.append(",\"updateTime\":\"").append(sp.getUpdateTime()).append("\"");
		//.append(",operation:\"").append("&nbsp;&nbsp;")
		//.append("\"");
		sb.append("}");
	}

	@Override
	public Ship getShipDetail(Integer shipId) {
		// TODO Auto-generated method stub
		return shipRepository.getShipById(shipId);
	}

}
