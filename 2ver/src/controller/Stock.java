package controller;

import model.*;

import java.io.File;

/**
 * Stores code to generate the stock user.
 */
public class Stock {

    /**
     * The constructor is private since this class only houses static methods.
     */
    private Stock() {}

    /**
     * Creates the stock user and returns a user object.
     * The stock user contains an group named "stock", which contains some sample
     * photos and tags applied to these photos.
     *
     * @return A stock user object.
     */
    public static User createStockUser() {
        try {
            User stockUser = new User("stock");
            Group stockGroup = new Group("stock");
            stockUser.add(stockGroup);

            Photo photo1 = new Photo(new File("data/icon.png"));
            photo1.setCaption("Original icon");
            stockGroup.add(photo1);

            Photo photo2 = new Photo(new File("data/icon2.png"));
            photo2.setCaption("Current icon");
            stockGroup.add(photo2);

            Photo photo3 = new Photo(new File("data/photo1.png"));
            photo3.setCaption("Beach");
            stockGroup.add(photo3);
            Tag tag1 = new Tag("Person", "Laura");
            Tag tag2 = new Tag("Person", "Ralf");
            Tag tag3 = new Tag("Person", "Hatsune");
            Tag tag4 = new Tag("Weather", "Clear");
            Tag tag5 = new Tag("air_quality", "Good");
            stockUser.tagPhoto(stockGroup, photo3, tag1);
            stockUser.tagPhoto(stockGroup, photo3, tag2);
            stockUser.tagPhoto(stockGroup, photo3, tag3);
            stockUser.tagPhoto(stockGroup, photo3, tag4);
            stockUser.tagPhoto(stockGroup, photo3, tag5);


            Photo photo4 = new Photo(new File("data/earth.png"));
            photo4.setCaption("Earth");
            stockGroup.add(photo4);
            stockUser.tagPhoto(stockGroup, photo4, new Tag("Abstract", "Circle"));

            Photo photo5 = new Photo(new File("data/abstract.png"));
            photo5.setCaption("Modern Art");
            stockGroup.add(photo5);
            Tag tag6 = new Tag("Person", "Hatsune");
            Tag tag7 = new Tag("Abstract", "Triangle");
            Tag tag8 = new Tag("Abstract", "Square");
            stockUser.tagPhoto(stockGroup, photo5, tag6);
            stockUser.tagPhoto(stockGroup, photo5, tag7);
            stockUser.tagPhoto(stockGroup, photo5, tag8);

            Photo photo6 = new Photo(new File("data/mountain.png"));
            photo6.setCaption("Mountain");
            stockGroup.add(photo6);
            Tag tag9 = new Tag("Person", "Hatsune");
            Tag tag10 = new Tag("Person", "Ralf");
            Tag tag11 = new Tag("Location", "FingerLakes");
            stockUser.tagPhoto(stockGroup, photo6, tag9);
            stockUser.tagPhoto(stockGroup, photo6, tag10);
            stockUser.tagPhoto(stockGroup, photo6, tag11);

            Photo photo7 = new Photo(new File("data/void.png"));
            photo7.setCaption("Void");
            stockGroup.add(photo7);
            Tag tag12 = new Tag("Abstract", "Circle");
            Tag tag13 = new Tag("Gradient", "True");
            stockUser.tagPhoto(stockGroup, photo7, tag12);
            stockUser.tagPhoto(stockGroup, photo7, tag13);

            return stockUser;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
