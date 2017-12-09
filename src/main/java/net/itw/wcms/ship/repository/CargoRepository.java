package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Integer>, JpaSpecificationExecutor<Cargo>,
		PagingAndSortingRepository<Cargo, Integer> {

}
