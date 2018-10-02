<?php
    $con = mysqli_connect("127.0.0.1", "root", "ppiyong", "user1");

    $username = $_POST["username"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($con, "SELECT name,username,age,password,aduino_id,sex FROM user WHERE username = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $name, $username, $age, $password, $aduino_id,$sex);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["name"] = $name;
        $response["age"] = $age;
        $response["username"] = $username;
        $response["password"] = $password;
	      $response["aduino_id"]=$aduino_id;
	      $response["sex"]=$sex;
    }

    echo json_encode($response);
?>
