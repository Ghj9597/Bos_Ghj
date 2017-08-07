package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.take_delivery.Order;

import javax.ws.rs.*;

/**
 * Created by 犹良 on 2017/8/7 0007.
 */
public interface OrderService {
    @Path("/add")
    @POST
    @Consumes({ "application/xml", "application/json"})
    @Produces({ "application/xml", "application/json" })
    void add(Order order);
}
