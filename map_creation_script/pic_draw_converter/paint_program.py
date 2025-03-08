import sys
from PyQt5.QtWidgets import QApplication, QMainWindow, QWidget, QPushButton, QVBoxLayout, QHBoxLayout, QLabel
from PyQt5.QtGui import QPainter, QColor, QPixmap
from PyQt5.QtCore import Qt, QPoint
import numpy as np

class PaintWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle('Paint Program')
        
        # Ana widget'ı oluştur
        central_widget = QWidget()
        self.setCentralWidget(central_widget)
        
        # Layout'ları oluştur
        layout = QVBoxLayout(central_widget)
        button_layout = QHBoxLayout()
        
        # Canvas'ı oluştur
        self.canvas = Canvas(self)
        layout.addWidget(self.canvas)
        
        # Renk butonlarını oluştur
        self.color1_btn = QPushButton('Kırmızı')
        self.color2_btn = QPushButton('Yeşil')
        self.color3_btn = QPushButton('Mavi')
        self.convert_btn = QPushButton('Matrisi Kaydet')
        
        # Fırça boyutu butonları
        self.increase_brush = QPushButton('Fırça +')
        self.decrease_brush = QPushButton('Fırça -')
        
        # Butonları layout'a ekle
        button_layout.addWidget(self.color1_btn)
        button_layout.addWidget(self.color2_btn)
        button_layout.addWidget(self.color3_btn)
        button_layout.addWidget(self.increase_brush)
        button_layout.addWidget(self.decrease_brush)
        button_layout.addWidget(self.convert_btn)
        
        layout.addLayout(button_layout)
        
        # Butonlara tıklama olaylarını bağla
        self.color1_btn.clicked.connect(lambda: self.canvas.set_color(0))
        self.color2_btn.clicked.connect(lambda: self.canvas.set_color(1))
        self.color3_btn.clicked.connect(lambda: self.canvas.set_color(2))
        self.convert_btn.clicked.connect(self.canvas.convert_to_matrix)
        self.increase_brush.clicked.connect(self.canvas.increase_brush_size)
        self.decrease_brush.clicked.connect(self.canvas.decrease_brush_size)
        
        self.setFixedSize(900, 1000)

class Canvas(QWidget):
    def __init__(self, parent):
        super().__init__(parent)
        self.setFixedSize(800, 800)
        
        # Grid boyutları
        self.GRID_SIZE = 100
        self.CELL_SIZE = 8  # Her hücrenin piksel boyutu
        
        # Fırça boyutu
        self.brush_size = 1
        
        # Çizim alanını oluştur
        self.image = QPixmap(800, 800)
        self.image.fill(Qt.white)
        
        # Matris oluştur
        self.matrix = np.zeros((self.GRID_SIZE, self.GRID_SIZE), dtype=int)
        
        # Renk seçimini ayarla
        self.current_color = 0
        self.colors = [
            QColor(255, 0, 0),    # Kırmızı
            QColor(0, 255, 0),    # Yeşil
            QColor(0, 0, 255)     # Mavi
        ]
        
        self.draw_grid()
    
    def draw_grid(self):
        painter = QPainter(self.image)
        painter.setPen(QColor(200, 200, 200))  # Açık gri renk
        
        # Dikey çizgiler
        for i in range(self.GRID_SIZE + 1):
            x = i * self.CELL_SIZE
            painter.drawLine(x, 0, x, self.GRID_SIZE * self.CELL_SIZE)
        
        # Yatay çizgiler
        for i in range(self.GRID_SIZE + 1):
            y = i * self.CELL_SIZE
            painter.drawLine(0, y, self.GRID_SIZE * self.CELL_SIZE, y)
        
        self.update()
    
    def set_color(self, color_index):
        self.current_color = color_index
    
    def increase_brush_size(self):
        if self.brush_size < 5:  # Maximum fırça boyutu
            self.brush_size += 1
            print(f"Fırça boyutu: {self.brush_size}")
    
    def decrease_brush_size(self):
        if self.brush_size > 1:  # Minimum fırça boyutu
            self.brush_size -= 1
            print(f"Fırça boyutu: {self.brush_size}")
    
    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            self.paint_cell(event.pos())
    
    def mouseMoveEvent(self, event):
        if event.buttons() & Qt.LeftButton:
            self.paint_cell(event.pos())
    
    def paint_cell(self, pos):
        # Grid koordinatlarını hesapla
        center_x = pos.x() // self.CELL_SIZE
        center_y = pos.y() // self.CELL_SIZE
        
        # Fırça boyutuna göre boyama alanını hesapla
        brush_range = self.brush_size // 2
        
        # Grid sınırları içindeki tüm hücreleri boya
        for y in range(max(0, center_y - brush_range), min(self.GRID_SIZE, center_y + brush_range + 1)):
            for x in range(max(0, center_x - brush_range), min(self.GRID_SIZE, center_x + brush_range + 1)):
                # Hücreyi boya
                painter = QPainter(self.image)
                painter.fillRect(
                    x * self.CELL_SIZE,
                    y * self.CELL_SIZE,
                    self.CELL_SIZE,
                    self.CELL_SIZE,
                    self.colors[self.current_color]
                )
                
                # Grid çizgilerini yeniden çiz
                painter.setPen(QColor(200, 200, 200))
                painter.drawRect(
                    x * self.CELL_SIZE,
                    y * self.CELL_SIZE,
                    self.CELL_SIZE,
                    self.CELL_SIZE
                )
                
                # Matrisi güncelle
                self.matrix[y, x] = self.current_color
        
        self.update()
        
    def paintEvent(self, event):
        painter = QPainter(self)
        painter.drawPixmap(0, 0, self.image)
        
    def convert_to_matrix(self):
        # Matrisi txt dosyasına kaydet
        with open('matrix.txt', 'w') as f:
            for row in self.matrix:
                # Her sayının arasına bir boşluk koyarak yaz
                f.write(' '.join(map(str, row)) + '\n')
        print("Matris 'matrix.txt' dosyasına kaydedildi!")

if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = PaintWindow()
    window.show()
    sys.exit(app.exec_())
