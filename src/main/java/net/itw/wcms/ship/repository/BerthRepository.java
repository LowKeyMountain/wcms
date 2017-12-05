package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.Berth;

public interface BerthRepository extends JpaRepository<Berth, Integer>, JpaSpecificationExecutor<Berth>,
		PagingAndSortingRepository<Berth, Integer> {

}
