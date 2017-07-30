package cn.itclass.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.crm.domain.Customer;
import cn.itclass.Service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class ServiceTest {
	@Autowired
	private CustomerService customerService;
	@Test
	public void test1(){
		List<Customer> list = customerService.findAssocationCustomers("刘康一区");
		System.out.println("=================>>>>>>>>>>>>>"+list);
	}
}
