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
import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.utils.StringUtil;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>,
		PagingAndSortingRepository<User, Integer> {

	User getUserByUserName(String userName);
	
	User getUserByUserNameAndPassword(String userName, String password);

	User getUserById(Integer id);

	Page<User> findByIsDeleteFalse(Pageable pageable);

	@Modifying(clearAutomatically = true)
	@Query("delete from User u where u.id =:id")
	Integer deleteById(@Param("id") Integer id);

	@Modifying(clearAutomatically = true)
	@Query("update User u set " + " u.realName =:realName " + ", u.gender=:gender " + ", u.isAdmin=:isAdmin "
			+ ", u.updateDate=:updateDate " + ", u.updatePerson=:updatePerson " + "where u.id =:id")
	void updateRealNameAndGenderAndIsAdminById(@Param("id") Integer id, @Param("realName") String realName,
			@Param("gender") Boolean gender, @Param("isAdmin") Boolean isAdmin, @Param("updateDate") Date updateDate,
			@Param("updatePerson") String updatePerson);

	@Modifying(clearAutomatically = true)
	@Query("update User u set " + "u.password = :password " + ", u.updateDate=:updateDate "
			+ ", u.updatePerson=:updatePerson " + "where u.id =:id")
	void updatePasswordById(@Param("id") Integer id, @Param("password") String password,
			@Param("updateDate") Date updateDate, @Param("updatePerson") String updatePerson);

	@Modifying(clearAutomatically = true)
	@Query("update User u set " + " u.isLock=:isLock " + ", u.updateDate=:updateDate "
			+ ", u.updatePerson=:updatePerson " + "where u.id =:id")
	void updateIsLockById(@Param("id") Integer id, @Param("isLock") Boolean isLock,
			@Param("updateDate") Date updateDate, @Param("updatePerson") String updatePerson);

	@Modifying(clearAutomatically = true)
	@Query("update User u set " + " u.isDelete=:isDelete " + ", u.updateDate=:updateDate "
			+ ", u.updatePerson=:updatePerson " + "where u.id =:id")
	void updateIsDeletekById(@Param("id") Integer id, @Param("isDelete") Boolean isDelete,
			@Param("updateDate") Date updateDate, @Param("updatePerson") String updatePerson);

	default Page<User> findAllSupportQuery(Pageable pageable, Map<String, String> params) {
		return this.findAll(new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = null;
				String sSearch = params.get("sSearch");
				if (StringUtils.isBlank(sSearch)) {
					predicate = cb.equal(root.get("isDelete"), false);
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
					predicate = cb.and(cb.equal(root.get("isDelete"), false),
							cb.or(predicates.toArray(new Predicate[predicates.size()])));
				}
				return predicate;
			}
		}, pageable);
	}

}
