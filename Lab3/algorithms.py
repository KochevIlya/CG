import tkinter as tk
from PIL import Image, ImageDraw, ImageTk
import time

class RasterAlgorithmsApp:
    def __init__(self, root):
        self.root = root
        self.root.title("Rasterization Algorithms")
        
        # Настройки Canvas
        self.canvas_size = 800
        self.grid_size = 20
        
        # Фрейм для холста
        self.canvas_frame = tk.Frame(root)
        self.canvas_frame.pack(fill=tk.BOTH, expand=True)
        
        # Создание Canvas
        self.canvas = tk.Canvas(self.canvas_frame, width=self.canvas_size, height=self.canvas_size, bg="white")
        self.canvas.pack(fill=tk.BOTH, expand=True)
        
        # Создаем фрейм для элементов управления
        self.controls_frame = tk.Frame(root)
        self.controls_frame.pack(fill=tk.X)
        
        # Поля для ввода координат начальной и конечной точек
        tk.Label(self.controls_frame, text="Start X:").pack(side="left")
        self.start_x_entry = tk.Entry(self.controls_frame, width=5)
        self.start_x_entry.pack(side="left")
        
        tk.Label(self.controls_frame, text="Start Y:").pack(side="left")
        self.start_y_entry = tk.Entry(self.controls_frame, width=5)
        self.start_y_entry.pack(side="left")
        
        tk.Label(self.controls_frame, text="End X:").pack(side="left")
        self.end_x_entry = tk.Entry(self.controls_frame, width=5)
        self.end_x_entry.pack(side="left")
        
        tk.Label(self.controls_frame, text="End Y:").pack(side="left")
        self.end_y_entry = tk.Entry(self.controls_frame, width=5)
        self.end_y_entry.pack(side="left")
        
        # Кнопки для алгоритмов
        tk.Button(self.controls_frame, text="Step-by-Step", command=self.step_by_step).pack(side="left")
        tk.Button(self.controls_frame, text="DDA", command=self.dda_algorithm).pack(side="left")
        tk.Button(self.controls_frame, text="Bresenham Line", command=self.bresenham_line).pack(side="left")
        tk.Button(self.controls_frame, text="Очистить", command=self.clear_canvas).pack(side="left")
        
        # Создание изображения и отрисовка сетки с координатными осями
        self.image = Image.new("RGB", (self.canvas_size, self.canvas_size), "white")
        self.draw = ImageDraw.Draw(self.image)
        self.draw_grid()
        self.update_canvas()

    def update_canvas(self):
        """Обновляет Canvas для отображения изображения"""
        self.tk_image = ImageTk.PhotoImage(self.image)
        self.canvas.create_image(0, 0, anchor="nw", image=self.tk_image)
        
    def draw_grid(self):
        """Рисует сетку и координатные оси"""
        for x in range(0, self.canvas_size, self.grid_size):
            self.draw.line([(x, 0), (x, self.canvas_size)], fill="lightgray")
        for y in range(0, self.canvas_size, self.grid_size):
            self.draw.line([(0, y), (self.canvas_size, y)], fill="lightgray")
        
        # Рисуем оси
        mid_x = self.canvas_size // 2
        mid_y = self.canvas_size // 2
        self.draw.line([(mid_x, 0), (mid_x, self.canvas_size)], fill="black")  # Y-axis
        self.draw.line([(0, mid_y), (self.canvas_size, mid_y)], fill="black")  # X-axis

    def get_coordinates(self):
        """Получает координаты начальной и конечной точек из полей ввода"""
        try:
            x1 = int(self.start_x_entry.get())
            y1 = int(self.start_y_entry.get())
            x2 = int(self.end_x_entry.get())
            y2 = int(self.end_y_entry.get())
            return x1, y1, x2, y2
        except ValueError:
            print("Ошибка: введите корректные целочисленные значения для координат.")
            return None

    def draw_pixel(self, x, y, color="black"):
        """Закрашивает квадрат 1x1 в координатах (x, y)"""
        pixel_x = (x + 20) * self.grid_size
        pixel_y = (20 - y) * self.grid_size
        self.draw.rectangle([pixel_x, pixel_y, pixel_x + self.grid_size, pixel_y + self.grid_size], fill=color)

    def step_by_step(self):
        """Реализация пошагового алгоритма"""
        coords = self.get_coordinates()
        if coords:
            x1, y1, x2, y2 = coords
            dx = x2 - x1
            dy = y2 - y1
            steps = max(abs(dx), abs(dy))
            x_inc = dx / steps
            y_inc = dy / steps
            x, y = x1, y1
            for i in range(steps + 1):
                self.draw_pixel(round(x), round(y))
                x += x_inc
                y += y_inc
            self.update_canvas()

    def dda_algorithm(self):
        """Реализация алгоритма ЦДА (DDA)"""
        coords = self.get_coordinates()
        if coords:
            x1, y1, x2, y2 = coords
            dx = x2 - x1
            dy = y2 - y1
            steps = max(abs(dx), abs(dy))
            x_inc = dx / steps
            y_inc = dy / steps
            x, y = x1, y1
            for i in range(steps + 1):
                self.draw_pixel(round(x), round(y), color="blue")
                x += x_inc
                y += y_inc
            self.update_canvas()

    def bresenham_line(self):
        """Реализация алгоритма Брезенхема для линии"""
        coords = self.get_coordinates()
        if coords:
            x1, y1, x2, y2 = coords
            dx = abs(x2 - x1)
            dy = abs(y2 - y1)
            sx = 1 if x1 < x2 else -1
            sy = 1 if y1 < y2 else -1
            err = dx - dy
            while True:
                self.draw_pixel(x1, y1, color="green")
                if x1 == x2 and y1 == y2:
                    break
                e2 = 2 * err
                if e2 > -dy:
                    err -= dy
                    x1 += sx
                if e2 < dx:
                    err += dx
                    y1 += sy
            self.update_canvas()

    def clear_canvas(self):
        """Очистка Canvas и восстановление сетки с осями"""
        self.image = Image.new("RGB", (self.canvas_size, self.canvas_size), "white")
        self.draw = ImageDraw.Draw(self.image)
        self.draw_grid()
        self.update_canvas()

if __name__ == "__main__":
    root = tk.Tk()
    root.geometry("900x850")  # Установим начальный размер окна
    app = RasterAlgorithmsApp(root)
    root.mainloop()
