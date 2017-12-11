package net.itw.wcms.ship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.itw.wcms.ship.entity.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Integer>, JpaSpecificationExecutor<Cargo>,
		PagingAndSortingRepository<Cargo, Integer> {
	
	/**
	 * 查询货物信息
	 * @param taskId
	 * @param cabinNo
	 * @return
	 */
	@Query(value = " select cargo.* from tab_cargo cargo , tab_cabin cabin where cargo.task_id = cabin.task_id and cargo.id = cabin.cargo_id and cabin.task_id =?1 and cabin.cabin_no =?2 ", nativeQuery = true)
	Cargo getCargoByTaskIdAndCabinNo(Integer taskId, Integer cabinNo);
	
	/**
	 * 查询货物存放舱位
	 * @param cargoId
	 * @return
	 */
	@Query(value = " select Group_concat(e.cabin_no SEPARATOR ',') from tab_cabin e where e.cargo_id =?1 ", nativeQuery = true)
	String getCargoWarehouse(Integer cargo_id);

}
