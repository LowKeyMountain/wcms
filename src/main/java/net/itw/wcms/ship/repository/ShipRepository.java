package net.itw.wcms.ship.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.entity.TaskDetail;

public interface ShipRepository extends JpaRepository<Ship, Integer>, JpaSpecificationExecutor<Ship>, PagingAndSortingRepository<Ship, Integer> {

	Ship getShipById(Integer id);
	
	default Page<Ship> findAllByStatus(Pageable pageable, String status) {
		return this.findAll(new Specification<Ship>() {
			public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> shipStatus = root.get("status");
				return cb.equal(shipStatus, status);
			}
		}, pageable);
	}
	

}
