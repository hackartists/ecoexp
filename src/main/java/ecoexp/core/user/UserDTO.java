package ecoexp.core.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="users")
public class UserDTO {

	@NotNull
	@Unique
	@NaturalId
	@Column(name="USERNAME")
	private String username;

	@NotNull
	@Column(name="PASSWORD")
	private String password;

	@NotNull
	@Column(name="JWT")
	private String jwt;

	@Id
	@Column(name="UID")
	@GeneratedValue(generator="LOCATION_SEQ_GEN",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(sequenceName="location_sequence_name",name="LOCATION_SEQ_GEN",allocationSize=1)
	private Long id;

	public final Long getId() {
		return id;
	}

	public final void setId(final Long id) {
		this.id = id;
	}

	public UserDTO(){ }
	public UserDTO(String username, String password, String jwt) {
		this.username = username;
		this.password = password;
		this.jwt = jwt;
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(final String username) {
		this.username = username;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(final String password) {
		this.password = password;
	}

	public final String getJwt() {
		return jwt;
	}

	public final void setJwt(final String jwt) {
		this.jwt = jwt;
	}

}
