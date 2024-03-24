package demo.config.costomComponent;

import demo.baseCommon.pojo.constant.SystemConstant;

public class OptionFilePathConfigurer {

	private static final String ROOT = SystemConstant.ROOT_PATH + "/optionFile";

	public static final String SYSTEM = ROOT + "/system/option.json";
	public static final String AUTOMATION_TEST = ROOT + "/automationTest/option.json";

	public static final String CURRENCY_EXCHANGE_RATE = ROOT + "/currencyExchangeRate/option.json";
	public static final String CN_STOCK_MARKET = ROOT + "/cnStockMarket/option.json";

	public static final String CRYPTO_COIN = ROOT + "/cryptoCoin/option.json";
}
