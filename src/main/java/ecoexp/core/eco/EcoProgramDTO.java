package ecoexp.core.eco;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.Lob;

@Entity
@Table(name = "programs")
public class EcoProgramDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PROGRAM_ID")
    private Long id;

	@NotNull
	@Column(name="NAME")
    private String name;

	@NotNull
	@Column(name="THEME_ID")
    private String themeId;

	@NotNull
	@Column(name="REGION")
	private String region;

	@Lob
	@Column(name="DESCRIPTION")
	private String desc;

	@Lob
	@Column(name="DETAIL")
	private String detail;

	public EcoProgramDTO() {
    }

    public EcoProgramDTO(EcoData data) {
		this.id = data.num;
        this.name=data.name;
		this.themeId=data.theme;
		this.region=data.region;
		this.desc = data.intro;
		this.detail = data.detail;
    }

	public final String getRegion() {
		return region;
	}

	public final String getDesc() {
		return desc;
	}

	public final void setDesc(final String desc) {
		this.desc = desc;
	}

	public final String getDetail() {
		return detail;
	}

	public final void setDetail(final String detail) {
		this.detail = detail;
	}

	public final void setRegion(final String region) {
		this.region = region;
	}

    @Override
    public String toString() {
        return "";
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getThemeId() {
        return themeId;
    }

    public final void setThemeId(final String themeId) {
        this.themeId = themeId;
    }
}
