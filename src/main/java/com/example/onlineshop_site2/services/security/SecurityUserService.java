package com.example.onlineshop_site2.services.security;
import com.example.onlineshop_site2.exceptions.AppError;
import com.example.onlineshop_site2.models.dtos.RegistrationUserDto;
import com.example.onlineshop_site2.models.dtos.requests.ChangePasswordReq;
import com.example.onlineshop_site2.models.entities.User;
import com.example.onlineshop_site2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {
    private UserRepository userRepository;
    private SecurityRoleService roleService;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleService(SecurityRoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", email)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setEmail(registrationUserDto.getEmail());
        user.setRoles(List.of(roleService.getUserRole()));
        user.setFirstName(registrationUserDto.getFirstName());
        user.setLastName(registrationUserDto.getLastName());
        user.setGender(registrationUserDto.getGender());
        user.setDOB(registrationUserDto.getDOB());
        user.setCountry(registrationUserDto.getCountry());
        user.setState(registrationUserDto.getState());
        user.setCity(registrationUserDto.getCity());
        user.setPhone(registrationUserDto.getPhone());
        user.setNewsletter(registrationUserDto.getNewsletter());
        return userRepository.save(user);
    }

    public ResponseEntity<?> changePas(ChangePasswordReq req){
        User user = findByEmail(req.getEmail()).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", req.getEmail())
        ));
        if(!req.getConfirmPassword().equals(req.getPassword())){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
