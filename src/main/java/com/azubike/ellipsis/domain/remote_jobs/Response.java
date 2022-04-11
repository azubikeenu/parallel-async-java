package com.azubike.ellipsis.domain.remote_jobs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
  @JsonProperty(value = "00-warning")
  private String warning;

  @JsonProperty(value = "0-legal-notice")
  private String legalNotice;

  @JsonProperty(value = "job-count")
  private int jobCount;

  private List<Job> jobs;
}
