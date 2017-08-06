package ServiceTest;

import cn.itcast.bos.domain.base.PageBean;
import cn.itcast.bos.domain.base.Promotion;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.springframework.data.domain.Page;

/**
 * Created by 犹良 on 2017/8/4 0004.
 */
public class PromotaionServiceTest {

    @Test
    public void test(){

        PageBean<Promotion> pageBean = WebClient.create("http://localhost:8888/bos_management/services/promotion/findAll?page=1&pageSize=2").get(PageBean.class);
        System.out.println(pageBean);
    }
}
