package com.votp.notification;

import com.votp.models.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotifierRegistry {

  private static final List<Notifier> notifiers = new ArrayList<>();

  public NotifierRegistry() {
  }

  public static void register(Notifier notifier) {
    notifiers.add(notifier);
  }

  public static void notifyByAllEnabled(Notification notification) {
    for (Notifier notifier : notifiers) {
      notifier.notify(notification);
    }
  }
}
