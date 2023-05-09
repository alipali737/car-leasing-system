package com.leasecompany.carleasingsystem;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Initialised the DAOFactory and creates the internal sessionFactory
        DAOFactory.getInstance();

//        tempLoadImages();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Car Leasing System");
        primaryStage.setWidth(1280);
        primaryStage.setHeight(735);

        SceneController.changeScene(SceneController.loginFXMLPath, primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

//    private void tempLoadImages() {
//        CarDAO carDAO = DAOFactory.getInstance().newCarDAO();
//        List<Car> cars = carDAO.findAll();
//        for (Car car : cars) {
//            if (car.getImage() != null) {
//                continue;
//            }
//            String imagePath = "src/main/resources/com/leasecompany/carleasingsystem/images/vehicles/";
//            imagePath += car.getBrand() + " ";
//            imagePath += car.getModel();
//            if (!car.getSpec().isBlank()) {
//                imagePath += " " + car.getSpec();
//            }
//            imagePath += ".jpeg";
//
//            System.out.println("Trying to upload image: " + imagePath);
//            try {
//                File imageFile = new File(imagePath);
//                byte[] bytes = carDAO.convertToByteArray(imageFile);
//                car.setImage(bytes);
//                carDAO.update(car);
//            } catch (FileNotFoundException e) {
//                System.err.println("Failed to upload image: " + e);
//            }
//
//        }
//    }

}
