package com.example.demo.repositories.quanly;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.quanly.VatTu_HongEntity;

public interface VatTu_HongRepository extends CrudRepository<VatTu_HongEntity, Integer> {
	@Query(value ="select * from vattu_hong  where idVatTu = ?1",nativeQuery = true)
	VatTu_HongEntity findOneById(Integer id);
	@Query(value ="select * from vattu_hong  where idVatTu = ?1",nativeQuery = true)
	VatTu_HongEntity findOneByIdVatTu(Integer id);
	@Query(value ="select * from vattu_hong  where id = ?1",nativeQuery = true)
	VatTu_HongEntity findOneByIdVatTuHong(Integer id);
}
