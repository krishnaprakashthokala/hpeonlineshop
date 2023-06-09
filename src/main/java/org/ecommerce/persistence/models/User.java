package org.ecommerce.persistence.models;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.ecommerce.persistence.constraints.EmailUnique;
import org.ecommerce.persistence.constraints.FieldMatch;
import org.ecommerce.persistence.constraints.FieldNotMatch;
import org.ecommerce.persistence.constraints.UserCurrentPassword;
import org.ecommerce.persistence.constraints.UsernameUnique;
import org.ecommerce.persistence.models.User.UserChangePassword;
import org.ecommerce.persistence.models.User.UserCreation;

/**
 * @author sergio
 */
@Entity
@Table(name = "USERS")
@FieldMatch(first = "passwordClear", second = "confirmPassword", message = "{user.pass.not.match}", groups = {
		UserCreation.class, UserChangePassword.class })
@FieldNotMatch(first = "currentClearPassword", second = "passwordClear", message = "{user.current.pass.not.match}", groups = {
		UserChangePassword.class })
public class User implements Serializable, UserDetails {

	/*
	 * Marker interface for grouping validations to be applied at the time of
	 * creating a (new) user.
	 */
	public interface UserCreation {
	}

	/*
	 * Marker interface for grouping validations to be applied at the time of
	 * updating a (existing) user.
	 */
	public interface UserUpdate {
	}

	/*
	 * Marker interface for grouping validations to be applied at the time of change
	 * user password.
	 */
	public interface UserChangePassword {
	}

	/*
	 * Marker interface for grouping validations to be applied at the time of
	 * updating a user status by administrator.
	 */
	public interface UserStatusUpdate {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(DataTablesOutput.View.class)
	private Long id;

	@NotBlank(message = "{user.username.notnull}", groups = { UserCreation.class, UserUpdate.class })
	@Size(min = 5, max = 15, message = "{user.username.size}", groups = { UserCreation.class, UserUpdate.class })
	@UsernameUnique(message = "{user.username.unique}", groups = { UserCreation.class, UserUpdate.class })
	@Column(nullable = false, length = 30, unique = true)
	@JsonView(DataTablesOutput.View.class)
	private String username;

	@NotBlank(message = "{user.current.pass.notnull}", groups = { UserChangePassword.class })
	@UserCurrentPassword(message = "{user.current.pass.not.match}", groups = { UserChangePassword.class })
	@Transient
	private String currentClearPassword;

	@NotBlank(message = "{user.pass.notnull}", groups = { UserCreation.class, UserChangePassword.class })
	@Size(min = 8, max = 25, message = "{user.pass.size}", groups = { UserCreation.class, UserChangePassword.class })
	@Transient
	private String passwordClear;

	@NotBlank(message = "{user.confirm.pass.notnull}", groups = { UserCreation.class, UserChangePassword.class })
	@Transient
	private String confirmPassword;

	@Column(length = 60)
	private String password;

	@NotBlank(message = "{user.email.notnull}", groups = { UserCreation.class, UserUpdate.class })
	@Email(message = "{user.email.invalid}", groups = { UserCreation.class, UserUpdate.class })
	@EmailUnique(message = "{user.email.unique}", groups = { UserCreation.class, UserUpdate.class })
	@Column(nullable = false, length = 90, unique = true)
	@JsonView(DataTablesOutput.View.class)
	private String email;

	@NotBlank(message = "{user.fullname.notnull}", groups = { UserCreation.class, UserUpdate.class })
	@Size(min = 8, max = 25, message = "{user.fullname.size}", groups = { UserCreation.class, UserUpdate.class })
	@Column(length = 100)
	@JsonView(DataTablesOutput.View.class)
	private String fullName;

	@Column(nullable = true)
	@JsonView(DataTablesOutput.View.class)
	private Date lastLoginAccess;

	@NotNull(message = "{user.enabled.notnull}", groups = { UserStatusUpdate.class })
	@JsonView(DataTablesOutput.View.class)
	private Boolean enabled = true;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
	private Set<Authority> authorities = new HashSet();

	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Review> reviews = new HashSet();

	@OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Avatar avatar;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "USER_ADDRESSES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID"))
	private Set<Address> addresses = new HashSet();

	@ManyToOne(fetch = FetchType.EAGER)
	private Country country;

	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private Set<Order> orders = new HashSet();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender = Gender.MAN;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(nullable = false)
	private Date birthday = new Date();

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<TasteBoolPreference> tasteBoolPreferences = new HashSet<TasteBoolPreference>();

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<TastePreferences> tastePreferences = new HashSet<TastePreferences>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentClearPassword() {
		return currentClearPassword;
	}

	public void setCurrentClearPassword(String currentClearPassword) {
		this.currentClearPassword = currentClearPassword;
	}

	public String getPasswordClear() {
		return passwordClear;
	}

	public void setPasswordClear(String passwordClear) {
		this.passwordClear = passwordClear;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getLastLoginAccess() {
		return lastLoginAccess;
	}

	public void setLastLoginAccess(Date lastLoginAccess) {
		this.lastLoginAccess = lastLoginAccess;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(Authority authority) {
		if (!this.authorities.contains(authority)) {
			this.authorities.add(authority);
			authority.addUser(this);
		}
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void addAddress(Address address) {
		if (!addresses.contains(address)) {
			addresses.add(address);
		}
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public void addOrder(Order order) {
		if (!orders.contains(order)) {
			orders.add(order);
			order.setCustomer(this);
		}
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		Calendar today = Calendar.getInstance();
		Calendar birthDate = Calendar.getInstance();
		int age = 0;
		birthDate.setTime(birthday);
		if (birthDate.after(today)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}
		age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		// If birth date is greater than todays date (after 2 days adjustment of leap
		// year) then decrement age one year
		if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3)
				|| (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
			age--;
			// If birth date and todays date are of same month and birth day of month is
			// greater than todays day of month then decrement age
		} else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH))
				&& (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
			age--;
		}
		return age;
	}

	public Set<TasteBoolPreference> getTasteBoolPreferences() {
		return tasteBoolPreferences;
	}

	public void setTasteBoolPreferences(Set<TasteBoolPreference> tasteBoolPreferences) {
		this.tasteBoolPreferences = tasteBoolPreferences;
	}

	public void addTasteBoolPreference(TasteBoolPreference tasteBoolPreference) {
		if (!tasteBoolPreferences.contains(tasteBoolPreference)) {
			tasteBoolPreferences.add(tasteBoolPreference);
			tasteBoolPreference.setUser(this);
		}
	}

	public Set<TastePreferences> getTastePreferences() {
		return tastePreferences;
	}

	public void setTastePreferences(Set<TastePreferences> tastePreferences) {
		this.tastePreferences = tastePreferences;
	}

	public void addTastePreferences(TastePreferences tastePreference) {
		if (!this.tastePreferences.contains(tastePreference)) {
			tastePreferences.add(tastePreference);
			tastePreference.setUser(this);
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username=" + username + ", email=" + email + ", fullName=" + fullName
				+ ", lastLoginAccess=" + lastLoginAccess + ", enabled=" + enabled + ", authorities=" + authorities
				+ ", avatar=" + avatar + '}';
	}
}
