package cn.itclass.Service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;

import org.springframework.stereotype.Service;

import cn.itcast.crm.domain.Customer;

/*在Service中要完成三个操作.
 * 1.查询已经关联定区的用户
 * 2.查询没有关联定区的用户
 * 3.给用户绑定关联的定区*/

public interface CustomerService {
	//方法一查询未关联定区的客户
	@Path("/noassociationcustomers")
	@GET
	@Produces({ "application/xml", "application/json" })
	public List<Customer> findNoAssociationCustomers();
	//方法二,根据传递的过来的定区ID查询已经绑定该定区的客户
	@Path("/customers/{fixedAreaId}")
	@GET
	@Consumes({ "application/xml", "application/json"})
	@Produces({ "application/xml", "application/json" })
	public List<Customer> findAssocationCustomers(@PathParam("fixedAreaId") String fixedAreaId);
	//方法三,将客户关联到定区
	@Path("/customers")
	@PUT
	@Consumes({ "application/xml", "application/json"})
	public void bindingCustomers(@QueryParam("customersId")String customersId,@QueryParam("fixedAreaId")String fixedAreaId);

	@Path("/findCustomer/{telephone}")
	@GET
	@Consumes({ "application/xml", "application/json"})
	@Produces({ "application/xml", "application/json" })
	public Customer findByTelephone( @PathParam("telephone")String telephone);

	@Path("/activeMail/{telephone}")
	@POST
	@Consumes({ "application/xml", "application/json"})
	@Produces({ "application/xml", "application/json" })
	public void activeMail(@PathParam("telephone")String telephone);

	@Path("/saveCustomer")
	@POST
	@Consumes({ "application/xml", "application/json"})
	@Produces({ "application/xml", "application/json" })
	public void saveCustomers(Customer customer);

	@Path("/login")
	@GET
	@Consumes({ "application/xml", "application/json"})
	@Produces({ "application/xml", "application/json" })
	public Customer logIn(@QueryParam("telephone") String telephone ,@QueryParam("password") String password);
}
