package com.oleamedical.wax.samples;

import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class ViewerUtils {

    public static ImageView getImage(String filename) {
        return new ImageView(new Image(Objects.requireNonNull(JFXDicomViewer.class.getResourceAsStream(filename))));
    }

    public static void openErrorDialog(String errorText) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);

        DialogPane dialogPane = new DialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK);
        dialogPane.setContentText(errorText);
        dialogPane.lookupButton(ButtonType.OK).setOnMouseClicked(mouseEvent -> {
            dialog.close();
        });
        Scene scene = new Scene(dialogPane);
        dialog.setScene(scene);
        dialog.show();
    }

}
