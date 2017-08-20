package cn.itcast.bos.domain.base.transit;

import cn.itcast.bos.domain.base.take_delivery.WayBill;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;


/**
 * @description: 运输配送信息
 */
@Entity
@Table(name = "T_TRANSIT_INFO")
public class TransitInfo {
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "C_WAYBILL_ID")
	private WayBill wayBill;

	@OneToMany
	@JoinColumn(name = "C_TRANSIT_INFO_ID")
	@OrderColumn(name = "C_IN_OUT_INDEX")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<InOutStorageInfo> inOutStorageInfos = new ArrayList<InOutStorageInfo>();

	@OneToOne
	@JoinColumn(name = "C_DELIVERY_INFO_ID")
	private DeliveryInfo deliveryInfo;

	@OneToOne
	@JoinColumn(name = "C_SIGN_INFO_ID")
	private SignInfo signInfo;

	@Column(name = "C_STATUS")
	// 出入库中转、到达网点、开始配置、正常签收、异常
	private String status;

	@Column(name = "C_OUTLET_ADDRESS")
	private String outletAddress;

	//为了能获取到该Json字符串;将其配置get方法;并且加以@Transient不在表中生成该字段;
	@Transient
	public String getTransferInfo() {
		//创建StringBuffer用来拼接字符串;
		StringBuffer buffer = new StringBuffer();
		// 添加出入库信息
		for (InOutStorageInfo inOutStorageInfo : inOutStorageInfos) {
			//如果入库信息不为空拼接入库信息;
			buffer.append(inOutStorageInfo.getDescription() + "<br/>");
		}
		// 添加配送信息
		if (deliveryInfo != null) {
			//如果配送信息不为空拼接配送信息
			buffer.append(deliveryInfo.getDescription() + "<br/>");
		}
		// 添加签收信息
		if (signInfo != null) {
			//如果签收信息不为空拼接签收信息;
			buffer.append(signInfo.getDescription() + "<br/>");
		}
		//将拼接好的StringBuffer转为字符串;
		return buffer.toString();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WayBill getWayBill() {
		return wayBill;
	}

	public void setWayBill(WayBill wayBill) {
		this.wayBill = wayBill;
	}

	public List<InOutStorageInfo> getInOutStorageInfos() {
		return inOutStorageInfos;
	}

	public void setInOutStorageInfos(List<InOutStorageInfo> inOutStorageInfos) {
		this.inOutStorageInfos = inOutStorageInfos;
	}

	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public SignInfo getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(SignInfo signInfo) {
		this.signInfo = signInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutletAddress() {
		return outletAddress;
	}

	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}

}
