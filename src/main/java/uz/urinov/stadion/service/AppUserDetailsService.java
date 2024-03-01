package uz.urinov.stadion.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.urinov.stadion.repository.UserRepository;


@Service
@AllArgsConstructor
public class AppUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User topilmadi"));
    }
}
