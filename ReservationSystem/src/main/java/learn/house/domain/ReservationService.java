package learn.house.domain;

import learn.house.data.*;
import learn.house.models.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findReservationsByHost(UUID id) throws DataException {
        List <Reservation> reservations = reservationRepository.findReservationByHost(id);

        for(Reservation reservation : reservations) {
            String guestId = reservation.getGuest().getId();
            reservation.setGuest(guestRepository.findById(guestId));
            reservation.setHost(hostRepository.findByID(id));

        }
        return reservations;
    }
    public Result makeReservation(Reservation reservation) throws DataException{

        Result result = validate(reservation);

        if(result.isSuccess()){
            reservation.setTotal(addUpTotal(reservation));
            reservation = reservationRepository.addReservation(reservation);
            result.setReservation(reservation);
        }
        return result;
    }

    private Result validate(Reservation toValidate) throws DataException {
        Result result = new Result();
        List<Reservation> priorReservations = reservationRepository.findReservationByHost(toValidate.getHost().getId());

        if(priorReservations.size()==0){
            result.addMessage("There are no reservations for this host!");
        }
        if(toValidate.getGuest() == null || toValidate.getGuest().getId()==null){
            result.addMessage("Guest is required!");
        }
        if(toValidate.getHost()==null || toValidate.getHost().getId() == null){
            result.addMessage("Host is required!");
        }
        if(toValidate.getStartDate() == null) {
            result.addMessage("Start date required.");
        }

        if(toValidate.getEndDate() == null) {
            result.addMessage("End date required.");
        }


        if((toValidate.getStartDate() != null) && (toValidate.getEndDate() != null)) {
            LocalDate ns = toValidate.getStartDate();
            LocalDate ne = toValidate.getEndDate();
            for(Reservation prior: priorReservations) {
                LocalDate ps = prior.getStartDate();
                LocalDate pe = prior.getEndDate();
                // n.e <= p.s || n.s >= p.e    the new reservation must either end before each prior reservation starts OR it must start after that reservation
                // ne > ps && ns < pe
                if(ne.isAfter(ps) && ns.isBefore(pe) ){
                    result.addMessage("Reservations cannot overlap!");
                }
            }

        }

        return result;
    }
    public BigDecimal addUpTotal(Reservation reservation){
        LocalDate start = reservation.getStartDate();
        LocalDate end = reservation.getEndDate();
        BigDecimal standardRate = reservation.getHost().getStandardRate();
        BigDecimal weekendRate = reservation.getHost().getWeekendRate();
        BigDecimal weekdayTotal = new BigDecimal("0");
        BigDecimal weekendTotal = new BigDecimal("0");
        for(; start.compareTo(end)< 0; start = start.plusDays(1)){
            if (start.getDayOfWeek() != DayOfWeek.FRIDAY && start.getDayOfWeek() != DayOfWeek.SATURDAY){
                weekdayTotal = weekdayTotal.add(standardRate);
            }
            if (start.getDayOfWeek() == DayOfWeek.FRIDAY || start.getDayOfWeek() == DayOfWeek.SATURDAY){
                weekdayTotal = weekdayTotal.add(weekendRate);
            }
        }
            return weekdayTotal.add(weekendTotal);
    }
}
