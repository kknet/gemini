package io.gemini.definition.market.instrument.futures.impl;

import io.gemini.definition.market.instrument.futures.api.Futures;

public final class JapanFutures extends Futures {

	public JapanFutures(JapanFuturesSymbol symbol, int term) {
		// TODO 设置id和code
		super(0, "", symbol);
	}

	@Override
	public String fmtText() {
		return "";
	}

}