package com.medilabo.front.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public abstract class SpringHelper {
    public static <T extends Object> ResponseEntity<T> getRedirectResponseEntity(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url));

        return new ResponseEntity<T>(headers, HttpStatus.FOUND);
    }
}
