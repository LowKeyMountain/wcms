package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.ShipUnloader;

public interface ShipUnloaderRepository extends JpaRepository<ShipUnloader, Integer>, JpaSpecificationExecutor<ShipUnloader>,
		PagingAndSortingRepository<ShipUnloader, Integer> {

}
