//package com.aqulasoft.fireman.mobile.ui.postlogin.models;
//
//import static com.aqulasoft.fireman.mobile.base.utils.FiremanSettings.TRACKER_HOSTNAME;
//
//import android.location.Location;
//
//import com.aqulasoft.fireman.mobile.ui.postlogin.ApiTracker;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class LocationSender {
//    ArrayList<Location> locations = new ArrayList<>();
//    ApiTracker apiTracker;
//
//    public LocationSender() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(TRACKER_HOSTNAME) //Базовая часть адреса
//                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
//                .build();
//        apiTracker = retrofit.create(ApiTracker.class); //Создаем объект, при помощи которого будем выполнять запросы
//    }
//
//    public void savePosition(Location location) throws IOException {
//        locations.add(location);
//        System.out.println(location.getLatitude());
//        System.out.println(location.getLongitude());
//        System.out.println("\n");
//        if (locations.size() > 5) {
//            sendRequest();
//        }
//    }
//
//    private void sendRequest() throws IOException {
//        System.out.println("Send in IntelliJ IDEA");
//        locations.clear();
//        apiTracker.postLocations(makeVehicleRequest("1234")).execute();
//        //TODO: send request on backend
//    }
//
//    public VehiclePositionRequest makeVehicleRequest(String vehicleId) {
//        //TODO: STREEEEAMS
//        VehiclePositionRequest vehiclePositionReq = new VehiclePositionRequest();
//        vehiclePositionReq.setVehicleId(vehicleId);
//        ArrayList<VehiclePositionDto> vehiclePosDtos = vehiclePositionReq.getPositions();
//        for (Location location : locations) {
//            VehiclePositionDto vehiclePosDto = new VehiclePositionDto(location, vehicleId);
//            vehiclePosDtos.add(vehiclePosDto);
//        }
//        vehiclePositionReq.setPositions(vehiclePosDtos);
////        vehiclePositionReq.setPositions(locations.stream()
////                .map(location -> { VehiclePositionDto(location, "asd")} )
////                .collect(Collectors.toList()));
//        return vehiclePositionReq;
//
//    }
//
//
//}
