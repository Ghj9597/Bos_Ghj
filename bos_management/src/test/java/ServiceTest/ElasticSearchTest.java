package ServiceTest;

import cn.itcast.bos.dao.es.WayBillRepository;
import cn.itcast.bos.domain.base.take_delivery.WayBill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 犹良 on 2017/8/12 0012.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ElasticSearchTest {
    @Autowired
    private WayBillRepository wayBillRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Test
    public void createMapping(){
        elasticsearchTemplate.putMapping(WayBill.class);
    }
}
