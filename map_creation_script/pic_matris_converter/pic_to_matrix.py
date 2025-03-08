from PIL import Image
import numpy as np
import os

def convert_image_to_matrix(image_path, output_path):
    # Open the image
    img = Image.open(image_path)
    
    # Convert image to RGB if it's not already
    img = img.convert('RGB')
    
    # Get image dimensions
    width, height = img.size
    
    # Create a matrix to store the values
    matrix = []
    
    # Process each pixel
    for y in range(height):
        row = []
        for x in range(width):
            r, g, b = img.getpixel((x, y))
            
            # Check which color is dominant
            if g > r and g > b:  # Green is dominant
                value = 1
            elif b > r and b > g:  # Blue is dominant
                value = 2
            else:  # Red is dominant or equal
                value = 0
                
            row.append(value)
        matrix.append(row)
    
    # Convert to numpy array for easier handling
    matrix = np.array(matrix)
    
    # Save to txt file
    with open(output_path, 'w') as f:
        for row in matrix:
            # Convert numbers to strings and join with spaces
            line = ' '.join(map(str, row))
            f.write(line + '\n')
    
    print(f"Matrix has been saved to {output_path}")
    print(f"Matrix dimensions: {width}x{height}")

if __name__ == "__main__":
    # Get the current directory
    current_dir = os.path.dirname(os.path.abspath(__file__))
    
    # Input and output paths
    image_path = os.path.join(current_dir, "input_image.png")  # You can change the extension based on your image
    output_path = os.path.join(current_dir, "output_matrix.txt")
    
    if os.path.exists(image_path):
        convert_image_to_matrix(image_path, output_path)
    else:
        print(f"Please place your pixel art image as 'input_image.png' in the folder: {current_dir}")
