package com.ders.udemyders.web;

import com.ders.udemyders.model.Owner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetClinicRestControllerTest {
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate = new RestTemplate();
        BasicAuthenticationInterceptor basicAuthenticationInterceptor = new BasicAuthenticationInterceptor("user3", "secret");
        restTemplate.setInterceptors(Arrays.asList(basicAuthenticationInterceptor));
    }

    @Test
    public void testGetOwnerById() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), Matchers.equalTo("test1"));
    }

    @Test
    public void testGetOwnersByLastName() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Eski", List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<Map<String, String>> body = response.getBody();
        List<String> firstnames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstnames, Matchers.containsInAnyOrder("Murat"));
    }

    @Test
    public void testGetOwners() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), Matchers.equalTo(200));

        List<Map<String, String>> body = response.getBody();
        List<String> firstnames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstnames, Matchers.containsInAnyOrder("test2", "test3", "test1", "test4"));
    }

    @Test
    public void testCreateOwner() {
        Owner owner = Owner.builder()
                //.id(11L)
                .firstName("test5")
                .lastName("test5")
                .build();
        URI location = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);

        Owner owner2 = restTemplate.getForObject(location, Owner.class);

        MatcherAssert.assertThat(owner2.getFirstName(), Matchers.equalTo(owner.getFirstName()));
        MatcherAssert.assertThat(owner2.getLastName(), Matchers.equalTo(owner.getLastName()));
    }

    @Test
    public void testUpdateOwner() {
        Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/11", Owner.class);

        MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("test5"));
        owner.setFirstName("test6");

        restTemplate.put("http://localhost:8080/rest/owner/11", owner);

        owner = restTemplate.getForObject("http://localhost:8080/rest/owner/11", Owner.class);

        MatcherAssert.assertThat(owner.getFirstName(), Matchers.equalTo("test6"));
    }

    @Test
    public void testDeleteOwner() {
        //restTemplate.delete("http://localhost:8080/rest/owner/9");
        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8080/rest/owner/9", HttpMethod.DELETE, null, Void.class);
        System.out.println(responseEntity.getStatusCodeValue());
        try {
            ResponseEntity<Owner> ownerRes = restTemplate.getForEntity("http://localhost:8080/rest/owner/9", Owner.class);
            //Assert.fail("should have not returned order");
        } catch (HttpClientErrorException e) {
            MatcherAssert.assertThat(e.getStatusCode(), Matchers.equalTo(404));
        }
    }

    @Test
    public void testServiceLevelValidation() {
        Owner owner = new Owner();

        ResponseEntity<URI> responseEntity = restTemplate.postForEntity("http://localhost:8080/rest/owner", owner, URI.class);

        MatcherAssert.assertThat(responseEntity.getStatusCode(),Matchers.equalTo(HttpStatus.PRECONDITION_FAILED));
    }
}
