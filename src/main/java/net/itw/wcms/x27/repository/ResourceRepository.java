package net.itw.wcms.x27.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

import net.itw.wcms.toolkit.lang.Int32;
import net.itw.wcms.x27.entity.Resource;
import net.itw.wcms.x27.utils.StringUtil;

public interface ResourceRepository extends JpaRepository<Resource, Integer>, JpaSpecificationExecutor<Resource>,
		PagingAndSortingRepository<Resource, Integer> {

	Resource getResourceById(Integer id);

	@Query(value = "select * from X27_Resources r where r.id in (select rr.resource_id from X27_JOIN_ROLE_RESOURCE rr where rr.role_id = ?1)", nativeQuery = true)
	List<Resource> getResourceListByRoleId(Integer roleId);
	

	default Page<Resource> findAllSupportQuery(Pageable pageable, Map<String, String> params) {
		return this.findAll(new Specification<Resource>() {
			public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
						} else if (Boolean.class.equals(class1)) {
							if (Arrays.asList("是,男,管理员".split(",")).contains(sSearch)) {
								predicates.add(cb.equal(root.get(s).as(Boolean.class), true));
							} else if (Arrays.asList("否,女,普通".split(",")).contains(sSearch)) {
								predicates.add(cb.equal(root.get(s).as(Boolean.class), false));
							}
						}
					}
					predicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}
				return predicate;
			}
		}, pageable);
	}

	@Modifying(clearAutomatically = true)
	@Query("update Resource r set " + " r.name =:name " + ", r.remark=:remark " + ", r.updateDate=:updateDate "
			+ ", r.updatePerson=:updatePerson " + "where r.id =:id")
	void updateResourceById(@Param("id") Integer id, @Param("name") String name, @Param("remark") String remark,
			@Param("updateDate") Date updateDate, @Param("updatePerson") String updatePerson);
	
	@Modifying(clearAutomatically = true)
	@Query("delete from Resource u where u.id =:id")
	Integer deleteById(@Param("id") Integer id);
	
}
