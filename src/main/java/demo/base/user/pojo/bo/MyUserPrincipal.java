package demo.base.user.pojo.bo;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import demo.base.user.pojo.po.Users;

public class MyUserPrincipal implements UserDetails {

	private static final long serialVersionUID = 3333800775540529992L;

	private Users user;

	private List<String> roles;

	private List<String> auths;

	private List<String> departments;

	private List<String> organizations;

	@Override
	public String toString() {
		return "MyUserPrincipal [user=" + user + ", roles=" + roles + ", auths=" + auths + ", departments="
				+ departments + ", organizations=" + organizations + "]";
	}

	public List<String> getAuths() {
		return auths;
	}

	public void setAuths(List<String> auths) {
		this.auths = auths;
	}

	public List<String> getDepartments() {
		return departments;
	}

	public void setDepartments(List<String> departments) {
		this.departments = departments;
	}

	public List<String> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<String> organizations) {
		this.organizations = organizations;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if (roles != null && roles.size() > 0) {
			for (String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		if (user != null) {
			return user.getPwd();
		} else {
			return null;
		}
	}

	@Override
	public String getUsername() {
		if (user != null) {
			return user.getUserName();
		} else {
			return null;
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		if (user != null) {
			return user.getAccountNonExpired();
		} else {
			return false;
		}
	}

	@Override
	public boolean isAccountNonLocked() {
		if (user != null) {
			return user.getAccountNonLocked();
		} else {
			return false;
		}
	}

	@Override
	public boolean isCredentialsNonExpired() {
		if (user != null) {
			return user.getCredentialsNonExpired();
		} else {
			return false;
		}
	}

	@Override
	public boolean isEnabled() {
		if (user != null) {
			return user.getEnable();
		} else {
			return false;
		}
	}

}
