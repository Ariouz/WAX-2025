function getLoader() {
    return document.getElementById("loader_container");
}

function hideLoader() {
    getLoader().style.opacity = "0";
}

function showLoader() {
    getLoader().style.opacity = "1";
}