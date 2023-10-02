package com.hasan.foraty.myblogapplication;

import com.hasan.foraty.myblogapplication.entity.Users;
import com.hasan.foraty.myblogapplication.payload.SignUpDto;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModelMapperTest {



    @Test
    void singUpDtoToUsers(){
        ModelMapper modelMapper = new ModelMapper();
        String name ="Hasan";
        String userName = "Sahan";
        String email = "Hasan@Gamil.com";
        String password = "jfdlajg";
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setName(name);
        signUpDto.setEmail(email);
        signUpDto.setUsername(userName);
        signUpDto.setPassword(password);
        Users users = modelMapper.map(signUpDto,Users.class);
        assertEquals(users.getName(),name);
        assertEquals(users.getEmail(),email);
        assertEquals(users.getUsername(),userName);
        assertEquals(users.getPassword(),password);

    }
}
