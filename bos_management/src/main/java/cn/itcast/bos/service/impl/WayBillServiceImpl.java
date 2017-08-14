package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.WayBillDao;
import cn.itcast.bos.dao.es.WayBillRepository;
import cn.itcast.bos.domain.base.take_delivery.WayBill;
import cn.itcast.bos.service.WayBillService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 犹良 on 2017/8/8 0008.
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
    //注入Dao层
    @Autowired
    private WayBillDao wayBillDao;
    //注入ElasticSearch的Dao层;
    @Autowired
    private WayBillRepository wayBillRepository;
    @Override
    public void save(WayBill t) {
        wayBillDao.save(t);
        wayBillRepository.save(t);
    }

    @Override
    public Page<WayBill> findAll(Pageable pageable,WayBill wayBill) {
        //判断查询条件是否存在;
        if (StringUtils.isBlank(wayBill.getWayBillNum())
                && StringUtils.isBlank(wayBill.getSendAddress())
                    && StringUtils.isBlank(wayBill.getRecAddress())
                    && StringUtils.isBlank(wayBill.getSendProNum())
                    && (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
                //如果查询条件为空.则条件查询数据库;
            Page<WayBill> page = wayBillDao.findAll(pageable);
        } else {
            //如果有查询条件,则创建布尔查询;
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            //判断是否有条件的条件;
            if (StringUtils.isNotBlank(wayBill.getWayBillNum())) {
                //如果不为空;则取出条件进行查询;
                QueryBuilder queryBuilder = new TermQueryBuilder("wayBillNum", wayBill.getWayBillNum());
                //添加进入布尔查询;
                boolQueryBuilder.must(queryBuilder);
            }
            if (StringUtils.isNoneBlank(wayBill.getSendAddress())) {
                // 发货地 模糊查询
                // 情况一： 输入"北" 是查询词条一部分， 使用模糊匹配词条查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder(
                        "sendAddress", "*" + wayBill.getSendAddress() + "*");

                // 情况二： 输入"北京市海淀区" 是多个词条组合，进行分词后 每个词条匹配查询
                QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(
                        wayBill.getSendAddress()).field("sendAddress")
                        .defaultOperator(QueryStringQueryBuilder.Operator.AND);

                // 两种情况取or关系
                BoolQueryBuilder boolQueryBuilder2 = new BoolQueryBuilder();
                boolQueryBuilder2.should(wildcardQuery);
                boolQueryBuilder2.should(queryStringQueryBuilder);

                boolQueryBuilder.must(boolQueryBuilder);
            }
            if (StringUtils.isNoneBlank(wayBill.getRecAddress())) {
                // 收货地 模糊查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder(
                        "recAddress", "*" + wayBill.getRecAddress() + "*");
                boolQueryBuilder.must(wildcardQuery);
            }
            if (StringUtils.isNoneBlank(wayBill.getSendProNum())) {
                // 速运类型 等值查询
                QueryBuilder termQuery = new TermQueryBuilder("sendProNum",
                        wayBill.getSendProNum());
                boolQueryBuilder.must(termQuery);
            }
            if (StringUtils.isNoneBlank(wayBill.getSendProNum())) {
                // 速运类型 等值查询
                QueryBuilder termQuery = new TermQueryBuilder("sendProNum",
                        wayBill.getSendProNum());
                boolQueryBuilder.must(termQuery);
            }
            if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
                // 签收状态查询
                QueryBuilder termQuery = new TermQueryBuilder("signStatus",
                        wayBill.getSignStatus());
                boolQueryBuilder.must(termQuery);
            }
            //创建SearchQuery对象;
            SearchQuery searchQuery = new NativeSearchQuery(boolQueryBuilder);
            searchQuery.setPageable(pageable);
            return wayBillRepository.search(searchQuery);
        }
      return wayBillDao.findAll(pageable);
    }



    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        WayBill wayBill=wayBillDao.findByWayBillNum(wayBillNum);
        return wayBill;
    }


}
