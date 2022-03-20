package com.example.mycards.controller;

import com.example.mycards.model.Card;
import com.example.mycards.model.MembershipBase;

import java.util.ArrayList;
import java.util.List;

public class CardController extends MembershipController {
    private final List<Card> cardsList = new ArrayList<>();
    public void addCard(Card c)
    {
        cardsList.add(c);
    }
    public void removeCard(Card c)
    {
        cardsList.remove(c);
    }
    public int getIndex(String id)
    {
        for (int i = 0; i < cardsList.size();i++)
            if (cardsList.get(i).getSystemID().equals(id))
                return i;
        return -1;
    }
    public void removeCard(String id)
    {
        int pos = getIndex(id);
        cardsList.remove(pos);
    }
    public void editCard(String id, Card c)
    {
        cardsList.set(getIndex(id),c);
    }
}
