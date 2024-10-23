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
    private TextField rField, gField, bField; // RGB поля

    @FXML
    public Slider hSlider, sSlider, vSlider;

    @FXML
    private Slider rSlider, gSlider, bSlider; // RGB ползунки

    @FXML
    private Slider cSlider, mSlider, ySlider, kSlider;

    @FXML
    private Rectangle colorDisplay; // Объект, который будет изменять цвет

    private ColorModel colorModel; // Модель цвета

    @FXML
    public void initialize() {
        colorModel = new ColorModel(255, 0, 0); // Инициализируем красным цветом

        // Связываем ползунки с изменениями
        setupSliderListeners();
        setupTextFieldListeners();

        updateColorDisplay(); // Обновляем начальный цвет
        updateSlidersAndFills(); // Убедимся, что все элементы интерфейса отображают правильные значения
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
            handleTextFieldInput(cField, cSlider, 100); // 100 для CMYK
            colorModel.setC(Double.parseDouble(cField.getText()));
            updateFromCMYK();
            isUpdating = false;
        });

        mField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(mField, mSlider, 100); // 100 для CMYK
            colorModel.setM(Double.parseDouble(mField.getText()));
            updateFromCMYK();
            isUpdating = false;
        });

        yField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(yField, ySlider, 100); // 100 для CMYK
            colorModel.setY(Double.parseDouble(yField.getText()));
            updateFromCMYK();
            isUpdating = false;
        });

        kField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(kField, kSlider, 100); // 100 для CMYK
            colorModel.setK(Double.parseDouble(kField.getText()));
            updateFromCMYK();
            isUpdating = false;
        });

        hField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(hField, hSlider, 360); // 360 для Hue
            colorModel.setH(Double.parseDouble(hField.getText()));
            updateFromHSV();
            isUpdating = false;
        });

        sField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(sField, sSlider, 100); // 100 для Saturation
            colorModel.setS(Double.parseDouble(sField.getText()));
            updateFromHSV();
            isUpdating = false;
        });

        vField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isUpdating) return;
            isUpdating = true;
            handleTextFieldInput(vField, vSlider, 100); // 100 для Value
            colorModel.setV(Double.parseDouble(vField.getText()));
            updateFromHSV();
            isUpdating = false;
        });
    }

    // Обновляем значения из RGB
    private void updateFromRGB() {
        colorModel.rgbToCmyk(); // Конвертируем RGB в CMYK
        colorModel.rgbToHsv(); // Конвертируем RGB в HSV
        updateColorDisplay();
        updateSlidersAndFills();
    }

    // Обновляем значения из CMYK
    private void updateFromCMYK() {
        colorModel.cmykToRgb(); // Конвертируем CMYK в RGB
        colorModel.cmykToHsv(); // Конвертируем CMYK в HSV
        /*System.out.println(colorModel.getC());
        System.out.println( colorModel.getM());
        System.out.println( colorModel.getY());
        System.out.println( colorModel.getK());
        System.out.println(colorModel.getR());
        System.out.println( colorModel.getG());
        System.out.println( colorModel.getB());
        System.out.println( colorModel.getH());
        System.out.println(colorModel.getS());
        System.out.println( colorModel.getV());*/

        updateColorDisplay();
        updateSlidersAndFills();
    }

    // Обновляем значения из HSV
    private void updateFromHSV() {
        colorModel.hsvToRgb(); // Конвертируем HSV в RGB
        colorModel.hsvToCmyk(); // Конвертируем HSV в CMYK
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

    // Метод для проверки ввода в текстовом поле
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
            // Обработка некорректного ввода, сброс значения к 0
            textField.setText("0");
            slider.setValue(0);
        }
    }

    private void updateSlidersAndFills() {
        // Обновляем значения для RGB
        rSlider.setValue(colorModel.getR());
        gSlider.setValue(colorModel.getG());
        bSlider.setValue(colorModel.getB());

        rField.setText(String.format("%.0f", colorModel.getR()));
        gField.setText(String.format("%.0f", colorModel.getG()));
        bField.setText(String.format("%.0f", colorModel.getB()));

        // Обновляем значения для CMYK
        cSlider.setValue(colorModel.getC());
        mSlider.setValue(colorModel.getM());
        ySlider.setValue(colorModel.getY());
        kSlider.setValue(colorModel.getK());

        cField.setText(String.format("%.0f", colorModel.getC()));
        mField.setText(String.format("%.0f", colorModel.getM()));
        yField.setText(String.format("%.0f", colorModel.getY()));
        kField.setText(String.format("%.0f", colorModel.getK()));

        // Обновляем значения для HSV
        hSlider.setValue(colorModel.getH());
        sSlider.setValue(colorModel.getS());
        vSlider.setValue(colorModel.getV());

        hField.setText(String.format("%.0f", colorModel.getH()));
        sField.setText(String.format("%.0f", colorModel.getS()));
        vField.setText(String.format("%.0f", colorModel.getV()));
    }


}
