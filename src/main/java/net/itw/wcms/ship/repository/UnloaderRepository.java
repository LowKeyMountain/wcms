package net.itw.wcms.ship.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.UnloaderAll;
import net.itw.wcms.toolkit.DateTimeUtils;

public interface UnloaderRepository extends JpaRepository<UnloaderAll, Integer>, JpaSpecificationExecutor<UnloaderAll>,
		PagingAndSortingRepository<UnloaderAll, Integer> {

//	@Query(value = "SELECT * FROM tab_unloader_1 t WHERE t.Cmsid = ?1",countQuery = "SELECT count(*) FROM tab_unloader_1 t WHERE t.Cmsid = ?1",nativeQuery = true)
//	Page<Unloader1> findByCmsid(String cmsId, Pageable pageable);
	
	default Page<UnloaderAll> findAllByParams(Pageable pageable,  Map<String, String> params) {
		return this.findAll(new Specification<UnloaderAll>() {
			public Predicate toPredicate(Root<UnloaderAll> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				List<Predicate> predicates = new ArrayList<Predicate>();
				String cmsId = params.get("cmsId");
				String startPosition = (params.get("startPosition") == null ? "0" : params.get("startPosition"));
				String endPosition = (params.get("endPosition") == null ? "0" : params.get("endPosition"));
				String startDate = (params.get("startDate") == null ? "" : params.get("startDate"));
				String endDate = (params.get("endDate") == null ? "" : params.get("endDate"));
				String operationType = (params.get("operationType") == null ? "" : params.get("operationType"));

				if(StringUtils.isNotEmpty(startPosition)) {
					predicates.add(cb.ge(root.get("unloaderMove"),Float.parseFloat(startPosition)));
				}
				if(StringUtils.isNotEmpty(endPosition)) {
					predicates.add(cb.le(root.get("unloaderMove"),Float.parseFloat(endPosition)));
				}

				if(StringUtils.isNotEmpty(startDate)) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("time"), DateTimeUtils.strDateTime2Date(startDate)));
				}
				if(StringUtils.isNotEmpty(endDate)) {
					predicates.add(cb.lessThanOrEqualTo(root.get("time"), DateTimeUtils.strDateTime2Date(endDate)));
				}
				if(StringUtils.isNotEmpty(operationType)) {
					predicates.add(cb.equal(root.get("operationType"), operationType));
				}
				if("1".equals(cmsId)) {
					predicates.add(cb.equal(root.get("cmsId"), "ABB_GSU_1"));
				} else if ("2".equals(cmsId)) {
					predicates.add(cb.equal(root.get("cmsId"), "ABB_GSU_2"));	
				} else if ("3".equals(cmsId)) {
					predicates.add(cb.equal(root.get("cmsId"), "ABB_GSU_3"));	
				} else if ("4".equals(cmsId)) {
					predicates.add(cb.equal(root.get("cmsId"), "ABB_GSU_4"));
				} else if ("5".equals(cmsId)) {
					predicates.add(cb.equal(root.get("cmsId"), "ABB_GSU_5"));
				} else if ("6".equals(cmsId)) {
					predicates.add(cb.equal(root.get("cmsId"), "ABB_GSU_6"));
				}
				else {
				}
				predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));				
		        query.where(predicate);  
		        //添加排序的功能  
		        query.orderBy(cb.desc(root.get("time")));
		        return query.getRestriction();  
				//return predicate;
			}
		}, pageable);
	}	
}
