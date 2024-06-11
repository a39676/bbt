package demo.tool.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import demo.tool.service.ComplexToolService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tool.pojo.constant.CxBbtInteractionUrl;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class ComplexToolServiceImpl extends CommonService implements ComplexToolService {

	@Autowired
	private SystemOptionService systemOptionService;
	@Autowired
	private CloudFlareOptionService cloudFlareOptionService;

	@Override
	public CommonResult cleanTmpFiles(String targetFolder, String extensionName, LocalDateTime oldestCreateTime) {
		CommonResult r = new CommonResult();

		File folder = new File(targetFolder);
		if (!folder.exists()) {
			return r;
		}

		File[] files = folder.listFiles();
		BasicFileAttributes attrs;
		Path p;
		Date fileTime;
		LocalDateTime fileLocalDateTime;
		for (File f : files) {
			if (f.isDirectory()) {
				cleanTmpFiles(f.getAbsolutePath(), extensionName, oldestCreateTime);
			}
			if (f.isFile()) {
				p = f.toPath();
				try {
					attrs = Files.readAttributes(p, BasicFileAttributes.class);
					fileTime = new Date(attrs.creationTime().toMillis());
					fileLocalDateTime = localDateTimeHandler.dateToLocalDateTime(fileTime);
					if (fileLocalDateTime.isBefore(oldestCreateTime)) {
						if (StringUtils.isBlank(extensionName)) {
							f.delete();
						} else {
							if (f.getName().endsWith(extensionName)) {
								f.delete();
							}
						}
					}
				} catch (IOException e) {
				}
			}
		}

		r.setIsSuccess();
		return r;
	}

	@Override
	public String ping() {
		return "pong";
	}

	@Override
	public void amIAlive() {
		if (systemOptionService.isDev()) {
			return;
		}

		try {
			URL url = new URL("https://" + systemOptionService.getWorker1Hostname() + CxBbtInteractionUrl.ROOT
					+ CxBbtInteractionUrl.WORKER_PING);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			if (content != null && content.toString().contains("pong")) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			FileUtilCustom f = new FileUtilCustom();
			String ipLocalSavePath = OptionFilePathConfigurer.SYSTEM.replaceAll("option.json", "ip.txt");
			String oldIpStr = f.getStringFromFile(ipLocalSavePath);

			executeShellScriptForGetIp();
			String newIpStr = f.getStringFromFile(ipLocalSavePath);
			if (StringUtils.isEmpty(newIpStr)) {
				log.error("Can NOT find IP record from local file");
				return;
			} else if (newIpStr.equals(oldIpStr)) {
				log.error("IP did NOT change, skip DNS update");
				return;
			}
			updateWork1DnsRecord(newIpStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeShellScriptForGetIp() {
		if (!isLinux()) {
			return;
		}

		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("~/toolSH/getIp.sh");
		try {
			Process process = processBuilder.start();
			process.waitFor();
//				int exitVal = process.waitFor();
//				if (exitVal != 0) {
//					sendTelegramMsg("Kill chrome driver error");
//				}
		} catch (Exception e) {
			e.printStackTrace();
			sendingMsg("Worker get IP error");
		}

	}

	private String createDNS(String targetIp) {
		JSONObject paramJson = new JSONObject();
		paramJson.put("type", "A");
		paramJson.put("name", cloudFlareOptionService.getTargetHost());
		paramJson.put("content", targetIp);
		paramJson.put("ttl", "120");
		paramJson.put("proxied", false);

		String url = "https://" + cloudFlareOptionService.getHost() + cloudFlareOptionService.getZonesApiRoot() + "/"
				+ cloudFlareOptionService.getZoneId() + cloudFlareOptionService.getDnsApiUrl();

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPost request = new HttpPost(url);
			StringEntity params = new StringEntity(paramJson.toString());
//			params.setContentType("application/json");
			request.addHeader("x-auth-email", cloudFlareOptionService.getEmail());
//			request.addHeader("x-auth-key", key);
			request.addHeader("Authorization", "Bearer " + cloudFlareOptionService.getKey());

//			request.addHeader("Content-Type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			String responseStr = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
			JSONObject responseJson = JSONObject.fromObject(responseStr);

			if (responseJson.containsKey("success") && responseJson.getBoolean("success")) {
				JSONObject resultDetail = responseJson.getJSONObject("result");
				resultDetail.remove("id");
				resultDetail.remove("zone_id");
				resultDetail.remove("zone_name");
				resultDetail.remove("name");
				resultDetail.remove("content");
				responseJson.put("result", resultDetail);
			}
			String msg = "Work DNS update response: " + responseJson.toString();
			sendingMsg(msg);

			String recordId = responseJson.getJSONObject("result").getString("id");

			return recordId;
		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private void editDNS(String recordID, String targetIp) {
		JSONObject paramJson = new JSONObject();
		paramJson.put("type", "A");
		paramJson.put("name", cloudFlareOptionService.getTargetHost());
		paramJson.put("content", targetIp);
		paramJson.put("ttl", "120");
		paramJson.put("proxied", false);

		String url = "https://" + cloudFlareOptionService.getHost() + cloudFlareOptionService.getZonesApiRoot() + "/"
				+ cloudFlareOptionService.getZoneId() + cloudFlareOptionService.getDnsApiUrl() + "/" + recordID;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPatch request = new HttpPatch(url);
			StringEntity params = new StringEntity(paramJson.toString());
			request.addHeader("x-auth-email", cloudFlareOptionService.getEmail());
			request.addHeader("Authorization", "Bearer " + cloudFlareOptionService.getKey());
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			String responseStr = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
			JSONObject responseJson = JSONObject.fromObject(responseStr);
			JSONObject responseResult = responseJson.getJSONObject("result");
			responseResult.put("zone_id", "");
			responseResult.put("zone_name", "");
			String[] hostnameArray = cloudFlareOptionService.getTargetHost().split(".");
			responseResult.put("name", hostnameArray[0]);
			responseResult.put("content", "");
			responseJson.put("result", responseResult);
			sendingMsg("Update worker DNS response: " + responseJson.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getTargetDnsRecordIdFromDnsList() {
		String url = "https://" + cloudFlareOptionService.getHost() + cloudFlareOptionService.getZonesApiRoot() + "/"
				+ cloudFlareOptionService.getZoneId() + cloudFlareOptionService.getDnsApiUrl();
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String responseStr = null;
		try {
			HttpGet request = new HttpGet(url);
			request.addHeader("x-auth-email", cloudFlareOptionService.getEmail());
			request.addHeader("Authorization", "Bearer " + cloudFlareOptionService.getKey());
			HttpResponse response = httpClient.execute(request);
			responseStr = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
			if (StringUtils.isBlank(responseStr)) {
				sendingMsg("Worker find DNS list error");
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		JSONObject json = JSONObject.fromObject(responseStr);
		JSONArray list = json.getJSONArray("result");
		JSONObject targetRecord = null;
		for (int i = 0; i < list.size() && targetRecord == null; i++) {
			JSONObject tmpJson = list.getJSONObject(i);
			if (cloudFlareOptionService.getTargetHost().equals(tmpJson.getString("name"))) {
				targetRecord = tmpJson;
			}
		}

		if (targetRecord != null) {
			return targetRecord.getString("id");
		}

		return null;
	}

	private void updateWork1DnsRecord(String targetIp) {
		String recordId = getTargetDnsRecordIdFromDnsList();
		if (recordId == null) {
			log.error("Can NOT find exists DNS records, will create new one");
			recordId = createDNS(targetIp);
			if (recordId == null) {
				sendingMsg("Can NOT create Worker 1 DNS record");
			}
		} else {
			editDNS(recordId, targetIp);
		}
	}
}
