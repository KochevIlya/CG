package org.example.lab3;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RasterAlgorithmsController {

    @FXML
    private Canvas canvas;
    @FXML
    private ComboBox<String> algorithmSelector;
    @FXML
    private Button drawButton;
    @FXML
    private TextField startXField;
    @FXML
    private TextField startYField;
    @FXML
    private TextField endXField;
    @FXML
    private TextField endYField;
    @FXML
    private Label executionTimeLabel;
    @FXML
    private Slider zoomSlider;

    private int countCellsWidth;
    private int countCellsHeight;
    private int CELL_SIZE = 20;

    @FXML
    public void initialize() {
        drawGrid();
        algorithmSelector.getSelectionModel().select("Пошаговый алгоритм");

        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            CELL_SIZE = newValue.intValue();
            drawButton.fire();
        });

        startXField.setText("0");
        startYField.setText("0");
        endXField.setText("0");
        endYField.setText("0");

        setNumericOnlyWithDefaultZero(startXField);
        setNumericOnlyWithDefaultZero(startYField);
        setNumericOnlyWithDefaultZero(endXField);
        setNumericOnlyWithDefaultZero(endYField);

        drawButton.setOnAction(e -> {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawGrid();

            int x1 = Integer.parseInt(startXField.getText());
            int y1 = Integer.parseInt(startYField.getText());
            int x2 = Integer.parseInt(endXField.getText());
            int y2 = Integer.parseInt(endYField.getText());

            long startTime = System.nanoTime();

            switch (algorithmSelector.getValue()) {
                case "Пошаговый алгоритм":
                    stepByStepLine(gc, x1, y1, x2, y2);
                    break;
                case "Алгоритм ЦДА":
                    ddaLine(gc, x1, y1, x2, y2);
                    break;
                case "Алгоритм Брезенхема (отрезок)":
                    bresenhamLine(gc, x1, y1, x2, y2);
                    break;
                case "Алгоритм Брезенхема (окружность)":
                    int radius = (int) Math.sqrt(Math.pow(x2 - x1, 2));
                    bresenhamCircle(gc, x1 - 1, y1 - 1, radius);
                    break;
            }

            long endTime = System.nanoTime();
            double executionTime = (endTime - startTime) / 1000;
            executionTimeLabel.setText("Execution Time: " + executionTime +"mcs");
        });
    }


    private void setNumericOnlyWithDefaultZero(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1 && newValue.startsWith("0")) {
                textField.setText(newValue.substring(1));
            }


            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (textField.getText().isEmpty()) {
                textField.setText("0");
            }
        });
    }

    private double transformX(int x) {
        return CELL_SIZE * countCellsWidth / 2 + x * CELL_SIZE;
    }

    private double transformY(int y) {
        return (CELL_SIZE * countCellsHeight / 2) - y * CELL_SIZE;
    }

    private void drawGrid() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.LIGHTGRAY);

        countCellsWidth = 0;
        for (int x = 0; x < canvas.getWidth(); x += CELL_SIZE) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
            countCellsWidth++;
        }
        countCellsHeight = 0;
        for (int y = 0; y < canvas.getHeight(); y += CELL_SIZE) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
            countCellsHeight++;
        }

        gc.setFill(Color.BLACK);
        gc.fillText("X", canvas.getWidth() - 15, canvas.getHeight() / 2);
        gc.fillText("Y", canvas.getWidth() / 2 - 15, 15);

        gc.setStroke(Color.BLACK);
        gc.strokeLine(CELL_SIZE * countCellsWidth / 2, 0, CELL_SIZE * countCellsWidth / 2, canvas.getHeight());
        gc.strokeLine(0, countCellsHeight * CELL_SIZE / 2 + CELL_SIZE, canvas.getWidth(), countCellsHeight * CELL_SIZE / 2 + CELL_SIZE);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(10 * CELL_SIZE / 20));
        int halfWidth = (int) canvas.getWidth() / 2;
        int halfHeight = (int) canvas.getHeight() / 2;

        for (int x = -halfWidth / CELL_SIZE; x <= halfWidth / CELL_SIZE; x++) {
            if (x != 0) {
                double xPos = transformX(x);
                gc.fillText(String.valueOf(x), xPos, halfHeight + 15);
            }
        }

        for (int y = -halfHeight / CELL_SIZE; y <= halfHeight / CELL_SIZE; y++) {
            if (y != 0) {
                double yPos = transformY(y - 1);
                gc.fillText(String.valueOf(y), halfWidth + 5, yPos);
            }
        }
    }

    private void stepByStepLine(GraphicsContext gc, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double steps = Math.max(Math.abs(dx), Math.abs(dy));
        double xIncrement = dx / steps;
        double yIncrement = dy / steps;

        double x = x1;
        double y = y1;
        gc.setFill(Color.RED);
        for (int i = 0; i <= steps; i++) {
            gc.fillRect(transformX((int) Math.floor(x)), transformY((int) Math.floor(y)), CELL_SIZE, CELL_SIZE);
            x += xIncrement;
            y += yIncrement;
        }
    }

    private void ddaLine(GraphicsContext gc, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        int steps = (int) Math.max(Math.abs(dx), Math.abs(dy));
        double xIncrement = dx / steps;
        double yIncrement = dy / steps;

        double x = x1;
        double y = y1;
        gc.setFill(Color.GREEN);
        for (int i = 0; i <= steps; i++) {
            gc.fillRect(transformX((int) Math.floor(x)), transformY((int) Math.floor(y)), CELL_SIZE, CELL_SIZE);
            x += xIncrement;
            y += yIncrement;
        }
    }

    private void bresenhamLine(GraphicsContext gc, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        gc.setFill(Color.BLUE);
        while (true) {
            gc.fillRect(transformX(x1), transformY(y1), CELL_SIZE, CELL_SIZE);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    private void bresenhamCircle(GraphicsContext gc, double xc, double yc, int r) {
        int x = 0;
        int y = r;
        int d = 3 - 2 * r;
        gc.setFill(Color.PURPLE);

        while (y >= x) {
            drawCirclePoints(gc, xc, yc, x, y);
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
        }
    }

    private void drawCirclePoints(GraphicsContext gc, double xc, double yc, int x, int y) {
        gc.fillRect(transformX((int) Math.round(xc + x)), transformY((int) Math.round(yc + y)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc - x)), transformY((int) Math.round(yc + y)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc + x)), transformY((int) Math.round(yc - y)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc - x)), transformY((int) Math.round(yc - y)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc + y)), transformY((int) Math.round(yc + x)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc - y)), transformY((int) Math.round(yc + x)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc + y)), transformY((int) Math.round(yc - x)), CELL_SIZE, CELL_SIZE);
        gc.fillRect(transformX((int) Math.round(xc - y)), transformY((int) Math.round(yc - x)), CELL_SIZE, CELL_SIZE);
    }
}