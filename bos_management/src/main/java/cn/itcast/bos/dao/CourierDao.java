package cn.itcast.bos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

public interface CourierDao extends JpaRepository<Courier,Integer>,JpaSpecificationExecutor<Courier>{
	@Query(value = "update Courier set deltag='1' where id = ?")
	@Modifying
	void delBatch(int id);

}
