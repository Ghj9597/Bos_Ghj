package ServiceTest;

import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.TakeTimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 犹良 on 2017/7/31 0031.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TakeServiceTest {

    @Autowired
    private TakeTimeService takeTimeService;

    @Test
    public void test(){
        List<TakeTime> all = takeTimeService.findAll();
        System.out.println(all);
    }
}
