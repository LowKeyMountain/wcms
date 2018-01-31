package net.itw.wcms.ship.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.entity.Task;
import net.itw.wcms.ship.entity.UnloaderAll;
import net.itw.wcms.toolkit.DateTimeUtils;
import net.itw.wcms.toolkit.lang.Int32;
import net.itw.wcms.x27.utils.StringUtil;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task>,
		PagingAndSortingRepository<Task, Integer> {

	List<Task> getTaskByStatus(Integer status);

	Task getTaskById(Integer id);

	@Modifying(clearAutomatically = true)
	@Query("update Task t set " + "t.status = :status " + ", t.updateTime=:updateTime " + ", t.updateUser=:updateUser "
			+ "where t.id =:id")
	void updateStatusById(@Param("id") Integer id, @Param("status") String status, @Param("updateTime") Date updateTime,
			@Param("updateUser") String updateUser);

	default Page<Task> findAllByStatus(Pageable pageable, Integer status) {
		return this.findAll(new Specification<Task>() {
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> shipStatus = root.get("status");
				return cb.equal(shipStatus, status);
			}
		}, pageable);
	}

	default Page<Task> findAllByParams(Pageable pageable,  Map<String, String> params) {
		return this.findAll(new Specification<Task>() {
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				List<Predicate> predicates = new ArrayList<Predicate>();
/*				String cmsId = params.get("cmsId");
				String startPosition = (params.get("startPosition") == null ? "0" : params.get("startPosition"));
				String endPosition = (params.get("endPosition") == null ? "0" : params.get("endPosition"));
				String startDate = (params.get("startDate") == null ? "" : params.get("startDate"));
				String endDate = (params.get("endDate") == null ? "" : params.get("endDate"));*/
				String status = (params.get("status") == null ? "" : params.get("status"));

/*				if(StringUtils.isNotEmpty(startPosition)) {
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
				}*/
				if(StringUtils.isNotEmpty(status)) {
					predicates.add(cb.equal(root.get("status"), status));
				}
				predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));				
		        query.where(predicate);  
		        //添加排序的功能  
		        query.orderBy(cb.desc(root.get("updateTime")));
		        return query.getRestriction();  
				//return predicate;
			}
		}, pageable);
	}	

	default Page<Task> findAll(Pageable pageable, Map<String, String> params) {
		return this.findAll(new Specification<Task>() {
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				String sSearch = params.get("sSearch");
				if (StringUtils.isBlank(sSearch)) {
				} else {
					sSearch = StringUtil.encode(sSearch);
					String sColumns = params.get("sColumns");
					List<Predicate> predicates = new ArrayList<Predicate>();
					for (String s : Arrays.asList(sColumns.split(","))) {
						if (StringUtils.isBlank(s)) {
							continue;
						}
						Class<?> class1 = root.get(s).getJavaType();
						if (String.class.equals(class1)) {
							predicates.add(cb.like(root.get(s).as(String.class), "%" + sSearch + "%"));
						} else if (Integer.class.equals(class1)) {
							MutableInt mi = new MutableInt();
							if (Int32.tryParse(sSearch, mi)) {
								predicates.add(cb.equal(root.get(s).as(Integer.class), mi.intValue()));
							}
						}
					}
					predicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
				return predicate;
			}
		}, pageable);
	}

}
