package ecoexp.algorithm;

import ecoexp.common.response.RecommendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ecoexp.core.region.RegionDTO;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import ecoexp.core.program.ProgramDTO;
import java.util.List;
import java.util.Set;

public class WeightedRegionSearch implements SearchAlgorithm {
	private Logger logger = LoggerFactory.getLogger(WeightedRegionSearch.class);

	private class recommendScore implements Comparable<recommendScore> {
		public String code;
		public int themeScore;
		public int descScore;
		public int detailScore;
		private int themeWeight=2;
		private int descWeight=-1;
		private int detailWeight=1;

		public recommendScore() { }
		public recommendScore(int themeScore, int descScore, int detailScore) {
			this.themeScore = themeScore;
			this.descScore = descScore;
			this.detailScore = detailScore;
		}

		@Override
		public int compareTo(recommendScore score) {
			int x = this.getThemeScore()+this.getDescScore()+this.getDetailScore();
			int y = score.getThemeScore()+score.getDescScore()+score.getDetailScore();

			return x-y;
		}

		@Override
		public String toString() {
			return String.format("%s: %d -> %d:%d:%d",this.code, this.getTotalScore(), this.getThemeScore(), this.getDescScore(), this.getDetailScore());
		}

		public int getTotalScore() {
			return this.getThemeScore()+this.getDescScore()+this.getDetailScore();
		}

		public int getThemeScore() {
			return this.themeScore*this.themeWeight;
		}

		public int getDescScore() {
			return this.descScore*this.descWeight;
		}

		public int getDetailScore() {
			return this.detailScore*this.detailWeight;
		}
	}

	private int getCountOfOccurence(String base, String keyword) {
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){
			lastIndex = base.indexOf(keyword,lastIndex);

			if(lastIndex != -1){
				count ++;
				lastIndex += keyword.length();
			}
		}

		return count;
	}

	private recommendScore compare(recommendScore x,final ProgramDTO el, String keyword) {
		recommendScore cur=new recommendScore(getCountOfOccurence(el.getTheme(),keyword),
											  getCountOfOccurence(el.getDesc(),keyword),
											  getCountOfOccurence(el.getDetail(),keyword));
		recommendScore res=x;
		if (cur.compareTo(res) > 0) {
			res = cur;
			res.code = el.getCode();
		}

		return res;
	}

	@Override
	public RecommendResponse recommendPlace(RegionDTO region, String keyword) {
		logger.debug("In: recommendPlace");
		RecommendResponse res = new RecommendResponse();
		Map<String,recommendScore> map = new HashMap<>();
		recommendScore maxScore = new recommendScore(-1,0,0);

		Set<ProgramDTO> data = region.getLinkedPrograms();

		for(ProgramDTO el: data) {
			maxScore = compare(maxScore, el, keyword);
		}

		res.program = maxScore.code;
		logger.debug("Out: recommendPlace{}",maxScore);

		return res;
	}

}
