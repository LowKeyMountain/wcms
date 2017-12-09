package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.Cabin;


public interface CabinRepository extends JpaRepository<Cabin, Integer>, JpaSpecificationExecutor<Cabin>,
		PagingAndSortingRepository<Cabin, Integer> {

}
