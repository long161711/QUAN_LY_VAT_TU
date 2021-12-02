package com.example.demo.repositories.quanly;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.quanly.VatTuEntity;

public interface VatTuRepository extends CrudRepository<VatTuEntity, Integer>{
	@Query(value ="select * from vattu  where id = ?1 and  SoLuong !=0",nativeQuery = true)
	VatTuEntity findOneById(Integer id);
	@Query(value ="select * from vattu  where MaVatTu = ?1 and  SoLuong !=0 ",nativeQuery = true)
	VatTuEntity findOneByMaVatTu(String maVatTu);
	@Query(value ="select * from vattu  where MaVatTu = ?1 and  SoLuong !=0",nativeQuery = true)
	VatTuEntity findByMaVatTu(String maVatTu);
	@Query(value ="select * from vattu  where SoLuong !=0",nativeQuery = true)
	List<VatTuEntity> findAll1();
	@Query(value ="select * from vattu  where NamSuDung = ?1 and  SoLuong !=0 and MaVatTu != ?2 and TenVatTu != ?3",nativeQuery = true)
	List<VatTuEntity> findAllByNamSuDung(String namSuDung, String maVatTu, String tenVatTu);
	@Query(value ="select * from vattu  where TenVatTu = ?1 and  SoLuong !=0 and MaVatTu != ?2",nativeQuery = true)
	List<VatTuEntity> findAllByTenVatTu(String tenVatTu, String maVatTu);
	@Query(value ="select * from vattu  where id = ?1 ",nativeQuery = true)
	VatTuEntity findOneById1(Integer idVatTu);
	@Query(value ="select * from vattu  where MaVatTu = ?1 and SoLuong !=0 and id!=?2",nativeQuery = true)
	VatTuEntity findOneByMaVaTuid(String maVatTu,Integer id);
	@Query(value ="select * from vattu  where TenVatTu = ?1 and MaVatTu != ?2",nativeQuery = true)
	List<VatTuEntity> findAllByTenVatTu1(String tenVatTu, String maVatTu);
	@Query(value ="select * from vattu  where MaVatTu = ?1 ",nativeQuery = true)
	VatTuEntity findByMaVatTu1(String maVatTu);
	@Query(value ="select * from vattu  where NamSuDung = ?1 and MaVatTu != ?2 and TenVatTu != ?3",nativeQuery = true)
	List<VatTuEntity> findAllByNamSuDung1(String namSuDung, String maVatTu, String tenVatTu);
	

}
