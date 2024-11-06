import matplotlib.pyplot as plt

import algorithms
# Функция для визуализации алгоритмов с квадратиками (пикселями 1x1)
def plot_rasterized_line(algorithm, x1, y1, x2, y2, title):
    result = algorithm(x1, y1, x2, y2)
    
    # Создаем график
    fig, ax = plt.subplots()
    ax.set_aspect('equal')
    plt.title(title)
    plt.xlabel('X')
    plt.ylabel('Y')
    
    # Рисуем квадраты размером 1x1 для каждого пикселя
    for (x, y) in result:
        rect = plt.Rectangle((x, y), 1, 1, color="black")
        ax.add_patch(rect)

    # Ограничиваем границы графика
    ax.set_xlimmi(n(x1, x2) - 1, max(x1, x2) + 1)
    ax.set_ylim(min(y1, y2) - 1, max(y1, y2) + 1)
    
    plt.grid(True, which='both', linestyle='--', color='lightgrey', linewidth=0.5)
    plt.gca().set_xticks(range(min(x1, x2) - 1, max(x1, x2) + 2))
    plt.gca().set_yticks(range(min(y1, y2) - 1, max(y1, y2) + 2))
    plt.show()

# Пример использования для каждого алгоритма
x1, y1 = 1, 1
x2, y2 = 9, 6

plot_rasterized_line(algorithms.step_by_step, x1, y1, x2, y2, "Step-by-Step Algorithm")
plot_rasterized_line(algorithms.dda, x1, y1, x2, y2, "DDA Algorithm")
plot_rasterized_line(algorithms.bresenhem, x1, y1, x2, y2, "Bresenham Algorithm")