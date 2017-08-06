package cn.itcast.bos.domain.base;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by 犹良 on 2017/8/4 0004.
 */

@XmlRootElement(name = "PageBean")
@XmlSeeAlso(value = {Promotion.class})
public class PageBean<T> {
    private long totalCount; // 总记录数
    private List<T> pageData; // 当前页数据

    public List<T> getPageData() {
        return pageData;
    }

    public long getTotalCount() {

        return totalCount;
    }

    public void setPageData(List<T> pageData) {

        this.pageData = pageData;
    }


    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", pageData=" + pageData +
                '}';
    }
}
