package com.votp.models;

import com.votp.models.OtpNotify.To;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

  private To to;
  private String notification;

}
