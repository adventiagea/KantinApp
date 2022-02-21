<?php
 
/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();

require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
$db = new DB_CONNECT();
 
// check for required fields
if (isset($_POST['id_pelanggan']) && isset($_POST['id_user']) && isset($_POST['nama_pelanggan'])) {
 
    $id_pelanggan = $_POST['id_pelanggan'];
    $id_user = $_POST['id_user'];
    $nama_pelanggan = $_POST['nama_pelanggan'];

 
    // include db connect class
    
 
    // mysql update row with matched pid
    $result = mysql_query("UPDATE pelanggan SET nama_pelanggan = '$nama_pelanggan' where id_pelanggan = $id_pelanggan AND id_user = $id_user");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "Item successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>