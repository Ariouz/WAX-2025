function getLoader() {
    return document.getElementById("loader_container");
}

function hideLoader() {
    setTimeout(() => {
        getLoader().style.opacity = "0";
        getLoader().style.display = "none";
    }, 1000);
}

function showLoader() {
    getLoader().style.display = "block";
    getLoader().style.opacity = "1";
}