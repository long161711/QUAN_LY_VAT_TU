package com.example.demo.repositories.quanly;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.quanly.VatTu_PhongEntity;

public interface VatTu_PhongRepository extends CrudRepository<VatTu_PhongEntity, Integer> {
	@Query(value ="select * from vattu_phong  where idPhong = ?1",nativeQuery = true)
	List<VatTu_PhongEntity> findAllById(int id);
	@Query(value ="select * from vattu_phong  where idVatTu = ?1",nativeQuery = true)
	VatTu_PhongEntity findOneByIdVatTu(Integer id);
	@Query(value ="select * from vattu_phong  where idVatTu = ?1",nativeQuery = true)
	List<VatTu_PhongEntity> findAllByIdVatTu(Integer id);
	@Query(value ="select * from vattu_phong  where idVatTu = ?2 and idPhong=?1",nativeQuery = true)
	VatTu_PhongEntity findOneByIdPhongIdVatTu(Integer id, Integer id2);
	@Query(value ="select * from vattu_phong  where id = ?1",nativeQuery = true)
	VatTu_PhongEntity findOneById(int id);

}
