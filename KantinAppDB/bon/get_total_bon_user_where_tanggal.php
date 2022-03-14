<?php

$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();

if (isset($_GET['id_pelanggan']) && isset($_GET['id_user']) && isset($_GET['tanggal'])) {

    $id_pelanggan = $_GET['id_pelanggan'];
    $id_user = $_GET['id_user'];
    $tanggal = $_GET['tanggal'];
}
 
// get all products from products table
$result = mysql_query("SELECT SUM(harga_total) AS total FROM bon where id_pelanggan = $id_pelanggan AND id_user = $id_user AND tanggal = $tanggal");
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["bon"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["total"] = $row["total"];
        // push single product into final response array
        array_push($response["bon"], $product);
    }
    // success
    //$response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>