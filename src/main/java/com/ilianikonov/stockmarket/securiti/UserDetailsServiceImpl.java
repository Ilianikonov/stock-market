package com.ilianikonov.stockmarket.securiti;

import com.ilianikonov.stockmarket.dao.DatabaseTraderRepository;
import com.ilianikonov.stockmarket.entity.Trader;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    DatabaseTraderRepository databaseTraderRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Trader trader = databaseTraderRepository.getTraderByName(username);
        Set<GrantedAuthority> grantedAuthorities = trader.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(trader.getName(), trader.getPassword().toString(), trader.getEnabled(), true, true, true,grantedAuthorities);
    }
}
