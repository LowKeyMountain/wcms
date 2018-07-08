package net.itw.wcms.common.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.common.log.entity.SystemLog;

public interface SystemLogRepository extends JpaRepository<SystemLog, Integer>, JpaSpecificationExecutor<SystemLog>,
		PagingAndSortingRepository<SystemLog, Integer> {
}
