package com.azubike.ellipsis.api.client;

import com.azubike.ellipsis.domain.remote_jobs.Job;
import com.azubike.ellipsis.domain.remote_jobs.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JobsApiClientTest {
  WebClient webClient = WebClient.create("https://remotive.com/");
  JobsApiClient jobsApiClient = new JobsApiClient(webClient);

  @Test
  void invokeJobsAPI_withLimit() {
    int limit = 3;
    String category = "software-dev";
    final List<Response> responses = jobsApiClient.invokeJobsAPI_withLimit(category, limit);
    final List<Job> jobs = responses.get(0).getJobs();
    assertNotNull(jobs);
    assertEquals(limit, jobs.size());
    jobs.forEach(Assertions::assertNotNull);
  }

  @Test
  void invokeJobsAPI_withListOfPageNumbers() {
    List<Integer> limits = List.of(1, 2, 3);
    String category = "software-dev";
    final List<Job> jobs = jobsApiClient.invokeJobsAPI_withListOfPageNumbers(category, limits);
    assertNotNull(jobs);
    jobs.forEach(Assertions::assertNotNull);
  }

  @Test
  void invokeJobsAPI_withListOfPageNumbers_CF() {
    List<Integer> limits = List.of(1, 2, 3);
    String category = "software-dev";
    final List<Job> jobs = jobsApiClient.invokeJobsAPI_withListOfPageNumbers_CF(category, limits);
    assertNotNull(jobs);
    jobs.forEach(Assertions::assertNotNull);

  }
}
