package stacs.estate.cs5031p3code.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stacs.estate.cs5031p3code.model.po.User;

import java.util.Collection;
import java.util.List;

/**
 * The class for implementing UserDetails for using authentication and authorization.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityUser implements UserDetails {

    /**
     * A User object.
     */
    private User user;

    /**
     * Permissions within this user.
     */
    private List<String> permissions;

    /**
     * Permissions within this user in Spring Security format.
     */
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    /**
     * Override the method for getting all authorities.
     *
     * @return Return the permission collection.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities != null) {
            return this.authorities;
        }
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /**
     * Override the method for getting user password.
     *
     * @return Return the user password.
     */
    @Override
    public String getPassword() {
        return this.user.getUserPassword();
    }

    /**
     * Override the method for getting userName (user email in this system).
     *
     * @return Return the user email.
     */
    @Override
    public String getUsername() {
        return this.user.getUserEmail();
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
        return true;
    }
}
