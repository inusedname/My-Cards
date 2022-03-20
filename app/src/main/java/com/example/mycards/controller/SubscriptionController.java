package com.example.mycards.controller;

import com.example.mycards.model.Coupon;
import com.example.mycards.model.Subscription;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionController {
    private final List<Subscription> subscriptionsList = new ArrayList<>();
    public void addSubscription(Subscription c)
    {
        subscriptionsList.add(c);
    }
    public void removeSubscription(Subscription c)
    {
        subscriptionsList.remove(c);
    }
    public int getIndex(String id)
    {
        for (int i = 0; i < subscriptionsList.size();i++)
            if (subscriptionsList.get(i).getSystemID().equals(id))
                return i;
        return -1;
    }
    public void removeSubscription(String id)
    {
        int pos = getIndex(id);
        subscriptionsList.remove(pos);
    }
    public void editSubscription(String id, Subscription c)
    {
        subscriptionsList.set(getIndex(id),c);
    }
}
