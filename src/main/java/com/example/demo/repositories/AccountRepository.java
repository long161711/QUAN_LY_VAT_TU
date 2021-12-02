package com.example.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.AccountEntity;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {
  public AccountEntity findOneByEmail(String email);
	@Query(value ="select * from account  where email = ?1",nativeQuery = true)
	public AccountEntity findOneByEmail1(String email);
	@Query(value ="select * from account where id = ?1",nativeQuery = true)
	 public AccountEntity findOneById(int Id);
}
