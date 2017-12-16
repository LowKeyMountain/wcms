package net.itw.wcms.ship.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.Cabin;


public interface CabinRepository extends JpaRepository<Cabin, Integer>, JpaSpecificationExecutor<Cabin>,
		PagingAndSortingRepository<Cabin, Integer> {
	
	/**
	 * 查询货物所在的船舱
	 * @param cargoId
	 * @return
	 */
	@Query(value = " select cabin.* from tab_cabin cabin where cabin.cargo_id  =?1 ", nativeQuery = true)
	List<Cabin> getCabinByCargoId(Integer cargoId);
	
	@Query(value = " select * from tab_cabin c where c.task_id =?1 ", nativeQuery = true)
	List<Cabin> findAllByTaskId(Integer taskId);
	
}
