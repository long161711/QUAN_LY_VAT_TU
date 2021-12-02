package com.example.demo.repositories.quanly;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.quanly.KhoaEntity;

public interface KhoaRepository extends CrudRepository<KhoaEntity, Integer>  {

	@Query(value ="select * from khoa  where MaKhoa = ?1",nativeQuery = true)
	KhoaEntity findOneByMaKhoa(String makhoa);
	@Query(value ="select * from khoa  where id = ?1",nativeQuery = true)
	KhoaEntity findOneById(int id);
	@Query(value ="select * from khoa  where MaKhoa = ?1 and id!=?2",nativeQuery = true)
	KhoaEntity findOneByMaKhoaid(String makhoa, Integer id);

	
		
}
