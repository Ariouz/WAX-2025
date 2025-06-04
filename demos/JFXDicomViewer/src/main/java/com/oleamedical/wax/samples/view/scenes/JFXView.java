package com.oleamedical.wax.samples.view.scenes;

import com.oleamedical.wax.samples.controller.ViewerController;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class JFXView {

    public JFXView() {}

    public abstract Scene open(Stage stage, Scene scene, ViewerController viewerController);

}
