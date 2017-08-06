package cn.itclass.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>, JpaSpecificationExecutor<Customer> {
    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query("update Customer set fixedAreaId=null where fixedAreaId=?1")
    @Modifying
    void updataFixedAreaIdNull(String fixedAreaId);

    @Query("update Customer set fixedAreaId=?1 where id=?2")
    @Modifying
    void updateCustomerFixedAreaId(String fixedAreaId, int customerId);

    Customer findByTelephone(String telephone);

    @Query("update Customer set type=1 where telephone=?1")
    @Modifying
    void activeMail(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String passworld);
}
