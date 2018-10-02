<?php
  $conn = mysqli_connect("127.0.0.1", "root", "ppiyong", "ArduinoData");

  $statement = mysqli_prepare($conn, "SELECT * FROM gps_data ORDER BY num DESC LIMIT 1");
  mysqli_stmt_execute($statement);

  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $num, $gps_lat, $gps_long, $gps_data, $gps_time);

  $response = array()
  $response["success"] = false;

  while(mysqli_stmt_fetch($statement)) {
    $response["success"] = true;
    $response["num"] = $num;
    $response["gps_lat"] = $gps_lat;
    $response["gps_long"] = $gps_long;
    $response["gps_data"] = $gps_data;
    $response["gps_time"] = $gps_time;
  }

  echo json_encode($response);
?>
