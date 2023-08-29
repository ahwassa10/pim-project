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
     * Creates the stock user and returns a garden object.
     * The stock user contains an group named "stock", which contains some sample
     * photos and tags applied to these photos.
     *
     * @return A stock user object.
     */
    public static Garden createStockGarden() {
        try {
            Garden garden = new Garden("stock");
            Group stockGroup = garden.createGroup("stock");

            Photo photo1 = new Photo(new File("data/icon.png"));
            photo1.setCaption("Original icon");
            garden.createPhoto(photo1);
            garden.copy(garden.all, stockGroup, photo1);

            Photo photo2 = new Photo(new File("data/icon2.png"));
            photo2.setCaption("Current icon");
            garden.createPhoto(photo2);
            garden.copy(garden.all, stockGroup, photo2);

            Photo photo3 = new Photo(new File("data/photo1.png"));
            photo3.setCaption("Beach");
            garden.createPhoto(photo3);
            garden.copy(garden.all, stockGroup, photo3);
            Tag tag1 = new Tag("Person", "Laura");
            Tag tag2 = new Tag("Person", "Ralf");
            Tag tag3 = new Tag("Person", "Hatsune");
            Tag tag4 = new Tag("Weather", "Clear");
            Tag tag5 = new Tag("air_quality", "Good");
            garden.tagPhoto(stockGroup, photo3, tag1);
            garden.tagPhoto(stockGroup, photo3, tag2);
            garden.tagPhoto(stockGroup, photo3, tag3);
            garden.tagPhoto(stockGroup, photo3, tag4);
            garden.tagPhoto(stockGroup, photo3, tag5);


            Photo photo4 = new Photo(new File("data/earth.png"));
            photo4.setCaption("Earth");
            garden.createPhoto(photo4);
            garden.copy(garden.all, stockGroup, photo4);
            garden.tagPhoto(stockGroup, photo4, new Tag("Abstract", "Circle"));

            Photo photo5 = new Photo(new File("data/abstract.png"));
            photo5.setCaption("Modern Art");
            garden.createPhoto(photo5);
            garden.copy(garden.all, stockGroup, photo5);
            Tag tag6 = new Tag("Person", "Hatsune");
            Tag tag7 = new Tag("Abstract", "Triangle");
            Tag tag8 = new Tag("Abstract", "Square");
            garden.tagPhoto(stockGroup, photo5, tag6);
            garden.tagPhoto(stockGroup, photo5, tag7);
            garden.tagPhoto(stockGroup, photo5, tag8);

            Photo photo6 = new Photo(new File("data/mountain.png"));
            photo6.setCaption("Mountain");
            garden.createPhoto(photo6);
            garden.copy(garden.all, stockGroup, photo6);
            Tag tag9 = new Tag("Person", "Hatsune");
            Tag tag10 = new Tag("Person", "Ralf");
            Tag tag11 = new Tag("Location", "FingerLakes");
            garden.tagPhoto(stockGroup, photo6, tag9);
            garden.tagPhoto(stockGroup, photo6, tag10);
            garden.tagPhoto(stockGroup, photo6, tag11);

            Photo photo7 = new Photo(new File("data/void.png"));
            photo7.setCaption("Void");
            garden.createPhoto(photo7);
            garden.copy(garden.all, stockGroup, photo7);
            Tag tag12 = new Tag("Abstract", "Circle");
            Tag tag13 = new Tag("Gradient", "True");
            garden.tagPhoto(stockGroup, photo7, tag12);
            garden.tagPhoto(stockGroup, photo7, tag13);

            return garden;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
