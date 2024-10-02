package org.example.lab1;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorController {

    @FXML
    public TextField cField, yField, mField, kField;

    @FXML
    public TextField hField, sField, vField;

    @FXML
    public Slider hSlider, sSlider, vSlider;

    @FXML
    private Slider rSlider, gSlider, bSlider; // RGB ползунки

    @FXML
    private Slider cSlider, mSlider, ySlider, kSlider;

    @FXML
    private TextField rField, gField, bField; // RGB поля

    @FXML
    private Rectangle colorDisplay; // Объект, который будет изменять цвет

    private ColorModel colorModel; // Модель цвета

    @FXML
    public void initialize() {
        colorModel = new ColorModel(255, 0, 0); // Инициализируем красным цветом
        // Связываем ползунки с изменениями
        rSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            colorModel.setR(newVal.doubleValue());
            updateColorDisplay();
        });

        gSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            colorModel.setG(newVal.doubleValue());
            updateColorDisplay();
        });

        bSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            colorModel.setB(newVal.doubleValue());

            updateColorDisplay();
        });
        rField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double redValue = Double.parseDouble(newValue);
                colorModel.setR(redValue);
                rSlider.setValue(redValue); // Обновляем ползунок для синхронизации
                colorDisplay.setFill(Color.rgb(
                        (int) colorModel.getR(),
                        (int) colorModel.getG(),
                        (int) colorModel.getB()
                ));
            } catch (NumberFormatException e) {
                // Обработка ошибки ввода
            }
        });

        gField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double greenValue = Double.parseDouble(newValue);
                colorModel.setG(greenValue);
                gSlider.setValue(greenValue); // Обновляем ползунок для синхронизации
                colorDisplay.setFill(Color.rgb(
                        (int) colorModel.getR(),
                        (int) colorModel.getG(),
                        (int) colorModel.getB()
                ));
            } catch (NumberFormatException e) {
                // Обработка ошибки ввода
            }
        });

        bField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double blueValue = Double.parseDouble(newValue);
                colorModel.setB(blueValue);
                bSlider.setValue(blueValue); // Обновляем ползунок для синхронизации
                colorDisplay.setFill(Color.rgb(
                        (int) colorModel.getR(),
                        (int) colorModel.getG(),
                        (int) colorModel.getB()
                ));
            } catch (NumberFormatException e) {
                // Обработка ошибки ввода
            }
        });
        updateColorDisplay(); // Обновляем начальный цвет

    }

    // Метод для обновления отображения цвета
    private void updateColorDisplay() {
        colorDisplay.setFill(Color.rgb(
                (int) colorModel.getR(),
                (int) colorModel.getG(),
                (int) colorModel.getB()
        ));
    }
}
