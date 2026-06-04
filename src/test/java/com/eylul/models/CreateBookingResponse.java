package com.eylul.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBookingResponse {

    @JsonProperty("bookingid")
    public int bookingid;

    @JsonProperty("booking")
    public Booking booking;
}
