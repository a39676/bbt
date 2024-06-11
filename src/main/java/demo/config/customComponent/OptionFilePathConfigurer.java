package demo.config.customComponent;

import demo.baseCommon.pojo.constant.SystemConstant;

public class OptionFilePathConfigurer {

	public static final String ROOT = SystemConstant.ROOT_PATH + "/optionFile/bbt";

	public static final String SYSTEM = ROOT + "/system/option.json";
	public static final String CLOUD_FLARE = ROOT + "/cloudflare/option.json";
	public static final String AUTOMATION_TEST = ROOT + "/automationTest/option.json";

	public static final String EDUCATION_INFO = ROOT + "/automationTest/educationInfoOption.json";
	public static final String V2EX_JOB_INFO = ROOT + "/automationTest/v2exJobInfoOption.json";

	public static final String CURRENCY_EXCHANGE_RATE = ROOT + "/currencyExchangeRate/option.json";
	public static final String CN_STOCK_MARKET = ROOT + "/cnStockMarket/option.json";

	public static final String CRYPTO_COIN = ROOT + "/cryptoCoin/option.json";
}
