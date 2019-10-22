package ecoexp.core.program;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import ecoexp.common.request.UpdateProgramRequest;
import java.util.function.Supplier;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.NaturalId;

import com.google.common.hash.Hashing;
import ecoexp.common.utils.Generalizer;
import ecoexp.core.raw.EcoData;
import ecoexp.core.region.RegionDTO;
import ecoexp.core.theme.ThemeDTO;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "programs")
public class ProgramDTO {
	@Id
	@Column(name="PROGRAM_ID", updatable = false, nullable = false)
	@GeneratedValue(generator="LOCATION_SEQ_GEN",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="location_sequence_name",name="LOCATION_SEQ_GEN",allocationSize=1)
    private Long id;

	@NotNull
	@Column(name="NAME")
    private String name;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "theme_links",
			joinColumns = @JoinColumn(name="PROGRAM_ID"),
			inverseJoinColumns= @JoinColumn(name="THEME_ID"))
    private Set<ThemeDTO> linkedThemes=new HashSet<ThemeDTO>();

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "region_links",
			joinColumns = @JoinColumn(name="PROGRAM_ID"),
			inverseJoinColumns= @JoinColumn(name="REGION_ID"))
	private Set<RegionDTO> linkedRegions=new HashSet<RegionDTO>();


	@NotNull
	@Column(name="REGION")
	private String region;

	@Lob
	@Column(name="DESCRIPTION")
	private String desc;

	@Lob
	@Column(name="DETAIL")
	private String detail;

	@Column(name="THEME")
	private String theme;

	@Unique
	@NaturalId
	@Column(name="CODE")
	private String code;

	public ProgramDTO() {
    }

	public ProgramDTO(UpdateProgramRequest data) {
		setConstructor(()->{this.id = data.id;
				this.code=data.code;
			}, data);
	}

    public ProgramDTO(EcoData data) {
		setConstructor(()->{
				String md = Hashing.sha256().hashString(String.format("%s::%s::%s", data.name,data.region, data.theme), StandardCharsets.UTF_8).toString().substring(0,4);
				this.id = Long.parseLong(md, 16);
				this.code=String.format("prg%d", this.id);
			}, data);
    }

	private void setConstructor(Runnable idSetter, EcoData data) {
		idSetter.run();
		this.name=data.name;
		this.region=data.region;
		this.desc = data.intro;
		this.detail = data.detail;
		this.theme = data.theme;

		Arrays.asList(data.theme.split(",")).stream()
			.filter(el->!el.equals(" ")&&!el.equals(""))
			.forEach(el->this.linkedThemes.add(new ThemeDTO(el,this)));

		Arrays.stream(data.region.split(",")).forEach(x->{
				Arrays.stream(x.split("~")).forEach(y->{
						List<String> r= Arrays.stream(y.split(" ", 3)).filter(el->!el.equals("")).collect(Collectors.toList());

						for (int i=0; i<2 && i<r.size(); i++) {
							this.linkedRegions.add(new RegionDTO(Generalizer.region(r.get(i)),this));
						}
					});
			});
	}

	public final String getCode() {
		return code;
	}

	public final void setCode(final String code) {
		this.code = code;
	}

	public final String getTheme() {
		return theme;
	}

	public final void setTheme(final String theme) {
		this.theme = theme;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final Set<ThemeDTO> getLinkedThemes() {
        return linkedThemes;
    }

    public final void setLinkedThemes(final Set<ThemeDTO> themeId) {
        this.linkedThemes = themeId;
    }

	public Set<RegionDTO> getLinkedRegions() {
		return linkedRegions;
	}

	public void setLinkedRegions(Set<RegionDTO> linkedRegions) {
		this.linkedRegions = linkedRegions;
	}
}
