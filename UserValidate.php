<?php

    $con = mysqli_connect("127.0.0.1", "root", "ppiyong", "user1");

     $userID = $_POST["userID"];
     $statement = mysqli_prepare($con, "SELECT username FROM user WHERE username = ?");

     mysqli_stmt_bind_param($statement, "s", $userID);
     mysqli_stmt_execute($statement);
     mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $userID);
     $response = array();
     $response["success"] = true;
     while(mysqli_stmt_fetch($statement)){
       $response["success"] = false;
       $response["username"] = $userID;
     }
     
     echo json_encode($response);
?>

