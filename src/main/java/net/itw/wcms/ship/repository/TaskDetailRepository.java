package net.itw.wcms.ship.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import net.itw.wcms.ship.entity.TaskDetail;

public interface TaskDetailRepository extends JpaRepository<TaskDetail, Integer>, JpaSpecificationExecutor<TaskDetail>, PagingAndSortingRepository<TaskDetail, Integer> {


	TaskDetail getTaskDetailById(Integer detailId);
	
	List<TaskDetail> getTaskDetailByTaskId(Integer taskId);

	@Modifying(clearAutomatically = true)
	@Query("update TaskDetail td set " + "td.status = :status " + ", td.updateTime=:updateTime "
			+ ", td.updateUser=:updateUser " + "where td.id =:id")
	void updateStatusById(@Param("id") Integer id, @Param("status") String status, @Param("updateTime") Date updateTime,
			@Param("updateUser") String updateUser);
	
	@Modifying(clearAutomatically = true)
	@Query("update TaskDetail td set " + "td.startPosition = :startPosition " + ", td.endPosition = :endPosition "
			+ ", td.updateTime=:updateTime " + ", td.updateUser=:updateUser " + "where td.id =:id")
	void updatePositionById(@Param("id") Integer id, @Param("startPosition") Double startPosition,
			@Param("endPosition") Double endPosition, @Param("updateTime") Date updateTime,
			@Param("updateUser") String updateUser);
	
/*	default Page<Task> findAllByStatus(Pageable pageable, String status) {
		return this.findAll(new Specification<TaskDetail>() {
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<String> shipStatus = root.get("status");
				return cb.equal(shipStatus, status);
			}
		}, pageable);
	}*/
	

}
