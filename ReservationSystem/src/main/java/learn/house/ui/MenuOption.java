package learn.house.ui;

public enum MenuOption {
    EXIT("Exit"),
    VIEW("View Reservations for Host"),
    MAKE("Make a Reservation"),
    EDIT("Edit a Reservation"),
    CANCEL("Cancel a Reservation");



    private final String option;

    MenuOption(String option) {
        this.option = option;
    }
    public Object getOption(){
        return option;
    }
}
