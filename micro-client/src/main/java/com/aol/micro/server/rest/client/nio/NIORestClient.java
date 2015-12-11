package com.aol.micro.server.rest.client.nio;


import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import lombok.Getter;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRequestCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import com.aol.micro.server.rest.jackson.JacksonUtil;

public class NIORestClient {

	@Getter
	private final AsyncRestTemplate template;

	// GET
	public <T> CompletableFuture<ResponseEntity<T>> getForEntity(String url,
			Class<T> responseType, Object... uriVariables)
			throws RestClientException {

		return toCompletableFuture(template.getForEntity(url, responseType,
				uriVariables));
	}

	public <T> CompletableFuture<ResponseEntity<T>> getForEntity(String url,
			Class<T> responseType, Map<String, ?> urlVariables)
			throws RestClientException {

		return toCompletableFuture(template.getForEntity(url, responseType,
				urlVariables));
	}

	public <T> CompletableFuture<ResponseEntity<T>> getForEntity(URI url,
			Class<T> responseType) throws RestClientException {

		return toCompletableFuture(template.getForEntity(url, responseType));
	}

	// HEAD

	public CompletableFuture<HttpHeaders> headForHeaders(String url,
			Object... uriVariables) throws RestClientException {

		return toCompletableFuture(template.headForHeaders(url, uriVariables));
	}

	public CompletableFuture<HttpHeaders> headForHeaders(String url,
			Map<String, ?> uriVariables) throws RestClientException {
		return toCompletableFuture(template.headForHeaders(url, uriVariables));
	}

	public CompletableFuture<HttpHeaders> headForHeaders(URI url)
			throws RestClientException {
		return toCompletableFuture(template.headForHeaders(url));
	}

	public CompletableFuture<URI> postForLocation(String url,
			HttpEntity<?> request, Object... uriVariables)
			throws RestClientException {
		return toCompletableFuture(template.postForLocation(url, request,
				uriVariables));
	}

	public CompletableFuture<URI> postForLocation(String url,
			HttpEntity<?> request, Map<String, ?> uriVariables)
			throws RestClientException {
		return toCompletableFuture(template.postForLocation(url, request,
				uriVariables));
	}

	public CompletableFuture<URI> postForLocation(URI url, HttpEntity<?> request)
			throws RestClientException {
		return toCompletableFuture(template.postForLocation(url, request));
	}

	public <T> CompletableFuture<ResponseEntity<T>> postForEntity(String url,
			HttpEntity<?> request, Class<T> responseType,
			Object... uriVariables) throws RestClientException {
		return toCompletableFuture(template.postForEntity(url, request,
				responseType, uriVariables));
	}

	public <T> CompletableFuture<ResponseEntity<T>> postForEntity(String url,
			HttpEntity<?> request, Class<T> responseType,
			Map<String, ?> uriVariables) throws RestClientException {
		return toCompletableFuture(template.postForEntity(url, request,
				responseType, uriVariables));
	}

	public <T> CompletableFuture<ResponseEntity<T>> postForEntity(URI url,
			HttpEntity<?> request, Class<T> responseType)
			throws RestClientException {
		return toCompletableFuture(template.postForEntity(url, request,
				responseType));
	}

	public CompletableFuture<?> put(String url, HttpEntity<?> request,
			Object... uriVariables) throws RestClientException {
		return toCompletableFuture(template.put(url, request, uriVariables));
	}

	public CompletableFuture<?> put(String url, HttpEntity<?> request,
			Map<String, ?> uriVariables) throws RestClientException {
		return toCompletableFuture(template.put(url, request, uriVariables));
	}

	public CompletableFuture<?> put(URI url, HttpEntity<?> request)
			throws RestClientException {
		return toCompletableFuture(template.put(url, request));
	}

	public CompletableFuture<?> delete(String url, Object... urlVariables)
			throws RestClientException {
		return toCompletableFuture(template.delete(url, urlVariables));
	}

	public CompletableFuture<?> delete(String url, Map<String, ?> urlVariables)
			throws RestClientException {
		return toCompletableFuture(template.delete(url, urlVariables));
	}

	public CompletableFuture<?> delete(URI url) throws RestClientException {
		return toCompletableFuture(template.delete(url));
	}

	public CompletableFuture<Set<HttpMethod>> optionsForAllow(String url,
			Object... uriVariables) throws RestClientException {
		return toCompletableFuture(template.optionsForAllow(url, uriVariables));
	}

	public CompletableFuture<Set<HttpMethod>> optionsForAllow(String url,
			Map<String, ?> uriVariables) throws RestClientException {
		return toCompletableFuture(template.optionsForAllow(url, uriVariables));
	}

	public CompletableFuture<Set<HttpMethod>> optionsForAllow(URI url)
			throws RestClientException {
		return toCompletableFuture(template.optionsForAllow(url));
	}

	public <T> CompletableFuture<ResponseEntity<T>> exchange(String url,
			HttpMethod method, HttpEntity<?> requestEntity,
			Class<T> responseType, Object... uriVariables)
			throws RestClientException {
		return toCompletableFuture(template.exchange(url, method,
				requestEntity, responseType, uriVariables));
	}

	public <T> CompletableFuture<ResponseEntity<T>> exchange(String url,
			HttpMethod method, HttpEntity<?> requestEntity,
			Class<T> responseType, Map<String, ?> uriVariables)
			throws RestClientException {
		return toCompletableFuture(template.exchange(url, method,
				requestEntity, responseType, uriVariables));
	}

	public <T> CompletableFuture<ResponseEntity<T>> exchange(URI url,
			HttpMethod method, HttpEntity<?> requestEntity,
			Class<T> responseType) throws RestClientException {
		return toCompletableFuture(template.exchange(url, method,
				requestEntity, responseType));
	}

	public <T> CompletableFuture<ResponseEntity<T>> exchange(String url,
			HttpMethod method, HttpEntity<?> requestEntity,
			ParameterizedTypeReference<T> responseType, Object... uriVariables)
			throws RestClientException {
		return toCompletableFuture(template.exchange(url, method,
				requestEntity, responseType, uriVariables));
	}

	public <T> CompletableFuture<ResponseEntity<T>> exchange(String url,
			HttpMethod method, HttpEntity<?> requestEntity,
			ParameterizedTypeReference<T> responseType,
			Map<String, ?> uriVariables) throws RestClientException {
		return toCompletableFuture(template.exchange(url, method,
				requestEntity, responseType, uriVariables));
	}

	public <T> CompletableFuture<ResponseEntity<T>> exchange(URI url,
			HttpMethod method, HttpEntity<?> requestEntity,
			ParameterizedTypeReference<T> responseType)
			throws RestClientException {
		return toCompletableFuture(template.exchange(url, method,
				requestEntity, responseType));
	}

	public <T> CompletableFuture<T> execute(String url, HttpMethod method,
			AsyncRequestCallback requestCallback,
			ResponseExtractor<T> responseExtractor, Object... urlVariables)
			throws RestClientException {
		return toCompletableFuture(template.execute(url, method,
				requestCallback, responseExtractor, urlVariables));
	}

	public <T> CompletableFuture<T> execute(String url, HttpMethod method,
			AsyncRequestCallback requestCallback,
			ResponseExtractor<T> responseExtractor, Map<String, ?> urlVariables)
			throws RestClientException {
		return toCompletableFuture(template.execute(url, method,
				requestCallback, responseExtractor, urlVariables));
	}

	public <T> CompletableFuture<T> execute(URI url, HttpMethod method,
			AsyncRequestCallback requestCallback,
			ResponseExtractor<T> responseExtractor) throws RestClientException {
		return toCompletableFuture(template.execute(url, method,
				requestCallback, responseExtractor));
	}

	public NIORestClient(AsyncRestTemplate template) {
		super();

		this.template = template;
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(JacksonUtil.getMapper());
		template.getMessageConverters().add(converter);
	}

	<T> CompletableFuture<T> toCompletableFuture(
			final ListenableFuture<T> listenableFuture) {
		// create an instance of CompletableFuture
		CompletableFuture<T> completable = new CompletableFuture<T>() {
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				// propagate cancel to the listenable future
				boolean result = listenableFuture.cancel(mayInterruptIfRunning);
				super.cancel(mayInterruptIfRunning);
				return result;
			}
		};

		// add callback
		listenableFuture.addCallback(new ListenableFutureCallback<T>() {
			@Override
			public void onSuccess(T result) {
				completable.complete(result);
			}

			@Override
			public void onFailure(Throwable t) {
				if(t instanceof HttpClientErrorException) {
					String message = ((HttpClientErrorException)t).getResponseBodyAsString();
					completable.completeExceptionally(new IllegalArgumentException(message, t));
				} else {
					completable.completeExceptionally(t);
				}
			}
		});
		return completable;
	}


}
