package vn.leoo.common.log;

import java.io.BufferedReader;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class LogUtils {
	@Autowired
	private Gson gson;

	@Autowired
	private HttpServletRequest request;

	// with only request param
	public EntryLog buildWithReqParams(String logType, String input, String output, String errorCode) {
		if (input == null) {
			return buildWithReqParams(logType, output, errorCode);
		}
		EntryLog entryLog = buildReq(logType, output, errorCode);
		entryLog.setInput(gson.toJson(request.getParameterMap()));
		return entryLog;
	}

	// with only request param with input
	public EntryLog buildWithReqParams(String logType, String output, String errorCode) {
		EntryLog entryLog = buildReq(logType, output, errorCode);
		entryLog.setInput(gson.toJson(request.getParameterMap()));
		return entryLog;
	}

	// with only request body with input
	public EntryLog buildWithReqBody(String logType, String input, String output, String errorCode) {
		if (input == null) {
			return buildWithReqBody(logType, output, errorCode);
		}
		EntryLog entryLog = buildReq(logType, output, errorCode);
		entryLog.setInput(input);
		return entryLog;
	}

	// with only request body
	public EntryLog buildWithReqBody(String logType, String output, String errorCode) {
		String reqBody = null;

		return new EntryLog();
	}

	// both request param and request body
	public EntryLog buildWithReq(String logType, String output, String errorCode) {
		EntryLog entryLog = buildReq(logType, output, errorCode);
		return getEntryLog(entryLog);
	}

	// request with input custom
	public EntryLog buildWithReq(String logType, String input, String output, String errorCode) {
		EntryLog entryLog = buildReq(logType, output, errorCode);
		if (input == null) {
			return getEntryLog(entryLog);
		}
		entryLog.setInput(input);
		return entryLog;
	}

	private EntryLog getEntryLog(EntryLog entryLog) {
		String param = gson.toJson(request.getParameterMap());
		String reqBody = null;
		try {
			reqBody = readData(request.getReader());
		} catch (IOException e) {
			/* e.printStackTrace(); */
		}
		String inputAll = String.format("param: %s --- body: %s", param, reqBody);
		entryLog.setInput(inputAll);
		return entryLog;
	}

	private EntryLog buildReq(String logType, String output, String errorCode) {
		EntryLog entryLog = new EntryLog();
		entryLog.setLogType(logType);
		entryLog.setMethod(request.getMethod());
		entryLog.setUrl(request.getRequestURL().toString());
		entryLog.setOutput(output);
		entryLog.setErrorCode(errorCode);
		return entryLog;
	}

	private String readData(BufferedReader br) {
		if (br == null) {
			return null;
		}
		StringBuilder strBuild = new StringBuilder();
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				strBuild.append(line);
			}
		} catch (IOException e) {
		}
		return strBuild.toString();
	}
}
