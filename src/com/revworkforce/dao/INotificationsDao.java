package com.revworkforce.dao;

import com.revworkforce.model.Notifications;
import java.util.List;

public interface INotificationsDao {

    boolean addNotification(Notifications notification);

    int getUnreadCount(int userId);

    List<Notifications> getNotificationsByUserId(int userId);

    boolean markAsRead(int notificationId);

    boolean deleteNotification(int notificationId);
}