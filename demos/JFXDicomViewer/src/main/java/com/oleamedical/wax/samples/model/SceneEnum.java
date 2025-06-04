package com.oleamedical.wax.samples.model;

import com.oleamedical.wax.samples.view.scenes.DicomSelectorView;
import com.oleamedical.wax.samples.view.scenes.DicomViewerView;

public enum SceneEnum {

    DICOM_SELECTOR_SCENE(DicomSelectorView.class),
    DICOM_VIEWER_SCENE(DicomViewerView.class),

    ;

    private final Class<?> clazz;

    SceneEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
