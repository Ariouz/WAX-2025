function handleDcmFileUploadForm() {
    let form = document.getElementById('form');
    form.addEventListener('submit', async (e) => {
        showLoader();
        e.preventDefault();
        let data = await fetchSegmentedDicomFile(new FormData(form));
        hideLoader();
        if (!data.success) {
            console.log(data.message);
            return ;
        }

        console.log(data.message);
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


    return data.json();
}