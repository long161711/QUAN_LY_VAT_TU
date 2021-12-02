package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.AccountRoleEntity;

public interface AccountRoleRepository extends CrudRepository<AccountRoleEntity, Integer> {
    public List<AccountRoleEntity> findByAccountId(int accountId);
    @Query(value ="select * from account_role where account_id = ?1",nativeQuery = true)
	public AccountRoleEntity findOneById(int id);

    
}
