package cn.itclass.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.domain.Customer;
import cn.itclass.Service.CustomerService;
import cn.itclass.dao.CustomerRepository;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Override
	public List<Customer> findNoAssociationCustomers() {
		//查询未关联定区的客户;也就是表中数据fixedAreaId为null的可以使用默认规则查询
		List<Customer> list=customerRepository.findByFixedAreaIdIsNull();
		return list;
	}

	@Override
	public List<Customer> findAssocationCustomers(String fixedAreaId) {
		List<Customer> list=customerRepository.findByFixedAreaId(fixedAreaId);
		return list;
	}

	@Override
	public void bindingCustomers(String customersId, String fixedAreaId) {
		System.out.println(customersId);
		System.out.println(fixedAreaId);
		customerRepository.updataFixedAreaIdNull(fixedAreaId);
		System.out.println("fixedAreaId值是=====================>>>>>>>>>>>>"+fixedAreaId);
		System.out.println("里边值是=====================>>>>>>>>>>>>"+customersId);
		if(customersId==null&&customersId==""&&"null".equals(customersId)){
			String[] sp = customersId.split(",");
			for (String st : sp) {
				int parseInt = Integer.parseInt(st);
				customerRepository.updateCustomerFixedAreaId(fixedAreaId,parseInt);
			}
		}
	}

}
