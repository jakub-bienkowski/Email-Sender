package com.jb.mailservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock
    Service service;

    @Test
    void send() {

        String email = "jbienkowski94@gmail.com";
        String subject = "hello";
        String message = "This is my message";
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("subject", subject);
        map.put("message", message);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("subject", subject);
        map2.put("message", message);

        when(service.send(anyMap())).thenReturn(true);

        Assertions.assertTrue(service.send(map));
    }
}