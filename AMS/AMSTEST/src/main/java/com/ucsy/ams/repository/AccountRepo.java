package com.ucsy.ams.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucsy.ams.entity.Account;

import jakarta.transaction.Transactional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {

	public Account findByEmail(String email);

	@Query("SELECT count(role) FROM Account a where a.role = ?1")
	Integer getTotal(String role);
	
	@Query("SELECT acc from Account acc WHERE acc.role = ?1")
	public List<Account> findTotalByRole(String role);
	
	@Modifying
	@Transactional
	@Query("UPDATE Account a SET email = ?1, role= ?2 WHERE id =?3")
	int accUpdate(String email, String role, int id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Account a SET password = ?1 WHERE id =?2")
	int resetPassword(String password, int id);
	
}
