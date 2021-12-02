package com.example.demo.repositories.quanly;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.demo.entities.quanly.PhongEntity;

public interface PhongRepository extends CrudRepository<PhongEntity, Integer>  {
	@Query(value ="select * from phong  where idkhoa = ?1",nativeQuery = true)
	List<PhongEntity> khoa_phong(int id);
	@Query(value ="select * from phong  where MaPhong = ?1",nativeQuery = true)
	PhongEntity findOneByMaphong(String maPhong);
	@Query(value ="select * from phong  where id = ?1",nativeQuery = true)
	PhongEntity findOneById(int id);
	@Query(value ="select * from phong  where idKhoa = ?1",nativeQuery = true)
	List<PhongEntity> findAllByIdKhoa(Integer id);

}
