package com.pms.projectmanagement.services.register;

import com.pms.projectmanagement.MailSenderService;
import com.pms.projectmanagement.dtos.UserDto;
import com.pms.projectmanagement.elasticsearch.model.UserES;
import com.pms.projectmanagement.elasticsearch.service.UserServiceES;
import com.pms.projectmanagement.enums.RoleType;
import com.pms.projectmanagement.models.User;
import com.pms.projectmanagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;

    private final UserServiceES userServiceES;
    private final ModelMapper modelMapper;

    private final MailSenderService mailSenderService;

    @Override
    public void register(UserDto user) {
        User saveUser = modelMapper.map(user, User.class);
        saveUser.setId(UUID.randomUUID());
        saveUser.setActiveGuide(UUID.randomUUID().toString());
   /* mailSenderService.sendMail(saveUser.getEmail(),
            saveUser.getActiveGuide(),saveUser.getUsername());*/
        saveUser.setRoleType(RoleType.USER);
        saveUser.setPassword(new BCryptPasswordEncoder().encode(saveUser.getPassword()));
        UserES savedUserES =userServiceES.createNewUser(UserES.builder()
                .id(saveUser.getId().toString())
                .username(saveUser.getUsername())
                .build());
        System.out.println("user es = " + savedUserES);
        userRepository.save(saveUser);
    }
}
