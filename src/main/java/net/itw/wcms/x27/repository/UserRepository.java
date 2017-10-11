package net.itw.wcms.x27.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.itw.wcms.x27.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	User getUserByUserName(String userName);

	User getUserById(Integer id);
	
    Page<User> findByIsDeleteFalse(Boolean isDelete, Pageable pageable); 
	
	@Modifying(clearAutomatically = true)
	@Query("delete from User u where u.id =:id")
	Integer deleteById(@Param("id") Integer id);
	
	
	@Modifying(clearAutomatically = true)
	@Query("update User u set " 
			+ " u.realName =:realName " 
			+ ", u.gender=:gender "
			+ ", u.isAdmin=:isAdmin "
			+ ", u.updateDate=:updateDate "
			+ ", u.updatePerson=:updatePerson "
			+ "where u.id =:id")
	void updateRealNameAndGenderAndIsAdminById(
			@Param("id") Integer id, 
			@Param("realName") String realName, 
			@Param("gender") Boolean gender, 
			@Param("isAdmin") Boolean isAdmin,
			@Param("updateDate") Date updateDate,
			@Param("updatePerson") String updatePerson
			);
	
	@Modifying(clearAutomatically = true)
	@Query("update User u set " 
			+ "u.password = :password " 
			+ ", u.updateDate=:updateDate "
			+ ", u.updatePerson=:updatePerson "
			+ "where u.id =:id")
	void updatePasswordById(
			@Param("id") Integer id, 
			@Param("password") String password,
			@Param("updateDate") Date updateDate,
			@Param("updatePerson") String updatePerson
			);
	
	@Modifying(clearAutomatically = true)
	@Query("update User u set " 
			+ " u.isLock=:isLock "
			+ ", u.updateDate=:updateDate "
			+ ", u.updatePerson=:updatePerson "
			+ "where u.id =:id")
	void updateIsLockById(
			@Param("id") Integer id, 
			@Param("isLock") Boolean isLock,
			@Param("updateDate") Date updateDate,
			@Param("updatePerson") String updatePerson
			);
	
	@Modifying(clearAutomatically = true)
	@Query("update User u set " 
			+ " u.isDelete=:isDelete " 
			+ ", u.updateDate=:updateDate "
			+ ", u.updatePerson=:updatePerson "
			+ "where u.id =:id")
	void updateIsDeletekById(
			@Param("id") Integer id, 
			@Param("isDelete") Boolean isDelete,
			@Param("updateDate") Date updateDate,
			@Param("updatePerson") String updatePerson
			);
	
	/**
	@Modifying(clearAutomatically = true)
	@Query("update User u set " 
			+ "u.password = :password " 
			+ ", u.email = :email " 
			+ ", u.realName =:realName " 
			+ ", u.gender=:gender "
			+ ", u.isAdmin=:isAdmin "
			+ ", u.isLock=:isLock "
			+ ", u.isDelete=:isDelete " 
			+ ", u.createDate=:createDate "
			+ ", u.createPerson=:createPerson "
			+ ", u.updateDate=:updateDate "
			+ ", u.updatePerson=:updatePerson "
			+ ", u.lastLoginTime=:lastLoginTime "
			+ ", u.lastLoginIp=:lastLoginIp "
			+ "where u.id =:id")
	void updateById(
			@Param("id") Integer id, 
			@Param("password") String password,
			@Param("email") String email,
			@Param("realName") String realName, 
			@Param("gender") Boolean gender, 
			@Param("isAdmin") Boolean isAdmin,
			@Param("isLock") Boolean isLock,
			@Param("isDelete") Boolean isDelete,
			@Param("createDate") Date createDate,
			@Param("createPerson") String createPerson,
			@Param("updateDate") Date updateDate,
			@Param("updatePerson") String updatePerson,
			@Param("lastLoginTime") Date lastLoginTime,
			@Param("lastLoginIp") String lastLoginIp
			);
	*/
}
