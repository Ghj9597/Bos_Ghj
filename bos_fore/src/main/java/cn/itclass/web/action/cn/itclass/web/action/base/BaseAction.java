package cn.itclass.web.action.cn.itclass.web.action.base;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

//代码重构的实现;
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	//第一步将模型驱动等代码抽取出来简化开发;
	protected T t;
	@Override
	public T getModel() {
		return t;
	}

	public BaseAction(){//将方法写在构造器中使他在类加载时初始化;
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();//拿到带有泛型的父类型;
		Class<T> modelClass = (Class<T>) type.getActualTypeArguments()[0];//拿到所有泛型,取出第一个并且实例化;
		try {
			t = modelClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//我们还可以将对json分页数据封装的方法写在方法中;
	public void paging(Page<T> page){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total",page.getTotalElements());
		map.put("rows",page.getContent());
		ActionContext.getContext().getValueStack().push(map);
	}
}
