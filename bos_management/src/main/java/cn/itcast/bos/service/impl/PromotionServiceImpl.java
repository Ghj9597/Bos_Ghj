package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.PromotionDao;
import cn.itcast.bos.domain.base.PageBean;
import cn.itcast.bos.domain.base.Promotion;
import cn.itcast.bos.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by 犹良 on 2017/8/3 0003.
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionDao promotionDao;
    public void save(Promotion promotion){
        promotionDao.save(promotion);
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {
        Page<Promotion> page = promotionDao.findAll(pageable);
        return page;
    }

    @Override
    public PageBean findAll(int page, int pageSize) {
        Pageable pageable=new PageRequest(page-1,pageSize);
        Page<Promotion> all = promotionDao.findAll(pageable);
        System.out.println(all.toString());
        PageBean<Promotion> pageBean=new PageBean<Promotion>();
        pageBean.setPageData(all.getContent());
        pageBean.setTotalCount(all.getTotalElements());
        return pageBean;
    }

    @Override
    public Promotion findOne(int id) {
        Promotion promotion = promotionDao.findOne(id);
        return promotion;
    }

    @Override
    public void updateStatus(Date date) {
        promotionDao.updateStatus(date);
    }
}
