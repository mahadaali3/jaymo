ReservationSystem Plan

1. Create `Reservation`, `Guest`, and `Host` model.
2. Create the data layer's custom `DataException`
3. Create the `GuestFileRepository` class.
- add the filePath field and create a constructor to initialize the field
- generate tests for `GuestFileRepository`, should be located in
  `src/test/java/learn/solar/data/GuestFileRepositoryTest`
- create a `data` directory in the project root. add test, seed, and
  production data files
- implement `findByEmail`, `findAll`, `findById`, and `deserialize`.
- test `findByEmail`

4. Create the `HostFileRepository` class.
- add the filePath field and create a constructor to initialize the field
- generate tests for `HostFileRepository`, should be located in
  `src/test/java/learn/solar/data/HostFileRepositoryTest`
- create a `data` directory in the project root. add test, seed, and
  production data files
- implement `findByEmail`, `findAll`, `findById`, and `deserialize`.
- test `findByEmail`

5. Create the `ReservationFileRepository` class.
- add the filePath field and create a constructor to initialize the field
- generate tests for `ReservationFileRepository`, should be located in
  `src/test/java/learn/solar/data/ReservationFileRepositoryTest`
- create a `data` directory in the project root. add test, seed, and
  production data files
- implement `findReservationByHost`, `serialize`, and `deserialize`.
- test `findReservationByHost`
- implement `makeReservation`
- test `makeReservation`
- implement `editReservation`
- test `editReservation`
- implement `cancelReservation`
- test `cancelReservation`
6. Extract the `GuestRepository` interface
   from `GuestFileRepository`.
7. Extract the `HostRepository` interface
   from `HostFileRepository`.
8. Extract the `ReservationRepository` interface
   from `ReservationFileRepository`.
9. Create `ReservationResult`.
10. Create `GuestService`, `HostService`, and  `ReservationService`.
- add a `ReservationRepository` (interface) field with a corresponding
  constructor
- generate tests for `ReservationService`
- create `ReservationRepositoryTestDouble` to support service testing, this
  test class implements `ReservationRepository`
- implement `findByHostEmail` and test, implement just enough code in
  `ReservationRepositoryTestDouble` to enable service testing
- implement `makeReservation` and test, requires validation
- implement `editReservation` and test, requires validation
- implement `cancelReservation` and test
7. Create `View`
- add `Scanner` field
- create read* methods: `readString`, `readRequiredString`, `readInt`,
  `readInt` (with min/max)
8. Create `Controller`
- add fields for service and view with corresponding constructor
- add a `run` method
9. Create `App` and the `main` method.
- instantiate all required objects: repository, service, view, and
  controller
- run the controller


 - Use LocalDate for dates
 - Use BigDecimal for financial math
 - Don't really know how to incorporate Spring dependency injection configured with either XML or annotations, but will figure it out.

