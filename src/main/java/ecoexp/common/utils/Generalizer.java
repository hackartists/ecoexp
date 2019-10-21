package ecoexp.common.utils;

public class Generalizer {
    public static String region(String region) {
        String filtered="시군읍면";
		region = region.replace(" ","");
		if (region.contains("광역시") || region.contains("특별시")) {
			return region;
		}

        int last=region.length()-1;
        String res = region;
        for (int i=0; i<filtered.length(); i++) {
            if (region.charAt(last)==filtered.charAt(i)) {
                res = region.substring(0, last);
                break;
            }
        }

        return res;
    }
}