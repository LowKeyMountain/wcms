package net.itw.wcms.x27.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.x27.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer>, JpaSpecificationExecutor<Privilege>,
		PagingAndSortingRepository<Privilege, Integer> {

	default List<Privilege> getPrivilegeListByUserId(Integer userId) {
		return this.findAll(new Specification<Privilege>() {
			public Predicate toPredicate(Root<Privilege> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return null;
			}
		});
	}

}
