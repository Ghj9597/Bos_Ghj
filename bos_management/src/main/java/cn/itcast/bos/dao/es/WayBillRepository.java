package cn.itcast.bos.dao.es;

import cn.itcast.bos.domain.base.take_delivery.WayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by 犹良 on 2017/8/12 0012.
 */
public interface WayBillRepository extends ElasticsearchRepository<WayBill,Integer>{

}
