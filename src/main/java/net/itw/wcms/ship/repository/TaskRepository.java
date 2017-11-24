package net.itw.wcms.ship.repository;

import java.util.Date;
import java.util.List;

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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import net.itw.wcms.ship.entity.Ship;
import net.itw.wcms.ship.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task>, PagingAndSortingRepository<Task, Integer> {

	List<Task> getTaskByStatus(String status);
	
	Task getTaskById(Integer id);

	@Modifying(clearAutomatically = true)
	@Query("update Task t set " + "t.status = :status " + ", t.updateTime=:updateTime " + ", t.updateUser=:updateUser "
			+ "where t.id =:id")
	void updateStatusById(@Param("id") Integer id, @Param("status") String status, @Param("updateTime") Date updateTime,
			@Param("updateUser") String updateUser);
	
	default Page<Task> findAllByStatus(Pageable pageable, String status) {
		return this.findAll(new Specification<Task>() {
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> shipStatus = root.get("status");
				return cb.equal(shipStatus, status);
			}
		}, pageable);
	}	


}
