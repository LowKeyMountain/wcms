package net.itw.wcms.common.log.repository;

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

import net.itw.wcms.common.log.entity.SystemLog;
import net.itw.wcms.toolkit.DateTimeUtils;

public interface SystemLogRepository extends JpaRepository<SystemLog, Integer>, JpaSpecificationExecutor<SystemLog>,
		PagingAndSortingRepository<SystemLog, Integer> {

	SystemLog getSystemLogByUids(String uids);

	default Page<SystemLog> findAllByParams(Pageable pageable, Map<String, String> params) {
		return this.findAll(new Specification<SystemLog>() {
			public Predicate toPredicate(Root<SystemLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				List<Predicate> predicates = new ArrayList<Predicate>();
				String startDate = params.get("startDate") == null ? "" : params.get("startDate");
				String endDate = params.get("endDate") == null ? "" : params.get("endDate");
				String workPlatform = params.get("workPlatform") == null ? "" : params.get("workPlatform");
				String inputUserId = params.get("inputUserId") == null ? "" : params.get("inputUserId");

				if (StringUtils.isNotEmpty(workPlatform)) {
					predicates.add(cb.like(root.get("workPlatform"), workPlatform));
				}
				if (StringUtils.isNotEmpty(inputUserId)) {
					predicates.add(cb.like(root.get("inputUserId"), inputUserId));
				}
				if (StringUtils.isNotEmpty(startDate)) {
					predicates.add(cb.greaterThanOrEqualTo(root.get("operationTime"),
							DateTimeUtils.strDateTime2Date(startDate)));
				}
				if (StringUtils.isNotEmpty(endDate)) {
					predicates.add(
							cb.lessThanOrEqualTo(root.get("operationTime"), DateTimeUtils.strDateTime2Date(endDate)));
				}

				predicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
				query.where(predicate);
				// 添加排序的功能
				query.orderBy(cb.desc(root.get("operationTime")));
				return query.getRestriction();
			}
		}, pageable);
	}

}
