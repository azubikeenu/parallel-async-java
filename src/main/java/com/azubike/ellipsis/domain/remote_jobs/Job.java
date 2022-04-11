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
public class Job {
  private String id;
  private String url;

  @JsonProperty(value = "company_name")
  private String companyName;

  @JsonProperty(value = "company_logo")
  private String companyLogo;

  private String category;
  private List<String> tags;

  @JsonProperty(value = "job_type")
  private String jobType;

  @JsonProperty(value = "publication_date")
  private String publicationDate;

  @JsonProperty(value = "candidate_required_location")
  private String candidateRequiredLocation;

  private String salary;
  private String description;

  @JsonProperty(value = "company_url_logo")
  private String companyUrlLogo;
}
