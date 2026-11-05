package controller;


import javafx.fxml.FXML;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.Gallery;
import model.Photo;
import javafx.event.ActionEvent;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import util.PhotoDownloader;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import util.PhotoDownloader;

public class GalleryController {

    private Gallery galleryModel;

    @FXML
    private TextField imageNameField;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<Photo> imagesListView;

    @FXML
    private TextField searchTextField;

    @FXML
    public void initialize() {
        // TODO additional FX controls initialization
        imagesListView.setCellFactory( param -> new ListCell<>(){
            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });

        imagesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                imageNameField.textProperty().unbindBidirectional(oldValue.nameProperty());
            }
            if (newValue != null) {
                bindSelectedPhoto(newValue);
            }
        });
    }

    @FXML
    public void searchButtonClicked(ActionEvent event) {
        String query = searchTextField.getText();
        System.out.println("Rozpoczynam wyszukiwanie dla frazy: " + query);

        galleryModel.clear();

        PhotoDownloader photoDownloader = new PhotoDownloader();

        photoDownloader.searchForPhotos(query)
                .subscribeOn(Schedulers.io())
                .observeOn(JavaFxScheduler.platform())
                .subscribe(
                        photo -> {
                            System.out.println("Pobrano zdjęcie: " + photo.getName());
                            galleryModel.addPhoto(photo);
                        },
                        error -> {
                            System.err.println("Błąd podczas wyszukiwania zdjęć:");
                            error.printStackTrace();
                        }
                );
    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
        imagesListView.setItems(gallery.getPhotos());
        imagesListView.getSelectionModel().select(0);
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        // TODO view <-> model bindings configuration
        //wiazanie dwukeirunkowe dla pola tekstowego
        imageNameField.textProperty().bindBidirectional(selectedPhoto.nameProperty());
        //wiazanie jednokierunkowe dla obrazka
        imageView.imageProperty().bind(selectedPhoto.photoDataProperty());
    }
}

