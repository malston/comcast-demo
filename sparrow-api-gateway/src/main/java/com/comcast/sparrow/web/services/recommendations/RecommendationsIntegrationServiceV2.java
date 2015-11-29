package com.comcast.sparrow.web.services.recommendations;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
//import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;

import rx.Observable;

@Service
public class RecommendationsIntegrationServiceV2 {

    Log log = LogFactory.getLog(RecommendationsIntegrationServiceV2.class);

//    @Autowired
//    @LoadBalanced
//     private OAuth2RestOperations restTemplate;

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    @LoadBalanced
    RestTemplate unsecuredTemplate;

    @HystrixCommand(fallbackMethod = "stubRecommendations",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
            })
    public Observable<List<Movie>> getRecommendations(final String mlId) {
        return new ObservableResult<List<Movie>>() {
            @Override
            public List<Movie> invoke() {
                ParameterizedTypeReference<List<Movie>> responseType = new ParameterizedTypeReference<List<Movie>>() {
                };
                log.debug(String.format("Calling v2 sparrow-recommendations service to load recommendations for mlId: %s", mlId));
                return unsecuredTemplate.exchange("http://sparrow-recommendations/recommendations/forMovie/{mlId}", HttpMethod.GET, null, responseType, mlId).getBody();
            }
        };
    }

    @HystrixCommand(fallbackMethod = "stubLikes", commandProperties = { @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
    public Observable<Boolean> likes(final String userName, final String mlId) {
        return new ObservableResult<Boolean>() {
            @Override
            public Boolean invoke() {
                log.debug(String.format("Calling v2 sparrow-recommendations service to load like answer for mlId: %s and movie: %s", mlId, userName));
                Boolean answer = unsecuredTemplate.getForObject("http://sparrow-recommendations/does/{userName}/like/{mlId}", Boolean.class, userName, mlId);
                log.debug(String.format("Answer v2 from sparrow-recommendations service for mlId: %s and movie: %s = %s", mlId, userName, answer));
                return answer;
            }
        };
    }

    @SuppressWarnings("unused")
    private List<Movie> stubRecommendations(final String mlId) {
        return null;
    }

    @SuppressWarnings("unused")
    private Boolean stubLikes(final String userName, final String mlId) {
        return false;
    }

}
