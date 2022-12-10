<link rel="stylesheet" href="style.css" />
<html lang=""><title>CAT201 Project</title></html>

<?php
$target_dir = getcwd()."/uploadedFile/";
//$target_dir = "/uploadedFile/";
$target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
$uploadOk = 1;
$fileExt= strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
$fileName = pathinfo($target_file,PATHINFO_FILENAME);

// Allow certain file formats
if($fileExt != "pdf") {
    $uploadOk = 0;
//  echo "\n\nSorry, pdf file are allowed"; ?>

    <div class="alert">
  <span class="closebtn"
        onclick="this.parentElement.style.display='none';">&times;</span>
        Sorry, only pdf file is allowed
    </div>

    <?php
}

// Check if $uploadOk is set to 0 by an error
if ($uploadOk == 0) {
    echo "<div class=message id = failed> Your file was not uploaded.</div>";

// if everything is ok, try to upload file
} else {
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"],
        $target_file)) {


        echo "\n\nThe file " . basename($_FILES["fileToUpload"]["name"]) . "
has been uploaded.";
        //record the file name in txt file
        $txt = fopen(getcwd() . "/fileName.txt", "w");
        fwrite($txt, $fileName);

        //Exec java in shell
        $output = "java -jar PdfTxt.jar";
        echo shell_exec($output);

        ?>
        <a href="convertedFile/<?php echo $fileName ?>_converted.txt"
           download >
            <input class=button id = download type="button" value="Download
here !"></a>


        <?php
    } else {
        echo "\n\nSorry, there was an error uploading your file.";
        echo "\n " . $_FILES["fileToUpload"]["error"];
    }
}

header('index.html');
?>
