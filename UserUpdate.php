<?php
$con = mysqli_connect("127.0.0.1", "root", "ppiyong", "user1");
    $age = $_POST['age'];
    $name = $_POST['name'];
    $sex = $_POST['sex'];   
    $username = $_POST['username'];
    $sql = "UPDATE user SET name = '$name', age = '$age', sex = '$sex' WHERE username = '$username'";
    $response = array();
    $response["success"] = false;
    //Updating database table
    if(mysqli_query($con,$sql)){
   
	
      $response["success"] = true;
    }
 

	echo json_encode($response);
         mysqli_close($con);
  
?>

