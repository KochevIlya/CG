let src;

document.getElementById("imageUpload").onchange = function (e) {
    let imgElement = new Image();
    imgElement.onload = function () {
        let originalCanvas = document.getElementById("originalCanvas");
        originalCanvas.width = imgElement.width;
        originalCanvas.height = imgElement.height;
        let ctx = originalCanvas.getContext("2d");
        ctx.drawImage(imgElement, 0, 0);
        
        src = cv.imread(originalCanvas);
    };
    imgElement.src = URL.createObjectURL(e.target.files[0]);
};


function showProcessedImage(dst) {
    let processedCanvas = document.getElementById("processedCanvas");
    processedCanvas.width = dst.cols;
    processedCanvas.height = dst.rows;
    cv.imshow("processedCanvas", dst);
    dst.delete();
}

function applySharpening(C = 5) {
    if (!src) return;  
    let dst = new cv.Mat();
    let kernel = cv.matFromArray(3, 3, cv.CV_32F, [
        0, -1,  0,
        -1,  C, -1,
        0, -1,  0
    ]);
    cv.filter2D(src, dst, cv.CV_8U, kernel);
    showProcessedImage(dst);
    kernel.delete();
}

function applyAdaptiveThreshold() {
    if (!src) return;
    let gray = new cv.Mat();
    cv.cvtColor(src, gray, cv.COLOR_RGBA2GRAY);
    let dst = new cv.Mat();
    cv.adaptiveThreshold(
        gray, dst, 255,
        cv.ADAPTIVE_THRESH_GAUSSIAN_C,
        cv.THRESH_BINARY,
        11, 2
    );
    showProcessedImage(dst);
    gray.delete();
}


function applyOtsuThreshold() {
    if (!src) return;
    let gray = new cv.Mat();
    cv.cvtColor(src, gray, cv.COLOR_RGBA2GRAY);
    let dst = new cv.Mat();
    cv.threshold(gray, dst, 0, 255, cv.THRESH_BINARY | cv.THRESH_OTSU);
    showProcessedImage(dst);
    gray.delete();
}


function applyHighPassFilter() {
    if (!src) return;
    let blurred = new cv.Mat();
    let highPass = new cv.Mat();

    cv.GaussianBlur(src, blurred, new cv.Size(15, 15), 0);
    cv.subtract(src, blurred, highPass);
    let result = new cv.Mat();
    cv.addWeighted(src, 1, highPass, 1.5, 0, result);
    
    showProcessedImage(result);
    blurred.delete();
    highPass.delete();
    result.delete();
}



function applyUnsharpMasking() {
    if (!src) return;
    let blurred = new cv.Mat();
    cv.GaussianBlur(src, blurred, new cv.Size(9, 9), 10, 10);
    let mask = new cv.Mat();
    cv.addWeighted(src, 1.5, blurred, -0.5, 0, mask);
    showProcessedImage(mask);
    blurred.delete();
}

function applyAdaptiveSharpening() {
    if (!src) return;
    let gray = new cv.Mat();
    let laplacian = new cv.Mat();
    let adaptiveSharp = new cv.Mat();

    cv.cvtColor(src, gray, cv.COLOR_RGBA2GRAY);
    cv.Laplacian(gray, laplacian, cv.CV_8U, 3, 1, 0, cv.BORDER_DEFAULT);
    cv.cvtColor(laplacian, laplacian, cv.COLOR_GRAY2RGBA);
    cv.addWeighted(src, 1, laplacian, 0.7, 0, adaptiveSharp);
    
    showProcessedImage(adaptiveSharp);
    gray.delete();
    laplacian.delete();
    adaptiveSharp.delete();
}

function applyAdaptiveMeanThreshold(blockSize = 11, C = 2) {
    if (!src) return;

    if (blockSize % 2 === 0) blockSize += 1;
    if (blockSize < 3) blockSize = 3;

    let gray = new cv.Mat();
    cv.cvtColor(src, gray, cv.COLOR_RGBA2GRAY);
    let dst = new cv.Mat();

    cv.adaptiveThreshold(
        gray, dst, 255,
        cv.ADAPTIVE_THRESH_MEAN_C,
        cv.THRESH_BINARY,
        blockSize, C
    );

    showProcessedImage(dst);
    gray.delete();
    dst.delete(); 
}

function applyAdaptiveGaussianThreshold(blockSize = 11, C = 2) {
    if (!src) return;
    if (blockSize % 2 === 0) blockSize += 1;
    if (blockSize < 3) blockSize = 3;
    
    let gray = new cv.Mat();
    cv.cvtColor(src, gray, cv.COLOR_RGBA2GRAY); 
    let dst = new cv.Mat();
    cv.adaptiveThreshold(
        gray, dst, 255,
        cv.ADAPTIVE_THRESH_GAUSSIAN_C,
        cv.THRESH_BINARY,
        blockSize, C
    );
    showProcessedImage(dst); 
    gray.delete(); 
    dst.delete(); 
}



