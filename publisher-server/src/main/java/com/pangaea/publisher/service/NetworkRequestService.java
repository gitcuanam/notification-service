package com.pangaea.publisher.service;

import com.pangaea.publisher.exception.BadRequestException;
import com.pangaea.publisher.exception.RestClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class NetworkRequestService {

    @Autowired
    private RestTemplate restTemplate;


    public <Request, Response> Response executeRequestCustomHeaders(String url, HttpMethod httpMethod, Request request, Class<Response> typedClass, Map<String, String> additionalHeaderParams) {
        try {
            HttpHeaders headers = new HttpHeaders();

            for (Map.Entry<String, String> entry : additionalHeaderParams.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }

            log.info("API URL: {}", url);
            log.info("API body: {}", request);
            HttpEntity<?> requestEntity = new HttpEntity<>(request, headers);

            ResponseEntity<Response> exchange = restTemplate.exchange(url, httpMethod, requestEntity, typedClass);
            log.info("API STATUS CODE VALUE: {}", exchange.getStatusCodeValue());
            return exchange.getBody();
        } catch (HttpClientErrorException ex) {
            log.info("Bad Request Exception: {}", ex.getResponseBodyAsString());
            throw new BadRequestException(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Exception", ex);
            throw new RestClientException(ex.getMessage());
        }

    }
}
