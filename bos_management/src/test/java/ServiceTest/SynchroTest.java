package ServiceTest;

import cn.itcast.bos.service.WayBillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 犹良 on 2017/8/15 0015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SynchroTest {
    @Autowired
    private WayBillService wayBillService;
    @Test
    public void sy(){
        wayBillService.synchro();
    }
}
