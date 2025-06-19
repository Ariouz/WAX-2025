#from unet_model import UNet
import os
from PIL import Image
from io import BytesIO
import matplotlib.pyplot as plt
from matplotlib.colors import ListedColormap
import torch

import numpy as np

class BrainSegmentation:
    def __init__(self):
        self.model = self.get_model()

    def get_model(self):
        print("Loading model")
        model = torch.jit.load(os.path.join(".", "unet_scripted.pt"), map_location="cpu")
        print("Model loaded")
        model.eval()
        return model


    def segment(self, fileData):
        print("debug 0")
        print(fileData)


        with open("filename.png", "wb") as newFile:
            for byte in fileData:
                newFile.write(byte.to_bytes(1, byteorder='big'))
            newFile.close()

        input_image = Image.open("filename.png")
        byte_data = bytearray(fileData)

        print("debug 01")

        input_tensor = to_tensor(byte_data, img=input_image)

        print(input_tensor)

        input_batch = input_tensor.unsqueeze(0)

        print("debug 1")

        ''' --------------- Inference --------------- '''

        device = torch.device("cpu" if not torch.cuda.is_available() else "cuda")

        print("debug 2")

        input_batch = input_batch.to(device)
        model = self.model.to(device)

        print("debug 3")

        with torch.no_grad():
            output = model(input_batch)

        print("debug 4")

        output_np = torch.round(output[0][0]).cpu().numpy().astype(int)
        output_msk = output_np

        red_mask = np.zeros((*output_np.shape, 4))
        red_mask[..., 0] = 1.0
        red_mask[..., 3] = output_msk.astype(float) * 0.3
        print(np.sum(output_np))

        print("debug 5")


        ''' --------------- Visualize the result --------------- '''
        input_np1 = input_tensor[0].cpu().numpy()
        input_np2 = input_tensor[1].cpu().numpy()
        input_np3 = input_tensor[2].cpu().numpy()


        """plt.figure(figsize=(12, 5))
        plt.subplot(1, 4, 1)
        plt.title("input channel1")
        plt.imshow(input_np1, cmap='gray')
    
        plt.subplot(1, 4, 2)
        plt.title("input channel2")
        plt.imshow(input_np2, cmap='gray')
    
        plt.subplot(1, 4, 3)
        plt.title("input channel3")
        plt.imshow(input_np3, cmap='gray')"""

        #plt.subplot(1, 4, 4)
        plt.title("Output prediction")
        plt.imshow(input_np2, cmap='gray')
        plt.imshow(red_mask)

        buffer = BytesIO()
        plt.savefig(buffer, format="png")
        buffer.seek(0)
        byte_array = bytearray(buffer.getvalue())
        buffer.close()

        return byte_array


def to_tensor(img_bytes, img=None):
    if not img:
        img = Image.open(BytesIO(img_bytes)).convert("RGB")

    arr = np.array(img).astype(np.float32) / 255.0
    arr = np.transpose(arr, (2, 0, 1))
    tensor = torch.from_numpy(arr)

    return tensor
