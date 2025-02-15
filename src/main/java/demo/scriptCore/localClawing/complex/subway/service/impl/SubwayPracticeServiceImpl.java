package demo.scriptCore.localClawing.complex.subway.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import demo.scriptCore.localClawing.complex.subway.pojo.dto.SubwayPracticeAnswerDTO;
import demo.scriptCore.localClawing.complex.subway.pojo.dto.SubwayPracticeChoiceDTO;
import demo.scriptCore.localClawing.complex.subway.pojo.dto.SubwayPracticeDTO;
import demo.scriptCore.localClawing.complex.subway.pojo.dto.SubwayPracticeOutputDTO;
import demo.scriptCore.localClawing.complex.subway.pojo.dto.SubwayPracticeOutputQuestionDTO;
import demo.scriptCore.localClawing.complex.subway.pojo.dto.SubwayPracticeQuestionDTO;
import demo.scriptCore.localClawing.complex.subway.pojo.dto.SubwayPracticeSubAnswerDTO;
import demo.scriptCore.localClawing.complex.subway.service.SubwayPracticeService;
import demo.selenium.service.impl.AutomationTestCommonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class SubwayPracticeServiceImpl extends AutomationTestCommonService implements SubwayPracticeService {

	private static String mainUrl = "https://api-phx-hw02.yunxuetang.cn";
//	private static String token = "";
	@SuppressWarnings("unused")
	private static final int SINGLE_CHOICE = 0;
	@SuppressWarnings("unused")
	private static final int MULTIPLE_CHOICE = 1;
	private static final int JUDGE = 2;

	@Override
	public void forPractice(String token) {
		List<String> practiceIdList = getPracticeIdList(token);
		for (String practiceId : practiceIdList) {
			handlePractice(token, practiceId);
		}
	}

	private List<String> getPracticeIdList(String token) {
		String response = null;
		if (systemOptionService.isDev()) {
			FileUtilCustom fio = new FileUtilCustom();
			response = fio.getStringFromFile(System.getProperty("user.home") + "/tmp/getPracticeIdList.json");
		} else {
			String script = "curl " + "-H \"Content-Type: application/json\" " + "-H \"token: %s\" "
					+ "-H \"source: 501\" " + "--request GET \"" + mainUrl
					+ "/ote/upm/list/nocount?status=1&name=&offset=0&limit=12&oteGroupFuncReq=0\"";
			script = String.format(script, token);
			response = run(script);
		}
		List<String> idList = new ArrayList<>();
		JSONObject ja = JSONObject.fromObject(response);
		JSONArray datas = ja.getJSONArray("datas");
		for (int i = 0; i < datas.size(); i++) {
			JSONObject data = datas.getJSONObject(i);
			idList.add(data.getString("praId"));
		}

		return idList;
	}

	public List<String> getPracticeIdList_old(String token) {
		// 2025-02-11 对方 API 有更新
		String url = mainUrl + "/audit/todos/list";
		String requestParam = "{\"attributes\":[],\"catgCodes\":[\"practice\"],\"sceneTarget\":\"0\",\"startTime\":\"\",\"endTime\":\"\"}";
		List<String> idList = new ArrayList<>();
		HttpUtil h = new HttpUtil();
		try {
			String response = h.sendPost(url, requestParam, getRequestPropertyMap(token));

			JSONObject ja = JSONObject.fromObject(response);
			JSONArray datas = ja.getJSONArray("datas");
			for (int i = 0; i < datas.size(); i++) {
				JSONObject data = datas.getJSONObject(i);
				idList.add(data.getString("subjectId"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return idList;
	}

	private void handlePractice(String token, String practiceId) {
		SubwayPracticeDTO practice = saveExam(token, practiceId);

		List<SubwayPracticeQuestionDTO> listQues = practice.getListQues();
		SubwayPracticeOutputDTO outputDTO = new SubwayPracticeOutputDTO();
		outputDTO.setQuestions(new ArrayList<>());
		SubwayPracticeOutputQuestionDTO questionOutputDTO = null;

		for (int i = 0; i < listQues.size(); i++) {
			SubwayPracticeQuestionDTO question = listQues.get(i);
			if (question.getChoiceItems() != null && !question.getChoiceItems().isEmpty()) {
				questionOutputDTO = new SubwayPracticeOutputQuestionDTO();
				questionOutputDTO.setQuestion(question.getContent());
				questionOutputDTO.setChoices(new ArrayList<>());
				questionOutputDTO.setAnswers(new ArrayList<>());
				for (SubwayPracticeChoiceDTO choice : question.getChoiceItems()) {
					questionOutputDTO.getChoices().add(choice.getOrderIndex() + ", " + choice.getItemContent());
					if ("1".equals(choice.getAnswer())) {
						questionOutputDTO.getAnswers().add(choice.getOrderIndex() + ", " + choice.getItemContent());
					}
				}
				outputDTO.getQuestions().add(questionOutputDTO);
			} else {
				questionOutputDTO = new SubwayPracticeOutputQuestionDTO();
				questionOutputDTO.setQuestion(question.getContent());
				questionOutputDTO.setAnswers(new ArrayList<>());
				if (handleJudgeQuestion(token, question, practice)) {
					questionOutputDTO.getAnswers().add("T");
				} else {
					questionOutputDTO.getAnswers().add("F");
				}
				outputDTO.getQuestions().add(questionOutputDTO);
			}
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(outputDTO);
		FileUtilCustom f = new FileUtilCustom();
		f.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8),
				(System.getProperty("user.home") + "/tmp/subway/" + practice.getPraName() + "_answer.txt"));
	}

	private boolean sendAnswer(String practiceId, SubwayPracticeAnswerDTO dto, String token) {
		String response = null;
		if (systemOptionService.isDev()) {
			return true;
		}

		String urlStr = mainUrl + "/ote/upm/%s/users/%s/answer";
		urlStr = String.format(urlStr, practiceId, dto.getPuId());
		
		JSONObject mainAnswer = new JSONObject();
		mainAnswer.put("isH5Exam", 0);
		mainAnswer.put("uniqueId", dto.getUniqueId());
		mainAnswer.put("pumId", dto.getPumId());
		mainAnswer.put("isLastQues", 0);
		mainAnswer.put("answers", dto.getAnswers());

		String script = "curl -H \"Content-Type: application/json\" " 
				+ "-H \"token: %s\" "
				+ "-H \"source: 501\" "
				+ "--data '%s'"
				+ "--request PUT \"" + urlStr + "\"";
		script = String.format(script, token, mainAnswer.toString());
		response = run(script);

		JSONArray responseJsonArray = JSONArray.fromObject(response.toString());
		JSONObject answerResult = responseJsonArray.getJSONObject(0);
		if (answerResult.containsKey("corrected")) {
			String corrected = answerResult.getString("corrected");
			return "1".equals(corrected);
		} else {
			System.out.println(response);
		}
		return false;
	}

	@SuppressWarnings("unused")
	private boolean sendAnswer_OLD(String practiceId, SubwayPracticeAnswerDTO dto, String token) {
		String url = mainUrl + "/ote/upm/%s/users/%s/answer";
		url = String.format(url, practiceId, dto.getPuId());

		JSONObject mainAnswer = new JSONObject();
		mainAnswer.put("isH5Exam", 0);
		mainAnswer.put("uniqueId", dto.getUniqueId());
		mainAnswer.put("pumId", dto.getPumId());
		mainAnswer.put("isLastQues", 0);

		mainAnswer.put("answers", dto.getAnswers());

		try {
			HttpURLConnection conn = null;
			StringBuilder response = new StringBuilder();

			byte[] postData = mainAnswer.toString().getBytes(StandardCharsets.UTF_8);

			try {

				URL myurl = new URI(url).toURL();
				conn = (HttpURLConnection) myurl.openConnection();

				conn.setDoOutput(true);
				conn.setRequestMethod("PUT");

				Map<String, String> requestPropertyMap = getRequestPropertyMap(token);
				for (Entry<String, String> entry : requestPropertyMap.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}

				if (postData != null) {
					DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
					wr.write(postData);
					wr.flush();
				}

				BufferedReader in = null;
				if ("gzip".equals(conn.getContentEncoding())) {
					in = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream())));
				} else {
					in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
				}

				String line;

				while ((line = in.readLine()) != null) {
					response.append(line);
					response.append(System.lineSeparator());
				}

			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
//			System.out.println(response);
//			[{"examQuesId":"466a37bd-0bb5-4ab0-82ed-deedc25ca49d","quesType":2,"answers":null,"keywords":null,"fillinAnswers":null,"fillinCorrectedInfo":null,"judgeAnswer":0,"explainText":"","answerPlay":null,"explainPlay":null,"corrected":0,"allowViewSingleQuesResult":1,"allowViewSingleQuesAnswer":1,"allowViewSingleQuesExplain":1}]
			JSONArray responseJsonArray = JSONArray.fromObject(response.toString());
			JSONObject answerResult = responseJsonArray.getJSONObject(0);
			if (answerResult.containsKey("corrected")) {
				String corrected = answerResult.getString("corrected");
				return "1".equals(corrected);
			} else {
				System.out.println(response);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return false;
	}

	private SubwayPracticeDTO saveExam(String token, String practiceId) {
		String response = null;
		if (systemOptionService.isDev()) {
			FileUtilCustom fio = new FileUtilCustom();
			response = fio.getStringFromFile(System.getProperty("user.home") + "/tmp/getExamDetail.json");
		} else {
			String script = "curl -H \"Content-Type: application/json\" " + "-H \"token: %s\" -H \"source: 501\" "
					+ "--request GET \"" + mainUrl + "/ote/upm/start?praId=%s&usedTime=0&praBatchId=\"";
			script = String.format(script, token, practiceId);
			response = run(script);
		}
		SubwayPracticeDTO practiceDTO = new Gson().fromJson(response, SubwayPracticeDTO.class);

		FileUtilCustom f = new FileUtilCustom();
		String filePath = System.getProperty("user.home") + "/tmp/subway/" + practiceDTO.getPraName() + "_question.txt";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(practiceDTO);
		f.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), filePath);

		return practiceDTO;
	}

	public SubwayPracticeDTO saveExam_old(String practiceId, String token) {
		// 2025-02-11 对方 API 更新
		HttpUtil h = new HttpUtil();

		String url = mainUrl + "/ote/upm/start?praId=%s&usedTime=6&praBatchId=";
		url = String.format(url, practiceId);

		Map<String, String> parameters = new HashMap<>();
		Map<String, String> requestPropertyMap = getRequestPropertyMap(token);
		String response = null;
		try {
			response = h.sendGet(url, parameters, requestPropertyMap);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		SubwayPracticeDTO practiceDTO = new Gson().fromJson(response, SubwayPracticeDTO.class);

		FileUtilCustom f = new FileUtilCustom();
		String filePath = System.getProperty("user.home") + "/tmp/subway/" + practiceDTO.getPraName() + "_question.txt";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonString = gson.toJson(practiceDTO);
		f.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), filePath);

		return practiceDTO;
	}

	private boolean handleJudgeQuestion(String token, SubwayPracticeQuestionDTO question, SubwayPracticeDTO practice) {
		if (question.getSubmitQuesAnswers() != null) {
			List<String> answers = question.getSubmitQuesAnswers();
			Integer corrected = question.getCorrected();
			String answer = answers.get(0);
			if (1 == (corrected)) {
				return "1".equals(answer);
			} else {
				return !"1".equals(answer);
			}
		} else {
			return sendAnswerForJudgeQuestion(token, question, practice);
		}
	}

	private boolean sendAnswerForJudgeQuestion(String token, SubwayPracticeQuestionDTO question,
			SubwayPracticeDTO practice) {
		String uniqueId = practice.getUniqueId();
		String pumId = practice.getPumId();
		String puId = practice.getPuId();
		SubwayPracticeSubAnswerDTO subAnswer = new SubwayPracticeSubAnswerDTO();
		subAnswer.setQuesId(question.getId());
		subAnswer.setLastSubmitTime(new Date().getTime());
		subAnswer.setAnswer(Arrays.asList("1"));
		subAnswer.setExamQuesId(question.getExamQuesId());
		subAnswer.setQuesType(JUDGE);
		SubwayPracticeAnswerDTO answerDTO = new SubwayPracticeAnswerDTO();
		answerDTO.setAnswers(Arrays.asList(subAnswer));
		answerDTO.setPuId(puId);
		answerDTO.setPumId(pumId);
		answerDTO.setUniqueId(uniqueId);
		boolean answerFlag = sendAnswer(practice.getPraId(), answerDTO, token);
		return answerFlag;
	}

	private Map<String, String> getRequestPropertyMap(String token) {
		Map<String, String> requestPropertyMap = new HashMap<>();
		requestPropertyMap.put("token", token);
		requestPropertyMap.put("source", "501");
		requestPropertyMap.put("Content-Type", "application/json");
		requestPropertyMap.put("Accept", "*/*");
		requestPropertyMap.put("Accept-Encoding", "gzip, deflate, br");
		requestPropertyMap.put("Connection", "keep-alive");
		requestPropertyMap.put("Cache-Control", "no-cache");
		requestPropertyMap.put("User-Agent", "PostmanRuntime/7.43.0");
//		requestPropertyMap.put("", "");
		return requestPropertyMap;
	}

	private static String run(String script) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		System.out.println("command: " + script);
		processBuilder.command(script);
		try {
			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				if (output.length() == 0 && !(line.startsWith("{") || line.startsWith("["))) {
					continue;
				}
				output.append(line + "\n");
			}
			int exitVal = process.waitFor();
			if (exitVal == 0) {
				return output.toString();
			} else {
				// abnormal...
//				return "error";
				return output.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "error";
		}
	}
}
