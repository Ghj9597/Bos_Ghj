package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.PageBean;

import cn.itcast.bos.domain.base.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;
import java.util.Date;

/**
 * Created by 犹良 on 2017/8/3 0003.
 */
public interface PromotionService {
    void save(Promotion promotion);
    Page<Promotion> findAll(Pageable pageable);

    @Path("/findAll")
    @GET
    @Consumes({ "application/xml", "application/json"})
    @Produces({ "application/xml", "application/json" })
    PageBean findAll(@QueryParam("page")int page,@QueryParam("pageSize") int pageSize);

    @Path("/findOne/{id}")
    @GET
    @Consumes({ "application/xml", "application/json"})
    @Produces({ "application/xml", "application/json" })
    Promotion findOne(@PathParam("id") int id);

    void updateStatus(Date date);
}
