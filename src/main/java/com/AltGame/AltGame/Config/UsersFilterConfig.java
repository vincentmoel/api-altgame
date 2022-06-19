package com.AltGame.AltGame.Config;

import com.AltGame.AltGame.Entity.RoleEntity;
import com.AltGame.AltGame.Entity.UserEntity;
import com.AltGame.AltGame.Repository.RoleRepo;
import com.AltGame.AltGame.Repository.UserRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class UsersFilterConfig implements UserDetailsService {
    private final Logger logger = LogManager.getLogger(UsersFilterConfig.class);
    private final PasswordEncoder passwordEncoder;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    public UsersFilterConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);
        if(userRepo.existsByUsername(username)){
            logger.info(username+ "found .!");
        }else{
            logger.error("user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Optional<RoleEntity> level = roleRepo.findById(user.getRoleId());
        authorities.add(new SimpleGrantedAuthority(level.get().getName()));
        return new
                User(user.getUsername(), user.getPassword(), authorities);
    }
}
