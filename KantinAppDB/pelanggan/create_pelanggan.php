<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

// array for JSON response
$response = array();
//print_r($_POST);
 
// check for required fields
if (isset($_POST['nama_pelanggan']) && isset($_POST['id_user'])) {
 
    $nama_pelanggan = $_POST['nama_pelanggan'];
    $id_user = $_POST['id_user'];

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO pelanggan(nama_pelanggan, id_user) VALUES('$nama_pelanggan', '$id_user')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>