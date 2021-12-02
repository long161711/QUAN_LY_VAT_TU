package com.example.demo.configs.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.configs.models.AccountModel;
import com.example.demo.configs.models.structures.AccountInfo;
import com.example.demo.entities.AccountEntity;
import com.example.demo.entities.AccountRoleEntity;
import com.example.demo.entities.RoleEntity;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.AccountRoleRepository;
import com.example.demo.repositories.RoleRepository;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRoleRepository accountRoleRepository;

    @Override
    public AccountModel loadUserByUsername(String email) throws UsernameNotFoundException {
        AccountEntity accountEntity = this.accountRepository.findOneByEmail(email);
        if (accountEntity != null) {
            List<AccountRoleEntity> accountRoleEntities = this.accountRoleRepository.findByAccountId(accountEntity.getId());
            List<RoleEntity> roleEntities = (List<RoleEntity>) this.roleRepository.findAll();
            List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
            RoleEntity roleEntity = new RoleEntity();
            for (AccountRoleEntity accountRoleEntity : accountRoleEntities) {
                roleEntity = _getRoleByRoleId(roleEntities, accountRoleEntity.getRoleId());
                GrantedAuthority authority = new SimpleGrantedAuthority(roleEntity.getRoleName());
                grantList.add(authority);
            }
            
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNotExpired = true;
            boolean accountNonLocked = true;
            
            AccountInfo accountInfo = _mappingAttributeFromEntities(accountEntity, roleEntity);
            AccountModel accountModel = new AccountModel(accountInfo, enabled, accountNonExpired, credentialsNotExpired, accountNonLocked, grantList);

            return accountModel;
        } else {
            throw new UsernameNotFoundException("Email " + email + " was not found in the database");
        }
    }

    private RoleEntity _getRoleByRoleId(List<RoleEntity> roleEntities, int roleId) {
        for (RoleEntity roleEntity : roleEntities) {
            if (roleEntity.getId().intValue() == roleId) {
                return roleEntity;
            }
        }
        return null;
    }

    private AccountInfo _mappingAttributeFromEntities(AccountEntity accountEntity, RoleEntity roleEntity) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setId(accountEntity.getId());
        accountInfo.setEmail(accountEntity.getEmail());
        accountInfo.setEncrytedPassword(accountEntity.getEncrytedPassword());
        accountInfo.setFullName(accountEntity.getFullName());
        accountInfo.setRoleCode(roleEntity.getRoleCode());
        accountInfo.setRoleName(roleEntity.getRoleName());

        return accountInfo;
    }
}
