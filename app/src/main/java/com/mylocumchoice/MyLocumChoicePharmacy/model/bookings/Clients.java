package com.mylocumchoice.MyLocumChoicePharmacy.model.bookings;

import java.util.List;

public class Clients {

    List<CompletedBookingResponse.Client> clients;

    public List<CompletedBookingResponse.Client> getClients() {
        return clients;
    }

    public void setClients(List<CompletedBookingResponse.Client> clients) {
        this.clients = clients;
    }
}
