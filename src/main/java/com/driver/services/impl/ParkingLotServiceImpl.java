package com.driver.services.impl;


import com.driver.Dto.ParkingLotResponseDto;
import com.driver.Dto.SpotResponseDto;
import com.driver.model.ParkingLot;
import com.driver.model.Spot;

import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {

        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);


        return parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        SpotResponseDto spotResponseDto = new SpotResponseDto();
        spotResponseDto.setOccupied(false);
        spotResponseDto.setNumberOfWheels(numberOfWheels);
        spotResponseDto.setPricePerHour(pricePerHour);

        ParkingLotResponseDto parkingLotResponseDto = new ParkingLotResponseDto();

        Spot spot = new Spot();
        spot.setOccupied(spotResponseDto.getOccupied());
        spot.setNumberOfWheels(spotResponseDto.getNumberOfWheels());
        spot.setPricePerHour(spotResponseDto.getPricePerHour());

        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        parkingLot.getSpotList().add(spot);

        spot.setParkingLot(parkingLot);
        parkingLotRepository1.save(parkingLot);

        parkingLotResponseDto.setName(parkingLot.getName());
        parkingLotResponseDto.setAddress(parkingLot.getAddress());
        spotResponseDto.setParkingLotResponseDto(parkingLotResponseDto);

        //spotRepository1.save(spot);
        return spot;

    }

    @Override
    public void deleteSpot(int spotId) {
        parkingLotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot>spotList = parkingLot.getSpotList();
        Spot spot = null;
        for(Spot spot1:spotList){
            if(spot1.getId()==spotId){
                spot=spot1;
            }
        }
        spot.setPricePerHour(pricePerHour);
        //parkingLot.setSpotList(spotList);

        //parkingLotRepository1.save(parkingLot);
        spotRepository1.save(spot);

        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        parkingLotRepository1.deleteById(parkingLotId);
    }
}
