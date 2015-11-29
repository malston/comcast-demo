package com.comcast.sparrow.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@SpringBootApplication
@RestController
public class SparrowWebApplication {

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}
	
    public static void main(String[] args) {
        SpringApplication.run(SparrowWebApplication.class, args);
    }
    
    @Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	    	
    	@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.httpBasic().and().authorizeRequests()
					.antMatchers("/index.html", "/views/home.html", "/views/login.html", "/").permitAll().anyRequest()
					.authenticated().and().csrf()
					.csrfTokenRepository(csrfTokenRepository()).and()
					.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		}

		private Filter csrfHeaderFilter() {
			return new OncePerRequestFilter() {
				@Override
				protected void doFilterInternal(HttpServletRequest request,
						HttpServletResponse response, FilterChain filterChain)
						throws ServletException, IOException {
					CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
							.getName());
					if (csrf != null) {
						Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
						String token = csrf.getToken();
						if (cookie == null || token != null
								&& !token.equals(cookie.getValue())) {
							cookie = new Cookie("XSRF-TOKEN", token);
							cookie.setPath("/");
							response.addCookie(cookie);
						}
					}
					filterChain.doFilter(request, response);
				}
			};
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
	}
}

@RestController
class MoviesController {

    Map<Long, MovieEntry> entries = new ConcurrentHashMap<Long, MovieEntry>();

    @RequestMapping("/movie")
    Collection<MovieEntry> entries() {
        return this.entries.values();
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.DELETE)
    MovieEntry remove(@PathVariable Long id) {
        return this.entries.remove(id);
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
    MovieEntry entry(@PathVariable Long id) {
        return this.entries.get(id);
    }

    @RequestMapping(value = "/movie/{id}", method = RequestMethod.POST)
    MovieEntry update(@RequestBody MovieEntry movie) {
        this.entries.put(movie.getId(), movie);
        return movie;
    }

    @RequestMapping(value = "/movie", method = RequestMethod.POST)
    MovieEntry add(@RequestBody MovieEntry movie) {
        long id = 10 + new Random().nextInt(99);
        movie.setId(id);
        this.entries.put(id, movie);
        return movie;
    }

    MoviesController() {
        for (long i = 0; i < 5; i++)
            this.entries.put(i, new MovieEntry(i, "Title #" + i));
    }

    public static class MovieEntry {
        private long id;
        private String title;

        public MovieEntry() {}

        public MovieEntry(long id, String b) {
            this.id = id;
            this.title = b;
        }

        public long getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}