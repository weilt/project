package com.hwt.domain.entity.price;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 
 */
public class HwPrice implements Serializable {
   

    /**
     * 对象id
     */
    private Long name_id;

    /**
     * 类型   1-景点 2-导游  3- 线路 4-资讯
     */
    private Integer name_type;

    /**
     * 成人价格
     */
    private BigDecimal adult_price;

    /**
     * 儿童价格
     */
    private BigDecimal child_price;

    /**
     * 单位  1-每单 2-每人 3-每天 4-每小时
     */
    private String company;

    /**
     * 时间字符串形式  yyyy-MM-dd
     */
    private String time_str;

    /**
     * 时间 毫秒时间戳形式
     */
    private Long time;

    /**
     * 该属性目前只能针对导游   0-可被下单 1-已被下单
     */
    private Integer status;

    /**
     * 人数上限  0-无上限
     */
    private Integer person_num;

    /**
     * 创建时间
     */
    private Long create_time;

    /**
     * 修改时间
     */
    private Long update_time;

    private static final long serialVersionUID = 1L;

    

    public Long getName_id() {
        return name_id;
    }

    public void setName_id(Long name_id) {
        this.name_id = name_id;
    }

    public Integer getName_type() {
        return name_type;
    }

    public void setName_type(Integer name_type) {
        this.name_type = name_type;
    }

    public BigDecimal getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(BigDecimal adult_price) {
        this.adult_price = adult_price;
    }

    public BigDecimal getChild_price() {
        return child_price;
    }

    public void setChild_price(BigDecimal child_price) {
        this.child_price = child_price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTime_str() {
        return time_str;
    }

    public void setTime_str(String time_str) {
        this.time_str = time_str;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPerson_num() {
        return person_num;
    }

    public void setPerson_num(Integer person_num) {
        this.person_num = person_num;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }
    
    public static void main(String[] args) {
		Set<Long> set = new HashSet<Long>();
		Long a = 555555555555l;
		Long b = 444444444444l;
		Long c = 333333333333l;
		Long d = 555555555555l;
		set.add(a);
		set.add(b);
		set.add(c);
		set.add(a);
		set.add(d);
		set.remove(444444444444l);
		System.out.println(set);
		
	}
}