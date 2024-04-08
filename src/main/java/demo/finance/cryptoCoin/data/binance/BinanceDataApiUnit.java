package demo.finance.cryptoCoin.data.binance;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.common.service.CryptoCoinCommonService;
import demo.finance.cryptoCoin.data.pojo.bo.BinanceKLineBO;
import net.sf.json.JSONArray;
import toolPack.httpHandel.HttpUtil;
import toolPack.ioHandle.FileUtilCustom;

@Component
public class BinanceDataApiUnit extends CryptoCoinCommonService {

	private static final String HOURLY_DATA_SAVING_PATH_MOUDLE = MAIN_FOLDER_PATH + "/crypto/data/hourlyData/%s.json";
	private static final int DEFAULT_HOUR_DATA_SIZE = 240;
	private static final int DEFAULT_DATA_GAP_MINUTE = 60;
	private static final int BINANCE_DATA_FIELD_COUNT = 12;

	public List<BinanceKLineBO> getKLineHourDataFromApi(String symbol) {
		String url = "https://api.binance.com/api/v3/klines?symbol=%s&interval=1h&startTime=%d&endTime=%d";
		Date now = new Date();
		long nowMillionSecond = now.getTime();
		long threeDayTimeGap = 1000L * 60 * 60 * 24 * 3;
		long startTime = nowMillionSecond - threeDayTimeGap;
		url = String.format(url, symbol.toUpperCase(), startTime, nowMillionSecond);

		HttpUtil h = new HttpUtil();
		String response = null;
		try {
			response = h.sendGet(url);
		} catch (IOException e) {
			log.error("Get hour data from binance IOExcepiton, symbol: " + symbol + ", error: "
					+ e.getLocalizedMessage());
			return new ArrayList<>();
		} catch (URISyntaxException e) {
			log.error("Get hour data from binance URISyntaxException, symbol: " + symbol + ", error: "
					+ e.getLocalizedMessage());
			return new ArrayList<>();
		}

		saveData(symbol, response);

		return buildDataListDTO(response);
	}

	private List<BinanceKLineBO> buildDataListDTO(String responseStr) {
		List<BinanceKLineBO> list = new ArrayList<>();
		if (StringUtils.isBlank(responseStr)) {
			return list;
		}
		JSONArray mainJsonArray = null;
		try {
			mainJsonArray = JSONArray.fromObject(responseStr);
		} catch (Exception e) {
			return list;
		}
		JSONArray tmpDataEle = null;
		BinanceKLineBO tmpBO = null;
		for (int i = 0; i < mainJsonArray.size(); i++) {
			tmpDataEle = mainJsonArray.getJSONArray(i);
			if (tmpDataEle.size() != BINANCE_DATA_FIELD_COUNT) {
				continue;
			}
			tmpBO = new BinanceKLineBO();
			tmpBO.setOpenTime(localDateTimeHandler.dateToLocalDateTime(new Date(tmpDataEle.getLong(0))));
			tmpBO.setOpen(new BigDecimal(tmpDataEle.getString(1)));
			tmpBO.setHigh(new BigDecimal(tmpDataEle.getString(2)));
			tmpBO.setLow(new BigDecimal(tmpDataEle.getString(3)));
			tmpBO.setClose(new BigDecimal(tmpDataEle.getString(4)));
			tmpBO.setVolume(new BigDecimal(tmpDataEle.getString(5)));
			tmpBO.setCloseTime(localDateTimeHandler.dateToLocalDateTime(new Date(tmpDataEle.getLong(6))));
			tmpBO.setBaseAssetVolume(new BigDecimal(tmpDataEle.getString(7)));
			tmpBO.setNumberOfTrades(new BigDecimal(tmpDataEle.getString(8)));
			tmpBO.setTakerBuyVolume(new BigDecimal(tmpDataEle.getString(9)));
			tmpBO.setTakerBuyBaseAssetVolume(new BigDecimal(tmpDataEle.getString(10)));
			tmpBO.setUnknown(tmpDataEle.getString(11));
			list.add(tmpBO);
		}

		return list;
	}

	private void saveData(String symbol, String response) {
		String savingPath = String.format(HOURLY_DATA_SAVING_PATH_MOUDLE, symbol);
		File f = new File(savingPath);
		if (!f.getParentFile().exists()) {
			File parentFolder = f.getParentFile();
			if (!parentFolder.mkdirs()) {
				log.error("Error when Binance hourly data saving, Can NOT create parent folder");
				return;
			}
		}
		FileUtilCustom fu = new FileUtilCustom();
		fu.byteToFile(response.getBytes(StandardCharsets.UTF_8), savingPath);
	}

	public List<BinanceKLineBO> getKLineHourDataFromLocal(String symbol) {
		FileUtilCustom fu = new FileUtilCustom();
		String savingPath = String.format(HOURLY_DATA_SAVING_PATH_MOUDLE, symbol);
		File file = new File(savingPath);
		if (!file.exists()) {
			return new ArrayList<>();
		}
		String content = fu.getStringFromFile(savingPath);
		List<BinanceKLineBO> oldDataList = buildDataListDTO(content);

		return oldDataList;
	}

	public boolean needUpdateDataList(List<BinanceKLineBO> dataList) {
		if (dataList == null || dataList.size() < DEFAULT_HOUR_DATA_SIZE) {
			return true;
		}
		LocalDateTime now = LocalDateTime.now();
		BinanceKLineBO lastData = dataList.get(dataList.size() - 1);
		long gapInMinutes = ChronoUnit.MINUTES.between(lastData.getOpenTime(), now);
		if (gapInMinutes > DEFAULT_DATA_GAP_MINUTE) {
			return true;
		}
		return (now.getHour() != lastData.getOpenTime().getHour());
	}

	public List<BinanceKLineBO> getKLineHourData(String symbol) {
		List<BinanceKLineBO> oldDataList = getKLineHourDataFromLocal(symbol);
		if (!needUpdateDataList(oldDataList)) {
			return oldDataList;
		}

		return getKLineHourDataFromApi(symbol);
	}
}
