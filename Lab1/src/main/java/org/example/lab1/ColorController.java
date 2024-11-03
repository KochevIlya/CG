package org.example.lab1;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorController {

    boolean isUpdating = false;
    @FXML
    public TextField cField, yField, mField, kField;

    @FXML
    public TextField hField, sField, vField;

    @FXML
    public ColorPicker colorPicker;

    @FXML
    private TextField rField, gField, bField;

    @FXML
    public Slider hSlider, sSlider, vSlider;

    @FXML
    private Slider rSlider, gSlider, bSlider;

    @FXML
    private Slider cSlider, mSlider, ySlider, kSlider;

    @FXML
    private Rectangle colorDisplay;

    private ColorModel colorModel;

    @FXML
    public void initialize() {
        colorModel = new ColorModel(255, 0, 0);

        setupSliderListeners();
        setupTextFieldListeners();

        updateColorDisplay();
        updateSlidersAndFills();
    }

    private void setupSliderListeners() {

        colorPicker.setOnAction(event -> {
            Color selectedColor = colorPicker.getValue();
            colorModel.setB(selectedColor.getBlue());
            colorModel.setG(selectedColor.getGreen());
            colorModel.setR(selectedColor.getRed());
            updateSlidersAndFills();
            colorDisplay.setFill(selectedColor);
        });

        rSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setR(newVal.doubleValue());
            updateFromRGB();
            isUpdating = false;
        });

        gSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setG(newVal.doubleValue());
            updateFromRGB();
            isUpdating = false;
        });

        bSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setB(newVal.doubleValue());
            updateFromRGB();
            isUpdating = false;
        });

        cSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setC(newVal.doubleValue());
            updateFromCMYK();
            isUpdating = false;
        });

        mSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setM(newVal.doubleValue());
            updateFromCMYK();
            isUpdating = false;
        });

        ySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setY(newVal.doubleValue());
            updateFromCMYK();
            isUpdating = false;
        });

        kSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setK(newVal.doubleValue());
            updateFromCMYK();
            isUpdating = false;
        });

        hSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setH(newVal.doubleValue());
            updateFromCMYK();
            isUpdating = false;
        });

        sSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setS(newVal.doubleValue());
            updateFromCMYK();
            isUpdating = false;
        });

        vSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (isUpdating) return;
            isUpdating = true;
            colorModel.setV(newVal.doubleValue());
            updateFromCMYK();
            isUpdating = false;
        });


    }

    private void setupTextFieldListeners() {
        rField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(rField, rSlider, 255);
            colorModel.setR(Double.parseDouble(rField.getText()));
            updateFromRGB();
            isUpdating = false;
        });

        gField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(gField, gSlider, 255);
            colorModel.setG(Double.parseDouble(gField.getText()));
            updateFromRGB();
            isUpdating = false;
        });

        bField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(bField, bSlider, 255);
            colorModel.setB(Double.parseDouble(bField.getText()));
            updateFromRGB();
            isUpdating = false;
        });

        cField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(cField, cSlider, 100);
            colorModel.setC(Double.parseDouble(cField.getText()));
            updateFromCMYK();
            isUpdating = false;
        });

        mField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(mField, mSlider, 100);
            colorModel.setM(Double.parseDouble(mField.getText()));
            updateFromCMYK();
            isUpdating = false;
        });

        yField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(yField, ySlider, 100);
            colorModel.setY(Double.parseDouble(yField.getText()));
            updateFromCMYK();
            isUpdating = false;
        });

        kField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(kField, kSlider, 100);
            colorModel.setK(Double.parseDouble(kField.getText()));
            updateFromCMYK();
            isUpdating = false;
        });

        hField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(hField, hSlider, 360);
            colorModel.setH(Double.parseDouble(hField.getText()));
            updateFromHSV();
            isUpdating = false;
        });

        sField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(sField, sSlider, 100);
            colorModel.setS(Double.parseDouble(sField.getText()));
            updateFromHSV();
            isUpdating = false;
        });

        vField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(vField, vSlider, 100);
            colorModel.setV(Double.parseDouble(vField.getText()));
            updateFromHSV();
            isUpdating = false;
        });
    }
    private void updateFromRGB() {
        colorModel.rgbToCmyk();
        colorModel.rgbToHsv();

        updateColorDisplay();
        updateSlidersAndFills();
    }
    private void updateFromCMYK() {
        colorModel.cmykToRgb();
        colorModel.cmykToHsv();

        updateColorDisplay();
        updateSlidersAndFills();
    }

    private void updateFromHSV() {
        colorModel.hsvToRgb();
        colorModel.hsvToCmyk();
        updateColorDisplay();
        updateSlidersAndFills();
    }

    private void updateColorDisplay() {
        colorDisplay.setFill(Color.rgb(
                (int) colorModel.getR(),
                (int) colorModel.getG(),
                (int) colorModel.getB()
        ));
    }

    private void handleTextFieldInput(TextField textField, Slider slider, int maxValue) {
        try {
            int value = Integer.parseInt(textField.getText());
            if (value < 0) {
                value = 0;
            } else if (value > maxValue) {
                value = maxValue;
            }
            slider.setValue(value);
            textField.setText(String.valueOf(value));
        } catch (NumberFormatException e) {
            textField.setText("0");
            slider.setValue(0);
        }
    }

    private void updateSlidersAndFills() {
        rSlider.setValue(colorModel.getR());
        gSlider.setValue(colorModel.getG());
        bSlider.setValue(colorModel.getB());

        rField.setText(String.format("%.0f", colorModel.getR()));
        gField.setText(String.format("%.0f", colorModel.getG()));
        bField.setText(String.format("%.0f", colorModel.getB()));

        cSlider.setValue(colorModel.getC());
        mSlider.setValue(colorModel.getM());
        ySlider.setValue(colorModel.getY());
        kSlider.setValue(colorModel.getK());

        cField.setText(String.format("%.0f", colorModel.getC()));
        mField.setText(String.format("%.0f", colorModel.getM()));
        yField.setText(String.format("%.0f", colorModel.getY()));
        kField.setText(String.format("%.0f", colorModel.getK()));

        hSlider.setValue(colorModel.getH());
        sSlider.setValue(colorModel.getS());
        vSlider.setValue(colorModel.getV());

        hField.setText(String.format("%.0f", colorModel.getH()));
        sField.setText(String.format("%.0f", colorModel.getS()));
        vField.setText(String.format("%.0f", colorModel.getV()));
    }


}
