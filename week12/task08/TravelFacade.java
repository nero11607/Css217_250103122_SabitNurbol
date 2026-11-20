public class TravelFacade {
    private final FlightBooking flightBooking;
    private final HotelReservation hotelReservation;
    private final CarRental carRental;

    public TravelFacade() {
        this.flightBooking = new FlightBooking();
        this.hotelReservation = new HotelReservation();
        this.carRental = new CarRental();
    }

    public void bookTrip(String destination, String startDate, String endDate) {
        System.out.println("Booking travel package to " + destination + "...");
        flightBooking.bookFlight(destination, startDate, endDate);
        hotelReservation.reserveRoom(destination, startDate, endDate);
        carRental.rentCar(destination, startDate, endDate);
        System.out.println("Trip booked successfully.");
    }
}
