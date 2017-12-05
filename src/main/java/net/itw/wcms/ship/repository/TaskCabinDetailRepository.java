package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.TaskCabinDetail;


public interface TaskCabinDetailRepository extends JpaRepository<TaskCabinDetail, Integer>, JpaSpecificationExecutor<TaskCabinDetail>,
		PagingAndSortingRepository<TaskCabinDetail, Integer> {

}
