package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.TaskUnloadShipDetail;


public interface TaskUnloadShipDetailRepository extends JpaRepository<TaskUnloadShipDetail, Integer>, JpaSpecificationExecutor<TaskUnloadShipDetail>,
		PagingAndSortingRepository<TaskUnloadShipDetail, Integer> {

}
