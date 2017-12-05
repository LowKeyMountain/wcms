package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.TaskBerth;

public interface TaskBerthRepository extends JpaRepository<TaskBerth, Integer>, JpaSpecificationExecutor<TaskBerth>,
		PagingAndSortingRepository<TaskBerth, Integer> {

}
