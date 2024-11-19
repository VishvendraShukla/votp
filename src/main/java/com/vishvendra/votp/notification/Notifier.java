package com.vishvendra.votp.notification;

import com.vishvendra.votp.models.Notification;

public interface Notifier {

  void notify(Notification message);

  default void register() {
    NotifierRegistry.register(this);
  }

}
