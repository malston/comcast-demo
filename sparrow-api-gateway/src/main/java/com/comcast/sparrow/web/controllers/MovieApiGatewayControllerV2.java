package com.comcast.sparrow.web.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.comcast.sparrow.web.domain.MovieDetails;
import com.comcast.sparrow.web.services.catalog.CatalogIntegrationService;
import com.comcast.sparrow.web.services.recommendations.RecommendationsIntegrationService;
import com.comcast.sparrow.web.services.reviews.ReviewsIntegrationService;

import rx.Observable;
import rx.Observer;

@RestController
public class MovieApiGatewayControllerV2 {

	Log log = LogFactory.getLog(MovieApiGatewayControllerV2.class);

	@Autowired
	CatalogIntegrationService catalogIntegrationService;

	@Autowired
	ReviewsIntegrationService reviewsIntegrationService;

	@Autowired
	RecommendationsIntegrationService recommendationsIntegrationService;

	@RequestMapping("/v2/movie/{mlId}")
	public DeferredResult<MovieDetails> movieDetails(@PathVariable String mlId, @RequestParam String userName) {
//		@RequestMapping("/movie/{mlId}")
//		public DeferredResult<MovieDetails> movieDetails(@PathVariable String mlId,
//				@AuthenticationPrincipal Principal principal) {

		log.debug(String.format("Loading anonymous movie details for mlId: %s", mlId));

		Observable<MovieDetails> movieDetails = anonymousMovieDetails(mlId);

//		if (principal != null) {
//			String userName = principal.getName();

			log.debug(String.format("Loading details for mlId: %s for username: %s", mlId, userName));

			movieDetails = Observable.zip(movieDetails, recommendationsIntegrationService.likes(userName, mlId),
					(details, likes) -> {
						details.setLikes(likes);
						return details;
					});
//		}

		return toDeferredResult(movieDetails);
	}

	private Observable<MovieDetails> anonymousMovieDetails(String mlId) {
		return Observable.zip(catalogIntegrationService.getMovie(mlId), reviewsIntegrationService.reviewsFor(mlId),
				recommendationsIntegrationService.getRecommendations(mlId), (movie, reviews, recommendations) -> {
					MovieDetails movieDetails = new MovieDetails();
					movieDetails.setMlId(movie.getMlId());
					movieDetails.setTitle(movie.getTitle());
					movieDetails.setReviews(reviews);
					movieDetails.setRecommendations(recommendations);
					movieDetails.setGenres(movie.getGenres());
					return movieDetails;
				});
	}

	public DeferredResult<MovieDetails> toDeferredResult(Observable<MovieDetails> details) {
		DeferredResult<MovieDetails> result = new DeferredResult<>();
		details.subscribe(new Observer<MovieDetails>() {
			@Override
			public void onCompleted() {
			}

			@Override
			public void onError(Throwable throwable) {
			}

			@Override
			public void onNext(MovieDetails movieDetails) {
				result.setResult(movieDetails);
			}
		});
		return result;
	}

}
