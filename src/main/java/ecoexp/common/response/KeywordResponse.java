package ecoexp.common.response;

import java.util.*;

class keywordProgram {
	public String region;
	public Integer count;

	public keywordProgram(String region, int count) {
		this.region = region;
		this.count = count;
	}
}

public class KeywordResponse {
	public String keyword;
	public List<keywordProgram> programs = new ArrayList<keywordProgram>();

	public KeywordResponse() {}

	public void addProgram(String region, int count) {
		this.programs.add(new keywordProgram(region,count));
	}
}
