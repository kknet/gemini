package io.gemini.definition.market.instrument.stock.impl;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;

import io.gemini.definition.market.instrument.Exchange;
import io.gemini.definition.market.instrument.PriceMultiplier;
import io.gemini.definition.market.instrument.stock.Stock;
import io.gemini.definition.market.vector.TradingPeriod;

public final class ChinaStock extends Stock {

	protected ChinaStock(int instrumentId, String instrumentCode) {
		super(instrumentId, instrumentCode);
	}

	@Override
	public boolean isAvailableImmediately() {
		return false;
	}

	@Override
	public String fmtText() {
		return "";
	}

	@Override
	public PriceMultiplier getPriceMultiplier() {
		return super.getPriceMultiplier();
	}

	@Override
	public ImmutableSortedSet<TradingPeriod> getTradingPeriodSet() {
		// TODO 添加固定的股票交易时间
		return null;
	}

	@Override
	public Exchange exchange() {
		return Exchange.SHFE;
	}

}
