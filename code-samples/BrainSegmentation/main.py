"""
# This is a test script to run the brain segmentation model from mateuszbuda/brain-segmentation-pytorch
https://pytorch.org/hub/mateuszbuda_brain-segmentation-pytorch_unet/
"""
import torch
import urllib
# import numpy as np
from torchvision import transforms
from PIL import Image
# import os
# import pydicom
import matplotlib.pyplot as plt
# from collections import deque
import io

''' --------------- Download the model --------------- '''

model = torch.hub.load('mateuszbuda/brain-segmentation-pytorch', 'unet',
                       in_channels=3, out_channels=1, init_features=32, pretrained=True)

''' --------------- Download the example image --------------- '''
url, filename = ("https://github.com/mateuszbuda/brain-segmentation-pytorch/raw/master/assets/TCGA_CS_4944.png",
                 "TCGA_CS_4944.png")
try: urllib.URLopener().retrieve(url, filename)
except: urllib.request.urlretrieve(url, filename)

input_image = Image.open(filename)

''' --------------- prepare image --------------- '''

# m, s = np.mean(input_image, axis=(0, 1)), np.std(input_image, axis=(0, 1))
preprocess = transforms.Compose([
    transforms.ToTensor(),
    # transforms.Normalize(mean=m, std=s),
])
input_tensor = preprocess(input_image)
input_batch = input_tensor.unsqueeze(0)

''' --------------- Inference --------------- '''

device = torch.device("cpu" if not torch.cuda.is_available() else "cuda")
input_batch = input_batch.to(device)
model = model.to(device)

with torch.no_grad():
    output = model(input_batch)

output_np = torch.round(output[0][0]).cpu().numpy().astype(int)
output_msk = output_np


''' --------------- Visualize the result --------------- '''
input_np1 = input_tensor[0].cpu().numpy()
input_np2 = input_tensor[1].cpu().numpy()
input_np3 = input_tensor[2].cpu().numpy()


plt.figure(figsize=(12, 5))
plt.subplot(1, 4, 1)
plt.title("input channel1")
plt.imshow(input_np1, cmap='gray')

plt.subplot(1, 4, 2)
plt.title("input channel2")
plt.imshow(input_np2, cmap='gray')

plt.subplot(1, 4, 3)
plt.title("input channel3")
plt.imshow(input_np3, cmap='gray')

plt.subplot(1, 4, 4)
plt.title("Output prediction")
plt.imshow(input_np2, cmap='gray')
plt.imshow(output_msk, cmap='hot', alpha=0.3)

buffer = io.BytesIO()
plt.savefig(buffer, format="png")
buffer.seek(0)
byte_array = bytearray(buffer.getvalue())
buffer.close()

image = Image.open(io.BytesIO(byte_array))
image.save("chart.png")
