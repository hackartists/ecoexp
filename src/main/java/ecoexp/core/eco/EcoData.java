package ecoexp.core.eco;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opencsv.bean.CsvBindByName;
import java.io.StringReader;
import com.opencsv.bean.CsvToBeanBuilder;
import java.util.Iterator;
import com.opencsv.bean.CsvToBean;


public class EcoData {
	private static Logger logger = LoggerFactory.getLogger(EcoData.class);

	public Long num;
	public String name;
	public String theme;
	public String region;
	public String intro;
	public String detail;

	public EcoData(String[] data) {
		this.num = Long.parseLong(data[0]);
		this.name = data[1].replace("\"","");
		this.theme = data[2].replace("\"","");
		this.region = data[3].replace("\"","");
		this.intro = data[4].replace("\"","");
		this.detail = data[5].replace("\"","");
	}

	public String toString() {
		return "Name: " + this.name +
			"\nTheme: " + this.theme +
			"\nRegion: " + this.region +
			"\nIntro : " + this.intro +
			"\nDetails : "+ this.detail;
	}

	public static List<EcoData> parseFromCsv(final String csv)  throws IOException {
		logger.debug("In: parseFromCsv");
		String[] lines = csv.split("\n");
		List<EcoData> res = new ArrayList<EcoData>();

		for(int i=1; i< lines.length; i++) {
			String[] l = lines[i].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
			EcoData d= new EcoData(l);
			res.add(d);
			logger.debug("Data: {}\n\n\n", d);
		}

		return res;
	}
}
