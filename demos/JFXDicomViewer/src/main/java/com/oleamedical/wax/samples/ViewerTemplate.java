package com.oleamedical.wax.samples;

import com.oleamedical.wax.samples.controller.SceneController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewerTemplate {

    private final Stage stage;

    public ViewerTemplate(Stage stage, SceneController sceneController) {
        this.stage = stage;
        initStage();
    }

    public void initStage() {
        stage.setTitle("OleaMedical x WAX - Native JFX Dicom Viewer");
        stage.setResizable(false);
        stage.getIcons().add(ViewerUtils.getImage("olea-logo-square.png").getImage());
    }

    public Scene createScene() {
        VBox mainContainer = new VBox(30);
        mainContainer.setId("mainContainer");
        fillTopContainer(mainContainer);

        Scene scene = new Scene(mainContainer, 640, 480);
        scene.getStylesheets().add(JFXDicomViewer.class.getResource("styles/styles.css").toExternalForm());

        return scene;
    }

    public FileChooser createFileChooser(String text, String extensionName, String ...allowedExtensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(text);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(extensionName, allowedExtensions));
        return fileChooser;
    }

    public DirectoryChooser createDirChooser(String text) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(text);
        return directoryChooser;
    }

    private void fillTopContainer(VBox mainContainer) {
        ImageView oleaLogo = ViewerUtils.getImage("olea-logo.png");
        oleaLogo.setFitHeight(40);
        oleaLogo.setPreserveRatio(true);

        ImageView waxLogo = ViewerUtils.getImage("wax-logo.png");
        waxLogo.setFitHeight(40);
        waxLogo.setPreserveRatio(true);

        HBox logoContainer = new HBox(400, oleaLogo, waxLogo);
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.setId("top-logo-container");

        mainContainer.getChildren().addAll(logoContainer);
    }

}
