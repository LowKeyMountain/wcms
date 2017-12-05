package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.TaskCabinPositionDetail;

public interface TaskCabinPositionDetailRepository extends JpaRepository<TaskCabinPositionDetail, Integer>, JpaSpecificationExecutor<TaskCabinPositionDetail>,
		PagingAndSortingRepository<TaskCabinPositionDetail, Integer> {

}
