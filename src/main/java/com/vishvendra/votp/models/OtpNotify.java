package com.vishvendra.votp.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpNotify extends Otp {

  private To to;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class To {

    private String email;
    private String phoneNumber;
  }
}
