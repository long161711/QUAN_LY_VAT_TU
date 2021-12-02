package com.example.demo.repositories.quanly;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.quanly.VatTu_ThanhLyEntity;

public interface VatTu_ThanhLyRepository extends CrudRepository<VatTu_ThanhLyEntity, Integer>{
	@Query(value ="select * from vattu_thanhLy  where idVatTu = ?1",nativeQuery = true)
	VatTu_ThanhLyEntity findOneByIdVatTu(int id);
	
}
