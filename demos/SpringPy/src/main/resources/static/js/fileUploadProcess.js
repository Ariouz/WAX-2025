function handleFileInput()
{
    let input = document.getElementById("dcmFile");

    let selectFileSpan = document.getElementById("selected-file");
    input.addEventListener("input", (e) => {
        selectFileSpan.innerText = `Selected File: ${e.target.files[0].name}`;
    })
}

function handleDcmFileUploadForm() {
    let form = document.getElementById('form');
    form.addEventListener('submit', async (e) => {
        showLoader();
        e.preventDefault();

        let imageUrl = await fetchSegmentedDicomFile(new FormData(form));
        hideLoader();
        if (imageUrl) {
            let segmentedImage = document.getElementById("segmented-image");
            segmentedImage.src = imageUrl;
            segmentedImage.style.opacity = "1";
            setTimeout(() => { segmentedImage.scrollIntoView(); }, 100);
        }
    })
}

async function fetchSegmentedDicomFile(formData) {
    let url = "http://localhost:8080/segment";

    console.log(formData);
    let data = await fetch(url, {
        method: 'POST',
        body: formData,
    });

    if (!data.ok) return null;

    let blob = await data.blob();

    return imageUrl = URL.createObjectURL(blob);
}