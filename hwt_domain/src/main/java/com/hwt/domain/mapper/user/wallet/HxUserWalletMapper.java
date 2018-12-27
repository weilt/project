package com.hwt.domain.mapper.user.wallet;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hwt.domain.entity.user.wallet.HxUserWallet;
import com.hwt.domain.entity.user.wallet.Vo.HxUserWalletUserVo;

@Mapper
public interface HxUserWalletMapper {


    int insert(HxUserWallet record);

    int insertSelective(HxUserWallet record);


    int updateByPrimaryKeySelective(HxUserWallet record);

    int updateByPrimaryKey(HxUserWallet record);

	/**
	 * 根据用户查询钱包
	 * @param user_id
	 * @return
	 */
    @Select("select name_id as user_id ,balance, is_can, is_not_can from hx_user_wallet where name_id = #{user_id} and name_type = 1")
	HxUserWalletUserVo query(@Param("user_id")Long user_id);

	/**
	 * 通过用户id查询
	 * @param user_id
	 * @return
	 */
    @Select("select name_id, name_type, balance, is_can, is_not_can, bad_payment, get_payment, sale_payment, "
   +" payment_password, payment_salt, day_pass_num, create_time from hx_user_wallet  where name_id = #{user_id} and name_type = 1 ")
	HxUserWallet selectByUserId(@Param("user_id")Long user_id);

	/**
	 * 增加钱 给用户
	 * @param user_id
	 * @param amount //退款金额
	 */
    @Update("update hx_user_wallet set balance = (balance + #{amount}),is_can = (is_can + #{amount}) where name_id = #{user_id} and name_type = 1 ")
	void addBalanceByUserId(@Param("user_id")Long user_id, @Param("amount")BigDecimal amount);


	/**
	 * 减去钱
	 * @param user_id
	 * @param amount
	 * @return 
	 */
    @Update("update hx_user_wallet set balance = (balance - #{amount}),is_can = (is_can - #{amount}) where name_id = #{user_id} and name_type = 1 and is_can >= #{amount}")
	int safeBalanceByUserId(@Param("user_id")Long user_id, @Param("amount")BigDecimal amount);

	/**
	 * 查询旅行社钱包
	 * @param bureau_id
	 */
    @Select("select name_id, name_type, balance, is_can, is_not_can, bad_payment, get_payment, sale_payment, "
   +" payment_password, payment_salt, day_pass_num, create_time  from hx_user_wallet  where name_id = #{bureau_id} and name_type = 2 ")
    HxUserWallet queryBureau(@Param("bureau_id")Long bureau_id);

    /**
     * 减去钱
     * @param bureau_id   
     * @param amount
     * @return
     */
    @Update("update hx_user_wallet set balance = (balance - #{amount}),is_can = (is_can - #{amount}) where name_id = #{bureau_id} and name_type = 2 and is_can >= #{amount}")
	int safeBalanceByBureauId(@Param("bureau_id")Long bureau_id, @Param("amount")BigDecimal amount);

	/**
	 * 收入总和
	 * @param bureau_id
	 * @return
	 */
    @Select("select count(operation_amount) from hx_user_wallet  where name_id = #{bureau_id} and name_type = 2 and bill_type = 3 and is_get = 1")
	BigDecimal soleBureauTatol(@Param("bureau_id")Long bureau_id);

    /**
     * 订单完成加钱
     * @param cicerone_id
     * @param name_type   1-用户 2-旅行社
     * @param payment
     */
    @Update("update hx_user_wallet set sale_payment = (sale_payment + #{amount}), balance = (balance + #{amount}),is_not_can = (is_not_can + #{amount}) where name_id = #{name_id} and name_type = #{name_type} ")
	void addBalanceByNameId(@Param("name_id")Long name_id,@Param("name_type") int name_type, @Param("amount")BigDecimal payment);

    /**
     * 订单完成7天 将钱转入可用金额
     * @param name_id
     * @param name_type
     * @param payment
     */
    @Update("update hx_user_wallet set is_can = (is_can + #{amount}),is_not_can = (is_not_can - #{amount}) where name_id = #{name_id} and name_type = #{name_type} and"
    		+ " is_not_can >=#{amount}")
	int is_not_can_to_can(@Param("name_id")Long name_id,@Param("name_type") int name_type, @Param("amount")BigDecimal payment);

	/**
	 * 增加钱 给用户
	 * @param user_id
	 * @param amount
	 */
	@Update("update hx_user_wallet set balance = (balance + #{amount}),is_can = (is_can + #{amount}) where name_id = #{user_id} and name_type = #{name_type} ")
	void addBalanceById(@Param("user_id")Long user_id, @Param("amount")BigDecimal amount,@Param("name_type")int name_type);

	/**
	 * 商家 和用户 有违约金 增加钱 给用户
	 * @param user_id
	 * @param amount
	 * @param contract_money
	 */
	@Update("update hx_user_wallet set balance = (balance + #{amount} ),is_can = (is_can + #{amount} ),bad_payment = (bad_payment + #{contract_money}) where name_id = #{user_id} and name_type = #{name_type} ")
	void addBalanceByBreakId(@Param("user_id")Long user_id, @Param("amount")BigDecimal amount,@Param("contract_money")BigDecimal contract_money,@Param("name_type") int name_type);


	/**
	 * 有违约金 给商家添加违约金(不是赔偿)
	 * @param bureau_id
	 * @param contract_money
	 */
	@Update("update hx_user_wallet set bad_payment = (bad_payment + #{contract_money}) where  name_id = #{bureau_id}")
	void updateByBureau_id(@Param("bureau_id")Long bureau_id,@Param("contract_money") BigDecimal contract_money);

	//查询商家的钱是不是够扣除违约金用
	@Select("select * from hx_user_wallet where name_id =#{bureau_id}")
	HxUserWallet selectByBureau_id(@Param("bureau_id")Long bureau_id);


	//冻结金额够用 扣除违约金
	@Update("update hx_user_wallet set balance = (balance - #{contract_money} ),is_not_can = (is_not_can - #{contract_money}),bad_payment = (bad_payment + #{contract_money}) where name_id = #{bureau_id} and name_type = 2")
	void updateByButton(@Param("bureau_id")Long bureau_id,@Param("contract_money") BigDecimal contract_money);

	//可用金额够用 扣除违约金
	@Update("update hx_user_wallet set balance = (balance - #{contract_money} ),is_can = (is_can - #{contract_money}),bad_payment = (bad_payment + #{contract_money}) where name_id = #{bureau_id} and name_type = 2")
	void updateByButton2(@Param("bureau_id")Long bureau_id,@Param("contract_money") BigDecimal contract_money);

	//总金额够用 扣除违约金 		bigMoney总金额 和 可用金额
	@Update("update hx_user_wallet set balance = #{bigMoney},is_can = #{bigMoney},is_not_can = 0 ,bad_payment = (bad_payment + #{contract_money}) where name_id = #{bureau_id} and name_type = 2")
	void updateByButton3(@Param("bureau_id")Long bureau_id,@Param("bigMoney") BigDecimal bigMoney,@Param("contract_money")BigDecimal contract_money);

	//修改操作 清空商家余额
	@Update("update hx_user_wallet set balance = 0,is_can = 0,is_not_can = 0  where name_id = #{bureau_id} and name_type = 2")
	void updateByEmpty(Long bureau_id);
	
	@Update("update hx_user_wallet set balance = (balance+100),is_can = (is_can+100)  where name_id = #{bureau_id} and name_type = 1")
	void test(@Param("bureau_id")long i);

	/**
	 * 导游订单，客户申请退款自动处理后，钱包操作
	 * @param user_id		用户id
	 * @param safeSubtract	扣除违约金后所剩金额
	 * @param penalty		违约金
	 */
	@Update("update hx_user_wallet set balance = (balance + #{safeSubtract}),is_can = (is_can + #{safeSubtract}),bad_payment = (bad_payment + #{penalty}) where name_id = #{user_id}  and name_type = 1 ")
	void orderRefundByUserId(@Param("user_id")Long user_id, @Param("safeSubtract")BigDecimal safeSubtract, @Param("penalty")BigDecimal penalty);
}