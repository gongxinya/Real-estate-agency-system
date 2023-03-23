package stacs.estate.cs5031p3code.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stacs.estate.cs5031p3code.model.po.User;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser implements UserDetails {
    private User user;
    private List<String> permissions;

    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public SecurityUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.authorities != null) {
            return this.authorities;
        }
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return this.user.getUserPassword();
    }

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
