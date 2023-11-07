package com.ilianikonov.stockmarket.securiti;

import com.ilianikonov.stockmarket.dao.DatabaseTraderRepository;
import com.ilianikonov.stockmarket.entity.Trader;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final DatabaseTraderRepository databaseTraderRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Trader trader = databaseTraderRepository.getTraderByName(username);
//        if (trader == null){
//            Set<GrantedAuthority> grantedAuthorities1 = new HashSet<>();
//            grantedAuthorities1.add(new SimpleGrantedAuthority("USER"));
//            return new User(username, "1234",grantedAuthorities1);
//        }
        Set<GrantedAuthority> grantedAuthorities = trader.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(trader.getName(), trader.getPassword(), trader.getEnabled(), true, true, true,grantedAuthorities);
    }
}
