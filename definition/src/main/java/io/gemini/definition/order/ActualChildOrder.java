package io.gemini.definition.order;

import static io.gemini.definition.order.OrderUniqueIds.allocateId;

import org.slf4j.Logger;

import io.gemini.definition.account.SubAccount;
import io.gemini.definition.market.instrument.Instrument;
import io.gemini.definition.order.enums.OrdType;
import io.gemini.definition.order.enums.TrdAction;
import io.gemini.definition.order.enums.TrdDirection;
import io.gemini.definition.order.structure.OrdPrice;
import io.gemini.definition.order.structure.OrdQty;
import io.gemini.definition.order.structure.TrdRecord;
import io.gemini.definition.order.structure.TrdRecordList;
import io.gemini.definition.strategy.StrategyIdConst;

/**
 * 实际执行订单的最小执行单元, 可能根据合规, 账户情况等由ActParentOrder拆分出多个ActChildOrder
 * 
 * @author yellow013
 * @creation 2018年1月14日
 */
public final class ActualChildOrder extends ActualOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3863592977001402228L;

	/**
	 * 经纪商提供的唯一码, 可能有多个, 使用数组实现
	 */
	private final String[] brokerIdentifier = new String[4];

	/**
	 * 订单成交列表
	 */
	private final TrdRecordList recordList;

	/**
	 * 
	 * @param strategyId   策略Id
	 * @param subAccountId 子账户Id
	 * @param accountId    实际账户Id
	 * @param instrument   交易标的
	 * @param ordQty       委托数量
	 * @param ordPrice     委托价格
	 * @param ordType      订单类型
	 * @param direction    交易方向
	 * @param action       交易动作
	 * @param ownerOrdId   所属上级订单
	 */
	ActualChildOrder(int strategyId, int subAccountId, int accountId, Instrument instrument, int offerQty,
			long offerPrice, OrdType type, TrdDirection direction, TrdAction action, long ownerOrdId) {
		super(allocateId(strategyId), strategyId, subAccountId, accountId, instrument, OrdQty.withOffer(offerQty),
				OrdPrice.withOffer(offerPrice), type, direction, action, ownerOrdId);
		this.recordList = new TrdRecordList(uniqueId());
	}

	/**
	 * 
	 * 用于构建外部来源的订单
	 * 
	 * @param uniqueId   外部传入的uniqueId, 用于处理非系统订单
	 * @param accountId  实际账户Id
	 * @param instrument 交易标的
	 * @param ordQty     委托数量
	 * @param ordPrice   委托价格
	 * @param ordType    订单类型
	 * @param direction  交易方向
	 * @param action     交易动作
	 */
	ActualChildOrder(long uniqueId, int accountId, Instrument instrument, int offerQty, long offerPrice,
			TrdDirection direction, TrdAction action) {
		super(uniqueId, StrategyIdConst.ExternalStrategyId, accountId, SubAccount.ExternalSubAccountId, instrument,
				OrdQty.withOffer(offerQty), OrdPrice.withOffer(offerPrice), OrdType.Limit, direction, action, 0L);
		this.recordList = new TrdRecordList(uniqueId());
	}

	@Override
	public int ordLevel() {
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public String[] brokerIdentifier() {
		return brokerIdentifier;
	}

	/**
	 * 
	 * @return
	 */
	public TrdRecordList recordList() {
		return recordList;
	}

	/**
	 * 
	 * @return
	 */
	public TrdRecord firstTrdRecord() {
		return recordList.first().get();
	}

	/**
	 * 
	 * @return
	 */
	public TrdRecord lastTrdRecord() {
		return recordList.last().get();
	}

	private static final String ChildOrderText = "{} :: {}, ChildOrder : uniqueId==[{}], ownerUniqueId==[{}], "
			+ "status==[{}], direction==[{}], action==[{}], type==[{}], instrument -> {}, price -> {}, "
			+ "qty -> {}, timestamp -> {}, trdRecordList -> {}";

	@Override
	public void writeLog(Logger log, String objName, String msg) {
		log.info(ChildOrderText, objName, msg, uniqueId(), ownerUniqueId(), status(), direction(), action(), type(),
				instrument(), price(), qty(), timestamp(), recordList);
	}

}
