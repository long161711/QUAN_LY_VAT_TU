package com.example.demo.repositories.quanly;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.quanly.VatTu_KhoaEntity;

public interface VatTu_KhoaRepository extends CrudRepository<VatTu_KhoaEntity, Integer> {
	@Query(value ="select * from vattu_khoa  where idKhoa = ?1",nativeQuery = true)
	List<VatTu_KhoaEntity> findAllByIdKhoa(int id);
	@Query(value ="select * from vattu_khoa  where idVatTu = ?1 and idKhoa =?2",nativeQuery = true)
	VatTu_KhoaEntity findOneByMaVatTu(Integer id, int id2);
	@Query(value ="select * from vattu_khoa  where id = ?1 ",nativeQuery = true)
	VatTu_KhoaEntity findOneById(int id);
	@Query(value ="select * from vattu_khoa  where idVatTu = ?1 and idKhoa =?2",nativeQuery = true)
	VatTu_KhoaEntity findOneByIdVatTuIdKhoa(Integer idVatTu, int idKhoa);

}
