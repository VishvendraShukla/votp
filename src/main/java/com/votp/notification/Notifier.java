package com.votp.notification;

import com.votp.models.Notification;

public interface Notifier {

  void notify(Notification message);

  default void register() {
    NotifierRegistry.register(this);
  }

}
