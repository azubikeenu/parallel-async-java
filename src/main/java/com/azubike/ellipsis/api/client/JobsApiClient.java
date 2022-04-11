package com.azubike.ellipsis.api.client;

import com.azubike.ellipsis.domain.remote_jobs.Job;
import com.azubike.ellipsis.domain.remote_jobs.Response;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.azubike.ellipsis.utils.CommonUtil.startTimer;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class JobsApiClient {
  private WebClient webClient;

  public JobsApiClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public List<Response> invokeJobsAPI_withLimit(String category, int limit) {
    final String uriString =
            UriComponentsBuilder.fromUriString("/api/remote-jobs")
                    .queryParam("category", category)
                    .queryParam("limit", limit)
                    .buildAndExpand()
                    .toUriString();
    log(uriString);
    return webClient
            .get()
            .uri(uriString)
            .retrieve()
            .bodyToFlux(com.azubike.ellipsis.domain.remote_jobs.Response.class)
            .collectList()
            .block();
  }

  public List<Job> invokeJobsAPI_withListOfPageNumbers(String category, List<Integer> limits) {
    startTimer();
    final List<Job> jobs =
            limits
                    .parallelStream()
                    .map(limit -> invokeJobsAPI_withLimit(category, limit))
                    .flatMap(Collection::parallelStream)
                    .map(Response::getJobs)
                    .flatMap(Collection::parallelStream)
                    .collect(Collectors.toList());
    timeTaken();
    return jobs;
  }

  public List<Job> invokeJobsAPI_withListOfPageNumbers_CF(String category, List<Integer> limits) {
    startTimer();
    final List<CompletableFuture<List<Response>>> responseCompletableFuture =
            limits
                    .parallelStream()
                    .map(
                            limit ->
                                    CompletableFuture.supplyAsync(() -> (invokeJobsAPI_withLimit(category, limit))))
                    .collect(Collectors.toList());
    final List<Job> jobs =
            responseCompletableFuture.stream()
                    .map(CompletableFuture::join)
                    .flatMap(Collection::parallelStream)
                    .map(Response::getJobs)
                    .flatMap(Collection::parallelStream)
                    .collect(Collectors.toList());
    timeTaken();
    return jobs;
  }
}
