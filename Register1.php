<?php
    $con = mysqli_connect("127.0.0.1", "root", "ppiyong", "user1");

    $name = $_POST["name"];
    $age = $_POST["age"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $aduino=$_POST["aduino"];
    $social = $_POST["social"];
    $sex = $_POST["sex"];
    $statement = mysqli_prepare($con, "INSERT INTO user (name, username, age, password, aduino_id, social_worker,sex) VALUES (?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssissss", $name, $username, $age, $password,$aduino,$social, $sex);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>

