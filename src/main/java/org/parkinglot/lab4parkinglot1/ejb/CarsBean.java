package org.parkinglot.lab4parkinglot1.ejb;

import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parkinglot.lab4parkinglot1.common.CarDto;
import org.parkinglot.lab4parkinglot1.entities.Car;
import org.parkinglot.lab4parkinglot1.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {

    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public List<CarDto> findAllCars(){
        LOG.info("findAllCars");
        try{
            TypedQuery<Car> typedQuery =
                    entityManager.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex){
            throw new EJBException(ex);
        }

    }

    private List<CarDto> copyCarsToDto(List<Car>cars){
        List <CarDto> carDtoList = new ArrayList<>();
        for (Car car :cars){
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername()
            );
            carDtoList.add(carDto);
        }
        return carDtoList;
    }

    public void createCar(String licensePlate, String parkingSpot, Long userId){
        LOG.info("createCar");

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        User user = entityManager.find(User.class, userId);
        user.getCars().add(car);
        car.setOwner(user);

        entityManager.persist(car);
    }

    public CarDto findById(Long carId){
        LOG.info("findById");
        Car car = entityManager.find(Car.class, carId);
        if(car==null){
            return null;
        }
        return new CarDto(
                car.getId(),
                car.getLicensePlate(),
                car.getParkingSpot(),
                car.getOwner().getUsername());
    }

    public void updateCar(Long cardId, String licensePlate, String parkingSpot, Long ownerId) {
        LOG.info("updateCar");

        if (cardId == null) {
            return;
        }
        User newOwner = entityManager.find(User.class, ownerId);
        Car car = entityManager.find(Car.class, cardId);
        car.setLicensePlate(licensePlate);


        if (!newOwner.equals(car.getOwner())) {
            //add the car to its new owner
            car.setOwner(newOwner);
            newOwner.getCars().add(car);
        }
    }

    public void deleteCarsByIds(Collection<Long> carIds){
        LOG.info("deleteCarsByIds");

        for (Long carId:carIds){
            Car car=entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }

}
