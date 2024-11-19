package com.vishvendra.votp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Otp {

  private String identifier;
  private String otp;
  private String lang = "en";

}
